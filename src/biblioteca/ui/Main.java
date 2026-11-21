package biblioteca.ui;
import biblioteca.ui.servicio.*;
import javax.swing.*;
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch(Exception ignored) {}
            BibliotecaService servicio=new DemoBibliotecaService();
            new VentanaLogin(servicio).setVisible(true);
        });
    }
}
