package co.gov.fna.oreciprocas.configuracion;

import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.spi.PersistenceProvider;
import javax.persistence.spi.PersistenceProviderResolver;
import javax.persistence.spi.PersistenceProviderResolverHolder;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;
//https://gist.github.com/jeffsheets/aec3e94870ef903ce7efe33e00563d3c

/**
 * Lanzador de la aplicaci&oacute;n Operaciones Rec&iacute;procas.
 *
 * @author Guillermo Garc&iacute;a - genrique.garcia@iecisa.com
 * @version 1.0
 *
 */

@Slf4j
@Configuration
public class HibernatePersistenceProviderResolver implements PersistenceProviderResolver {
	
	private volatile PersistenceProvider  persistenceProvider = new HibernatePersistenceProvider();
	
	@Override
	public List<PersistenceProvider> getPersistenceProviders() {
		return Collections.singletonList(persistenceProvider);
	}

	@Override
	public void clearCachedProviders() {
		persistenceProvider = new HibernatePersistenceProvider();
	}
		
	@PostConstruct
	public void register() {	
		log.info("Registering HibernatePersistenceProviderResolver");
		PersistenceProviderResolverHolder.setPersistenceProviderResolver(new HibernatePersistenceProviderResolver());
	}
}
	