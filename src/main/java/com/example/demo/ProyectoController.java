package com.example.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import com.example.demo.Usuario;

@Controller
public class ProyectoController {
	
	@Autowired
    private UsuarioServicio servicio;
	
	@Autowired
    private PedidoServicio servicio2;
	
	@Autowired
    private ComidaServicio servicio3;

	@GetMapping("/") // Página principal
	public String index(HttpSession session, HttpServletRequest request, Model modelo) {
		String usuarioLoggeado = (String) session.getAttribute("usuarioLoggeado");
		
		Cookie[] cookies = request.getCookies();
			
			if (cookies != null) { // Buscamos si hay alguna cookie
				for (Cookie cookie : cookies) {
                    if ("sesion".equals(cookie.getName())) {
                    	usuarioLoggeado = cookie.getValue();
                        session.setAttribute("usuarioLoggeado", usuarioLoggeado); // Restauramos la sesión
                        break;
                    }
                }
			}
		
		if (usuarioLoggeado == null) { // Si no hay inicio de sesión volvemos al login
			return "redirect:/login";
		}
		
		modelo.addAttribute("usuario", usuarioLoggeado);
		
		Usuario usuario = servicio.buscarUsuarioPorNombre(usuarioLoggeado);
		
		modelo.addAttribute("usuario2", usuario);
		
		Pedido pedido = new Pedido(usuario, "", "", "");
        modelo.addAttribute("pedido", pedido);
        
        modelo.addAttribute("rol", usuario.getRol());

		Comida h1 = new Comida("Doble Cheeseburger", "Hamburguesa", 4.5);
        Comida h2 = new Comida("Big Mac", "Hamburguesa", 4.5);
        Comida h3 = new Comida("Cuarto de Libra", "Hamburguesa", 5.5);
        Comida h4 = new Comida("Patatas Fritas", "Patatas", 0.5);
        Comida h5 = new Comida("Patatas Deluxe", "Patatas", 0.75);
        Comida h6 = new Comida("Patatas Gratinadas", "Patatas", 1);
        Comida h7 = new Comida("Coca Cola", "Bebida", 1.5);
        Comida h8 = new Comida("Fanta de Naranja", "Bebida", 1.5);
        Comida h9 = new Comida("Agua", "Bebida", 1);
        
        servicio3.guardarComida(h1);
        servicio3.guardarComida(h2);
        servicio3.guardarComida(h3);
        servicio3.guardarComida(h4);
        servicio3.guardarComida(h5);
        servicio3.guardarComida(h6);
        servicio3.guardarComida(h7);
        servicio3.guardarComida(h8);
        servicio3.guardarComida(h9);
        
        List<Comida> comidas = servicio3.listarComida();
        
        modelo.addAttribute("comidas", comidas);
        
        
		
		return "index";
	}
	
	@PostMapping("/pedido")
	public String guardarPedido(@ModelAttribute("pedido") Pedido pedido, @RequestParam(name="hamburguesa", required=true, defaultValue="") String hamburguesa, @RequestParam(name="patatas", required=true, defaultValue="") String patatas, @RequestParam(name="bebida", required=true, defaultValue="") String bebida, Model modelo, HttpSession session) {
		modelo.addAttribute("hamburguesa", hamburguesa); // Enviamos las variables correspondientes al pedido
		modelo.addAttribute("patatas", patatas);
		modelo.addAttribute("bebida", bebida);
		
		Comida hamb = servicio3.buscarComidaPorNombre(hamburguesa);
		Comida pat = servicio3.buscarComidaPorNombre(patatas);
		Comida beb = servicio3.buscarComidaPorNombre(bebida);
		
		double suma = hamb.getPrecio() + pat.getPrecio() + beb.getPrecio();
		
		modelo.addAttribute("suma", suma);
		
		servicio2.guardarPedido(pedido);
        return "pedido";
    }

	@GetMapping("/login") // Página de login
	public String login() {
		return "login";
	}
	
	@PostMapping("/login") // Página de login tras enviar el formulario
	public String login(@RequestParam(name="usuario", required=true) String usuario, @RequestParam(name="password", required=true) String password, HttpSession session, HttpServletResponse response, Model modelo) {
		List<Usuario> usuarios = servicio.listarUsuario();
		
		for (Usuario user : usuarios) {
			if (user.getNombre().equals(usuario) && user.getPassword().equals(password)) {
				session.setAttribute("usuarioLoggeado", usuario);
	            
	            Cookie cookie = new Cookie("sesion", usuario);
	            cookie.setMaxAge(365 * 24 * 3600); // 1 año de validez
	            cookie.setPath("/"); // Disponible en toda la app
	            response.addCookie(cookie);
	            
	            return "redirect:/"; // Nos redirige a la página principal si el usuario y contraseña son correctos
			}
		}
		
        modelo.addAttribute("error", "Usuario o contraseña incorrectos");
		return "login"; // Si el usuario o contraseña no son correctos, nos redirige a la página de login y lanza un mensaje de error
	}
	
	@GetMapping("/cerrar") // Página de cierre de sesión
	public String cerrar(HttpSession session, HttpServletResponse response) {
		session.invalidate(); // Borramos la sesión
		Cookie cookie = new Cookie("sesion", null); // Eliminamos las cookies
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
		return "login";
	}
	
	@GetMapping("/registro") // Página de registro
	public String registro(Model modelo) {
		modelo.addAttribute("usuario", new Usuario());
		return "registro";
	}
	
	@PostMapping("/registro") // Página de registro
	public String registro(@Valid @ModelAttribute("usuario") Usuario usuario, BindingResult bindingResult, @RequestParam(name="password2", required=true) String password2, @RequestParam(name="adminPassword", required=true) String adminPassword, Model modelo) {
		
		if (bindingResult.hasErrors()) {
	        return "registro";
	    }
		
		if (!usuario.getPassword().equals(password2)) {
			modelo.addAttribute("error", "No coinciden las contraseñas");
			return "registro";
		}
		
		if (servicio.buscarUsuarioPorNombre(usuario.getNombre()) != null) {
			modelo.addAttribute("error", "El nombre de usuario ya existe");
			return "registro";
		}
		
		String rol = "";
		
		if (adminPassword.equals("AdMIn@_741_?adMIN")) {
			rol = "admin";
		} else {
			rol = "user";
		}
		
		usuario.setRol(rol);
		
		servicio.guardarUsuario(usuario);
		
		return "redirect:/login";
	}
	
	@GetMapping("/administracion") // Página de administración
	public String admin(Model modelo) {
		List<Usuario> usuarios = servicio.listarUsuario();
		modelo.addAttribute("usuarios", usuarios);
		
		return "administracion";
	}
	
	@GetMapping("/administracion/editar/{id}")
    public String formularioEditar(@PathVariable Integer id, Model modelo) {
        modelo.addAttribute("usuario", servicio.obtenerUsuario(id));
        return "editar";
    }

	@PostMapping("/administracion/{id}")
	public String actualizar(@Valid @ModelAttribute("usuario") Usuario usuario, BindingResult bindingResult, Model modelo, RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			return "redirect:/administracion/editar/" + usuario.getId();
	    }
		
		if (servicio.buscarUsuarioPorNombre(usuario.getNombre()) != null) {
			redirectAttributes.addFlashAttribute("error", "El nombre de usuario ya existe");
			return "redirect:/administracion/editar/{id}";
		}
	
		    servicio.actualizarUsuario(usuario);
		    return "redirect:/administracion";
	}

    @GetMapping("/administracion/{id}")
    public String eliminar(@PathVariable Integer id) {
        servicio.borrarUsuario(id);
        return "redirect:/administracion";
    }
    
    @GetMapping("/crear")
    public String formulario(Model modelo) {
        Usuario usuario = new Usuario();
        modelo.addAttribute("usuario", usuario);
        return "crear";
    }
    
    @PostMapping("/crear")
    public String guardarUsuario(@Valid @ModelAttribute("usuario") Usuario usuario, BindingResult bindingResult, Model modelo) {
    	
    	if (bindingResult.hasErrors()) {
	        return "crear";
	    }
		
		if (servicio.buscarUsuarioPorNombre(usuario.getNombre()) != null) {
			modelo.addAttribute("error", "El nombre de usuario ya existe");
			return "crear";
		}
    	
        servicio.guardarUsuario(usuario);
        return "redirect:/administracion";
    }
    
    @GetMapping("/pedidos")
    public String visualizarPedidos(@RequestParam(name="hamburguesa", required=false) String hamburguesa, HttpSession session, HttpServletRequest request, Model modelo) {
    	String usuarioLoggeado = (String) session.getAttribute("usuarioLoggeado");
		
		Usuario usuario = servicio.buscarUsuarioPorNombre(usuarioLoggeado);
		
		modelo.addAttribute("usuario", usuario);
		
		List <Pedido> pedidos = servicio2.listarPedido();
		
		List <Pedido> pedidosUsuario = new ArrayList<Pedido>();
		
		modelo.addAttribute("hamburguesa", hamburguesa);
		
		hamburguesa = hamburguesa.trim();
		hamburguesa = hamburguesa.toLowerCase();
		hamburguesa = hamburguesa.replace(" ", "");
		
		if (hamburguesa == "") {
			for (Pedido pedido : pedidos) {
				if (pedido.getIdUsuario().getId() == usuario.getId()) {
					pedidosUsuario.add(pedido);
				}
			}
		} else {
			for (Pedido pedido : pedidos) {
				String h = pedido.getHamburguesa().trim();
				h = h.toLowerCase();
				h = h.replace(" ", "");
				
				if (pedido.getIdUsuario().getId() == usuario.getId() && h.contains(hamburguesa)) {
					pedidosUsuario.add(pedido);
				}
			}
		}
		
		modelo.addAttribute("pedidos", pedidosUsuario);
    	
    	return "pedidos";
    }
}
