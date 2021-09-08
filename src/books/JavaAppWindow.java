package books;

import java.awt.EventQueue;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.border.TitledBorder;

import net.proteanit.sql.DbUtils;

import java.awt.SystemColor;
import java.awt.Color;
import java.sql.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.print.PrinterException;
public class JavaAppWindow {

	private JFrame frmKnjizara;
	private JTextField txtNazivKnjige;
	private JTextField txtIzdanje;
	private JTextField txtCijena;
	private JTable table;
	private JTextField txtPretrazi;
	private JTextField txtDostupno;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JavaAppWindow window = new JavaAppWindow();
					window.frmKnjizara.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	

	/**
	 * Create the application.
	 */
	public JavaAppWindow() {
		initialize();
		Connect();
		table_load();
	}
	
	Connection con;
	PreparedStatement pst;
	ResultSet rs;

	 public void Connect()
	    {
	        try {
	            Class.forName("com.mysql.jdbc.Driver");
	            con = DriverManager.getConnection("jdbc:mysql://localhost/knjizara", "root","");
	        }
	        catch (ClassNotFoundException ex) 
	        {
	          ex.printStackTrace();
	        }
	        catch (SQLException ex) 
	        {
	        	   ex.printStackTrace();
	        }

	    }


  public void table_load()
	    {
	    	try 
	    	{
		    pst = con.prepareStatement("select * from knjige");
		    rs = pst.executeQuery();
		    table.setModel(DbUtils.resultSetToTableModel(rs));
		} 
	    	catch (SQLException e) 
	    	 {
	    		e.printStackTrace();
		  } 
	    }
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmKnjizara = new JFrame();
		frmKnjizara.setTitle("Knjizara");
		frmKnjizara.getContentPane().setBackground(new Color(30, 144, 255));
		frmKnjizara.getContentPane().setForeground(new Color(244, 164, 96));
		frmKnjizara.setBounds(100, 100, 809, 523);
		frmKnjizara.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmKnjizara.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("KNJI\u017DARA");
		lblNewLabel.setForeground(new Color(0, 0, 0));
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 24));
		lblNewLabel.setBounds(317, 24, 135, 34);
		frmKnjizara.getContentPane().add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(176, 224, 230));
		panel.setBorder(new TitledBorder(null, "Dodavanje knjiga", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 83, 297, 183);
		frmKnjizara.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Naziv knjige:");
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.BOLD, 18));
		lblNewLabel_1.setBounds(10, 39, 119, 25);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Izdanje:");
		lblNewLabel_1_1.setFont(new Font("Times New Roman", Font.BOLD, 18));
		lblNewLabel_1_1.setBounds(10, 74, 86, 25);
		panel.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Cijena:");
		lblNewLabel_1_1_1.setFont(new Font("Times New Roman", Font.BOLD, 18));
		lblNewLabel_1_1_1.setBounds(10, 147, 77, 25);
		panel.add(lblNewLabel_1_1_1);
		
		txtNazivKnjige = new JTextField();
		txtNazivKnjige.setBounds(126, 43, 158, 20);
		panel.add(txtNazivKnjige);
		txtNazivKnjige.setColumns(10);
		
		txtIzdanje = new JTextField();
		txtIzdanje.setColumns(10);
		txtIzdanje.setBounds(126, 75, 158, 20);
		panel.add(txtIzdanje);
		
		txtCijena = new JTextField();
		txtCijena.setColumns(10);
		txtCijena.setBounds(126, 147, 158, 20);
		panel.add(txtCijena);
		
		JLabel lblNewLabel_1_1_1_1 = new JLabel("Dostupno:");
		lblNewLabel_1_1_1_1.setFont(new Font("Times New Roman", Font.BOLD, 18));
		lblNewLabel_1_1_1_1.setBounds(10, 110, 106, 25);
		panel.add(lblNewLabel_1_1_1_1);
		
		txtDostupno = new JTextField();
		txtDostupno.setColumns(10);
		txtDostupno.setBounds(126, 110, 158, 20);
		panel.add(txtDostupno);
		
		JButton btnNewButton = new JButton("DODAJ");
		btnNewButton.addActionListener(new ActionListener() {
			
			// DUGME DODAJ KNJIGU
			
			public void actionPerformed(ActionEvent e) {
				
				String nazivKnjige, izdanje, cijena, dostupno;
				
				nazivKnjige = txtNazivKnjige.getText();
				izdanje = txtIzdanje.getText();
				dostupno = txtDostupno.getText();
				cijena = txtCijena.getText();
							
				 try {
					pst = con.prepareStatement("insert into knjige(naziv, izdanje, dostupno, cijena)values(?,?,?,?)");
					pst.setString(1, nazivKnjige);
					pst.setString(2, izdanje);
					pst.setString(3, dostupno);
					pst.setString(4, cijena);
					pst.executeUpdate();
					
					JOptionPane.showMessageDialog(null, "Knjiga uspješno dodata!");
					table_load();
						           
					txtNazivKnjige.setText("");
					txtIzdanje.setText("");
					txtDostupno.setText("");
					txtCijena.setText("");
					txtNazivKnjige.requestFocus();
				   }
			 
				catch (SQLException e1) 
			        {
									
				e1.printStackTrace();
				
			}
			}
		});
		
		
		btnNewButton.setForeground(new Color(0, 128, 0));
		btnNewButton.setFont(new Font("Times New Roman", Font.BOLD, 16));
		btnNewButton.setBackground(new Color(152, 251, 152));
		btnNewButton.setBounds(25, 299, 99, 34);
		frmKnjizara.getContentPane().add(btnNewButton);
		
		JButton btnNoviUnos = new JButton("NOVI UNOS");
		btnNoviUnos.addActionListener(new ActionListener() {
			
			// NOVI UNOS
			public void actionPerformed(ActionEvent e) {
				txtNazivKnjige.setText("");
				txtIzdanje.setText("");
				txtDostupno.setText("");
				txtCijena.setText("");
				txtPretrazi.setText("");
				txtNazivKnjige.requestFocus();
			}
		});
		btnNoviUnos.setForeground(new Color(0, 0, 255));
		btnNoviUnos.setFont(new Font("Times New Roman", Font.BOLD, 16));
		btnNoviUnos.setBackground(new Color(175, 238, 238));
		btnNoviUnos.setBounds(160, 299, 129, 34);
		frmKnjizara.getContentPane().add(btnNoviUnos);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(317, 83, 466, 295);
		frmKnjizara.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(240, 230, 140));
		panel_1.setBorder(new TitledBorder(null, "PRETRA\u017DI", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(10, 386, 297, 66);
		frmKnjizara.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel_1_1_2 = new JLabel("Knjiga:");
		lblNewLabel_1_1_2.setBounds(21, 27, 58, 25);
		panel_1.add(lblNewLabel_1_1_2);
		lblNewLabel_1_1_2.setFont(new Font("Times New Roman", Font.BOLD, 18));
		
		txtPretrazi = new JTextField();
		txtPretrazi.addKeyListener(new KeyAdapter() {
			
			// PRETRAZIVANJE KNJIGE
			@Override
			public void keyReleased(KeyEvent e) {
				try {
			          
		            String unos = txtPretrazi.getText();

		                pst = con.prepareStatement("SELECT * FROM knjige WHERE naziv = ?");
		                pst.setString(1, unos);
		                ResultSet rs = pst.executeQuery();

		            if(rs.next() == true)
		            {
		              
		                String naziv = rs.getString(2);
		                String izdanjeS = rs.getString(3);
		                String dostupno = rs.getString(4);
		                String cijena = rs.getString(5);
		                
		                txtNazivKnjige.setText(naziv);
		                txtIzdanje.setText(izdanjeS);
		                txtDostupno.setText(dostupno);
		                txtCijena.setText(cijena);
		                
		                
		            }   
		            else
		            {
		            	txtNazivKnjige.setText("");
		            	txtIzdanje.setText("");
		                txtDostupno.setText("");
		                txtCijena.setText("");
		                 
		            }
		            
		        } 
			
			 catch (SQLException ex) {
		           
		        }
			}
		});
		txtPretrazi.setColumns(10);
		txtPretrazi.setBounds(89, 31, 173, 20);
		panel_1.add(txtPretrazi);
		
		JButton btnIzmjeni = new JButton("IZMJENI");
		btnIzmjeni.addActionListener(new ActionListener() {
			
			// IZMJENI KNJIGU
			public void actionPerformed(ActionEvent e) {
				
				
				String nazivKnjige, izdanje, cijena, dostupno, unos;
				
				nazivKnjige = txtNazivKnjige.getText();
				izdanje = txtIzdanje.getText();
				dostupno = txtDostupno.getText();
				cijena = txtCijena.getText();
				unos = txtPretrazi.getText();
							
				 try {
					pst = con.prepareStatement("update knjige set naziv=?, izdanje=?, dostupno=?, cijena=? where naziv=?");
					
					pst.setString(1, nazivKnjige);
					pst.setString(2, izdanje);
					pst.setString(3, dostupno);
					pst.setString(4, cijena);
					pst.setString(5, unos);
					pst.executeUpdate();
					
					JOptionPane.showMessageDialog(null, "Knjiga uspješno izmjenjena!");
					table_load();
						           
					txtNazivKnjige.setText("");
					txtIzdanje.setText("");
					txtDostupno.setText("");
					txtCijena.setText("");
					txtNazivKnjige.requestFocus();
				   }
			 
				catch (SQLException e1) 
			        {
									
				e1.printStackTrace();
				
			}
			}
		});
		btnIzmjeni.setForeground(new Color(255, 255, 255));
		btnIzmjeni.setFont(new Font("Times New Roman", Font.BOLD, 16));
		btnIzmjeni.setBackground(new Color(47, 79, 79));
		btnIzmjeni.setBounds(408, 413, 110, 34);
		frmKnjizara.getContentPane().add(btnIzmjeni);
		
		JButton btnObrisi = new JButton("OBRI\u0160I");
		btnObrisi.addActionListener(new ActionListener() {
			
			//BRISANJE KNJIGE
			
			public void actionPerformed(ActionEvent e) {
				
				String unos;
				
				unos = txtPretrazi.getText();
							
				 try {
					pst = con.prepareStatement("DELETE FROM knjige WHERE naziv=?");
					pst.setString(1, unos);
							
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "Knjiga uspješno izbrisana!");
					table_load();
						           
					txtNazivKnjige.setText("");
					txtIzdanje.setText("");
					txtDostupno.setText("");
					txtCijena.setText("");
					txtPretrazi.setText("");
					txtNazivKnjige.requestFocus();
				   }
			 
				catch (SQLException e1) 
			        {
									
				e1.printStackTrace();
				
			}
			}
		});
		btnObrisi.setForeground(new Color(255, 255, 255));
		btnObrisi.setFont(new Font("Times New Roman", Font.BOLD, 16));
		btnObrisi.setBackground(new Color(255, 0, 0));
		btnObrisi.setBounds(581, 413, 99, 34);
		frmKnjizara.getContentPane().add(btnObrisi);
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon("D:\\Downloads\\book (1).png"));
		lblNewLabel_2.setBounds(10, 11, 80, 61);
		frmKnjizara.getContentPane().add(lblNewLabel_2);
	}
}
		

