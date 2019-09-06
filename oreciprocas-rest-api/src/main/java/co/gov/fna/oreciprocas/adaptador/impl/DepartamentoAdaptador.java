/*
 * Copyright 2019. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.adaptador.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.gov.fna.oreciprocas.dominio.dto.DepartamentoDTO;
import co.gov.fna.oreciprocas.dominio.modelo.Departamento;

/**
 * The Class DepartamentoAdaptador.
 *
 * @author <a href="tecnico_integracion5@utayecisa.com"> Guillermo Garc&iacute;a</a>
 * @version 1.0
 */
@Component
public class DepartamentoAdaptador {

	/** The municipio adaptador. */
	@Autowired
	private MunicipioAdaptador municipioAdaptador;
	/**
	 * Convierte el modelo a dto.
	 *
	 * @param modelo the modelo
	 * @return the departamento DTO
	 */
	public DepartamentoDTO modeloAdto(final Departamento modelo) {
		
		if(Objects.isNull(modelo))
			throw new IllegalArgumentException("Modelo no puede ser nulo");
		
		DepartamentoDTO dto = DepartamentoDTO.builder()
				.codigoDANE(modelo.getCodigoDANE()).nombre(modelo.getNombre())
				.municipios(municipioAdaptador.modeloAdto(modelo.getMunicipios()))
				.build();

		return dto;
	}
	
	/**
	 * Dto a modelo.
	 *
	 * @param dto the dto
	 * @return the departamento modelo
	 */
	public Departamento dtoAmodelo(final DepartamentoDTO dto) {
		
		if(Objects.isNull(dto))
			throw new IllegalArgumentException("Dto no puede ser nulo");
		
		Departamento modelo = Departamento.builder()
				.codigoDANE(dto.getCodigoDANE())
				.nombre(dto.getNombre())
				.municipios(municipioAdaptador.dtoAmodelo(dto.getMunicipios()))
				.build();

		return modelo;
	}
	
	/**
	 * Dto a modelo.
	 *
	 * @param coleccionDto the colecci&oacute;n dto
	 * @return the collection
	 */
	public Collection<Departamento> dtoAmodelo(final Collection<DepartamentoDTO> coleccionDto) {
		
		if(Objects.isNull(coleccionDto))
			throw new IllegalArgumentException("coleccionDto no puede ser nulo");
						
		return CollectionUtils.isEmpty(coleccionDto) ? new ArrayList<>()
				: coleccionDto.stream().map(elemento -> dtoAmodelo(elemento)).collect(Collectors.toList());

	}
	
	/**
	 * Modelo a dto.
	 *
	 * @param coleccionModelo the colecci&oacute;n dto
	 * @return the collection
	 */
	public Collection<DepartamentoDTO> modeloAdto(final Collection<Departamento> coleccionModelo) {
		
		if(Objects.isNull(coleccionModelo))
			throw new IllegalArgumentException("coleccionDto no puede ser nulo");
						
		return CollectionUtils.isEmpty(coleccionModelo) ? new ArrayList<>()
				: coleccionModelo.stream().map(elemento -> modeloAdto(elemento)).collect(Collectors.toList());

	}
}
	
