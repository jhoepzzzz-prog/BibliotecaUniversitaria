package biblioteca.ui.servicio;
import java.time.LocalDate;
public record PrestamoDTO(int id, String tituloLibro, LocalDate fechaPrestamo,
                          LocalDate fechaDevolucion, String estado) {}
