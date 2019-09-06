/*
 * Copyright 2019. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.servicio;

import java.math.BigInteger;

import org.springframework.security.core.Authentication;

import co.gov.fna.oreciprocas.dominio.dto.ComentarioDTO;
import co.gov.fna.oreciprocas.dominio.dto.ComentarioResultDTO;
import co.gov.fna.oreciprocas.dominio.dto.ComentarioSolicitudDTO;

/**
 * Servicio para gestionar los comentarios
 *
 * @author Zamir Garc&iacute;a Romero - zagarcia@ayesa.com
 * @version 1.0
 *
 */
public interface ComentarioServicio {

	/**
	 * Busca los comentarios asociados a una transacci&oacute;n.
	 * @param idTransaccion Identificador de la transacci&oacute;n.
	 * @param serverContext Contexto en donde fue realizada la solicitud.
	 * @return Objeto que incluye los comentarios asociados con la transacci&oacute;n.
	 */
	ComentarioResultDTO buscarPorTransaccion(BigInteger idTransaccion, String serverContext);

	/**
	 * Crea un comentario de acuerdo con la solicitud ingresada
	 * @param solicitud Datos de solicitud
	 * @param authentication Datos de autenticaci&oacute;n del usuario.
	 * @return El comentario creado
	 */
	ComentarioDTO crearComentario(ComentarioSolicitudDTO solicitud, Authentication authentication);

}
