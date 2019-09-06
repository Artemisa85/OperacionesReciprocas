/*
 * Copyright 2019. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.servicio;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import co.gov.fna.oreciprocas.dominio.dto.CargarTransaccionesDTO;
import co.gov.fna.oreciprocas.dominio.dto.ConsultarOperacionesRespuestaDTO;
import co.gov.fna.oreciprocas.dominio.dto.ConsultarOperacionesSolicitudDTO;
import co.gov.fna.oreciprocas.dominio.dto.DescargarTransaccionesDTO;
import co.gov.fna.oreciprocas.dominio.dto.FiltrosConsultaRespuestaDTO;
import co.gov.fna.oreciprocas.dominio.dto.TransaccionDTO;
import co.gov.fna.oreciprocas.dominio.dto.TransaccionDetalleDTO;

/**
 * Servicio para gestionar las transacciones
 *
 * @author Zamir Garc&iacute;a Romero - zagarcia@ayesa.com
 * @version 1.0
 *
 */
public interface TransaccionServicio {
	
	/**
	 * Busca una transacci&oacute;n por su identificador.
	 * 
	 * @param id Identificador dela transacci&oacute;n
	 * @return Transacci&oacute;n coincidiente, si existe.
	 */
	Optional<TransaccionDTO> buscarTransaccionPorId(BigInteger id);

    /**
	 * Modifica una transacci&oacute;n
	 * @param transaccion Transacci&oacute;n a modificar
	 * @return Transacci&oacute;n modificada.
	 */
	Optional<TransaccionDTO> modificar(TransaccionDTO transaccion);

	/**
     * Obtiene el listado de transacciones de acuerdo con el perfil y la
     * empresa.
     *
     * @param solicitud Par&aacute;metros de consulta.
     * @param auth Informaci&oacute;n de autenticaci&oacute;n
     * @param pageable
     * @return Transacciones que puede consultar el usuario.
     */
    ConsultarOperacionesRespuestaDTO consultarTransacciones(ConsultarOperacionesSolicitudDTO solicitud,
            Authentication auth, Pageable pageable);

    /**
     * Carga las transacciones que tienen origen en un archivo excel a la db
     *
     * @param cargarTransaccionesDTO, un dto con el excel a cargar
     * @return el mismo dto cambiando parametros deacuerdo a el resultado
     * presentado
     */
    CargarTransaccionesDTO cargarTransacciones(CargarTransaccionesDTO cargarTransaccionesDTO);
    
    /**
     * Obtiene transacciones &uacute;nicas dependiendo la fecha de
     * consolidaci&oacute;n.
     *
     * @return Transacciones que corresponden a una única fecha de
     * consolidaci&oacute;n.
     */
    Collection<Date> obtenerFechasConsolidados();

    /**
     * Retorna los detalles de una transacci&oacute;
     *
     * @param idTransaccion
     * @return Detalles de transacci&oacute;n
     */
    TransaccionDetalleDTO getDetalleTransaccion(BigInteger idTransaccion);

    /**
     * Contruye y devuelve un archivo excel con las transacciones asociadas a una solicitud 
     * que contiene filtros.
     *  
     * @param solicitud, los filtros a aplicar en busqueda de las transacciones a 
     * @return un objeto que contiene el archivo excel y un mensaje que comunica el resultado 
     * del proceso
     */
    DescargarTransaccionesDTO descargarTransacciones(ConsultarOperacionesSolicitudDTO solicitud);
    
    /**
	 * Retorna las opciones de filtro para el servicio de consulta de transacci&oacute;n.
	 * @return
	 */
	FiltrosConsultaRespuestaDTO obtenerFiltrosConsulta();

}
