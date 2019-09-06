/*
 * Copyright 2019. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.controlador;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import co.gov.fna.core.common.generic.rest.FnaDtoResult;
import co.gov.fna.core.common.generic.rest.FnaResponse;

/**
 * Controlador REST que gestiona la informaci&oacute;n relacionada con Departamentos. 
 *
 * @author <a href="tecnico_integracion5@utayecisa.com"> Guillermo Garc&iacute;a</a>
 * @version 1.0
 */
public interface DepartamentoControlador {
	
	/**
	 * Recupera y pagina todos los departamentos almacenados en el sistema.
	 * 
	 * @param pageable Configuraci&oacute;n de paginaci&oacute;n.
	 * @return Departamentos almacenados en el sistema.
	 */
	ResponseEntity<FnaResponse<FnaDtoResult>> recuperarTodos(Pageable pageable);
	
	/**
	 * Busca los municipios pertenecientes a un departamento.
	 * 
	 * @param codDepartamento C&oacute;digo DANE del departamento.
	 * @param pageable Configuraci&oacute;n de paginaci&oacute;n.
	 * @return Municipios pertenecientes al departmento
	 */
	ResponseEntity<FnaResponse<FnaDtoResult>> buscarMunicipiosDepartamento(String codDepartamento);
		
}
