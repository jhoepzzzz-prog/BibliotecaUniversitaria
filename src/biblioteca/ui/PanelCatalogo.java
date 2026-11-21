package biblioteca.ui;
import biblioteca.ui.servicio.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class PanelCatalogo extends JPanel {
    private final BibliotecaService servicio;
    private final DefaultTableModel modelo=new DefaultTableModel(new Object[]{"ID","Título","Autor","Categoría","Stock"},0) {
        public boolean isCellEditable(int r,int c){return false;}
    };
    private final JTable tabla=new JTable(modelo); private final JTextField buscar=new JTextField(20);
    public PanelCatalogo(BibliotecaService servicio) {
        this.servicio=servicio;setLayout(new BorderLayout(10,10));setBackground(Estilos.FONDO);setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        JPanel north=new JPanel(new BorderLayout(8,8));north.setOpaque(false);north.add(Estilos.titulo("Catálogo de libros"),BorderLayout.NORTH);
        JPanel filtros=new JPanel(new FlowLayout(FlowLayout.LEFT));filtros.setOpaque(false);filtros.add(new JLabel("Buscar:"));filtros.add(buscar);
        JButton b=Estilos.boton("Buscar"),limpiar=new JButton("Limpiar");filtros.add(b);filtros.add(limpiar);north.add(filtros,BorderLayout.CENTER);
        Estilos.tabla(tabla);add(north,BorderLayout.NORTH);add(new JScrollPane(tabla),BorderLayout.CENTER);
        b.addActionListener(e->cargar(servicio.buscarLibros(buscar.getText())));
        buscar.addActionListener(e->cargar(servicio.buscarLibros(buscar.getText())));
        limpiar.addActionListener(e->{buscar.setText("");actualizar();});actualizar();
    }
    public void actualizar(){cargar(servicio.listarLibros());}
    private void cargar(java.util.List<LibroDTO> lista){modelo.setRowCount(0);for(LibroDTO l:lista)modelo.addRow(new Object[]{l.id(),l.titulo(),l.autor(),l.categoria(),l.stock()});}
}
