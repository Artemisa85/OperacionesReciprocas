/*
 * Copyright 2019. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.repositorio;

import java.util.Collection;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.PagingAndSortingRepository;

import co.gov.fna.oreciprocas.dominio.modelo.ConfiguracionRegistro;

/**
 * Repositorio de entidades tipo {@link ConfiguracionRegistro}
 *
 * @author Zamir Garc&iacute;a Romero - zagarcia@ayesa.com
 * @version 1.0
 *
 */
public interface ConfiguracionRepositorio extends 
						PagingAndSortingRepository<ConfiguracionRegistro, Integer> {

	/**
	 * Retorna todas las configuraciones de un dominio.
	 * @param dominio Nombre del dominio buscado.
	 * @return Configuraciones de un dominio.
	 */
	@Cacheable(value = "configuration")
	Collection<ConfiguracionRegistro> findByDominio(String dominio);

	/**
	 * Retorna una configuracion espec&iacute;fica.
	 * @param dominio Dominio de configuraci&oacute;nn
	 * @param codigo C&oacute;ndigo de la configuraci&oacute;nn
	 * @return configuracion espec&iacute;fica. buscada.11
	 */
	@Cacheable(value = "configuration")
	ConfiguracionRegistro findByDominioAndCodigo(String dominio, String codigo);
	
}
