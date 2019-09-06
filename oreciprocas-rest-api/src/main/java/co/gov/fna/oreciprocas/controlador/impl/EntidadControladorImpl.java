/*
 * Copyright 2019. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.controlador.impl;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.gov.fna.core.common.exception.ErrorDetail;
import co.gov.fna.core.common.generic.rest.FnaDtoResult;
import co.gov.fna.core.common.generic.rest.FnaResponse;
import co.gov.fna.oreciprocas.controlador.EntidadControlador;
import co.gov.fna.oreciprocas.dominio.dto.BusquedaEntidadDTO;
import co.gov.fna.oreciprocas.dominio.dto.EntidadDTO;
import co.gov.fna.oreciprocas.dominio.dto.EntidadResultadoDTO;
import co.gov.fna.oreciprocas.exception.EntidadNoEncontradaException;
import co.gov.fna.oreciprocas.servicio.EntidadServicio;

/**
 * Implementaci&oacute;n por defecto de {@link EntidadControlador}
 *
 * @author Zamir Garc&iacute;a Romero - zagarcia@ayesa.com
 * @version 1.0
 *
 */
@RestController
@RequestMapping("/api/entidades")
public class EntidadControladorImpl implements EntidadControlador {
	
	@Autowired
	private EntidadServicio entidadServicio;
	

	/**
	  * {@inheritDoc}
	  */
	@PreAuthorize("hasAnyRole('FNA_ADMIN')")
	@GetMapping
	public ResponseEntity<FnaResponse<FnaDtoResult>> recuperarEntidades(Pageable pageable) {
		
		EntidadResultadoDTO resultado = entidadServicio.recuperarEntidades(pageable);
		
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
	public ResponseEntity<FnaResponse<FnaDtoResult>> recuperarEntidadesActivas(Pageable pageable) {
		throw new UnsupportedOperationException("Método no implementado");
	}

	/**
	  * {@inheritDoc}
	  */
	@PreAuthorize("hasAnyRole('FNA_ADMIN')")
	@PostMapping("/buscar")
	public ResponseEntity<FnaResponse<FnaDtoResult>> buscar(
			@RequestBody(required = false) BusquedaEntidadDTO solicitud, 
			Pageable pageable) {
		
		EntidadResultadoDTO resultado = entidadServicio.buscar(solicitud, pageable);
		
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
	public ResponseEntity<FnaResponse<FnaDtoResult>> buscarPorIdCgn(@PathVariable("cod") String idCgn) {
		Optional<EntidadDTO> entidad = entidadServicio.buscarEntidadPorIdCgn(idCgn);
		if (!entidad.isPresent()) {
			lanzarExcepcionEntidadNoExiste();
		}
		
		HttpStatus status = HttpStatus.OK;
		
		FnaResponse<FnaDtoResult> fnaResponse = 
				FnaResponse.builder()
				.code(status.name())
				.status(status.value())
				.timestamp(Calendar.getInstance().getTime())
				.message(status.name())
				.result(Arrays.asList(entidad.get()))
				.build();
		
		return ResponseEntity.status(status).body(fnaResponse);
	}

	/**
	  * {@inheritDoc}
	  */
	@PreAuthorize("hasAnyRole('FNA_ADMIN')")
	@PostMapping
	public ResponseEntity<FnaResponse<FnaDtoResult>> crearEntidad(
			@Valid @RequestBody EntidadDTO entidad, Authentication auth) {
		EntidadDTO resultado = entidadServicio.crear(entidad, auth);
			
		HttpStatus status = HttpStatus.CREATED;
		
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
	@PreAuthorize("hasAnyRole('FNA_ADMIN', 'EXTERNAL_USER')")
	@PutMapping
	public ResponseEntity<FnaResponse<FnaDtoResult>> editarEntidad(
			@Valid @RequestBody EntidadDTO entidad, 
			Authentication auth) {
		EntidadDTO resultado = entidadServicio.editar(entidad, auth);
		
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
	public ResponseEntity<FnaResponse<FnaDtoResult>> eliminarEntidad(Integer id, 
			Authentication auth) {
		throw new UnsupportedOperationException("Método no implementado");
	}
	
	
	/**
	 * Lanza una excepci&oacute;n indicando que la entidad no existe
	 */
	private void lanzarExcepcionEntidadNoExiste() {
		ErrorDetail errorDetail = new ErrorDetail();
		errorDetail.setCode("ENTIDAD_NO_EXISTE");
		errorDetail.setMessage("La entidad solicitada no existe"); 
		errorDetail.setTimestamp(new Date());
		throw new EntidadNoEncontradaException(errorDetail, HttpStatus.NOT_FOUND);
	}

}
