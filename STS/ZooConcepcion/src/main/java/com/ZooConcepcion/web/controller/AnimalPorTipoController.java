package com.ZooConcepcion.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ZooConcepcion.web.DAO.AnimalDao;
import com.ZooConcepcion.web.DAO.TipoDao;
import com.ZooConcepcion.web.entity.Animal;

@Controller
@RequestMapping("/estadisticas")
public class AnimalPorTipoController {

	@Autowired
	private TipoDao tDao;

	@Autowired
	private AnimalDao animalDao;

	@GetMapping("/animalTipo")
	public String animalTipo(Model model) {
		model.addAttribute("tipos", tDao.crud().findAll());
		return "animalesPorTipo.html";
	}

	@GetMapping("/animalFecha")
	public String animalFecha() {
		return "animalesPorFecha.html";
	}

	@PostMapping("/buscarTipo")
	public String buscarTipo(Model model, RedirectAttributes ra, @RequestParam("cboTipo") int tipo) {

		List<Animal> animales = animalDao.buscarAnimalesTipo(tipo);
		model.addAttribute("animales", animales);
		model.addAttribute("tipos", tDao.crud().findAll());
		return "animalesPorTipo.html";

	}

	@PostMapping("/buscarFecha")
	public String buscarFecha(Model model, RedirectAttributes ra,
			@RequestParam("txtFechaIngreso") @DateTimeFormat(pattern = "yyyy-MM-dd") java.util.Date fechaIngreso,
			@RequestParam("txtFechaFin") @DateTimeFormat(pattern = "yyyy-MM-dd") java.util.Date fechaFin) {

		if (fechaIngreso == null || fechaFin == null) {
			String mensaje = "Validar Fechas de Ingreso";
			ra.addFlashAttribute("mensaje", mensaje);
		} else {
			List<Animal> animales = animalDao.buscarAnimalesFecha(fechaIngreso, fechaFin);
			model.addAttribute("animales", animales);

		}
		return "animalesPorFecha.html";

	}

}
