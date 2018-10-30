package com.ZooConcepcion.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ZooConcepcion.web.DAO.TipoDao;
import com.ZooConcepcion.web.entity.Animal;
import com.ZooConcepcion.web.entity.Sector;
import com.ZooConcepcion.web.entity.Tipo;

@Controller
@RequestMapping("/tipo")
public class TipoController {

	@Autowired
	private TipoDao tDao;

	@GetMapping("/listar")
	public String listar(Model model) {
		model.addAttribute("tipos", tDao.crud().findAll());
		return "listarTipoAnimal.html";
	}

	@GetMapping("/eliminar")
	public String eliminar(Model model, RedirectAttributes ra, @RequestParam("id") int id) {
		String mensaje = "";

		try {
			tDao.crud().deleteById(id);
			mensaje = "Eliminado Correctamente";
		} catch (Exception ex) {
			mensaje = "Error al Eliminar";
		}

		ra.addFlashAttribute("mensaje", mensaje);
		return "redirect:listar";

	}

	@GetMapping("/crear")
	public String crear(Model model) {

		return "agregarTipoAnimal.html";
	}

	@PostMapping("/almacenar")
	public String almacenar(Model model, RedirectAttributes ra, @RequestParam("txtNombre") String nombre,
			@RequestParam("txtDescripcion") String descripcion) {
		String mensaje = "";
		try {

			Tipo tipo = new Tipo();
			tipo.setNombre(nombre);
			tipo.setDescripcion(descripcion);

			Tipo ag = tDao.crud().save(tipo);

			if (ag != null) {
				mensaje = "Agregado Correctamente";
			}

		} catch (Exception e) {
			mensaje = e.getMessage().toString();
		}

		ra.addFlashAttribute("mensaje", mensaje);
		return "redirect:crear";

	}

	@GetMapping("/editar")
	public String editar(Model model, RedirectAttributes ra, @RequestParam("id") int id) {
		Tipo a = null;
		try {
			a = tDao.crud().findById(id).get();
		} catch (Exception e) {
			ra.addFlashAttribute("mensaje", "El tipo de animal no existe");
			return "redirect:listar";
		}

		model.addAttribute("a", a);
		return "modificarTipoAnimal.html";
	}

	@PostMapping("/actualizar")
	public String actualizar(Model model, @RequestParam("txtId") int id, RedirectAttributes ra,
			@RequestParam("txtNombre") String nombre, @RequestParam("txtDescripcion") String descripcion) {
		String mensaje = "Error al modificar";
		try {

			Tipo tipo = tDao.crud().findById(id).get();

			if (tipo != null) {
				tipo.setNombre(nombre);
				tipo.setDescripcion(descripcion);

				Tipo ag = tDao.crud().save(tipo);

				if (ag != null) {
					mensaje = "Modificado Correctamente";
				}

			} else {
				mensaje = "Tipo animal no existe";
			}

		} catch (Exception e) {
			mensaje = e.getMessage().toString();
		}

		ra.addFlashAttribute("mensaje", mensaje);
		return "redirect:listar";

	}

}
