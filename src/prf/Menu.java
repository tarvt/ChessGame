package prf;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Image;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Menu extends JFrame {

	private JPanel contentPane;
	private JTextField textname;
	private JTextField textp1;
	private JTextField textp2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Menu frame = new Menu();
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
	public Menu() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 605, 800);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 153));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("play online");
		btnNewButton.setBackground(new Color(255, 204, 153));
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Board.player=textname.getText();
				dispose();
				Board.main(null);
			}
		});
		btnNewButton.setBounds(39, 319, 275, 42);
		contentPane.add(btnNewButton);
		
		Image chess = new ImageIcon(this.getClass().getResource("/chess1.png")).getImage();
		JLabel lbllogo = new JLabel();
		lbllogo.setBounds(133, 11, 300, 217);
		lbllogo.setIcon(new ImageIcon(chess.getScaledInstance(300, -1, Image.SCALE_SMOOTH)));
		contentPane.add(lbllogo);
		
		JLabel lblChessGame = new JLabel("chess game");
		lblChessGame.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 29));
		lblChessGame.setBounds(186, 218, 235, 59);
		contentPane.add(lblChessGame);
		
		JLabel lblPleaseEnterYour = new JLabel("please enter your name :");
		lblPleaseEnterYour.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblPleaseEnterYour.setBounds(29, 288, 149, 14);
		contentPane.add(lblPleaseEnterYour);
		
		textname = new JTextField();
		textname.setBackground(new Color(255, 255, 153));
		textname.setBounds(186, 288, 151, 20);
		contentPane.add(textname);
		textname.setColumns(10);
		
		JLabel lblPleaseEnterFirst = new JLabel("please enter first player :");
		lblPleaseEnterFirst.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblPleaseEnterFirst.setBounds(273, 413, 148, 14);
		contentPane.add(lblPleaseEnterFirst);
		
		textp1 = new JTextField();
		textp1.setBackground(new Color(255, 255, 153));
		textp1.setBounds(430, 410, 149, 20);
		contentPane.add(textp1);
		textp1.setColumns(10);
		
		JLabel lblPleaseEnterSecond = new JLabel("please enter second player :");
		lblPleaseEnterSecond.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblPleaseEnterSecond.setBounds(265, 438, 168, 14);
		contentPane.add(lblPleaseEnterSecond);
		
		textp2 = new JTextField();
		textp2.setBackground(new Color(255, 255, 153));
		textp2.setBounds(430, 435, 149, 20);
		contentPane.add(textp2);
		textp2.setColumns(10);
		
		JButton btnNewButton_1 = new JButton("play with your friend");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Boardof.player1 =textp1.getText();
				Boardof.player2 =textp2.getText();
				dispose();
				Boardof.main(null);
				
			}
		});
		btnNewButton_1.setBackground(new Color(255, 204, 153));
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNewButton_1.setBounds(279, 485, 300, 48);
		contentPane.add(btnNewButton_1);
	}
}
