/*
 * Copyright 2019. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.repositorio;

import java.math.BigInteger;
import java.util.Collection;

import org.springframework.data.repository.PagingAndSortingRepository;

import co.gov.fna.oreciprocas.dominio.modelo.Comentario;

/**
 * Repositorio de entidades tipo {@link Comentario}
 *
 * @author Zamir Garc&iacute;a Romero - zagarcia@ayesa.com
 * @version 1.0
 *
 */
public interface ComentarioRepositorio extends PagingAndSortingRepository<Comentario, BigInteger> {
	
	/**
	 * Recupera los comentarios asociados a una transacci&oacute;n.
	 * @param idCgnEntidadReciproca
	 * @return Colecci&oacute;n con los comentarios de una transacci&oacute;n
	 */
	Collection<Comentario> findByIdTransaccion(BigInteger idTransaccion);
	


}
