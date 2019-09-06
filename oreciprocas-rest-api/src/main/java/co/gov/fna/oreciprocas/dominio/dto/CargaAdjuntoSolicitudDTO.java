/*
 * Copyright 2019. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.dominio.dto;

import java.math.BigInteger;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Contiene los datos de solicitud para guardar adjuntos de un comentario.
 *
 * @author Zamir Garc&iacute;a Romero - zagarcia@ayesa.com
 * @version 1.0
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CargaAdjuntoSolicitudDTO {
	
	/**
	 * Identificador del comentario al que se le asociar&aacute;n los adjuntos.
	 */
	private BigInteger idComentario;
	
	/**
	 * Archivos adjuntos del comentario.
	 */
	private MultipartFile[] archivos;
	
	/**
	 * Contexto que fue invocado en el servidor. Ayuda a construir la URL de los archivos.
	 */
	private String contextoSolicitado;
	

}
