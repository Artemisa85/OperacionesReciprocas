/*
 * Copyright 2019. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.dominio.dto;

import co.gov.fna.oreciprocas.dominio.modelo.UsuarioExterno;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * Representa los datos de una contrase&ntilde;a con el fin de ser validada.
 *
 * @author Zamir Garc&iacute;a Romero - zagarcia@ayesa.com
 * @version 1.0
 *
 */

@Data
@Builder
@AllArgsConstructor
public class ValidacionContrasenaDTO {
	
	/**
	 * Contrase&ntilde;a actual suministrada en la solicitud.
	 */
	private String contrasenaActual;
	
	/**
	 * Contrase&ntilde;a nueva suministrada en la solicitud.
	 */
	private String contrasenaNueva;
	
	/**
	 *  Confirmaci&acute;n de contrase&ntilde;a nueva en la solicitud.
	 */
	private String confirmacionContrasena;
	
	/**
	 * Nombre (alias) del usuario.
	 */
	private UsuarioExterno usuario;
	

}
