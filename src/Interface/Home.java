package Interface;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.sql.SQLException;

import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Home {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Home window = new Home();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Home() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setBounds(100, 100, 669, 545);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel.setBackground(Color.WHITE);
		panel.setBounds(10, 10, 627, 52);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lab = new JLabel("Gestion du Clinique");
		lab.setHorizontalAlignment(SwingConstants.CENTER);
		lab.setFont(new Font("Sylfaen", Font.PLAIN, 31));
		lab.setForeground(Color.BLUE);
		lab.setBounds(163, 10, 273, 36);
		panel.add(lab);
		
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(0, 204, 204));
		panel_1.setBounds(10, 72, 627, 426);
		frame.getContentPane().add(panel_1);
		
		JButton btn_medecin = new JButton("Medecins");
		btn_medecin.setBounds(213, 113, 218, 60);
		btn_medecin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 Medecins medecins = null;
			            try {
							medecins = new Medecins();
						} catch (ClassNotFoundException e1) {
							e1.printStackTrace();
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
			        
			        medecins.setVisible(true);
			        frame.dispose();
			    }
			});
		
		panel_1.setLayout(null);
		btn_medecin.setBackground(new Color(255, 255, 255));
		btn_medecin.setFont(new Font("Tahoma", Font.BOLD, 21));
		btn_medecin.setForeground(new Color(0, 128, 255));
		panel_1.add(btn_medecin);
		
		JButton btn_patient = new JButton("Patients");
		btn_patient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 Patient patient = null;
		            try {
						patient = new Patient();
					} catch (ClassNotFoundException e1) {
						e1.printStackTrace();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
		        
		        patient.setVisible(true);
		        frame.dispose();
		    }
			
		});
		btn_patient.setBounds(213, 213, 218, 60);
		btn_patient.setForeground(new Color(0, 128, 255));
		btn_patient.setFont(new Font("Tahoma", Font.BOLD, 21));
		btn_patient.setBackground(Color.WHITE);
		panel_1.add(btn_patient);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBackground(new Color(51, 204, 204));
		lblNewLabel.setIcon(new ImageIcon("C:\\Users\\hacob\\Desktop\\eclipse\\istockphoto-1146968122-612x612.jpg"));
		lblNewLabel.setBounds(10, 0, 612, 410);
		panel_1.add(lblNewLabel);
		File imageFile = new File("/Image/istockphoto-1146968122-612x612.jpg");
		ImageIcon imageIcon = new ImageIcon(imageFile.getAbsolutePath());
		JLabel background = new JLabel(imageIcon);
		background.setBounds(0, 0, 1034, 514);
	}
	public void setVisible(boolean b) {
	    frame.setVisible(b);
	}
}
