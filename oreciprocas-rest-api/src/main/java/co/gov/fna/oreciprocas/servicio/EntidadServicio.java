/*
 * Copyright 2018. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.servicio;

import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import co.gov.fna.oreciprocas.dominio.dto.BusquedaEntidadDTO;
import co.gov.fna.oreciprocas.dominio.dto.EntidadDTO;
import co.gov.fna.oreciprocas.dominio.dto.EntidadResultadoDTO;
import co.gov.fna.oreciprocas.dominio.modelo.Entidad;

/**
 * Servicio de negocio para entidades tipo {@link Entidad}
 *
 * @author Zamir Garc&iacute;a Romero - zagarcia@ayesa.com
 * @version 1.0
 *
 */
public interface EntidadServicio {
	
	/**
	 * Busca una entidad de acuerdo con el NIT y el identificador asignado
	 * por la Contadur&iacute;a General de la Naci&oacute;n.
	 * @param nit N&uacute;mero de identificaci&oacute;n tributaria de la entidad
	 * @param idCGN Identificador asignado por la Contadur&iacute;a
	 *  General de la Naci&oacute;n.
	 * @return Entidad encontrada de acuerdo con los par&acute;metros de b&uacute;squeda.
	 */
	Optional<EntidadDTO> buscarEntidad(String nit, String idCGN);
	
	/**
	 * Busca una entidad por el identificador asignado
	 * por la Contadur&iacute;a General de la Naci&oacute;n. 
	 * @param idCgn Identificador asignado por la Contadur&iacute;a
	 *  General de la Naci&oacute;n.
	 * @return
	 */
	Optional<EntidadDTO> buscarEntidadPorIdCgn(String idCgn);

	/**
	 * Recupera y pagina todas las entidades almacenadas en el sistema. 
	 * @param pageable Configuraci&oacute;n de paginaci&oacute;n
	 * @return Objeto con el resultado de entidades.
	 */
	EntidadResultadoDTO recuperarEntidades(Pageable pageable);

	/**
	 * Busca una entidad de acuerdo a los criterios de b&uacute;squeda ingresados.
	 * 
	 * @param solicitud Criterios de b&uacute;squeda. 
	 * @param pageable Configuraci&oacute;n de paginaci&oacute;n
	 * @return Objeto con el resultado de entidades.
	 */
	EntidadResultadoDTO buscar(BusquedaEntidadDTO solicitud, Pageable pageable);

	/**
	 * Almacena una entidad en el sistema.
	 * 
	 * @param entidad Entidad a crear.
	 * @param auth Datos de autenticaci&oacute;n del usuario.
	 * 
	 * @return Entidad resultado del proceso.
	 */
	EntidadDTO crear(EntidadDTO entidad, Authentication auth);

	/**
	 * Edita una entidad existente en el sistema.
	 * 
	 * @param entidad Entidad a editar.
	 * @param auth Datos de autenticaci&oacute;n del usuario.
	 * 
	 * @return Entidad resultado del proceso
	 */
	EntidadDTO editar(EntidadDTO entidad, Authentication auth);

}
