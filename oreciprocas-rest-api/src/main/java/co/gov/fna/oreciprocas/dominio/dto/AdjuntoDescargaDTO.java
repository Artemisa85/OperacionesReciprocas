/*
 * Copyright 2019. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.dominio.dto;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Contiene la informaci&oacute;n de descarga de un adjunto asociado a un comentario.
 *
 * @author Zamir Garc&iacute;a Romero - zagarcia@ayesa.com
 * @version 1.0
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdjuntoDescargaDTO {

	/**
	 * Tipo del contenido del archivo (MimeType)
	 */
	private String tipoMime;
	
	/**
	 * Encabezados HTTP de la respuesta.
	 */
	private HttpHeaders encabezadosHttp;
	
	/**
	 * Archivo a desacargar
	 */
	private Resource archivo;
}
