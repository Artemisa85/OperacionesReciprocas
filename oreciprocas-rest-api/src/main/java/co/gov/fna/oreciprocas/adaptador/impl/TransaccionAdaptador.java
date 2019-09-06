/*
 * Copyright 2019. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.adaptador.impl;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.stereotype.Component;

import co.gov.fna.oreciprocas.adaptador.ModeloDtoAdaptador;
import co.gov.fna.oreciprocas.dominio.dto.TransaccionDTO;
import co.gov.fna.oreciprocas.dominio.modelo.Transaccion;

/**
 * Adaptador para modelo y DTO de transacciones.
 *
 * @author Zamir Garc&iacute;a Romero - zagarcia@ayesa.com
 * @version 1.0
 *
 */
@Component
public class TransaccionAdaptador implements ModeloDtoAdaptador<Transaccion, TransaccionDTO> {

	/**
	  * {@inheritDoc}
	  */
	public TransaccionDTO modeloAdto(Transaccion modelo) {
		return TransaccionDTO.builder()
				.id(modelo.getId())
				.periodo(modelo.getPeriodo())
				.fechaConsolidado(modelo.getFechaConsolidado())
				.idCgnEntidadReciproca(modelo.getIdCgnEntidadReciproca())
				.nombreEntidadReciproca(modelo.getNombreEntidadReciproca())
				.entidadesInvolucradas(modelo.getEntidadesInvolucradas())
				.contactoContador(modelo.getContactoContador())
				.estado(modelo.getEstado())
				.fechaCague(modelo.getFechaCague())
				.build();
	}

	/**
	  * {@inheritDoc}
	  */
	public Collection<TransaccionDTO> modeloAdto(Collection<Transaccion> coleccionModelo) {
		Collection<TransaccionDTO> transacciones = new ArrayList<>();
		coleccionModelo.forEach(trx -> {
			transacciones.add( modeloAdto(trx) );
		});
		return transacciones;
	}

	/**
	  * {@inheritDoc}
	  */
	public Transaccion dtoAmodelo(TransaccionDTO dto) {
		return Transaccion.builder()
				.id(dto.getId())
				.periodo(dto.getPeriodo())
				.fechaConsolidado(dto.getFechaConsolidado())
				.idCgnEntidadReciproca(dto.getIdCgnEntidadReciproca())
				.nombreEntidadReciproca(dto.getNombreEntidadReciproca())
				.entidadesInvolucradas(dto.getEntidadesInvolucradas())
				.contactoContador(dto.getContactoContador())
				.estado(dto.getEstado())
				.fechaCague(dto.getFechaCague())
				.build();
	}

	/**
	  * {@inheritDoc}
	  */
	public Collection<Transaccion> dtoAmodelo(Collection<TransaccionDTO> coleccionDTO) {
		Collection<Transaccion> transacciones = new ArrayList<>();
		coleccionDTO.forEach(trx -> {
			transacciones.add( dtoAmodelo(trx) );
		});
		return transacciones;
	}

}
