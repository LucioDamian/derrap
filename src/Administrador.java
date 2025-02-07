import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Enumeration;

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JRadioButton;
import javax.swing.JCheckBox;
import javax.swing.JTextArea;

public class Administrador extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private CardLayout card = new CardLayout();
    private JPanel cardpanel = new JPanel();
    private final Action action = new SwingAction();
    private Conector conector;
    private JCheckBox diagnostico = new JCheckBox("Diagnostico");
    private JCheckBox preitv = new JCheckBox("Pre ITV");
    private JCheckBox frenos = new JCheckBox("Frenos y ABS");
    private JCheckBox aceites = new JCheckBox("Aceites y filtros");
    private JCheckBox neumaticos = new JCheckBox("Neumaticos");
    private JCheckBox revision = new JCheckBox("Revision oficial");
    private JCheckBox matriculas = new JCheckBox("Matriculas");
    private JCheckBox pintura = new JCheckBox("Chapa y pintura");
    private JCheckBox equilibrado = new JCheckBox("Equilibrado/Alineacion");
    private JCheckBox aire = new JCheckBox("Aire acondicionado");
    private JCheckBox electronica = new JCheckBox("Electronica");
    private JTable tablaClientes; // Tabla para mostrar clientes
    private String clienteSeleccionadoDNI = null;
    private String ordenseleccionada = null;// Para almacenar el DNI del cliente seleccionado
    private JTextField usuario_nom;
    private JButton suma;
    private JTextField fechaord;
    private JTable tablaorden;
    private int precio = 0;
    private JTextField matriculavehi;
    private JTextField idordenactu;
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Administrador frame = new Administrador();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public Administrador() {
        conector = new Conector();

        // Configuración de la ventana
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 723, 588);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(245, 245, 245));  // Fondo claro
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // Menú superior
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBounds(0, 0, 697, 22);
        menuBar.setBackground(new Color(0, 120, 215));  // Fondo azul
        contentPane.add(menuBar);

        // Botones del menú
        JButton cliente = new JButton("Clientes");
        cliente.setForeground(Color.WHITE);  // Texto blanco
        cliente.setBackground(new Color(0, 120, 215));  // Fondo azul
        cliente.setFont(new Font("Arial", Font.BOLD, 12));
        cliente.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                card.show(cardpanel, "Cliente");
            }
        });
        menuBar.add(cliente);

        JButton vehiculos = new JButton("Vehiculos");
        vehiculos.setForeground(Color.WHITE);  // Texto blanco
        vehiculos.setBackground(new Color(0, 120, 215));  // Fondo azul
        vehiculos.setFont(new Font("Arial", Font.BOLD, 12));
        vehiculos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                card.show(cardpanel, "Vehiculo");
            }
        });
        menuBar.add(vehiculos);

        JButton trabajadores = new JButton("Trabajadores");
        trabajadores.setForeground(Color.WHITE);  // Texto blanco
        trabajadores.setBackground(new Color(0, 120, 215));  // Fondo azul
        trabajadores.setFont(new Font("Arial", Font.BOLD, 12));
        trabajadores.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                card.show(cardpanel, "Trabajadores");
            }
        });
        menuBar.add(trabajadores);

        JButton piezas = new JButton("Piezas");
        menuBar.add(piezas);

        JButton ordenes = new JButton("Ordenes");
        ordenes.setForeground(Color.WHITE);  // Texto blanco
        ordenes.setBackground(new Color(0, 120, 215));  // Fondo azul
        ordenes.setFont(new Font("Arial", Font.BOLD, 12));
        ordenes.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		card.show(cardpanel, "Ordenes");
        	}
        });
        menuBar.add(ordenes);

        JButton citas = new JButton("Citas");
        menuBar.add(citas);

        JButton facturacion = new JButton("Facturacion");
        menuBar.add(facturacion);

        JButton proveedores = new JButton("Proveedores");
        menuBar.add(proveedores);

        JButton usuarios = new JButton("Usuarios");
        menuBar.add(usuarios);

        // Panel de tarjetas (CardLayout)
        cardpanel.setBounds(37, 58, 632, 468);
        contentPane.add(cardpanel);
        cardpanel.setLayout(card);

        // Panel de clientes
        JPanel clientePanel = new JPanel();
        clientePanel.setBackground(new Color(255, 255, 255));  // Fondo blanco
        cardpanel.add(clientePanel, "Cliente");
        clientePanel.setLayout(null);

        // Campos de texto para el CRUD de clientes
        JLabel lblDni = new JLabel("DNI:");
        lblDni.setBounds(20, 20, 80, 25);
        clientePanel.add(lblDni);

        JTextField txtDni = new JTextField();
        txtDni.setBounds(100, 20, 150, 25);
        clientePanel.add(txtDni);

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(20, 60, 80, 25);
        clientePanel.add(lblNombre);

        JTextField txtNombre = new JTextField();
        txtNombre.setBounds(100, 60, 150, 25);
        clientePanel.add(txtNombre);

        JLabel lblApellidos = new JLabel("Apellidos:");
        lblApellidos.setBounds(20, 100, 80, 25);
        clientePanel.add(lblApellidos);

        JTextField txtApellidos = new JTextField();
        txtApellidos.setBounds(100, 100, 150, 25);
        clientePanel.add(txtApellidos);

        JLabel lblTelefono = new JLabel("Teléfono:");
        lblTelefono.setBounds(20, 140, 80, 25);
        clientePanel.add(lblTelefono);

        JTextField txtTelefono = new JTextField();
        txtTelefono.setBounds(100, 140, 150, 25);
        clientePanel.add(txtTelefono);

        JLabel lblDireccion = new JLabel("Dirección:");
        lblDireccion.setBounds(20, 180, 80, 25);
        clientePanel.add(lblDireccion);

        JTextField txtDireccion = new JTextField();
        txtDireccion.setBounds(100, 180, 150, 25);
        clientePanel.add(txtDireccion);

        JLabel lblCodigoPostal = new JLabel("Código Postal:");
        lblCodigoPostal.setBounds(20, 220, 100, 25);
        clientePanel.add(lblCodigoPostal);

        JTextField txtCodigoPostal = new JTextField();
        txtCodigoPostal.setBounds(130, 220, 120, 25);
        clientePanel.add(txtCodigoPostal);

        // Botones para acciones CRUD
        JButton btnAgregarCliente = new JButton("Agregar");
        btnAgregarCliente.setBounds(20, 260, 100, 25);
        clientePanel.add(btnAgregarCliente);

        JButton btnActualizarCliente = new JButton("Actualizar");
        btnActualizarCliente.setBounds(130, 260, 100, 25);
        clientePanel.add(btnActualizarCliente);

        JButton btnEliminarCliente = new JButton("Eliminar");
        btnEliminarCliente.setBounds(240, 260, 100, 25);
        clientePanel.add(btnEliminarCliente);

        JButton btnListarClientes = new JButton("Listar");
        btnListarClientes.setBounds(350, 260, 100, 25);
        clientePanel.add(btnListarClientes);

        // Tabla para mostrar clientes
        tablaClientes = new JTable();
        JScrollPane scrollClientes = new JScrollPane(tablaClientes);
        scrollClientes.setBounds(20, 300, 550, 150);
        clientePanel.add(scrollClientes);

        // Listener para la selección de filas en la tabla
        tablaClientes.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int filaSeleccionada = tablaClientes.getSelectedRow();
                if (filaSeleccionada != -1) {
                    // Obtener el DNI del cliente seleccionado (primera columna)
                    clienteSeleccionadoDNI = (String) tablaClientes.getValueAt(filaSeleccionada, 0);
                    System.out.println("Cliente seleccionado: " + clienteSeleccionadoDNI);
                }
            }
        });

        // Acción para agregar un cliente
        btnAgregarCliente.addActionListener(e -> {
            String dni = txtDni.getText();
            String nombre = txtNombre.getText();
            String apellidos = txtApellidos.getText();
            String telefono = txtTelefono.getText();
            String direccion = txtDireccion.getText();
            String codigopostal = txtCodigoPostal.getText();

            boolean resultado = conector.agregarCliente(dni, nombre, apellidos, telefono, direccion, codigopostal);
            if (resultado) {
                JOptionPane.showMessageDialog(null, "Cliente agregado correctamente.");
                actualizarTablaClientes();
            } else {
                JOptionPane.showMessageDialog(null, "Error al agregar cliente.");
            }
        });

        // Acción para actualizar un cliente
        btnActualizarCliente.addActionListener(e -> {
            String dni = txtDni.getText();
            String nombre = txtNombre.getText();
            String apellidos = txtApellidos.getText();
            String telefono = txtTelefono.getText();
            String direccion = txtDireccion.getText();
            String codigopostal = txtCodigoPostal.getText();

            boolean resultado = conector.actualizarCliente(dni, nombre, apellidos, telefono, direccion, codigopostal);
            if (resultado) {
                JOptionPane.showMessageDialog(null, "Cliente actualizado correctamente.");
                actualizarTablaClientes();
            } else {
                JOptionPane.showMessageDialog(null, "Error al actualizar cliente.");
            }
        });

        // Acción para eliminar un cliente
        btnEliminarCliente.addActionListener(e -> {
            if (clienteSeleccionadoDNI != null) {
                boolean resultado = conector.eliminarCliente(clienteSeleccionadoDNI);
                if (resultado) {
                    JOptionPane.showMessageDialog(null, "Cliente eliminado correctamente.");
                    actualizarTablaClientes();
                } else {
                    JOptionPane.showMessageDialog(null, "Error al eliminar cliente.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Por favor, selecciona un cliente de la tabla.");
            }
        });

        // Acción para listar clientes
        btnListarClientes.addActionListener(e -> {
            actualizarTablaClientes();
        });

        // Panel de vehículos
        JPanel vehiculoPanel = new JPanel();
        vehiculoPanel.setBackground(new Color(255, 255, 255));  // Fondo blanco
        cardpanel.add(vehiculoPanel, "Vehiculo");
        vehiculoPanel.setLayout(null);
        
        
        
        
        
        //Todo el codigo de la ventana de ordenes esta aqui!!!!
        
        
        
        
        
        
        JPanel Ordenes = new JPanel();
        Ordenes.setBackground(new Color(255, 255, 255));
        cardpanel.add(Ordenes, "Ordenes");
        Ordenes.setLayout(null);
        
        JLabel clien_ord_dni = new JLabel("Usuario");
        clien_ord_dni.setBounds(21, 42, 46, 14);
        Ordenes.add(clien_ord_dni);
        
        usuario_nom = new JTextField();
        usuario_nom.setBounds(69, 39, 210, 20);
        Ordenes.add(usuario_nom);
        usuario_nom.setColumns(10);
        
        JLabel servicios = new JLabel("Servicios:");
        servicios.setBounds(36, 67, 46, 14);
        Ordenes.add(servicios);
        
        //Crear las opciones para las ordenes 
        diagnostico.setBounds(46, 88, 149, 23);
        Ordenes.add(diagnostico);                
        preitv.setBounds(46, 114, 149, 23);
        Ordenes.add(preitv);                
        frenos.setBounds(46, 140, 149, 23);
        Ordenes.add(frenos);
        aceites.setBounds(46, 166, 149, 23);
        Ordenes.add(aceites);                
        neumaticos.setBounds(46, 192, 149, 23);
        Ordenes.add(neumaticos);                
        revision.setBounds(46, 218, 149, 23);
        Ordenes.add(revision);                
        matriculas.setBounds(197, 88, 149, 23);
        Ordenes.add(matriculas);
        pintura.setBounds(197, 114, 149, 23);
        Ordenes.add(pintura);                
        equilibrado.setBounds(197, 140, 149, 23);
        Ordenes.add(equilibrado);                
        aire.setBounds(197, 166, 149, 23);
        Ordenes.add(aire);        
        electronica.setBounds(197, 193, 149, 20);
        Ordenes.add(electronica);
        deshabilitaropciones();
        
        //valor numerico a los checkbox
        
        diagnostico.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    precio += 10;  // Sumar 1 si está seleccionado
                } else {
                    precio -= 10;  // Restar 1 si está deseleccionado
                }
                System.out.println("Valor actual: " + precio);
            }
        });
        
        preitv.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    precio += 30;  // Sumar 1 si está seleccionado
                } else {
                    precio -= 30;  // Restar 1 si está deseleccionado
                }
                System.out.println("Valor actual: " + precio);
            }
        });
        
        frenos.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    precio += 50;  // Sumar 1 si está seleccionado
                } else {
                    precio -= 50;  // Restar 1 si está deseleccionado
                }
                System.out.println("Valor actual: " + precio);
            }
        });
        
        aceites.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    precio += 15;  // Sumar 1 si está seleccionado
                } else {
                    precio -= 15;  // Restar 1 si está deseleccionado
                }
                System.out.println("Valor actual: " + precio);
            }
        });
        neumaticos.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    precio += 27;  // Sumar 1 si está seleccionado
                } else {
                    precio -= 27;  // Restar 1 si está deseleccionado
                }
                System.out.println("Valor actual: " + precio);
            }
        });
        
        revision.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    precio += 10;  // Sumar 1 si está seleccionado
                } else {
                    precio -= 10;  // Restar 1 si está deseleccionado
                }
                System.out.println("Valor actual: " + precio);
            }
        });
        
        matriculas.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    precio += 20;  // Sumar 1 si está seleccionado
                } else {
                    precio -= 20;  // Restar 1 si está deseleccionado
                }
                System.out.println("Valor actual: " + precio);
            }
        });
        
        pintura.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    precio += 30;  // Sumar 1 si está seleccionado
                } else {
                    precio -= 30;  // Restar 1 si está deseleccionado
                }
                System.out.println("Valor actual: " + precio);
            }
        });
        
        equilibrado.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    precio += 35;  // Sumar 1 si está seleccionado
                } else {
                    precio -= 35;  // Restar 1 si está deseleccionado
                }
                System.out.println("Valor actual: " + precio);
            }
        });
        
        aire.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    precio += 40;  // Sumar 1 si está seleccionado
                } else {
                    precio -= 40;  // Restar 1 si está deseleccionado
                }
                System.out.println("Valor actual: " + precio);
            }
        });
        electronica.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    precio += 60;  // Sumar 1 si está seleccionado
                } else {
                    precio -= 60;  // Restar 1 si está deseleccionado
                }
                System.out.println("Valor actual: " + precio);
            }
        });
        JLabel lblNewLabel = new JLabel("Total:");
        lblNewLabel.setBounds(36, 248, 46, 14);
        Ordenes.add(lblNewLabel);
        
        JLabel preciototal = new JLabel("0€");
        preciototal.setBounds(102, 248, 46, 14);
        Ordenes.add(preciototal);
        
        JButton btnNewButton = new JButton("Comprobar");
        btnNewButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		Boolean comprobado= comprobarmatricula(matriculavehi.getText());
        		if(comprobado == true) {
        			habilitaropciones();
        			suma.setVisible(true);
        			usuario_nom.setText("");
        		}
        		else{
        			JOptionPane.showMessageDialog(null, "No se encontro ningun usuario");
        			usuario_nom.setText("");
        		}
        	}
        });
        btnNewButton.setBounds(289, 22, 89, 23);
        Ordenes.add(btnNewButton);
        
        suma = new JButton("Suma Total");
        suma.setVisible(false);
        suma.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		String preciofinal= String.valueOf(precio);
        		preciototal.setText(preciofinal+"€");
        	}
        });
        suma.setBounds(215, 244, 89, 23);
        Ordenes.add(suma);
        
        JLabel lblNewLabel_2 = new JLabel("Fecha:");
        lblNewLabel_2.setBounds(380, 3, 46, 14);
        Ordenes.add(lblNewLabel_2);
        
        fechaord = new JTextField();
        fechaord.setBounds(432, 0, 129, 20);
        Ordenes.add(fechaord);
        fechaord.setColumns(10);
        
        JLabel lblNewLabel_3 = new JLabel("Descripcion:");
        lblNewLabel_3.setBounds(353, 102, 76, 14);
        Ordenes.add(lblNewLabel_3);
        
        JTextArea descripcion = new JTextArea();
        descripcion.setBounds(422, 71, 210, 102);
        Ordenes.add(descripcion);
        
        matriculavehi = new JTextField();
        matriculavehi.setBounds(69, 11, 210, 20);
        Ordenes.add(matriculavehi);
        matriculavehi.setColumns(10);
        
        
        tablaorden = new JTable();
        tablaorden.setBounds(10, 273, 599, 172);
        tablaorden.setVisible(false);
        Ordenes.add(tablaorden);
        
        idordenactu = new JTextField();
        idordenactu.setBounds(432, 23, 129, 20);
        Ordenes.add(idordenactu);
        idordenactu.setColumns(10);
        
        JButton modificar = new JButton("Modificar");
        modificar.setVisible(false);
        modificar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		int idord = Integer.parseInt(idordenactu.getText());
                String estadorep = "Disponible";
                String  usuario= usuario_nom.getText();
                String vehiculo_matri = matriculavehi.getText();
                int importe = precio;
                String fecha = fechaord.getText();
                String servicio = descripcion.getText();

                boolean resultado = conector.actualizarOrden(idord, estadorep, usuario, vehiculo_matri, importe, fecha, servicio);
                if (resultado) {
                    JOptionPane.showMessageDialog(null, "Trabajador actualizado correctamente.");
                    actualizarTablaOrdenes(tablaorden);
                } else {
                    JOptionPane.showMessageDialog(null, "Error al actualizar trabajador.");
                }
        	}
        });
        tablaorden.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int filaSeleccionada = tablaorden.getSelectedRow();
                if (filaSeleccionada != -1) {
                    // Obtener el DNI del cliente seleccionado (primera columna)
                    ordenseleccionada = (String) tablaorden.getValueAt(filaSeleccionada, 0);
                    System.out.println("Orden seleccionada: " + ordenseleccionada);
                }
            }
        });
        
        JButton eliminar = new JButton("Eliminar");
        eliminar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if (ordenseleccionada != null) {
        			int ordeneliminar = Integer.parseInt(ordenseleccionada);
                    boolean resultado = conector.eliminarOrden(ordeneliminar);
                    if (resultado) {
                        JOptionPane.showMessageDialog(null, "Orden eliminada correctamente.");
                        actualizarTablaOrdenes(tablaorden);
                    } else {
                        JOptionPane.showMessageDialog(null, "Error al eliminar orden.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Por favor, selecciona una orden de la tabla.");
                }
        	}
        });
        eliminar.setVisible(false);
        eliminar.setBounds(506, 445, 89, 23);
        Ordenes.add(eliminar);
        

        
        JButton revisar = new JButton("Revisar");
        revisar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		modificar.setVisible(true);
        		eliminar.setVisible(true);
        		tablaorden.setVisible(true);
        		actualizarTablaOrdenes(tablaorden);
        	}
        });
        revisar.setBounds(314, 445, 89, 23);
        Ordenes.add(revisar);
        modificar.setBounds(410, 445, 89, 23);
        Ordenes.add(modificar);
        
        
        
        JLabel lblNewLabel_1 = new JLabel("Matricula");
        lblNewLabel_1.setBounds(21, 14, 46, 14);
        Ordenes.add(lblNewLabel_1);
        
        JButton agregar = new JButton("Agregar");
        agregar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		int idord = (int) (Math.random() * (99999999 - 10000000 + 1)) + 10000000;
                String estadorep = "Disponible";
                String  usuario= usuario_nom.getText();
                String vehiculo_matri = matriculavehi.getText();
                int importe = precio;
                String fecha = fechaord.getText();
                String servicio = descripcion.getText();

                boolean resultado = conector.agregarOrden(idord, estadorep, usuario, vehiculo_matri, importe, fecha, servicio);
                if (resultado) {
                    JOptionPane.showMessageDialog(null, "Trabajador agregado correctamente.");
                    actualizarTablaOrdenes(tablaorden);
                } else {
                    JOptionPane.showMessageDialog(null, "Error al agregar trabajador.");
                }
        	}
        });
        agregar.setBounds(353, 244, 89, 23);
        Ordenes.add(agregar);
        
        JLabel lblNewLabel_4 = new JLabel("Id orden:");
        lblNewLabel_4.setBounds(380, 26, 46, 14);
        Ordenes.add(lblNewLabel_4);
        
        
        
        //AQUI TERMINA LA VENTANA DE ORDENES!!!!!


        // Campos de texto para el CRUD de vehículos
        JLabel lblMatricula = new JLabel("Matrícula:");
        lblMatricula.setBounds(20, 20, 80, 25);
        vehiculoPanel.add(lblMatricula);

        JTextField txtMatricula = new JTextField();
        txtMatricula.setBounds(100, 20, 150, 25);
        vehiculoPanel.add(txtMatricula);

        JLabel lblModelo = new JLabel("Modelo:");
        lblModelo.setBounds(20, 60, 80, 25);
        vehiculoPanel.add(lblModelo);

        JTextField txtModelo = new JTextField();
        txtModelo.setBounds(100, 60, 150, 25);
        vehiculoPanel.add(txtModelo);

        JLabel lblMarca = new JLabel("Marca:");
        lblMarca.setBounds(20, 100, 80, 25);
        vehiculoPanel.add(lblMarca);

        JTextField txtMarca = new JTextField();
        txtMarca.setBounds(100, 100, 150, 25);
        vehiculoPanel.add(txtMarca);

        JLabel lblCombustible = new JLabel("Combustible:");
        lblCombustible.setBounds(20, 140, 80, 25);
        vehiculoPanel.add(lblCombustible);

        JTextField txtCombustible = new JTextField();
        txtCombustible.setBounds(100, 140, 150, 25);
        vehiculoPanel.add(txtCombustible);

        JLabel lblKilometraje = new JLabel("Kilometraje:");
        lblKilometraje.setBounds(20, 180, 80, 25);
        vehiculoPanel.add(lblKilometraje);

        JTextField txtKilometraje = new JTextField();
        txtKilometraje.setBounds(100, 180, 150, 25);
        vehiculoPanel.add(txtKilometraje);

        JLabel lblColor = new JLabel("Color:");
        lblColor.setBounds(20, 220, 80, 25);
        vehiculoPanel.add(lblColor);

        JTextField txtColor = new JTextField();
        txtColor.setBounds(100, 220, 150, 25);
        vehiculoPanel.add(txtColor);

        JLabel lblAnio = new JLabel("Año:");
        lblAnio.setBounds(20, 260, 80, 25);
        vehiculoPanel.add(lblAnio);

        JTextField txtAnio = new JTextField();
        txtAnio.setBounds(100, 260, 150, 25);
        vehiculoPanel.add(txtAnio);

        JLabel lblClienteDni = new JLabel("DNI Cliente:");
        lblClienteDni.setBounds(20, 300, 80, 25);
        vehiculoPanel.add(lblClienteDni);

        JTextField txtClienteDni = new JTextField();
        txtClienteDni.setBounds(100, 300, 150, 25);
        vehiculoPanel.add(txtClienteDni);

        // Botones para acciones CRUD
        JButton btnAgregarVehiculo = new JButton("Agregar");
        btnAgregarVehiculo.setBounds(20, 340, 100, 25);
        vehiculoPanel.add(btnAgregarVehiculo);

        JButton btnActualizarVehiculo = new JButton("Actualizar");
        btnActualizarVehiculo.setBounds(130, 340, 100, 25);
        vehiculoPanel.add(btnActualizarVehiculo);

        JButton btnEliminarVehiculo = new JButton("Eliminar");
        btnEliminarVehiculo.setBounds(240, 340, 100, 25);
        vehiculoPanel.add(btnEliminarVehiculo);

        JButton btnListarVehiculos = new JButton("Listar");
        btnListarVehiculos.setBounds(350, 340, 100, 25);
        vehiculoPanel.add(btnListarVehiculos);

        // Tabla para mostrar vehículos
        JTable tablaVehiculos = new JTable();
        JScrollPane scrollVehiculos = new JScrollPane(tablaVehiculos);
        scrollVehiculos.setBounds(20, 380, 550, 150);
        vehiculoPanel.add(scrollVehiculos);

        // Listener para la selección de filas en la tabla
        tablaVehiculos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int filaSeleccionada = tablaVehiculos.getSelectedRow();
                if (filaSeleccionada != -1) {
                    // Obtener la matrícula del vehículo seleccionado (primera columna)
                    String matriculaSeleccionada = (String) tablaVehiculos.getValueAt(filaSeleccionada, 0);
                    System.out.println("Vehículo seleccionado: " + matriculaSeleccionada);
                }
            }
        });

        // Acción para agregar un vehículo
        btnAgregarVehiculo.addActionListener(e -> {
            String matricula = txtMatricula.getText();
            String modelo = txtModelo.getText();
            String marca = txtMarca.getText();
            String combustible = txtCombustible.getText();
            int kilometraje = Integer.parseInt(txtKilometraje.getText());
            String color = txtColor.getText();
            String anio = txtAnio.getText();
            String clienteDni = txtClienteDni.getText();

            boolean resultado = conector.agregarVehiculo(matricula, modelo, marca, combustible, kilometraje, color, anio, clienteDni);
            if (resultado) {
                JOptionPane.showMessageDialog(null, "Vehículo agregado correctamente.");
                actualizarTablaVehiculos(tablaVehiculos);
            } else {
                JOptionPane.showMessageDialog(null, "Error al agregar vehículo.");
            }
        });

        // Acción para actualizar un vehículo
        btnActualizarVehiculo.addActionListener(e -> {
            String matricula = txtMatricula.getText();
            String modelo = txtModelo.getText();
            String marca = txtMarca.getText();
            String combustible = txtCombustible.getText();
            int kilometraje = Integer.parseInt(txtKilometraje.getText());
            String color = txtColor.getText();
            String anio = txtAnio.getText();
            String clienteDni = txtClienteDni.getText();

            boolean resultado = conector.actualizarVehiculo(matricula, modelo, marca, combustible, kilometraje, color, anio, clienteDni);
            if (resultado) {
                JOptionPane.showMessageDialog(null, "Vehículo actualizado correctamente.");
                actualizarTablaVehiculos(tablaVehiculos);
            } else {
                JOptionPane.showMessageDialog(null, "Error al actualizar vehículo.");
            }
        });

        // Acción para eliminar un vehículo
        btnEliminarVehiculo.addActionListener(e -> {
            String matricula = txtMatricula.getText();
            boolean resultado = conector.eliminarVehiculo(matricula);
            if (resultado) {
                JOptionPane.showMessageDialog(null, "Vehículo eliminado correctamente.");
                actualizarTablaVehiculos(tablaVehiculos);
            } else {
                JOptionPane.showMessageDialog(null, "Error al eliminar vehículo.");
            }
        });

        // Acción para listar vehículos
        btnListarVehiculos.addActionListener(e -> {
            actualizarTablaVehiculos(tablaVehiculos);
        });

        // Panel de trabajadores
        JPanel trabajadoresPanel = new JPanel();
        trabajadoresPanel.setBackground(new Color(255, 255, 255));  // Fondo blanco
        cardpanel.add(trabajadoresPanel, "Trabajadores");
        trabajadoresPanel.setLayout(null);

        // Campos de texto para el CRUD de trabajadores
        JLabel lblDniTrabajador = new JLabel("DNI:");
        lblDniTrabajador.setBounds(20, 20, 80, 25);
        trabajadoresPanel.add(lblDniTrabajador);

        JTextField txtDniTrabajador = new JTextField();
        txtDniTrabajador.setBounds(100, 20, 150, 25);
        trabajadoresPanel.add(txtDniTrabajador);

        JLabel lblNombreTrabajador = new JLabel("Nombre:");
        lblNombreTrabajador.setBounds(20, 60, 80, 25);
        trabajadoresPanel.add(lblNombreTrabajador);

        JTextField txtNombreTrabajador = new JTextField();
        txtNombreTrabajador.setBounds(100, 60, 150, 25);
        trabajadoresPanel.add(txtNombreTrabajador);

        JLabel lblApellidosTrabajador = new JLabel("Apellidos:");
        lblApellidosTrabajador.setBounds(20, 100, 80, 25);
        trabajadoresPanel.add(lblApellidosTrabajador);

        JTextField txtApellidosTrabajador = new JTextField();
        txtApellidosTrabajador.setBounds(100, 100, 150, 25);
        trabajadoresPanel.add(txtApellidosTrabajador);

        JLabel lblTelefonoTrabajador = new JLabel("Teléfono:");
        lblTelefonoTrabajador.setBounds(20, 140, 80, 25);
        trabajadoresPanel.add(lblTelefonoTrabajador);

        JTextField txtTelefonoTrabajador = new JTextField();
        txtTelefonoTrabajador.setBounds(100, 140, 150, 25);
        trabajadoresPanel.add(txtTelefonoTrabajador);

        JLabel lblDireccionTrabajador = new JLabel("Dirección:");
        lblDireccionTrabajador.setBounds(20, 180, 80, 25);
        trabajadoresPanel.add(lblDireccionTrabajador);

        JTextField txtDireccionTrabajador = new JTextField();
        txtDireccionTrabajador.setBounds(100, 180, 150, 25);
        trabajadoresPanel.add(txtDireccionTrabajador);

        JLabel lblClaveTrabajador = new JLabel("Clave:");
        lblClaveTrabajador.setBounds(20, 220, 80, 25);
        trabajadoresPanel.add(lblClaveTrabajador);

        JTextField txtClaveTrabajador = new JTextField();
        txtClaveTrabajador.setBounds(100, 220, 150, 25);
        trabajadoresPanel.add(txtClaveTrabajador);

        // Botones para acciones CRUD
        JButton btnAgregarTrabajador = new JButton("Agregar");
        btnAgregarTrabajador.setBounds(20, 260, 100, 25);
        trabajadoresPanel.add(btnAgregarTrabajador);

        JButton btnActualizarTrabajador = new JButton("Actualizar");
        btnActualizarTrabajador.setBounds(130, 260, 100, 25);
        trabajadoresPanel.add(btnActualizarTrabajador);

        JButton btnEliminarTrabajador = new JButton("Eliminar");
        btnEliminarTrabajador.setBounds(240, 260, 100, 25);
        trabajadoresPanel.add(btnEliminarTrabajador);

        JButton btnListarTrabajadores = new JButton("Listar");
        btnListarTrabajadores.setBounds(350, 260, 100, 25);
        trabajadoresPanel.add(btnListarTrabajadores);

        // Tabla para mostrar trabajadores
        JTable tablaTrabajadores = new JTable();
        JScrollPane scrollTrabajadores = new JScrollPane(tablaTrabajadores);
        scrollTrabajadores.setBounds(20, 300, 550, 150);
        trabajadoresPanel.add(scrollTrabajadores);

        // Listener para la selección de filas en la tabla
        tablaTrabajadores.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int filaSeleccionada = tablaTrabajadores.getSelectedRow();
                if (filaSeleccionada != -1) {
                    // Obtener el DNI del trabajador seleccionado (primera columna)
                    String dniSeleccionado = (String) tablaTrabajadores.getValueAt(filaSeleccionada, 0);
                    System.out.println("Trabajador seleccionado: " + dniSeleccionado);
                }
            }
        });

        // Acción para agregar un trabajador
        btnAgregarTrabajador.addActionListener(e -> {
            String dni = txtDniTrabajador.getText();
            String nombre = txtNombreTrabajador.getText();
            String apellidos = txtApellidosTrabajador.getText();
            String telefono = txtTelefonoTrabajador.getText();
            String direccion = txtDireccionTrabajador.getText();
            String clave = txtClaveTrabajador.getText();

            boolean resultado = conector.agregarTrabajador(dni, nombre, apellidos, telefono, direccion, clave);
            if (resultado) {
                JOptionPane.showMessageDialog(null, "Trabajador agregado correctamente.");
                actualizarTablaTrabajadores(tablaTrabajadores);
            } else {
                JOptionPane.showMessageDialog(null, "Error al agregar trabajador.");
            }
        });

        // Acción para actualizar un trabajador
        btnActualizarTrabajador.addActionListener(e -> {
            String dni = txtDniTrabajador.getText();
            String nombre = txtNombreTrabajador.getText();
            String apellidos = txtApellidosTrabajador.getText();
            String telefono = txtTelefonoTrabajador.getText();
            String direccion = txtDireccionTrabajador.getText();
            String clave = txtClaveTrabajador.getText();

            boolean resultado = conector.actualizarTrabajador(dni, nombre, apellidos, telefono, direccion, clave);
            if (resultado) {
                JOptionPane.showMessageDialog(null, "Trabajador actualizado correctamente.");
                actualizarTablaTrabajadores(tablaTrabajadores);
            } else {
                JOptionPane.showMessageDialog(null, "Error al actualizar trabajador.");
            }
        });

        // Acción para eliminar un trabajador
        btnEliminarTrabajador.addActionListener(e -> {
            String dni = txtDniTrabajador.getText();
            boolean resultado = conector.eliminarTrabajador(dni);
            if (resultado) {
                JOptionPane.showMessageDialog(null, "Trabajador eliminado correctamente.");
                actualizarTablaTrabajadores(tablaTrabajadores);
            } else {
                JOptionPane.showMessageDialog(null, "Error al eliminar trabajador.");
            }
        });

        // Acción para listar trabajadores
        btnListarTrabajadores.addActionListener(e -> {
            actualizarTablaTrabajadores(tablaTrabajadores);
            
            
        });
    }

    /**
     * Método para actualizar la tabla de clientes
     */
    private void actualizarTablaClientes() {
        ResultSet rs = conector.obtenerClientes();
        DefaultTableModel modelo = new DefaultTableModel();

        // Definir las columnas de la tabla
        modelo.addColumn("DNI");
        modelo.addColumn("Nombre");
        modelo.addColumn("Apellidos");
        modelo.addColumn("Teléfono");
        modelo.addColumn("Dirección");
        modelo.addColumn("Código Postal");

        try {
            while (rs.next()) {
                modelo.addRow(new Object[] {
                    rs.getString("DNI"),
                    rs.getString("nombre"),
                    rs.getString("apellidos"),
                    rs.getString("telefono"),
                    rs.getString("direccion"),
                    rs.getString("codigopostal")
                });
            }
            tablaClientes.setModel(modelo);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Método para actualizar la tabla de vehículos
     */
    private void actualizarTablaVehiculos(JTable tablaVehiculos) {
        ResultSet rs = conector.obtenerVehiculos();
        DefaultTableModel modelo = new DefaultTableModel();

        // Definir las columnas de la tabla
        modelo.addColumn("Matrícula");
        modelo.addColumn("Modelo");
        modelo.addColumn("Marca");
        modelo.addColumn("Combustible");
        modelo.addColumn("Kilometraje");
        modelo.addColumn("Color");
        modelo.addColumn("Año");
        modelo.addColumn("DNI Cliente");

        try {
            while (rs.next()) {
                modelo.addRow(new Object[] {
                    rs.getString("matricula"),
                    rs.getString("modelo"),
                    rs.getString("marca"),
                    rs.getString("tipocombustible"),
                    rs.getInt("kilometraje"),
                    rs.getString("color"),
                    rs.getString("anio"),
                    rs.getString("cliente_DNI")
                });
            }
            tablaVehiculos.setModel(modelo);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Método para actualizar la tabla de trabajadores
     */
    private void actualizarTablaTrabajadores(JTable tablaTrabajadores) {
        ResultSet rs = conector.obtenerTrabajadores();
        DefaultTableModel modelo = new DefaultTableModel();

        // Definir las columnas de la tabla
        modelo.addColumn("DNI");
        modelo.addColumn("Nombre");
        modelo.addColumn("Apellidos");
        modelo.addColumn("Teléfono");
        modelo.addColumn("Dirección");
        modelo.addColumn("Clave");

        try {
            while (rs.next()) {
                modelo.addRow(new Object[] {
                    rs.getString("dni"),
                    rs.getString("nombre"),
                    rs.getString("apellidos"),
                    rs.getString("telefono"),
                    rs.getString("direccion"),
                    rs.getString("clave")
                });
            }
            tablaTrabajadores.setModel(modelo);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
//METODOS PARA LA VENTANA DE ORDENES!!!
    
    
    private void deshabilitaropciones() {
        diagnostico.setEnabled(false);
        preitv.setEnabled(false);
        frenos.setEnabled(false);
        aceites.setEnabled(false);
        neumaticos.setEnabled(false);
        revision.setEnabled(false);
        matriculas.setEnabled(false);
        pintura.setEnabled(false);
        equilibrado.setEnabled(false);
        aire.setEnabled(false);
        electronica.setEnabled(false);
        }

    private void habilitaropciones() {
    	diagnostico.setEnabled(true);
        preitv.setEnabled(true);
        frenos.setEnabled(true);
        aceites.setEnabled(true);
        neumaticos.setEnabled(true);
        revision.setEnabled(true);
        matriculas.setEnabled(true);
        pintura.setEnabled(true);
        equilibrado.setEnabled(true);
        aire.setEnabled(true);
        electronica.setEnabled(true);
    }
    
    private boolean comprobarmatricula(String matricula) {
    	boolean comprobado = false;
    	ResultSet rs = conector.vehiculomatricula();
    	try {
    	while(rs.next()) {
    		if(rs.getString("matricula").equals(matricula)) {
    			comprobado = true;
    		}	
    	}
    	}
    	catch(SQLException ex) {
    		ex.printStackTrace();
    	}
    	return comprobado;
    }

    private void actualizarTablaOrdenes(JTable tablaOrdenes) {
        ResultSet rs = conector.obtenerOrden();
        DefaultTableModel modelo = new DefaultTableModel();

        // Definir las columnas de la tabla
        modelo.addColumn("idorden");
        modelo.addColumn("estadoreparacion");
        modelo.addColumn("usuario_dni");
        modelo.addColumn("vehiculo_matricula");
        modelo.addColumn("importe");
        modelo.addColumn("fecha");
        modelo.addColumn("servicio");

        try {
            while (rs.next()) {
                modelo.addRow(new Object[] {
                    rs.getString("idorden"),
                    rs.getString("estadoreparacion"),
                    rs.getString("usuario_dni"),
                    rs.getString("vehiculo_matricula"),
                    rs.getString("importe"),
                    rs.getString("fecha"),
                    rs.getString("servicio")
                });
            }
            tablaOrdenes.setModel(modelo);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
// FIN DE LOS METODOS DE LA VENTANA DE ORDENES
    /**
     * Clase interna para manejar acciones.
     */
    private class SwingAction extends AbstractAction {
        public SwingAction() {
            putValue(NAME, "SwingAction");
            putValue(SHORT_DESCRIPTION, "Some short description");
        }
        public void actionPerformed(ActionEvent e) {
        }
    }
}