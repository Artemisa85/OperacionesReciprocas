/*
 * Copyright 2019. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.servicio.impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.gov.fna.oreciprocas.dominio.modelo.ConfiguracionRegistro;
import co.gov.fna.oreciprocas.repositorio.ConfiguracionRepositorio;
import co.gov.fna.oreciprocas.servicio.ConfiguracionServicio;

/**
 * Implementaci&oacute;n por defecto de {@link OpcionesServicioContrato}
 *
 * @author Zamir Garc&iacute;a Romero - zagarcia@ayesa.com
 * @version 1.0
 *
 */
@Service
public class ConfiguracionServicioImpl implements ConfiguracionServicio {
	
	@Autowired
	private ConfiguracionRepositorio repositorioConfiguracion;

	/**
	  * {@inheritDoc}
	  */
	@Override
	public Collection<ConfiguracionRegistro> obtenerConfiguracionDominio(String dominio) {
		return repositorioConfiguracion.findByDominio(dominio);
	}

	/**
	  * {@inheritDoc}
	  */
	@Override
	public ConfiguracionRegistro obtenerRegistroConfiguracion(String dominio, String codigo) {
		return repositorioConfiguracion.findByDominioAndCodigo(dominio, codigo);
	}
	

}
