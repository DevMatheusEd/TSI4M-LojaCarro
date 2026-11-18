package br.org.edu.ifrn.LojaCarro.controllers;

import br.org.edu.ifrn.LojaCarro.model.Carro;
import br.org.edu.ifrn.LojaCarro.model.Usuario;
import br.org.edu.ifrn.LojaCarro.services.UsuarioService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/listar")
    public ResponseEntity<List<Usuario>> pesquisarTodosCarros() {
        List<Usuario> usuarios = usuarioService.listar();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<Usuario> findById(@PathVariable Long id) {
        Usuario usuario = usuarioService.findById(id);
        return ResponseEntity.ok(usuario);
    }

    @PostMapping("/salvar")
    public ResponseEntity<Usuario> salvarCarro(@RequestBody Usuario c) {
        Usuario savedCarro = usuarioService.save(c);
        return ResponseEntity.ok(savedCarro);
    }

    // Deletar carro (por ID)
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id) {
        usuarioService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Usuario> atualizarUsuario(@PathVariable Long id, @RequestBody Usuario u) {
        u.setId(id);  // Define o ID no objeto
        Usuario updatedUsuario = usuarioService.update(u, id);
        return ResponseEntity.ok(updatedUsuario);
    }

    @PostMapping("/login")
    public void login(@RequestParam String nome,
                      @RequestParam String senha,
                      HttpSession session,
                      HttpServletResponse response) throws IOException {
        Usuario usuario = usuarioService.buscarPorNome(nome);


        if (usuario != null && usuario.getSenha() != null && usuario.getSenha().equals(senha)) {
            session.setAttribute("usuarioLogado", usuario);
            response.sendRedirect("/carro.html");
// Vai para a tela principal após logar
        } else {
            response.sendRedirect("/index.html");
// Volta para o login se errar
        }
    }


    @GetMapping("/logout")
    public void logout(HttpSession session, HttpServletResponse response) throws IOException {
        session.invalidate();
        response.sendRedirect("/index.html");
    }
}
