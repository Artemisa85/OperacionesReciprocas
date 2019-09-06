/*
 * Copyright 2019. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.constantes;

/**
 * Contiene los valores constantes de la configuraci&oacute;n de la aplicaci&oacute;n. 
 * Tambi&eacute;n contiene los valores para adquirir los datos de configuraci&oacute;n de base de
 * datos. 
 *
 * @author Zamir Garc&iacute;a Romero - zagarcia@ayesa.com
 * @version 1.0
 *
 */
public interface OReciprocasConstantes {
	
	/**
	 * Identificador del FNA asignado por la CGN.
	 */
	String ID_FNA = "41300000";
	
	/**
	 * Identificador de la CGN.
	 */
	String ID_CGN = "920300000";
	
	/**
     * Rol asignado a usuarios del FNA
     */
    String ROL_FNA = "FNA_ADMIN";
	
	/**
	 * Rol que usar&aacute;n los usuarios de la CGN
	 */
	String ROL_CGN = "CGN_USER";
	
	/**
	 * Rol que usar&aacute;n los dem&aacute;s usuarios externos.
	 */
	String ROL_EXTERNO = "EXTERNAL_USER";
	
	/**
	 * Nombre de la plantilla de correo para registro.
	 */
	String PLANTILLA_CORREO_REGISTRO = "registro";
	
	/**
	 * Nombre de la plantilla de correo para comentarios.
	 */
	String PLANTILLA_CORREO_COMENTARIO = "comentario";
	
	/**
	 * Nombre del dominio global
	 */
	String DB_DOMINIO_GLOBAL = "oreciprocas";
	
	/**
	 * Nombre dominio ldap
	 */
	String DB_DOMINIO_LDAP = "ldap";

	/**
	 * Nombre dominio archivos permitidos
	 */
	String DB_DOMINIO_ARCHIVOS_PERMITIDOS = "archivos-permitidos";

	/**
	 * Nombre dominio seguridad
	 */
	String DB_DOMINIO_SEGURIDAD = "seguridad";

	/**
	 * Nombre dominio seguridad
	 */
	String DB_DOMINIO_CSRF = "csrf";
	
	/**
	 * C&oacute;digo del par&aacute;metro de vencimiento de contrase&ntilde;a.
	 */
	String DB_COD_DIAS_VENCIMIENTO_CLAVE = "password-expiration-days";
	

}
