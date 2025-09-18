package com.deone.base.bussoft.core.config.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.deone.base.bussoft.core.commons.libs.auth.jwt.JwtUtil;
import com.deone.base.bussoft.core.domain.app.bussines.service.user.UserService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class SecurityFilter extends OncePerRequestFilter{

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws  ServletException, IOException{
        final String authorizationHeader = request.getHeader("Authorization");
        String username = null;//almacenara el nombre
        String jwt = null;//almacenara el token jwt
        //validamos que el header no sea nulo y empiece con "Bearer"
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);//extraemos el token (ignoramos "Bearer")
            username = jwtUtil.extractUsername(jwt);//Extraemos el nombre de usuario desde el token
        }
        //si hay un username y todavia no hay autenticacion en el contexto de seguridad
        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            //cargamos los datos del usuario desde la bd
            UserDetails userDetails = userService.loadUserByUsername(username);
            //validamos el token con los datos del usuario
            if(jwtUtil.validateToken(jwt, userDetails)) {
                //creamos el objeto de autenticacion con el usuario y sus roles(authorities)
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                //Agregamos mas informacion de la peticion http al token(IP, sesion, etc)
                usernamePasswordAuthenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                //Establecemos al usuario autenticado en el contexto de seguridad
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }

        }
        //continuamos con la cadena de filtros(otros filtros o el controlador)
        filterChain.doFilter(request, response);
    }


}
