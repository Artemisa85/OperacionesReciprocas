/*
 * Copyright 2019. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.dominio.modelo;

import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representa las opciones de configuraci&oacute;n de la aplicación Operaiones rec&iacute;procas.
 *
 * @author Zamir Garc&iacute;a Romero - zagarcia@ayesa.com
 * @version 1.0
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "CONFIGURACION")
public class ConfiguracionRegistro {

	/**
     * Identificador de la opci&oacute;n
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true)
    private BigInteger id;

    /**
     * Dominio de configuraci&oacute;n
     */
    @Column(name = "DOMINIO", unique = true)
    private String dominio;

    /**
     * C&oacute;digo de la opci&oacute;n de configuraci&oacute;n
     */
    @Column(name = "CODIGO")
    private String codigo;

    /**
     * Valor de la opci&oacute;n de configuraci&oacute;n
     */
    @Column(name = "VALOR")
    private String valor;

    /**
     * Descripci&oacute;n de la opci&oacute;n de configuraci&oacute;n
     */
    @Column(name = "DESCRIPCION")
    private String descripcion;

}
