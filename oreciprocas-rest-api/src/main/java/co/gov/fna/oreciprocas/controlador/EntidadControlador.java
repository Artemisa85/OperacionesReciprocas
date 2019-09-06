/*
 * Copyright 2019. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.controlador;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import co.gov.fna.core.common.generic.rest.FnaDtoResult;
import co.gov.fna.core.common.generic.rest.FnaResponse;
import co.gov.fna.oreciprocas.dominio.dto.BusquedaEntidadDTO;
import co.gov.fna.oreciprocas.dominio.dto.EntidadDTO;

/**
 * Controlador REST que gestiona la informaci&oacute;n relacionada con Entidades.
 *
 * @author Zamir Garc&iacute;a Romero - zagarcia@ayesa.com
 * @version 1.0
 *
 */
public interface EntidadControlador {
	
	/**
	 * Recupera todas las entidades del sistema.
	 * @param pageable Objeto que gestiona la paginaci&oacute;n.
	 * @return Entidades almacenadas en el sistema.
	 */
	ResponseEntity<FnaResponse<FnaDtoResult>> recuperarEntidades(Pageable pageable);
	
	/**
	 * Recupera todas las entidades que se encuentran marcadas como activas.
	 * @param pageable Objeto que gestiona la paginaci&oacute;n.
	 * @return Entidades activas.
	 */
	ResponseEntity<FnaResponse<FnaDtoResult>> recuperarEntidadesActivas(Pageable pageable);
	
	
	/**
	 * Busca una entidad de acuerdo a los criterios de b&uacute;squeda ingresados.
	 * 
	 * @param solicitud Par&aacute;metros de b&uacute;squeda
	 * @param pageable Objeto que gestiona la paginaci&oacute;n.
	 * @return Entidades coincidientes con los criterios (Si existen).
	 */
	ResponseEntity<FnaResponse<FnaDtoResult>> buscar(BusquedaEntidadDTO solicitud, Pageable pageable);
	
	/**
	 * Busca una entidad por el idCgn
	 * 
	 * @param idCgn Identificador asignado por la Contadur&iacute;a General de la Naci&oacute;n.
	 * @return Entidad que corresponde al idCgn solicitado (Si existe).
	 */
	ResponseEntity<FnaResponse<FnaDtoResult>> buscarPorIdCgn(String idCgn);
	
	/**
	 * Crea una entidad en el sistema.
	 * 
	 * @param solicitud Datos de la entidad.
	 * @param auth Datos de autenticaci&oacute;n del usuario.
	 * @return Informaci&oacute;n sobre la creaci&oacute;n de la entidad.
	 */
	ResponseEntity<FnaResponse<FnaDtoResult>> crearEntidad(EntidadDTO solicitud, Authentication auth);

	/**
	 * Crea una entidad en el sistema.
	 * 
	 * @param solicitud Datos de la entidad.
	 * @param auth Datos de autenticaci&oacute;n del usuario.
	 * @return Informaci&oacute;n sobre la creaci&oacute;n de la entidad.
	 */
	ResponseEntity<FnaResponse<FnaDtoResult>> editarEntidad(EntidadDTO solicitud, Authentication auth);
	
	/**
	 * Elimina un entidad del sistema.
	 * 
	 * @param id Identificador de la entidad.
	 * @param auth Datos de autenticaci&oacute;n del usuario.
	 * @return Informaci&oacute;n sobre la creaci&oacute;n de la entidad.
	 */
	ResponseEntity<FnaResponse<FnaDtoResult>> eliminarEntidad(Integer id, Authentication auth);
	
	

}
