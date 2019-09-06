/*
 * Copyright 2019. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.dominio.modelo;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

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
 * Representa las plantillas almacenadas en el sistema. Estas plantillas pueden
 * ser para env&iacute;o de correo o para otros prop&oacute;sitos.
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
@Table(name = "ADM_PLANTILLA")
public class Plantilla implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = -2960043250423965169L;

	/**
     * Identificador num&eacute;ro
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true)
    private BigInteger id;

    /**
     * Nombre de la plantilla (&Uacute;nico).
     */
    @Column(name = "NOMBRE", unique = true)
    private String nombre;

    /**
     * Contenido de la plantilla.
     */
    @Column(name = "CONTENIDO")
    private String contenido;

    /**
     * Modo o tipo de plantilla: HTML, TEXT, etc...
     */
    @Column(name = "MODO_PLANTILLA")
    private String modoPlantilla;

    /**
     * Juego de caracteres compatible con la plantilla. Eg. UTF-8
     */
    @Column(name = "CHARSET")
    private String juegoCaracteres;

    /**
     * Fecha de creaci&oacute;n de la plantilla
     */
    @Column(name = "FECHA_CREACION")
    private Date fechaCreacion;

    /**
     * Fecha de modficaci&oacute;n de la plantilla
     */
    @Column(name = "FECHA_MODIFICACION")
    private Date fechaModificacion;

    /**
     * Usuario creador del registro
     */
    @Column(name = "USUARIO_CREADOR")
    private String creador;

    /**
     * Usuario que modifica el registro
     */
    @Column(name = "USUARIO_EDITOR")
    private String editor;

    @Column(name = "ACTIVO")
    private Boolean activo;

}
