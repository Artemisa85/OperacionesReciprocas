/*
 * Copyright 2019. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.repositorio;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import co.gov.fna.oreciprocas.dominio.modelo.UsuarioExterno;

/**
 * 
 * Repositorio que gestiona las entidades JPA {@link UsuarioExterno}.
 *
 * @author Zamir Garc&iacute;a Romero - zagarcia@ayesa.com
 * @version 1.0
 *
 */
public interface UsuarioExternoRepositorio extends CrudRepository<UsuarioExterno, Integer>  {
	
	/**
	 * Busca un usuario externo por el nombre de usuario.
	 * @param usuario Nombre de usuario.
	 * @return Usuario Externo si es encontrado.
	 */
	public Optional<UsuarioExterno> findByUsuario(String usuario);

}
