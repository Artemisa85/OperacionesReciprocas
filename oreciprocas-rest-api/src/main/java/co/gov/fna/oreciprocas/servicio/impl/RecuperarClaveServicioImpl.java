/*
 * Copyright 2018. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.servicio.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import co.gov.fna.core.common.exception.ErrorDetail;
import co.gov.fna.oreciprocas.dominio.dto.AdjuntoEmailDTO;
import co.gov.fna.oreciprocas.dominio.dto.NotificacionDTO;
import co.gov.fna.oreciprocas.dominio.dto.RecuperarClaveRespuestaDTO;
import co.gov.fna.oreciprocas.dominio.dto.RecuperarClaveSolicitudDTO;
import co.gov.fna.oreciprocas.dominio.modelo.ContrasenaHistorial;
import co.gov.fna.oreciprocas.dominio.modelo.Entidad;
import co.gov.fna.oreciprocas.dominio.modelo.UsuarioExterno;
import co.gov.fna.oreciprocas.exception.EntidadNoEncontradaException;
import co.gov.fna.oreciprocas.repositorio.ContrasenaHistorialRepositorio;
import co.gov.fna.oreciprocas.repositorio.EntidadRepositorio;
import co.gov.fna.oreciprocas.repositorio.UsuarioExternoRepositorio;
import co.gov.fna.oreciprocas.servicio.ClaveServicio;
import co.gov.fna.oreciprocas.servicio.NotificacionServicio;
import co.gov.fna.oreciprocas.servicio.PlantillaServicio;
import co.gov.fna.oreciprocas.servicio.RecuperarClaveServicio;
/**
 * Implementaci&oacute;n por defecto de {@link RecuperarClaveServicio}
 *
 * @author Zamir Garc&iacute;a Romero - zagarcia@ayesa.com
 * @version 1.0
 *
 */
@Service
public class RecuperarClaveServicioImpl implements RecuperarClaveServicio {
	
	
	private static String NOMBRE_PLANTILLA = "recuperar-clave";

	@Autowired
	private EntidadRepositorio repositorioEntidad;

	@Autowired
	private UsuarioExternoRepositorio repositorioUsuario;

	@Autowired
	private ClaveServicio servicioGenerarClave;

	@Autowired
	public PasswordEncoder passwordEncoder;

	@Autowired
	private PlantillaServicio plantillaServicio;
	
	@Autowired
	private ContrasenaHistorialRepositorio repositorioHistorial;

	@Autowired
	private NotificacionServicio notificacionServicio;

	/**
	  * {@inheritDoc}
	  */
	public RecuperarClaveRespuestaDTO recuperarClave(RecuperarClaveSolicitudDTO entrada) {
		
		String mensajeServicio = "Una contraseña temporal ha sido enviada a %s";
		
		Optional<Entidad> optEntidad = 
				repositorioEntidad.findByIdCGNAndNit(entrada.getIdCGN(), entrada.getNit());
		
		if ( !optEntidad.isPresent() ) {
			lanzarExcepcionUsuarioNoValido();
		}

		UsuarioExterno usuario = optEntidad.get().getUsuario();
		
		if ( Objects.isNull(usuario) ) {
			lanzarExcepcionUsuarioNoValido();
		}

		String claveEnClaro = asignarNuevaClave(usuario);
		
		mensajeServicio =  String.format(mensajeServicio, usuario.getCorreo());
		
		Map<String, Object> variables = new HashMap<>();
		variables.put("entidad", optEntidad.get());
		variables.put("clave", claveEnClaro);
		variables.put("imgEncabezado","imgEncabezado");
		variables.put("imgPie","imgPie");
		
		String mensaje = plantillaServicio.procesarPlantilla(variables, NOMBRE_PLANTILLA);
		
		AdjuntoEmailDTO encabezado = AdjuntoEmailDTO.builder()
				.id("imgEncabezado")
				.origen(new ClassPathResource("images/email_header.png"))
				.tipoContenido("image/png")
				.modo(AdjuntoEmailDTO.MODO_EN_LINEA)
				.build();
		
		AdjuntoEmailDTO pie = AdjuntoEmailDTO.builder()
				.id("imgPie")
				.origen(new ClassPathResource("images/email_footer.png"))
				.tipoContenido("image/png")
				.modo(AdjuntoEmailDTO.MODO_EN_LINEA)
				.build();
		
		NotificacionDTO notificacion = NotificacionDTO.builder()
				.asunto("Recuperación de clave")
				.destinatarios(new String[]{usuario.getCorreo()})
				.mensaje(mensaje)
				.adjuntos(new AdjuntoEmailDTO[] {encabezado, pie})
				.contenidoHtmlL(true)
				.build();
		notificacionServicio.notificar(notificacion);

		return RecuperarClaveRespuestaDTO.builder().mensaje(mensajeServicio).build();
	}

	/**
	 * Asigna una nueva clave aleatoria a un usuario.
	 * @param usuario Usuario al que se le asignar&aacute; la nueva clave aleatoria.
	 */
	private String asignarNuevaClave(UsuarioExterno usuario) {
		String nuevaClave = servicioGenerarClave.generarClave();
		String nuevaClaveCodificada = passwordEncoder.encode(nuevaClave);
		String claveAnteriorCodificada = usuario.getContrasena();
		
		usuario.setContrasena(nuevaClaveCodificada);
		usuario.setContrasenaExpirada(true);
		usuario.setCuentaBloqueada(false);
		usuario.setIntentosFallidos(0);
		
		ContrasenaHistorial historial = ContrasenaHistorial.builder()
				.idUsuario(usuario.getId())
				.contrasena(claveAnteriorCodificada)
				.fechaCambioContrasena(new Date())
				.build();
		
		repositorioHistorial.save(historial);
		
		repositorioUsuario.save(usuario);
		return nuevaClave;
	}

	/**
	 * Lanza una excepci&oacute;n indicando que la entidad no es v&aacute;lida para continuar con 
	 * el registro.
	 */
	private void lanzarExcepcionUsuarioNoValido() {
		ErrorDetail errorDetail = new ErrorDetail();
		errorDetail.setCode("USUARIO_NO_VALIDO");
		errorDetail.setMessage("Usuario no registrado o no tiene permisos para ingresar a la aplicación"); 
		errorDetail.setTimestamp(new Date());
		throw new EntidadNoEncontradaException(errorDetail, HttpStatus.BAD_REQUEST);
	}

}
