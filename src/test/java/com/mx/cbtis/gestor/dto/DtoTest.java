package com.mx.cbtis.gestor.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mx.cbtis.gestor.modelo.Asignaciones;
import com.mx.cbtis.gestor.modelo.Comentarios;
import com.mx.cbtis.gestor.modelo.Proyectos;
import com.mx.cbtis.gestor.modelo.Tareas;
import com.mx.cbtis.gestor.modelo.Usuario;

/**
 * Los DTO leen el mismo JSON que enviaba el front y se convierten en la entidad correcta.
 */
class DtoTest {

	private final ObjectMapper json = new ObjectMapper();

	@Test
	@DisplayName("UsuarioDTO: JSON con nombres originales → entidad Usuario")
	void usuario() throws Exception {
		UsuarioDTO dto = json.readValue("{\"id_usuario\":131,\"nombre\":\"Ana\",\"correo_electronico\":\"ana@cbtis75.edu.mx\","
				+ "\"contrasena\":\"0722\",\"rol\":\"Estudiante\",\"imagen_usuario\":\"#70FAB7\"}", UsuarioDTO.class);
		assertEquals(131, dto.idUsuario());
		assertEquals("Ana", dto.nombre());
		assertEquals("Estudiante", dto.rol());
		assertEquals("#70FAB7", dto.imagenUsuario());

		Usuario u = dto.toEntity();
		assertEquals(131, u.getId_usuario());
		assertEquals("ana@cbtis75.edu.mx", u.getCorreo_electronico());
		assertEquals("0722", u.getContrasena());
		assertEquals("#70FAB7", u.getImagen_usuario());
	}

	@Test
	@DisplayName("ProyectoDTO: JSON con nombres originales → entidad Proyectos")
	void proyecto() throws Exception {
		ProyectoDTO dto = json.readValue("{\"id_proyecto\":197,\"nombre\":\"My API Rest\",\"descripcion\":\"API\","
				+ "\"fecha_inicio\":\"2026-10-01\",\"fecha_finalizacion\":\"2026-12-15\",\"estado_proyecto\":\"En proceso\","
				+ "\"fondo\":\"/Images/naranja.png\"}", ProyectoDTO.class);
		assertEquals(197, dto.idProyecto());
		assertEquals("My API Rest", dto.nombre());
		assertEquals("API", dto.descripcion());
		assertEquals("2026-10-01", dto.fechaInicio());
		assertEquals("2026-12-15", dto.fechaFinalizacion());
		assertEquals("En proceso", dto.estadoProyecto());
		assertEquals("/Images/naranja.png", dto.fondo());

		Proyectos p = dto.toEntity();
		assertEquals(197, p.getId_proyecto());
		assertEquals("2026-12-15", p.getFecha_finalizacion());
		assertEquals("/Images/naranja.png", p.getFondo());
	}

	@Test
	@DisplayName("TareaDTO: JSON con nombres originales → entidad Tareas")
	void tarea() throws Exception {
		TareaDTO dto = json.readValue("{\"id_tarea\":199,\"nombre\":\"Base de datos\",\"descripcion\":\"Tablas\","
				+ "\"fecha_vencimiento\":\"2026-11-10\",\"lista\":\"terminado\",\"estado\":\"danger\",\"id_proyecto\":197}",
				TareaDTO.class);
		assertEquals(199, dto.idTarea());
		assertEquals("Base de datos", dto.nombre());
		assertEquals("Tablas", dto.descripcion());
		assertEquals("2026-11-10", dto.fechaVencimiento());
		assertEquals("terminado", dto.lista());
		assertEquals("danger", dto.estado());
		assertEquals(197, dto.idProyecto());

		Tareas t = dto.toEntity();
		assertEquals(199, t.getId_tarea());
		assertEquals("terminado", t.getLista());
		assertEquals(197, t.getId_proyecto());
	}

	@Test
	@DisplayName("AsignacionDTO: JSON con nombres originales → entidad Asignaciones")
	void asignacion() throws Exception {
		AsignacionDTO dto = json.readValue("{\"id_asignacion\":5,\"id_usuario\":131,\"id_tarea\":199,"
				+ "\"fecha_asignacion\":\"2026-11-01\"}", AsignacionDTO.class);
		assertEquals(5, dto.idAsignacion());
		assertEquals(131, dto.idUsuario());
		assertEquals(199, dto.idTarea());
		assertEquals("2026-11-01", dto.fechaAsignacion());

		Asignaciones a = dto.toEntity();
		assertEquals(5, a.getId_asignacion());
		assertEquals("2026-11-01", a.getFecha_asignacion());
	}

	@Test
	@DisplayName("ComentarioDTO: JSON con nombres originales → entidad Comentarios")
	void comentario() throws Exception {
		ComentarioDTO dto = json.readValue("{\"id_comentario\":10,\"id_usuario\":\"131\",\"id_tarea\":199,"
				+ "\"texto_comentario\":\"Listo\",\"fecha_hora\":\"2026-11-01 10:00\"}", ComentarioDTO.class);
		assertEquals(10, dto.idComentario());
		assertEquals("131", dto.idUsuario());
		assertEquals(199, dto.idTarea());
		assertEquals("Listo", dto.textoComentario());
		assertEquals("2026-11-01 10:00", dto.fechaHora());

		Comentarios c = dto.toEntity();
		assertEquals(10, c.getId_comentario());
		assertEquals("Listo", c.getTexto_comentario());
	}
}
