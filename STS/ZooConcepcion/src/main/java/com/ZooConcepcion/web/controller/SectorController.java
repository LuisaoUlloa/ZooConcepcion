package com.ZooConcepcion.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ZooConcepcion.web.DAO.SectorDao;
import com.ZooConcepcion.web.entity.Sector;



@Controller
@RequestMapping("/sector")
public class SectorController {
	
	
	@Autowired
	private SectorDao sDao;
	
	@GetMapping("/listar")
	public String listar(Model model) {
		model.addAttribute("sectores", sDao.crud().findAll());
		return "listarSectores.html";
	}
	
	@GetMapping("/crear")
	public String crear(Model model) {

		return "agregarSector.html";
	}
	
	@PostMapping("/almacenar")
	public String almacenar(Model model, RedirectAttributes ra, @RequestParam("txtNombre") String nombre,
			@RequestParam("txtDescripcion") String descripcion) {
		String mensaje = "";
		try {

			Sector sec = new Sector();
			sec.setNombre(nombre);
			sec.setDescripcion(descripcion);

			Sector ag = sDao.crud().save(sec);

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
			sDao.crud().deleteById(id);
			mensaje = "Eliminado Correctamente";
		} catch (Exception ex) {
			mensaje = "Error al Eliminar";
		}

		ra.addFlashAttribute("mensaje", mensaje);
		return "redirect:listar";

	}
	
	@GetMapping("/editar")
	public String editar(Model model, RedirectAttributes ra, @RequestParam("id") int id) {
		Sector a = null;
		try {
			a = sDao.crud().findById(id).get();
		} catch (Exception e) {
			ra.addFlashAttribute("mensaje", "El sector no existe");
			return "redirect:listar";
		}

		model.addAttribute("a", a);
		return "modificarSector.html";
	}
	
	@PostMapping("/actualizar")
	public String actualizar(Model model, @RequestParam("txtId") int id, RedirectAttributes ra,
			@RequestParam("txtNombre") String nombre, @RequestParam("txtDescripcion") String descripcion) {
		String mensaje = "Error al modificar";
		try {

			Sector sec = sDao.crud().findById(id).get();

			if (sec != null) {
				sec.setNombre(nombre);
				sec.setDescripcion(descripcion);

				Sector ag = sDao.crud().save(sec);

				if (ag != null) {
					mensaje = "Modificado Correctamente";
				}

			} else {
				mensaje = "Sector no existe";
			}

		} catch (Exception e) {
			mensaje = e.getMessage().toString();
		}

		ra.addFlashAttribute("mensaje", mensaje);
		return "redirect:listar";

	}
	
	


}
