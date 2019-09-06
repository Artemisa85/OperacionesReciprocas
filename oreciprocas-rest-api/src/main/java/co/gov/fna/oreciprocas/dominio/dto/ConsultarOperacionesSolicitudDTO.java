/*
 * Copyright 2019. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.dominio.dto;

import java.math.BigInteger;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Objeto que contiene los atributos para realizar la consulta de las operaciones
 * rec&iacute;procas disponibles para el usuario. 
 *
 * @author Zamir Garc&iacute;a Romero - zagarcia@ayesa.com
 * @version 1.0
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConsultarOperacionesSolicitudDTO {
	
	/**
	 * Periodo de corte de informaci&oacute;n:
	 * <br/>1. Enero - Marzo
	 * <br/>2. Abril - Junio 
	 * <br/>3. Julio - Septiembre
	 * <br/>4. Octubre - Diciembre 
	 */
	private Integer periodo;
	
	/**
	 * Año del Periodo de información
	 */
	private Integer annio;
	
	/**
	 * Fecha de consolidaci&oacute;n del archivo.
	 */
	@JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	private Date fechaConsolidacion;
	
	/**
	 * C&oacute;digo del estado de la operaci&oacute;n.
	 */
	private String codEstado;
	
	/**
	 * Identificador de la transacci&oacute;n
	 */
	private BigInteger idTransaccion;
	
	/**
	 * Identificador de la entidad.
	 */
	private String idEntidad;
	
	/**
	 * Campo para b&uacute;queda por entidades involucradas.
	 */
	private String entidadesInv; 
	
}
