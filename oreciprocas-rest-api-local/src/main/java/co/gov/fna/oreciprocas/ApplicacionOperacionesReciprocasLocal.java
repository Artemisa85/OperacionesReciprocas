package co.gov.fna.oreciprocas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

/**
 * The Class ApplicacionOperacionesReciprocasLocal.
 * 
 * @author Guillermo Enrique García Carrasquilla
 * @version
 */
@SpringBootApplication (exclude = {SecurityAutoConfiguration.class })
public class ApplicacionOperacionesReciprocasLocal {
 
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(ApplicacionOperacionesReciprocasLocal.class, args);
	}
	
	

}

