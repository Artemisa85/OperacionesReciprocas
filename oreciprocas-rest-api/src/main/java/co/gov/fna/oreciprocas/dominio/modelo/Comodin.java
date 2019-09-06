/*
 * Copyright 2019. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.dominio.modelo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representa los comodines que pueden estar en el archivo de carga de transacciones
 * 
 * @author Esteban Salinas
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "ORE_COMODIN")
public class Comodin implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -310526697987656936L;

	/**
     * Identificador comodin
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true)
    private BigInteger id;

    /**
     * Transacci&oacute;n a la que est&aacutea; asociada los comodines.
     */
    @JsonBackReference
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "ORE_TRANSACCION_ID", referencedColumnName = "ID")
    private Transaccion idTransaccion;

    /**
     * Comodin1
     */
    @Column(name = "COMODIN_1")
    private String comodin1;

    /**
     * Comodin2
     */
    @Column(name = "COMODIN_2")
    private String comodin2;

    /**
     * Comodin3
     */
    @Column(name = "COMODIN_3")
    private String comodin3;

    /**
     * Comodin4
     */
    @Column(name = "COMODIN_4")
    private String comodin4;

    /**
     * Comodin5
     */
    @Column(name = "COMODIN_5")
    private String comodin5;
}
