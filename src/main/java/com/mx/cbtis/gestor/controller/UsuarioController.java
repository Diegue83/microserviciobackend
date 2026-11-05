package com.mx.cbtis.gestor.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mx.cbtis.gestor.dto.UsuarioDTO;
import com.mx.cbtis.gestor.modelo.Usuario;
import com.mx.cbtis.gestor.repository.UsuarioRepository;

@RestController
public class UsuarioController {

	private final UsuarioRepository usuariotable;

	public UsuarioController(UsuarioRepository usuariotable) {
		this.usuariotable = usuariotable;
	}

	@PostMapping("/getUsuarioLogin")
	public Usuario getUsuarioLogin(@RequestBody UsuarioDTO userParam) {

		Optional<Usuario> optionaluser = usuariotable.findByCorreoPass(
				userParam.correoElectronico(),
				userParam.contrasena());

		Usuario user = new Usuario();

		if(optionaluser.isPresent()) {
			user = optionaluser.get();
		}else {
			user.setNombre("000");
		}

		return user;
	}

	@GetMapping("/buscarUsuario")
	public Usuario getUsuario(@RequestParam int id) {

		Usuario buscar = new Usuario();
		Optional<Usuario> usuario = usuariotable.findById(id);
		if(usuario.isPresent())
		{
			buscar = usuario.get();
		}

		return buscar;
	}

	@PostMapping("/addUsuario")
	public int addUsuario(@RequestBody UsuarioDTO usuario) {
		int flag = 0;
		if(usuario != null) {
			usuariotable.save(usuario.toEntity());
			flag = 1;
		}

		return flag;
	}

	@PostMapping("/updateUsuario")
	public int updateUsuario(@RequestBody UsuarioDTO usuario) {
		int flag = 0;
		if(usuario != null) {
			usuariotable.save(usuario.toEntity());
			flag = 1;
		}

		return flag;
	}

	@DeleteMapping("/deleteUsuario")
	public int deleteUsuario(@RequestBody UsuarioDTO usuario) {
		int flag = 0;
		if(usuario != null) {
			usuariotable.delete(usuario.toEntity());
			flag = 1;
		}

		return flag;
	}

	@PostMapping("/buscarUsuarios")
	public List<Usuario> getUsuarios(){
		List<Usuario> listaUsuarios = new ArrayList<>();
		usuariotable.findAll().forEach(listaUsuarios::add);
		return listaUsuarios;
	}
}
