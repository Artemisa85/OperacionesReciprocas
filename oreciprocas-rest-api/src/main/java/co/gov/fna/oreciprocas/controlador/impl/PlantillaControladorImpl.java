/*
 * Copyright 2019. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.controlador.impl;

import co.gov.fna.core.common.generic.rest.FnaDtoResult;
import co.gov.fna.core.common.generic.rest.FnaResponse;
import co.gov.fna.oreciprocas.controlador.PlantillaControlador;
import co.gov.fna.oreciprocas.dominio.dto.ActualizarPlatillaDTO;
import co.gov.fna.oreciprocas.dominio.dto.ConsultarPlantillasRespuestaDTO;
import co.gov.fna.oreciprocas.dominio.modelo.Plantilla;
import co.gov.fna.oreciprocas.servicio.PlantillaServicio;
import java.util.Arrays;
import java.util.Calendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Esteban Salinas
 */
@RestController
@RequestMapping("/api/plantillas")
public class PlantillaControladorImpl implements PlantillaControlador {

    @Autowired
    PlantillaServicio plantillaServicio;

    @PreAuthorize("hasAnyRole('FNA_ADMIN')")
    @GetMapping
    @Override
    public ResponseEntity<FnaResponse<FnaDtoResult>> consultarPlantillas() {
        ConsultarPlantillasRespuestaDTO plantillas
                = plantillaServicio.consultarPlantillas();

        FnaResponse<FnaDtoResult> fnaResponse
                = FnaResponse.builder()
                        .code(HttpStatus.OK.name())
                        .status(HttpStatus.OK.value())
                        .timestamp(Calendar.getInstance().getTime())
                        .message(HttpStatus.OK.name())
                        .result(Arrays.asList(plantillas))
                        .build();

        return new ResponseEntity<>(fnaResponse, HttpStatus.OK);
    }
    
    @PreAuthorize("hasAnyRole('FNA_ADMIN')")
    @PutMapping
    @Override
    public ResponseEntity<FnaResponse<FnaDtoResult>> actualizarPlantilla(@RequestBody Plantilla plantillaModelo) {
        ActualizarPlatillaDTO resultPlantilla
                = plantillaServicio.actualizarPlantilla(plantillaModelo);

        FnaResponse<FnaDtoResult> fnaResponse
                = FnaResponse.builder()
                        .code(HttpStatus.OK.name())
                        .status(HttpStatus.OK.value())
                        .timestamp(Calendar.getInstance().getTime())
                        .message(HttpStatus.OK.name())
                        .result(Arrays.asList(resultPlantilla))
                        .build();

        return new ResponseEntity<>(fnaResponse, HttpStatus.OK);
    }

}
