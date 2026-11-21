package biblioteca.ui.servicio;
import java.util.List;

/** Contrato que conecta Swing con el modelo del Integrante 1 y la BD del Integrante 3. */
public interface BibliotecaService {
    UsuarioSesion autenticar(String correo, String contrasena);
    List<LibroDTO> listarLibros();
    List<LibroDTO> buscarLibros(String texto);
    List<PrestamoDTO> listarPrestamos(String correo);
    void prestar(String correo, int idLibro);
    void devolver(String correo, int idPrestamo);
}
