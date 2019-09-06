/*
 * Copyright 2019. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.configuracion;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Ticker;

/**
 * Configuraci&oacute;n para cache del sistema.
 *
 * @author Zamir Garc&iacute;a Romero - zagarcia@ayesa.com
 * @version 1.0
 *
 */
@Configuration
@EnableCaching
public class ConfiguracionCache {
	
	/**
	 * Expone como bean el gestor de cache del sistema. En este m&eacute;todo se definen y 
	 * configuran los caches particulares.
	 * 
	 * @param ticker Fuente de tiempo.
	 * @return Gestor de cache del sistema.
	 */
	@Bean
	public CacheManager cacheManager(Ticker ticker) {
	    SimpleCacheManager manager = new SimpleCacheManager();
	    //Registrar los caches del sistema
	    manager.setCaches(Arrays.asList(
	    		//Cache para clientes OAuth
	    		buildCache("oauth-clients", ticker, 120),
	    		//Cache para configuracion
	    		buildCache("configuration", ticker, 120),
	    		//Cache para plantillas de email
	    		buildCache("templates", ticker, 120)
	    		));
	    return manager;
	}
	
	/**
	 * Fuente de timepo que retorna el valor de tiempo que representa el n&uacute;mero de
	 * nanosegundos transcurridos partiendo de algún punto arbitrariamente establecido en el
	 * tiempo
	 * 
	 * @return Fuente de tiempo
	 */
	@Bean
	public Ticker ticker() {
	    return Ticker.systemTicker();
	}
	
	/**
	 * Construye un cache de tipo {@link CaffeineCache} de acuerdo con los par&aacute;metros
	 * ingresados.
	 * 
	 * @param nombre Nombre del cache.
	 * @param ticker Fuente de tiempo.
	 * @param expiracionMinutos Tiempo de expiraci&oacute;n del cache (En minutos)
	 * @return Cache con la configuraci&oacute;n solicitada.
	 */
	private CaffeineCache buildCache(String nombre, Ticker ticker, int expiracionMinutos) {
	    return new CaffeineCache(nombre, Caffeine.newBuilder()
	                .expireAfterWrite(expiracionMinutos, TimeUnit.MINUTES)
	                .maximumSize(100)
	                .ticker(ticker)
	                .build());
	}

}
