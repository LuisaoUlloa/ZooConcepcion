package com.ZooConcepcion.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.ZooConcepcion.web.DAO.UsuarioDao;
import com.ZooConcepcion.web.entity.Usuario;

@Component
public class CustomUserDetailService implements UserDetailsService {
	
	@Autowired
	private UsuarioDao usuarioDAO;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Usuario usuario = usuarioDAO.buscarPorNombreUsuario(username);
		
		//crearemos un Userbuilder para crear un usuario de
		//Spring
		
		UserBuilder user = null;
		
		if(usuario!=null) {
			//si existe el usuario lo transformo en un usuario
			//de Spring
			
			user = org.springframework.security.core.userdetails.User.withUsername(username);
			user.password(new BCryptPasswordEncoder().encode(usuario.getClave()));
			user.roles("USER");
			
			
		} else {
			//si el usuario no existe mandaremos una excepcion
			throw new UsernameNotFoundException("Usuario no encontrado");
		}
		
		return user.build();
	}
	
	

}
