/*
 * Copyright 2019. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.controlador.impl;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Calendar;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import co.gov.fna.core.common.generic.rest.FnaDtoResult;
import co.gov.fna.core.common.generic.rest.FnaResponse;
import co.gov.fna.oreciprocas.controlador.TransaccionControlador;
import co.gov.fna.oreciprocas.dominio.dto.ComentarioResultDTO;
import co.gov.fna.oreciprocas.dominio.dto.ConsultarOperacionesRespuestaDTO;
import co.gov.fna.oreciprocas.dominio.dto.ConsultarOperacionesSolicitudDTO;
import co.gov.fna.oreciprocas.dominio.dto.CargarTransaccionesDTO;
import co.gov.fna.oreciprocas.dominio.dto.DescargarTransaccionesDTO;
import co.gov.fna.oreciprocas.dominio.dto.FiltrosConsultaRespuestaDTO;
import co.gov.fna.oreciprocas.dominio.dto.TransaccionDetalleDTO;
import co.gov.fna.oreciprocas.servicio.ComentarioServicio;
import co.gov.fna.oreciprocas.servicio.TransaccionServicio;

/**
 * Implementaci&oacute;n por defecto de {@link TransaccionControlador}
 *
 * @author Zamir Garc&iacute;a Romero - zagarcia@ayesa.com
 * @version 1.0
 *
 */
@RestController
@RequestMapping("/api/transacciones")
public class TransaccionControladorImpl implements TransaccionControlador {
	
	@Autowired
	private TransaccionServicio transaccionServicio;
	
	@Autowired
	private ComentarioServicio comentarioServicio;
	
	@Autowired
	private HttpServletRequest request;
	
	
	/**
	  * {@inheritDoc}
	  */
	@PreAuthorize("hasAnyRole('FNA_ADMIN', 'CGN_USER', 'EXTERNAL_USER')")
	@PostMapping
	public ResponseEntity<FnaResponse<FnaDtoResult>> consultarTransacciones(
			@RequestBody ConsultarOperacionesSolicitudDTO solicitud,
			Authentication auth, Pageable pageable) {
	
		ConsultarOperacionesRespuestaDTO respuesta = 
				transaccionServicio.consultarTransacciones(solicitud, auth, pageable);
		
		FnaResponse<FnaDtoResult> fnaResponse = 
				FnaResponse.builder()
				.code(HttpStatus.OK.name())
				.status(HttpStatus.OK.value())
				.timestamp(Calendar.getInstance().getTime())
				.message(respuesta.getMensaje()) 
				.result(Arrays.asList(respuesta))
				.build();
		
		return new ResponseEntity<FnaResponse<FnaDtoResult>>(fnaResponse, HttpStatus.OK);
	}


	/**
	  * {@inheritDoc}
	  */
	@PreAuthorize("hasAnyRole('FNA_ADMIN', 'CGN_USER', 'EXTERNAL_USER')")
	@GetMapping("/{id}")
	public ResponseEntity<FnaResponse<FnaDtoResult>> buscarPorId(@PathVariable BigInteger id) {
		
		TransaccionDetalleDTO detalle = transaccionServicio.getDetalleTransaccion(id);
		
		FnaResponse<FnaDtoResult> fnaResponse = 
				FnaResponse.builder()
				.code(HttpStatus.OK.name())
				.status(HttpStatus.OK.value())
				.timestamp(Calendar.getInstance().getTime())
				.message(HttpStatus.OK.name()) 
				.result(Arrays.asList(detalle))
				.build();
		
		return new ResponseEntity<FnaResponse<FnaDtoResult>>(fnaResponse, HttpStatus.OK);
	}
	
	/**
	  * {@inheritDoc}
	  */
	@PreAuthorize("hasAnyRole('FNA_ADMIN', 'CGN_USER', 'EXTERNAL_USER')")
	@GetMapping("/{id}/comentarios")
	public ResponseEntity<FnaResponse<FnaDtoResult>> buscarComentariosTransaccion(@PathVariable BigInteger id) {
		
		ComentarioResultDTO comentarios = 
				comentarioServicio.buscarPorTransaccion(id, getServerContext());
		
		FnaResponse<FnaDtoResult> fnaResponse = 
				FnaResponse.builder()
				.code(HttpStatus.OK.name())
				.status(HttpStatus.OK.value())
				.timestamp(Calendar.getInstance().getTime())
				.message(HttpStatus.OK.name()) 
				.result(Arrays.asList(comentarios))
				.build();
		
		return new ResponseEntity<FnaResponse<FnaDtoResult>>(fnaResponse, HttpStatus.OK);
	}
	
	/**
	 * Retorna el contexto en donde se realiz&oacute; la petici&oacute;n
	 * @return
	 */
	private String getServerContext(){
		StringBuilder sb = new StringBuilder();
		sb.append(request.getContextPath());
		return sb.toString();
	}

	@PreAuthorize("hasAnyRole('FNA_ADMIN')")
	@RequestMapping(value = "/cargar")
	@PostMapping
	@Override
	public ResponseEntity<FnaResponse<FnaDtoResult>> cargarTransacciones(@RequestBody CargarTransaccionesDTO cargarTransaccionesDTO) {
		CargarTransaccionesDTO respuesta
		= transaccionServicio.cargarTransacciones(cargarTransaccionesDTO);

		FnaResponse<FnaDtoResult> fnaResponse
		= FnaResponse.builder()
		.code(HttpStatus.OK.name())
		.status(HttpStatus.OK.value())
		.timestamp(Calendar.getInstance().getTime())
		.message(respuesta.getMensaje())
		.result(Arrays.asList(respuesta))
		.build();

		return new ResponseEntity<>(fnaResponse, HttpStatus.OK);
	}


	@PreAuthorize("hasAnyRole('FNA_ADMIN', 'EXTERNAL_USER')")
	@RequestMapping(value = "/descargar")
	@PostMapping
	public ResponseEntity<FnaResponse<FnaDtoResult>> descargarTransacciones(
			@RequestBody ConsultarOperacionesSolicitudDTO solicitud) {
		DescargarTransaccionesDTO respuesta
			= transaccionServicio.descargarTransacciones(solicitud);

		FnaResponse<FnaDtoResult> fnaResponse = FnaResponse.builder()
				.code(HttpStatus.OK.name())
				.status(HttpStatus.OK.value())
				.timestamp(Calendar.getInstance().getTime())
				.message(respuesta.getMensaje())
				.result(Arrays.asList(respuesta))
				.build();

		return new ResponseEntity<>(fnaResponse, HttpStatus.OK);
	}
        
    /**
   	 * {@inheritDoc}
   	 */
   	@PreAuthorize("hasAnyRole('FNA_ADMIN', 'CGN_USER', 'EXTERNAL_USER')")
   	@GetMapping(path = "/filtros-consulta")
   	public ResponseEntity<FnaResponse<FnaDtoResult>> obtenerFiltrosConsulta() {
   		
   		FiltrosConsultaRespuestaDTO filtros = transaccionServicio.obtenerFiltrosConsulta();
   		
   		FnaResponse<FnaDtoResult> fnaResponse = 
   				FnaResponse.builder()
   				.code(HttpStatus.OK.name())
   				.status(HttpStatus.OK.value())
   				.timestamp(Calendar.getInstance().getTime())
   				.message("OK") 
   				.result(Arrays.asList(filtros))
   				.build();
   		
   		return ResponseEntity.status(HttpStatus.OK).body(fnaResponse);
   	}
	
}
