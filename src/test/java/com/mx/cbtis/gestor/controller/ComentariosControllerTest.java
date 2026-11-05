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

import com.mx.cbtis.gestor.modelo.Comentarios;
import com.mx.cbtis.gestor.repository.ComentariosRepository;

@WebMvcTest(ComentariosController.class)
class ComentariosControllerTest {

	private static final String COMENTARIO_JSON = "{\"id_comentario\":10,\"id_usuario\":\"131\",\"id_tarea\":199,"
			+ "\"texto_comentario\":\"Listo\"}";

	@Autowired
	MockMvc mvc;

	@MockitoBean
	ComentariosRepository repo;

	private static Comentarios comentario() {
		return new Comentarios(10, "131", 199, "Listo", "2026-11-01 10:00");
	}

	@Test
	@DisplayName("Comentarios de una tarea: usa el id_tarea recibido")
	void comentarios_por_tarea() throws Exception {
		when(repo.findByTarea(199)).thenReturn(List.of(comentario()));

		mvc.perform(post("/getComentario").contentType(MediaType.APPLICATION_JSON).content("{\"id_tarea\":199}"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].texto_comentario").value("Listo"));
	}

	@Test
	@DisplayName("Buscar comentario existente por id")
	void buscar_existente() throws Exception {
		when(repo.findById(10)).thenReturn(Optional.of(comentario()));

		mvc.perform(get("/buscarComentario").param("id", "10"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id_tarea").value(199));
	}

	@Test
	@DisplayName("Buscar comentario inexistente: devuelve un comentario vacío")
	void buscar_inexistente() throws Exception {
		when(repo.findById(9999)).thenReturn(Optional.empty());

		mvc.perform(get("/buscarComentario").param("id", "9999"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id_comentario").doesNotExist());
	}

	@Test
	@DisplayName("Alta, actualización y baja devuelven 1 y llaman al repositorio")
	void alta_actualizacion_baja() throws Exception {
		mvc.perform(post("/addComentario").contentType(MediaType.APPLICATION_JSON).content(COMENTARIO_JSON))
				.andExpect(status().isOk()).andExpect(content().string("1"));
		mvc.perform(post("/updateComentario").contentType(MediaType.APPLICATION_JSON).content(COMENTARIO_JSON))
				.andExpect(status().isOk()).andExpect(content().string("1"));
		mvc.perform(delete("/deleteComentario").contentType(MediaType.APPLICATION_JSON).content(COMENTARIO_JSON))
				.andExpect(status().isOk()).andExpect(content().string("1"));

		verify(repo, times(2)).save(any(Comentarios.class));
		verify(repo).delete(any(Comentarios.class));
	}

	@Test
	@DisplayName("Listar comentarios")
	void listar() throws Exception {
		when(repo.findAll()).thenReturn(List.of(comentario()));

		mvc.perform(post("/buscarComentarios"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()").value(1));
	}
}
