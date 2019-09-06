/*
 * Copyright 2018. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.dominio.modelo;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Digits;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import co.gov.fna.core.common.generic.rest.FnaDtoResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * Representa un municipio de acuerdo con la divisi&oacute;n politico-administrativa
 * de la Rep&uacute;blica de Colombia (DIVIPOLA)
 *
 * @author Zamir Garc&iacute;a Romero - zagarcia@ayesa.com
 * @version 1.0
 *
 */
@Data
@AllArgsConstructor
@Builder

@Entity
@Table(name = "ADM_MUNICIPIO")
public class Municipio implements FnaDtoResult {
	
	/**
	 * Identificador del municipio en el sistema Operaciones Rec&iacute;procas.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Digits(integer=4, fraction=0)
	@Column(name = "ID")
	private Integer id;
	
	/**
	 * C&oacute;digo DANE del municipio.
	 */
	@NotEmpty
	@Length(max = 5)
	@Column(name = "CODIGO_DANE")	
	private String codigoDANE;
	
	/**
	 * Nombre del municipio
	 */
	@NotEmpty
	@Length(max = 60)
	@Column(name = "NOMBRE")	
	private String nombre;
	
	/**
	 * Entidades que pertenecen al municipio.
	 */
	@OneToMany(mappedBy = "municipio", targetEntity = Entidad.class)
	private Collection<Entidad> entidades;
	
	
	/**
	 * Departamento al que pertenece el municipio.
	 */
	@ManyToOne
	@JoinColumn(name = "ADM_DEPARTAMENTO_ID", referencedColumnName = "ID")
	private Departamento departamento;
	

	/**
	 * Constructor sin pa&aacute;metros.
	 */
	public Municipio() {
		
	}

	/**
	 * Crea un municipio solo con el identificador.
	 * @param id Identificador del municipio en el sistema.
	 */
	public Municipio(Integer id) {
		this.id = id;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigoDANE == null) ? 0 : codigoDANE.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Municipio other = (Municipio) obj;
		if (codigoDANE == null) {
			if (other.codigoDANE != null)
				return false;
		} else if (!codigoDANE.equals(other.codigoDANE))
			return false;
		return true;
	}

}
