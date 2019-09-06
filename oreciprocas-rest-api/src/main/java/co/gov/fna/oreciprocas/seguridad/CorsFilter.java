/*
 * Copyright 2019. Fondo Nacional del Ahorro
 */
package co.gov.fna.oreciprocas.seguridad;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import co.gov.fna.oreciprocas.dominio.modelo.ConfiguracionRegistro;
import co.gov.fna.oreciprocas.servicio.ConfiguracionServicio;

import static co.gov.fna.oreciprocas.constantes.OReciprocasConstantes.DB_DOMINIO_CSRF;

/**
 * Controla de manera globlal las solicitudes de or&iacute;genes cruzados (CORS)
 *
 * @author Zamir Garc&iacute;a Romero - zagarcia@ayesa.com
 * @version 1.0
 *
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsFilter  implements Filter {
	
	@Autowired
	private ConfiguracionServicio configuracionServicio;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void destroy() {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		
		final HttpServletResponse response = (HttpServletResponse) res;
		final HttpServletRequest request = (HttpServletRequest) req;
		
		Collection<ConfiguracionRegistro> corsOpts = 
				configuracionServicio.obtenerConfiguracionDominio(DB_DOMINIO_CSRF);
		
		for (ConfiguracionRegistro conf: corsOpts) {
			response.setHeader(conf.getCodigo(), conf.getValor());
		}
		
		if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
			response.setStatus(HttpServletResponse.SC_OK);
		} else {
			chain.doFilter(req, res);
		}

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

}
