package mx.edu.itlapiedad.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import mx.edu.itlapiedad.dao.CuentaJDBC;
import mx.edu.itlp.models.Cuenta;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH})
public class CuentaWS {
	@Autowired
	CuentaJDBC repo;
	
	@PostMapping("/planes/{plan_id}/cuentas")
	public ResponseEntity<?> insertar(@RequestBody Cuenta nueva_cuenta){
		try {
			int id = repo.insertar(nueva_cuenta);
			return new ResponseEntity<>(id, HttpStatus.CREATED);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
	}
	
	@PutMapping("/cuentas/{cuenta_id}")
	public ResponseEntity<?> modificar(@PathVariable int cuenta_id, @RequestBody Cuenta cuenta){
		repo.modificar(cuenta_id, cuenta);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@PutMapping("/planes/{plan_id}/cuentas/{cuenta_id}")
	public ResponseEntity<?> modificarPlan(@PathVariable int cuenta_id, @RequestBody Cuenta cuenta1){
		repo.modificarPlan(cuenta_id, cuenta1);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@DeleteMapping("/cuentas/{cuenta_id}")
	public ResponseEntity<?> desactivar(@PathVariable int cuenta_id){
		repo.desactivar(cuenta_id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> verificar(@RequestBody Cuenta login_cuenta){
		try {
			Cuenta resultado = repo.verificar(login_cuenta);
			return new ResponseEntity<>(resultado, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
	}
	
	
}
