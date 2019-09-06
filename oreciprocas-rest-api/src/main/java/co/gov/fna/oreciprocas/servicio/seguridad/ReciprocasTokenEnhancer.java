/*
 * Copyright 2019. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.servicio.seguridad;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.naming.InvalidNameException;
import javax.naming.Name;
import javax.naming.ldap.LdapName;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.ldap.userdetails.LdapUserDetailsImpl;
import org.springframework.security.oauth2.common.DefaultExpiringOAuth2RefreshToken;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import co.gov.fna.oreciprocas.dominio.dto.seguridad.DetallesUsuario;
import co.gov.fna.oreciprocas.dominio.modelo.UsuarioExterno;
import co.gov.fna.oreciprocas.servicio.UsuarioExternoServicio;

/**
 * A&ntilde;ade la informaci&oacute;n adicional al token JWT
 *
 * @author Zamir Garc&iacute;a Romero - zagarcia@ayesa.com
 * @version 1.0
 *
 */
@Component
public class ReciprocasTokenEnhancer implements TokenEnhancer {
	
	@Autowired
	private UsuarioExternoServicio usuarioServicio;
	
	/**
	  * {@inheritDoc}
	  */
	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {

		DefaultOAuth2AccessToken customAT = (DefaultOAuth2AccessToken) accessToken;
		
		Optional<Map<String, Object>> optAdditionalInfo = getAdditionalInfo(authentication);
		Map<String, Object> additionalInfo = null;
		
		
		if (optAdditionalInfo.isPresent()) {
			additionalInfo = optAdditionalInfo.get();
		} else {
			additionalInfo = new HashMap<>();
		}
		
		DefaultExpiringOAuth2RefreshToken refreshToken = 
				(DefaultExpiringOAuth2RefreshToken) accessToken.getRefreshToken();
		
		long refreshTokenExpiresIn = 
				(refreshToken.getExpiration().getTime() - System.currentTimeMillis()) / 1000;
		
		additionalInfo.put("refresh_token_expires_in", refreshTokenExpiresIn);
		
		customAT.setAdditionalInformation(additionalInfo);
		
		return customAT;
	}
	
	/**
	 * Retorna propiedades adicionales para a&ntilde;adir al token.
	 * @param authentication Objeto de autenticaci&oacute;
	 * @return Propiedades adicionales.
	 */
	private Optional<Map<String, Object>> getAdditionalInfo(OAuth2Authentication authentication) {
		
		Object genericUserDetails = authentication.getUserAuthentication().getPrincipal();
		
		if (genericUserDetails instanceof LdapUserDetailsImpl) {
			return getLdapAdditionalInfo(authentication);
		}
		if (genericUserDetails instanceof DetallesUsuario) {
			return getDBAdditionalInfo(authentication);
		}
		
		return Optional.empty();
	}
	
	/**
	 * Retorna propiedades adicionales de usuarios autenticados por base de datos.
	 * @param authentication Objeto de autenticaci&oacute;
	 * @return Propiedades adicionales.
	 */
	private Optional<Map<String, Object>> getDBAdditionalInfo(OAuth2Authentication authentication){
		
		DetallesUsuario userDetails = 
				(DetallesUsuario) authentication.getUserAuthentication().getPrincipal();
		UsuarioExterno usuario = userDetails.getUserDetails();
		
		final Map<String, Object> additionalInfo = new HashMap<>();
		String nombre = usuario.getNombre() + " " + usuario.getApellido();
		if (nombre.trim().isEmpty() || usuario.getNombre() == null 
				|| usuario.getApellido() == null) {
			nombre = null;
		}
		String fechaString = formatearFecha(usuario.getUltimoInicioSesion());
		
		additionalInfo.put("name", nombre);
        additionalInfo.put("companies", Arrays.asList(usuario.getEntidad().getRazonSocial() ));
        additionalInfo.put("authorities", authentication.getAuthorities());
        additionalInfo.put("last_login_time", fechaString);
        /*
         * Se realiza el registro de inicio de sesión acá para obtener el último inicio de 
         * sesión real y no el actual. No se usa esperando un AuthenticationSuccessEvent
         * porque se dispara mucho antes que este decorador de token.
         */
        usuarioServicio.registrarInicioDeSesion(usuario);
        
        return Optional.of(additionalInfo);
	}
	
	
	/**
	 * Obtiene los datos adicionales para los usuarios autenticados por LDAP
	 * @param authentication Informaci&oacute;n del usuario.
	 * @return Datos adicionales
	 */
	private Optional<Map<String, Object>> getLdapAdditionalInfo(OAuth2Authentication authentication) {
		LdapUserDetailsImpl userDetails = 
				(LdapUserDetailsImpl) authentication.getUserAuthentication().getPrincipal();
		
		final Map<String, Object> additionalInfo = new HashMap<>();
		
		additionalInfo.put("name", getCnFromRdn(userDetails.getDn()));
        additionalInfo.put("companies", Arrays.asList("Fondo Nacional del Ahorro"));
        additionalInfo.put("authorities", authentication.getAuthorities());
        //additionalInfo.put("last_login_time", usuario.getUltimoInicioSesion());
		
		return Optional.of(additionalInfo);
	}
	
	/**
	 * Obtiene el nombre com&uacute;n (CN) de un nombre distinguido relativo (RDN)
	 * @param rdn Cadena con RDN
	 * @return CN asociado al RDN
	 */
	private String getCnFromRdn(String rdn) {
		try {
			LdapName nombreLdap = new LdapName(rdn);
			Name nombreComun = nombreLdap.getSuffix(nombreLdap.size() - 1);
			String nombreRol = nombreComun.toString().toUpperCase().replaceFirst("CN=", ""); 
			return nombreRol;
			
		} catch (InvalidNameException e) {
			return null;
		}
		
	}

	/**
	 * Formatea una fecha al formato yyyy-MM-dd HH:mm:ss
	 * @param fecha
	 * @return
	 */
	private String formatearFecha(Date fecha) {
		if (fecha == null) {
			return null;
		}
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return dateFormat.format(fecha);
	}

}
