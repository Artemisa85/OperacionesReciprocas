package co.gov.fna.oreciprocas.dominio.modelo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * Representa los usuarios asociados a las entidades externas.
 *
 * @author Zamir Garc&iacute;a Romero - zagarcia@ayesa.com
 * @version 1.0
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "ADM_USUARIO_EXTERNO")
public class UsuarioExterno {
	
	/**
	 * Identificador del usuario en el sistema Operaciones Rec&iacute;procas.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;
	
	/**
	 * Nombre de usuario, username, nickname.
	 */
	@NotBlank
	@NotEmpty
	@Column(name = "USUARIO")
	private String usuario;
	
	/**
	 * Nombre real del usuario (Persona)
	 */
	@Column(name = "NOMBRE")
	private String nombre;
	
	/**
	 * Apellido del usuario (Persona)
	 */
	@Column(name = "APELLIDO")
	private String apellido;
	
	/**
	 * Correo electr&oacute;nico del usuario.
	 */
	@NotBlank
	@NotEmpty
	@Column(name = "CORREO")
	private String correo;

	/**
	 * Contrase&ntilde;a del usuario para autenticarse en el sistema.
	 */
	@JsonIgnore
	@Column(name = "CONTRASENA")
	private String contrasena;
	
	/**
	 * Cadena separada por comas indicando los roles asignados al usuario.
	 */
	@Column(name = "ROLES")
	private String roles;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ULTIMO_INICIO_SESION")
	private Date ultimoInicioSesion;
	
	/**
	 * Indica si la cuenta del usuario se encuentra expirada
	 */
	@Column(name = "USUARIO_EXPIRADO")
	private boolean cuentaExpirada;

	/**
	 * Indica si la cuenta del usuario se encuentra bloqueado
	 */
	@Column(name = "USUARIO_BLOQUEADO")
	private boolean cuentaBloqueada;

	/**
	 * Indica si la contrase&ntilde;a del usuario se encuentra vencida o vencida.
	 */
	@Column(name = "CONTRASENA_VENCIDA")
	private boolean contrasenaExpirada;

	/**
	 * Indica si el usuario se encuentra activo en el sistema.
	 */
	@Column(name = "ACTIVO")
	private boolean cuentaActiva;
	
	/**
	 * Fecha en la que el usuario realiz&oacute; el proceso de registro.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_REGISTRO")
	private Date fechaRegistro;
	
	/**
	 * Fecha del &uacute;ltimo cambio de contrase&ntilde;a.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_CAMBIO_CONTRASENA")
	private Date fechaCambioContrasena;
	
	/**
	 * N&uacute;mero de intentos de autenticaci&oacute;n fallidos.
	 */
	@Column(name = "INTENTOS_FALLIDOS")
	private int intentosFallidos;
	
	/**
	 * Fecha del &uacute;ltimo intento fallido de autenticaci&oacute;n.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_ULTIMO_INTENTO")
	private Date fechaUltimoIntento;
	
	
	
	/**
	 * Entidad asociada con el usuario.
	 */
	@OneToOne
	@JoinTable(
		name = "ADM_ENTIDAD_USUARIO",
		joinColumns = {
			@JoinColumn(name = "ADM_USUARIO_EXTERNO_ID", referencedColumnName = "ID")
		},
		inverseJoinColumns = {
			@JoinColumn(name = "ADM_ENTIDAD_ID", referencedColumnName = "ID")
		}
	)
	private Entidad entidad;


	

}
