/*
 * Copyright 2019. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.servicio;

import org.springframework.data.domain.Pageable;

import co.gov.fna.oreciprocas.dominio.dto.MunicipioDTO;
import co.gov.fna.oreciprocas.dominio.dto.MunicipioResultadoDTO;
import co.gov.fna.oreciprocas.dominio.modelo.Municipio;

/**
 * Servicio de negocio para entidades tipo {@link Municipio}.
 *
 * @author <a href="tecnico_integracion5@utayecisa.com"> Guillermo García</a>
 * @version 1.0
 */
public interface MunicipioServicio {

	/**
	 * Recupera todos los municipios almacenados en el sistema.
	 * 
	 * @param pageable Configuraci&oacute;n de paginaci&oacute;n.
	 * @return Municipios almacenados en el sistema.
	 */
	MunicipioResultadoDTO recuperarTodos(Pageable pageable);
	
	
	/**
	 * Busca municipios por el c&oacute;digo DANE del departamento.
	 * 
	 * @param codDepartamento C&oacute;digo DANE del departamento.
	 * @return Municipios que pertenecen al departamento.
	 */
	MunicipioResultadoDTO buscarPorCodigoDepartamento(String codDepartamento);


	/**
	 * Busca un departamento por su código DANE
	 * 
	 * @param codigoDANE Código del departamento.
	 * @return Municipio que corresponde al código suministrado.
	 */
	MunicipioDTO buscarPorCodigoDane(String codigoDANE);

	
}
