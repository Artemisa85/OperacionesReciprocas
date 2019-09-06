/*
 * Copyright 2019. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.controlador.impl;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.gov.fna.oreciprocas.controlador.AdjuntoControlador;
import co.gov.fna.oreciprocas.dominio.dto.AdjuntoDescargaDTO;
import co.gov.fna.oreciprocas.servicio.AdjuntoServicio;

/**
 * Implementaci&oacute;n por defecto de {@link AdjuntoControlador}
 *
 * @author Zamir Garc&iacute;a Romero - zagarcia@ayesa.com
 * @version 1.0
 *
 */
@RequestMapping("/api/adjuntos")
@RestController
public class AdjuntoControladorImpl implements AdjuntoControlador {
	
	@Autowired
	private AdjuntoServicio adjuntoServicio;

	/**
	  * {@inheritDoc}
	  */
	@PreAuthorize("hasAnyRole('FNA_ADMIN', 'CGN_USER', 'EXTERNAL_USER')")
	@GetMapping("/{id}")
	public ResponseEntity<Resource> descargarAdjunto(@PathVariable("id") BigInteger idAdjunto) {
		AdjuntoDescargaDTO descarga = adjuntoServicio.obtenerAdjunto(idAdjunto); 
		return ResponseEntity.ok()
				.contentType( MediaType.valueOf(descarga.getTipoMime()) )
				.headers(descarga.getEncabezadosHttp())
				.body(descarga.getArchivo());
	}

}
