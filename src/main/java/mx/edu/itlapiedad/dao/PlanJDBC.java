	package mx.edu.itlapiedad.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import mx.edu.itlp.models.Plan;
import java.util.List;

@Repository
public class PlanJDBC {
	
	@Autowired
	private JdbcTemplate conexion;
	
	public List<Plan> consultar(){
		String sql = "SELECT * FROM planes WHERE activo = 1";
		return conexion.query(sql, new PlanRM());
	}
	
	public Plan buscar(int id) {
		String sql = "SELECT * FROM planes WHERE id=? AND activo = 1";
		return conexion.queryForObject(sql, new PlanRM(), id);
	}
	
	
	public int insertar(Plan nuevo_plan) {
		String sql = "INSERT INTO planes(descripcion, precio_mensual, calidad_video,"
				+ "resolucion, cantidad_dispositivos) VALUES (?, ?, ?, ?, ?)";
		conexion.update(sql, nuevo_plan.getDescripcion(), nuevo_plan.getPrecio_mensual(),
				nuevo_plan.getCalidad_video(), nuevo_plan.getResolucion(), 
				nuevo_plan.getCantidad_dispositivos());
		sql = "SELECT LAST_INSERT_ID()";
		return conexion.queryForObject(sql, Integer.class);
	}
}
