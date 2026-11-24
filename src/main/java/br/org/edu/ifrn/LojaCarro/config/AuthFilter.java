package br.org.edu.ifrn.LojaCarro.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String uri = req.getRequestURI();

        boolean rotaRestrita = uri.equals("/carro.html")
                || uri.equals("/listarCarros.html")
                || uri.startsWith("/carro/");

        if (rotaRestrita) {
            HttpSession session = req.getSession(false);

            if (session == null || session.getAttribute("usuarioLogado") == null) {
                if (uri.startsWith("/carro/")) {
                    res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    res.setContentType("text/plain;charset=UTF-8");
                    res.getWriter().write("Usuario nao autenticado.");
                } else {
                    res.sendRedirect("/index.html");
                }
                return;
            }
        }

        chain.doFilter(request, response);
    }
}