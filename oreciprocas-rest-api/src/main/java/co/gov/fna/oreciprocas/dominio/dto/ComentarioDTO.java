/*
 * Copyright 2019. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.dominio.dto;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import co.gov.fna.core.common.generic.rest.FnaDtoResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representa un comentario DTO
 *
 * @author Zamir Garc&iacute;a Romero - zagarcia@ayesa.com
 * @version 1.0
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComentarioDTO implements FnaDtoResult {
	
	/**
	 * Identificador del comentario
	 */
	private BigInteger id;
	
	/**
	 * Texto del comentario
	 */
	private String texto;
	
	/**.
	 * Autor del comentario
	 */
	private String autor;
	
	/**
	 * Nombre del rol asociado al autor
	 */
	private String autorRol;
	
	/**
	 * Fecha del comentario.
	 */
	@JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	private Date fecha;
	
	/**
	 * Identificador de la transaccion.
	 */
	private BigInteger idTransaccion;
	
	/**
	 * C&oacute;digo estado de transacci&oacute;n.
	 */
	private String codEstadoTransaccion;
	
	/**
	 * Descricpci&oacute;n estado de transacci&oacute;n.
	 */
	private String descEstadoTransaccion;
	
	/**
	 * Archivos adjuntos asociados al usuario
	 */
	private Collection<AdjuntoDTO> adjuntos;
	

}
