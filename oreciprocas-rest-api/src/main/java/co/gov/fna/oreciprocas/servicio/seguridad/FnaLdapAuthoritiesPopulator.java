/*
 * Copyright 2019. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.servicio.seguridad;

import java.util.ArrayList;
import java.util.Collection;

import javax.naming.InvalidNameException;
import javax.naming.Name;
import javax.naming.ldap.LdapName;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.ldap.userdetails.LdapAuthoritiesPopulator;
import org.springframework.stereotype.Service;

import co.gov.fna.oreciprocas.dominio.dto.seguridad.RolUsuario;

/**
 * Descripcion del tipo, no use caracteres especiales.
 *
 * @author Zamir Garc&iacute;a Romero - zagarcia@ayesa.com
 * @version 1.0
 *
 */
@Service
public class FnaLdapAuthoritiesPopulator implements LdapAuthoritiesPopulator {
	
	
	private static final Logger logger = LoggerFactory.getLogger(FnaLdapAuthoritiesPopulator.class);
	
	/**
	 * Roles/ grupos en LDAP con el Rol administrativo de Operaciones Recíprocas
	 */
	@Value("${oreciprocas.config.fna-ldap-roles}")
	private String[] rolesFna;


	/**
	  * {@inheritDoc}
	  */
	@Override
	public Collection<? extends GrantedAuthority> getGrantedAuthorities(DirContextOperations userData,
			String username) {
		
		Object[] grupos = userData.getObjectAttributes("memberOf");
		Collection<RolUsuario> roles = new ArrayList<>();
		
		for (Object grupo: grupos) {
			for (String rolValido: rolesFna) {
				if ( rolValido.equalsIgnoreCase( getCnFromRdn(grupo.toString()) ) ) {
					roles.add( new RolUsuario("FNA_ADMIN")  );
					break;
				}
			}
			
		}
		return roles;
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
			logger.error("Error extrayendo CN de {}", rdn);
			return null;
		}
		
	}

}
