package com.mx.cbtis.gestor.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoInteractions;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.mx.cbtis.gestor.repository.AsignacionesRepository;
import com.mx.cbtis.gestor.repository.ComentariosRepository;
import com.mx.cbtis.gestor.repository.ProtectosRepository;
import com.mx.cbtis.gestor.repository.TareasRepository;
import com.mx.cbtis.gestor.repository.UsuarioRepository;

/**
 * Alta, actualización y baja con cuerpo nulo: devuelven 0 y no tocan el repositorio.
 * Spring rechaza un cuerpo vacío antes de llegar al controlador, por eso se llama directamente.
 */
class ValidacionNulosTest {

	@Test
	@DisplayName("Usuario: cuerpo nulo devuelve 0")
	void usuario() {
		UsuarioRepository repo = mock(UsuarioRepository.class);
		UsuarioController controller = new UsuarioController(repo);
		assertEquals(0, controller.addUsuario(null));
		assertEquals(0, controller.updateUsuario(null));
		assertEquals(0, controller.deleteUsuario(null));
		verifyNoInteractions(repo);
	}

	@Test
	@DisplayName("Proyectos: cuerpo nulo devuelve 0")
	void proyectos() {
		ProtectosRepository repo = mock(ProtectosRepository.class);
		ProyectosController controller = new ProyectosController(repo);
		assertEquals(0, controller.addProyecto(null));
		assertEquals(0, controller.updateProyecto(null));
		assertEquals(0, controller.deleteProyecto(null));
		verifyNoInteractions(repo);
	}

	@Test
	@DisplayName("Tareas: cuerpo nulo devuelve 0")
	void tareas() {
		TareasRepository repo = mock(TareasRepository.class);
		TareasController controller = new TareasController(repo);
		assertEquals(0, controller.addTareas(null));
		assertEquals(0, controller.updateTareas(null));
		assertEquals(0, controller.deleteTareas(null));
		verifyNoInteractions(repo);
	}

	@Test
	@DisplayName("Asignaciones: cuerpo nulo devuelve 0")
	void asignaciones() {
		AsignacionesRepository repo = mock(AsignacionesRepository.class);
		AsignacionesController controller = new AsignacionesController(repo);
		assertEquals(0, controller.addAsignacion(null));
		assertEquals(0, controller.updateAsignacion(null));
		assertEquals(0, controller.deleteAsignacion(null));
		verifyNoInteractions(repo);
	}

	@Test
	@DisplayName("Comentarios: cuerpo nulo devuelve 0")
	void comentarios() {
		ComentariosRepository repo = mock(ComentariosRepository.class);
		ComentariosController controller = new ComentariosController(repo);
		assertEquals(0, controller.addComentario(null));
		assertEquals(0, controller.updateComentario(null));
		assertEquals(0, controller.deleteComentario(null));
		verifyNoInteractions(repo);
	}
}
