/*
 * Copyright 2019. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.dominio.dto;

import java.math.BigInteger;
import java.util.Collection;

import co.gov.fna.core.common.generic.rest.FnaDtoResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representa el detalle de una transacci&oacute;n
 *
 * @author Zamir Garc&iacute;a Romero - zagarcia@ayesa.com
 * @version 1.0
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransaccionDetalleDTO implements FnaDtoResult {
	
	/**
	 * Identificador de la transacci&oacute;n
	 */
	private BigInteger idTransaccion;
	
	/**
	 * Entidades involucradas en la transacci&oacute;n
	 */
	private Collection<EntidadInfoBasicaDTO> entidadesInvolucradas;
	
	/**
	 * Informaci&oacute;n sobre el contador de la entidad rec&iacute;proca.
	 */
	private String contactoContador;
	
	/**
	 * Estado de la transaccion.
	 */
	private EstadoDTO estado;
	
	/**
	 * Fecha de consolidaci&oacute;n del archivo en que la transacci&oacute;n
	 * fue reportada.
	 */
	private String fechaConsolidacion;
	
	/**
	 * Fecha en la que se carg&oacute; el archivo
	 */
	private String fechaCargue;
	
	/**
	 * Conjunto de operaciones asociadas a la CGN.
	 */
	private Collection<OperacionDTO> operacionesCGN;
	
	/**
	 * Conjunto de operaciones asociadas al FNA.
	 */
	private Collection<OperacionDTO> operacionesFNA;
	
	/**
	 * Conjunto de operaciones asociadas a terceros.
	 */
	private Collection<OperacionDTO> operacionesOtros;
	
	/**
	 * Valor del campo comod&iacute;n 1
	 */
	private String comodin1;
	
	/**
	 * Valor del campo comod&iacute;n 2
	 */
	private String comodin2;
	
	/**
	 * Valor del campo comod&iacute;n 3
	 */
	private String comodin3;
	
	/**
	 * Valor del campo comod&iacute;n 4
	 */
	private String comodin4;
	
	/**
	 * Valor del campo comod&iacute;n 5
	 */
	private String comodin5;
	

}
