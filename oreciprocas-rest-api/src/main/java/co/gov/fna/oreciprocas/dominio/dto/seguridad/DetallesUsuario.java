/*
 * Copyright 2019. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.dominio.dto.seguridad;

import java.util.ArrayList;
import java.util.Collection;
import java.util.StringTokenizer;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import co.gov.fna.oreciprocas.dominio.modelo.UsuarioExterno;

/**
 * Descripci&oacute;n del prop&oacute;sito de la clase o interfaz,
 * no use caracteres especiales.
 *
 * @author Zamir Garc&iacute;a Romero - zagarcia@ayesa.com
 * @version 1.0
 *
 */
public class DetallesUsuario implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6445324366477125546L;
	
	/**
	 * Informaci&oacute;n de los detalles del usuario.
	 */
	private UsuarioExterno userDetails;
	
	/**
	 * Autoridades o roles concedidos al usuario.
	 */
	private Collection<RolUsuario> authorities;

	/**
	 * Crea los detalles de usuario a partir de un usuario del modelo de dominio.
	 * @param userDetails Informaci&oacute;n con detalles del usuario.
	 */
	public DetallesUsuario(UsuarioExterno userDetails) {
		this.userDetails = userDetails;
		mapAuthorities();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getPassword() {
		return userDetails.getContrasena();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getUsername() {
		return userDetails.getUsuario();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isAccountNonExpired() {
		return ! userDetails.isCuentaExpirada();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isAccountNonLocked() {
		return ! userDetails.isCuentaBloqueada();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isCredentialsNonExpired() {
		return ! userDetails.isContrasenaExpirada();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isEnabled() {
		return userDetails.isCuentaActiva();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}
	
	/**
	 * Retorna userDetails
	 * @return the userDetails
	 */
	public UsuarioExterno getUserDetails() {
		return userDetails;
	}

	private void mapAuthorities() {
		String currentAuthorities = userDetails.getRoles();
		StringTokenizer tokenizer = new StringTokenizer(currentAuthorities, ",");
		authorities = new ArrayList<>();

		while (tokenizer.hasMoreTokens()) {
			authorities.add(new RolUsuario("ROLE_" + tokenizer.nextToken().trim()));
		}
	}

}
