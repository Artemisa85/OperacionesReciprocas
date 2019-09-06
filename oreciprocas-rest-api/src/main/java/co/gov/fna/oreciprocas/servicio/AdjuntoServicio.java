/*
 * Copyright 2019. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.servicio;

import java.math.BigInteger;

import co.gov.fna.oreciprocas.dominio.dto.AdjuntoDescargaDTO;
import co.gov.fna.oreciprocas.dominio.dto.CargaAdjuntoRespuestaDTO;
import co.gov.fna.oreciprocas.dominio.dto.CargaAdjuntoSolicitudDTO;

/**
 * Servicio para gestionar los adjuntos relacionados con la gesti&oacute;n de Operaciones
 * Rec&iacute;procas.
 *
 * @author Zamir Garc&iacute;a Romero - zagarcia@ayesa.com
 * @version 1.0
 *
 */
public interface AdjuntoServicio {

	/**
	 * Guarda los archivos adjuntos contenidos en la solicitud.
	 * @param solicitud Contiene los archivos a guardar y el id de comentario.
	 * @return Detalles sobre el proceso de guardado.
	 */
	CargaAdjuntoRespuestaDTO guardarAdjuntos(CargaAdjuntoSolicitudDTO solicitud);

	/**
	 * Obtiene un adjunto para descargar.
	 * @param idAdjunto Identificador del archivo adjunto
	 * @return Archivo adjunto para descarga.
	 */
	AdjuntoDescargaDTO obtenerAdjunto(BigInteger idAdjunto);
	
}
