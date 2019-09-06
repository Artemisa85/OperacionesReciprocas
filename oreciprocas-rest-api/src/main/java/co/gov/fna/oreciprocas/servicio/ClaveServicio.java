/*
 * Copyright 2018. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.servicio;

/**
 * Servicio utilitario que genera claves de manera aleatoria
 *
 * @author Cristian Ramirez Quintero - tecnico_desarrollador24@utayecisa.com
 * @version 1.0
 *
 */
public interface ClaveServicio {

	/**
	 * Genera una contrase&ntilde;a de manera aleatoria
	 * @param entrada void
	 * @return String con la contrase&ntilde;a generada
	 */
	String generarClave();
	
	/**
	 * Valida la fortaleza de una contrase&ntilde;a de acuerdo con las po&iacute;ticas de seguridad
	 * de la entidad
	 * @param clave Clave a validar
	 */
	void validarFortalezaClave(String clave);

}
