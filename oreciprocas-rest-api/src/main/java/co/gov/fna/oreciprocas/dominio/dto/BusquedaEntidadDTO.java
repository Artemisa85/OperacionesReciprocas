/*
 * Copyright 2019. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.dominio.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Criterios de b&uacute;squeda de entidades;
 *
 * @author Zamir Garc&iacute;a Romero - zagarcia@ayesa.com
 * @version 1.0
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BusquedaEntidadDTO {

	/**
	 * NIT de la entidad.
	 */
	private String nit;
	
	/**
	 * Id CGN de la entidad.
	 */
	private String idCgn;
	
	/**
	 * Raz&oacute;n social de la entidad.
	 */
	private String razonSocial;
	
}
