/*
 * Copyright 2019. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.configuracion;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ITemplateResolver;
import org.thymeleaf.templateresolver.StringTemplateResolver;

/**
 * Configuraci&oacute;n para el procesamiento de plantillas
 *
 * @author Zamir Garc&iacute;a Romero - zagarcia@ayesa.com
 * @version 1.0
 *
 */
@Configuration
public class ConfiguracionPlantillas {
	
	/**
	 * Devuelve el motor de plantillas que usar&aacute; el sistema
	 * @return
	 */
	@Bean
	public TemplateEngine templateEngine() {
		SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		templateEngine.setTemplateResolver(templateResolver());
		templateEngine.setEnableSpringELCompiler(true);
		
		return templateEngine;
	}
	
	/**
	 * Retorna el objeto que resuelve plantillas
	 * @return Objeto que resuelve plantillas.
	 */
	@Bean
	public ITemplateResolver templateResolver() {
		final StringTemplateResolver templateResolver = new StringTemplateResolver();
        templateResolver.setOrder(0);
		return templateResolver;
	}

}
