package co.gov.fna.oreciprocas.servicio.impl;

import java.util.Collection;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import co.gov.fna.core.common.exception.ErrorDetail;
import co.gov.fna.oreciprocas.adaptador.impl.MunicipioAdaptador;
import co.gov.fna.oreciprocas.dominio.dto.MunicipioDTO;
import co.gov.fna.oreciprocas.dominio.dto.MunicipioResultadoDTO;
import co.gov.fna.oreciprocas.dominio.modelo.Municipio;
import co.gov.fna.oreciprocas.exception.EntidadNoEncontradaException;
import co.gov.fna.oreciprocas.repositorio.MunicipioRepositorio;
import co.gov.fna.oreciprocas.servicio.MunicipioServicio;

/**
 * Implementaci&oacute;n por defecto de {@link MunicipioServicio}
 *
 * @author <a href="tecnico_integracion5@utayecisa.com"> Guillermo Garc&iacute;a</a>
 * @version 1.0
 */
@Service
public class MunicipioServicioImpl implements MunicipioServicio {
	
	@Autowired
	private MunicipioRepositorio municipioRepositorio;
	
	@Autowired
	private MunicipioAdaptador municipioAdaptador;
	

	/**
	  * {@inheritDoc}
	  */
	@Override
	public MunicipioResultadoDTO recuperarTodos(Pageable pageable) {
		Page<MunicipioDTO> municipios = municipioRepositorio.recuperarTodos(pageable);
		return MunicipioResultadoDTO.builder()
				.municipiosPag(municipios)
				.build();
	}


	/**
	  * {@inheritDoc}
	  */
	@Override
	public MunicipioResultadoDTO buscarPorCodigoDepartamento(String codDepartamento) {
		Collection<MunicipioDTO> municipios = 
							municipioRepositorio.buscarPorCodigoDepartamento(codDepartamento);
		
		return MunicipioResultadoDTO.builder()
				.municipiosColl(municipios)
				.build();
	}


	/**
	  * {@inheritDoc}
	  */
	@Override
	public MunicipioDTO buscarPorCodigoDane(String codigoDANE) {
		Optional<Municipio> municipio = municipioRepositorio.findByCodigoDANE(codigoDANE);
		
		if ( !municipio.isPresent() ) {
			lanzarExcepcionMunicipioNoValido(codigoDANE);
		}
		return municipioAdaptador.modeloAdto(municipio.get());
	}
	

	/**
	 * Lanza una excepci&oacute; cuando no encuentra el municipio por su c&oacute;digo.
	 * 
	 * @param codigoMunicipio C&oacute;digo del municipio.
	 */
	private void lanzarExcepcionMunicipioNoValido(String codigoMunicipio) {
		ErrorDetail errorDetail = new ErrorDetail();
		errorDetail.setCode("MUNICIPIO_NO_VALIDO");
		errorDetail.setMessage(String.format("El municipio con código %s no existe",
				codigoMunicipio)); 
		errorDetail.setTimestamp(new Date());
		throw new EntidadNoEncontradaException(errorDetail, HttpStatus.BAD_REQUEST);
	}


}
