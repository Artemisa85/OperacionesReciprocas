/*
 * Copyright 2019. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.servicio.impl;

import java.util.Date;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.context.IContext;
import co.gov.fna.core.common.exception.ErrorDetail;
import co.gov.fna.core.common.exception.FnaException;
import co.gov.fna.oreciprocas.dominio.dto.ActualizarPlatillaDTO;
import co.gov.fna.oreciprocas.dominio.dto.ConsultarPlantillasRespuestaDTO;
import co.gov.fna.oreciprocas.dominio.modelo.Plantilla;
import co.gov.fna.oreciprocas.repositorio.PlantillaRepositorio;
import co.gov.fna.oreciprocas.servicio.PlantillaServicio;
import com.google.common.collect.Lists;

/**
 * Obtiene y procesa las plantillas almacenadas en base de datos.
 *
 * @author Zamir Garc&iacute;a Romero - zagarcia@ayesa.com
 * @version 1.0
 *
 */
@Service
public class PlantillaServicioImpl implements PlantillaServicio {
	
	@Autowired
	private TemplateEngine templateEngine;
	
	@Autowired
	private PlantillaRepositorio plantillaRepositorio;
	

	/**
	  * {@inheritDoc}
	  */
        @Override
	public String procesarPlantilla(Map<String, Object> variables, String nombrePlantilla) {
		IContext contexto = construirContexto(variables);
		Optional<Plantilla> plantilla = 
				plantillaRepositorio.findByNombreAndActivo(nombrePlantilla, Boolean.TRUE);
		
		if ( !plantilla.isPresent()) {
			lanzarExcepcionPlantillaNoEncontrada(nombrePlantilla);
		}
		
		return templateEngine.process(plantilla.get().getContenido() , contexto );
	}

	/**
	 * Construye el contexto Thymeleaf desde las variables
	 * @param variables Contiene los nombres y valores de las variables del contexto
	 * @return Contexto compatible con Thymeleaf
	 */
	private IContext construirContexto(Map<String, Object> variables) {
		Context contexto = new Context();
		variables.forEach((nombre, valor) -> {
			contexto.setVariable(nombre, valor);
		});
		return contexto;
	}
	
	
	/**
     * Lanza excepci&oacute;n para plantillas no encontradas.
     */
        private void lanzarExcepcionPlantillaNoEncontrada(String nombrePlantilla) {
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setCode("PLANTILLA_NO_ENCONTRADA");
        errorDetail.setMessage(String.format("No se encontró la plantilla %s", nombrePlantilla));
        errorDetail.setTimestamp(new Date());
        throw new FnaException(errorDetail, HttpStatus.NOT_FOUND);

        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ConsultarPlantillasRespuestaDTO consultarPlantillas() {
            ConsultarPlantillasRespuestaDTO consultarPlantillasRespuestaDTO = new ConsultarPlantillasRespuestaDTO();
            consultarPlantillasRespuestaDTO.setPlantillas(Lists.newArrayList(plantillaRepositorio.findAll()));
            consultarPlantillasRespuestaDTO.setMensaje("Consulta plantillas OK");
            return consultarPlantillasRespuestaDTO;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ActualizarPlatillaDTO actualizarPlantilla(Plantilla plantillaModelo) {
            ActualizarPlatillaDTO ans = new ActualizarPlatillaDTO();
            Optional<Plantilla> plantillaDB = plantillaRepositorio.findById(plantillaModelo.getId());
            if (!plantillaDB.isPresent()) {
                lanzarExcepcionPlantillaNoEncontrada(plantillaModelo.getNombre());
            } else if(plantillaDB.get().getActivo().equals(plantillaModelo.getActivo()) 
                    && plantillaDB.get().getContenido().equals(plantillaModelo.getContenido())) {
                ans.setMensaje("Actualizar Plantilla OK: No hubo cambios");
            } else {
                ans.setMensaje("Actualizar Plantilla OK: Si hubo cambios");
                plantillaRepositorio.save(plantillaModelo);
            }
            ans.setPlantillaActualizada(plantillaModelo);
            return ans;
        }
	
}
