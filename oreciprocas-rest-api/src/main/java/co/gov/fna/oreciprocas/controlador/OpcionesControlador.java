/*
 * Copyright 2019. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.controlador;

import org.springframework.http.ResponseEntity;

import co.gov.fna.core.common.generic.rest.FnaDtoResult;
import co.gov.fna.core.common.generic.rest.FnaResponse;

/**
 * Brinda la informaci&oacute;n sobre opciones y configuraciones de la aplicaci&oacute;n.
 *
 * @author Zamir Garc&iacute;a Romero - zagarcia@ayesa.com
 * @version 1.0
 *
 */
public interface OpcionesControlador {
	
	
	/**
	 * Retorna las opciones de archivos permitidos para carga de archivos.
	 * @return
	 */
	ResponseEntity<FnaResponse<FnaDtoResult>> obtenerTipoArchivosPermitidos();
	

}
