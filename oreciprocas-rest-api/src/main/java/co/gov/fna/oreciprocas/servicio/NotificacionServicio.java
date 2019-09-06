/*
 * Copyright 2019. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.servicio;

import co.gov.fna.oreciprocas.dominio.dto.NotificacionDTO;

/**
 * Permite notificar acciones del sistema.
 *
 * @author Zamir Garc&iacute;a Romero - zagarcia@ayesa.com
 * @version 1.0
 *
 */
public interface NotificacionServicio {
	
	/**
	 * Realiza la notificaci&oacute;n solicitada.
	 * 
	 * @param notificacion Datos resultantes del registro.
	 */
	void notificar(NotificacionDTO notificacion);
	
}
