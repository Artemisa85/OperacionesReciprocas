/*
 * Copyright 2019. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.servicio.impl;

import java.util.Date;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.gov.fna.oreciprocas.constantes.OReciprocasConstantes;
import co.gov.fna.oreciprocas.dominio.modelo.ConfiguracionRegistro;
import co.gov.fna.oreciprocas.dominio.modelo.UsuarioExterno;
import co.gov.fna.oreciprocas.repositorio.UsuarioExternoRepositorio;
import co.gov.fna.oreciprocas.servicio.ConfiguracionServicio;
import co.gov.fna.oreciprocas.servicio.UsuarioExternoServicio;

/**
 * Implementaci&oacute;n por defecto de {@link UsuarioExternoServicio}
 *
 * @author Zamir Garc&iacute;a Romero - zagarcia@ayesa.com
 * @version 1.0
 *
 */
@Service
public class UsuarioExternoServicioImpl implements UsuarioExternoServicio {
	
	private static Logger logger = LoggerFactory.getLogger(UsuarioExternoServicioImpl.class);
	
	@Autowired
	private UsuarioExternoRepositorio usuarioRepositorio;
	
	@Autowired
	private ConfiguracionServicio configuracionServicio;

	/**
	  * {@inheritDoc}
	  */
	@Override
	public void registrarInicioDeSesion(UsuarioExterno usuario) {
		Date ahora = new Date();
		usuario.setUltimoInicioSesion(ahora);
		usuario.setIntentosFallidos(0);
		
		//Validar vencimiento de la contraseña
		validarVencimientoClave(usuario);
		
		usuarioRepositorio.save(usuario);
	}

	/**
	  * {@inheritDoc}
	  */
	@Override
	public void registrarIntentoFallido(String nombreUsuario) {
		Optional<UsuarioExterno> optUsuario = usuarioRepositorio.findByUsuario(nombreUsuario);
		
		if (optUsuario.isPresent()) {
			UsuarioExterno usuario = optUsuario.get();
			//Se aumenta el número de intentos fallidos
			int intentosFallidos = usuario.getIntentosFallidos() + 1;
			//Se bloquea la cuenta con 3 o más intentos fallidos
			boolean cuentaBloqueada = intentosFallidos > 2 ? true: false; 
			
			usuario.setIntentosFallidos(intentosFallidos);
			usuario.setFechaUltimoIntento(new Date());
			usuario.setCuentaBloqueada(cuentaBloqueada);
			
			//Validar vencimiento de la contraseña
			validarVencimientoClave(usuario);
			
			usuarioRepositorio.save(usuario);
		}
		
	}
	
	private void validarVencimientoClave(UsuarioExterno usuario) {
		Date ahora = new Date();
		if (usuario.getFechaCambioContrasena() != null) {
			ConfiguracionRegistro diasVencimiento = configuracionServicio.obtenerRegistroConfiguracion(
					OReciprocasConstantes.DB_DOMINIO_SEGURIDAD, 
					OReciprocasConstantes.DB_COD_DIAS_VENCIMIENTO_CLAVE);
			Integer numDiasVencimientoClave = Integer.valueOf(diasVencimiento.getValor());
			
			Integer diasTranscurridos = 
				(int) ( (ahora.getTime() - usuario.getFechaCambioContrasena().getTime()) / 86400000);
			
			boolean vencida = diasTranscurridos >= numDiasVencimientoClave;
			
			logger.debug("Han pasado {} días desde cambio de clave para {} (vendida: {}) ",
					diasTranscurridos, usuario.getUsuario(), vencida);
			
			if (vencida) {
				usuario.setContrasenaExpirada(true);
			}
			
		}
	}

}
