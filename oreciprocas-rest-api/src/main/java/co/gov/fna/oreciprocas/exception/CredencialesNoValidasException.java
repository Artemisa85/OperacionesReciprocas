/*
 * Copyright 2019. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.exception;

import org.springframework.http.HttpStatus;

import co.gov.fna.core.common.exception.ErrorDetail;
import co.gov.fna.core.common.exception.FnaException;

/**
 * Excepci&oacute;n lanzada cuando no coinciden las credenciales de autenticaci&oacute;n. 
 *
 * @author Zamir Garc&iacute;a Romero - zagarcia@ayesa.com
 * @version 1.0
 *
 */
public class CredencialesNoValidasException extends FnaException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -265099528818381076L;
	
	/**
	 * @param errorDetail
	 * @param status
	 */
	public CredencialesNoValidasException(ErrorDetail errorDetail, HttpStatus status) {
		super(errorDetail, status);
	}

	

}
