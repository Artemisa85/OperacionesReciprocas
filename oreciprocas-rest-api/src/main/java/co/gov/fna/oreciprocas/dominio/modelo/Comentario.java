/*
 * Copyright 2019. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.dominio.modelo;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representa los comentarios realizados en la gesti&oacute;n de las transacciones
 * reportadas para conciliar.
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
@Table(name = "ORE_COMENTARIO")
public class Comentario implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Identificador del comentario
	 */
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private BigInteger id;
	
	/**
	 * Texto del comentario.
	 */
	@Column(name = "TEXTO")
	private String texto;
	
	/**
	 * Id usuario autor del comentario.
	 */
	@Column(name = "ID_AUTOR")
	private String idAutor;
	
	/**
	 * Nombre del autor del comentario.
	 */
	@Column(name = "AUTOR")
	private String autor;
	
	/**
	 * Rol del autor del comentario
	 */
	@Column(name = "AUTOR_ROL")
	private String autorRol;
	
	/**
	 * Fecha del comentario.
	 */
	@Column(name = "FECHA")
	private Date fecha;
	
	/**
	 * Identificador de la transacci&oacute;n.
	 */
	@Column(name = "ORE_TRANSACCION_ID")
	private BigInteger idTransaccion;
	
	/**
	 * Archivos adjuntos al comentario.
	 */
	@OneToMany(mappedBy = "comentario", targetEntity = ArchivoAdjunto.class)
	private Collection<ArchivoAdjunto> adjuntos;

}
