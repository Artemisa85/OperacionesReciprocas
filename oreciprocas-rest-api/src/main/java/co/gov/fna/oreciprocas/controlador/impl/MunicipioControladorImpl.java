/*
 * Copyright 2019. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.controlador.impl;

import java.util.Arrays;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.gov.fna.core.common.generic.rest.FnaDtoResult;
import co.gov.fna.core.common.generic.rest.FnaResponse;
import co.gov.fna.oreciprocas.controlador.MunicipioControlador;
import co.gov.fna.oreciprocas.dominio.dto.MunicipioDTO;
import co.gov.fna.oreciprocas.dominio.dto.MunicipioResultadoDTO;
import co.gov.fna.oreciprocas.servicio.MunicipioServicio;

/**
 * Controlador que implementa el contrato de la clase {@link MunicipioControlador}.
 *
 * @author <a href="tecnico_integracion5@utayecisa.com"> Guillermo Garc&iacute;a</a>
 * @version 1.0
 */
@RestController
@RequestMapping("/api/municipios")
public class MunicipioControladorImpl implements MunicipioControlador {

	@Autowired
	private MunicipioServicio municipioServicio;

	/**
	  * {@inheritDoc}
	  */
	@GetMapping
	public ResponseEntity<FnaResponse<FnaDtoResult>> recuperarTodos(Pageable pageable) {
		MunicipioResultadoDTO resultado = municipioServicio.recuperarTodos(pageable);
		
		HttpStatus status = HttpStatus.OK;
		
		FnaResponse<FnaDtoResult> fnaResponse = 
				FnaResponse.builder()
				.code(status.name())
				.status(status.value())
				.timestamp(Calendar.getInstance().getTime())
				.message(status.name())
				.result(Arrays.asList(resultado))
				.build();
		
		return ResponseEntity.status(status).body(fnaResponse);
	}

	/**
	  * {@inheritDoc}
	  */
	@GetMapping("/{cod}")
	public ResponseEntity<FnaResponse<FnaDtoResult>> buscarPorCodigo(
						@PathVariable("cod") String codigoDANE) {

		MunicipioDTO resultado = municipioServicio.buscarPorCodigoDane(codigoDANE);
		
		HttpStatus status = HttpStatus.OK;
		
		FnaResponse<FnaDtoResult> fnaResponse = 
				FnaResponse.builder()
				.code(status.name())
				.status(status.value())
				.timestamp(Calendar.getInstance().getTime())
				.message(status.name())
				.result(Arrays.asList(resultado))
				.build();
		
		return ResponseEntity.status(status).body(fnaResponse);
	}

	/**
	  * {@inheritDoc}
	  */
	@Override
	public ResponseEntity<FnaResponse<FnaDtoResult>> crear(MunicipioDTO municipio) {
		throw new UnsupportedOperationException("Método no implementado");
	}

	/**
	  * {@inheritDoc}
	  */
	@Override
	public ResponseEntity<FnaResponse<FnaDtoResult>> editar(MunicipioDTO municipio) {
		throw new UnsupportedOperationException("Método no implementado");
	}

	/**
	  * {@inheritDoc}
	  */
	@Override
	public ResponseEntity<FnaResponse<FnaDtoResult>> eliminar(String codMunicipio) {
		throw new UnsupportedOperationException("Método no implementado");
	}
	
}
