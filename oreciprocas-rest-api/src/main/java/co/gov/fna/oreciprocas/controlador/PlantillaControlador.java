/*
 * Copyright 2019. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.controlador;

import co.gov.fna.core.common.generic.rest.FnaDtoResult;
import co.gov.fna.core.common.generic.rest.FnaResponse;
import co.gov.fna.oreciprocas.dominio.modelo.Plantilla;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author Esteban Salinas
 */
public interface PlantillaControlador {
    
    /**
     * Obtener todas las plantillas registradas en db
     * @return las plantillas encontradas
     */
    ResponseEntity<FnaResponse<FnaDtoResult>> consultarPlantillas();
    
    /**
     * Actualizar una plantilla
     * @param plantillaModelo la plantilla a actualizar
     * @return la plantilla actualizada
     */
    ResponseEntity<FnaResponse<FnaDtoResult>> actualizarPlantilla(Plantilla plantillaModelo);
}
