package com.ZooConcepcion.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ZooConcepcion.web.DAO.UsuarioDao;
import com.ZooConcepcion.web.entity.Tipo;
import com.ZooConcepcion.web.entity.Usuario;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {
	
	
	@Autowired
	
	private UsuarioDao uDao;
	
	@GetMapping("/inicio")
	public String inicio()
	{
		return "inicio.html";
	}
	
	@GetMapping("/listar")
	public String listar(Model model) {
		model.addAttribute("usuarios", uDao.crud().findAll());
		return "listarUsuarios.html";
	}
	
	@GetMapping("/crear")
	public String crear(Model model) {

		return "agregarUsuario.html";
	}
	
	@PostMapping("/almacenar")
	public String almacenar(Model model, RedirectAttributes ra, @RequestParam("txtNombre") String nombre,
			@RequestParam("txtpassword") String pass) {
		String mensaje = "";
		try {

			Usuario us = new Usuario();
			us.setUsuario(nombre);
			us.setClave(pass);

			Usuario ag = uDao.crud().save(us);

			if (ag != null) {
				mensaje = "Agregado Correctamente";
			}

		} catch (Exception e) {
			mensaje = e.getMessage().toString();
		}

		ra.addFlashAttribute("mensaje", mensaje);
		return "redirect:crear";

	}
	
	@GetMapping("/eliminar")
	public String eliminar(Model model, RedirectAttributes ra, @RequestParam("id") int id) {
		String mensaje = "";

		try {
			uDao.crud().deleteById(id);
			mensaje = "Eliminado Correctamente";
		} catch (Exception ex) {
			mensaje = "Error al Eliminar";
		}

		ra.addFlashAttribute("mensaje", mensaje);
		return "redirect:listar";

	}
	
	@GetMapping("/editar")
	public String editar(Model model, RedirectAttributes ra, @RequestParam("id") int id) {
		Usuario a = null;
		try {
			a = uDao.crud().findById(id).get();
		} catch (Exception e) {
			ra.addFlashAttribute("mensaje", "El usuario no existe");
			return "redirect:listar";
		}

		model.addAttribute("a", a);
		return "modificarUsuario.html";
	}
	
	@PostMapping("/actualizar")
	public String actualizar(Model model, @RequestParam("txtId") int id, RedirectAttributes ra,
			@RequestParam("txtNombre") String nombre, @RequestParam("txtPassword") String pass) {
		String mensaje = "Error al modificar";
		try {

			Usuario us = uDao.crud().findById(id).get();

			if (us != null) {
				us.setUsuario(nombre);
				us.setClave(pass);

				Usuario ag = uDao.crud().save(us);

				if (ag != null) {
					mensaje = "Modificado Correctamente";
				}

			} else {
				mensaje = "Usuario no existe";
			}

		} catch (Exception e) {
			mensaje = e.getMessage().toString();
		}

		ra.addFlashAttribute("mensaje", mensaje);
		return "redirect:listar";

	}

}
