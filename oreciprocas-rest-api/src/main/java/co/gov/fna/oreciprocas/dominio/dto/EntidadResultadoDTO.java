/*
 * Copyright 2019. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.dominio.dto;

import org.springframework.data.domain.Page;

import co.gov.fna.core.common.generic.rest.FnaDtoResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Contiene el resultado paginado de una consulta de entidades.
 *
 * @author Zamir Garc&iacute;a Romero - zagarcia@ayesa.com
 * @version 1.0
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EntidadResultadoDTO implements FnaDtoResult {
	
	/**
	 * Mensaje de resultado.
	 */
	private String mensaje;
	
	/**
	 * Entidades resultado.
	 */
	private Page<EntidadDTO> entidades;
	
}
