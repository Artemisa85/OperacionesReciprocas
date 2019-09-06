/*
 * Copyright 2019. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.servicio.seguridad;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import co.gov.fna.oreciprocas.dominio.dto.seguridad.DetallesUsuario;
import co.gov.fna.oreciprocas.dominio.modelo.Entidad;
import co.gov.fna.oreciprocas.dominio.modelo.UsuarioExterno;
import co.gov.fna.oreciprocas.repositorio.EntidadRepositorio;

/**
 * Descripcion del tipo, no use caracteres especiales.
 *
 * @author Zamir Garc&iacute;a Romero - zagarcia@ayesa.com
 * @version 1.0
 *
 */
@Service(value = "dbUserDetailsService")
public class DbUserDetailsService implements UserDetailsService {
	
	private final static String SEPARADOR_USUARIO = "\\|"; 
	
	private final static String MENSAJE_ERROR = "Usuario [%s] no encontrado"; 
	
	@Autowired
	private EntidadRepositorio repositorioEntidad;


	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		String[] datosEntidad = username.split(SEPARADOR_USUARIO);
		
		if (datosEntidad.length != 2) {
			throw new UsernameNotFoundException(String.format(MENSAJE_ERROR, username));
		}
		
		String nit = datosEntidad[0];
		String idCGN = datosEntidad[1];
		
		Optional<Entidad> entidad = repositorioEntidad.findByIdCGNAndNit(idCGN, nit);
		if (! entidad.isPresent() || entidad.get().getUsuario() == null) {
			throw new UsernameNotFoundException(String.format(MENSAJE_ERROR, username));
        } 
		
		UsuarioExterno usuario = entidad.get().getUsuario();
		
        return new DetallesUsuario(usuario);
	}

	
	
}
