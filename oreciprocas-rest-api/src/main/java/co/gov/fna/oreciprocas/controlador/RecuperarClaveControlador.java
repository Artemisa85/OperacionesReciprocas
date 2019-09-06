/*
 * Copyright 2019. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.controlador;

import org.springframework.http.ResponseEntity;

import co.gov.fna.core.common.generic.rest.FnaDtoResult;
import co.gov.fna.core.common.generic.rest.FnaResponse;
import co.gov.fna.oreciprocas.dominio.dto.RecuperarClaveSolicitudDTO;

/**
 * Controlador REST para generar y enviar la contrase&ntilde;a de una entidad. Este controlador es de acceso
 * p&uacute;blico y no expone informaci&oacute;n sensible.
 *
 * @author Zamir Garc&iacute;a Romero - zagarcia@ayesa.com
 * @version 1.0
 *
 */
public interface RecuperarClaveControlador {
	
	/**
	 * Busca una entidad por NIT, idCGN Identificador asignado por la Contadur&iacute;a General de la Naci&oacute;n
	 *  y correo con el fin de generar una nueva clave 
	 * @param RecuperarClaveSolicitudDTO entidad que contiene el nit N&uacute;mero de identificación tributaria
	 *  de la entidad, el idCGN Identificador asignado por la Contadur&iacute;a General de la Naci&oacute;n 
	 * @return etalles del resultado del proceso.
	 */
	ResponseEntity<FnaResponse<FnaDtoResult>> registroUsuario(RecuperarClaveSolicitudDTO solicitud);

}
