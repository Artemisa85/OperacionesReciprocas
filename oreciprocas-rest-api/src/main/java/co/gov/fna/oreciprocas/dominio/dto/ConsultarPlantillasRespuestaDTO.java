/*
 * Copyright 2019. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.dominio.dto;

import co.gov.fna.core.common.generic.rest.FnaDtoResult;
import co.gov.fna.oreciprocas.dominio.modelo.Plantilla;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Contiene la respuesta para una consulta de plantillas.
 * 
 * @author Esteban Salinas
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConsultarPlantillasRespuestaDTO implements FnaDtoResult {
    
    /**
     * Mensaje de respuesta
     */
    private String mensaje;
    
    /**
     * Plantillas resultado.
     */
    private List<Plantilla> plantillas;
    
}
