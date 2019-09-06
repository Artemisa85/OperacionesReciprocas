/*
 * Copyright 2018. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.dominio.dto;



import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import co.gov.fna.oreciprocas.constantes.ValidacionConstantes;
import co.gov.fna.oreciprocas.dominio.modelo.Entidad;

/**
 * DTO para transferencia de informaci&oacute; de {@link Entidad}
 *	
 * @author Zamir Garc&iacute;a Romero - zagarcia@ayesa.com
 * @version 1.0
 *
 */
public class RegistroUsuarioSolicitudDTO { 

	/**
	 * N&uacute;mero de identificaci&oacute;n tributaria de la entidad.
	 */
	@NotNull
	@Size(min = 9, max = 9)
	@Digits(fraction = 0, integer = 9)
	private String nit;
	
	/**
	 * Identificador asignado a la entidad por la Contadur&iacute;a General de la Naci&oacute;n.
	 */
	@NotNull
	@Size(min = 8, max = 9)
	private String idCGN;
	
	/**
	 * Correo del usuario asignado a la entidad
	 */
	@NotNull
	@Pattern(regexp = ValidacionConstantes.EXPR_REG_EMAIL, 
		message = ValidacionConstantes.MSG_EMAIL_REQUERIDO)
	private String correo;
	
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
	 * @return el Identificador asignado a la entidad por la Contadur&iacute;a General de la Naci&oacute;n
	 */
	public String getIdCGN() {
		return idCGN;
	}

	/**
	 * @param establece el Identificador asignado a la entidad por la Contadur&iacute;a General de la Naci&oacute;n
	 */
	public void setIdCGN(String idCGN) {
		this.idCGN = idCGN;
	}

	/**
	 * @return el Correo del usuario asignado a la entidad
	 */
	public String getCorreo() {
		return correo;
	}

	/**
	 * @param establece el Correo del usuario asignado a la entidad
	 */
	public void setCorreo(String correo) {
		this.correo = correo;
	}

}
