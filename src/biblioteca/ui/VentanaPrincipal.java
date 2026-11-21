package biblioteca.ui;
import biblioteca.ui.servicio.*;
import java.awt.*;
import javax.swing.*;

public class VentanaPrincipal extends JFrame {
    private final BibliotecaService servicio; private final UsuarioSesion usuario;
    private final CardLayout cardLayout=new CardLayout(); private final JPanel centro=new JPanel(cardLayout);
    private PanelCatalogo catalogo; private PanelPrestamos panelPrestamos; private PanelHistorial historial;
    public VentanaPrincipal(BibliotecaService servicio, UsuarioSesion usuario) {
        this.servicio=servicio;this.usuario=usuario;setTitle("Biblioteca - "+usuario.tipo());
        setDefaultCloseOperation(EXIT_ON_CLOSE);setSize(1000,650);setLocationRelativeTo(null);construir();
    }
    private void construir() {
        JPanel root=new JPanel(new BorderLayout());root.setBackground(Estilos.FONDO);
        JPanel top=new JPanel(new BorderLayout());top.setBackground(Color.WHITE);top.setBorder(BorderFactory.createEmptyBorder(14,18,14,18));
        JLabel quien=new JLabel(usuario.nombre()+" | "+usuario.tipo());quien.setForeground(Estilos.AZUL);quien.setFont(new Font("SansSerif",Font.BOLD,15));
        JButton salir=new JButton("Cerrar sesión");top.add(quien,BorderLayout.WEST);top.add(salir,BorderLayout.EAST);
        salir.addActionListener(e->{dispose();new VentanaLogin(servicio).setVisible(true);});
        JPanel menu=new JPanel(new GridLayout(0,1,7,7));menu.setBackground(Estilos.CLARO);menu.setBorder(BorderFactory.createEmptyBorder(15,12,15,12));
        JButton bCat=new JButton("Catálogo"),bPres=new JButton("Préstamos"),bHist=new JButton("Mi historial");
        menu.add(bCat);menu.add(bPres);menu.add(bHist);
        catalogo=new PanelCatalogo(servicio);panelPrestamos=new PanelPrestamos(servicio,usuario,this::actualizar);
        historial=new PanelHistorial(servicio,usuario,this::actualizar);
        centro.add(catalogo,"catalogo");centro.add(panelPrestamos,"prestamos");centro.add(historial,"historial");
        bCat.addActionListener(e->mostrar("catalogo"));bPres.addActionListener(e->mostrar("prestamos"));bHist.addActionListener(e->mostrar("historial"));
        root.add(top,BorderLayout.NORTH);root.add(menu,BorderLayout.WEST);root.add(centro,BorderLayout.CENTER);setContentPane(root);
    }
    private void mostrar(String nombre){actualizar();cardLayout.show(centro,nombre);}
    private void actualizar(){catalogo.actualizar();panelPrestamos.actualizar();historial.actualizar();}
}
