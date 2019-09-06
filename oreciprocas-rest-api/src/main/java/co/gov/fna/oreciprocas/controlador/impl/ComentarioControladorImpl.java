/*
 * Copyright 2019. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.controlador.impl;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import co.gov.fna.core.common.generic.rest.FnaDtoResult;
import co.gov.fna.core.common.generic.rest.FnaResponse;
import co.gov.fna.oreciprocas.controlador.ComentarioControlador;
import co.gov.fna.oreciprocas.dominio.dto.CargaAdjuntoRespuestaDTO;
import co.gov.fna.oreciprocas.dominio.dto.CargaAdjuntoSolicitudDTO;
import co.gov.fna.oreciprocas.dominio.dto.ComentarioDTO;
import co.gov.fna.oreciprocas.dominio.dto.ComentarioSolicitudDTO;
import co.gov.fna.oreciprocas.servicio.AdjuntoServicio;
import co.gov.fna.oreciprocas.servicio.ComentarioServicio;

/**
 * Implementaci&oacute;n por defecto de {@link ComentarioControlador}
 *
 * @author Zamir Garc&iacute;a Romero - zagarcia@ayesa.com
 * @version 1.0
 *
 */
@RestController
@RequestMapping("/api/comentarios")
public class ComentarioControladorImpl implements ComentarioControlador {
	
	@Autowired
	private ComentarioServicio comentarioServicio;
	
	@Autowired
	private AdjuntoServicio adjuntoServicio;
	
	@Autowired
	private HttpServletRequest request;

	/**
	  * {@inheritDoc}
	  */
	public ResponseEntity<FnaResponse<FnaDtoResult>> buscarPorId(BigInteger id) {
		throw new UnsupportedOperationException("Operación no implementada");
	}

	/**
	  * {@inheritDoc}
	  */
	@PreAuthorize("hasAnyRole('FNA_ADMIN', 'EXTERNAL_USER')")
	@PostMapping
	public ResponseEntity<FnaResponse<FnaDtoResult>> crearComentario(
			@RequestBody ComentarioSolicitudDTO solicitud,
			Authentication authentication) {
		
		ComentarioDTO salida = comentarioServicio.crearComentario(solicitud, authentication);
		
		FnaResponse<FnaDtoResult> fnaResponse = 
				FnaResponse.builder()
				.code(HttpStatus.CREATED.name())
				.status(HttpStatus.CREATED.value())
				.timestamp(Calendar.getInstance().getTime())
				.message(HttpStatus.CREATED.name()) 
				.result(Arrays.asList(salida))
				.build();
		
		return new ResponseEntity<FnaResponse<FnaDtoResult>>(fnaResponse, HttpStatus.CREATED);
	}

	/**
	  * {@inheritDoc}
	  */
	@PreAuthorize("hasAnyRole('FNA_ADMIN', 'EXTERNAL_USER')")
	@PostMapping("{idComentario}/cargar-archivos")
	public ResponseEntity<FnaResponse<FnaDtoResult>> cargarAdjuntos(
			@RequestParam("archivos") MultipartFile[] archivos, 
			@PathVariable(required = true) BigInteger idComentario) {
		
		CargaAdjuntoSolicitudDTO solicitud = CargaAdjuntoSolicitudDTO.builder()
				.idComentario(idComentario)
				.archivos(archivos)
				.contextoSolicitado(getServerRequest())
				.build();
		
		CargaAdjuntoRespuestaDTO respuesta = adjuntoServicio.guardarAdjuntos(solicitud);
		
		FnaResponse<FnaDtoResult> fnaResponse = 
				FnaResponse.builder()
				.code(HttpStatus.CREATED.name())
				.status(HttpStatus.CREATED.value())
				.timestamp(Calendar.getInstance().getTime())
				.message(HttpStatus.CREATED.name()) 
				.result(Arrays.asList(respuesta))
				.build();
		
		return new ResponseEntity<FnaResponse<FnaDtoResult>>(fnaResponse, HttpStatus.CREATED);
	}
	
	
	/**
	 * Retorna el contexto en donde se realiz&oacute; la petici&oacute;n
	 * @return
	 */
	private String getServerRequest(){
		StringBuilder sb = new StringBuilder();
		sb.append(request.getScheme());
		sb.append("://");
		sb.append(request.getServerName());
		if (!"https".equalsIgnoreCase(request.getScheme()) 
				&& !"443".equals( String.valueOf(request.getServerPort()) )){
			sb.append(":");
			sb.append(request.getServerPort());
		}
		sb.append(request.getContextPath());
		return sb.toString();
	}

}
