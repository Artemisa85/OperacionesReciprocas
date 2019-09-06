/*
 * Copyright 2019. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.controlador;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import co.gov.fna.core.common.generic.rest.FnaDtoResult;
import co.gov.fna.core.common.generic.rest.FnaResponse;
import co.gov.fna.oreciprocas.dominio.dto.MunicipioDTO;


/**
 * Controlador REST que gestiona la informaci&oacute;n relacionada con Municipios.
 *
 * @author Zamir Garc&iacute;a Romero - zagarcia@ayesa.com
 * @version 1.0
 *
 */
public interface MunicipioControlador {
	
	/**
	 * Recupera todos los municipios almacenados en el sistema. Este m&eacute;todo pagina los
	 * resultados.
	 * 
	 * @param pageable Configuraci&oacute;n de paginaci&oacute;n.
	 * @return Municipios almacenados en el sistema.
	 */
	ResponseEntity<FnaResponse<FnaDtoResult>> recuperarTodos(Pageable pageable);

	/**
	 * Busca un municipio por su c&ocacute;digo DANE
	 * 
	 * @param codigoDANE C&oacute;digo DANE del municipio.
	 * @return El municipio que concuerde con el c&oacute;digo suministrado.
	 */
	ResponseEntity<FnaResponse<FnaDtoResult>> buscarPorCodigo(String codigoDANE);
	
	/**
	 * Crea un municipio.
	 * 
	 * @param municipio Datos del municipio.
	 * @return Resultado del proceso de creaci&oacute;n,
	 */
	ResponseEntity<FnaResponse<FnaDtoResult>> crear(MunicipioDTO municipio);

	/**
	 * Permite editar un municipio.
	 * 
	 * @param municipio Datos del municipio.
	 * @return Resultado del proceso de edici&oacute;n
	 */
	ResponseEntity<FnaResponse<FnaDtoResult>> editar(MunicipioDTO municipio);
	
	/**
	 * Elimina un municipio del sistema.
	 * 
	 * @param codMunicipio C&oacute;digo del municipio.
	 * @return Resultado del proceso de eliminaci&oacute;n
	 */
	ResponseEntity<FnaResponse<FnaDtoResult>> eliminar(String codMunicipio);
	
	
	
	
}
