/*
 * Copyright 2019. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.dominio.dto;

import java.util.Collection;

import co.gov.fna.core.common.generic.rest.FnaDtoResult;
import lombok.Builder;
import lombok.Data;

/**
 * Encapula una serie de comentarios como resultado de una consulta.
 *
 * @author Zamir Garc&iacute;a Romero - zagarcia@ayesa.com
 * @version 1.0
 *
 */
@Data
@Builder
public class ComentarioResultDTO implements FnaDtoResult {
	
	/**
	 * Comentarios asociados con el resultado.
	 */
	private Collection<ComentarioDTO> comentarios;

}
