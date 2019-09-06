/*
 * Copyright 2019. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.servicio.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import co.gov.fna.core.common.exception.ErrorDetail;
import co.gov.fna.core.common.exception.FnaException;
import co.gov.fna.oreciprocas.adaptador.impl.ComentarioAdaptador;
import co.gov.fna.oreciprocas.constantes.OReciprocasConstantes;
import co.gov.fna.oreciprocas.dominio.dto.AdjuntoEmailDTO;
import co.gov.fna.oreciprocas.dominio.dto.ComentarioDTO;
import co.gov.fna.oreciprocas.dominio.dto.ComentarioResultDTO;
import co.gov.fna.oreciprocas.dominio.dto.ComentarioSolicitudDTO;
import co.gov.fna.oreciprocas.dominio.dto.EntidadDTO;
import co.gov.fna.oreciprocas.dominio.dto.NotificacionDTO;
import co.gov.fna.oreciprocas.dominio.dto.TransaccionDTO;
import co.gov.fna.oreciprocas.dominio.enumeracion.EstadoTransaccion;
import co.gov.fna.oreciprocas.dominio.modelo.Comentario;
import co.gov.fna.oreciprocas.repositorio.ComentarioRepositorio;
import co.gov.fna.oreciprocas.servicio.ComentarioServicio;
import co.gov.fna.oreciprocas.servicio.ConfiguracionServicio;
import co.gov.fna.oreciprocas.servicio.EntidadServicio;
import co.gov.fna.oreciprocas.servicio.NotificacionServicio;
import co.gov.fna.oreciprocas.servicio.PlantillaServicio;
import co.gov.fna.oreciprocas.servicio.TransaccionServicio;

import static co.gov.fna.oreciprocas.constantes.OReciprocasConstantes.PLANTILLA_CORREO_COMENTARIO;
import static co.gov.fna.oreciprocas.constantes.OReciprocasConstantes.DB_DOMINIO_GLOBAL;

/**
 * Implementaci&oacute;n por defecto de {@link ComentarioServicio}
 *
 * @author Zamir Garc&iacute;a Romero - zagarcia@ayesa.com
 * @version 1.0
 *
 */
@Service
public class ComentarioServicioImpl implements ComentarioServicio {
	
	private static Logger logger = LoggerFactory.getLogger(ComentarioServicioImpl.class);
	
		
	@Autowired
	private ComentarioRepositorio comentarioRepositorio;
	
	@Autowired
	private TransaccionServicio transaccionServicio;
	
	@Autowired
	private EntidadServicio entidadServicio;

	@Autowired
	private PlantillaServicio plantillaServicio;
	
	@Autowired
	private NotificacionServicio notificacionServicio;
	
	@Autowired
	private ConfiguracionServicio configuracionServicio;
	
	@Autowired
	private ComentarioAdaptador comentarioAdaptador;

	/**
	  * {@inheritDoc}
	  */
	@Override
	public ComentarioResultDTO buscarPorTransaccion(BigInteger idTransaccion, String serverContext) {
		
		Collection<Comentario> comentarios = 
				comentarioRepositorio.findByIdTransaccion(idTransaccion);
		
		comentarios.stream().filter(comentario -> !Objects.isNull(comentario.getAdjuntos()))
			.forEach(comentario -> {
				comentario.getAdjuntos().forEach(adjunto -> {
					adjunto.setUrl(serverContext + "/api/adjuntos/" + adjunto.getId());
				});
			});
		
		Collection<ComentarioDTO> comentariosOut = 
				comentarioAdaptador.modeloAdto(comentarios);
		
		return ComentarioResultDTO.builder()
				.comentarios(comentariosOut)
				.build();
	}

	/**
	  * {@inheritDoc}
	  */
	@Override
	public ComentarioDTO crearComentario(ComentarioSolicitudDTO solicitud, Authentication authentication) {
		logger.info("Registrando comentario de: {}", authentication.getName());
		List<String> roles = rolesComoLista(authentication);
		String nombreAutor = 
				solicitud.getNombreAutor() == null 
				? authentication.getName(): solicitud.getNombreAutor();
		String rolActual = roles.get(0);
		
		Comentario comentario = Comentario.builder().texto(solicitud.getTexto())
				.idAutor(authentication.getName())
				.autor(nombreAutor).autorRol(rolActual)
				.fecha(new Date()).idTransaccion(solicitud.getIdTransaccion())
				.build();
		
		try {
			comentarioRepositorio.save(comentario);
		} catch (Exception e) {
			logger.error("Error guardando el comentario ");
			logger.error(e.getLocalizedMessage(), e);
			lanzarExcepcionTransaccionNoEncontrada();
		}
		
		TransaccionDTO transaccion = cambiarEstadoTransaccion(rolActual, solicitud);
		EntidadDTO entidadDestino = resolverEntidadDestino(rolActual, transaccion);
		String urlAplicacion = resolverUrlSservidor(rolActual);
		
		
		Map<String, Object> variables = new HashMap<>();
		variables.put("razonSocial", entidadDestino.getRazonSocial());
		variables.put("comentario", solicitud.getTexto());
		variables.put("usuario", authentication.getName());
		variables.put("estado", transaccion.getEstado().getDescripcion());
		variables.put("urlAplicacion", urlAplicacion);
		
		variables.put("imgEncabezado","imgEncabezado");
		variables.put("imgPie","imgPie");
		
		String mensaje = plantillaServicio.procesarPlantilla(variables, PLANTILLA_CORREO_COMENTARIO);
		String asunto = String.format("[Partida conciliatoria %d] Nuevo comentario de %s", 
				solicitud.getIdTransaccion(), authentication.getName());
		
		AdjuntoEmailDTO encabezado = AdjuntoEmailDTO.builder().id("imgEncabezado")
				.origen(new ClassPathResource("images/email_header.png"))
				.tipoContenido("image/png").modo(AdjuntoEmailDTO.MODO_EN_LINEA)
				.build();
		
		AdjuntoEmailDTO pie = AdjuntoEmailDTO.builder().id("imgPie")
				.origen(new ClassPathResource("images/email_footer.png"))
				.tipoContenido("image/png")	.modo(AdjuntoEmailDTO.MODO_EN_LINEA)
				.build();
		
		NotificacionDTO notificacion = NotificacionDTO.builder()
				.asunto(asunto).destinatarios(new String[] {entidadDestino.getCorreo()})
				.mensaje(mensaje).adjuntos(new AdjuntoEmailDTO[] {encabezado, pie})
				.contenidoHtmlL(true)
				.build();
		notificacionServicio.notificar(notificacion);
		
		ComentarioDTO comentarioRespuesta = comentarioAdaptador.modeloAdto(comentario); 
		comentarioRespuesta.setCodEstadoTransaccion(transaccion.getEstado().getCodigo());
		comentarioRespuesta.setDescEstadoTransaccion(transaccion.getEstado().getDescripcion());
		
		return comentarioRespuesta;
	}
	
	/**
	 * Extrae los roles de los datos de autenticaci&oacute;n
	 * @param authentication Datos de autenticaci&oacute;
	 * @return Roles del usuario separados por coma.
	 */
	private List<String> rolesComoLista(Authentication authentication) {
		List<String> roles = new ArrayList<>();
		authentication.getAuthorities().forEach(authority -> {
			roles.add( authority.getAuthority().replaceFirst("ROLE_", "") );
		});
		return roles;
	}
	
	/**
	 * Lanza excepci&oacute;n para transacciones no encontradas. 
	 */
	private void lanzarExcepcionTransaccionNoEncontrada() {
		ErrorDetail errorDetail = new ErrorDetail();
		errorDetail.setCode("TRANSACCION_NO_ENCONTRADA");
		errorDetail.setMessage("Transacción no encontrada."); 
		errorDetail.setTimestamp(new Date());
		throw new FnaException(errorDetail, HttpStatus.NOT_FOUND);
	}

	/**
	 * Cambia el estado de la transacci&oacute;n
	 * @param rolActual Rol del usuario que crea el comentario
	 * @param solicitud Solicitud
	 * @return Transacci&oacute;n a la que se le cambi&oacute; el estado.
	 */
	private TransaccionDTO cambiarEstadoTransaccion(String rolActual, 
			ComentarioSolicitudDTO solicitud) {
		
		TransaccionDTO transaccion = 
				transaccionServicio.buscarTransaccionPorId(solicitud.getIdTransaccion()).get();
		
		EstadoTransaccion estadoTransaccion = EstadoTransaccion.VACIO;
		
		if (solicitud.isCambioManual()) { // Cambio  manual
			if (OReciprocasConstantes.ROL_FNA.equalsIgnoreCase(rolActual)) {
				estadoTransaccion = EstadoTransaccion.get(solicitud.getCodEstado());
			}
		} else { // Cambio automática
			// No se pueden cambiar automáticamente los estados cerrado y obsoleto
			boolean estadoModificable = transaccion.getEstado() != EstadoTransaccion.CERRADO
					&& transaccion.getEstado() != EstadoTransaccion.OBSOLETO;
			
			if (estadoModificable) {
				if (OReciprocasConstantes.ROL_FNA.equalsIgnoreCase(rolActual)) {
					estadoTransaccion = EstadoTransaccion.TRAMITE_ENTIDAD;
				} else {
					estadoTransaccion = EstadoTransaccion.TRAMITE_FNA;
				}
				
			}
		}
		
		transaccion.setEstado(estadoTransaccion);
		
		return transaccionServicio.modificar(transaccion).get();
	}
	
	/**
	 * Cambia el estado de la transacci&oacute;n
	 * @param rolActual Rol del usuario que crea el comentario
	 * @return Transacci&oacute;n a la que se le cambi&oacute; el estado.
	 */
	private EntidadDTO resolverEntidadDestino(String rolActual, TransaccionDTO transaccion) {
		String entidadDestino = "";
		if (OReciprocasConstantes.ROL_FNA.equalsIgnoreCase(rolActual)) {
			entidadDestino = transaccion.getIdCgnEntidadReciproca();
		} else {
			entidadDestino = OReciprocasConstantes.ID_FNA;
		}
		
		return entidadServicio.buscarEntidadPorIdCgn(entidadDestino).get();
	}

	/**
	 * Devuelve la URL de la aplicación dependiendo del ROL.
	 * @param rolActual
	 * @return
	 */
	private String resolverUrlSservidor(String rolActual) {
		String tipoUrl = "";
		if (OReciprocasConstantes.ROL_FNA.equalsIgnoreCase(rolActual)) {
			tipoUrl = "url-ext";
		} else {
			tipoUrl = "url-int";
		}
		return configuracionServicio.obtenerRegistroConfiguracion(DB_DOMINIO_GLOBAL, tipoUrl)
				.getValor();
	}

}
