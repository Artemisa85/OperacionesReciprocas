/*
 * Copyright 2019. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.repositorio;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import co.gov.fna.oreciprocas.dominio.modelo.ContrasenaHistorial;

/**
 * Repositorio de entidates tipo {@link ContrasenaHistorial}.
 *
 * @author Zamir Garc&iacute;a Romero - zagarcia@ayesa.com
 * @version 1.0
 *
 */
public interface ContrasenaHistorialRepositorio extends JpaRepository<ContrasenaHistorial, Integer> {
	
	/**
	 * Busca el historial de contrase&ntilde;as de un usuario.
	 * 
	 * @param idUsuario Identificaci&oacute;n del usuario.
	 * 
	 * @return Collecci&oacute;n de contrase&ntilde;as.
	 */
	Collection<ContrasenaHistorial> 
					findFirst3ByIdUsuarioOrderByFechaCambioContrasenaDesc(Integer idUsuario);

}
