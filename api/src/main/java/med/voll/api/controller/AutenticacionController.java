package med.voll.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import med.voll.api.domain.usuarios.DatosAutenticarUsuario;
import med.voll.api.domain.usuarios.Usuario;
import med.voll.api.infra.security.DatosJWTToken;
import med.voll.api.infra.security.TokenService;

@RestController
@RequestMapping("/login")
public class AutenticacionController {
  
  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private TokenService tokenService;


  @PostMapping
  public ResponseEntity autenticarUsuario(@RequestBody @Valid DatosAutenticarUsuario datosAutenticarUsuario){
    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(datosAutenticarUsuario.login(), datosAutenticarUsuario.clave());
    var usuarioAutenticado =  authenticationManager.authenticate(token);
    var JWTtoken = tokenService.generarToken((Usuario)usuarioAutenticado.getPrincipal());//se valida el usuario que ya se autentico
    return ResponseEntity.ok(new DatosJWTToken(JWTtoken));


  }
}
