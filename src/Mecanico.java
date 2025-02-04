import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.CardLayout;
import java.awt.Color;
import javax.swing.JTable;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTextPane;
public class Mecanico extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	JButton bt1;
	JButton bt2;
	JButton bt3;
	JButton bt4;
	JButton bt5;
	JButton bt6;
	private JPanel cardpanel=new JPanel();
	private Conector con=new Conector();
	private ResultSet rs=null;
	private JPanel panel;
	private JPanel panel_1;
	private JPanel panel_2;
	private JPanel panel_3;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private JLabel lblNewLabel_3;
	private JLabel lblNewLabel_4;
	private JLabel lblNewLabel_5;
	private JLabel lblNewLabel_6;
	private JLabel lblNewLabel_7;
	private JLabel lblNewLabel_8;
	private JLabel lblNewLabel_9;
	private JLabel lblNewLabel_10;
	private JLabel lblNewLabel_11;
	private JLabel lblNewLabel_12;
	private JLabel lblNewLabel_13;
	private JLabel lblNewLabel_14;
	private JLabel lblNewLabel_15;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Mecanico frame = new Mecanico();
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
	public Mecanico() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 724, 466);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		bt2 = new JButton("Modificar orden");
		bt2.setBounds(575, 368, 123, 59);
		contentPane.add(bt2);
		bt2.setVisible(false);
		bt3 = new JButton("Ver lista ordenes");
		bt3.setEnabled(false);
		/*bt3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				card.show(cardpanel, "Ordenes");
				rs=con.listado();
				DefaultTableModel modelo=new DefaultTableModel();
				modelo.addColumn("IdPedido");
				modelo.addColumn("Fecha");
				modelo.addColumn("Importe");
				try {
					while(rs.next()) {
						modelo.addRow(new Object[] {
								rs.getString("idPedido"),
								rs.getString("Fecha"),
								rs.getString("Importe")
						});
								
					}
					torden.setModel(modelo);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});*/
		bt3.setBounds(575, 18, 123, 59);
		contentPane.add(bt3);
		
		bt4 = new JButton("Consultar stock");
		bt4.setBounds(575, 158, 123, 59);
		contentPane.add(bt4);
		
		bt5 = new JButton("Solicitar piezas");
		bt5.setBounds(575, 228, 123, 59);
		contentPane.add(bt5);
		
		bt6 = new JButton("Consultar historial");
		bt6.setBounds(575, 298, 123, 59);
		contentPane.add(bt6);
		
		bt1 = new JButton("Añadir orden");
		bt1.setBounds(575, 88, 123, 59);
		contentPane.add(bt1);
		
		panel = new JPanel();
		panel.setBackground(new Color(255, 255, 255));
		panel.setBounds(10, 29, 108, 387);
		contentPane.add(panel);
		panel.setLayout(null);
		
		lblNewLabel = new JLabel("Orden");
		lblNewLabel.setBounds(10, 11, 46, 14);
		panel.add(lblNewLabel);
		
		lblNewLabel_4 = new JLabel("1");
		lblNewLabel_4.setBounds(10, 50, 46, 14);
		panel.add(lblNewLabel_4);
		
		lblNewLabel_8 = new JLabel("2");
		lblNewLabel_8.setBounds(10, 85, 88, 14);
		panel.add(lblNewLabel_8);
		
		lblNewLabel_12 = new JLabel("3");
		lblNewLabel_12.setBounds(10, 122, 46, 14);
		panel.add(lblNewLabel_12);
		
		panel_1 = new JPanel();
		panel_1.setBackground(new Color(255, 255, 255));
		panel_1.setBounds(128, 29, 108, 387);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		lblNewLabel_1 = new JLabel("Estado");
		lblNewLabel_1.setBounds(10, 11, 46, 14);
		panel_1.add(lblNewLabel_1);
		
		lblNewLabel_5 = new JLabel("Primera orden");
		lblNewLabel_5.setBounds(10, 51, 88, 14);
		panel_1.add(lblNewLabel_5);
		
		lblNewLabel_9 = new JLabel("Segunda orden");
		lblNewLabel_9.setBounds(10, 85, 88, 14);
		panel_1.add(lblNewLabel_9);
		
		lblNewLabel_13 = new JLabel("Tercera orden");
		lblNewLabel_13.setBounds(10, 122, 88, 14);
		panel_1.add(lblNewLabel_13);
		
		panel_2 = new JPanel();
		panel_2.setBackground(new Color(255, 255, 255));
		panel_2.setBounds(246, 29, 123, 387);
		contentPane.add(panel_2);
		panel_2.setLayout(null);
		
		lblNewLabel_2 = new JLabel("Cliente_DNI");
		lblNewLabel_2.setBounds(10, 11, 90, 14);
		panel_2.add(lblNewLabel_2);
		
		lblNewLabel_6 = new JLabel("124234");
		lblNewLabel_6.setBounds(10, 49, 90, 14);
		panel_2.add(lblNewLabel_6);
		
		lblNewLabel_10 = new JLabel("124235");
		lblNewLabel_10.setBounds(10, 84, 90, 14);
		panel_2.add(lblNewLabel_10);
		
		lblNewLabel_14 = new JLabel("12543456");
		lblNewLabel_14.setBounds(10, 123, 90, 14);
		panel_2.add(lblNewLabel_14);
		
		panel_3 = new JPanel();
		panel_3.setBackground(new Color(255, 255, 255));
		panel_3.setBounds(379, 29, 123, 387);
		contentPane.add(panel_3);
		panel_3.setLayout(null);
		
		lblNewLabel_3 = new JLabel("Matricula Vehiculo");
		lblNewLabel_3.setBounds(10, 11, 113, 14);
		panel_3.add(lblNewLabel_3);
		
		lblNewLabel_7 = new JLabel("547465");
		lblNewLabel_7.setBounds(10, 50, 103, 14);
		panel_3.add(lblNewLabel_7);
		
		lblNewLabel_11 = new JLabel("1253467");
		lblNewLabel_11.setBounds(10, 85, 103, 14);
		panel_3.add(lblNewLabel_11);
		
		lblNewLabel_15 = new JLabel("235423465");
		lblNewLabel_15.setBounds(10, 124, 103, 14);
		panel_3.add(lblNewLabel_15);
		//rs=con.listado();
		/*DefaultTableModel modelo=new DefaultTableModel();
		modelo.addColumn("Orden");
		modelo.addColumn("Estado");
		modelo.addColumn("DNI_Cliente");
		modelo.addColumn("Matricula_Vehiculo");
		Object[] titulo = {"Orden","Estado","DNI_Cliente","Matricula_Vehiculo"};
		modelo.addRow(titulo);
		Object[] orden1 = {"1", "Primera orden", "131242354", "254872458"};
		modelo.addRow(orden1);
		Object[] orden2 = {"2","Segunda orden","18723648","7812452154"};
		modelo.addRow(orden2);
		Object[] orden3 = {"3","Tercera orden", "173568754","8724824"};
		modelo.addRow(orden3);		
		/*try {
			while(rs.next()) {
				modelo.addRow(new Object[] {
						rs.getString("idPedido"),
						rs.getString("Fecha"),
						rs.getString("Importe")
				});
						
			}
			torden.setModel(modelo);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		torden.setModel(modelo);*/
	}
}
