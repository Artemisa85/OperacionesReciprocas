/*
 * Copyright 2018. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.dominio.dto;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * DTO que contiene la informaci&oacute;n de solicitud de cambio de clave.
 *	
 * @author Zamir Garc&iacute;a Romero - zagarcia@ayesa.com
 * @version 1.0
 *
 */
public class CambiarClaveSolicitudDTO {
	
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
	 *  Contrase&ntilde;a actual del usuario
	 */
	@NotNull
	private String contrasenaActual;
	
	/**
	 *  Contrase&ntilde;a nueva del usuario
	 */
	@NotNull
	@Size(min = 8)
	private String contrasenaNueva;
	
	/**
	 *  Confirmaci&acute;n de contrase&ntilde;a nueva del usuario
	 */
	@NotNull
	@Size(min = 8)
	private String confirmacionContrasena;

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

	/**
	 * @return la Contrase&ntilde;a actual del usuario
	 */
	public String getContrasenaActual() {
		return contrasenaActual;
	}

	/**
	 * @param establece Contrase&ntilde;a actual del usuario
	*/
	public void setContrasenaActual(String contrasenaActual) {
		this.contrasenaActual = contrasenaActual;
	}

	/**
	 * @return la Contrase&ntilde;a nueva del usuario
	 */
	public String getContrasenaNueva() {
		return contrasenaNueva;
	}

	/**
	 * @param establece la Contrase&ntilde;a nueva del usuario
	 */
	public void setContrasenaNueva(String contrasenaNueva) {
		this.contrasenaNueva = contrasenaNueva;
	}

	/**
	 * Retorna confirmacionContrasena
	 * @return the confirmacionContrasena
	 */
	public String getConfirmacionContrasena() {
		return confirmacionContrasena;
	}

	/**
	 * Establece el valor para confirmacionContrasena
	 * @param confirmacionContrasena the confirmacionContrasena to set
	 */
	public void setConfirmacionContrasena(String confirmacionContrasena) {
		this.confirmacionContrasena = confirmacionContrasena;
	}
	
}
