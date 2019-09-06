package co.gov.fna.oreciprocas.repositorio;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import co.gov.fna.oreciprocas.dominio.dto.DepartamentoDTO;
import co.gov.fna.oreciprocas.dominio.modelo.Departamento;

/**
 * Repositorio de entidates tipo {@link Departamento}
 *
 * @author Zamir Garc&iacute;a Romero - zagarcia@ayesa.com
 * @version 1.0
 *
 */
public interface DepartamentoRepositorio extends PagingAndSortingRepository<Departamento, Integer>{

	/**
	 * Recupera todos los departamentos almacenados en el sistema.
	 * 
	 * @param pageable Configuraci&oacute;n de paginaci&oacute;n.
	 * @return Departamentos almacenados en el sistema.
	 */
	@Query("select new co.gov.fna.oreciprocas.dominio.dto.DepartamentoDTO ( "
			+ "d.codigoDANE, d.nombre"
			+ ") from Departamento d")
	Page<DepartamentoDTO> recuperarTodos(Pageable pageable);

}
