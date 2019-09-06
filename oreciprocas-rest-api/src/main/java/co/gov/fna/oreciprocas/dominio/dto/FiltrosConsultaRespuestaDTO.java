/*
 * Copyright 2019. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.dominio.dto;

import java.util.Collection;

import co.gov.fna.core.common.generic.rest.FnaDtoResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Descripcion del tipo, no use caracteres especiales.
 *
 * @author Zamir Garc&iacute;a Romero - zagarcia@ayesa.com
 * @version 1.0
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FiltrosConsultaRespuestaDTO implements FnaDtoResult {
	
	/**
	 * Lista de periodos disponibles
	 */
	private Collection<PeriodoDTO> periodos;
	
	/**
	 * Lista de a&ntilde;os disponibles.
	 */
	private Collection<String> annios;
	
	/**
	 * Lista de fechas de consolidaci&oacute;n de archivos.
	 */
	private Collection<String> fechasConsolidacion; 
	
	/**
	 * Lista de estados disponible.
	 */
	private Collection<EstadoDTO> estados;
	
}
