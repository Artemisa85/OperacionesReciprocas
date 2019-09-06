/*
 * Copyright 2019. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.adaptador.impl;

import java.util.Collection;

import org.springframework.stereotype.Component;

import co.gov.fna.oreciprocas.adaptador.ModeloDtoAdaptador;
import co.gov.fna.oreciprocas.dominio.dto.EstadoDTO;
import co.gov.fna.oreciprocas.dominio.dto.TransaccionDetalleDTO;
import co.gov.fna.oreciprocas.dominio.modelo.Transaccion;

/**
 * Adaptador para el modelo y DTO de Detalle de Transaccion
 *
 * @author Zamir Garc&iacute;a Romero - zagarcia@ayesa.com
 * @version 1.0
 *
 */
@Component
public class TransaccionDetalleAdaptador implements ModeloDtoAdaptador<Transaccion, TransaccionDetalleDTO> {

	/**
	  * {@inheritDoc}
	  */
	@Override
	public TransaccionDetalleDTO modeloAdto(Transaccion transaccion) {
		
		EstadoDTO estado = EstadoDTO.builder()
				.codigo(transaccion.getEstado().getCodigo())
				.descripcion(transaccion.getEstado().getDescripcion())
				.build();
		
		String[] comodines = resolverComodines(transaccion);
		
		return TransaccionDetalleDTO.builder()
				.idTransaccion(transaccion.getId())
				.contactoContador(transaccion.getContactoContador())
				.estado(estado)
				.fechaCargue(transaccion.getFechaCague().toString())
				.fechaConsolidacion(transaccion.getFechaConsolidado().toString())
				.comodin1(comodines[0]).comodin2(comodines[1]).comodin3(comodines[2])
				.comodin4(comodines[3]).comodin5(comodines[4])
				.build();
	}

	/**
	  * {@inheritDoc}
	  */
	@Override
	public Collection<TransaccionDetalleDTO> modeloAdto(Collection<Transaccion> coleccionModelo) {
		throw new UnsupportedOperationException("Not yet implemented");
	}

	/**
	  * {@inheritDoc}
	  */
	@Override
	public Transaccion dtoAmodelo(TransaccionDetalleDTO dto) {
		throw new UnsupportedOperationException("Not yet implemented");
	}

	/**
	  * {@inheritDoc}
	  */
	@Override
	public Collection<Transaccion> dtoAmodelo(Collection<TransaccionDetalleDTO> coleccionDTO) {
		throw new UnsupportedOperationException("Not yet implemented");
	}

	/**
	 * Extrae los valores de los comodines de una transacci&oacute;n
	 * @param transaccion Transacci&oacute;n involucrada
	 * @return Arreglo de String de 5 posiciones con los comodines.
	 */
	private String[] resolverComodines(Transaccion transaccion) {
		String[] comodines = new String[5];
		if (transaccion.getComodin() != null) {
			comodines[0] = transaccion.getComodin().getComodin1();
			comodines[1] = transaccion.getComodin().getComodin2();
			comodines[2] = transaccion.getComodin().getComodin3();
			comodines[3] = transaccion.getComodin().getComodin4();
			comodines[4] = transaccion.getComodin().getComodin5();
		}
		return comodines;
	}

}
