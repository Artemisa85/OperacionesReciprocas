/*
 * Copyright 2019. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.adaptador.impl;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.gov.fna.oreciprocas.adaptador.ModeloDtoAdaptador;
import co.gov.fna.oreciprocas.dominio.dto.ComentarioDTO;
import co.gov.fna.oreciprocas.dominio.modelo.Comentario;

/**
 * Adaptador para el modelo y DTO de Comentario
 *
 * @author Zamir Garc&iacute;a Romero - zagarcia@ayesa.com
 * @version 1.0
 *
 */
@Component
public class ComentarioAdaptador implements ModeloDtoAdaptador<Comentario, ComentarioDTO> {

	
	@Autowired
	private AdjuntoAdaptator adjuntoAdaptador;
	
	
	/**
	  * {@inheritDoc}
	  */
	@Override
	public ComentarioDTO modeloAdto(Comentario comentario) {
		return ComentarioDTO.builder()
				.id(comentario.getId())
				.texto(comentario.getTexto())
				.autor(comentario.getAutor())
				.autorRol(comentario.getAutorRol())
				.fecha(comentario.getFecha())
				.idTransaccion(comentario.getIdTransaccion())
				.build();
	}

	/**
	  * {@inheritDoc}
	  */
	@Override
	public Collection<ComentarioDTO> modeloAdto(Collection<Comentario> coleccionModelo) {
		Collection<ComentarioDTO> coleccionDto = new ArrayList<>();
		coleccionModelo.forEach(comentario -> {
			ComentarioDTO comentarioDTO = modeloAdto(comentario);
			comentarioDTO.setAdjuntos( adjuntoAdaptador.modeloAdto(comentario.getAdjuntos()) );
			coleccionDto.add( comentarioDTO );
		});
		return coleccionDto;
	}

	/**
	  * {@inheritDoc}
	  */
	@Override
	public Comentario dtoAmodelo(ComentarioDTO dto) {
		throw new UnsupportedOperationException("Not yet implemented");
	}

	/**
	  * {@inheritDoc}
	  */
	@Override
	public Collection<Comentario> dtoAmodelo(Collection<ComentarioDTO> coleccionDTO) {
		throw new UnsupportedOperationException("Not yet implemented");
	}

}
