/*
 * Copyright 2019. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.servicio.seguridad;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

import co.gov.fna.oreciprocas.dominio.modelo.ClienteOAuth;
import co.gov.fna.oreciprocas.repositorio.ClienteOAuthRepositorio;
import co.gov.fna.oreciprocas.servicio.UsuarioExternoServicio;

/**
 * Componente para gestionar el comportamiento de la aplicación en caso de autenticación
 * fallida a causa de credenciales incorrectas.
 *
 * @author Zamir Garc&iacute;a Romero - zagarcia@ayesa.com
 * @version 1.0
 *
 */
@Component
public class AuthenticationFailureListener
			implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

	private static Logger logger = LoggerFactory.getLogger(AuthenticationFailureListener.class);
	
	@Autowired
	private ClienteOAuthRepositorio clienteRepositorio;
	
	@Autowired
	private UsuarioExternoServicio usuarioServicio;
	
	/**
	  * {@inheritDoc}
	  */
	@Override
	public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {
		Optional<ClienteOAuth> cliente = 
				clienteRepositorio.findByClientId(event.getAuthentication().getName());
		
		//Se descarta que la autenticación fallida sea de un cliente.
		if ( !cliente.isPresent() ) {
			logger.info("Autenticación fallida: {}", event.getAuthentication().getName());
			//Se registra el intento fallido del usuario.
			usuarioServicio.registrarIntentoFallido(event.getAuthentication().getName());
			
		}
		
	}
}
