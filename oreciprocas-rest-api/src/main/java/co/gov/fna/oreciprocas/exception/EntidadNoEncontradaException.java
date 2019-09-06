/*
 * Copyright 2018. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.exception;

import org.springframework.http.HttpStatus;

import co.gov.fna.core.common.exception.ErrorDetail;
import co.gov.fna.core.common.exception.FnaException;
import co.gov.fna.oreciprocas.dominio.modelo.Entidad;

/**
 * Excepci&oacute;n lanzada cuando no se encuentra una entidad tipo {@link Entidad}
 *
 * @author Zamir Garc&iacute;a Romero - zagarcia@ayesa.com
 * @version 1.0
 *
 */
public class EntidadNoEncontradaException extends FnaException {

	/**
	 * @param errorDetail
	 * @param status
	 */
	public EntidadNoEncontradaException(ErrorDetail errorDetail, HttpStatus status) {
		super(errorDetail, status);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -3055335383334794871L;

}
