/*
 * Copyright 2018. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.dominio.dto;

import co.gov.fna.core.common.generic.rest.FnaDtoResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para transferencia de informaci&oacute; con respecto a la repuesta del proceso de registro
 * de un usuario.
 *	
 * @author Zamir Garc&iacute;a Romero - zagarcia@ayesa.com
 * @version 1.0
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistroUsuarioRespuestaDTO implements FnaDtoResult {

	/**
	 * Usuario registrado a la entidad.
	 */
	private String usuario;
	
	/**
	 * Mensaje de respuesta para el registro de usuarios.
	 */
	private String mensajeRespuesta;

	
}
