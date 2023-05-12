package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.direccion.DatosDireccion;
import med.voll.api.domain.medico.DatosActualizarMedico;
import med.voll.api.domain.medico.DatosListadoMedico;
import med.voll.api.domain.medico.DatosRegistroMedico;
import med.voll.api.domain.medico.DatosRespuestaMedico;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;

import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

  @Autowired
  private MedicoRepository medicoRepository;

  @PostMapping
  public ResponseEntity<DatosRespuestaMedico> registrarMedico(
      @RequestBody @Valid DatosRegistroMedico datosRegistroMedico,
      UriComponentsBuilder uriComponentsBuilder) {
    Medico medico = medicoRepository.save(new Medico(datosRegistroMedico));
    DatosRespuestaMedico datosRespuestaMedico = new DatosRespuestaMedico(medico.getId(), medico.getNombre(),
        medico.getEmail(),
        medico.getTelefono(), medico.getEspecialidad().toString(),
        new DatosDireccion(medico.getDireccion().getCalle(), medico.getDireccion().getDistrito(),
            medico.getDireccion().getCiudad(), medico.getDireccion().getNumero(),
            medico.getDireccion().getComplemento()));

    URI url = uriComponentsBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
    return ResponseEntity.created(url).body(datosRespuestaMedico);
    /*
     * ResponseEntity este nos retorna los valores de respuesta que se usan
     * comunmente en la we
     * DataAccesObect/Objeto/Entidad
     * El objto medico de La base de datos de la entidad que manda a llamar a la
     * base de datos osea la entidad se crea
     * a partir de un obeto, esta es tomada por parte de hibernate y esta la
     * entiende como
     * la consulta hacia la base de datos
     * Ese obejeto creado contiene toda la informacion y es peligroso, para eso se
     * crea
     * un objeto de tipo record el cual va a tener la informacion que uno desea
     * mandar
     */
  }

  @GetMapping
  public ResponseEntity<Page<DatosListadoMedico>> listadoMedicos(@PageableDefault(size = 2) Pageable paginacion) {
    // return medicoRepository.findAll(paginacion).map(DatosListadoMedico::new);
    return ResponseEntity.ok(medicoRepository.findByActivoTrue(paginacion).map(DatosListadoMedico::new));
  }

  @PutMapping
  @Transactional
  public ResponseEntity actualizarMedico(@RequestBody @Valid DatosActualizarMedico datosActualizarMedico) {
    Medico medico = medicoRepository.getReferenceById(datosActualizarMedico.id());
    medico.actualizarDatos(datosActualizarMedico);
    return ResponseEntity.ok(new DatosRespuestaMedico(medico.getId(), medico.getNombre(), medico.getEmail(),
        medico.getTelefono(), medico.getEspecialidad().toString(),
        new DatosDireccion(medico.getDireccion().getCalle(), medico.getDireccion().getDistrito(),
            medico.getDireccion().getCiudad(), medico.getDireccion().getNumero(),
            medico.getDireccion().getComplemento())));
  }
      /*
     * aqui estor tomando una instancia de medico la cual se busca en base al id que
     * este viene de
     * datos actualizqar medico ahi esta ubicado el parametro y el parametro esta
     * almacenado en id
     */

  // DELETE LOGICO
  @DeleteMapping("/{id}")
  @Transactional
  public ResponseEntity eliminarMedico(@PathVariable Long id) {
    Medico medico = medicoRepository.getReferenceById(id);
    medico.desactivarMedico();
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/{id}")
  public ResponseEntity<DatosRespuestaMedico> retornaDatosMedico(@PathVariable Long id) {
    Medico medico = medicoRepository.getReferenceById(id);
    var datosMedico = new DatosRespuestaMedico(medico.getId(), medico.getNombre(), medico.getEmail(),
        medico.getTelefono(), medico.getEspecialidad().toString(),
        new DatosDireccion(medico.getDireccion().getCalle(), medico.getDireccion().getDistrito(),
            medico.getDireccion().getCiudad(), medico.getDireccion().getNumero(),
            medico.getDireccion().getComplemento()));
    return ResponseEntity.ok(datosMedico);
  }

    // DELETE en base de datos
  /*
   * public void eliminarMedico(@PathVariable Long id){
   * Medico medico = medicoRepository.getReferenceById(id);
   * medicoRepository.delete(medico);
   * }
   */
  
}
/*
 * Categoría de código
 * 
 * Los códigos HTTP (o HTTPS) tienen tres dígitos, y el primer dígito representa
 * la clasificación dentro de las cinco categorías posibles.
 * 
 * 1XX: Informativo: la solicitud fue aceptada o el proceso aún está en curso;
 * 2XX: Confirmación: la acción se completó o se comprendió;
 * 3XX: Redirección: indica que se debe hacer o se debió hacer algo más para
 * completar la solicitud;
 * 4XX: Error del cliente: indica que la solicitud no se puede completar o
 * contiene una sintaxis incorrecta;
 * 5XX: Error del servidor: el servidor falló al concluir la solicitud.
 */