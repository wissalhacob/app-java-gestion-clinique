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

public class Medecins {

	private JFrame frame;
	private JTextField txt_nom;
	private JTextField txt_specialite;
	private JTextField txt_adresse;
	private JDateChooser txt_naissance;
	private JDateChooser txt_recrutement;

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
					Medecins window = new Medecins();
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
     private JButton btnNewButton;
	public Medecins() throws ClassNotFoundException, SQLException {
		    initialize();
		    afficherMedecins();

	}
     public void Connect() throws ClassNotFoundException, SQLException {
 		Class.forName("com.mysql.cj.jdbc.Driver");
 		con = DriverManager.getConnection("jdbc:mysql://localhost/clinique","root","");
 	    System.out.println("connexion etablie");
 	}

 
	
     public void afficherMedecins() throws SQLException, ClassNotFoundException {
    	    Connect();

    	    String[] entet = {"Id", "Nom et Prenom", "CIN", "Tel", "Date de Naissance", "Date de recrutement", "Adresse", "Spécialité"};
    	    DefaultTableModel model = new DefaultTableModel(null, entet);

    	    String sql = "SELECT * FROM medecin";
    	    PreparedStatement ps = con.prepareStatement(sql);
    	    ResultSet rs = ps.executeQuery();


    	    while (rs.next()) {
    	        String[] ligne = new String[11];

    	        ligne[0] = rs.getString("id");
    	        ligne[1] = rs.getString("nomprenom");
    	        ligne[2] = rs.getString("CIN");
    	        ligne[3] = String.valueOf(rs.getInt("tel"));
    	        ligne[4] = rs.getDate("datenaissance") != null ? rs.getDate("datenaissance").toString() : null;
    	        ligne[5] = rs.getDate("daterecrutement") != null ? rs.getDate("daterecrutement").toString() : null;
    	        ligne[6] = rs.getString("adresse");
    	        ligne[7] = rs.getString("specialite");
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
    	                Date daterecrutement=null;
						try {
							SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

							datenaissance = format.parse(table.getValueAt(selectedRow, 4).toString());
	    	                daterecrutement = format.parse(table.getValueAt(selectedRow, 5).toString());

						} catch (ParseException e1) {
							e1.printStackTrace();
						}

    	                String adresse = table.getValueAt(selectedRow, 6).toString();
    	                String specialite = table.getValueAt(selectedRow, 7).toString();
    	                txt_nom.setText(nomprenom);
    	                txt_cin.setText(cin);
    	                txt_tel.setText(tel);

    	                txt_naissance.setDate(datenaissance);
    	                txt_recrutement.setDate(daterecrutement);

    	                txt_adresse.setText(adresse);
    	                txt_specialite.setText(specialite);
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
		panel_1.setBounds(37, 71, 1061, 315);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lab_specialite = new JLabel("Spécialité:");
		lab_specialite.setBounds(611, 89, 91, 25);
		panel_1.add(lab_specialite);
		lab_specialite.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		JLabel lblNewLabel_1_1 = new JLabel("Numéro de Téléphone :");
		lblNewLabel_1_1.setBounds(611, 142, 211, 25);
		panel_1.add(lblNewLabel_1_1);
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		txt_specialite = new JTextField();
		txt_specialite.setBounds(832, 82, 192, 32);
		panel_1.add(txt_specialite);
		txt_specialite.setColumns(10);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Date de Naissance :");
		lblNewLabel_1_1_1.setBounds(34, 142, 177, 25);
		panel_1.add(lblNewLabel_1_1_1);
		lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		JLabel lblNewLabel_1_2 = new JLabel("Adresse :");
		lblNewLabel_1_2.setBounds(611, 26, 82, 25);
		panel_1.add(lblNewLabel_1_2);
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		txt_tel = new JTextField();
		txt_tel.setBounds(832, 142, 192, 33);
		panel_1.add(txt_tel);
		txt_tel.setColumns(10);
		
		JLabel lblNewLabel_1_1_1_1 = new JLabel("Date de Recrutement :");
		lblNewLabel_1_1_1_1.setBounds(34, 204, 202, 25);
		panel_1.add(lblNewLabel_1_1_1_1);
		lblNewLabel_1_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		txt_adresse = new JTextField();
		txt_adresse.setBounds(832, 18, 192, 33);
		panel_1.add(txt_adresse);
		txt_adresse.setColumns(10);
		
		txt_naissance = new JDateChooser();
		txt_naissance.setBounds(267, 142, 192, 33);
		panel_1.add(txt_naissance);
		txt_naissance.setToolTipText("");
		
		txt_recrutement = new JDateChooser();
		txt_recrutement.setBounds(267, 204, 192, 33);
		panel_1.add(txt_recrutement);
		txt_recrutement.setToolTipText("");
	
		
		txt_nom = new JTextField();
		txt_nom.setBounds(267, 26, 192, 33);
		panel_1.add(txt_nom);
		txt_nom.setToolTipText("nom prenom");
		txt_nom.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Nom et Prenom :");
		panel_1.add(lblNewLabel_1);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		btn_ajouter = new JButton("Ajouter");
		btn_ajouter.setBounds(138, 255, 166, 50);
		panel_1.add(btn_ajouter);
		btn_ajouter.setBackground(new Color(51, 102, 255));
		btn_ajouter.setFont(new Font("Tw Cen MT Condensed Extra Bold", Font.PLAIN, 26));

		btn_ajouter.addActionListener(new ActionListener()
		
		{
			public void actionPerformed(ActionEvent e) {

				try {
					 if (txt_nom.getText().isEmpty() || txt_cin.getText().isEmpty() || txt_tel.getText().isEmpty() || txt_adresse.getText().isEmpty()|| txt_specialite.getText().isEmpty() ) {
				    	   JOptionPane.showMessageDialog(null, "Veuillez remplir tous les champs obligatoires", "Erreur", JOptionPane.INFORMATION_MESSAGE);
				           return;
				       }
					  int tel;
				        java.util.Date date1;
				        java.util.Date date;
				        java.sql.Date sqlDate1;
				        java.sql.Date sqlDate;

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
				        try {
				             date = txt_recrutement.getDate();
				            sqlDate = new java.sql.Date(date1.getTime());
				        } catch (Exception ex) {
				            JOptionPane.showMessageDialog(null, "Vérifier vos données", "Erreur de saisie", JOptionPane.ERROR_MESSAGE);
				            return;
				        }
						Connect();

						pst=con.prepareStatement("insert into medecin (nomprenom , datenaissance , tel , adresse , daterecrutement , specialite , CIN)  values (?,?,?,?,?,?,?)");
				       pst.setString(1, txt_nom.getText());
				        date1 = txt_naissance.getDate();
				        sqlDate1 = new java.sql.Date(date1.getTime());
				       pst.setDate(2, sqlDate1);
				       tel = Integer.parseInt(txt_tel.getText());
				       pst.setInt(3, tel);
				       pst.setString(4, txt_adresse.getText());
				       date = txt_recrutement.getDate();
				        sqlDate = new java.sql.Date(date.getTime());
				       pst.setDate(5, sqlDate);
				       pst.setString(6, txt_specialite.getText());
				       pst.setString(7, txt_cin.getText());
				       pst.executeUpdate();
				       JOptionPane.showMessageDialog(null, "Medecin ajouté");
				       txt_nom.setText("");
				        txt_naissance.setDate(null);
				        txt_tel.setText("");
				        txt_adresse.setText("");
				        txt_recrutement.setDate(null);
				        txt_specialite.setText("");
				        txt_cin.setText("");
				       afficherMedecins();
				       con.close();

				}
					 catch (Exception e1) {
						e1.printStackTrace();
					}	
			}
		});
		
		btn_ajouter.setForeground(new Color(255, 255, 255));
		
		btn_modifier = new JButton("Modifier");
		btn_modifier.setBounds(457, 255, 166, 50);
		panel_1.add(btn_modifier);
		btn_modifier.setForeground(new Color(255, 255, 255));
		btn_modifier.setBackground(new Color(51, 102, 204));
		btn_modifier.setFont(new Font("Tw Cen MT Condensed Extra Bold", Font.PLAIN, 26));
		btn_modifier.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 try {
					 
					 if (txt_nom.getText().isEmpty() || txt_cin.getText().isEmpty() || txt_tel.getText().isEmpty() || txt_adresse.getText().isEmpty()|| txt_specialite.getText().isEmpty() ) {
				    	   JOptionPane.showMessageDialog(null, "Veuillez remplir tous les champs obligatoires", "Erreur", JOptionPane.INFORMATION_MESSAGE);
				           return;
				       }
					  int tel;
				        java.util.Date date1;
				        java.util.Date date;
				        java.sql.Date sqlDate1;
				        java.sql.Date sqlDate;

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
				        try {
				             date = txt_recrutement.getDate();
				            sqlDate = new java.sql.Date(date1.getTime());
				        } catch (Exception ex) {
				            JOptionPane.showMessageDialog(null, "Vérifier vos données", "Erreur de saisie", JOptionPane.ERROR_MESSAGE);
				            return;
				        }
				        Connect();
				        
				        pst=con.prepareStatement("update medecin set nomprenom=?, datenaissance=?, tel=?, adresse=?, daterecrutement=?, specialite=? , CIN=?  where id=?");
				        pst.setString(1, txt_nom.getText());
				         date1 = txt_naissance.getDate();
				         sqlDate1 = new java.sql.Date(date1.getTime());
				        pst.setDate(2, sqlDate1);
				         tel = Integer.parseInt(txt_tel.getText());
				        pst.setInt(3, tel);
				        pst.setString(4, txt_adresse.getText());
				         date = txt_recrutement.getDate();
				         sqlDate = new java.sql.Date(date.getTime());
				        pst.setDate(5, sqlDate);
				        pst.setString(6, txt_specialite.getText());
				        pst.setString(7, txt_cin.getText());
				        int row = table.getSelectedRow();
				        int id = Integer.parseInt(table.getModel().getValueAt(row, 0).toString());
				        pst.setInt(8, id);

				        pst.executeUpdate();
				        con.close();

				        JOptionPane.showMessageDialog(null, "Medecin modifié");
				        txt_nom.setText("");
				        txt_naissance.setDate(null);
				        txt_tel.setText("");
				        txt_adresse.setText("");
				        txt_recrutement.setDate(null);
				        txt_specialite.setText("");
				        txt_cin.setText("");
				        afficherMedecins();


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
			        pst=con.prepareStatement("DELETE FROM medecin WHERE CIN=?");
			        pst.setString(1, txt_cin.getText());
			        pst.executeUpdate();
			        con.close();
			        JOptionPane.showMessageDialog(null, "Medecin supprimé");
			        txt_nom.setText("");
			        txt_naissance.setDate(null);
			        txt_tel.setText("");
			        txt_adresse.setText("");
			        txt_recrutement.setDate(null);
			        txt_specialite.setText("");
			        txt_cin.setText("");
			        afficherMedecins();
			        

			    } catch (Exception e1) {
			        e1.printStackTrace();
			    } 
			}
		});
		btn_supprimer.setBounds(773, 255, 171, 50);
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
		
		JDateChooser txt_recrutement = new JDateChooser();
		txt_recrutement.setBounds(267, 204, 192, 32);
		panel_1.add(txt_recrutement);
		
		JLabel lblNewLabel_1_1_1_2_1 = new JLabel("Nom et Prenom:");
		lblNewLabel_1_1_1_2_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel_1_1_1_2_1.setBounds(34, 26, 177, 25);
		panel_1.add(lblNewLabel_1_1_1_2_1);
		
		JPanel panel = new JPanel();
		panel.setBackground(UIManager.getColor("Button.background"));
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.setBounds(352, 10, 412, 51);
		frame.getContentPane().add(panel);
		
		JLabel lblNewLabel = new JLabel("Medecins");
		lblNewLabel.setForeground(new Color(0, 51, 153));
		lblNewLabel.setFont(new Font("Tw Cen MT Condensed", Font.BOLD, 41));
		panel.add(lblNewLabel);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(37, 396, 1061, 346);
		frame.getContentPane().add(panel_2);
		panel_2.setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 5, 1041, 300);
		scrollPane.setViewportView(table);

		panel_2.add(scrollPane);
		scrollPane.setViewportView(table);
		
		btnNewButton = new JButton("Retour");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Home home = null;
	            home = new Home();
	        
	       home.setVisible(true);
	        frame.dispose();
			}
		});
		btnNewButton.setBounds(21, 10, 85, 21);
		frame.getContentPane().add(btnNewButton);
		
		

		
	
	
	}
	public void setVisible(boolean b) {
	    frame.setVisible(b);
	}

}
