package biblioteca.ui;
import biblioteca.ui.servicio.*;
import java.awt.*;
import javax.swing.*;

public class VentanaLogin extends JFrame {
    private final BibliotecaService servicio;
    private final JTextField correo=new JTextField(22);
    private final JPasswordField clave=new JPasswordField(22);
    public VentanaLogin(BibliotecaService servicio) {
        this.servicio=servicio; setTitle("Biblioteca - Inicio de sesión");
        setDefaultCloseOperation(EXIT_ON_CLOSE); setSize(440,330); setResizable(false); setLocationRelativeTo(null);
        JPanel p=new JPanel(new GridBagLayout()); p.setBackground(Estilos.FONDO);
        JPanel card=new JPanel(new GridBagLayout()); card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createEmptyBorder(24,28,24,28));
        GridBagConstraints g=new GridBagConstraints(); g.insets=new Insets(8,6,8,6); g.fill=GridBagConstraints.HORIZONTAL;
        g.gridx=0;g.gridy=0;g.gridwidth=2;
        JLabel title=Estilos.titulo("Sistema de Biblioteca"); title.setHorizontalAlignment(SwingConstants.CENTER); card.add(title,g);
        g.gridy++; JLabel info=new JLabel("Ingresa tus credenciales"); info.setHorizontalAlignment(SwingConstants.CENTER); card.add(info,g);
        g.gridwidth=1;g.gridy++;g.gridx=0;card.add(new JLabel("Correo:"),g);g.gridx=1;card.add(correo,g);
        g.gridy++;g.gridx=0;card.add(new JLabel("Contraseña:"),g);g.gridx=1;card.add(clave,g);
        g.gridy++;g.gridx=0;g.gridwidth=2;JButton entrar=Estilos.boton("Ingresar");card.add(entrar,g);
        g.gridy++;JLabel demo=new JLabel("<html><center>Demo: estudiante@unsa.edu.pe / 1234<br>docente@unsa.edu.pe / 1234<br>bibliotecario@unsa.edu.pe / 1234</center></html>");
        demo.setHorizontalAlignment(SwingConstants.CENTER);demo.setForeground(Color.GRAY);card.add(demo,g);
        entrar.addActionListener(e -> ingresar()); getRootPane().setDefaultButton(entrar);
        p.add(card);setContentPane(p);
    }
    private void ingresar() {
        String c=correo.getText().trim(), pass=new String(clave.getPassword());
        if(c.isEmpty()||pass.isEmpty()) {
            JOptionPane.showMessageDialog(this,"Completa todos los campos.","Campos vacíos",JOptionPane.WARNING_MESSAGE); return;
        }
        UsuarioSesion u=servicio.autenticar(c,pass);
        if(u==null) {
            JOptionPane.showMessageDialog(this,"Correo o contraseña incorrectos.","Error de acceso",JOptionPane.ERROR_MESSAGE);
            clave.setText(""); return;
        }
        dispose(); new VentanaPrincipal(servicio,u).setVisible(true);
    }
}
