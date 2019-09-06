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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import co.gov.fna.core.common.generic.rest.FnaDtoResult;
import co.gov.fna.core.common.generic.rest.FnaResponse;
import co.gov.fna.oreciprocas.controlador.RecuperarClaveControlador;
import co.gov.fna.oreciprocas.dominio.dto.RecuperarClaveSolicitudDTO;
import co.gov.fna.oreciprocas.dominio.dto.RecuperarClaveRespuestaDTO;
import co.gov.fna.oreciprocas.servicio.RecuperarClaveServicio;

/**
 * Implementaci&oacute;n por defecto de {@link RecuperarClaveControlador}
 *
 * @author Zamir Garc&iacute;a Romero - zagarcia@ayesa.com
 * @version 1.0
 *
 */
@RestController
@RequestMapping("/publico/recuperar-clave")
public class RecuperarClaveControladorImpl implements RecuperarClaveControlador {
	
	@Autowired
	private RecuperarClaveServicio servicioRecuperarClave;
	
	/**
	 * {@inheritDoc}
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<FnaResponse<FnaDtoResult>> registroUsuario(
							@RequestBody @Valid RecuperarClaveSolicitudDTO solicitud) {
		
		RecuperarClaveRespuestaDTO respuesta = servicioRecuperarClave.recuperarClave(solicitud);
		
		FnaResponse<FnaDtoResult> fnaResponse = 
				FnaResponse.builder()
				.code(HttpStatus.OK.name())
				.status(HttpStatus.OK.value())
				.timestamp(Calendar.getInstance().getTime())
				.message(respuesta.getMensaje()) 
				.result(Arrays.asList(respuesta))
				.build();
		
		return ResponseEntity.status(HttpStatus.OK).body(fnaResponse);
		
	}

}
