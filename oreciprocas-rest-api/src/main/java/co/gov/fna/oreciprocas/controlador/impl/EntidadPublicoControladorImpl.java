/*
 * Copyright 2018. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.controlador.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.gov.fna.oreciprocas.dominio.dto.EntidadDTO;
import co.gov.fna.oreciprocas.dominio.modelo.Entidad;
import co.gov.fna.oreciprocas.exception.EntidadNoEncontradaException;
import co.gov.fna.oreciprocas.servicio.EntidadServicio;


/**
 * Controlador REST para entidades tipo {@link Entidad}. Este controlador es de acceso
 * p&uacute;blico y no expone informaci&oacute;n sensible.
 *
 * @author Zamir Garc&iacute;a Romero - zagarcia@ayesa.com
 * @version 1.0
 *
 */
@RestController
@RequestMapping("/publico/entidades")
public class EntidadPublicoControladorImpl {

	@Autowired
	EntidadServicio servicioEntidad;
	
	/**
	 * Busca una entidad por NIT y por Id CGN
	 * @param nit N&uacute;mero de identificación tributaria
	 * @param idCGN Identificador asignado por la Contadur&iacute;a General de la Naci&oacute;n.
	 * @return Entidad encontrada
	 */
	@GetMapping
	@ExceptionHandler(EntidadNoEncontradaException.class)
	public ResponseEntity<EntidadDTO> buscarEntidad(@RequestParam(value = "nit") String nit,
												@RequestParam(value = "idCGN") String idCGN) {
		Optional<EntidadDTO> entidad = servicioEntidad.buscarEntidad(nit, idCGN);
		return ResponseEntity.ok(entidad.get());
	}
	
}