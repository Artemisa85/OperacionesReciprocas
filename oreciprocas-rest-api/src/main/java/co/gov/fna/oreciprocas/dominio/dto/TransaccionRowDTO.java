/*
 * Copyright 2019. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.dominio.dto;

import java.math.BigDecimal;
import java.math.BigInteger;

import co.gov.fna.oreciprocas.dominio.enumeracion.EstadoTransaccion;
import co.gov.fna.oreciprocas.dominio.enumeracion.Periodo;
import lombok.Data;

/**
 * Entidad que representa la información requerida de una transacción.
 *
 * @author Zamir Garc&iacute;a Romero - zagarcia@ayesa.com
 * @version 1.0
 *
 */
@Data
public class TransaccionRowDTO {
	

	/**
	 * @param idTransaccion
	 * @param entidadesInvolucradas
	 * @param codigoEntidad
	 */
	public TransaccionRowDTO(BigInteger idTransaccion, String entidadesInvolucradas) {
		super();
		this.idTransaccion = idTransaccion;
		this.entidadesInvolucradas = entidadesInvolucradas;
	}
	
	public TransaccionRowDTO(BigInteger idTransaccion, String entidadesInvolucradas, String codigoEntidad,
			String nombreEntidad, Integer liquidez, String codCuenta, String descCuenta, BigDecimal valorReportado,
			BigDecimal partidaConciliatoria, EstadoTransaccion estado, Periodo periodo) {
		super();
		this.idTransaccion = idTransaccion;
		this.entidadesInvolucradas = entidadesInvolucradas;
		this.codigoEntidad = codigoEntidad;
		this.nombreEntidad = nombreEntidad;
		this.liquidez = liquidez;
		this.codCuenta = codCuenta;
		this.descCuenta = descCuenta;
		this.valorReportado = valorReportado;
		this.partidaConciliatoria = partidaConciliatoria;
		this.codEstado = estado.getCodigo();
		this.descEstado = estado.getDescripcion();
		this.codPeriodo = periodo.getCodigo();
		this.descPeriodo = periodo.getDescripcion();
	}


	private BigInteger idTransaccion;
	
	private String entidadesInvolucradas;
	
	private String codigoEntidad;
	
	private String nombreEntidad;
	
	private Integer liquidez;
	
	private String codCuenta;
	
	private String descCuenta;
	
	private BigDecimal valorReportado;
	
	private BigDecimal partidaConciliatoria;
	
	private String codEstado;
	
	private String descEstado;
	
	private Integer codPeriodo;
	
	private String descPeriodo;
	
	

}
