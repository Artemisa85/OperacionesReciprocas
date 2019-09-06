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
 * Contiene la informaci&oacute;n de respuesta de las transacciones.
 *
 * @author Zamir Garc&iacute;a Romero - zagarcia@ayesa.com
 * @version 1.0
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConsultarOperacionesRespuestaDTO implements FnaDtoResult {
	
	private String mensaje;
	
	private Page<TransaccionRowDTO> transacciones;
	
	

}
