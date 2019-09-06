/*
 * Copyright 2018. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.dominio.dto;

import java.util.Date;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import co.gov.fna.core.common.generic.rest.FnaDtoResult;
import co.gov.fna.oreciprocas.constantes.ValidacionConstantes;
import co.gov.fna.oreciprocas.dominio.modelo.Entidad;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * DTO para transferencia de informaci&oacute; de {@link Entidad}
 *	
 * @author Zamir Garc&iacute;a Romero - zagarcia@ayesa.com
 * @version 1.0
 *
 */
@Data
@AllArgsConstructor
@Builder
public class EntidadDTO implements FnaDtoResult {
	
	/**
	 * N&uacute;mero de identificaci&oacute;n tributaria de la entidad.
	 */
	@NotNull
	@Size(min = 9, max = 9)
	@Digits(fraction = 0, integer = 9)
	private String nit;
	
	
	/**
	 * Identificador asignado a la entidad por la Contadur&iacute;a General de la Naci&oacute;n.
	 */
	@NotNull
	@Size(min = 8, max = 9)
	private String idCGN;
	
	
	/**
	 * Raz&oacute;n social de la entidad.
	 */
	@NotNull
	@Size(min = 1, message = "Campo requerido: Razón social")
	private String razonSocial;
	
	
	/**
	 * Sigla de la entidad.
	 */
	private String sigla;
	
	
	/**
	 * Direcci&oacute;n de la entidad
	 */
	@NotNull
	@Size(min = 1, message = "Campo requerido: Dirección")
	private String direccion;
	
	
	/**
	 * Tel&eacute;fonos de contacto de la entidad.
	 */
	@NotNull
	@Size(min = 1, message = "Campo requerido: Teléfono")
	private String telefono;
	
	
	/**
	 * Nombres del contacto
	 */
	@NotNull
	@Size(min = 1, message = "Campo requerido: Nombre contacto")
	private String nombreContacto;
	
	
	/**
	 * Apellidos del contacto
	 */
	@NotNull
	@Size(min = 1, message = "Campo requerido: Apellido contacto")
	private String apellidoContacto;
	
	
	/**
	 * Direcci&oacute;n de correo registrada
	 */
	@NotNull
	@Pattern(regexp = ValidacionConstantes.EXPR_REG_EMAIL
		, message = ValidacionConstantes.MSG_EMAIL_REQUERIDO)
	private String correo;
	

	/**
	 * Sitio WEB de la entidad.
	 */
	private String sitioWEB;

	
	/**
	 * Fecha de creaci&oacute;n de la entidad en el sistema.
	 */
	private Date fechaCreacion;
	
	
	/**
	 * Fecha de ultima actualizaci&oacute;n de la informaci&oacute;n de la entidad.
	 */
	private Date fechaModificacion;

	
	/**
	 * Usuario que cre&oacute; la entidad en el sistema.
	 */
	private String usuarioCreador;
	
	
	/**
	 * Usuario que modific&oacute; la informaci&oacute;n de la entidad.
	 */
	private String usuarioEditor;
	
	
	/**
	 * Indica si la entidad est&aacute; activa en el sistema.
	 */
	@NotNull
	private Boolean activo;
	
	
	/**
	 * C&oacute;digo DANE del municipio.
	 */
	@NotNull
	@Size(min = 5, max = 5)
	private String codigoMunicipio;
	
	/**
	 * C&oacute;digo DANE del departamento.
	 */
	private String codigoDepartamento;
	
	/**
	 * Constructor sin argumentos.
	 */
	public EntidadDTO() {
		// No args
	}


	/**
	 * Construye una entidad de acuerdo con los par&aacute;metros ingresados.
	 * 
	 * @param id
	 * @param nit
	 * @param idCGN
	 * @param razonSocial
	 * @param sigla
	 * @param direccion
	 * @param telefono
	 * @param nombreContacto
	 * @param apellidoContacto
	 * @param correo
	 * @param sitioWEB
	 * @param fechaCreacion
	 * @param fechaModificacion
	 * @param usuarioCreador
	 * @param usuarioEditor
	 * @param activo
	 * @param codigoMunicipio
	 */
	public EntidadDTO(String nit, String idCGN, String razonSocial, String sigla, String direccion,
			String telefono, String nombreContacto, String apellidoContacto, String correo, String sitioWEB,
			Date fechaCreacion, Date fechaModificacion, String usuarioCreador, String usuarioEditor, Boolean activo,
			String codigoMunicipio) {
		super();
		this.nit = nit;
		this.idCGN = idCGN;
		this.razonSocial = razonSocial;
		this.sigla = sigla;
		this.direccion = direccion;
		this.telefono = telefono;
		this.nombreContacto = nombreContacto;
		this.apellidoContacto = apellidoContacto;
		this.correo = correo;
		this.sitioWEB = sitioWEB;
		this.fechaCreacion = fechaCreacion;
		this.fechaModificacion = fechaModificacion;
		this.usuarioCreador = usuarioCreador;
		this.usuarioEditor = usuarioEditor;
		this.activo = activo;
		this.codigoMunicipio = codigoMunicipio;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idCGN == null) ? 0 : idCGN.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EntidadDTO other = (EntidadDTO) obj;
		if (idCGN == null) {
			if (other.idCGN != null)
				return false;
		} else if (!idCGN.equals(other.idCGN))
			return false;
		return true;
	}

	public String toString() {
		return "Entidad [ nit=" + nit + ", idCGN=" + idCGN + ", razonSocial=" + razonSocial + "]";
	}

}
