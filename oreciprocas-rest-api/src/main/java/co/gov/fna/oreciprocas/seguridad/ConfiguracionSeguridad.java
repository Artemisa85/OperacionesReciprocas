/*
 * Copyright 2018. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.seguridad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.ldap.userdetails.LdapAuthoritiesPopulator;

import co.gov.fna.oreciprocas.constantes.LdapConstantes;
import co.gov.fna.oreciprocas.constantes.OReciprocasConstantes;
import co.gov.fna.oreciprocas.dominio.modelo.ConfiguracionRegistro;
import co.gov.fna.oreciprocas.servicio.ConfiguracionServicio;
import co.gov.fna.oreciprocas.servicio.seguridad.UtilidadCifrado;

/**
 * Configuraci&oacuten principal de seguridad de la apliación Operaciones Rec&iacute;procas.
 * 
 * @author Zamir Garc&iacute;a Romero - zagarcia@ayesa.com
 * @version 1.0
 *
 */
@Configuration
@EnableWebSecurity( debug = false )
@Order(SecurityProperties.BASIC_AUTH_ORDER)
public class ConfiguracionSeguridad extends WebSecurityConfigurerAdapter {
	
	/**
	 * Trae la configuraci&oacute;n almacenada en la base de datos.
	 */
	@Autowired
	private ConfiguracionServicio configuracionServicio;
	
	/**
	 * Utilidad de cifrado.
	 */
	@Autowired
	private UtilidadCifrado utilidadCifrado;
	
	/**
	 * Servicio para recuperar los usuarios de la base de datos.
	 */
	@Autowired
	@Qualifier("dbUserDetailsService")
    private UserDetailsService dbUserDetailsService;
	
	/**
	 * Establece los roles para usuarios LDAP
	 */
	@Autowired
	private LdapAuthoritiesPopulator ldapAuthoritiesPopulator;

	@Override
    protected void configure(HttpSecurity http) throws Exception {
		http
        	.formLogin().disable() // disable form authentication
        	.anonymous().disable() // disable anonymous user
        	// Restringir el acceso a usuarios autenticados
        	.authorizeRequests().anyRequest().authenticated()
        	;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
			//Autenticación base de datos
			.userDetailsService(dbUserDetailsService).passwordEncoder(passwordEncoder())

			//Autenticación LDAP
			.and().ldapAuthentication()
			
			.userSearchBase("OU=Usuarios,DC=FNA,DC=COM,DC=CO")
			.userSearchFilter("(&(objectCategory=person)(objectClass=user)(sAMAccountName={0}))")
			
			.groupSearchBase("OU=Grupos,DC=FNA,DC=COM,DC=CO")
			
			.groupSearchFilter("(&(objectClass=group)(cn={0}))")
			.groupRoleAttribute("memberOf")
			.ldapAuthoritiesPopulator(ldapAuthoritiesPopulator)
			
			.contextSource()
			.url(getLdapUrl())
			.managerDn(getLdapManagerDn())
			.managerPassword(getLdapManagerPassword())
			
    		;
    }
    
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    
    @Bean
	public PasswordEncoder passwordEncoder(){
	   return new BCryptPasswordEncoder();
	}

	/**
	 * Provee la URL de conexi&oacute;n al servidor LDAP.
	 * @return URL de conexi&oacute;n al servidor LDAP.
	 */
	private String getLdapUrl() {
		ConfiguracionRegistro url = configuracionServicio.obtenerRegistroConfiguracion(
				OReciprocasConstantes.DB_DOMINIO_LDAP, LdapConstantes.URL);
		return url.getValor();
	}

	/**
	 * Provee el usuario administrador del LDAP
	 * @return Usuario administrador del LDAP
	 */
	private String getLdapManagerDn() {
		ConfiguracionRegistro url = configuracionServicio.obtenerRegistroConfiguracion(
				OReciprocasConstantes.DB_DOMINIO_LDAP, LdapConstantes.MANAGER_DN);
		return url.getValor();
	}

	/**
	 * Provee la contrase&ntilde;a del usuario administrador del LDAP
	 * @return Contrase&ntilde;a del usuario administrador del LDAP
	 */
	private String getLdapManagerPassword() {
		ConfiguracionRegistro url = configuracionServicio.obtenerRegistroConfiguracion(
				OReciprocasConstantes.DB_DOMINIO_LDAP, LdapConstantes.MANAGER_PASSWORD);
		return utilidadCifrado.descifrar(url.getValor());
	}
    
}
