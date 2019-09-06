/*
 * Copyright 2019. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.dominio.dto.seguridad;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.StringTokenizer;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;

import co.gov.fna.oreciprocas.dominio.modelo.ClienteOAuth;

/**
 * Implementaci&oacute;n para la interfaz {@link ClientDetails}
 *
 * @author Zamir Garc&iacute;a Romero - zagarcia@ayesa.com
 * @version 1.0
 *
 */
public class DetallesCliente implements ClientDetails {

	/**
	 * UID Generado
	 */
	private static final long serialVersionUID = -5293661217999965322L;
	
	/**
	 * Informaci&oacute;n sobre el cliente (Aplicaci&oacute;n)
	 */
	private ClienteOAuth clienteOAuth;
	
	/**
	 * Recuersos a los que un cliente puede acceder.
	 */
	private Set<String> resourceIds;

	/**
	 * &Aacute;mbitos que son concedidos al token
	 */
	private Set<String> scope;
	
	/**
	 * Tipos de concesi&oacute;n para los que este cliente está autorizado.
	 */
	private Set<String> authorizedGrantTypes;
	
	/**
	 * La URI predefinida para este cliente durante el acceso al "authorization_code"
	 */
	private Set<String> registeredRedirectUri;
	
	/**
	 * Autoridades o roles concedidos al cliente.
	 */
	private Collection<GrantedAuthority> authorities;
	
	
	/**
	 * Construye los detalles del cliente usando un {@link ClienteOAuth}
	 * @param clienteOAuth Informaci&oacute;n del cliente
	 */
	public DetallesCliente(ClienteOAuth clienteOAuth) {
		if (Objects.isNull(clienteOAuth)) {
			throw new IllegalArgumentException(
					"Detalles de cliente son requeridos");
		}
		this.clienteOAuth = clienteOAuth;
		mapResourceIds();
		mapScopes();
		mapGrantTypes();
		mapRedirectUri();
		mapAuthorities();
	}

	@Override
	public String getClientId() {
		return clienteOAuth.getClientId();
	}

	@Override
	public Set<String> getResourceIds() {
		return resourceIds;
	}

	@Override
	public boolean isSecretRequired() {
		return Objects.isNull(clienteOAuth.getSecret());
	}

	@Override
	public String getClientSecret() {
		return clienteOAuth.getSecret();
	}

	@Override
	public boolean isScoped() {
		return true;
	}

	@Override
	public Set<String> getScope() {
		return scope;
	}

	@Override
	public Set<String> getAuthorizedGrantTypes() {
		return authorizedGrantTypes;
	}

	@Override
	public Set<String> getRegisteredRedirectUri() {
		return registeredRedirectUri;
	}

	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public Integer getAccessTokenValiditySeconds() {
		return clienteOAuth.getAccessTokenValiditySeconds();
	}

	@Override
	public Integer getRefreshTokenValiditySeconds() {
		return clienteOAuth.getRefreshTokenValiditySeconds();
	}

	@Override
	public boolean isAutoApprove(String scope) {
		return clienteOAuth.isScopeAutoApprove();
	}

	@Override
	public Map<String, Object> getAdditionalInformation() {
		return null;
	}
	

	
	/*
	 *************************************************************************
	 * Métodos utilitarios 
	 *************************************************************************
	 */
	
	private void mapResourceIds() {
		resourceIds = 
			new HashSet<>(
				Arrays.asList(clienteOAuth.getResourceIds().split(",")));
	}

	private void mapScopes() {
		if ( Objects.nonNull( clienteOAuth.getScope()  ) ) {
			scope = new HashSet<>();
			Collection<String> scopeCollection =
					Arrays.asList(clienteOAuth.getScope().split(","));
			
			scopeCollection.forEach(currentScope -> {
				scope.add(currentScope.trim());
			});
		}
		
	}

	private void mapGrantTypes() {
		authorizedGrantTypes = new HashSet<>();
		Collection<String>	grantTypes =
			Arrays.asList(clienteOAuth.getGrantTypes().split(","));
		
		grantTypes.forEach(currentGrantType -> {
			authorizedGrantTypes.add(currentGrantType.trim());
		});
	}

	private void mapRedirectUri() {
		if ( Objects.nonNull( clienteOAuth.getRedirectUri() ) ) {
			registeredRedirectUri = new HashSet<>();
			Collection<String>	registeredUris =
					Arrays.asList(clienteOAuth.getRedirectUri().split(","));
				
				registeredUris.forEach(uri -> {
					registeredUris.add(uri.trim());
				});
		}
	}

	private void mapAuthorities() {
		String currentAuthorities = clienteOAuth.getAuthorities();
		StringTokenizer tokenizer = new StringTokenizer(currentAuthorities, ",");
		authorities = new ArrayList<>();
		
		while (tokenizer.hasMoreTokens()) {
			SimpleGrantedAuthority authority = 
					new SimpleGrantedAuthority(tokenizer.nextToken().trim());
			authorities.add(authority);
		}
	}

}
