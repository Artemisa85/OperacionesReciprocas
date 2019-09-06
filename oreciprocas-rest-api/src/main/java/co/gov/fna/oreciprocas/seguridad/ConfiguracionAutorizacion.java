package co.gov.fna.oreciprocas.seguridad;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import co.gov.fna.oreciprocas.servicio.seguridad.ReciprocasTokenEnhancer;

/**
 * Configuraci&oacute;n para activar el servidor de autorizaci&oacute;n.
 *
 * @author Zamir Garc&iacute;a Romero - zagarcia@ayesa.com
 * @version 1.0
 *
 */
@Configuration
@EnableAuthorizationServer
public class ConfiguracionAutorizacion extends AuthorizationServerConfigurerAdapter {

	/**
	 * Gestor de autenticaci&oacute;n
	 */
	@Autowired
	private AuthenticationManager authenticationManager;
	
	/**
	 * Servicio de gesti&oacute;n de clientes (aplicaciones)
	 */
	@Autowired
	@Qualifier("servicioCliente")
	private ClientDetailsService clientDetailsService;
	
	/**
	 * "Potenciador" del token JWT
	 */
	@Autowired
	private ReciprocasTokenEnhancer reciprocasTokenEnhancer;
	
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints
			.authenticationManager(this.authenticationManager)
			.tokenServices(tokenServices())
			.tokenStore(tokenStore())
			.accessTokenConverter(accessTokenConverter());
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
		oauthServer
			// Permitir acceso para solicitar/validar token solo a clientes
			// con el Rol 'ROLE_TRUSTED_CLIENT'
			.tokenKeyAccess("hasAuthority('ROLE_TRUSTED_CLIENT')")
			.checkTokenAccess("hasAuthority('ROLE_TRUSTED_CLIENT')");
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.withClientDetails(clientDetailsService);
	}

	@Bean
	public TokenStore tokenStore() {
		return new JwtTokenStore(accessTokenConverter());
	}

	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		converter.setSigningKey("9RhoHHsqkV");
		return converter;
	}

	@Bean
	@Primary
	public DefaultTokenServices tokenServices() {
		DefaultTokenServices tokenServices = new DefaultTokenServices();
		tokenServices.setClientDetailsService(clientDetailsService);
		tokenServices.setTokenStore(tokenStore());
		tokenServices.setSupportRefreshToken(true);
		tokenServices.setReuseRefreshToken(false);
		
		TokenEnhancerChain tokenChain = new TokenEnhancerChain();
		List<TokenEnhancer> delegates = new ArrayList<>();
		delegates.add(accessTokenConverter()); //Se agrega enhancer JWT
		delegates.add(reciprocasTokenEnhancer); //Se agrega enhancer personalizado
		tokenChain.setTokenEnhancers(delegates);
		tokenServices.setTokenEnhancer(tokenChain);
		
		return tokenServices;
	}

}
