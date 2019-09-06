/*
 * Copyright 2019. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.dominio.dto;

import co.gov.fna.core.common.generic.rest.FnaDtoResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representa un tipo de archivo permitido para cargar en el sistema.
 *
 * @author Zamir Garc&iacute;a Romero - zagarcia@ayesa.com
 * @version 1.0
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TipoArchivoPermitido implements FnaDtoResult {
	
	/**
	 * Extensi&oaucte;n del archivo
	 */
	private String extension;
	
	/**
	 * Tipo mime del archivo.
	 */
	private String tipoMime;

}
