/*
 * Copyright 2018. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.servicio;

import co.gov.fna.oreciprocas.dominio.dto.RecuperarClaveSolicitudDTO;
import co.gov.fna.oreciprocas.dominio.dto.RecuperarClaveRespuestaDTO;
import co.gov.fna.oreciprocas.dominio.modelo.Entidad;

/**
 * Servicio de negocio para entidades tipo {@link Entidad}
 *
 * @author Zamir Garc&iacute;a Romero - zagarcia@ayesa.com
 * @version 1.0
 *
 */
public interface RecuperarClaveServicio {

	/**
	 * Genera y env&iacute;a una clave aleatoria para el usuario asociado a la entidad
	 * RecuperarClaveEntradaDTO con la informaci&oacute;n del nit de la entidad,
	 * el identificador asignado por la Contadur&iacute;a General de la Naci&oacute;n
	 * y el correo del usuario
	 * @return RecuperarClaveSalidaDTO con la informaci&oacute;n de la respuesta del m&eacute;todo
	 */
	RecuperarClaveRespuestaDTO recuperarClave(RecuperarClaveSolicitudDTO entrada);

}
