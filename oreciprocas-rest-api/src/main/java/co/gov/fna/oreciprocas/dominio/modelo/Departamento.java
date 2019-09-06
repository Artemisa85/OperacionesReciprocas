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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representa un departamento de acuerdo con la divisi&oacute;n politico-administrativa
 * de la Rep&uacute;blica de Colombia (DIVIPOLA)
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
@Table(name = "ADM_DEPARTAMENTO")
public class Departamento {
	
	
	/**
	 * Identificador del departamento en el sistema Operaciones Rec&iacute;procas.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;
	
	/**
	 * C&oacute;digo DANE del departamento.
	 */
	@NotEmpty
	@Length(max = 3)
	@Column(name = "CODIGO_DANE")
	private String codigoDANE;
	
	/**
	 * Nombre del departamento
	 */
	@NotEmpty
	@Length(max = 60)
	@Column(name = "NOMBRE")
	private String nombre;
	
	/**
	 * Municipios pertenecientes al departamento.
	 */
	@OneToMany(mappedBy = "departamento", targetEntity = Municipio.class)
	private Collection<Municipio> municipios;
	

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
		Departamento other = (Departamento) obj;
		if (codigoDANE == null) {
			if (other.codigoDANE != null)
				return false;
		} else if (!codigoDANE.equals(other.codigoDANE))
			return false;
		return true;
	}

}
