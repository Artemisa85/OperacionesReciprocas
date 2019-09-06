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
import co.gov.fna.oreciprocas.controlador.DepartamentoControlador;
import co.gov.fna.oreciprocas.dominio.dto.DepartamentoResultadoDTO;
import co.gov.fna.oreciprocas.dominio.dto.MunicipioResultadoDTO;
import co.gov.fna.oreciprocas.servicio.DepartamentoServicio;
import co.gov.fna.oreciprocas.servicio.MunicipioServicio;

/**
 * Controlador que implementa el contrato de la clase {@link DepartamentoControlador}.
 *
 * @author <a href="tecnico_integracion5@utayecisa.com"> Guillermo Garc&iacute;a</a>
 * @version 1.0
 */
@RestController
@RequestMapping("/api/departamentos")
public class DepartamentoControladorImpl implements DepartamentoControlador {

	@Autowired
	private DepartamentoServicio departamentoServicio;
	
	@Autowired
	private MunicipioServicio municipioServicio;

	/**
	  * {@inheritDoc}
	  */
	@GetMapping
	public ResponseEntity<FnaResponse<FnaDtoResult>> recuperarTodos(Pageable pageable) {
		DepartamentoResultadoDTO resultado = departamentoServicio.recuperarTodos(pageable);
		
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
	@GetMapping("/{cod}/municipios")
	public ResponseEntity<FnaResponse<FnaDtoResult>> buscarMunicipiosDepartamento(
			@PathVariable("cod") String codDepartamento) {
		MunicipioResultadoDTO resultado = 
				municipioServicio.buscarPorCodigoDepartamento(codDepartamento);
		
		HttpStatus status = HttpStatus.OK;
		
		FnaResponse<FnaDtoResult> fnaResponse = 
				FnaResponse.builder()
				.code(status.name())
				.status(status.value())
				.timestamp(Calendar.getInstance().getTime())
				.message("Departamento: " + codDepartamento)
				.result(Arrays.asList(resultado))
				.build();
		
		return ResponseEntity.status(status).body(fnaResponse);
	}
	
}