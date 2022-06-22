package prf;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.ietf.jgss.Oid;

//import oop.Main;
//import oop.Main.MainThread;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Font;

public class Board extends JFrame {
	static JButton[][] B = new JButton[8][8];
	static String inside[][] = { { "br", "bn", "bb", "bq", "bk", "bb", "bn", "br" },
			{ "bp", "bp", "bp", "bp", "bp", "bp", "bp", "bp" }, { "0", "0", "0", "0", "0", "0", "0", "0" },
			{ "0", "0", "0", "0", "0", "0", "0", "0" }, { "0", "0", "0", "0", "0", "0", "0", "0" },
			{ "0", "0", "0", "0", "0", "0", "0", "0" }, { "wp", "wp", "wp", "wp", "wp", "wp", "wp", "wp" },
			{ "wr", "wn", "wb", "wq", "wk", "wb", "wn", "wr" } };
	private JPanel contentPane;
	Image bb = new ImageIcon(this.getClass().getResource("/bb.png")).getImage();
	Image bk = new ImageIcon(this.getClass().getResource("/bk.png")).getImage();
	Image bn = new ImageIcon(this.getClass().getResource("/bn.png")).getImage();
	Image bp = new ImageIcon(this.getClass().getResource("/bp.png")).getImage();
	Image bq = new ImageIcon(this.getClass().getResource("/bq.png")).getImage();
	Image br = new ImageIcon(this.getClass().getResource("/br.png")).getImage();
	Image wb = new ImageIcon(this.getClass().getResource("/wb.png")).getImage();
	Image wk = new ImageIcon(this.getClass().getResource("/wk.png")).getImage();
	Image wn = new ImageIcon(this.getClass().getResource("/wn.png")).getImage();
	Image wp = new ImageIcon(this.getClass().getResource("/wp.png")).getImage();
	Image wq = new ImageIcon(this.getClass().getResource("/wq.png")).getImage();
	Image wr = new ImageIcon(this.getClass().getResource("/wr.png")).getImage();
	static JLabel lblnum_wp = new JLabel("0"), lblnum_wn = new JLabel("0"), lblnum_wr = new JLabel("0"),
			lblnum_wb = new JLabel("0"), lblnum_wq = new JLabel("0");
	static JLabel lblnum_bp = new JLabel("0"), lblnum_bn = new JLabel("0"), lblnum_br = new JLabel("0"),
			lblnum_bb = new JLabel("0"), lblnum_bq = new JLabel("0");
	// number of eaten mohres
	static int num_bb = 0, num_bk = 0, num_bn = 0, num_bp = 0, num_bq = 0, num_br = 0, num_wb = 0, num_wk = 0,
			num_wn = 0, num_wp = 0, num_wq = 0, num_wr = 0;
	private static BufferedReader in;
	private static Socket socket;
	private static PrintWriter out;
	private static Board frame;
	private static JLabel lblTurn = new JLabel(), lblwWB = new JLabel(), lbltime = new JLabel("0");;
	private static int playerNumber;
	private static boolean turn = true , win = false;
	static int king_moved=0 , rookl_moved =0, rookr_moved =0;
	static String player = "player";
	private static int time = 0, period = 0, exit = 0;
	static boolean[][] b = new boolean[8][8] ;
	// move is the state when we pick the first box or the second on

	/**
	 * Launch the application.
	 */
	static Timer timer = new Timer();
	static TimerTask task = new TimerTask() {

		@Override
		public void run() {
			if ((playerNumber == 1 && turn) || (playerNumber == 2 && !turn)) {
				time--;
				lbltime.setText(Integer.toString(time));
				if (time == 0) {
					out.println("end");
					out.flush();
					JOptionPane.showMessageDialog(frame, player + " your time is done");
					
				}
			}
		}
	};

	public static class MainThread extends Thread {
		public void run() {
			while (true) {
				String line = null;
				try {
					line = in.readLine();
					System.out.println("Client : " + line);
				} catch (IOException e1) {
					System.out.println(1);
					e1.printStackTrace();
				}
				// when more than 2 people are here to play
				if (line.startsWith("SERVER-BUSY")) {
					frame.dispose();
					JOptionPane.showMessageDialog(frame, "server is busy");
					// when the game is ready
				} else if (line.startsWith("START")) {
					playerNumber = line.charAt(line.length() - 1) - '0';
					if (playerNumber == 1) {
						// the first player is white we get time and send it to server and server send
						// it to all
						try {
							time = Integer.parseInt(JOptionPane.showInputDialog(lblTurn,
									player + "you are white \nplease enter the hole time in min"));
							lblTurn.setText("your turn");
						} catch (Exception e) {
							time = 5;
						} finally {
							out.println("time" + Integer.toString(time));
							out.flush();
						}
						try {
							period = Integer
									.parseInt(JOptionPane.showInputDialog(lblTurn, "please enter the time in seconds"));
						} catch (Exception e) {
							period = 10;
						} finally {
							out.println("period" + Integer.toString(period));
							out.flush();
						}

						lblTurn.setText("your turn");
						lblwWB.setText("WHITE");
						frame.setTitle(player);
					} else {
						frame.setTitle(player);
						JOptionPane.showMessageDialog(lblTurn, player + " you are black");
						lblTurn.setText("");
						lblwWB.setText("BLACK");
					} // it line starts with turn it means the turn should change
				} else if (line.startsWith("TURN")) {
					turn = !turn;
					System.out.println(playerNumber + " : " + turn);
					lblTurn.setText("");
					out.println("lbl" + playerNumber);
					out.flush();
				} // if line starts with time , we get time from sever and insaulize it
				else if (line.startsWith("time")) {
					String s = line.substring(4, line.length());
					time = Integer.parseInt(s) * 60;
					lbltime.setText(Integer.toString(time));
					timer.scheduleAtFixedRate(task, 1000, 1000);
				} else if (line.startsWith("period")) {
					String s = line.substring(6, line.length());
					period = Integer.parseInt(s);
				} else if (line.startsWith("lbl")) {
					// int a = Integer.parseInt(line.substring(3, line.length()));
					if ((playerNumber == 1 && turn) || (playerNumber == 2 && !turn)) {
						time = time + period;

						lblTurn.setText("your turn");
					}

				}else if (line.startsWith("check")) {
					JOptionPane.showMessageDialog(lblTurn, "CHECK!");
				}
				// help is the green boxes , we get array help and pass it to method help
				else if (line.startsWith("c")) {
					b = change_toB(line);
					help(b);

				} // if line starts with move we get the new array and update the scene
				else if (line.startsWith("move")) {
					String s = line.substring(4, line.length());
					inside = chang_toarray(s);
					update();
					for (int l = 0; l < 8; l++) {
						for (int g = 0; g < 8; g++) {
							System.out.print(inside[l][g]);
						}
						System.out.println();
					}
					out.println("turn_change");
					out.flush();
					update();
					
					// if it starts with equality a massage should be shown that asks if we want to
					// finish game or not
				} else if (line.startsWith("equality")) {
					try {
						exit = Integer.parseInt(JOptionPane.showInputDialog(lblTurn,
								"the other player offered ceasefire\n1.i will take it\n2.i want to go one"));
					} catch (Exception e2) {
						exit = 2;
					}
					if (exit == 1) {
						out.println("end");
						out.flush();
					}
				} // if line starts with up , we update the scene
				else if (line.startsWith("up")) {
					String s = line.substring(2, line.length());
					inside = chang_toarray(s);
					update();
				} // if it starts with tm we do time = time +20
				else if (line.startsWith("tm")) {
					JOptionPane.showMessageDialog(lblTurn, "time +20");
					time = time + 20;
				} // when the game is over :
				else if (line.startsWith("end")) {
					JOptionPane.showMessageDialog(lblwWB, "the game is over ");
					frame.dispose();
				} // if one of the powns are at the end of the game the must be upgrade
				else if (line.startsWith("grade")) {
					int ans = 1;
					try {
						ans = Integer.parseInt(JOptionPane.showInputDialog(lblTurn,
								"the pown needs to be upgraded choose what you want: \n1.queen \n2.bishop\n3.knight \n4.rook"));
					} catch (Exception e) {
						ans = 1;
					} finally {
						out.println(line + ans + playerNumber);
						out.flush();
					}
				}
				//
				else if (line.startsWith("win")) {
					JOptionPane.showMessageDialog(lblTurn, "congratulations you won");
					frame.dispose();
					Menu.main(null);
				}
				// if player clicked castle its the result :
				else if (line.startsWith("tastle")) {
					String answer = line.substring(6, 8);
					king_moved = Integer.parseInt(line.substring(8, 9));
					rookl_moved =Integer.parseInt(line.substring(9, 10));
					rookr_moved = Integer.parseInt(line.substring(10, 11));
					int ans = 1;
					if (answer.equals("11")) {
						if (king_moved ==0 && rookl_moved ==0 && rookr_moved==0) {
						try {
							ans = Integer.parseInt(JOptionPane.showInputDialog(lblTurn,
									"do you want to do castle \n1.right  side \n2.left side"));
						} catch (Exception e) {
							ans = 1;
						} finally {
							out.println("docsl" + ans + playerNumber);
							out.flush();
						}
						}else {
							JOptionPane.showMessageDialog(lblTurn, " you have moved your pieces befor ");
						}
					} else if (answer.equals("01")){
						if (king_moved ==0 &&  rookr_moved==0) {
						JOptionPane.showMessageDialog(lblTurn, " right side castle : ");
						out.println("docsl" + "1" + playerNumber);
						out.flush();
						}else {
							JOptionPane.showMessageDialog(lblTurn, " you have moved your pieces befor ");
						}
					} else if (answer.equals("10")) {
						if (king_moved ==0 && rookl_moved ==0 ) {
						JOptionPane.showMessageDialog(lblTurn, " left side castle : ");
						out.println("docsl" + "2" + playerNumber);
						out.flush();
						}else {
							JOptionPane.showMessageDialog(lblTurn, " you have moved your pieces befor ");
						}
					} else if (answer.equals("00")) {
						JOptionPane.showMessageDialog(lblTurn, " we can not make castle ");
					}

				} else if (line.startsWith("maate")) {
					int answer = 0;
					try {
					answer = Integer.parseInt(JOptionPane.showInputDialog(frame, player + " ," + "do you accept that you are mate and you lose ?\n1.yes\n2.no"));
					}catch (Exception e) {
						answer=0;
					}
					if (answer == 1) {
						out.println("lost" + playerNumber);
						out.flush();
						JOptionPane.showMessageDialog(lblTurn, "you lost the game :(");
						frame.dispose();
					}
					
				}
				// this is for when one of the the mohres is eaten and we want to update the
					// upper labels
				else if (line.startsWith("eat")) {
					String s = line.substring(3, line.length());
					if (s.charAt(0) == 'w') {
						if (s.charAt(1) == 'p') {
							num_wp++;
							lblnum_wp.setText(Integer.toString(num_wp));
						}
						if (s.charAt(1) == 'n') {
							num_wn++;
							lblnum_wn.setText(Integer.toString(num_wn));
						}
						if (s.charAt(1) == 'r') {
							num_wr++;
							lblnum_wr.setText(Integer.toString(num_wr));
						}
						if (s.charAt(1) == 'b') {
							num_wb++;
							lblnum_wb.setText(Integer.toString(num_wb));
						}
						if (s.charAt(1) == 'n') {
							num_wq++;
							lblnum_wq.setText(Integer.toString(num_wq));
						}
					}
					if (s.charAt(0) == 'b') {
						if (s.charAt(1) == 'p') {
							num_bp++;
							lblnum_bp.setText(Integer.toString(num_bp));
						}
						if (s.charAt(1) == 'n') {
							num_bn++;
							lblnum_bn.setText(Integer.toString(num_bn));
						}
						if (s.charAt(1) == 'r') {
							num_br++;
							lblnum_br.setText(Integer.toString(num_br));
						}
						if (s.charAt(1) == 'b') {
							num_bb++;
							lblnum_bb.setText(Integer.toString(num_bb));
						}
						if (s.charAt(1) == 'n') {
							num_bq++;
							lblnum_bq.setText(Integer.toString(num_bq));
						}
					}
				}

			}
		}
		// this method make the buttons that we can click green
		public void help(boolean[][] b) {
			for (int i = 0; i < 8; i++)
				for (int j = 0; j < 8; j++) {
					if (b[i][j] == true)
						B[i][j].setBackground(new Color(29, 131, 72));
				}
		}
// we take string c and change it to a boolean array of things we can click on 
		public boolean[][] change_toB(String c) {
			boolean[][] b = new boolean[8][8];
			int count = 1;
			for (int x = 0; x < 8; x++)
				for (int y = 0; y < 8; y++) {
					if (c.charAt(count) == '1')
						b[x][y] = true;
					else
						b[x][y] = false;
					count++;
				}
			return b;
		}
		// we take string c and change it to a string array of inside
		public String[][] chang_toarray(String s) {
			int count = 0;
			String[][] ins = new String[8][8];
			for (int x = 0; x < 8; x++)
				for (int y = 0; y < 8; y++) {
					if (s.charAt(count) == '0') {
						ins[x][y] = "0";
						count++;
					} else {
						ins[x][y] = "" + s.charAt(count) + s.charAt(count + 1);
						count = count + 2;
					}
				}
			return ins;

		}

		// this method is for updating new moves
		public void update() {
			Image bb = new ImageIcon(this.getClass().getResource("/bb.png")).getImage();
			Image bk = new ImageIcon(this.getClass().getResource("/bk.png")).getImage();
			Image bn = new ImageIcon(this.getClass().getResource("/bn.png")).getImage();
			Image bp = new ImageIcon(this.getClass().getResource("/bp.png")).getImage();
			Image bq = new ImageIcon(this.getClass().getResource("/bq.png")).getImage();
			Image br = new ImageIcon(this.getClass().getResource("/br.png")).getImage();
			Image wb = new ImageIcon(this.getClass().getResource("/wb.png")).getImage();
			Image wk = new ImageIcon(this.getClass().getResource("/wk.png")).getImage();
			Image wn = new ImageIcon(this.getClass().getResource("/wn.png")).getImage();
			Image wp = new ImageIcon(this.getClass().getResource("/wp.png")).getImage();
			Image wq = new ImageIcon(this.getClass().getResource("/wq.png")).getImage();
			Image wr = new ImageIcon(this.getClass().getResource("/wr.png")).getImage();
			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {
					// set pictures
					if (inside[i][j].equals("bb"))
						B[i][j].setIcon(new ImageIcon(bb.getScaledInstance(40, -1, Image.SCALE_SMOOTH)));
					if (inside[i][j].equals("bk"))
						B[i][j].setIcon(new ImageIcon(bk.getScaledInstance(40, -1, Image.SCALE_SMOOTH)));
					if (inside[i][j].equals("bn"))
						B[i][j].setIcon(new ImageIcon(bn.getScaledInstance(40, -1, Image.SCALE_SMOOTH)));
					if (inside[i][j].equals("bp"))
						B[i][j].setIcon(new ImageIcon(bp.getScaledInstance(40, -1, Image.SCALE_SMOOTH)));
					if (inside[i][j].equals("bq"))
						B[i][j].setIcon(new ImageIcon(bq.getScaledInstance(40, -1, Image.SCALE_SMOOTH)));
					if (inside[i][j].equals("br"))
						B[i][j].setIcon(new ImageIcon(br.getScaledInstance(40, -1, Image.SCALE_SMOOTH)));
					if (inside[i][j].equals("wb"))
						B[i][j].setIcon(new ImageIcon(wb.getScaledInstance(40, -1, Image.SCALE_SMOOTH)));
					if (inside[i][j].equals("wk"))
						B[i][j].setIcon(new ImageIcon(wk.getScaledInstance(40, -1, Image.SCALE_SMOOTH)));
					if (inside[i][j].equals("wn"))
						B[i][j].setIcon(new ImageIcon(wn.getScaledInstance(40, -1, Image.SCALE_SMOOTH)));
					if (inside[i][j].equals("wp"))
						B[i][j].setIcon(new ImageIcon(wp.getScaledInstance(40, -1, Image.SCALE_SMOOTH)));
					if (inside[i][j].equals("wq"))
						B[i][j].setIcon(new ImageIcon(wq.getScaledInstance(40, -1, Image.SCALE_SMOOTH)));
					if (inside[i][j].equals("wr"))
						B[i][j].setIcon(new ImageIcon(wr.getScaledInstance(40, -1, Image.SCALE_SMOOTH)));
					if (inside[i][j].equals("0"))
						B[i][j].setIcon(null);
					if (i % 2 == 0) {
						if (j % 2 == 0)
							B[i][j].setBackground(new Color(235, 152, 78));
						else
							B[i][j].setBackground(new Color(252, 243, 207));
					}
					if (i % 2 != 0) {
						if (j % 2 != 0)
							B[i][j].setBackground(new Color(235, 152, 78));
						else
							B[i][j].setBackground(new Color(252, 243, 207));
					}
					// */
				}
			}
			if ((playerNumber == 1 && turn) || (playerNumber == 2 && !turn))
				lblTurn.setText("your " + Integer.toString(time));
		}

	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainThread mainThread = new MainThread();
					frame = new Board();
					mainThread.start();
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
	public Board() {

		try {
			socket = new Socket("localhost", 9994);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream());
		} catch (IOException e) {
			
		}
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setBounds(100, 100, 605, 800);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(249, 231, 159));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 0, 0 };
		gbl_contentPane.rowHeights = new int[] { 0, 0, 0 };
		gbl_contentPane.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 1.0, 1.4, Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(249, 231, 159));
		panel.setLayout(null);
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		contentPane.add(panel, gbc_panel);
		lblTurn.setFont(new Font("Tahoma", Font.BOLD, 13));

		lblTurn.setBounds(223, 49, 151, 48);
		panel.add(lblTurn);

		// setting upper icons
		JLabel lbl_wp = new JLabel();
		lbl_wp.setBounds(0, 90, 46, 35);
		lbl_wp.setIcon(new ImageIcon(wp.getScaledInstance(40, -1, Image.SCALE_SMOOTH)));
		panel.add(lbl_wp);

		lblnum_wp.setBounds(15, 115, 46, 35);
		panel.add(lblnum_wp);

		JLabel lbl_wn = new JLabel();
		lbl_wn.setBounds(30, 90, 46, 35);
		lbl_wn.setIcon(new ImageIcon(wn.getScaledInstance(40, -1, Image.SCALE_SMOOTH)));
		panel.add(lbl_wn);

		lblnum_wn.setBounds(45, 115, 46, 35);
		panel.add(lblnum_wn);

		JLabel lbl_wr = new JLabel();
		lbl_wr.setBounds(60, 90, 46, 35);
		lbl_wr.setIcon(new ImageIcon(wr.getScaledInstance(40, -1, Image.SCALE_SMOOTH)));
		panel.add(lbl_wr);

		lblnum_wr.setBounds(75, 115, 46, 35);
		panel.add(lblnum_wr);

		JLabel lbl_wb = new JLabel();
		lbl_wb.setBounds(90, 90, 46, 39);
		lbl_wb.setIcon(new ImageIcon(wb.getScaledInstance(40, -1, Image.SCALE_SMOOTH)));
		panel.add(lbl_wb);

		lblnum_wb.setBounds(105, 115, 46, 35);
		panel.add(lblnum_wb);

		JLabel lbl_wq = new JLabel();
		lbl_wq.setBounds(120, 90, 46, 35);
		lbl_wq.setIcon(new ImageIcon(wq.getScaledInstance(40, -1, Image.SCALE_SMOOTH)));
		panel.add(lbl_wq);

		lblnum_wq.setBounds(135, 115, 46, 35);
		panel.add(lblnum_wq);

		JLabel lbl_bp = new JLabel();
		lbl_bp.setBounds(400, 90, 46, 35);
		lbl_bp.setIcon(new ImageIcon(bp.getScaledInstance(40, -1, Image.SCALE_SMOOTH)));
		panel.add(lbl_bp);

		lblnum_bp.setBounds(415, 115, 46, 35);
		panel.add(lblnum_bp);

		JLabel lbl_bn = new JLabel();
		lbl_bn.setBounds(430, 90, 46, 35);
		lbl_bn.setIcon(new ImageIcon(bn.getScaledInstance(40, -1, Image.SCALE_SMOOTH)));
		panel.add(lbl_bn);

		lblnum_bn.setBounds(445, 115, 46, 35);
		panel.add(lblnum_bn);

		JLabel lbl_br = new JLabel();
		lbl_br.setBounds(460, 90, 46, 35);
		lbl_br.setIcon(new ImageIcon(br.getScaledInstance(40, -1, Image.SCALE_SMOOTH)));
		panel.add(lbl_br);

		lblnum_br.setBounds(475, 115, 46, 35);
		panel.add(lblnum_br);

		JLabel lbl_bb = new JLabel();
		lbl_bb.setBounds(490, 90, 46, 39);
		lbl_bb.setIcon(new ImageIcon(bb.getScaledInstance(40, -1, Image.SCALE_SMOOTH)));
		panel.add(lbl_bb);

		lblnum_bb.setBounds(505, 115, 46, 35);
		panel.add(lblnum_bb);

		JLabel lbl_bq = new JLabel();
		lbl_bq.setBounds(520, 90, 46, 35);
		lbl_bq.setIcon(new ImageIcon(bq.getScaledInstance(40, -1, Image.SCALE_SMOOTH)));
		panel.add(lbl_bq);

		lblnum_bq.setBounds(535, 115, 46, 35);
		panel.add(lblnum_bq);

		lblwWB.setBounds(248, 11, 46, 14);
		panel.add(lblwWB);

		JLabel lblNewLabel = new JLabel("time left :");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel.setBounds(212, 91, 67, 20);
		panel.add(lblNewLabel);

		lbltime.setFont(new Font("Tahoma", Font.BOLD, 11));
		lbltime.setBounds(289, 95, 46, 14);
		panel.add(lbltime);
		
		// when this button is when its our turn and we want to stop the game or offer
		// case fire
		Image equal = new ImageIcon(this.getClass().getResource("/equality.png")).getImage();
		JButton btnequal = new JButton("");
		btnequal.setFocusPainted(false);
		btnequal.setContentAreaFilled(false);
		btnequal.setIcon(new ImageIcon(equal.getScaledInstance(25, -1, Image.SCALE_SMOOTH)));
		btnequal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				out.println("equal" + playerNumber);
				out.flush();
			}
		});
		btnequal.setBounds(77, 11, 59, 35);
		panel.add(btnequal);

		// when this button is clicked 20s will be added to the other player
		Image timer = new ImageIcon(this.getClass().getResource("/timer.png")).getImage();
		JButton btntimer = new JButton("");
		btntimer.setFocusPainted(false);
		btntimer.setContentAreaFilled(false);
		btntimer.setIcon(new ImageIcon(timer.getScaledInstance(25, -1, Image.SCALE_SMOOTH)));
		btntimer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				out.println("tm+" + playerNumber);
				out.flush();
			}
		});
		btntimer.setBounds(505, 11, 61, 35);
		panel.add(btntimer);

		// this button is for when we want to make castle
		Image castle = new ImageIcon(this.getClass().getResource("/castle.png")).getImage();
		JButton btncastle = new JButton("");
		btncastle.setFocusPainted(false);
		btncastle.setContentAreaFilled(false);
		btncastle.setIcon(new ImageIcon(castle.getScaledInstance(25, -1, Image.SCALE_SMOOTH)));
		btncastle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if ((playerNumber == 1 && turn) || (playerNumber == 2 && !turn)) {
					out.println("permcastle" + playerNumber +change_tostring());
					out.flush();
				} else
					JOptionPane.showMessageDialog(lblTurn, "not your  turn ");
			}
		});
		btncastle.setBounds(15, 11, 61, 35);
		panel.add(btncastle);
		
		// this button is for when one of the players is mate 
		Image mate = new ImageIcon(this.getClass().getResource("/mate.png")).getImage();
		JButton btnmate = new JButton("");
		btnmate.setFocusPainted(false);
		btnmate.setContentAreaFilled(false);
		btnmate.setIcon(new ImageIcon(mate.getScaledInstance(50, -1, Image.SCALE_SMOOTH)));
		btnmate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				out.println("mate" + playerNumber);
				out.flush();
				
				
			}
		});
		btnmate.setBounds(445, 11, 59, 35);
		panel.add(btnmate);

		JPanel panel_1 = new JPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.insets = new Insets(0, 0, 5, 0);
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 1;
		contentPane.add(panel_1, gbc_panel_1);
		panel_1.setLayout(new GridLayout(8, 8));

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				B[i][j] = new JButton();
				// set pictures
				// *
				if (inside[i][j].equals("bb"))
					B[i][j].setIcon(new ImageIcon(bb.getScaledInstance(40, -1, Image.SCALE_SMOOTH)));
				if (inside[i][j].equals("bk"))
					B[i][j].setIcon(new ImageIcon(bk.getScaledInstance(40, -1, Image.SCALE_SMOOTH)));
				if (inside[i][j].equals("bn"))
					B[i][j].setIcon(new ImageIcon(bn.getScaledInstance(40, -1, Image.SCALE_SMOOTH)));
				if (inside[i][j].equals("bp"))
					B[i][j].setIcon(new ImageIcon(bp.getScaledInstance(40, -1, Image.SCALE_SMOOTH)));
				if (inside[i][j].equals("bq"))
					B[i][j].setIcon(new ImageIcon(bq.getScaledInstance(40, -1, Image.SCALE_SMOOTH)));
				if (inside[i][j].equals("br"))
					B[i][j].setIcon(new ImageIcon(br.getScaledInstance(40, -1, Image.SCALE_SMOOTH)));
				if (inside[i][j].equals("wb"))
					B[i][j].setIcon(new ImageIcon(wb.getScaledInstance(40, -1, Image.SCALE_SMOOTH)));
				if (inside[i][j].equals("wk"))
					B[i][j].setIcon(new ImageIcon(wk.getScaledInstance(40, -1, Image.SCALE_SMOOTH)));
				if (inside[i][j].equals("wn"))
					B[i][j].setIcon(new ImageIcon(wn.getScaledInstance(40, -1, Image.SCALE_SMOOTH)));
				if (inside[i][j].equals("wp"))
					B[i][j].setIcon(new ImageIcon(wp.getScaledInstance(40, -1, Image.SCALE_SMOOTH)));
				if (inside[i][j].equals("wq"))
					B[i][j].setIcon(new ImageIcon(wq.getScaledInstance(40, -1, Image.SCALE_SMOOTH)));
				if (inside[i][j].equals("wr"))
					B[i][j].setIcon(new ImageIcon(wr.getScaledInstance(40, -1, Image.SCALE_SMOOTH)));

				// */

				if (i % 2 == 0) {
					if (j % 2 == 0)
						B[i][j].setBackground(new Color(235, 152, 78));
					else
						B[i][j].setBackground(new Color(252, 243, 207));
				}
				if (i % 2 != 0) {
					if (j % 2 != 0)
						B[i][j].setBackground(new Color(235, 152, 78));
					else
						B[i][j].setBackground(new Color(252, 243, 207));

				}

				B[i][j].addMouseListener(new MouseListener() {

					@Override
					public void mouseReleased(MouseEvent arg0) {}

					@Override
					public void mousePressed(MouseEvent arg0) {}

					@Override
					public void mouseExited(MouseEvent arg0) {}

					@Override
					public void mouseEntered(MouseEvent arg0) {}

					@Override
					public void mouseClicked(MouseEvent eventButton) {
						int j = getI(eventButton.getComponent().getX());
						int i = getJ(eventButton.getComponent().getY());
						System.out.println(inside[i][j]);
						if (((playerNumber == 1 && turn) || (playerNumber == 2 && !turn))
								&& (b[i][j] == true || (inside[i][j].charAt(0) == 'w' && playerNumber == 1)
										|| (inside[i][j].charAt(0) == 'b' && playerNumber == 2))) {
							help_goner();
							out.println("BTN-CLICKED" + " " + Integer.toString(i) + "_" + Integer.toString(j)
									+ change_tostring() + Integer.toString(playerNumber));
							out.flush();
						} else {
							JOptionPane.showMessageDialog(lblTurn,"NOT YOUR TURN");
						}

					}
				});
				panel_1.add(B[i][j]);
			}
		}

	}

	// its a method that eliminates the grean helps
	public void help_goner() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (i % 2 == 0) {
					if (j % 2 == 0)
						B[i][j].setBackground(new Color(235, 152, 78));
					else
						B[i][j].setBackground(new Color(252, 243, 207));
				}
				if (i % 2 != 0) {
					if (j % 2 != 0)
						B[i][j].setBackground(new Color(235, 152, 78));
					else
						B[i][j].setBackground(new Color(252, 243, 207));
				}

			}
		}

	}
// this method is for changing inside into  string
	public String change_tostring() {
		String s = "";
		for (int x = 0; x < 8; x++)
			for (int y = 0; y < 8; y++)
				s = s + inside[x][y];

		return s;
	}

	public static int getI(float x) {
		int i = (int) (x * 8 / 505);
		if (i == 8)
			i--;
		return i;
	}

	public static int getJ(float y) {
		int j = (int) (y * 8 / 518);
		if (j == 8)
			j--;
		return j;
	}
}
