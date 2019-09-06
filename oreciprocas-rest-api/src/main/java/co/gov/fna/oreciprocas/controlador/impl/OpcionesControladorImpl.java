/*
 * Copyright 2019. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.controlador.impl;

import static co.gov.fna.oreciprocas.constantes.OReciprocasConstantes.DB_DOMINIO_ARCHIVOS_PERMITIDOS;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.gov.fna.core.common.generic.rest.FnaDtoResult;
import co.gov.fna.core.common.generic.rest.FnaResponse;
import co.gov.fna.oreciprocas.controlador.OpcionesControlador;
import co.gov.fna.oreciprocas.dominio.dto.TipoArchivoPermitido;
import co.gov.fna.oreciprocas.dominio.modelo.ConfiguracionRegistro;
import co.gov.fna.oreciprocas.servicio.ConfiguracionServicio;

/**
 * Implementaci&oacute;n por defecto de {@link OpcionesControlador}
 *
 * @author Zamir Garc&iacute;a Romero - zagarcia@ayesa.com
 * @version 1.0
 *
 */
@RestController
@RequestMapping("/api/opciones")
public class OpcionesControladorImpl implements OpcionesControlador {
	
	
	@Autowired
	private ConfiguracionServicio configuracionServicio;
	

	/**
	  * {@inheritDoc}
	  */
	@PreAuthorize("hasAnyRole('FNA_ADMIN', 'CGN_USER', 'EXTERNAL_USER')")
	@GetMapping(path = "/tipo-archivos-permitidos")
	public ResponseEntity<FnaResponse<FnaDtoResult>> obtenerTipoArchivosPermitidos() {
		
		Collection<ConfiguracionRegistro> dbPermitidos = 
				configuracionServicio.obtenerConfiguracionDominio(DB_DOMINIO_ARCHIVOS_PERMITIDOS);
				
		Collection<TipoArchivoPermitido> archivosPermitidos = mapearArchivos(dbPermitidos);
		
		FnaResponse<FnaDtoResult> fnaResponse = 
				FnaResponse.builder()
				.code(HttpStatus.OK.name())
				.status(HttpStatus.OK.value())
				.timestamp(Calendar.getInstance().getTime())
				.message("OK") 
				.result(archivosPermitidos)
				.build();
		
		return ResponseEntity.status(HttpStatus.OK).body(fnaResponse);
	}


	/**
	 * Realiza el mapeo de la configuración a los tipos de archivo permitidos.
	 * @param dbPermitidos
	 * @return
	 */
	private Collection<TipoArchivoPermitido> mapearArchivos(
									Collection<ConfiguracionRegistro> dbPermitidos) {
		
		Collection<TipoArchivoPermitido> permitidos = new ArrayList<>();
		
		dbPermitidos.forEach(archivo -> {
			TipoArchivoPermitido permitido = TipoArchivoPermitido.builder()
					.extension(archivo.getCodigo())
					.tipoMime(archivo.getValor())
					.build();
			permitidos.add(permitido);
		});
		
		return permitidos;
	}

}
