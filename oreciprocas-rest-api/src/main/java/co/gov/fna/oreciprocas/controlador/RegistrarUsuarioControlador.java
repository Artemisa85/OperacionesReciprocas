/*
 * Copyright 2019. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.controlador;

import org.springframework.http.ResponseEntity;

import co.gov.fna.core.common.generic.rest.FnaDtoResult;
import co.gov.fna.core.common.generic.rest.FnaResponse;
import co.gov.fna.oreciprocas.dominio.dto.RegistroUsuarioSolicitudDTO;

/**
 * Controlador REST para registrar un usuario externo con una entidad. Este controlador es de acceso
 * p&uacute;blico y no expone informaci&oacute;n sensible.
 *
 * @author Zamir Garc&iacute;a Romero - zagarcia@ayesa.com
 * @version 1.0
 *
 */
public interface RegistrarUsuarioControlador {
	
	/**
	 * Crea y relaciona un usuario externo con una entidad.
	 * 
	 * @param entrada RegistroUsuarioEntradaDTO contiene nit N&uacute;mero de identificación tributaria
	 * y idCGN Identificador asignado por la Contadur&iacute;a General de la Naci&oacute;n.
	 * @return Detalles del resultado del proceso.
	 */
	ResponseEntity<FnaResponse<FnaDtoResult>> registroUsuario(RegistroUsuarioSolicitudDTO entrada);

}
