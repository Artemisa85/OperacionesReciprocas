/*
 * Copyright 2019. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.servicio;

import co.gov.fna.oreciprocas.dominio.dto.ActualizarPlatillaDTO;
import co.gov.fna.oreciprocas.dominio.dto.ConsultarPlantillasRespuestaDTO;
import co.gov.fna.oreciprocas.dominio.modelo.Plantilla;
import java.util.Map;

/**
 * Servicio para gestionar plantillas HTML en el sistema.
 * <br/>
 * <br/>
 * Las plantillas se usan para env&iacute;o de correo o mostrar contenido HTML
 * 
 * 
 * @author Zamir Garc&iacute;a Romero - zagarcia@ayesa.com
 * @version 1.0
 *
 */
public interface PlantillaServicio {
	
	/**
	 * Procesa una plantilla con las variables suministradas.
	 * @param variables Variables requeridas por la plantilla.
	 * @param nombrePlantilla Nombre de la plantilla deseada.
	 * @return Cadena con el resultado del procesamiento de la plantilla
	 */
	String procesarPlantilla(Map<String,Object> variables, String nombrePlantilla);

        /**
         * Consultar las plantillas del sistema
         * @return una lista con todas las plantillas encontradas
         */
        ConsultarPlantillasRespuestaDTO consultarPlantillas();

        /**
         * Actualiza el contenido de la plantilla o su actividad
         * @param plantillaModelo, la informacion de la plantilla a actualizar
         * @return un objeto con un mensaje de confirmacion de actualizacion y con el modelo de plantilla que quedo 
         */
        ActualizarPlatillaDTO actualizarPlantilla(Plantilla plantillaModelo);
	

}
