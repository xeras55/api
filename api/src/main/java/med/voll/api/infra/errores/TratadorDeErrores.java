package med.voll.api.infra.errores;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class TratadorDeErrores {

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity tratarError404() {
    return ResponseEntity.notFound().build();

  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity tratarError400(MethodArgumentNotValidException e) {
    var errores = e.getFieldErrors().stream().map(DatosErrorvalidacion::new).toList();
    return ResponseEntity.badRequest().body(errores);
  }

  private record DatosErrorvalidacion(String campo, String error) {
    public DatosErrorvalidacion(FieldError error) {
      this(error.getField(), error.getDefaultMessage());
    }
  }
}
// Programacion orientada a aspectos
/*
 * public record DatosCadastroMedico(
 * 
 * @NotBlank(message = "Nombre es obligatorio")
 * String nombre,
 * 
 * @NotBlank(message = "Email es obligatorio")
 * 
 * @Email(message = "Formato de email es inválido")
 * String email,
 * 
 * @NotBlank(message = "Teléfono es obligatorio")
 * String telefono,
 * 
 * @NotBlank(message = "CRM es obligatorio")
 * 
 * @Pattern(regexp = "\\d{4,6}", message = "Formato do CRM es inválido")
 * String crm,
 * 
 * @NotNull(message = "Especialidad es obligatorio")
 * Especialidad especialidad,
 * 
 * @NotNull(message = "Datos de dirección son obligatorios")
 * 
 * @Valid DatosDireccion direccion) {}
 */
/*
  ! otra forma es
 */
/*
 * Otra forma es aislar los mensajes en un archivo de propiedades, que debe
 * tener el nombre ValidationMessages.properties y estar creado en el directorio
 * src/main/resources:
 * nombre.obligatorio=El nombre es obligatorio
 * email.obligatorio=Correo electrónico requerido
 * email.invalido=El formato del correo electrónico no es válido
 * phone.obligatorio=Teléfono requerido
 * crm.obligatorio=CRM es obligatorio
 * crm.invalido=El formato CRM no es válido
 * especialidad.obligatorio=La especialidad es obligatoria
 * address.obligatorio=Los datos de dirección son obligatorios
 * Y, en las anotaciones, indicar la clave de las propiedades por el propio
 * atributo message, delimitando con los caracteres { e }:
 * public record DatosRegistroMedico(
 * 
 * @NotBlank(message = "{nombre.obligatorio}")
 * String nombre,
 * 
 * @NotBlank(message = "{email.obligatorio}")
 * 
 * @Email(message = "{email.invalido}")
 * String email,
 * 
 * @NotBlank(message = "{telefono.obligatorio}")
 * String telefono,
 * 
 * @NotBlank(message = "{crm.obligatorio}")
 * 
 * @Pattern(regexp = "\\d{4,6}", message = "{crm.invalido}")
 * String crm,
 * 
 * @NotNull(message = "{especialidad.obligatorio}")
 * Especialidad especialidad,
 * 
 * @NotNull(message = "{direccion.obligatorio}")
 * 
 * @Valid DatosDireccion direccion) {}
 */