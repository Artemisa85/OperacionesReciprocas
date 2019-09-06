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
import co.gov.fna.oreciprocas.controlador.CambiarClaveControlador;
import co.gov.fna.oreciprocas.dominio.dto.CambiarClaveRespuestaDTO;
import co.gov.fna.oreciprocas.dominio.dto.CambiarClaveSolicitudDTO;
import co.gov.fna.oreciprocas.servicio.CambiarClaveServicio;

/**
 * Implementaci&oacute;n por defecto de {@link CambiarClaveControlador}
 *
 * @author Zamir Garc&iacute;a Romero - zagarcia@ayesa.com
 * @version 1.0
 *
 */
@RestController
@RequestMapping("/publico/cambiar-clave")
public class CambiarClaveControladorImpl implements CambiarClaveControlador {
	
	@Autowired
	private CambiarClaveServicio servicioCambiarClave;
	
	/**
	 * {@inheritDoc}
	 */
	@PostMapping
	public ResponseEntity<FnaResponse<FnaDtoResult>> registroUsuario(
							@RequestBody @Valid CambiarClaveSolicitudDTO entrada) {
		
		CambiarClaveRespuestaDTO respuesta = servicioCambiarClave.cambiarClave(entrada);
		
		FnaResponse<FnaDtoResult> fnaResponse = 
				FnaResponse.builder()
				.code(HttpStatus.OK.name())
				.status(HttpStatus.OK.value())
				.timestamp(Calendar.getInstance().getTime())
				.message(respuesta.getMensaje())
				.result(Arrays.asList(respuesta))
				.build();
		return ResponseEntity.ok(fnaResponse);
	}
	
	

}
