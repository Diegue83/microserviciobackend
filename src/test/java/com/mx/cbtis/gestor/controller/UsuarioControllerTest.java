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

import com.mx.cbtis.gestor.modelo.Usuario;
import com.mx.cbtis.gestor.repository.UsuarioRepository;

@WebMvcTest(UsuarioController.class)
class UsuarioControllerTest {

	private static final String USUARIO_JSON = "{\"id_usuario\":131,\"nombre\":\"Ana\","
			+ "\"correo_electronico\":\"ana@cbtis75.edu.mx\",\"contrasena\":\"0722\",\"rol\":\"Estudiante\"}";

	@Autowired
	MockMvc mvc;

	@MockitoBean
	UsuarioRepository repo;

	private static Usuario ana() {
		return new Usuario(131, "Ana", "ana@cbtis75.edu.mx", "0722", "Estudiante", "#70FAB7");
	}

	@Test
	@DisplayName("Login correcto: devuelve el usuario encontrado")
	void login_valido() throws Exception {
		when(repo.findByCorreoPass("ana@cbtis75.edu.mx", "0722")).thenReturn(Optional.of(ana()));

		mvc.perform(post("/getUsuarioLogin").contentType(MediaType.APPLICATION_JSON).content(USUARIO_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id_usuario").value(131))
				.andExpect(jsonPath("$.nombre").value("Ana"));
	}

	@Test
	@DisplayName("Login incorrecto: devuelve la marca \"000\"")
	void login_invalido() throws Exception {
		when(repo.findByCorreoPass(any(), any())).thenReturn(Optional.empty());

		mvc.perform(post("/getUsuarioLogin").contentType(MediaType.APPLICATION_JSON).content(USUARIO_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.nombre").value("000"))
				.andExpect(jsonPath("$.id_usuario").doesNotExist());
	}

	@Test
	@DisplayName("Buscar usuario existente por id")
	void buscar_existente() throws Exception {
		when(repo.findById(131)).thenReturn(Optional.of(ana()));

		mvc.perform(get("/buscarUsuario").param("id", "131"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.correo_electronico").value("ana@cbtis75.edu.mx"));
	}

	@Test
	@DisplayName("Buscar usuario inexistente: devuelve un usuario vacío")
	void buscar_inexistente() throws Exception {
		when(repo.findById(9999)).thenReturn(Optional.empty());

		mvc.perform(get("/buscarUsuario").param("id", "9999"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id_usuario").doesNotExist());
	}

	@Test
	@DisplayName("Alta, actualización y baja devuelven 1 y llaman al repositorio")
	void alta_actualizacion_baja() throws Exception {
		mvc.perform(post("/addUsuario").contentType(MediaType.APPLICATION_JSON).content(USUARIO_JSON))
				.andExpect(status().isOk()).andExpect(content().string("1"));
		mvc.perform(post("/updateUsuario").contentType(MediaType.APPLICATION_JSON).content(USUARIO_JSON))
				.andExpect(status().isOk()).andExpect(content().string("1"));
		mvc.perform(delete("/deleteUsuario").contentType(MediaType.APPLICATION_JSON).content(USUARIO_JSON))
				.andExpect(status().isOk()).andExpect(content().string("1"));

		verify(repo, times(2)).save(any(Usuario.class));
		verify(repo).delete(any(Usuario.class));
	}

	@Test
	@DisplayName("Listar usuarios")
	void listar() throws Exception {
		when(repo.findAll()).thenReturn(List.of(ana(), new Usuario()));

		mvc.perform(post("/buscarUsuarios"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()").value(2))
				.andExpect(jsonPath("$[0].nombre").value("Ana"));
	}
}
