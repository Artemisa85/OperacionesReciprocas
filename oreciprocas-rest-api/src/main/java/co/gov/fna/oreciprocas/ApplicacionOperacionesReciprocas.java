/*
 * Copyright 2018. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Lanzador de la aplicaci&oacute;n Operaciones Rec&iacute;procas.
 *
 * @author Zamir Garc&iacute;a Romero - zagarcia@ayesa.com
 * @version 1.0
 *
 */
@SpringBootApplication (exclude = {SecurityAutoConfiguration.class })
@EnableAsync
public class ApplicacionOperacionesReciprocas extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(ApplicacionOperacionesReciprocas.class, args);
	}
	
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ApplicacionOperacionesReciprocas.class);
    }
	
	@Bean
	public MessageSource messageSource() {
	    ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
//	    messageSource.addBasenames("classpath:org/springframework/security/messages");
	    messageSource.addBasenames("classpath:messages/security");
	    return messageSource;
	}
	
}

