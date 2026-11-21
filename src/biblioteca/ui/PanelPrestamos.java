package biblioteca.ui;
import biblioteca.ui.servicio.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class PanelPrestamos extends JPanel {
    private final BibliotecaService servicio;private final UsuarioSesion usuario;private final Runnable alCambiar;
    private final DefaultTableModel modelo=new DefaultTableModel(new Object[]{"ID","Título","Autor","Categoría","Stock"},0) {
        public boolean isCellEditable(int r,int c){return false;}
    };
    private final JTable tabla=new JTable(modelo);
    public PanelPrestamos(BibliotecaService servicio,UsuarioSesion usuario,Runnable alCambiar) {
        this.servicio=servicio;this.usuario=usuario;this.alCambiar=alCambiar;
        setLayout(new BorderLayout(10,10));setBackground(Estilos.FONDO);setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        add(Estilos.titulo("Solicitar préstamo"),BorderLayout.NORTH);Estilos.tabla(tabla);add(new JScrollPane(tabla),BorderLayout.CENTER);
        JPanel acciones=new JPanel(new FlowLayout(FlowLayout.LEFT));acciones.setOpaque(false);
        JButton solicitar=Estilos.boton("Solicitar préstamo"),actualizar=new JButton("Actualizar");
        acciones.add(solicitar);acciones.add(actualizar);add(acciones,BorderLayout.SOUTH);
        solicitar.addActionListener(e->solicitar());actualizar.addActionListener(e->actualizar());actualizar();
    }
    public void actualizar(){modelo.setRowCount(0);for(LibroDTO l:servicio.listarLibros())modelo.addRow(new Object[]{l.id(),l.titulo(),l.autor(),l.categoria(),l.stock()});}
    private void solicitar() {
        int fila=tabla.getSelectedRow();
        if(fila<0){JOptionPane.showMessageDialog(this,"Selecciona un libro de la tabla.","Aviso",JOptionPane.WARNING_MESSAGE);return;}
        int id=(int)modelo.getValueAt(tabla.convertRowIndexToModel(fila),0);
        int ok=JOptionPane.showConfirmDialog(this,"¿Deseas solicitar este libro?","Confirmar préstamo",JOptionPane.YES_NO_OPTION);
        if(ok!=JOptionPane.YES_OPTION)return;
        try {
            servicio.prestar(usuario.correo(),id);
            JOptionPane.showMessageDialog(this,"Préstamo registrado correctamente.","Éxito",JOptionPane.INFORMATION_MESSAGE);
            actualizar();alCambiar.run();
        } catch(IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this,ex.getMessage(),"No se pudo prestar",JOptionPane.ERROR_MESSAGE);
        }
    }
}
