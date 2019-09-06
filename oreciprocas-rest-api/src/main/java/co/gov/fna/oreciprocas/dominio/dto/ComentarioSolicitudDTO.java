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
 * Objeto con la inforrmaci&oacute;n de solicitud de creaci&oacute;n/modificaci&oacute;n 
 * de un comentario.
 *
 * @author Zamir Garc&iacute;a Romero - zagarcia@ayesa.com
 * @version 1.0
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComentarioSolicitudDTO {
	
	/**
	 * Identificador de la transacci&oacute;n a la que se asocia el comentario
	 */
	private BigInteger idTransaccion;
	
	/**
	 * Texto del comentario
	 */
	private String texto;
	
	/**
	 * C&oacute;digo del estado de la transacci&oacute;n
	 */
	private String codEstado;
	
	/**
	 * Indica si se est&acute; cambiando manualmente el estado.
	 */
	private boolean cambioManual;
	
	/**
	 * Nombre del autor del comentario.
	 */
	private String nombreAutor;

}
