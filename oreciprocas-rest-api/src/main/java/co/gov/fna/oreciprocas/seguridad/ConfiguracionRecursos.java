package co.gov.fna.oreciprocas.seguridad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;

@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class ConfiguracionRecursos extends ResourceServerConfigurerAdapter {

	@Value("${security.oauth2.resource.id}")
	private String resourceId;

	// The DefaultTokenServices bean provided at the AuthorizationConfig
	@Autowired
	private DefaultTokenServices tokenServices;

	// The TokenStore bean provided at the AuthorizationConfig
	@Autowired
	private TokenStore tokenStore;

	// To allow the rResourceServerConfigurerAdapter to understand the token,
	// it must share the same characteristics with AuthorizationServerConfigurerAdapter.
	// So, we must wire it up the beans in the ResourceServerSecurityConfigurer.
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) {
		resources
			.resourceId(resourceId)
			.tokenServices(tokenServices)
			.tokenStore(tokenStore);
	}	
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
			.antMatchers(HttpMethod.OPTIONS).permitAll()
        	// Configuración de seguridad
			.antMatchers(HttpMethod.GET, 	"/ping").permitAll()
			.antMatchers(HttpMethod.GET,	"/publico/*").permitAll()
			.antMatchers(HttpMethod.POST, 	"/publico/*").permitAll()
			.antMatchers(HttpMethod.PUT, 	"/publico/*").permitAll()
			.antMatchers(HttpMethod.DELETE, "/publico/*").permitAll()
			.antMatchers(HttpMethod.HEAD, 	"/publico/*").permitAll()
			
			.anyRequest().authenticated()
			// Deshabilitar CSRF
			.and().csrf().disable();
	}

}
