/*
 * Copyright 2019. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.servicio;

import co.gov.fna.oreciprocas.dominio.dto.ValidacionContrasenaDTO;

/**
 * Valida que una contrase&ntilde;a cumpla con la pol&iacute;tica de seguridad
 * del FNA.
 *
 * @author Zamir Garc&iacute;a Romero - zagarcia@ayesa.com
 * @version 1.0
 *
 */
public interface ValidacionContrasenaServicio {
	
	/**.
	 * Valida si una contrase&ntilde;a cumple con la pol&iacute;tica de seguridad.
	 * 
	 * @param clave Cadena que se va a validar.
	 */
	void validar(ValidacionContrasenaDTO contrasena);

}
