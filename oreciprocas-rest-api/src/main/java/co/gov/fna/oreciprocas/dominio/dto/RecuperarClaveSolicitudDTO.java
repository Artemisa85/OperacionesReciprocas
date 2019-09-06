/*
 * Copyright 2018. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.dominio.dto;

import co.gov.fna.oreciprocas.dominio.modelo.Entidad;

/**
 * DTO para transferencia de informaci&oacute; de {@link Entidad}
 *	
 * @author Zamir Garc&iacute;a Romero - zagarcia@ayesa.com
 * @version 1.0
 *
 */
public class RecuperarClaveSolicitudDTO {
	
	/**
	 * N&uacute;mero de identificaci&oacute;n tributaria de la entidad.
	 */
//	@NotNull
//	@NotBlank
	private String nit;
	
	/**
	 * Identificador asignado a la entidad por la Contadur&iacute;a General de la Naci&oacute;n.
	 */
//	@NotNull
//	@NotBlank
	private String idCGN;

	/**
	 * @return el N&uacute;mero de identificaci&oacute;n tributaria de la entidad
	 */
	public String getNit() {
		return nit;
	}

	/**
	 * @param establece el N&uacute;mero de identificaci&oacute;n tributaria de la entidad
	 */
	public void setNit(String nit) {
		this.nit = nit;
	}

	/**
	 * @return el Identificador asignado a la entidad por la Contadur&iacute;a General de la Naci&oacute;n.
	 */
	public String getIdCGN() {
		return idCGN;
	}

	/**
	 * @param establece el Identificador asignado a la entidad por la Contadur&iacute;a General de la Naci&oacute;n.
	 */
	public void setIdCGN(String idCGN) {
		this.idCGN = idCGN;
	}
	
}
