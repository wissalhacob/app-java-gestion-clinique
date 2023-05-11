package Interface;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.toedter.calendar.JDateChooser;

import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.UIManager;

public class Patient {

	private JFrame frame;
	private JTextField txt_nom;
	private JDateChooser txt_naissance;

	private JTextField txt_tel;
	private JButton btn_ajouter;
	private JButton btn_modifier;
	private JButton btn_supprimer;
	private JPanel panel_1;
	  private JTextField txt_cin;
	     private JScrollPane scrollPane;
	    
	   
	     JTable table = new JTable();
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Patient window = new Patient();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
     
	 Connection con;
     PreparedStatement pst ;
     ResultSet rs;
	public Patient() throws ClassNotFoundException, SQLException {
		    initialize();
		    afficherPatient();

	}
     public void Connect() throws ClassNotFoundException, SQLException {
 		Class.forName("com.mysql.cj.jdbc.Driver");
 		con = DriverManager.getConnection("jdbc:mysql://localhost/clinique","root","");
 	    System.out.println("connexion etablie");
 	}

 
	
     public void afficherPatient() throws SQLException, ClassNotFoundException {
    	    Connect();

    	    String[] entet = {"Id", "Nom et Prenom", "CIN", "Tel", "Date de Naissance"};
    	    DefaultTableModel model = new DefaultTableModel(null, entet);

    	    String sql = "SELECT * FROM patient";
    	    PreparedStatement ps = con.prepareStatement(sql);
    	    ResultSet rs = ps.executeQuery();


    	    while (rs.next()) {
    	        String[] ligne = new String[11];

    	        ligne[0] = rs.getString("id");
    	        ligne[1] = rs.getString("nomprenom");
    	        ligne[2] = rs.getString("CIN");
    	        ligne[3] = String.valueOf(rs.getInt("tel"));
    	        ligne[4] = rs.getDate("datenaissance") != null ? rs.getDate("datenaissance").toString() : null;
    	        model.addRow(ligne);

    	    }
    	    table.setForeground(new Color(0, 0, 0));
    	    table.setBackground(new Color(192, 192, 192));
    	    table.setFont(new Font("Tahoma", Font.PLAIN, 14));
    	    table.setModel(model);
    	    con.close();

    	    ListSelectionListener selectionListener = new ListSelectionListener() {
    	        public void valueChanged(ListSelectionEvent e) {
    	        	if (e.getValueIsAdjusting()) {
    	                return;
    	            }
    	            int rowIndex = table.getSelectedRow();
    	            if (rowIndex >= 0) {
    	                int selectedRow = table.getSelectedRow();

    	                String nomprenom = table.getValueAt(selectedRow, 1).toString();
    	                String cin = table.getValueAt(selectedRow, 2).toString();
    	                String tel = table.getValueAt(selectedRow, 3).toString();
    	                Date datenaissance=null;
						try {
							SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

							datenaissance = format.parse(table.getValueAt(selectedRow, 4).toString());

						} catch (ParseException e1) {
							e1.printStackTrace();
						}

    	                txt_nom.setText(nomprenom);
    	                txt_cin.setText(cin);
    	                txt_tel.setText(tel);

    	                txt_naissance.setDate(datenaissance);
    	            
    	            }
    	        }
    	    };

    	    table.getSelectionModel().addListSelectionListener(selectionListener);
    	}


	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(240, 240, 240));
		frame.setBounds(100, 100, 1167, 720);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		
		
		
		panel_1 = new JPanel();
		panel_1.setBackground(UIManager.getColor("Button.background"));
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1.setBounds(37, 108, 1061, 250);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel_1_1 = new JLabel("Numéro de Téléphone :");
		lblNewLabel_1_1.setBounds(611, 82, 211, 25);
		panel_1.add(lblNewLabel_1_1);
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
	
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Date de Naissance :");
		lblNewLabel_1_1_1.setBounds(611, 26, 177, 25);
		panel_1.add(lblNewLabel_1_1_1);
		lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		txt_tel = new JTextField();
		txt_tel.setBounds(832, 82, 192, 33);
		panel_1.add(txt_tel);
		txt_tel.setColumns(10);
		

		
		txt_naissance = new JDateChooser();
		txt_naissance.setBounds(832, 26, 192, 33);
		panel_1.add(txt_naissance);
		txt_naissance.setToolTipText("");
		
	
	
		
		txt_nom = new JTextField();
		txt_nom.setBounds(267, 26, 192, 33);
		panel_1.add(txt_nom);
		txt_nom.setToolTipText("nom prenom");
		txt_nom.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Nom et Prenom :");
		panel_1.add(lblNewLabel_1);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		btn_ajouter = new JButton("Ajouter");
		btn_ajouter.setBounds(138, 165, 166, 50);
		panel_1.add(btn_ajouter);
		btn_ajouter.setBackground(new Color(51, 102, 255));
		btn_ajouter.setFont(new Font("Tw Cen MT Condensed Extra Bold", Font.PLAIN, 26));

		btn_ajouter.addActionListener(new ActionListener()
		
		{
			public void actionPerformed(ActionEvent e) {

				try {
						Connect();

						pst=con.prepareStatement("insert into patient (nomprenom , datenaissance , tel , CIN)  values (?,?,?,?)");
				       pst.setString(1, txt_nom.getText());
				       java.util.Date date1 = txt_naissance.getDate();
				       java.sql.Date sqlDate1 = new java.sql.Date(date1.getTime());
				       pst.setDate(2, sqlDate1);
				       int tel = Integer.parseInt(txt_tel.getText());
				       pst.setInt(3, tel);
				       pst.setString(4, txt_cin.getText());
				       pst.executeUpdate();
				       JOptionPane.showMessageDialog(null, "Patient ajouté");
				       txt_nom.setText("");
				        txt_naissance.setDate(null);
				        txt_tel.setText("");
				        txt_cin.setText("");
				       afficherPatient();
				       con.close();

				}
					 catch (Exception e1) {
						e1.printStackTrace();
					}	
			}
		});
		
		btn_ajouter.setForeground(new Color(255, 255, 255));
		
		btn_modifier = new JButton("Modifier");
		btn_modifier.setBounds(457, 165, 166, 50);
		panel_1.add(btn_modifier);
		btn_modifier.setForeground(new Color(255, 255, 255));
		btn_modifier.setBackground(new Color(51, 102, 204));
		btn_modifier.setFont(new Font("Tw Cen MT Condensed Extra Bold", Font.PLAIN, 26));
		btn_modifier.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 try {
				        Connect();
				        
				        pst=con.prepareStatement("update patient set nomprenom=?, datenaissance=?, tel=?, CIN=?  where id=?");
				        pst.setString(1, txt_nom.getText());
				        java.util.Date date1 = txt_naissance.getDate();
				        java.sql.Date sqlDate1 = new java.sql.Date(date1.getTime());
				        pst.setDate(2, sqlDate1);
				        int tel = Integer.parseInt(txt_tel.getText());
				        pst.setInt(3, tel);
				        pst.setString(4, txt_cin.getText());
				        int row = table.getSelectedRow();
				        int id = Integer.parseInt(table.getModel().getValueAt(row, 0).toString());
				        pst.setInt(5, id);

				        pst.executeUpdate();
				        con.close();

				        JOptionPane.showMessageDialog(null, "Patient modifié");
				        txt_nom.setText("");
				        txt_naissance.setDate(null);
				        txt_tel.setText("");
				      
				        txt_cin.setText("");
				        afficherPatient();


				 } catch (Exception e1) {
				        e1.printStackTrace();
				    }
				}
		});
		
		
		btn_supprimer = new JButton("Supprimer");
		btn_supprimer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
			        Connect();
			        pst=con.prepareStatement("DELETE FROM patient WHERE CIN=?");
			        pst.setString(1, txt_cin.getText());
			        pst.executeUpdate();
			        con.close();
			        JOptionPane.showMessageDialog(null, "Patient supprimé");
			        txt_nom.setText("");
			        txt_naissance.setDate(null);
			        txt_tel.setText("");
			        txt_cin.setText("");
			        afficherPatient();
			    } catch (Exception e1) {
			        e1.printStackTrace();
			    } 
			}
		});
		btn_supprimer.setBounds(773, 165, 171, 50);
		panel_1.add(btn_supprimer);
		btn_supprimer.setBackground(new Color(51, 102, 204));
		btn_supprimer.setForeground(new Color(255, 255, 255));
		btn_supprimer.setFont(new Font("Tw Cen MT Condensed Extra Bold", Font.PLAIN, 26));
		
		JLabel lblNewLabel_1_1_1_2 = new JLabel("CIN :");
		lblNewLabel_1_1_1_2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel_1_1_1_2.setBounds(34, 82, 177, 25);
		panel_1.add(lblNewLabel_1_1_1_2);
		
		txt_cin = new JTextField();
		txt_cin.setToolTipText("");
		txt_cin.setColumns(10);
		txt_cin.setBounds(267, 82, 192, 33);
		panel_1.add(txt_cin);
		
		JLabel lblNewLabel_1_1_1_2_1 = new JLabel("Nom et Prenom:");
		lblNewLabel_1_1_1_2_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel_1_1_1_2_1.setBounds(34, 26, 177, 25);
		panel_1.add(lblNewLabel_1_1_1_2_1);
		
		JPanel panel = new JPanel();
		panel.setBackground(UIManager.getColor("Button.background"));
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.setBounds(352, 47, 412, 51);
		frame.getContentPane().add(panel);
		
		JLabel lblNewLabel = new JLabel("Patients");
		lblNewLabel.setForeground(new Color(0, 51, 153));
		lblNewLabel.setFont(new Font("Tw Cen MT Condensed", Font.BOLD, 41));
		panel.add(lblNewLabel);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(37, 368, 1061, 346);
		frame.getContentPane().add(panel_2);
		panel_2.setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 1051, 277);
		scrollPane.setViewportView(table);

		panel_2.add(scrollPane);
		scrollPane.setViewportView(table);
		
		JButton btn_retour = new JButton("Retour");
		btn_retour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 Home home = null;
		            home = new Home();
		        
		       home.setVisible(true);
		        frame.dispose();

			}
		});
		btn_retour.setBounds(20, 10, 85, 21);
		frame.getContentPane().add(btn_retour);
		

	
	}
	public void setVisible(boolean b) {
	    frame.setVisible(b);
	}
}
