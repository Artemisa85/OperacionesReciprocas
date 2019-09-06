/*
 * Copyright 2019. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.servicio;

import co.gov.fna.oreciprocas.dominio.modelo.UsuarioExterno;

/**
 * Servicio para gestionar los usuarios externos.
 *
 * @author Zamir Garc&iacute;a Romero - zagarcia@ayesa.com
 * @version 1.0
 *
 */
public interface UsuarioExternoServicio {
	
	/**
	 * Registra
	 * @param usuario
	 */
	void registrarInicioDeSesion(UsuarioExterno usuario);

	/**
	 * Intenta registrar un intento fallido de un usuario en base de datos. No produce
	 * error si no encuentra al usuario.
	 * 
	 * @param nombreUsuario Nombre o nickname del usuario. 
	 */
	void registrarIntentoFallido(String nombreUsuario);

}
