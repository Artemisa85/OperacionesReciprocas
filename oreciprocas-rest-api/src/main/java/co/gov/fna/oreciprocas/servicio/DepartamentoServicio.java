package co.gov.fna.oreciprocas.servicio;

import org.springframework.data.domain.Pageable;

import co.gov.fna.oreciprocas.dominio.dto.DepartamentoResultadoDTO;
import co.gov.fna.oreciprocas.dominio.modelo.Departamento;

/**
 * Contrato para gestionar todo lo relacionado con la entidad {@link Departamento}
 * 
 * @author <a href="tecnico_integracion5@utayecisa.com"> Guillermo García</a> 
 * @version 1.0
 */
public interface DepartamentoServicio {

	/**
	 * Recupera todos los departamentos almacenados en el sistema.
	 * 
	 * @param pageable Configuraci&oacute;n de paginaci&oacute;n.
	 * @return Departamentos almacenados en el sistema.
	 */
	DepartamentoResultadoDTO recuperarTodos(Pageable pageable);

}
