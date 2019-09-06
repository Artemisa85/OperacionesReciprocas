/*
 * Copyright 2019. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.servicio.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

import co.gov.fna.oreciprocas.dominio.dto.seguridad.DetallesCliente;
import co.gov.fna.oreciprocas.dominio.modelo.ClienteOAuth;
import co.gov.fna.oreciprocas.repositorio.ClienteOAuthRepositorio;
import co.gov.fna.oreciprocas.servicio.ClienteServicio;

/**
 * Implementaci&oacute;n por defecto de {@link ClienteServicio}
 *
 * @author Zamir Garc&iacute;a Romero - zagarcia@ayesa.com
 * @version 1.0
 *
 */
@Service(value = "servicioCliente")
public class ClienteServicioImpl implements ClienteServicio {
	
	@Autowired
	private ClienteOAuthRepositorio repositorioCliente;

	@Override
	public ClientDetails loadClientByClientId(String clientId)  {
		Optional<ClienteOAuth> clienteOAuth = repositorioCliente.findByClientId(clientId);
		if (clienteOAuth.isPresent()) {
			return new DetallesCliente(clienteOAuth.get());
		}
		throw new ClientRegistrationException(
					String.format("Cliente [%s] no encontrado", clientId));
	}

}
