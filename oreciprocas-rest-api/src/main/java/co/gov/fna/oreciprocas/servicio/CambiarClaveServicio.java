/*
 * Copyright 2018. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.servicio;

import co.gov.fna.oreciprocas.dominio.dto.CambiarClaveRespuestaDTO;
import co.gov.fna.oreciprocas.dominio.dto.CambiarClaveSolicitudDTO;

/**
 * Servicio para gesti&oacute;n de cambio de clave.
 *
 * @author Zamir Garc&iacute;a Romero - zagarcia@ayesa.com
 * @version 1.0
 *
 */
public interface CambiarClaveServicio {

	/**
	 * Cambia la contrase&ntilde;a actual para el usuario asociado a la entidad
	 * CambiarClaveEntradaDTO con la informaci&oacute;n del nit de la entidad,
	 * el identificador asignado por la Contadur&iacute;a General de la Naci&oacute;n
	 * y el correo del usuario
	 * @return Informaci&oacute;n de la respuesta del m&eacute;todo
	 */
	CambiarClaveRespuestaDTO cambiarClave(CambiarClaveSolicitudDTO solicitud);

}
