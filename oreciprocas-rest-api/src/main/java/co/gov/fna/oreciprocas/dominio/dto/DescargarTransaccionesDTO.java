/*
 * Copyright 2019. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.dominio.dto;

import co.gov.fna.core.common.generic.rest.FnaDtoResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Esteban Salinas
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DescargarTransaccionesDTO implements FnaDtoResult {

    private String mensaje;
    private byte[] fileTransacciones;
}
