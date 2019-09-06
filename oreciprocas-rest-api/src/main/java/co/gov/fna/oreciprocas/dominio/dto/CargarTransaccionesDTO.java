/*
 * Copyright 2019. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.dominio.dto;

import co.gov.fna.core.common.generic.rest.FnaDtoResult;
import co.gov.fna.oreciprocas.controlador.impl.TransaccionControladorImpl;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para peticiones a {@link TransaccionControladorImpl}.cargarTransacciones
 * 
 * @author Esteban Salinas
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CargarTransaccionesDTO implements FnaDtoResult {

    private String mensaje;
    private byte[] excelFile;
    private byte[] errorFile;
    private int transaccionesCargadas;

}
