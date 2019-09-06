/*
 * Copyright 2019. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.repositorio;

import java.util.Optional;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;

import co.gov.fna.oreciprocas.dominio.modelo.Plantilla;
import java.math.BigInteger;

/**
 * Repositorio de entidades tipo {@link Plantilla}
 *
 * @author Zamir Garc&iacute;a Romero - zagarcia@ayesa.com
 * @version 1.0
 *
 */
public interface PlantillaRepositorio extends CrudRepository<Plantilla, BigInteger> {
	
	/**
	 * Busca una plantilla por su nombre y por su existencia l&oacute;gica.
	 * @param nombre Nombre de la plantilla
	 * @param activo Estado deseado.
	 * @return Plantilla que corresponda al nombre y estado.
	 */
	@Cacheable(value = "templates")
	Optional<Plantilla> findByNombreAndActivo(String nombre, Boolean activo);

}
