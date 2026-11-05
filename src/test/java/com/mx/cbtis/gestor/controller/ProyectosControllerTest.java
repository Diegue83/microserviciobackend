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

import com.mx.cbtis.gestor.modelo.Proyectos;
import com.mx.cbtis.gestor.repository.ProtectosRepository;

@WebMvcTest(ProyectosController.class)
class ProyectosControllerTest {

	private static final String PROYECTO_JSON = "{\"id_proyecto\":197,\"nombre\":\"My API Rest\","
			+ "\"estado_proyecto\":\"En proceso\"}";

	@Autowired
	MockMvc mvc;

	@MockitoBean
	ProtectosRepository repo;

	private static Proyectos proyecto() {
		return new Proyectos(197, "My API Rest", "API del gestor", "2026-10-01", "2026-12-15", "En proceso",
				"/Images/animacion1.gif");
	}

	@Test
	@DisplayName("Buscar proyecto existente por id")
	void buscar_existente() throws Exception {
		when(repo.findById(197)).thenReturn(Optional.of(proyecto()));

		mvc.perform(get("/buscarProyecto").param("id_proyecto", "197"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.nombre").value("My API Rest"));
	}

	@Test
	@DisplayName("Buscar proyecto inexistente: devuelve un proyecto vacío")
	void buscar_inexistente() throws Exception {
		when(repo.findById(9999)).thenReturn(Optional.empty());

		mvc.perform(get("/buscarProyecto").param("id_proyecto", "9999"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id_proyecto").doesNotExist());
	}

	@Test
	@DisplayName("Alta, actualización y baja devuelven 1 y llaman al repositorio")
	void alta_actualizacion_baja() throws Exception {
		mvc.perform(post("/addProyecto").contentType(MediaType.APPLICATION_JSON).content(PROYECTO_JSON))
				.andExpect(status().isOk()).andExpect(content().string("1"));
		mvc.perform(post("/updateProyecto").contentType(MediaType.APPLICATION_JSON).content(PROYECTO_JSON))
				.andExpect(status().isOk()).andExpect(content().string("1"));
		mvc.perform(delete("/deleteProyecto").contentType(MediaType.APPLICATION_JSON).content(PROYECTO_JSON))
				.andExpect(status().isOk()).andExpect(content().string("1"));

		verify(repo, times(2)).save(any(Proyectos.class));
		verify(repo).delete(any(Proyectos.class));
	}

	@Test
	@DisplayName("Listar proyectos")
	void listar() throws Exception {
		when(repo.findAll()).thenReturn(List.of(proyecto()));

		mvc.perform(post("/buscarProyectos"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()").value(1))
				.andExpect(jsonPath("$[0].id_proyecto").value(197));
	}
}
