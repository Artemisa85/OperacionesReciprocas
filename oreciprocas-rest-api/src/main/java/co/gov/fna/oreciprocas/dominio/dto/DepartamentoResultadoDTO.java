/*
 * Copyright 2019. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.dominio.dto;

import java.util.Collection;

import org.springframework.data.domain.Page;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import co.gov.fna.core.common.generic.rest.FnaDtoResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Resultado de una operaci&oacute;n de b&uacute;squeda de Departamentos.
 *
 * @author Zamir Garc&iacute;a Romero - zagarcia@ayesa.com
 * @version 1.0
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class DepartamentoResultadoDTO implements FnaDtoResult {
	
	/**
	 * P&aacute;gina con los resultados de departamento.
	 */
	private Page<DepartamentoDTO> departamentosPag;
	
	/**
	 * Colecci&oacute;n con los resultados de departamento.
	 */
	private Collection<DepartamentoDTO> departamentosColl;

}
