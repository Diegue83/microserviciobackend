package com.mx.cbtis.gestor.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.mx.cbtis.gestor.modelo.Tareas;
import com.mx.cbtis.gestor.repository.TareasRepository;

@WebMvcTest(TareasController.class)
class TareasControllerTest {

	private static final String TAREA_JSON = "{\"id_tarea\":199,\"nombre\":\"Base de datos\",\"id_proyecto\":197}";

	@Autowired
	MockMvc mvc;

	@MockitoBean
	TareasRepository repo;

	private static Tareas tarea() {
		return new Tareas(199, "Base de datos", "Crear tablas", "2026-11-10", "terminado", "danger", 197);
	}

	@Test
	@DisplayName("Tareas de un proyecto: usa el id_proyecto recibido")
	void tareas_por_proyecto() throws Exception {
		when(repo.findByIdProyecto(197)).thenReturn(List.of(tarea()));

		mvc.perform(post("/getTarea").contentType(MediaType.APPLICATION_JSON).content("{\"id_proyecto\":197}"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()").value(1))
				.andExpect(jsonPath("$[0].nombre").value("Base de datos"));
	}

	@Test
	@DisplayName("Buscar tarea existente por id")
	void buscar_existente() throws Exception {
		when(repo.findById(199)).thenReturn(Optional.of(tarea()));

		mvc.perform(get("/buscarTarea").param("id_tarea", "199"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.lista").value("terminado"));
	}

	@Test
	@DisplayName("Buscar tarea inexistente: devuelve una tarea vacía")
	void buscar_inexistente() throws Exception {
		when(repo.findById(9999)).thenReturn(Optional.empty());

		mvc.perform(get("/buscarTarea").param("id_tarea", "9999"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id_tarea").doesNotExist());
	}

	@Test
	@DisplayName("Alta, actualización y baja devuelven 1 y llaman al repositorio")
	void alta_actualizacion_baja() throws Exception {
		mvc.perform(post("/addTareas").contentType(MediaType.APPLICATION_JSON).content(TAREA_JSON))
				.andExpect(status().isOk()).andExpect(content().string("1"));
		mvc.perform(post("/updateTareas").contentType(MediaType.APPLICATION_JSON).content(TAREA_JSON))
				.andExpect(status().isOk()).andExpect(content().string("1"));
		mvc.perform(delete("/deleteTareas").contentType(MediaType.APPLICATION_JSON).content(TAREA_JSON))
				.andExpect(status().isOk()).andExpect(content().string("1"));

		verify(repo, times(2)).save(any(Tareas.class));
		verify(repo).delete(any(Tareas.class));
	}

	@Test
	@DisplayName("Listar tareas")
	void listar() throws Exception {
		when(repo.findAll()).thenReturn(List.of(tarea(), new Tareas()));

		mvc.perform(post("/buscarTareas"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()").value(2));
	}
}
