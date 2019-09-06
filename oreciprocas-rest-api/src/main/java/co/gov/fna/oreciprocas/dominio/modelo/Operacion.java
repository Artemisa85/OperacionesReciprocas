/*
 * Copyright 2019. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.dominio.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representa las operaciones asociadas a las transacciones reportadas.
 *
 * @author Zamir Garc&iacute;a Romero - zagarcia@ayesa.com
 * @version 1.0
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "ORE_OPERACION")
public class Operacion implements Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 5514696765962794683L;

	/**
     * Identificador operacion
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true)
    private BigInteger id;

    /**
     * Transacci&oacute;n a la que est&aacutea; asociada la operaci&oacute;n.
     */
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "ORE_TRANSACCION_ID", referencedColumnName = "ID")
    private Transaccion idTransaccion;

    /**
     * C&oacute;digo CGN asociado a la entidad que en el registro de
     * operaci&oacute;n
     */
    @Column(name = "ID_CGN_ENTIDAD")
    private String idCgn;

    /**
     * Nombre entidad
     */
    @Column(name = "NOMBRE_ENTIDAD")
    private String nombreEntidad;

    /**
     * Valor reportado de liquidez
     */
    @Column(name = "LIQUIDEZ")
    private Integer liquidez;

    /**
     * Valor reportado en c&oacute;digo cuenta.
     */
    @Column(name = "COD_CUENTA")
    private String codigoCuenta;

    /**
     * Valor reportado en Cuenta
     */
    @Column(name = "DESC_CUENTA")
    private String descripcionCuenta;

    /**
     * Valor reportado en "valor reportado"
     */
    @Column(name = "VALOR_REPORTADO")
    private BigDecimal valorReportado;

    /**
     * Valor reportado en "Partida conciliatoria"
     */
    @Column(name = "PARTIDA_CONCILIATORIA")
    private BigDecimal partidaConciliatoria;

    /**
     * Valor reportado en "Origen diferencia"
     */
    @Column(name = "ORIGEN_DIFERENCIA")
    private String origenDiferencia;

    /**
     * Valor reportado en Cuenta
     */
    @Column(name = "ANALISTA")
    private String analistaCategoria;

}
