/*
 * Copyright 2018. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.controlador.impl;

import java.util.Arrays;
import java.util.Calendar;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.gov.fna.core.common.generic.rest.FnaDtoResult;
import co.gov.fna.core.common.generic.rest.FnaResponse;
import co.gov.fna.oreciprocas.controlador.RegistrarUsuarioControlador;
import co.gov.fna.oreciprocas.dominio.dto.RegistroUsuarioRespuestaDTO;
import co.gov.fna.oreciprocas.dominio.dto.RegistroUsuarioSolicitudDTO;
import co.gov.fna.oreciprocas.servicio.RegistroServicio;


/**
 * Implementaci&oacute;n por defecto de {@link RegistrarUsuarioControlador}
 *
 * @author Zamir Garc&iacute;a Romero - zagarcia@ayesa.com
 * @version 1.0
 *
 */
@RestController
@RequestMapping("/publico/registro")
public class RegistrarUsuarioControladorImpl implements RegistrarUsuarioControlador { 
	
	@Autowired
	private RegistroServicio servicioRegistro;
	
	/**
	 * {@inheritDoc}
	 */
	@PostMapping()
	public ResponseEntity<FnaResponse<FnaDtoResult>> registroUsuario(
						@Valid @RequestBody RegistroUsuarioSolicitudDTO entrada) {
		
		RegistroUsuarioRespuestaDTO respuesta = servicioRegistro.registrar(entrada);
		
		FnaResponse<FnaDtoResult> fnaResponse = 
				FnaResponse.builder()
				.code(HttpStatus.CREATED.name())
				.status(HttpStatus.CREATED.value())
				.timestamp(Calendar.getInstance().getTime())
				.message(respuesta.getMensajeRespuesta())
				.result(Arrays.asList(respuesta))
				.build();
		
		return ResponseEntity.status(HttpStatus.CREATED).body(fnaResponse);
	}

}
