/*
 * Copyright 2019. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.repositorio;

import co.gov.fna.oreciprocas.dominio.modelo.Comodin;
import java.math.BigInteger;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Repositorio de entidades de tipo {@link Comodin}
 * 
 * @author Esteban Salinas
 * @version 1.0
 */
public interface ComodinRepositorio extends PagingAndSortingRepository<Comodin, BigInteger> {

}
