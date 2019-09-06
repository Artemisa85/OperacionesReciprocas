/*
 * Copyright 2019. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.controlador;

import org.springframework.http.ResponseEntity;

import co.gov.fna.core.common.generic.rest.FnaDtoResult;
import co.gov.fna.core.common.generic.rest.FnaResponse;
import co.gov.fna.oreciprocas.dominio.dto.CambiarClaveSolicitudDTO;

/**
 * Controlador REST para cambiar la contrase&ntilde;a de un usuario. Este controlador es de acceso
 * p&uacute;blico y no expone informaci&oacute;n sensible.
 *
 * @author Zamir Garc&iacute;a Romero - zagarcia@ayesa.com
 * @version 1.0
 *
 */
public interface CambiarClaveControlador {
	
	/**
	 * Busca una entidad por NIT y idCGN Identificador asignado por la Contadur&iacute;a General 
	 * de la Naci&oacute;n con el fin de cambiar la clave actual asignada
	 * 
	 * @param Entidad que contiene el nit N&uacute;mero de identificación tributaria
	 * 		  de la entidad, el idCGN Identificador asignado por la Contadur&iacute;a General de la
	 *        Naci&oacute;n, la contrase&ntilde;a actual y la contrase&ntilde;a antigua
	 * @return Detalles del resultado del proceso.
	 */
	ResponseEntity<FnaResponse<FnaDtoResult>> registroUsuario(CambiarClaveSolicitudDTO solicitud);

}
