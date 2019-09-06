/*
 * Copyright 2019. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.dominio.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Contiene la informaci&oacute; de una operaci&oacute;n
 *
 * @author Zamir Garc&iacute;a Romero - zagarcia@ayesa.com
 * @version 1.0
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OperacionDTO {
	
	/**
	 * Identificador de la entidad (Id CGN)
	 */
	private String idEntidad;
	
	/**
	 * Nombre de la entidad.
	 */
	private String nombreEntidad;
	
	/**
	 * Valor reportado de liquidez
	 */
	private Integer liquidez;
	
	/**
	 * Valor reportado en c&oacute;digo cuenta.
	 */
	private String codigoCuenta;
	
	/**
	 * Valor reportado en Cuenta
	 */
	private String descripcionCuenta;
	
	/**
	 * Valor reportado en "valor reportado"
	 */
	private BigDecimal valorReportado;

	/**
	 * Valor reportado en "Partida conciliatoria"
	 */
	private BigDecimal partidaConciliatoria;
	
	/**
	 * Valor reportado en "Origen diferencia"
	 */
	private String origenDiferencia;
	
	/**
	 * Valor analista
	 */
	private String analistaCategoria;

}
