/*
 * Copyright 2018. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.dominio.dto;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

import co.gov.fna.core.common.generic.rest.FnaDtoResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representa un municipio de acuerdo con la divisi&oacute;n politico-administrativa
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
public class MunicipioDTO implements FnaDtoResult {
	
	/**
	 * Identificador del municipio en el sistema Operaciones Rec&iacute;procas.
	 */
	@JsonIgnore
	private Integer id;
	
	/**
	 * C&oacute;digo DANE del municipio.
	 */
	@NotEmpty
	@Length(max = 5)	
	private String codigoDANE;
	
	/**
	 * Nombre del municipio
	 */
	@NotEmpty
	@Length(max = 60)	
	private String nombre;
	
		
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
		MunicipioDTO other = (MunicipioDTO) obj;
		if (codigoDANE == null) {
			if (other.codigoDANE != null)
				return false;
		} else if (!codigoDANE.equals(other.codigoDANE))
			return false;
		return true;
	}
	
}


