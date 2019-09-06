package co.gov.fna.oreciprocas.servicio.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import co.gov.fna.oreciprocas.dominio.dto.DepartamentoDTO;
import co.gov.fna.oreciprocas.dominio.dto.DepartamentoResultadoDTO;
import co.gov.fna.oreciprocas.repositorio.DepartamentoRepositorio;
import co.gov.fna.oreciprocas.servicio.DepartamentoServicio;

/**
 * Implementaci&oacute;n por defecto de {@link DepartamentoServicio}
 *
 * @author <a href="tecnico_integracion5@utayecisa.com"> Guillermo Garc&iacute;a</a>
 * @version 1.0
 */
@Service
public class DepartamentoServicioImpl implements DepartamentoServicio {

	/** The repository. */
	@Autowired
	private DepartamentoRepositorio departemnentoRepositorio;

	/**
	  * {@inheritDoc}
	  */
	@Override
	public DepartamentoResultadoDTO recuperarTodos(Pageable pageable) {
		Page<DepartamentoDTO> departamentos = departemnentoRepositorio.recuperarTodos(pageable);
		
		return DepartamentoResultadoDTO.builder()
				.departamentosPag(departamentos)
				.build();
	}
	

}
