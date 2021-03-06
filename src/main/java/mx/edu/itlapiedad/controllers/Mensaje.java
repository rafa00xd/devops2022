package mx.edu.itlapiedad.controllers;

//Imports de las clases
import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import mx.edu.itlapiedad.dao.UsuariosJDBC;

import org.springframework.web.bind.annotation.RequestParam;

//comentario para ejemplificar cambios git

//Anotaciones para configurar la clase como un controlador HTTP
@RestController
@RequestMapping("/api/mensajes")

public class Mensaje {
	
	@Autowired
	UsuariosJDBC repo;
	
	@GetMapping("/hola")
	public String saludar() {
		return "¡Hola WS!";
	}

	
	@GetMapping("/eco")
	public String eco(@RequestParam String mensaje) {
		return mensaje + " " + mensaje + " " + mensaje;
	}
	
	
	
	@GetMapping("/saludo")
	public String saludarUsuario(@RequestParam String usuario, @RequestParam String mensaje) {
		return usuario + " " + mensaje;
	}
	
	
	@GetMapping("/mensajes/{numero}")
	public String elegirMensaje(@PathVariable int numero) {
		String mensajes[] = new String[] {"Mensaje 1", "Mensaje 2", "Mensaje 3"};
		try {
			return mensajes[numero];
		}
			catch (Exception e)
			{
			return "Número incorrecto";
			}
		}

		@PostMapping("/login")
		public ResponseEntity<?> autenticar (@RequestParam String email,@RequestParam String contrasena ){
			String token="";
			if (repo.login(email,contrasena))
			{
				token=generarJWTToken(email);
				return new ResponseEntity<String>(token, HttpStatus.OK);
			} else {
				return new ResponseEntity<String>(HttpStatus.NO_CONTENT); 
			}
			
			}
	
		private String generarJWTToken(String usuario){
			String secretKey = "rafa ";
			List<GrantedAuthority> grantedAuthorities = AuthorityUtils
			.commaSeparatedStringToAuthorityList("ROLE_USER");

			String token = Jwts
			.builder()
			.setId("itlpJWT")
			.setSubject(usuario)
			.claim("authorities",
					grantedAuthorities.stream()
						.map(GrantedAuthority::getAuthority)
						.collect(Collectors.toList()))
				.setIssuedAt(new Date (System.currentTimeMillis()))
				.setExpiration(new Date (System.currentTimeMillis() + 600000))
				.signWith(SignatureAlgorithm.HS512,
						secretKey.getBytes()).compact();
			return "Bearer " + token;
			
			}	
	

}
