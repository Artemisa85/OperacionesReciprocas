/*
 * Copyright 2019. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.servicio.seguridad;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Utilidad de cifrado para la aplicaci&oacute;n. Se usa cifrado AES.
 *
 * @author Zamir Garc&iacute;a Romero - zagarcia@ayesa.com
 * @version 1.0
 *
 */
@Component
public class UtilidadCifrado {
	
	private static Logger LOGGER = LoggerFactory.getLogger(UtilidadCifrado.class);

	/**
	 * Algoritmo de cifrado usado.
	 */
	private static final String ALGORITMO = "AES";
	

	/**
	 * Construye una instancia de la utilidad de cifrado.
	 */
	public UtilidadCifrado() {
		inicializar();
	}
	
	/**
	 * Cifra una cadena de acuerdo con la configuraci&oacute;n de la aplicaci&oacute;n.
	 * 
	 * @param cadena Cadena a cifrar.
	 * @return Cadena cifrada.
	 */
	public String cifrar(String cadena) {
		return cifrar(cadena, aux);
	}
	
	/**
	 * Descifra una cadena de acuerdo con la configuraci&oacute;n de la aplicaci&oacute;n.
	 * 
	 * @param cadena Cadena a descifrar.
	 * @return Cadena descifrada.
	 */
	public String descifrar(String cadena) {
		return descifrar(cadena, aux);
	}
	

	
	/*
	 * Métodos auxiliares.
	 */
	
	private String cifrar(String cadena, String especificacionLlave) {
		
		try {
			KeyGenerator keyGen = KeyGenerator.getInstance(ALGORITMO);
			keyGen.init(128);
			SecretKey sk = keyGen.generateKey();
			
			if (especificacionLlave == null) {
				 especificacionLlave = byteArrayToHexString(sk.getEncoded());
			}
			SecretKeySpec sks = getSecretKeySpec(especificacionLlave);
			Cipher cipher = Cipher.getInstance(ALGORITMO);
			cipher.init(Cipher.ENCRYPT_MODE, sks, cipher.getParameters());
			byte[] encrypted = cipher.doFinal(cadena.getBytes());
			return byteArrayToHexString(encrypted);
		}  catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException 
				| InvalidAlgorithmParameterException | IllegalBlockSizeException 
				| BadPaddingException | IOException e) {
			LOGGER.error("Ocurrió un error al cifrar la cadena");
			LOGGER.error(e.getMessage(), e);
		}  
		return null;
	}

	private String descifrar(String cadena, String especificacionLlave) {
		
		try {
			SecretKeySpec sks = getSecretKeySpec(especificacionLlave);
			Cipher cipher = Cipher.getInstance(ALGORITMO);
			cipher.init(Cipher.DECRYPT_MODE, sks);
			byte[] decrypted = cipher.doFinal(hexStringToByteArray(cadena));
			return new String(decrypted);
		} catch (IOException | NoSuchAlgorithmException | NoSuchPaddingException 
				| InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
			LOGGER.error("Ocurrió un error al descifrar la cadena");
			LOGGER.error(e.getMessage(), e);
		}
		return null;
	}
	
	
	private SecretKeySpec getSecretKeySpec(String especificacionEncriptacion) 
			throws  IOException {

		byte[] key = hexStringToByteArray(especificacionEncriptacion);
		SecretKeySpec sks = new SecretKeySpec(key, ALGORITMO);
		return sks;
	}

	private void inicializar() {
		StringBuilder sb = new StringBuilder();
		for (int i: _AUX) {
			sb.append((char)i);
		}
		aux = sb.toString();
	}


	private byte[] hexStringToByteArray(String s) {
		byte[] b = new byte[s.length() / 2];
		int index = 0;
		int v = 0;

		for (int i = 0; i < b.length; i++) {
			index = i * 2;
			v = Integer.parseInt(s.substring(index, index + 2), 16);
			b[i] = (byte) v;
		}
		return b;
	}

	private String byteArrayToHexString(byte[] b) {
		StringBuffer sb = new StringBuffer(b.length * 2);
		int v = 0;
		for (int i = 0; i < b.length; i++) {
			v = b[i] & 0xff;
			if (v < 16) {
				sb.append('0');
			}
			sb.append(Integer.toHexString(v));
		}
		return sb.toString().toUpperCase();
	}
	
	private static int[] _AUX = new int[] {
			57,69,54,69,49,52,53,69,
			67,48,67,54,68,69,51,52,
			52,69,66,67,68,53,52,65,
			48,70,56,56,66,55,49,49
		};
		
		private String aux;

}
