/*
 * Copyright 2019. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.adaptador.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import co.gov.fna.oreciprocas.adaptador.ModeloDtoAdaptador;
import co.gov.fna.oreciprocas.dominio.dto.MunicipioDTO;
import co.gov.fna.oreciprocas.dominio.modelo.Municipio;

/**
 * Adaptador para el modelo y DTO de Municipio.
 *
 * @author <a href="tecnico_integracion5@utayecisa.com"> Guillermo
 *         Garc&iacute;a</a>
 * @version 1.0
 */
@Component
public class MunicipioAdaptador implements ModeloDtoAdaptador<Municipio, MunicipioDTO> {

	/**
	 * 
	  * {@inheritDoc}
	 */
	public MunicipioDTO modeloAdto(final Municipio modelo) {
		if(Objects.isNull(modelo)) {
			return null;
		}
		
		return MunicipioDTO.builder()
				.id(modelo.getId())
				.codigoDANE(modelo.getCodigoDANE())
				.nombre(modelo.getNombre())
				.build();
	}
	
	/**
	  * {@inheritDoc}
	 */
	public Collection<MunicipioDTO> modeloAdto(final Collection<Municipio> coleccionModelo) {
		return CollectionUtils.isEmpty(coleccionModelo) ? new ArrayList<>()
				: coleccionModelo.stream().map(elemento -> modeloAdto(elemento))
				.collect(Collectors.toList());
	}

	/**
	 * Dto a modelo.
	 *
	 * @param dto the dto
	 * @return the Municipio modelo
	 */
	public Municipio dtoAmodelo(final MunicipioDTO dto) {
		if(Objects.isNull(dto)) {
			return null;
		}
		
		return Municipio.builder()
				.codigoDANE(dto.getCodigoDANE())
				.nombre(dto.getNombre())
				.build();
	}
	
	/**
	 * Dto a modelo.
	 *
	 * @param coleccionDto the colecci&oacute;n dto
	 * @return the collection
	 */
	public Collection<Municipio> dtoAmodelo(final Collection<MunicipioDTO> coleccionDto) {
		return CollectionUtils.isEmpty(coleccionDto) ? new ArrayList<>()
				: coleccionDto.stream().map(municipio -> dtoAmodelo(municipio))
				.collect(Collectors.toList());
	}
}
	
