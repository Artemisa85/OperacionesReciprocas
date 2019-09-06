/*
 * Copyright 2018. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.servicio.impl;

import org.springframework.stereotype.Service;

import co.gov.fna.oreciprocas.servicio.ClaveServicio;

/**
 * Descripci&oacute;n del prop&oacute;sito de la clase o interfaz,
 * no use caracteres especiales.
 *
 * @author Zamir Garc&iacute;a Romero - zagarcia@ayesa.com
 * @version 1.0
 *
 */
@Service
public class ClaveServicioImpl implements ClaveServicio {
	
	public String generarClave() {
		String clave = new PasswordGenerator().getPassword();
		return clave;
	}
	
	/**
	  * {@inheritDoc}
	  */
	@Override
	public void validarFortalezaClave(String clave) {
		throw new UnsupportedOperationException("Método no implementado");
		
	}

	public class PasswordGenerator {
		 
		public static final String NUMEROS = "0123456789";
		public static final String MAYUSCULAS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		public static final String MINUSCULAS = "abcdefghijklmnopqrstuvwxyz";
		public static final String ESPECIALES = "ñÑ";
		
		public String getPinNumber() {
			return getPassword(NUMEROS, 4);
		}
	
		public String getPassword() {
			return getPassword(8);
		}
	
		public String getPassword(int length) {
			return getPassword(NUMEROS + MAYUSCULAS + MINUSCULAS, length);
		}
	
		public String getPassword(String key, int length) {
			String pswd = "";
	
			for (int i = 0; i < length; i++) {
				pswd+=(key.charAt((int)(Math.random() * key.length())));
			}
	
			return pswd;
		}
	}

}
