/*
 * Copyright 2019. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.adaptador.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import org.springframework.stereotype.Service;

import co.gov.fna.oreciprocas.adaptador.ModeloDtoAdaptador;
import co.gov.fna.oreciprocas.dominio.dto.EntidadDTO;
import co.gov.fna.oreciprocas.dominio.modelo.Entidad;

/**
 * Adaptador para el modelo y DTO de Entidad
 *
 * @author Zamir Garc&iacute;a Romero - zagarcia@ayesa.com
 * @version 1.0
 *
 */
@Service
public class EntidadAdaptador implements ModeloDtoAdaptador<Entidad, EntidadDTO>{

	/**
	  * {@inheritDoc}
	  */
	public EntidadDTO modeloAdto(Entidad modelo) {
		if ( Objects.isNull(modelo) ) {
			return null;
		}
		return EntidadDTO.builder()
				.nit(modelo.getNit())
				.idCGN(modelo.getIdCGN())
				.razonSocial(modelo.getRazonSocial())
				.sigla(modelo.getSigla())
				.direccion(modelo.getDireccion())
				.telefono(modelo.getTelefono())
				.nombreContacto(modelo.getNombreContacto())
				.apellidoContacto(modelo.getApellidoContacto())
				.correo(modelo.getCorreo())
				.sitioWEB(modelo.getSitioWEB())
				.fechaCreacion(modelo.getFechaCreacion())
				.fechaModificacion(modelo.getFechaModificacion())
				.usuarioCreador(modelo.getUsuarioCreador())
				.usuarioEditor(modelo.getUsuarioEditor())
				.activo(modelo.isActivo())
				//.codigoMunicipio(modelo.getIdMunicipio())
				.build();
	}

	/**
	  * {@inheritDoc}
	  */
	public Collection<EntidadDTO> modeloAdto(Collection<Entidad> coleccionModelo) {
		Collection<EntidadDTO> entidades = new ArrayList<>();
		coleccionModelo.forEach(entidad -> {
			entidades.add(modeloAdto(entidad));
		});
		return entidades;
	}

	/**
	  * {@inheritDoc}
	  */
	public Entidad dtoAmodelo(EntidadDTO dto) {
		if ( Objects.isNull(dto) ) {
			return null;
		}
		return Entidad.builder()
				.nit(dto.getNit())
				.idCGN(dto.getIdCGN())
				.razonSocial(dto.getRazonSocial())
				.sigla(dto.getSigla())
				.direccion(dto.getDireccion())
				.telefono(dto.getTelefono())
				.nombreContacto(dto.getNombreContacto())
				.apellidoContacto(dto.getApellidoContacto())
				.correo(dto.getCorreo())
				.sitioWEB(dto.getSitioWEB())
				.fechaCreacion(dto.getFechaCreacion())
				.fechaModificacion(dto.getFechaModificacion())
				.usuarioCreador(dto.getUsuarioCreador())
				.usuarioEditor(dto.getUsuarioEditor())
				.activo(dto.getActivo())
				//.codigoMunicipio(modelo.getIdMunicipio())
				.build();
	}

	/**
	  * {@inheritDoc}
	  */
	public Collection<Entidad> dtoAmodelo(Collection<EntidadDTO> coleccionDTO) {
		Collection<Entidad> entidades = new ArrayList<>();
		coleccionDTO.forEach(entidad -> {
			entidades.add(dtoAmodelo(entidad));
		});
		return entidades;
	}

}
