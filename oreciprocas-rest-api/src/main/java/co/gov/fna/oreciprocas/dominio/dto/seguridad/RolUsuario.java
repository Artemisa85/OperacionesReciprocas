/*
 * Copyright 2019. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.dominio.dto.seguridad;

import org.springframework.security.core.GrantedAuthority;

/**
 * Descripci&oacute;n del prop&oacute;sito de la clase o interfaz,
 * no use caracteres especiales.
 *
 * @author Zamir Garc&iacute;a Romero - zagarcia@ayesa.com
 * @version 1.0
 *
 */
public class RolUsuario implements GrantedAuthority {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6016427717686611314L;
	
	/**
	 * Identificador de rol o autoridad.
	 */
	private String rolId;
	

	/**
	 * Construye un rol de usuario especificando el identificador de rol o autoridad.
	 * @param rolId Rol o autoridad asignada.
	 */
	public RolUsuario(String rolId) {
		super();
		this.rolId = rolId;
	}



	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getAuthority() {
		return rolId;
	}

}
