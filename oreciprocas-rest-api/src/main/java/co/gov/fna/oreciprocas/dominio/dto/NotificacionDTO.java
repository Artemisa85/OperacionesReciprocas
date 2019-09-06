/*
 * Copyright 2019. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.dominio.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Contiene los datos de notificaci&oacute;n.
 *
 * @author Zamir Garc&iacute;a Romero - zagarcia@ayesa.com
 * @version 1.0
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificacionDTO {
	
	/**
	 * Correo electr&oacute;nico del destinatario.
	 */
	private String[] destinatarios;
	
	/**
	 * Asunto del mensaje
	 */
	private String asunto;
	
	/**
	 * Contenido o mensaje para el destinatario.
	 */
	private String mensaje;
	
	/**
	 * Archivos adjuntos
	 */
	private AdjuntoEmailDTO[] adjuntos;
	
	/**
	 * Tipo de notificaci&oacute;n enviada.
	 */
	private String tipoNotificacion;
	
	/**
	 * Indica si el contenido es HTML
	 */
	private boolean contenidoHtmlL;

}
