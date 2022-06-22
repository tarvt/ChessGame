package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JOptionPane;
import javax.swing.text.html.HTMLDocument.HTMLReader.HiddenAction;

import prf.Checkof;
import prf.Menu;

public class Server extends Thread {
	public static ServerSocket ss;
	public static ArrayList<Socket> sockets = new ArrayList<Socket>();
	public static ArrayList<String> playerOneMoves = new ArrayList<String>();
	public static ArrayList<String> playerTwoMoves = new ArrayList<String>();
	static int num_wp=0 ,num_bb=0 , num_bk =0 , num_bn =0 , num_bp =0 , num_bq = 0 , num_br=0 , num_wb=0 , num_wk=0 , num_wn=0 ;
	static boolean check1 = false;
	static boolean check2 = false;
	static int num_wq =0 , num_wr = 0;
	static int king2_moved=0, rook2l_moved = 0, rook2r_moved = 0 , king1_moved=0 , rook1l_moved = 0, rook1r_moved = 0;
	public static boolean turn = false;
	
	public static void main(String[] args) throws IOException {
		ss = new ServerSocket(9994);
		Menu.main(null);
		System.out.println("SERVER STARTED");
		try {
			while (true) {
				new handle(ss.accept()).start();
				
			}
		} finally {
			ss.close();
		}
	}
//bazikon aval sefid ast
	private static class handle extends Thread {
		private Socket socket;
		private BufferedReader in;
		private PrintWriter out;
		public String[][] inside = { { "br", "bn", "bb", "bq", "bk", "bb", "bn", "br" },
			{ "bp", "bp", "bp", "bp", "bp", "bp", "bp", "bp" }, { "0", "0", "0", "0", "0", "0", "0", "0" },
			{ "0", "0", "0", "0", "0", "0", "0", "0" }, { "0", "0", "0", "0", "0", "0", "0", "0" },
			{ "0", "0", "0", "0", "0", "0", "0", "0" }, { "wp", "wp", "wp", "wp", "wp", "wp", "wp", "wp" },
			{ "wr", "wn", "wb", "wq", "wk", "wb", "wn", "wr" } };
		int state = 0 ;
		int first_i ,  first_j;
		
		public handle(Socket socket) throws IOException {
			this.socket = socket;
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);
			if (Server.sockets.size() <= 1) {
				System.out.println("Client Connected !");
				Server.sockets.add(socket);
				// we start the game when there is 2 players online 
				if (Server.sockets.size() == 2) {
					
					play();
				}
			} else {
				out.println("SERVER-BUSY");
				out.flush();
				System.out.println("Server is busy !");
			}
		}

		public void run() {
			try {
				while (true) {
					String command = in.readLine();
					System.out.println("Server : " + command);
					// when we click on any button:
					if (command.startsWith("BTN-CLICKED")) {
						String address = command.substring(12, 15);
						String inside_string = command.substring(15 , command.length()-1 ) ;
						inside = chang_toarray(inside_string);
						int i = address.charAt(0) - '0';
						int j = address.charAt(2) - '0';
						//System.out.println(inside[i][j]);
						int playerNumber = command.charAt(command.length() - 1) - '0';
						Check c = new Check(i , j , inside , playerNumber);
						//here we check if the fist button is clicked right
						if ((playerNumber==1 && inside[i][j].startsWith("w"))||(playerNumber==2 && inside[i][j].startsWith("b"))) {
							//we send them a bunch of things he can click on 
							
							out.println("c" + c.canmove_checked(change_toB(c.can_move()), playerNumber));
							state= 1;
							first_i = i;
							first_j =j;
							out.flush();
							
						}
						else if (state == 1 ) {
							state = 0 ;
							check1 = false;
							check2 = false;
							if (inside[i][j]!="0") {
								for (Socket sc : sockets) {
									PrintWriter outSc = new PrintWriter(sc.getOutputStream(), true);
									outSc.println("eat"+ inside[i][j]);
									eat(inside[i][j]);
								}
							}
							inside[i][j]=inside[first_i][first_j];
							inside[first_i][first_j]="0";
							out.println("move" + change_tostring());
							out.flush();
							// if we move king or rook we cant make castle 
							castle_condition(i,j);
							//if a player is checked by the other player we'll understand
							if (c.checkCh(playerNumber, inside)) {
								if (playerNumber == 1) {
									PrintWriter outSc = new PrintWriter(sockets.get(1).getOutputStream(), true);
									outSc.println("check");
									outSc.flush();
									check2 = true;
								}
								if (playerNumber == 2) {
									PrintWriter outSc = new PrintWriter(sockets.get(0).getOutputStream(), true);
									outSc.println("check");
									outSc.flush();
									check1 = true;
								}
							}
							for (Socket sc : sockets) {
								PrintWriter outSc = new PrintWriter(sc.getOutputStream(), true);
								outSc.println("up"+ change_tostring());
							}
							// we check the conditions for upgrade  
							if ((playerNumber == 1 && inside[i][j].equals("wp") && i==0 )||(playerNumber == 2 && inside[i][j].equals("bp") && i==7 )) {
								out.println("grade" + i + ""+j );
								out.flush();
							}
							
						}

						// if pown is on line 0 or 6 it means it can't move and should be upgraded
					}else if  (command.startsWith("grade")) {
						String ans = command.substring(7, 8);
						String p = command.substring(8, 9);
						int i = command.charAt(5) - '0';
						int j = command.charAt(6) - '0';
						if (ans.equals("1") && p.equals("1"))
							inside[i][j]="wq";
						if (ans.equals("2") && p.equals("1") )
							inside[i][j]="wb";
						if (ans.equals("3") && p.equals("1") )
							inside[i][j]="wn";
						if (ans.equals("4") && p.equals("1") )
							inside[i][j]="wr";
						if (ans.equals("1") && p.equals("2") )
							inside[i][j]="bq";
						if (ans.equals("2") && p.equals("2"))
							inside[i][j]="bb";
						if (ans.equals("3") && p.equals("2") )
							inside[i][j]="bn";
						if (ans.equals("4") && p.equals("2"))
							inside[i][j]="br";
						
						for (Socket sc : sockets) {
							PrintWriter outSc = new PrintWriter(sc.getOutputStream(), true);
							outSc.println("up"+ change_tostring());
						}
					}// we get time and period and from the first player and send it to all
					else if  (command.startsWith("time")) {
						String s = command.substring(4, command.length());
						for (Socket sc : sockets) {
							PrintWriter outSc = new PrintWriter(sc.getOutputStream(), true);
							outSc.println(command);
						}
					}else if  (command.startsWith("period")) {
						String s = command.substring(6, command.length());
						for (Socket sc : sockets) {
							PrintWriter outSc = new PrintWriter(sc.getOutputStream(), true);
							outSc.println(command);
						}
						// when its the end of a turn and the label needs to be change into "your turn "
					}else if  (command.startsWith("lbl")) {
						for (Socket sc : sockets) {
							PrintWriter outSc = new PrintWriter(sc.getOutputStream(), true);
							outSc.println(command);
						}
					}
					// we get this when ones turn is over
					else if  (command.startsWith("turn")) {
						for (Socket sc : sockets) {
							PrintWriter outSc = new PrintWriter(sc.getOutputStream(), true);
							outSc.println("TURN--");
						}
					}
					// the exit message
					else if  (command.startsWith("end")) {
						for (Socket sc : sockets) {
							PrintWriter outSc = new PrintWriter(sc.getOutputStream(), true);
							outSc.println("end");
							outSc.flush();
						}
					}

					// if someone clicked equal it is case fire 
					else if  (command.startsWith("equal")) {
						int num = Integer.parseInt(command.substring(5, 6));
						if (num == 1) {
							num=1;
						}else
							num =0;
						
						PrintWriter outSc = new PrintWriter(sockets.get(num).getOutputStream(), true);
						outSc.println("equality");
						outSc.flush();
					}
					//// this line is for when one of the players is mate 
					else if (command.startsWith("mate")) {
						int num = Integer.parseInt(command.substring(4, 5));
						System.out.println(num);
						if (num == 1) {
							num=1;
						}else
							num =0;
						PrintWriter outSc = new PrintWriter(sockets.get(num).getOutputStream(), true);
						outSc.println("maate");
						outSc.flush();
					}// if the agreement mate is true then we send win to the winner and game is dne 
					else if (command.startsWith("lost")) {
						int num = Integer.parseInt(command.substring(4, 5));
						if (num == 1) {
							num=1;
						}else
							num =0;
						PrintWriter outSc = new PrintWriter(sockets.get(num).getOutputStream(), true);
						outSc.println("win");
						outSc.flush();
					}
					// if someone clicked timer to give the other player +20 time 
					else if  (command.startsWith("tm+")) {
						int num = Integer.parseInt(command.substring(3, 4));
						if (num == 1) {
							num=1;
						}else
							num =0;
						
						PrintWriter outSc = new PrintWriter(sockets.get(num).getOutputStream(), true);
						outSc.println("tm");
						outSc.flush();
					}// if someone clicked castle 
					else if  (command.startsWith("permcastle")){
						int player = Integer.parseInt(command.substring(10, 11));
						String inside_string = command.substring(11,command.length());
						inside = chang_toarray(inside_string);
						Check c = new Check(0, 0, inside, player) ; 
						System.out.println(c.can_castle());
						if (player == 1 )
						out.println("tastle" + c.can_castle() + king1_moved +""+ rook1l_moved +""+rook1r_moved);
						if (player == 2)
							out.println("tastle" + c.can_castle() + king2_moved +""+ rook2l_moved +""+rook2r_moved);
						System.out.println(c.can_castle());
						out.flush();
					}// now we should make the castle :
					else if  (command.startsWith("docsl")){
						int player = Integer.parseInt(command.substring(6, 7));
						int right_left = Integer.parseInt(command.substring(5, 6));
						Check c = new Check(0, 0, inside, player) ;
						inside= c.do_castle(right_left);
						out.println("move" + change_tostring());
						out.flush();
						for (Socket sc : sockets) {
							PrintWriter outSc = new PrintWriter(sc.getOutputStream(), true);
							outSc.println("up"+ change_tostring());
							outSc.flush();
						}
					}
					
					}
			} catch (Exception e) {

			}

		}
		// if we move king or rook we cant make castle 
		public void castle_condition(int i , int j) {
			if (inside[i][j].equals("wk"))
				king1_moved=1;
			if (inside[i][j].equals("wr") && first_j==0)
				rook1l_moved=1;
			if (inside[i][j].equals("wr")&& first_j==7)
				rook1r_moved=1;
			if (inside[i][j].equals("bk"))
				king2_moved=1;
			if (inside[i][j].equals("br") && first_j==0)
				rook2l_moved=1;
			if (inside[i][j].equals("br")&& first_j==7)
				rook2r_moved=1;
		}
		// here we recgnize 
		public void eat(String s) {
			if (s.charAt(0)=='w') {
				if (s.charAt(1)=='p')
					num_wp++;
				if (s.charAt(1)=='n')
					num_wn++;
				if (s.charAt(1)=='r')
					num_wr++;
				if (s.charAt(1)=='p')
					num_wb++;
				if (s.charAt(1)=='n')
					num_wq++;
			}
			if (s.charAt(0)=='b') {
				if (s.charAt(1)=='p')
					num_bp++;
				if (s.charAt(1)=='n')
					num_bn++;
				if (s.charAt(1)=='r')
					num_br++;
				if (s.charAt(1)=='p')
					num_bb++;
				if (s.charAt(1)=='n')
					num_bq++;
			}
		}
		// we take string c and change it to a string array of inside
		public String[][] chang_toarray(String s){
			int count =0 ;
			String[][] ins = new String[8][8];
			for (int x =0 ; x<8 ; x++ )
				for ( int y =0 ; y < 8 ; y++) {
					if (s.charAt(count)=='0') {
					  ins[x][y]="0";
					  count++;
					}
					else {
						ins[x][y]="" +s.charAt(count) + s.charAt(count+1);
						count = count+2 ;
					}
				}
			return ins;
			
			
			
		}
		// we take string c and change it to a boolean array of things we can click on 
		public boolean[][] change_toB(String c) {
			boolean[][] b = new boolean[8][8];
			int count = 0;
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
		// we change inside to a string
		public String change_tostring() {
			String s = "";
			for (int x = 0; x < 8; x++)
				for (int y = 0; y < 8; y++)
					s = s + inside[x][y];

			return s;
		}
	}
	
	public static void play() {
		// a is a random number 0 or 1 that choose which player will be first which will be second
		
		int counter = 1 ;
		
		for (Socket sc : sockets) {
			PrintWriter outSc;
			try {
				
				outSc = new PrintWriter(sc.getOutputStream(), true);
				outSc.println("START" + " " + Integer.toString(counter));
				outSc.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
				counter++;
			
		}
	}
	
}
