/*
 * Copyright 2019. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.dominio.dto;

import java.math.BigInteger;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representa un archivo adjunto asociado a un comentario. (DTO)
 *
 * @author Zamir Garc&iacute;a Romero - zagarcia@ayesa.com
 * @version 1.0
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdjuntoDTO {
	
	/**
	 * Identificador del archivo adjunto.
	 */
	private BigInteger id;
	
	/**
	 * Nombre simple del archivo.
	 */
	private String nombre;
	
	/**
	 * Extensi&oacute;n del archivo
	 */
	private String extension;
	
	/**
	 * Tipo MIME del archivo
	 */
	private String tipoMime;
	
	/**
	 * Ubicaci&oacuten; del archivo
	 */
	private String url; 

}
