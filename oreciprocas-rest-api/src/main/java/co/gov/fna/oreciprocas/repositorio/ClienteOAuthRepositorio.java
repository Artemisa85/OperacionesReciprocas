/*
 * Copyright 2019. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.repositorio;

import java.util.Optional;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;

import co.gov.fna.oreciprocas.dominio.modelo.ClienteOAuth;

/**
 * Repositorio que gestiona las entidades JPA {@link ClienteOAuth}.
 *
 * @author Zamir Garc&iacute;a Romero - zagarcia@ayesa.com
 * @version 1.0
 *
 */
public interface ClienteOAuthRepositorio extends CrudRepository<ClienteOAuth, Integer> {
	
	/**
	 * Busca un cliente por el nombre de su identificador
	 * @param clientId Nombre identificador del cliente.
	 * @return Cliente OAuth si este est&aacute; registrado.
	 */
	@Cacheable(value = "oauth-clients")
	Optional<ClienteOAuth> findByClientId(String clientId);

}
