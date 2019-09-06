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
 * Resultado de una operaci&oacute;n de b&uacute;squeda de municipios.
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
public class MunicipioResultadoDTO implements FnaDtoResult {
	
	/**
	 * P&aacute;gina con los municipios encontrados
	 */
	private Page<MunicipioDTO> municipiosPag;
	
	/**
	 * Collecci&oacute;n de municipios encontrados.
	 */
	private Collection<MunicipioDTO> municipiosColl;

}
