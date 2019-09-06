/*
 * Copyright 2019. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.dominio.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * Definici&oacute; de los periodos
 *
 * @author Zamir Garc&iacute;a Romero - zagarcia@ayesa.com
 * @version 1.0
 *
 */
@Builder
@Data
@AllArgsConstructor
public class PeriodoDTO {
	
	/**
	 * C&oacute;digo de periodo
	 */
	private Integer codigo;
	
	/**
	 * Descripci&oacute;n de periodo.
	 */
	private String descripcion;
}