/*
 * Copyright 2019. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.controlador;

import java.math.BigInteger;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import co.gov.fna.core.common.generic.rest.FnaDtoResult;
import co.gov.fna.core.common.generic.rest.FnaResponse;
import co.gov.fna.oreciprocas.dominio.dto.ComentarioSolicitudDTO;

/**
 * Controlador REST que gestiona la informaci&oacute;n relacionada con comentarios.
 *
 * @author Zamir Garc&iacute;a Romero - zagarcia@ayesa.com
 * @version 1.0
 *
 */
public interface ComentarioControlador {
	
	/**
	 * Busca un comentario por su identificador.
	 * @param id Identificador del comentario
	 * @return Comentario correspondiente al identificador suministrado.
	 */
	ResponseEntity<FnaResponse<FnaDtoResult>> buscarPorId(BigInteger id);
	
	/**
	 * Crea un comentario asociado a una transacci&oacute;n
	 * @param solicitud Contiene los datos de la solicitud
	 * @param authentication Datos de autenticaci&oacute;n del usuario.
	 * @return El comentario creado.
	 */
	ResponseEntity<FnaResponse<FnaDtoResult>> 
			crearComentario(ComentarioSolicitudDTO solicitud, Authentication authentication);
	
	/**
	 * Permite cargar archivos adjuntos asociados a un comentario.
	 * @param files Archivos que se solicita cargar.
	 * @param idComentario Identificador del comentario al que se asociar&aacute;n los archivos.
	 * @return
	 */
	ResponseEntity<FnaResponse<FnaDtoResult>> cargarAdjuntos(MultipartFile[] files,
			BigInteger idComentario);
	

}
