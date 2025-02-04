import javax.swing.*; 
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;

/**
 * Clase que representa una ventana de inicio de sesión en una aplicación.
 * La clase permite al usuario ingresar un nombre de usuario y una contraseña, 
 * y al hacer clic en el botón "Iniciar sesión" o presionar Enter, valida las credenciales.
 * Dependiendo del tipo de usuario (Administrador, Mecánico o error), 
 * se redirige a una ventana diferente.
 */
public class Login extends JFrame {

    private static final long serialVersionUID = 1L;

    private JPanel contentPane;
    private JTextField txtusuario;
    private JPasswordField textcontraseña;
    private JButton inicio;
    private Conector con = new Conector();
    private JLabel intro;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Login frame = new Login();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Login() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 918, 590);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(220, 220, 220));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(245, 245, 245));
        panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        panel.setBounds(180, 0, 524, 562);
        contentPane.add(panel);
        panel.setLayout(null);

        txtusuario = new JTextField();
        txtusuario.setBounds(86, 216, 265, 28);
        panel.add(txtusuario);
        txtusuario.setColumns(10);

        JLabel usuario_1 = new JLabel("Usuario:");
        usuario_1.setBounds(86, 175, 78, 14);
        panel.add(usuario_1);
        usuario_1.setFont(new Font("Arial", Font.BOLD, 14));

        textcontraseña = new JPasswordField();
        textcontraseña.setBounds(86, 315, 265, 28);
        panel.add(textcontraseña);
        textcontraseña.setColumns(10);

        JLabel contraseña_1 = new JLabel("Contraseña:");
        contraseña_1.setBounds(86, 274, 96, 14);
        panel.add(contraseña_1);
        contraseña_1.setFont(new Font("Arial", Font.BOLD, 14));

        inicio = new JButton("Iniciar sesión");
        inicio.setBounds(67, 405, 325, 55);
        panel.add(inicio);
        inicio.setForeground(new Color(0, 0, 0));
        inicio.setBackground(new Color(255, 255, 255));
        inicio.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 14));
        inicio.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));

        intro = new JLabel("Bienvenido de vuelta");
        intro.setBounds(86, 47, 303, 46);
        panel.add(intro);
        intro.setFont(new Font("Arial", Font.BOLD, 30));

        // Acción del botón "Iniciar sesión"
        inicio.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                realizarLogin();
            }
        });

        // Evento de teclado para el campo de usuario
        txtusuario.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    realizarLogin();
                }
            }
        });

        // Evento de teclado para el campo de contraseña
        textcontraseña.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    realizarLogin();
                }
            }
        });
    }

    /**
     * Método que realiza la validación de las credenciales y redirige a la ventana correspondiente.
     */
    private void realizarLogin() {
        String usuario = txtusuario.getText();
        String contraseña = new String(textcontraseña.getPassword());
        int result = con.comprobarusuario(usuario, contraseña);

        if (result == 1) {
            Administrador admin = new Administrador();
            admin.setVisible(true);
            dispose();
        } else if (result == 0) {
            Mecanico meca = new Mecanico();
            meca.setVisible(true);
            dispose();
        } else if (result == 2) {
            JOptionPane.showMessageDialog(inicio, "El usuario o la contraseña son incorrectos");
        }
    }
}
