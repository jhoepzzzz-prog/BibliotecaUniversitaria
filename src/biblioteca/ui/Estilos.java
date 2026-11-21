package biblioteca.ui;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.JTableHeader;
final class Estilos {
    static final Color AZUL=new Color(30,64,115), FONDO=new Color(246,248,252), CLARO=new Color(232,240,252);
    static JLabel titulo(String s) {
        JLabel l=new JLabel(s); l.setFont(new Font("SansSerif",Font.BOLD,22)); l.setForeground(AZUL); return l;
    }
    static JButton boton(String s) {
        JButton b=new JButton(s); b.setFocusPainted(false); b.setBackground(AZUL); b.setForeground(Color.WHITE);
        b.setFont(new Font("SansSerif",Font.BOLD,13)); return b;
    }
    static void tabla(JTable t) {
        t.setRowHeight(28); t.setSelectionBackground(CLARO); t.setFillsViewportHeight(true);
        JTableHeader h=t.getTableHeader(); h.setBackground(AZUL); h.setForeground(Color.WHITE);
        h.setFont(new Font("SansSerif",Font.BOLD,13));
    }
    private Estilos() {}
}
