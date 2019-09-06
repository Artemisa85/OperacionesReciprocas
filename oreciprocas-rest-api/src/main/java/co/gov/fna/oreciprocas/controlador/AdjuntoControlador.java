/*
 * Copyright 2019. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.controlador;

import java.math.BigInteger;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

/**
 * Controlador REST que gestiona la informaci&oacute;n relacionada con
 * adjuntos de comentarios.
 *
 * @author Zamir Garc&iacute;a Romero - zagarcia@ayesa.com
 * @version 1.0
 *
 */
public interface AdjuntoControlador {
	
	/**
	 * Permite descargar un adjunto almacenado en el sistema usando el identificador.
	 * @param idAdjunto Identiricador del archivo adjunto.
	 * @return Archivo adjunto si existe.
	 */
	ResponseEntity<Resource> descargarAdjunto(BigInteger idAdjunto);

}
