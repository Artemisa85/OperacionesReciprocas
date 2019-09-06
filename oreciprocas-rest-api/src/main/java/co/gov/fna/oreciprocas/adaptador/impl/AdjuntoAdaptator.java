/*
 * Copyright 2019. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.adaptador.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import org.springframework.stereotype.Component;

import co.gov.fna.oreciprocas.adaptador.ModeloDtoAdaptador;
import co.gov.fna.oreciprocas.dominio.dto.AdjuntoDTO;
import co.gov.fna.oreciprocas.dominio.modelo.ArchivoAdjunto;

/**
 * Adaptador para el modelo y DTO de Comentario
 *
 * @author Zamir Garc&iacute;a Romero - zagarcia@ayesa.com
 * @version 1.0
 *
 */
@Component
public class AdjuntoAdaptator implements ModeloDtoAdaptador<ArchivoAdjunto, AdjuntoDTO> {

	/**
	  * {@inheritDoc}
	  */
	@Override
	public AdjuntoDTO modeloAdto(ArchivoAdjunto modelo) {
		return AdjuntoDTO.builder()
				.id(modelo.getId())
				.nombre(modelo.getNombre())
				.extension(modelo.getExtension())
				.tipoMime(modelo.getTipoMime())
				.url(modelo.getUrl())
				.build();
				
	}

	/**
	  * {@inheritDoc}
	  */
	@Override
	public Collection<AdjuntoDTO> modeloAdto(Collection<ArchivoAdjunto> coleccionModelo) {
		if ( Objects.isNull(coleccionModelo) ) {
			return null;
		}
		Collection<AdjuntoDTO> adjuntos = new ArrayList<>();
		
		coleccionModelo.forEach(adjunto -> {
			adjuntos.add(modeloAdto(adjunto));
		});
		
		return adjuntos;
	}

	/**
	  * {@inheritDoc}
	  */
	@Override
	public ArchivoAdjunto dtoAmodelo(AdjuntoDTO dto) {
		throw new UnsupportedOperationException("Not yet implemented");
	}

	/**
	  * {@inheritDoc}
	  */
	@Override
	public Collection<ArchivoAdjunto> dtoAmodelo(Collection<AdjuntoDTO> coleccionDTO) {
		throw new UnsupportedOperationException("Not yet implemented");
	}

}
