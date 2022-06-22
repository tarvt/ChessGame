package prf;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import prf.Board.MainThread;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Boardof extends JFrame {

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
	static JLabel lbltime1 = new JLabel("0"), lbltime2 = new JLabel("0");
	static int num_bb = 0, num_bk = 0, num_bn = 0, num_bp = 0, num_bq = 0, num_br = 0, num_wb = 0, num_wk = 0,
			num_wn = 0, num_wp = 0, num_wq = 0, num_wr = 0;
	// private static ObjectInputStream in ;
	private static Boardof frame;
	private static JLabel lblTurn = new JLabel(), lblwWB = new JLabel(), lbltimeIfo = new JLabel("New label"),
			lblcheck = new JLabel("");
	boolean check1 = false, check2 = false, king1_moved = false, rook1l_moved = false, rook1r_moved = false;
	boolean king2_moved = false, rook2l_moved = false, rook2r_moved = false;
	private static int turn = 1;
	static String player1 = "player1", player2 = "player2";
	private static int time = 6, period = 0, time1, time2;
	int first_i, first_j;
	static boolean[][] b = new boolean[8][8], check = new boolean[8][8];
	static String record_moves1 = "", record_moves2 = "", frist_move = "";
	// move is the state when we pick the first box or the second on

	/**
	 * Launch the application.
	 */
	int secondpassed = 0;
	Timer timer = new Timer();
	TimerTask task = new TimerTask() {

		@Override
		public void run() {
			if (turn == 1) {
				time1--;
				lbltime1.setText(Integer.toString(time1));
				if (time1 == 0) {
					JOptionPane.showMessageDialog(frame, player1 + " your time is done");
					frame.dispose();
				}
			}
			if (turn == 2) {
				time2--;
				lbltime2.setText(Integer.toString(time2));
				if (time2 == 0) {
					JOptionPane.showMessageDialog(frame, player2 + " your time is done");
					frame.dispose();
				}
			}

		}
	};

	private final JLabel lblNewLabel_1 = new JLabel("time left :");
	private final JLabel lblNewLabel = new JLabel("time left :");
	private final JLabel lblmane1 = new JLabel(player1);
	private final JLabel lblname2 = new JLabel(player2);
	private final JButton btncastle = new JButton("");
	private final JButton btnmoves1 = new JButton("");
	private final JButton btnmoves2 = new JButton("");

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainThread mainThread = new MainThread();
					frame = new Boardof();
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
	public Boardof() {

		timer.scheduleAtFixedRate(task, 1000, 1000);
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
		// a is a random number if a is 0 the arrange of players will change
		Random rand = new Random();
		int a = rand.nextInt(2);
		if (a == 0) {
			String temp = player1;
			player1 = player2;
			player2 = temp;
		}

		JOptionPane.showMessageDialog(lblTurn, player1 + " you are white\n" + player2 + " you are black");
		// here we get time and the period from player 1
		try {
			time = 60 * Integer
					.parseInt(JOptionPane.showInputDialog(lblTurn, player1 + "please enter the hole time in min"));

		} catch (Exception e) {
			time = 5 * 60;
		} finally {
			time1 = time;
			time2 = time;
			lbltime1.setText(Integer.toString(time1));
			lbltime2.setText(Integer.toString(time2));
		}
		try {
			period = Integer.parseInt(JOptionPane.showInputDialog(lblTurn, "please enter the time in seconds"));
		} catch (Exception e) {
			period = 5;
		}

		lbltimeIfo.setText("time for each move:   " + period);
		lblTurn.setFont(new Font("Tahoma", Font.BOLD, 11));

		lblTurn.setBounds(219, -6, 122, 48);
		lblTurn.setText(player1 + " should play");
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
		lbltime1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lbltime1.setBounds(90, 65, 46, 14);

		panel.add(lbltime1);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1.setBounds(15, 53, 92, 35);

		panel.add(lblNewLabel_1);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel.setBounds(413, 59, 73, 23);

		panel.add(lblNewLabel);
		lbltime2.setFont(new Font("Tahoma", Font.BOLD, 11));
		lbltime2.setBounds(505, 65, 46, 14);

		panel.add(lbltime2);
		lblmane1.setBounds(75, 28, 61, 14);

		panel.add(lblmane1);
		lblname2.setBounds(445, 28, 46, 14);

		panel.add(lblname2);
		lbltimeIfo.setBounds(219, 36, 169, 23);

		panel.add(lbltimeIfo);

		lblcheck.setBounds(151, 11, 46, 14);
		panel.add(lblcheck);

		// when this button is clicked 20s will be added to the other player
		Image timer = new ImageIcon(this.getClass().getResource("/timer.png")).getImage();
		JButton btntime = new JButton("");
		btntime.setFocusPainted(false);
		btntime.setContentAreaFilled(false);
		btntime.setIcon(new ImageIcon(timer.getScaledInstance(30, -1, Image.SCALE_SMOOTH)));
		btntime.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (turn == 1) {
					JOptionPane.showMessageDialog(btntime, player1 + " your time is +20");
					time1 = time1 + 20;
				}
				if (turn == 2) {
					JOptionPane.showMessageDialog(btntime, player1 + " your time is +20");
					time2 = time2 + 20;
				}
			}
		});
		btntime.setBounds(167, 65, 52, 35);
		panel.add(btntime);

		// when this button is when its our turn and we want to stop the game or offer
		// case fire
		Image equal = new ImageIcon(this.getClass().getResource("/equality.png")).getImage();
		JButton btnequal = new JButton("");
		btnequal.setFocusPainted(false);
		btnequal.setContentAreaFilled(false);
		btnequal.setIcon(new ImageIcon(equal.getScaledInstance(30, -1, Image.SCALE_SMOOTH)));
		btnequal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// white player will message black player 
				if (turn == 1) {
					int exit = 2;
					try {
						exit = Integer.parseInt(JOptionPane.showInputDialog(lblTurn, "dear " + player2 + ", " + player1
								+ " offered ceasefire\n1.i will take it\n2.i want to go one"));
					} catch (Exception e2) {
						exit = 2;
					}
					if (exit == 1) {
						JOptionPane.showMessageDialog(btntime, "the game is over , thanks for playing");
						frame.dispose();
					}
				}
				// black player will message white player 
				if (turn == 2) {
					int exit = 2;
					try {
						exit = Integer.parseInt(JOptionPane.showInputDialog(lblTurn, "dear " + player1 + ", " + player2
								+ " offered ceasefire\n1.i will take it\n2.i want to go one"));
					} catch (Exception e2) {
						exit = 2;
					}
					if (exit == 1) {
						JOptionPane.showMessageDialog(btntime, "the game is over , thanks for playing");
						frame.dispose();
					}
				}
			}
		});
		btnequal.setBounds(219, 65, 46, 35);
		panel.add(btnequal);

		// this button is for when we want to make castle
		Image castle = new ImageIcon(this.getClass().getResource("/castle.png")).getImage();
		btncastle.setFocusPainted(false);
		btncastle.setContentAreaFilled(false);
		btncastle.setIcon(new ImageIcon(castle.getScaledInstance(30, -1, Image.SCALE_SMOOTH)));
		btncastle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Checkof c = new Checkof(0, 0, inside, turn);
				String ans = c.can_castle();
				int right_left = 1;
				// if we can make both castles
				if (ans.equals("11")) {
					if ((turn == 1 && king1_moved == false && rook1l_moved == false && rook1r_moved == false)
							|| (turn == 2 && king2_moved == false && rook2l_moved == false && rook2r_moved == false)) {
						try {
							right_left = Integer.parseInt(JOptionPane.showInputDialog(lblTurn,
									"do you want to do castle \n1.right  side \n2.left side"));
						} catch (Exception e) {
							right_left = 1;
						} finally {
							inside = c.do_castle(right_left);
							update();
							// turn changes :
							if (turn == 1) {
								time1 = time1 + period;
								lbltime1.setText(Integer.toString(time1));
								turn = 2;
								lblTurn.setText(player2 + " should play");
							} else if (turn == 2) {
								time2 = time2 + period;
								lbltime2.setText(Integer.toString(time2));
								turn = 1;
								lblTurn.setText(player1 + " should play");
							}
						}
					} else {
						JOptionPane.showMessageDialog(lblTurn, " you have moved your pieces befor ");
					}
					// if we can just do the right castle
				} else if (ans.equals("01")) {
					if ((turn == 1 && king1_moved == false && rook1r_moved == false)
							|| (turn == 2 && king2_moved == false && rook2r_moved == false)) {
						JOptionPane.showMessageDialog(lblTurn, " right side castle : ");
						right_left = 1;
						inside = c.do_castle(right_left);
						update();
						// turn changes :
						if (turn == 1) {
							time1 = time1 + period;
							lbltime1.setText(Integer.toString(time1));
							turn = 2;
							lblTurn.setText(player2 + " should play");
						} else if (turn == 2) {
							time2 = time2 + period;
							lbltime2.setText(Integer.toString(time2));
							turn = 1;
							lblTurn.setText(player1 + " should play");
						}
						// if we can just do the left castle
					} else {
						JOptionPane.showMessageDialog(lblTurn, " you have moved your pieces befor ");
					}
				} else if (ans.equals("10")) {
					if ((turn == 1 && king1_moved == false && rook1l_moved == false)
							|| (turn == 2 && king2_moved == false && rook2l_moved == false)) {
						JOptionPane.showMessageDialog(lblTurn, " left side castle : ");
						right_left = 2;
						inside = c.do_castle(right_left);
						update();
						// turn changes :
						if (turn == 1) {
							time1 = time1 + period;
							lbltime1.setText(Integer.toString(time1));
							turn = 2;
							lblTurn.setText(player2 + " should play");
						} else if (turn == 2) {
							time2 = time2 + period;
							lbltime2.setText(Integer.toString(time2));
							turn = 1;
							lblTurn.setText(player1 + " should play");
						}
						// if we cant make any castle
					} else {
						JOptionPane.showMessageDialog(lblTurn, " you have moved your pieces befor ");
					}

				} else {
					JOptionPane.showMessageDialog(lblTurn, " we can not make castle ");
				}

			}
		});
		btncastle.setBounds(270, 65, 52, 35);
		panel.add(btncastle);
		
		// this button shows the steps that player1 took 
		Image moves = new ImageIcon(this.getClass().getResource("/moves.png")).getImage();
		btnmoves1.setFocusPainted(false);
		btnmoves1.setContentAreaFilled(false);
		btnmoves1.setIcon(new ImageIcon(moves.getScaledInstance(30, -1, Image.SCALE_SMOOTH)));
		btnmoves1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(btntime, record_moves1);
			}

		});
		btnmoves1.setBounds(15, 11, 46, 38);
		panel.add(btnmoves1);
		// this button shows the steps that player2 took 
		btnmoves2.setFocusPainted(false);
		btnmoves2.setContentAreaFilled(false);
		btnmoves2.setIcon(new ImageIcon(moves.getScaledInstance(30, -1, Image.SCALE_SMOOTH)));
		btnmoves2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(btntime, record_moves2);
			}

		});
		btnmoves2.setBounds(505, 11, 46, 36);

		panel.add(btnmoves2);
		
		
		// this button is for when one of the players is mate and cannot move any of the picies
		Image mate = new ImageIcon(this.getClass().getResource("/mate.png")).getImage();
		JButton btnmate = new JButton();
		btnmate.setFocusPainted(false);
		btnmate.setContentAreaFilled(false);
		btnmate.setIcon(new ImageIcon(mate.getScaledInstance(50, -1, Image.SCALE_SMOOTH)));
		btnmate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// if player white cant move the pieces is mate 
				if (turn==1) {
					int answer = 0;
					try {
					answer = Integer.parseInt(JOptionPane.showInputDialog(frame, player2 + " ," + "do you accept that you are mate and you lose ?\n1.yes\n2.no"));
					}catch (Exception e) {
						answer=0;
					}
					if (answer == 1) {
						JOptionPane.showMessageDialog(lblTurn, player1 + " is the winner ");
						dispose();
						Menu.main(null);
					}
				}
				// if player black cant move the pieces is mate 
				if (turn==2) {
					int answer = 0;
					try {
					answer = Integer.parseInt(JOptionPane.showInputDialog(frame, player1 + " ," + "do you accept that you are mate and you lose ?\n1.yes\n2.no"));
					}catch (Exception e) {
						answer=0;
					}
					if (answer == 1) {
						JOptionPane.showMessageDialog(lblTurn, player2 + " is the winner ");
						dispose();
						Menu.main(null);
					}
				}
			}
		});
		btnmate.setBounds(324, 65, 46, 35);
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

				// set the colors
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
					public void mouseReleased(MouseEvent arg0) {
					}

					@Override
					public void mousePressed(MouseEvent arg0) {
					}

					@Override
					public void mouseExited(MouseEvent arg0) {
					}

					@Override
					public void mouseEntered(MouseEvent arg0) {
					}

					@Override
					public void mouseClicked(MouseEvent eventButton) {
						// we find i & j of the box witch is clicked
						int j = getI(eventButton.getComponent().getX());
						int i = getJ(eventButton.getComponent().getY());
						System.out.println(inside[i][j]);
						// if its the second click
						if (b[i][j] == true) {
							check1 = false;
							check2 = false;
							if (inside[i][j] != "0") {
								eat(inside[i][j]);
							}
							// here we move the mohre
							inside[i][j] = inside[first_i][first_j];
							inside[first_i][first_j] = "0";
							update();
							// if we move king or rook we cant make castle
							castle_condition(i, j);
							if ((turn == 1 && inside[i][j].equals("wp") && i == 0)
									|| (turn == 2 && inside[i][j].equals("bp") && i == 7)) {
								upgrade(turn, i, j);
							}
							// here we check if our move Creates check or not
							Checkof c = new Checkof(i, j, inside, turn);
							if (c.checkCh(turn, inside)) {
								if (turn == 1) {
									JOptionPane.showMessageDialog(lblTurn, player2 + " ,check");
									check2 = true;
								}
								if (turn == 2) {
									JOptionPane.showMessageDialog(lblTurn, player1 + " ,check");
									check1 = true;
								}
							}
							// we change the turns and timer
							if (turn == 1) {
								record_moves1 = inside[i][j] + " : " + frist_move + " >> " + locatin(i, j) + "\n"
										+ record_moves1;
								time1 = time1 + period;
								lbltime1.setText(Integer.toString(time1));
								turn = 2;
								lblTurn.setText(player2 + " should play");
							} else if (turn == 2) {
								record_moves2 = inside[i][j] + " : " + frist_move + " >> " + locatin(i, j) + "\n"
										+ record_moves2;
								time2 = time2 + period;
								lbltime2.setText(Integer.toString(time2));
								turn = 1;
								lblTurn.setText(player1 + " should play");
							}

						} // if its the first click
						else if ((inside[i][j].charAt(0) == 'w' && turn == 1)
								|| (inside[i][j].charAt(0) == 'b' && turn == 2)) {
							help_goner();
							first_i = i;
							first_j = j;
							frist_move = locatin(i, j);
							Checkof c = new Checkof(i, j, inside, turn);
							b = c.can_move();
							// we can not click on boxes that couse check
							b = c.canmove_checked(b, turn);
							help(b);
						}

					}

				});
				panel_1.add(B[i][j]);
			}
		}

	}

	// if pown is on line 0 or 6 it means it can't move and should be upgraded
	private void upgrade(int p, int i, int j) {
		int ans = 1;
		try {
			ans = Integer.parseInt(JOptionPane.showInputDialog(lblTurn,
					"the pown needs to be upgraded choose what you want: \n1.queen \n2.bishop\n3.knight \n4.rook"));
		} catch (Exception e) {
			ans = 1;
		}
		if (ans == 1 && p == 1)
			inside[i][j] = "wq";
		if (ans == 2 && p == 1)
			inside[i][j] = "wb";
		if (ans == 3 && p == 1)
			inside[i][j] = "wn";
		if (ans == 4 && p == 1)
			inside[i][j] = "wr";
		if (ans == 1 && p == 2)
			inside[i][j] = "bq";
		if (ans == 2 && p == 2)
			inside[i][j] = "bb";
		if (ans == 3 && p == 2)
			inside[i][j] = "bn";
		if (ans == 4 && p == 2)
			inside[i][j] = "br";
		update();

	}

	

// this method make the buttons that we can click green
	public void help(boolean[][] b) {
		for (int i = 0; i < 8; i++)
			for (int j = 0; j < 8; j++) {
				if (b[i][j] == true)
					B[i][j].setBackground(new Color(29, 131, 72));

			}
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

// this method is for when one of the the mohres is eaten and we want to update the upper labels
	public void eat(String s) {
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
			if (s.charAt(1) == 'q') {
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
			if (s.charAt(1) == 'q') {
				num_bq++;
				lblnum_bq.setText(Integer.toString(num_bq));
			}
		}
	}

// this method is for updating new moves 
	public void update() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				// set pictures
				b[i][j] = false;
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

	// it makes the [0][4] location type into f1 type
	public String locatin(int i, int j) {
		String s = "";
		if (j == 0)
			s = s + "a";
		if (j == 1)
			s = s + "b";
		if (j == 2)
			s = s + "c";
		if (j == 3)
			s = s + "d";
		if (j == 4)
			s = s + "e";
		if (j == 5)
			s = s + "f";
		if (j == 6)
			s = s + "g";
		if (j == 7)
			s = s + "h";
		if (i == 0)
			s = s + "8";
		if (i == 1)
			s = s + "7";
		if (i == 2)
			s = s + "6";
		if (i == 3)
			s = s + "5";
		if (i == 4)
			s = s + "4";
		if (i == 5)
			s = s + "3";
		if (i == 6)
			s = s + "2";
		if (i == 7)
			s = s + "1";

		return s;

	}

	//this method is to determine if we have moved rook or king
	public void castle_condition(int i, int j) {
		if (inside[i][j].equals("wk")) {
			king1_moved = true;
			System.out.println("king1_moved");
		}
		if (inside[i][j].equals("wr") && first_j == 0) {
			rook1l_moved = true;
			System.out.println("rook11_moved");
		}
		if (inside[i][j].equals("wr") && first_j == 7) {
			rook1r_moved = true;
			System.out.println("rook1r_moved");
		}
		if (inside[i][j].equals("bk"))
			king2_moved = true;
		if (inside[i][j].equals("br") && first_j == 0)
			rook2l_moved = true;
		if (inside[i][j].equals("br") && first_j == 7)
			rook2r_moved = true;
	}
}
