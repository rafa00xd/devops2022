package mx.edu.itlapiedad.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import mx.edu.itlapiedad.dao.PlanJDBC;
import mx.edu.itlp.models.Plan;

@RestController
@RequestMapping("/api/planes")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH})
public class PlanWS {
	@Autowired
	PlanJDBC repo;
	
	@GetMapping
	public ResponseEntity<?> consultar(){
		List<Plan> resultado = repo.consultar();
		return new ResponseEntity<List<Plan>>(resultado, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> buscar(@PathVariable int id){
		try {
		Plan resultado = repo.buscar(id);
		return new ResponseEntity<Plan>(resultado, HttpStatus.OK);
		} catch (DataAccessException e) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}
	
	
@PostMapping()
	public ResponseEntity<?> insertar(@RequestBody Plan nuevo_plan){
		try {
			int id = repo.insertar(nuevo_plan);
			return new ResponseEntity<>(id, HttpStatus.CREATED);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
	}
	
}
