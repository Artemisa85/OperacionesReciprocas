/*
 * Copyright 2019. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.dominio.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Informaci&oacute;n b&aacute;sica de una entidad
 *
 * @author Zamir Garc&iacute;a Romero - zagarcia@ayesa.com
 * @version 1.0
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EntidadInfoBasicaDTO {
	
	/**
	 * Identificador de la entidad (asignado por la CGN)
	 */
	private String id;
	
	/**
	 * Nombre de la entidad
	 */
	private String nombre;

}
