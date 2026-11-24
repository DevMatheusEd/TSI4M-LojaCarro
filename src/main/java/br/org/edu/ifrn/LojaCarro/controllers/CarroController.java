package br.org.edu.ifrn.LojaCarro.controllers;

import br.org.edu.ifrn.LojaCarro.model.Carro;
import br.org.edu.ifrn.LojaCarro.services.CarroService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/carro")
public class CarroController {

    @Autowired
    private CarroService carroService;

    @PostMapping("/salvar")
    public void salvarCarro(@ModelAttribute Carro c, HttpSession session, HttpServletResponse response) throws IOException {
        if (session.getAttribute("usuarioLogado") == null) {
            response.sendRedirect("/index.html");
            return;
        }
        carroService.save(c);
        response.sendRedirect("/carro.html");
    }

    @GetMapping("/listarCarros")
    public ResponseEntity<List<Carro>> pesquisarTodosCarros(HttpSession session) {
        if (session.getAttribute("usuarioLogado") == null) {
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.ok(carroService.findAll());
    }

    @GetMapping("/buscarPorId")
    public ResponseEntity<Carro> pesquisarCarroPorId(@RequestParam Long id, HttpSession session) {
        if (session.getAttribute("usuarioLogado") == null) {
            return ResponseEntity.status(401).build();
        }
        Optional<Carro> carro = carroService.findById(id);
        return carro.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/atualizar")
    public void atualizarCarro(@ModelAttribute Carro c, HttpSession session, HttpServletResponse response) throws IOException {
        if (session.getAttribute("usuarioLogado") == null) {
            response.sendRedirect("/index.html");
            return;
        }
        carroService.update(c);
        response.sendRedirect("/carro.html");
    }

    @PostMapping("/deletar")
    public void deletarCarro(@RequestParam Long id, HttpSession session, HttpServletResponse response) throws IOException {
        if (session.getAttribute("usuarioLogado") == null) {
            response.sendRedirect("/index.html");
            return;
        }
        carroService.deleteById(id);
        response.sendRedirect("/carro.html?deletado=1");
    }
}