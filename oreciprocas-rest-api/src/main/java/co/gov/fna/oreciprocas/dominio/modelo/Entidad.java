/*
 * Copyright 2018. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.dominio.modelo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representa las entidades externas.
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
@Table(name = "ADM_ENTIDAD")
public class Entidad {
	
	/**
	 * Identificador de la entidad en el sistema Operaciones Rec&iacute;procas.
	 */
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	
	/**
	 * N&uacute;mero de identificaci&oacute;n tributaria de la entidad.
	 */
	@Column(name = "NIT")
	private String nit;
	
	
	/**
	 * Identificador asignado a la entidad por la Contadur&iacute;a General de la Naci&oacute;n.
	 */
	@Column(name = "ID_CGN")
	private String idCGN;
	
	
	/**
	 * Raz&oacute;n social de la entidad.
	 */
	@Column(name = "RAZON_SOCIAL")
	private String razonSocial;
	
	
	/**
	 * Sigla de la entidad.
	 */
	@Column(name = "SIGLA")
	private String sigla;
	
	
	/**
	 * Direcci&oacute;n de la entidad
	 */
	@Column(name = "DIRECCION")
	private String direccion;
	
	
	/**
	 * Tel&eacute;fonos de contacto de la entidad.
	 */
	@Column(name = "TELEFONO")
	private String telefono;
	
	
	/**
	 * Nombres del contacto
	 */
	@Column(name = "NOMBRE_CONTACTO")
	private String nombreContacto;
	
	
	/**
	 * Apellidos del contacto
	 */
	@Column(name = "APELLIDO_CONTACTO")
	private String apellidoContacto;
	
	
	/**
	 * Direcci&oacute;n de correo registrada
	 */
	@Column(name = "CORREO")
	private String correo;
	

	/**
	 * Sitio WEB de la entidad.
	 */
	@Column(name = "SITIO_WEB")
	private String sitioWEB;

	
	/**
	 * Fecha de creaci&oacute;n de la entidad en el sistema.
	 */
	@Column(name = "FECHA_CREACION")
	private Date fechaCreacion;
	
	/**
	 * Fecha de ultima actualizaci&oacute;n de la informaci&oacute;n de la entidad.
	 */
	@Column(name = "FECHA_MODIFICACION")
	private Date fechaModificacion;

	/**
	 * Usuario que cre&oacute; la entidad en el sistema.
	 */
	@Column(name = "USUARIO_CREADOR")
	private String usuarioCreador;
	
	/**
	 * Usuario que modific&oacute; la informaci&oacute;n de la entidad.
	 */
	@Column(name = "USUARIO_EDITOR")
	private String usuarioEditor;
	
	/**
	 * Indica si la entidad est&aacute; activa en el sistema.
	 */
	@Column(name = "ACTIVO")
	private boolean activo;
	
	/**
	 * Municipio donde est&aacute; ubicada la entidad
	 */
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ADM_MUNICIPIO_ID", referencedColumnName = "ID")
	private Municipio municipio;

	/**
	 * Usuario que administra la informaci&oacute;n de la entidad.
	 */
	@OneToOne(mappedBy = "entidad")
	private UsuarioExterno usuario;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idCGN == null) ? 0 : idCGN.hashCode());
		return result;
	}
	

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Entidad other = (Entidad) obj;
		if (idCGN == null) {
			if (other.idCGN != null)
				return false;
		} else if (!idCGN.equals(other.idCGN))
			return false;
		return true;
	}
	

	public String toString() {
		return "Entidad [id=" + id + ", nit=" + nit + ", idCGN=" + idCGN 
				+ ", razonSocial=" + razonSocial + "]";
	}
	
	
	
}
