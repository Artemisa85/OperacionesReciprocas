package co.gov.fna.oreciprocas.controlador.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ping")
public class PingContrloladorImpl {

	/**
	 * Punto de entrada para comprobar la desponibilidad de la aplicaci&oacute;n.
	 * @return Mensaje de disponibilidad
	 */
	@GetMapping
	public ResponseEntity<String> ping(){
		return ResponseEntity.ok("Ping service OK");
	}

	/**
	 * Punto de entrada para comprobar la disponibilidad de la seguridad de la
	 * aplicaci&ocuate;n.
	 * 
	 * @param principal Objeto con informaci&oacute;n del usuario.
	 * @return Mensaje de disponibilidad
	 */
	@GetMapping("/secure")
	@PreAuthorize("hasAnyRole('FNA_ADMIN', 'CGN_USER', 'EXTERNAL_USER')")
	public ResponseEntity<String> helloAdmin(Authentication authentication){
		StringBuilder respuesta = new StringBuilder();
		respuesta.append("Bienvenido: ")
		.append(authentication.getName())
		.append(". Sus roles son: ");
		
		authentication.getAuthorities().forEach(authority -> {
			respuesta.append(authority.getAuthority());
			respuesta.append(",");
		});
		respuesta.deleteCharAt(respuesta.length() - 1);
		return ResponseEntity.ok(respuesta.toString());
	}
		

}