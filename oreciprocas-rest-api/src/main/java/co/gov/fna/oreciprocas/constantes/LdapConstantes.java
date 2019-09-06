/*
 * Copyright 2019. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.constantes;

/**
 * Contiene los valores constantes de la configuraci&oacute;n LDAP
 *
 * @author Zamir Garc&iacute;a Romero - zagarcia@ayesa.com
 * @version 1.0
 *
 */
public interface LdapConstantes {
	
	/**
	 * Atributo de conexi&oacute;n al servidor LDAP
	 */
	String URL = "url";
	
	/**
	 * Atributo usuario administrador LDAP
	 */
	String MANAGER_DN = "manager-dn";
	
	/**
	 * Atributo contrase&ntilde;a del administrador.
	 */
	String MANAGER_PASSWORD = "manager-password";
	
}
