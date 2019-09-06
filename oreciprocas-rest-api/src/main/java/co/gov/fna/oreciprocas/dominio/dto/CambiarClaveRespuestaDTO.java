/*
 * Copyright 2018. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.dominio.dto;

import co.gov.fna.core.common.generic.rest.FnaDtoResult;
import co.gov.fna.oreciprocas.dominio.modelo.Entidad;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para transferencia de informaci&oacute; de {@link Entidad}
 *	
 * @author Zamir Garc&iacute;a Romero - zagarcia@ayesa.com
 * @version 1.0
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CambiarClaveRespuestaDTO implements FnaDtoResult {
	

	/**
	 * Mensaje al cambiar la clave
	 */
	private String mensaje;

}
