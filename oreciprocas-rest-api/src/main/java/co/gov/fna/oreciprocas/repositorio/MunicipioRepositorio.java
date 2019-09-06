/*
 * Copyright 2018. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.repositorio;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import co.gov.fna.oreciprocas.dominio.dto.MunicipioDTO;
import co.gov.fna.oreciprocas.dominio.modelo.Departamento;
import co.gov.fna.oreciprocas.dominio.modelo.Municipio;

/**
 * Repositorio de entidates tipo {@link Departamento}.
 *
 * @author Zamir Garc&iacute;a Romero - zagarcia@ayesa.com
 * @version 1.0
 */
public interface MunicipioRepositorio extends PagingAndSortingRepository<Municipio, Integer>{

	/**
	 * Recupera todos los municipios almacenados en la base de datos.
	 * 
	 * @param pageable Configuraci&oacute;n de paginaci&oacute;n.
	 * @return Municipios almacenados en el sistema.
	 */
	@Query("select new co.gov.fna.oreciprocas.dominio.dto.MunicipioDTO ("
			+ "m.id, m.codigoDANE, m.nombre"
			+ ") from Municipio m")
	Page<MunicipioDTO> recuperarTodos(Pageable pageable);
	
	/**
	 * Busca municipios por el c&oacute;digo DANE del departamento.
	 * 
	 * @param codDepartamento C&oacute;digo DANE del departamento.
	 * @return Municipioas que pertenecen al departamento.
	 */
	@Query("select new co.gov.fna.oreciprocas.dominio.dto.MunicipioDTO ("
			+ "m.id, m.codigoDANE, m.nombre"
			+ ") from Municipio m join m.departamento d "
			+ "where d.codigoDANE = :codDepartamento")
	Collection<MunicipioDTO> buscarPorCodigoDepartamento(
									@Param("codDepartamento") String codDepartamento);

	/**
	 * Busca un municipio por su c&oacute;digo DANE.
	 * @param codigoDANE C&oacute;digo DANE del municipio.
	 * @return Municipio asociado el c&oacute;digo solicitado.
	 */
	Optional<Municipio> findByCodigoDANE(String codigoDANE);

}
