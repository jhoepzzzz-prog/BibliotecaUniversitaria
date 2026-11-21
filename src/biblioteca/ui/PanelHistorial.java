package biblioteca.ui;
import biblioteca.ui.servicio.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class PanelHistorial extends JPanel {
    private final BibliotecaService servicio;private final UsuarioSesion usuario;private final Runnable alCambiar;
    private final DefaultTableModel modelo=new DefaultTableModel(new Object[]{"ID préstamo","Libro","Fecha préstamo","Fecha devolución","Estado"},0) {
        public boolean isCellEditable(int r,int c){return false;}
    };
    private final JTable tabla=new JTable(modelo);
    public PanelHistorial(BibliotecaService servicio,UsuarioSesion usuario,Runnable alCambiar) {
        this.servicio=servicio;this.usuario=usuario;this.alCambiar=alCambiar;
        setLayout(new BorderLayout(10,10));setBackground(Estilos.FONDO);setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        add(Estilos.titulo("Historial de mis préstamos"),BorderLayout.NORTH);Estilos.tabla(tabla);add(new JScrollPane(tabla),BorderLayout.CENTER);
        JPanel acciones=new JPanel(new FlowLayout(FlowLayout.LEFT));acciones.setOpaque(false);
        JButton devolver=Estilos.boton("Devolver libro"),actualizar=new JButton("Actualizar");
        acciones.add(devolver);acciones.add(actualizar);add(acciones,BorderLayout.SOUTH);
        devolver.addActionListener(e->devolver());actualizar.addActionListener(e->actualizar());actualizar();
    }
    public void actualizar() {
        modelo.setRowCount(0);
        for(PrestamoDTO p:servicio.listarPrestamos(usuario.correo()))
            modelo.addRow(new Object[]{p.id(),p.tituloLibro(),p.fechaPrestamo(),
                p.fechaDevolucion()==null?"—":p.fechaDevolucion(),p.estado()});
    }
    private void devolver() {
        int fila=tabla.getSelectedRow();
        if(fila<0){JOptionPane.showMessageDialog(this,"Selecciona un préstamo de la tabla.","Aviso",JOptionPane.WARNING_MESSAGE);return;}
        int id=(int)modelo.getValueAt(tabla.convertRowIndexToModel(fila),0);
        int ok=JOptionPane.showConfirmDialog(this,"¿Confirmas la devolución del libro?","Confirmar devolución",JOptionPane.YES_NO_OPTION);
        if(ok!=JOptionPane.YES_OPTION)return;
        try {
            servicio.devolver(usuario.correo(),id);
            JOptionPane.showMessageDialog(this,"Libro devuelto correctamente.","Éxito",JOptionPane.INFORMATION_MESSAGE);
            actualizar();alCambiar.run();
        } catch(IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this,ex.getMessage(),"Error de devolución",JOptionPane.ERROR_MESSAGE);
        }
    }
}
