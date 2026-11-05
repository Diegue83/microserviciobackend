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

import com.mx.cbtis.gestor.modelo.Asignaciones;
import com.mx.cbtis.gestor.repository.AsignacionesRepository;

@WebMvcTest(AsignacionesController.class)
class AsignacionesControllerTest {

	private static final String ASIGNACION_JSON = "{\"id_asignacion\":5,\"id_usuario\":131,\"id_tarea\":199}";

	@Autowired
	MockMvc mvc;

	@MockitoBean
	AsignacionesRepository repo;

	@Test
	@DisplayName("Buscar asignación existente por id")
	void buscar_existente() throws Exception {
		when(repo.findById(5)).thenReturn(Optional.of(new Asignaciones(5, 131, 199, "2026-11-01")));

		mvc.perform(get("/buscarAsignacion").param("id", "5"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id_usuario").value(131));
	}

	@Test
	@DisplayName("Buscar asignación inexistente: devuelve una asignación vacía")
	void buscar_inexistente() throws Exception {
		when(repo.findById(9999)).thenReturn(Optional.empty());

		mvc.perform(get("/buscarAsignacion").param("id", "9999"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id_asignacion").doesNotExist());
	}

	@Test
	@DisplayName("Alta, actualización y baja devuelven 1 y llaman al repositorio")
	void alta_actualizacion_baja() throws Exception {
		mvc.perform(post("/addAsignacion").contentType(MediaType.APPLICATION_JSON).content(ASIGNACION_JSON))
				.andExpect(status().isOk()).andExpect(content().string("1"));
		mvc.perform(post("/updateAsignacion").contentType(MediaType.APPLICATION_JSON).content(ASIGNACION_JSON))
				.andExpect(status().isOk()).andExpect(content().string("1"));
		mvc.perform(delete("/deleteAsignacion").contentType(MediaType.APPLICATION_JSON).content(ASIGNACION_JSON))
				.andExpect(status().isOk()).andExpect(content().string("1"));

		verify(repo, times(2)).save(any(Asignaciones.class));
		verify(repo).delete(any(Asignaciones.class));
	}

	@Test
	@DisplayName("Listar asignaciones")
	void listar() throws Exception {
		when(repo.findAll()).thenReturn(List.of(new Asignaciones(5, 131, 199, "2026-11-01")));

		mvc.perform(post("/buscarAsignaciones"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()").value(1));
	}
}
