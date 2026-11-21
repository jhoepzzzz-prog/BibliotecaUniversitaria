package biblioteca.ui.servicio;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/** Implementación temporal en memoria para probar el Sprint 2 sin MySQL. */
public class DemoBibliotecaService implements BibliotecaService {
    private static class Libro {
        int id, stock; String titulo, autor, categoria;
        Libro(int id, String titulo, String autor, String categoria, int stock) {
            this.id=id; this.titulo=titulo; this.autor=autor; this.categoria=categoria; this.stock=stock;
        }
        LibroDTO dto() { return new LibroDTO(id,titulo,autor,categoria,stock); }
    }
    private static class Prestamo {
        int id, idLibro; String correo; LocalDate fecha; LocalDate devolucion;
        Prestamo(int id, int idLibro, String correo) {
            this.id=id; this.idLibro=idLibro; this.correo=correo; this.fecha=LocalDate.now();
        }
    }
    private final List<Libro> libros = new ArrayList<>();
    private final List<Prestamo> prestamos = new ArrayList<>();
    private int siguienteId=1;

    public DemoBibliotecaService() {
        libros.add(new Libro(1,"Cien años de soledad","Gabriel García Márquez","Novela",4));
        libros.add(new Libro(2,"Don Quijote de la Mancha","Miguel de Cervantes","Clásico",3));
        libros.add(new Libro(3,"Clean Code","Robert C. Martin","Programación",5));
        libros.add(new Libro(4,"El principito","Antoine de Saint-Exupéry","Ficción",2));
        libros.add(new Libro(5,"Java: Cómo programar","Paul Deitel","Programación",0));
    }
    public UsuarioSesion autenticar(String correo, String clave) {
        if (correo==null || clave==null || !clave.equals("1234")) return null;
        String c=correo.trim().toLowerCase(Locale.ROOT);
        return switch(c) {
            case "estudiante@unsa.edu.pe" -> new UsuarioSesion(1,"Ana Estudiante",c,"Estudiante");
            case "docente@unsa.edu.pe" -> new UsuarioSesion(2,"Carlos Docente",c,"Docente");
            case "bibliotecario@unsa.edu.pe" -> new UsuarioSesion(3,"Lucía Bibliotecaria",c,"Bibliotecario");
            default -> null;
        };
    }
    public List<LibroDTO> listarLibros() {
        return libros.stream().map(Libro::dto).toList();
    }
    public List<LibroDTO> buscarLibros(String texto) {
        String q=texto==null ? "" : texto.toLowerCase(Locale.ROOT).trim();
        return libros.stream().filter(l -> l.titulo.toLowerCase(Locale.ROOT).contains(q)
            || l.autor.toLowerCase(Locale.ROOT).contains(q)
            || l.categoria.toLowerCase(Locale.ROOT).contains(q)).map(Libro::dto).toList();
    }
    public List<PrestamoDTO> listarPrestamos(String correo) {
        List<PrestamoDTO> r=new ArrayList<>();
        for (Prestamo p:prestamos) if (p.correo.equalsIgnoreCase(correo)) {
            Libro l=libro(p.idLibro);
            r.add(new PrestamoDTO(p.id,l.titulo,p.fecha,p.devolucion,
                p.devolucion==null ? "Activo" : "Devuelto"));
        }
        return r;
    }
    public void prestar(String correo, int idLibro) {
        Libro l=libro(idLibro);
        if (l==null) throw new IllegalArgumentException("El libro no existe.");
        if (l.stock<=0) throw new IllegalArgumentException("No hay ejemplares disponibles.");
        UsuarioSesion u=tipoUsuario(correo);
        long activos=prestamos.stream().filter(p -> p.correo.equalsIgnoreCase(correo) && p.devolucion==null).count();
        int limite=switch(u.tipo()) { case "Estudiante" -> 3; case "Docente" -> 5; default -> 10; };
        if (activos>=limite) throw new IllegalArgumentException("Se alcanzó el límite de "+limite+" préstamos.");
        l.stock--;
        prestamos.add(new Prestamo(siguienteId++,idLibro,correo));
    }
    public void devolver(String correo, int idPrestamo) {
        for (Prestamo p:prestamos) {
            if (p.id==idPrestamo && p.correo.equalsIgnoreCase(correo) && p.devolucion==null) {
                p.devolucion=LocalDate.now();
                libro(p.idLibro).stock++;
                return;
            }
        }
        throw new IllegalArgumentException("El préstamo no existe, no pertenece al usuario o ya fue devuelto.");
    }
    private Libro libro(int id) {
        return libros.stream().filter(l -> l.id==id).findFirst().orElse(null);
    }
    private UsuarioSesion tipoUsuario(String correo) {
        String c=correo.toLowerCase(Locale.ROOT);
        if(c.startsWith("docente")) return new UsuarioSesion(2,"Docente",c,"Docente");
        if(c.startsWith("bibliotecario")) return new UsuarioSesion(3,"Bibliotecario",c,"Bibliotecario");
        return new UsuarioSesion(1,"Estudiante",c,"Estudiante");
    }
}
