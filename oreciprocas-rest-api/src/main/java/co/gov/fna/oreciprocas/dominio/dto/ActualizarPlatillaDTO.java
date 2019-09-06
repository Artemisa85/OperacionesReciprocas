/*
 * Copyright 2019. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.dominio.dto;

import co.gov.fna.core.common.generic.rest.FnaDtoResult;
import co.gov.fna.oreciprocas.dominio.modelo.Plantilla;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Esteban Salinas
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActualizarPlatillaDTO implements FnaDtoResult{

    private String mensaje;
    private Plantilla plantillaActualizada;

}
