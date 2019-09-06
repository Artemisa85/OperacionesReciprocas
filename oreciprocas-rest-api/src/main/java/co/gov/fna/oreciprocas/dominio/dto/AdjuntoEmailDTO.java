/*
 * Copyright 2019. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.dominio.dto;

import org.springframework.core.io.InputStreamSource;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Especifica los datos adjuntos en una notificaci&oacute;n. Estos datos serán ubicados en el
 * Classpath del proyecto.
 *
 * @author Zamir Garc&iacute;a Romero - zagarcia@ayesa.com
 * @version 1.0
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdjuntoEmailDTO {
	
	/**
	 * Modo adjunto normal;
	 */
	public static String MODO_NORMAL = "NORMAL";
	
	/**
	 * Modo adjunto en l&iacute;nea. 
	 */
	public static String MODO_EN_LINEA = "EN_LINEA";
	
	
	/* Atributos de instancia	 */
	
	/**
	 * Identificador del contenido
	 */
	private String id;
	
	/**
	 * Indica la ruta 
	 */
	private InputStreamSource origen;
	
	/**
	 * Establece el tipo de contenido (ContentType)
	 */
	private String tipoContenido;
	
	/**
	 * Indica el modo del adjunto (Normal, en línea).
	 */
	private String modo;

}
