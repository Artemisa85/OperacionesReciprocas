/*
 * Copyright 2019. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.repositorio;

import java.math.BigInteger;

import org.springframework.data.repository.PagingAndSortingRepository;

import co.gov.fna.oreciprocas.dominio.modelo.ArchivoAdjunto;

/**
 * Repositorio de entidades tipo {@link ArchivoAdjunto}
 *
 * @author Zamir Garc&iacute;a Romero - zagarcia@ayesa.com
 * @version 1.0
 *
 */
public interface AdjuntoRepositorio extends PagingAndSortingRepository<ArchivoAdjunto, BigInteger> {
	
}
