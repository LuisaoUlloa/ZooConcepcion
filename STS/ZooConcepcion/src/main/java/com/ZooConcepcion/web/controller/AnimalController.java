package com.ZooConcepcion.web.controller;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ZooConcepcion.web.DAO.AnimalDao;
import com.ZooConcepcion.web.DAO.SectorDao;
import com.ZooConcepcion.web.DAO.TipoDao;
import com.ZooConcepcion.web.entity.Animal;
import com.ZooConcepcion.web.entity.Sector;
import com.ZooConcepcion.web.entity.Tipo;

@Controller
@RequestMapping("/animal")
public class AnimalController {
	
	
	@Autowired
	private AnimalDao aDao;
	
	@Autowired
	private SectorDao sDao;
	
	@Autowired
	private TipoDao tDao;
	
	@GetMapping("/listar")
	public String listar(Model model)
	{
		model.addAttribute("animales",aDao.crud().findAll());
		return "listar.html";
	}
	
	@GetMapping("/eliminar")
	public String eliminar(Model model, RedirectAttributes ra,
	@RequestParam("id") int id)
	{
		String mensaje="";
		
		try
		{
			aDao.crud().deleteById(id);
			mensaje="Eliminado Correctamente";
		}
		catch(Exception ex)
		{
			mensaje = "Error al Eliminar";
		}
		
		ra.addFlashAttribute("mensaje",mensaje);
		return "redirect:listar";
	
	}
	
	@GetMapping("/crear")
	public String crear(Model model) {
		
		
		model.addAttribute("sector", sDao.crud().findAll());
		model.addAttribute("tipos", tDao.crud().findAll());
		
		return "agregar.html";
	}
	
	
	@PostMapping("/almacenar")
	public String almacenar(Model model,
	RedirectAttributes ra,
	@RequestParam("txtNombre") String nombre,
	@RequestParam("txtPeso") float peso,
	@RequestParam("cboTipo") int tipo,
	@RequestParam("cboGenero") int genero,
	@RequestParam("txtFechaNacimiento")
	@DateTimeFormat(pattern="yyyy-MM-dd") java.util.Date fechaNacimiento,
	@RequestParam("cboSector") int sector,
	@RequestParam("txtFechaIngreso")
	@DateTimeFormat(pattern="yyyy-MM-dd") java.util.Date fechaIngreso,
	@RequestParam("txtFechaDefuncion")
	@DateTimeFormat(pattern="yyyy-MM-dd") java.util.Date fechaDefuncion
	)
	{
		String mensaje="";
		try
		{
			
			
			Tipo eTipo = new Tipo();
			eTipo.setId(tipo);
			
			Sector eSector = new Sector();
			eSector.setId(sector);
			
			Animal animal = new Animal();
			animal.setNombre(nombre);
			animal.setPeso(peso);
			animal.setTipo(eTipo);
			animal.setGenero(genero);
			animal.setFechaNacimiento(fechaNacimiento);
			animal.setSector(eSector);
			animal.setFechaIngreso(fechaIngreso);
			animal.setFechaDefuncion(fechaDefuncion);
			
			Animal ag = aDao.crud().save(animal);
			
			if(ag != null)
			{
				mensaje = "Agregado Correctamente";
			}
			
			
		}
		catch (Exception e) {
			mensaje = e.getMessage().toString();
		}
		
		ra.addFlashAttribute("mensaje",mensaje);
		return "redirect:crear";
		
		
	}
	
	@GetMapping("/editar")
	public String editar(Model model,RedirectAttributes ra,
    @RequestParam("id") int id
    )
	{
		Animal a = null;
		try
		{
			 a = aDao.crud().findById(id).get();
		}
		catch(Exception e)
		{
		   ra.addFlashAttribute("mensaje","El animal no existe");
		   return "redirect:listar";
		}
		
		model.addAttribute("a",a);
		model.addAttribute("sector", sDao.crud().findAll());
		model.addAttribute("tipos", tDao.crud().findAll());
		
		
		return "modificar.html";
	}
	
	@PostMapping("/actualizar")
	public String actualizar(Model model,
	@RequestParam("txtId") int id,		
	RedirectAttributes ra,
	@RequestParam("txtNombre") String nombre,
	@RequestParam("txtPeso") float peso,
	@RequestParam("cboTipo") int tipo,
	@RequestParam("cboGenero") int genero,
	@RequestParam("txtFechaNacimiento")
	@DateTimeFormat(pattern="yyyy-MM-dd") java.util.Date fechaNacimiento,
	@RequestParam("cboSector") int sector,
	@RequestParam("txtFechaIngreso")
	@DateTimeFormat(pattern="yyyy-MM-dd") java.util.Date fechaIngreso,
	@RequestParam("txtFechaDefuncion")
	@DateTimeFormat(pattern="yyyy-MM-dd") java.util.Date fechaDefuncion
	)
	{
		String mensaje="Error al modificar";
		try
		{
			
			
			Tipo eTipo = new Tipo();
			eTipo.setId(tipo);
			
			Sector eSector = new Sector();
			eSector.setId(sector);
			
			Animal animal = aDao.crud().findById(id).get();
			
			if(animal !=null)
			{
				animal.setNombre(nombre);
				animal.setPeso(peso);
				animal.setTipo(eTipo);
				animal.setGenero(genero);
				animal.setFechaNacimiento(fechaNacimiento);
				animal.setSector(eSector);
				animal.setFechaIngreso(fechaIngreso);
				animal.setFechaDefuncion(fechaDefuncion);
				
				Animal ag = aDao.crud().save(animal);
				
				if(ag != null)
				{
					mensaje = "Modificado Correctamente";
				}
				
			}
			else
			{
				mensaje = "Animal no existe";
			}
			
				
		}
		catch (Exception e) {
			mensaje = e.getMessage().toString();
		}
		
		ra.addFlashAttribute("mensaje",mensaje);
		return "redirect:listar";
		
		
	}
	

}
