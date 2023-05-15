package med.voll.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import med.voll.api.domain.usuarios.DatosAutenticarUsuario;

@RestController
@RequestMapping("/login")
public class AutenticacionController {
  
  @Autowired
  private AuthenticationManager authenticationManager;


  @PostMapping
  public ResponseEntity autenticarUsuario(DatosAutenticarUsuario datosAutenticarUsuario){
    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(datosAutenticarUsuario.login(), datosAutenticarUsuario.clave());
    authenticationManager.authenticate(token);
    return ResponseEntity.ok().build();


  }
}
