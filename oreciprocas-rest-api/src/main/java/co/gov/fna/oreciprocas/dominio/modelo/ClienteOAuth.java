/*
 * Copyright 2019. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.dominio.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Contiene la informaci&oacute;n de los clientes (Aplicaciones) que pueden autenticarse en el 
 * contexto de seguridad de la aplicaci&oacute;n Operaciones Rec&iacute;procas.
 *
 * @author Zamir Garc&iacute;a Romero - zagarcia@ayesa.com
 * @version 1.0
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "OAUTH_CLIENT")
public class ClienteOAuth {

	/**
	 * Identificador del cliente (aplicaci&oacute;n en el sistema Operaciones Rec&iacute;procas.
	 */
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	/**
	 * Propiedad OAuth: Nombre identificador del cliente. 
	 */
	@Column(name = "CLIENT_ID")
	private String clientId;
	
	/**
	 * Propiedad OAuth: Nombre de los recursos que accede la aplicaci&oacute;n.
	 */
	@Column(name = "RESOURCE_IDS")
	private String resourceIds;

	/**
	 * Propiedad OAuth: Contrase&ntilde;a del cliente
	 */
	@Column(name = "CLIENT_SECRET")
	private String secret;
	
	/**
	 * Propiedad OAuth: &Aacutembito del cliente.
	 */
	@Column(name = "SCOPE")
	private String scope;
	
	/**
	 * Propiedad OAuth: Tipos de permisos autorizados
	 */
	@Column(name = "AUTHORIZED_GRANT_TYPES")
	private String grantTypes;
	
	/**
	 * Propiedad OAuth: Roles o permisos concedidos
	 */
	@Column(name = "AUTHORITIES")
	private String authorities;
	
	/**
	 * Propiedad OAuth: URIs registradas para redirecci&oacute;n.
	 */
	@Column(name = "REDIRECT_URIS")
	private String redirectUri;
	
	/**
	 * Propiedad OAuth: Tiempo de validez del token de acceso
	 */
	@Column(name = "ACCESSTOKEN_VALIDITY_SECONDS")
	private int accessTokenValiditySeconds;
	
	/**
	 * Propiedad OAuth: Tiempo de validez del token de refresco
	 */
	@Column(name = "REFRESHTOKEN_VALIDITY_SECONDS")
	private int refreshTokenValiditySeconds;
	
	/**
	 * Propiedad OAuth: Indica si el cliente (aplicaci&oacute;n) necesita
	 * aprobaci&oacute;n por parte del cliente para un &aacute;mbito (Scope) en
	 * particular. 
	 */
	@Column(name = "SCOPE_AUTO_APPROVE")
	private boolean scopeAutoApprove;

	
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ClienteOAuth other = (ClienteOAuth) obj;
		if (clientId == null) {
			if (other.clientId != null)
				return false;
		} else if (!clientId.equals(other.clientId))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((clientId == null) ? 0 : clientId.hashCode());
		return result;
	}

	
	
	

}
