/*
 * Copyright 2018. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.dominio.dto;

import java.util.Collection;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import co.gov.fna.core.common.generic.rest.FnaDtoResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * Representa un departamento de acuerdo con la divisi&oacute;n politico-administrativa
 * de la Rep&uacute;blica de Colombia (DIVIPOLA)
 *
 * @author Zamir Garc&iacute;a Romero - zagarcia@ayesa.com
 * @version 1.0
 *
 */
@Data
@AllArgsConstructor
@Builder
public class DepartamentoDTO implements FnaDtoResult{
	
	/**
	 * C&oacute;digo DANE del departamento.
	 */
	@NotEmpty	
	@Length(max = 3)	
	private String codigoDANE;
	
	/**
	 * Nombre del departamento
	 */
	@NotEmpty
	private String nombre;
	
	/**
	 * Municipios pertenecientes al departamento.
	 */	
	private Collection<MunicipioDTO> municipios;
	
	
	/**
	 * Construye un departamento con los par&aacute;metros establecidos.
	 * 
	 * @param codigoDANE C&oacute;digo DANE del departamento
	 * @param nombre Nombre del departamento.
	 */
	public DepartamentoDTO(String codigoDANE, String nombre) {
		this.codigoDANE = codigoDANE;
		this.nombre = nombre;
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
		DepartamentoDTO other = (DepartamentoDTO) obj;
		if (codigoDANE == null) {
			if (other.codigoDANE != null)
				return false;
		} else if (!codigoDANE.equals(other.codigoDANE))
			return false;
		return true;
	}
	
	@Data		
	public class DepartamentoFindByIdDTO {
		
		/**
		 * Identificador del departamento en el sistema Operaciones Rec&iacute;procas.
		 */	
		@Digits(integer=3, fraction=0)
		private Integer id;
		
		public DepartamentoFindByIdDTO() {			
		}
	}

}
