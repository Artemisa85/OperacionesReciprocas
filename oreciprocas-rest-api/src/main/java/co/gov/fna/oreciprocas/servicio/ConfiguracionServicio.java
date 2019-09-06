/*
 * Copyright 2019. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.servicio;

import java.util.Collection;

import co.gov.fna.oreciprocas.dominio.modelo.ConfiguracionRegistro;

/**
 * Servicio que gestiona las opciones de la aplicaci&oacute;n
 *
 * @author Zamir Garc&iacute;a Romero - zagarcia@ayesa.com
 * @version 1.0
 *
 */
public interface ConfiguracionServicio {
	
	
	/**
	 * Retorna todas las configuraciones de un dominio.
	 * @param dominio Nombre del dominio buscado.
	 * @return Configuraciones de un dominio.
	 */
	Collection<ConfiguracionRegistro> obtenerConfiguracionDominio(String dominio);
	
	/**
	 * Retorna una configuracion espec&iacute;fica.
	 * @param dominio Dominio de configuraci&oacute;nn
	 * @param codigo C&oacute;ndigo de la configuraci&oacute;nn
	 * @return configuracion espec&iacute;fica. buscada.11
	 */
	ConfiguracionRegistro obtenerRegistroConfiguracion(String dominio, String codigo);
	
	

}
