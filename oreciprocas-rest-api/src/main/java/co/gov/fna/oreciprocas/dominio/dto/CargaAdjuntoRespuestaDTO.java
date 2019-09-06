/*
 * Copyright 2019. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.dominio.dto;

import java.util.List;

import co.gov.fna.core.common.generic.rest.FnaDtoResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Contiene los detalles del proceso de carga de adjuntos.
 *
 * @author Zamir Garc&iacute;a Romero - zagarcia@ayesa.com
 * @version 1.0
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CargaAdjuntoRespuestaDTO implements FnaDtoResult {
	
	/**
	 * Mensaje de salida del proceso.
	 */
	private String mensaje;
	
	/**
	 * Nombres de archivos cargados.
	 */
	private List<AdjuntoDTO> archivos;

}
