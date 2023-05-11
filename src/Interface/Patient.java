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
import javax.swing.JComboBox;

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
	     private  JComboBox <String> txt_medecin ;

	   
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
     private JTextField txt_maladie;
	public Patient() throws ClassNotFoundException, SQLException {
		    initialize();
		    afficherPatient();
          liste();

	}
     public void Connect() throws ClassNotFoundException, SQLException {
 		Class.forName("com.mysql.cj.jdbc.Driver");
 		con = DriverManager.getConnection("jdbc:mysql://localhost/clinique","root","");
 	    System.out.println("connexion etablie");
 	}
     public void liste() throws SQLException, ClassNotFoundException {
    	    Connect();

    	    String sql = "SELECT CIN FROM medecin";
    	    PreparedStatement ps = con.prepareStatement(sql);
    	    ResultSet rs = ps.executeQuery();

    	    txt_medecin.removeAllItems(); 
    	    txt_medecin.addItem("");
    	    while (rs.next()) {
    	        String cin = rs.getString("CIN");
    	        txt_medecin.addItem(cin); 

    	    }
    	    con.close();
    	}


	
     public void afficherPatient() throws SQLException, ClassNotFoundException {
    	    Connect();

    	    String[] entet = {"Id", "Nom et Prenom", "CIN", "Tel", "Date de Naissance" , "Maladie" ,"Medecin Traitant"};
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
    	        ligne[5]=rs.getString("Maladie");
    	        ligne[6]=rs.getString("MedecinTraitant");

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
    	                String maladie =table.getValueAt(selectedRow, 5).toString();
    	                String medecin = (String) table.getValueAt(selectedRow, 6);


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
    	                txt_maladie.setText(maladie);
    	                txt_medecin.setSelectedItem(medecin);

    	            
    	            }
    	        }
    	    };

    	    table.getSelectionModel().addListSelectionListener(selectionListener);
    	}
     
     
    

	private void initialize() throws ClassNotFoundException, SQLException {

		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(240, 240, 240));
		frame.setBounds(100, 100, 1167, 720);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		
		

		panel_1 = new JPanel();
		panel_1.setBackground(UIManager.getColor("Button.background"));
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1.setBounds(37, 108, 1061, 312);
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
		btn_ajouter.setBounds(136, 252, 166, 50);
		panel_1.add(btn_ajouter);
		btn_ajouter.setBackground(new Color(51, 102, 255));
		btn_ajouter.setFont(new Font("Tw Cen MT Condensed Extra Bold", Font.PLAIN, 26));

		btn_ajouter.addActionListener(new ActionListener()
		
		{
			public void actionPerformed(ActionEvent e) {

				try {
					
					  if (txt_nom.getText().isEmpty() || txt_cin.getText().isEmpty() || txt_tel.getText().isEmpty()) {
				    	   JOptionPane.showMessageDialog(null, "Veuillez remplir tous les champs obligatoires", "Erreur", JOptionPane.INFORMATION_MESSAGE);
				           return;
				       }
					  int tel;
				        java.util.Date date1;
				        java.sql.Date sqlDate1;
					  try {
				             tel = Integer.parseInt(txt_tel.getText());
				        } catch (NumberFormatException ex) {
				            JOptionPane.showMessageDialog(null, "Vérifier vos données", "Erreur de saisie", JOptionPane.ERROR_MESSAGE);
				            return;
				        }
					 
				        try {
				             date1 = txt_naissance.getDate();
				            sqlDate1 = new java.sql.Date(date1.getTime());
				        } catch (Exception ex) {
				            JOptionPane.showMessageDialog(null, "Vérifier vos données", "Erreur de saisie", JOptionPane.ERROR_MESSAGE);
				            return;
				        }
					
						Connect();

						pst=con.prepareStatement("insert into patient (nomprenom , datenaissance , tel , CIN , Maladie , MedecinTraitant)  values (?,?,?,?,?,?)");
				       pst.setString(1, txt_nom.getText());
				        date1 = txt_naissance.getDate();
				        sqlDate1 = new java.sql.Date(date1.getTime());
				       pst.setDate(2, sqlDate1);
				       tel = Integer.parseInt(txt_tel.getText());
				       pst.setInt(3, tel);
				       pst.setString(4, txt_cin.getText());
				       pst.setString(5, txt_maladie.getText());
				       pst.setString(6, (String) txt_medecin.getSelectedItem());
				       
				       pst.executeUpdate();
				 
				       JOptionPane.showMessageDialog(null, "Patient ajouté");
				       txt_nom.setText("");
				        txt_naissance.setDate(null);
				        txt_tel.setText("");
				        txt_cin.setText("");
				        txt_maladie.setText("");
					    txt_medecin.setSelectedItem("");

				       afficherPatient();
				       con.close();
				       try {
				             tel = Integer.parseInt(txt_tel.getText());
				        } catch (NumberFormatException ex) {
				            JOptionPane.showMessageDialog(null, "Le numéro de téléphone doit être un entier valide", "Erreur de saisie", JOptionPane.ERROR_MESSAGE);
				            return;
				        }
				        try {
				             date1 = txt_naissance.getDate();
				            sqlDate1 = new java.sql.Date(date1.getTime());
				        } catch (Exception ex) {
				            JOptionPane.showMessageDialog(null, "La date de naissance n'est pas valide", "Erreur de saisie", JOptionPane.ERROR_MESSAGE);
				            return;
				        }

				}
					 catch (Exception e1) {
						 
						e1.printStackTrace();
					}
				
			}
		});
		
		btn_ajouter.setForeground(new Color(255, 255, 255));
		
		btn_modifier = new JButton("Modifier");
		btn_modifier.setBounds(455, 252, 166, 50);
		panel_1.add(btn_modifier);
		btn_modifier.setForeground(new Color(255, 255, 255));
		btn_modifier.setBackground(new Color(51, 102, 204));
		btn_modifier.setFont(new Font("Tw Cen MT Condensed Extra Bold", Font.PLAIN, 26));
		btn_modifier.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 try {
					 if (txt_nom.getText().isEmpty() || txt_cin.getText().isEmpty() || txt_tel.getText().isEmpty()) {
				    	   JOptionPane.showMessageDialog(null, "Veuillez remplir tous les champs obligatoires", "Erreur", JOptionPane.INFORMATION_MESSAGE);
				           return;
				       }
					  int tel;
				        java.util.Date date1;
				        java.sql.Date sqlDate1;
					  try {
				             tel = Integer.parseInt(txt_tel.getText());
				        } catch (NumberFormatException ex) {
				            JOptionPane.showMessageDialog(null, "Vérifier vos données", "Erreur de saisie", JOptionPane.ERROR_MESSAGE);
				            return;
				        }
					 
				        try {
				             date1 = txt_naissance.getDate();
				            sqlDate1 = new java.sql.Date(date1.getTime());
				        } catch (Exception ex) {
				            JOptionPane.showMessageDialog(null, "Vérifier vos données", "Erreur de saisie", JOptionPane.ERROR_MESSAGE);
				            return;
				        } 
					 
				        Connect();
				      
				        pst=con.prepareStatement("update patient set nomprenom=?, datenaissance=?, tel=?, CIN=?,Maladie=?,MedecinTraitant=?  where id=?");
				        pst.setString(1, txt_nom.getText());
				         date1 = txt_naissance.getDate();
				         sqlDate1 = new java.sql.Date(date1.getTime());
				        pst.setDate(2, sqlDate1);
				         tel = Integer.parseInt(txt_tel.getText());
				        pst.setInt(3, tel);
				        pst.setString(4, txt_cin.getText());
				        pst.setString(5, txt_maladie.getText());
				        pst.setString(6, (String) txt_medecin.getSelectedItem());
				        int row = table.getSelectedRow();
				        int id = Integer.parseInt(table.getModel().getValueAt(row, 0).toString());
				        pst.setInt(7, id);
				       
				        pst.executeUpdate();
				      
				        con.close();
				    

				        JOptionPane.showMessageDialog(null, "Patient modifié");
				        txt_nom.setText("");
				        txt_naissance.setDate(null);
				        txt_tel.setText("");
				      
				        txt_cin.setText("");
				        txt_maladie.setText("");
					    txt_medecin.setSelectedItem("");
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
			        txt_maladie.setText("");
				    txt_medecin.setSelectedItem("");
			        afficherPatient();
			    } catch (Exception e1) {
			        e1.printStackTrace();
			    } 
			}
		});
		btn_supprimer.setBounds(777, 252, 171, 50);
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
		
		JLabel lblNewLabel_1_1_1_2_2 = new JLabel("Maladie :");
		lblNewLabel_1_1_1_2_2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel_1_1_1_2_2.setBounds(34, 145, 177, 25);
		panel_1.add(lblNewLabel_1_1_1_2_2);
		
		txt_maladie = new JTextField();
		txt_maladie.setToolTipText("");
		txt_maladie.setColumns(10);
		txt_maladie.setBounds(267, 145, 192, 33);
		panel_1.add(txt_maladie);
		
		JLabel lblNewLabel_1_1_1_2_2_1 = new JLabel("Medecin Traitant :");
		lblNewLabel_1_1_1_2_2_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel_1_1_1_2_2_1.setBounds(611, 155, 177, 25);
		panel_1.add(lblNewLabel_1_1_1_2_2_1);
		
	    txt_medecin = new JComboBox<>();
		txt_medecin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		

			}	});
		txt_medecin.setBackground(new Color(255, 255, 255));
		txt_medecin.setBounds(832, 155, 192, 33);
		panel_1.add(txt_medecin);
		
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
		panel_2.setBounds(37, 430, 1061, 284);
		frame.getContentPane().add(panel_2);
		panel_2.setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 10, 1051, 285);
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
