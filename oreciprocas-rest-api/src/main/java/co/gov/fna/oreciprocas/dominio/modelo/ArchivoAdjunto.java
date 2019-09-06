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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representa un archivo adjunto asociado a un comentario.
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
@Table(name ="ORE_ADJUNTO")
public class ArchivoAdjunto {
	
	/**
	 * Identificador del archivo adjunto.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private BigInteger id;
	
	/**
	 * Nombre simple del archivo.
	 */
	@Column(name = "NOMBRE")
	private String nombre;
	
	/**
	 * Extensi&oacute;n del archivo
	 */
	@Column(name = "EXTENSION")
	private String extension;
	
	/**
	 * Tipo MIME del archivo
	 */
	@Column(name = "TIPO_MIME")
	private String tipoMime;
	
	/**
	 * Ubicaci&oacuten; del archivo
	 */
	@Column(name = "UBICACION")
	private String ubicacion;
	
	/**
	 * URL donde se puede descargar el archivo.
	 */
	@Transient
	private String url;
	
	/**
	 * Comentario al que pertenece el adjunto.
	 */
	@ManyToOne
	@JoinColumn(name = "ORE_COMENTARIO_ID", referencedColumnName = "ID")
	private Comentario comentario;

}
