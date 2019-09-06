/*
 * Copyright 2019. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.servicio.impl;

import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import co.gov.fna.oreciprocas.dominio.dto.AdjuntoEmailDTO;
import co.gov.fna.oreciprocas.dominio.dto.NotificacionDTO;
import co.gov.fna.oreciprocas.servicio.NotificacionServicio;

/**
 * Implementaci&oacute;n de {@link NotificacionServicio}. Notifica usando SMTP. 
 *
 * @author Zamir Garc&iacute;a Romero - zagarcia@ayesa.com
 * @version 1.0
 *
 */
@Service
public class NotificacionSmtpServicio implements NotificacionServicio {
	
	
	private static Logger logger = LoggerFactory.getLogger(NotificacionSmtpServicio.class);
	
	@Autowired
	private JavaMailSender sender;

	/**
	  * {@inheritDoc}
	  */
	@Async
	public void notificar(NotificacionDTO notificacion) {
		logger.info("Enviando notificación a: <{}>", 
				extraerDestinatarios(notificacion.getDestinatarios()));
		
		MimeMessagePreparator messagePreparator = mimeMessage -> {
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
			messageHelper.setFrom("noreply@fna.gov.co", "FNA - Operaciones Recíprocas"); 
			messageHelper.setTo(notificacion.getDestinatarios());
			messageHelper.setSubject(notificacion.getAsunto());
			messageHelper.setText(notificacion.getMensaje(), notificacion.isContenidoHtmlL());
			adicionarAdjuntos(messageHelper, notificacion);
		};
		sender.send(messagePreparator);
	}
	
	/**
	 * Adiciona los datos adjuntos si existen.
	 * @param messageHelper Gestor de mensajes
	 * @param notificacion Objeto notificaci&oacute;n.
	 */
	private void adicionarAdjuntos(MimeMessageHelper messageHelper, NotificacionDTO notificacion) {
		if (notificacion != null && notificacion.getAdjuntos() != null) {
			for (AdjuntoEmailDTO adjunto: notificacion.getAdjuntos()) {
				switch (adjunto.getModo()) {
				case "EN_LINEA":
					try {
						messageHelper.addInline(adjunto.getId(), adjunto.getOrigen(), 
								adjunto.getTipoContenido());
					} catch (MessagingException e) {
						logger.error("Error adjuntando contenido {} en email", adjunto.getId());
						logger.error(e.getLocalizedMessage(), e);
					}
					break;

				default:
					try {
						messageHelper.addAttachment(adjunto.getId(), adjunto.getOrigen(), 
									adjunto.getTipoContenido());
					} catch (MessagingException e) {
						logger.error("Error adjuntando contenido {} en email", adjunto.getId());
						logger.error(e.getLocalizedMessage(), e);
					}
					break;
				}
			}
		}
		
	}

	private String extraerDestinatarios(String... destinatarios) {
		StringBuilder sb = new StringBuilder();
		for (String destinatario: destinatarios) {
			sb.append(destinatario);
			sb.append(",");
		}
		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}

}
