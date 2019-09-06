/*
 * Copyright 2019. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.controlador;

import java.math.BigInteger;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import co.gov.fna.core.common.generic.rest.FnaDtoResult;
import co.gov.fna.core.common.generic.rest.FnaResponse;
import co.gov.fna.oreciprocas.dominio.dto.ConsultarOperacionesSolicitudDTO;
import co.gov.fna.oreciprocas.dominio.dto.CargarTransaccionesDTO;

/**
 * Controlador REST que gestiona la informaci&oacute;n relacionada con
 * transacciones.
 *
 * @author Zamir Garc&iacute;a Romero - zagarcia@ayesa.com
 * @version 1.0
 *
 */
public interface TransaccionControlador {

    /**
     * Permite consultar las operaciones de acuerdo con los filtros
     *
     * @param solicitud Incluye los filtros de b&uacute;queda
     * @param auth Informaci&oacute;n de autenticaci&oacute;n y perfil del
     * usuario
     * @return Lista de transacciones disponibles de acuerdo con los filtros de
     * b&uacute;squeda.
     */
    ResponseEntity<FnaResponse<FnaDtoResult>>
            consultarTransacciones(ConsultarOperacionesSolicitudDTO solicitud, Authentication auth,
                    Pageable pageable);

    /**
     * Busca una transacci&oacute;n de acuerdo con su identificador.
     *
     * @param idTransaccion Identificador de la transacci&oacute;n.
     * @return Transacci&oacute;n asociada (Si existe)
     */
    ResponseEntity<FnaResponse<FnaDtoResult>> buscarPorId(BigInteger idTransaccion);

    /**
     * Busca los comentarios asociados a una transacci&oacute;n.
     *
     * @param id Identificador de la transacci&oacute;n.
     * @return Comentarios de la transacci&oacute;n especificada.
     */
    ResponseEntity<FnaResponse<FnaDtoResult>> buscarComentariosTransaccion(BigInteger id);

    /**
     * Carga las transacciones especificadas en un documento excel
     * @param cargarTransaccionesDTO, un objeto con el excel dentro para su procesamiento
     * @return el objeto recibido con un errorFile si es el caso o con el numero de 
     *         transacciones cargadas correctamente
     */
    ResponseEntity<?> cargarTransacciones(CargarTransaccionesDTO cargarTransaccionesDTO);
    
    /**
     * Descarga las transacciones de acuerdo con los filtros
     * @param solicitud Incluye los filtros de busqueda
     * @return Archivo excel con las transacciones, sus operaciones, comodines y comentarios 
     */
    ResponseEntity<FnaResponse<FnaDtoResult>> descargarTransacciones(ConsultarOperacionesSolicitudDTO solicitud);
    
    /**
	 * Retorna las opciones de filtros para el formulario de consulta de transacciones.
	 * @return
	 */
	ResponseEntity<FnaResponse<FnaDtoResult>> obtenerFiltrosConsulta();

}
