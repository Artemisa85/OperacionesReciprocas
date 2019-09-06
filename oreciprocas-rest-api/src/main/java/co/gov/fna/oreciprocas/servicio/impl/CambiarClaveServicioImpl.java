/*
 * Copyright 2018. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.servicio.impl;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import co.gov.fna.core.common.exception.ErrorDetail;
import co.gov.fna.oreciprocas.dominio.dto.CambiarClaveRespuestaDTO;
import co.gov.fna.oreciprocas.dominio.dto.CambiarClaveSolicitudDTO;
import co.gov.fna.oreciprocas.dominio.dto.ValidacionContrasenaDTO;
import co.gov.fna.oreciprocas.dominio.modelo.ContrasenaHistorial;
import co.gov.fna.oreciprocas.dominio.modelo.Entidad;
import co.gov.fna.oreciprocas.dominio.modelo.UsuarioExterno;
import co.gov.fna.oreciprocas.exception.EntidadNoEncontradaException;
import co.gov.fna.oreciprocas.repositorio.ContrasenaHistorialRepositorio;
import co.gov.fna.oreciprocas.repositorio.EntidadRepositorio;
import co.gov.fna.oreciprocas.repositorio.UsuarioExternoRepositorio;
import co.gov.fna.oreciprocas.servicio.CambiarClaveServicio;
import co.gov.fna.oreciprocas.servicio.ValidacionContrasenaServicio;

/**
 * Implementaci&oacute;n por defecto de {@link CambiarClaveServicio}
 *
 * @author Zamir Garc&iacute;a Romero - zagarcia@ayesa.com
 * @version 1.0
 *
 */
@Service
public class CambiarClaveServicioImpl implements CambiarClaveServicio {

	@Autowired
	private EntidadRepositorio repositorioEntidad;

	@Autowired
	private UsuarioExternoRepositorio repositorioUsuario;
	
	@Autowired
	private ContrasenaHistorialRepositorio repositorioHistorial;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private ValidacionContrasenaServicio validacionContrasenaServicio;

	public CambiarClaveRespuestaDTO cambiarClave(CambiarClaveSolicitudDTO entrada) {
		
		
		
		Optional<Entidad> optEntidad = 
				repositorioEntidad.findByIdCGNAndNit(entrada.getIdCGN(), entrada.getNit());
		
		if (! optEntidad.isPresent() ) {
			lanzarExcepcionEntidadNoValida();
		}
		
		UsuarioExterno usuario = optEntidad.get().getUsuario();
		
		if ( Objects.isNull(usuario) ) {
			lanzarExcepcionEntidadNoValida();
		}
		
		ValidacionContrasenaDTO contrasenaDTO = ValidacionContrasenaDTO.builder()
				.contrasenaActual(entrada.getContrasenaActual())
				.contrasenaNueva(entrada.getContrasenaNueva())
				.confirmacionContrasena(entrada.getConfirmacionContrasena())
				.usuario(usuario)
				.build();
		
		validacionContrasenaServicio.validar(contrasenaDTO);
		
		CambiarClaveRespuestaDTO salida = cambiarClave(entrada, usuario);
		
		return salida;
	}
	


	/**
	 * Cambia la contrase&ntilde;a del usuario
	 * @param entrada Datos nuevo password
	 * @param usuario Usuario al que se cambian las credenciales
	 * @return 
	 */
	private CambiarClaveRespuestaDTO cambiarClave(CambiarClaveSolicitudDTO entrada, 
												UsuarioExterno usuario) {
		Date ahora = new Date();
		String claveAnteriorCodificada = usuario.getContrasena();
		
		usuario.setContrasenaExpirada(false);
		usuario.setContrasena(passwordEncoder.encode(entrada.getContrasenaNueva()));
		usuario.setCuentaBloqueada(false);
		usuario.setIntentosFallidos(0);
		usuario.setFechaCambioContrasena(ahora);
		repositorioUsuario.save(usuario);
		
		ContrasenaHistorial historial = ContrasenaHistorial.builder()
				.idUsuario(usuario.getId())
				.contrasena(claveAnteriorCodificada)
				.fechaCambioContrasena(ahora)
				.build();
		
		repositorioHistorial.save(historial);
		
		String mensaje = "Contraseña cambiada exitosamente";
		return CambiarClaveRespuestaDTO.builder().mensaje(mensaje ).build();
	}

	/**
	 * Lanza una excepci&oacute;n indicando que la entidad no es v&aacute;lida para continuar con 
	 * el registro.
	 */
	private void lanzarExcepcionEntidadNoValida() {
		ErrorDetail errorDetail = new ErrorDetail();
		errorDetail.setCode("USUARIO_NO_VALIDO");
		errorDetail.setMessage("El NIT / Usuario Ingresado no se encuentra en la base datos de "
				+ "Entidades del FNA, o No tiene permisos para ingresar a la aplicación"); 
		errorDetail.setTimestamp(new Date());
		throw new EntidadNoEncontradaException(errorDetail, HttpStatus.BAD_REQUEST);
	}


}
