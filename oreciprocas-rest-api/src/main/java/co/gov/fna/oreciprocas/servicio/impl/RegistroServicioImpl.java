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
import co.gov.fna.core.common.exception.FnaException;
import co.gov.fna.oreciprocas.constantes.OReciprocasConstantes;
import co.gov.fna.oreciprocas.dominio.dto.AdjuntoEmailDTO;
import co.gov.fna.oreciprocas.dominio.dto.NotificacionDTO;
import co.gov.fna.oreciprocas.dominio.dto.RegistroUsuarioRespuestaDTO;
import co.gov.fna.oreciprocas.dominio.dto.RegistroUsuarioSolicitudDTO;
import co.gov.fna.oreciprocas.dominio.modelo.Entidad;
import co.gov.fna.oreciprocas.dominio.modelo.UsuarioExterno;
import co.gov.fna.oreciprocas.exception.EntidadNoEncontradaException;
import co.gov.fna.oreciprocas.repositorio.EntidadRepositorio;
import co.gov.fna.oreciprocas.repositorio.UsuarioExternoRepositorio;
import co.gov.fna.oreciprocas.servicio.NotificacionServicio;
import co.gov.fna.oreciprocas.servicio.PlantillaServicio;
import co.gov.fna.oreciprocas.servicio.RegistroServicio;

/**
 * Implementaci&oacute;n por defecto de {@link RegistroServicio}
 *
 * @author Zamir Garc&iacute;a Romero - zagarcia@ayesa.com
 * @version 1.0
 *
 */
@Service
public class RegistroServicioImpl implements RegistroServicio {
	
	
	
	@Autowired
	private PlantillaServicio plantillaServicio;
	
	@Autowired
	private NotificacionServicio notificacionServicio;
	
	@Autowired
	private EntidadRepositorio repositorioEntidad;
	
	@Autowired
	private UsuarioExternoRepositorio repositorioUsuarioExterno;
	
	@Autowired
	private ClaveServicioImpl servicioClave;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public RegistroUsuarioRespuestaDTO registrar(RegistroUsuarioSolicitudDTO entrada) {
		Optional<Entidad> optEntidad = 
				repositorioEntidad.findByIdCGNAndNit(entrada.getIdCGN(), entrada.getNit());
		if ( ! optEntidad.isPresent() || entrada.getIdCGN().equals(OReciprocasConstantes.ID_FNA)) {
			lanzarExcepcionEntidadNoValida();
		}
		Entidad entidad = optEntidad.get();
		if ( !Objects.isNull(entidad.getUsuario()) ) {
			lanzarExcepcionEntidadYaRegistrada();
		}
		String claveEnClaro = servicioClave.generarClave();
		entidad.setCorreo(entrada.getCorreo());
		
		String roles = asignarRoles(entrada);
		
		UsuarioExterno usuario = UsuarioExterno.builder()
				.usuario(entrada.getIdCGN())
				.contrasena(passwordEncoder.encode(claveEnClaro))
				.correo(entrada.getCorreo())
				.nombre(entidad.getNombreContacto()) 
				.apellido(entidad.getApellidoContacto())
				.entidad(entidad)
				.cuentaActiva(true)
				.contrasenaExpirada(true)
				.fechaRegistro(new Date())
				.roles(roles)
				.build();
		
		repositorioUsuarioExterno.save(usuario);
		
		Map<String, Object> variables = new HashMap<>();
		variables.put("entidad", entidad);
		variables.put("clave", claveEnClaro);
		variables.put("imgEncabezado","imgEncabezado");
		variables.put("imgPie","imgPie");
		
		String mensaje = plantillaServicio.procesarPlantilla(variables,
				OReciprocasConstantes.PLANTILLA_CORREO_REGISTRO);
		
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
				.asunto("Se ha creado su cuenta exitosamente")
				.destinatarios( new String[] {usuario.getCorreo()} )
				.mensaje(mensaje)
				.contenidoHtmlL(true)
				.adjuntos(new AdjuntoEmailDTO[] {encabezado, pie})
				.build();
		notificacionServicio.notificar(notificacion);
		
		
		RegistroUsuarioRespuestaDTO respuesta = 
				RegistroUsuarioRespuestaDTO.builder()
				.usuario(usuario.getUsuario())
				.mensajeRespuesta("La contraseña ha sido enviada al correo especificado")
				.build();
		
		return respuesta;
	}

	/**
	 * Determina los roles que deben asignarse al usuario.
	 * @param entrada 
	 * @return Cadena con los roles asociados al usuario.
	 */
	private String asignarRoles(RegistroUsuarioSolicitudDTO entrada) {
		if (OReciprocasConstantes.ID_CGN.equals(entrada.getIdCGN())) {
			return OReciprocasConstantes.ROL_CGN;
		}
		return OReciprocasConstantes.ROL_EXTERNO;
	}

	/**
	 * Lanza una excepci&oacute;n indicando que la entidad no es v&aacute;lida para continuar 
	 * con el registro.
	 */
	private void lanzarExcepcionEntidadNoValida() {
		ErrorDetail errorDetail = new ErrorDetail();
		errorDetail.setCode("USUARIO_NO_VALIDO");
		errorDetail.setMessage("El NIT / Usuario Ingresado no se encuentra en la base datos de"
				+ " Entidades del FNA, o No tiene permisos para ingresar a la aplicación"); 
		errorDetail.setTimestamp(new Date());
		throw new EntidadNoEncontradaException(errorDetail, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Lanza una excepci&oacute;n indicando que el usuario ya se encuentra registrado.
	 */
	private void lanzarExcepcionEntidadYaRegistrada() {
		ErrorDetail errorDetail = new ErrorDetail();
		errorDetail.setCode("USUARIO_EN_USO");
		errorDetail.setMessage("El NIT / Usuario no puede ser registrado más de una vez"); 
		errorDetail.setTimestamp(new Date());
		throw new FnaException(errorDetail, HttpStatus.BAD_REQUEST);
		
	}

}
