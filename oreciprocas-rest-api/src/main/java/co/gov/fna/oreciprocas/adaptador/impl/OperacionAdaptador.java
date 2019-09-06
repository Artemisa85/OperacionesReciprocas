/*
 * Copyright 2019. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.adaptador.impl;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.stereotype.Component;

import co.gov.fna.oreciprocas.adaptador.ModeloDtoAdaptador;
import co.gov.fna.oreciprocas.dominio.dto.OperacionDTO;
import co.gov.fna.oreciprocas.dominio.modelo.Operacion;

/**
 * Adaptador para el modelo y DTO de Operacion.
 *
 * @author Zamir Garc&iacute;a Romero - zagarcia@ayesa.com
 * @version 1.0
 *
 */
@Component
public class OperacionAdaptador implements ModeloDtoAdaptador<Operacion, OperacionDTO>{

	/**
	  * {@inheritDoc}
	  */
	@Override
	public OperacionDTO modeloAdto(Operacion modelo) {
		return OperacionDTO.builder()
				.idEntidad(modelo.getIdCgn())
				.nombreEntidad(modelo.getNombreEntidad())
				.liquidez(modelo.getLiquidez())
				.codigoCuenta(modelo.getCodigoCuenta())
				.descripcionCuenta(modelo.getDescripcionCuenta())
				.valorReportado(modelo.getValorReportado())
				.partidaConciliatoria(modelo.getPartidaConciliatoria())
				.origenDiferencia(modelo.getOrigenDiferencia())
				.analistaCategoria(modelo.getAnalistaCategoria())
				.build();
	}

	/**
	  * {@inheritDoc}
	  */
	@Override
	public Collection<OperacionDTO> modeloAdto(Collection<Operacion> coleccionModelo) {
		Collection<OperacionDTO> operaciones = new ArrayList<>();
		
		coleccionModelo.forEach(operacion -> {
			operaciones.add( modeloAdto(operacion) );
		});
		
		return operaciones;
	}

	/**
	  * {@inheritDoc}
	  */
	@Override
	public Operacion dtoAmodelo(OperacionDTO dto) {
		throw new UnsupportedOperationException("Not yet implemented");
	}

	/**
	  * {@inheritDoc}
	  */
	@Override
	public Collection<Operacion> dtoAmodelo(Collection<OperacionDTO> coleccionDTO) {
		throw new UnsupportedOperationException("Not yet implemented");
	}

}
