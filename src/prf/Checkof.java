package prf;

import java.util.ArrayList;

public class Checkof {
	String[][] inside;
	int i;
	int j;
	int player;
	boolean[][] click = new boolean[8][8];
	

	public Checkof(int i, int j, String[][] inside, int turn) {
		this.i = i;
		this.j = j;
		this.inside = inside;
		this.player = turn;

	}

	public boolean[][] can_move() {
		// white player
		char state = inside[i][j].charAt(1);

		for (int x = 0; x < 8; x++)
			for (int y = 0; y < 8; y++)
				click[x][y] = false;

		if (state == 'r') {
			rook_move();
		}
		if (state == 'n') {
			knight_move();
		}
		if (state == 'b') {
			bishop_move();
		}
		if (state == 'q') {
			queen_move();
		}
		if (state == 'k') {
			king_move();
		}
		if (state == 'p') {
			pown_move();
		}
		for (int x = 0; x < 8; x++)
			for (int y = 0; y < 8; y++)
				if (inside[x][y].equals("wk") || inside[x][y].equals("bk"))
					click[x][y] = false;

		return click;

	}
	public boolean[][] canmove_checked(boolean[][] b , int p) {
		if (p==1)
			p=2;
		else p=1;
		String[][] temp = new String[8][8];
		for (int x =0  ; x<8 ; x++)
			for (int y = 0 ; y<8 ; y++) { 
				temp[x][y]=inside[x][y];
			}
		for (int x =0  ; x<8 ; x++)
			for (int y = 0 ; y<8 ; y++) 
				if (b[x][y]==true) {
					String t = temp[x][y];
					temp[x][y]=temp[i][j];
					temp[i][j]="0";
					for (int a =0  ; a<8 ;a++) {
						for (int q = 0 ; q<8 ; q++) 
							System.out.print(temp[a][q]);
						System.out.println();
					}
					System.out.println("------------------------------");
					if (checkCh(p, temp)==true) {
						b[x][y]=false;
					}
					temp[i][j]=temp[x][y];
					temp[x][y]=t;
					
					
				}
		return b;
		
	}
	public String can_castle() {
		String cc = "";
		if (player == 1) {
			if (inside[7][0].equals("wr") && inside[7][1].equals("0") && inside[7][2].equals("0") && inside[7][3].equals("0") && inside[7][4].equals("wk"))
				cc= cc+ "1";
			else
				cc = cc+"0";		
			if (inside[7][4].equals("wk") && inside[7][5].equals("0") && inside[7][6].equals("0") && inside[7][7].equals("wr"))
				cc= cc+ "1";
			else
				cc = cc+"0";
			
		}
		if (player == 2) {
			if (inside[0][0].equals("br") && inside[0][1].equals("0") && inside[0][2].equals("0") && inside[0][3].equals("0") && inside[0][4].equals("bk"))
				cc= cc+ "1";
			else
				cc = cc+"0";		
			if (inside[0][4].equals("bk") && inside[0][5].equals("0") && inside[0][6].equals("0") && inside[0][7].equals("br"))
				cc= cc+ "1";
			else
				cc = cc+"0";
			
		}
		System.out.println(" van casle " +cc);
		return cc;
		
	}
	
	public String[][]  do_castle(int right_left) {
		if (player==1) {
			if (right_left == 2) {
				inside[7][0]="0";
				inside[7][4]="0";
				inside[7][3]="wr";
				inside[7][2]="wk";
			}
			if (right_left == 1) {
				inside[7][7]="0";
				inside[7][4]="0";
				inside[7][5]="wr";
				inside[7][6]="wk";
			}
		}
		if (player==2) {
			if (right_left == 2) {
				inside[0][0]="0";
				inside[0][4]="0";
				inside[0][3]="br";
				inside[0][2]="bk";
			}
			if (right_left == 1) {
				inside[0][7]="0";
				inside[0][4]="0";
				inside[0][5]="br";
				inside[0][6]="bk";
			}
		}
		return inside;
	}
	
	public boolean checkCh(int p, String[][] in) {
		for (int x = 0; x < 8; x++)
			for (int y = 0; y < 8; y++) {
				if (!in[x][y].equals("0")) {
					char state = in[x][y].charAt(1);
					if (state == 'r') {
						if (check_rook(x, y, p, in))
							return true;
					}
					if (state == 'n') {
						if (check_knight(x, y, p, in)) {
							return true;
						}
					}
					if (state == 'b') {
						if (check_bishop(x, y, p, in)) {
							System.out.println("checked bishop");
							return true;
						}
					}
					if (state == 'q') {
						if (check_queen(x, y, p, in)) {
							System.out.println("checked queen");
							return true;
						}
					}
					//*/
					if (state == 'p') {
						if (check_pown(x, y, p, in))
							return true;
					}
				}
			}
		return false;

	}

	

// defines places rook can move
	public void rook_move() {

		System.out.println("clicked rook");
		for (int x = i + 1; x < 8; x++) {
			if (inside[x][j] != "0") {
				if ((player == 1 && inside[x][j].charAt(0) == 'b') || player == 2 && inside[x][j].charAt(0) == 'w')
					click[x][j] = true;
				break;
			} else
				click[x][j] = true;
		}
		for (int x = i - 1; x >= 0; x--) {
			if (inside[x][j] != "0") {
				if ((player == 1 && inside[x][j].charAt(0) == 'b') || player == 2 && inside[x][j].charAt(0) == 'w')
					click[x][j] = true;
				break;
			} else
				click[x][j] = true;
		}
		for (int y = j - 1; y >= 0; y--) {
			if (inside[i][y] != "0") {
				if ((player == 1 && inside[i][y].charAt(0) == 'b') || player == 2 && inside[i][y].charAt(0) == 'w')
					click[i][y] = true;
				break;
			} else
				click[i][y] = true;
		}
		for (int y = j + 1; y <8; y++) {
			if (inside[i][y] != "0") {
				if ((player == 1 && inside[i][y].charAt(0) == 'b') || player == 2 && inside[i][y].charAt(0) == 'w')
					click[i][y] = true;
				break;
			} else
				click[i][y] = true;
		}

	}

	// defines places pown can move
	public void pown_move() {
		System.out.println("clicked pown");
		// white
		if (player == 1) {
			// moves
			if (i > 0)
				if (inside[i - 1][j] == "0") {
					System.out.println("print shart");
					click[i - 1][j] = true;
					if (i == 6 && inside[i - 2][j] == "0")
						click[i - 2][j] = true;
				}
			// treads
			if (j > 0 && i > 0) {
				if (inside[i - 1][j - 1].charAt(0) == 'b')
					click[i - 1][j - 1] = true;
			}
			if (j < 7 && i > 0)
				if (inside[i - 1][j + 1].charAt(0) == 'b')
					click[i - 1][j + 1] = true;

		}
		// black
		if (player == 2) {
			// moves
			if (i < 7)
				if (inside[i + 1][j] == "0") {
					click[i + 1][j] = true;
					if (i == 1 && inside[i + 2][j] == "0")
						click[i + 2][j] = true;
				}
			// treads
			if (i < 7 && j < 7)
				if (inside[i + 1][j + 1].charAt(0) == 'w')
					click[i + 1][j + 1] = true;
			if (j > 0 && i < 7) {
				if (inside[i + 1][j - 1].charAt(0) == 'w')
					click[i + 1][j - 1] = true;
			}

		}
	}

	// defines places knight can move
	public void knight_move() {
		System.out.println("clicked knight");
		if (player == 1) {
			if (j > 0 && i > 1)
				if (inside[i - 2][j - 1].charAt(0) != 'w')
					click[i - 2][j - 1] = true;
			if (j < 7 && i > 1) {
				if (inside[i - 2][j + 1].charAt(0) != 'w')
					click[i - 2][j + 1] = true;
			}
			if (j > 0 && i < 6)
				if (inside[i + 2][j - 1].charAt(0) != 'w')
					click[i + 2][j - 1] = true;
			if (j < 7 && i < 6) {
				if (inside[i + 2][j + 1].charAt(0) != 'w')
					click[i + 2][j + 1] = true;
			}
			if (j > 1 && i > 0)
				if (inside[i - 1][j - 2].charAt(0) != 'w')
					click[i - 1][j - 2] = true;
			if (i < 7 && j > 1) {
				if (inside[i + 1][j - 2].charAt(0) != 'w')
					click[i + 1][j - 2] = true;
			}
			if (i > 0 && j < 6)
				if (inside[i - 1][j + 2].charAt(0) != 'w')
					click[i - 1][j + 2] = true;
			if (i < 7 && j < 6) {
				if (inside[i + 1][j + 2].charAt(0) != 'w')
					click[i + 1][j + 2] = true;
			}

		}
		if (player == 2) {
			if (j > 0 && i > 1)
				if (inside[i - 2][j - 1].charAt(0) != 'b')
					click[i - 2][j - 1] = true;
			if (j < 7 && i > 1) {
				if (inside[i - 2][j + 1].charAt(0) != 'b')
					click[i - 2][j + 1] = true;
			}
			if (j > 0 && i < 6)
				if (inside[i + 2][j - 1].charAt(0) != 'b')
					click[i + 2][j - 1] = true;
			if (j < 7 && i < 6) {
				if (inside[i + 2][j + 1].charAt(0) != 'b')
					click[i + 2][j + 1] = true;
			}
			if (j > 1 && i > 0)
				if (inside[i - 1][j - 2].charAt(0) != 'b')
					click[i - 1][j - 2] = true;
			if (i < 7 && j > 1) {
				if (inside[i + 1][j - 2].charAt(0) != 'b')
					click[i + 1][j - 2] = true;
			}
			if (i > 0 && j < 6)
				if (inside[i - 1][j + 2].charAt(0) != 'b')
					click[i - 1][j + 2] = true;
			if (i < 7 && j < 6) {
				if (inside[i + 1][j + 2].charAt(0) != 'b')
					click[i + 1][j + 2] = true;
			}

		}
	}

	// defines places bishop can move
	public void bishop_move() {
		System.out.println("clicked bishop");
		if (player == 1) {
			for (int x = i - 1, y = j - 1; y >= 0 && x >= 0; x--, y--) {
				if (inside[x][y].charAt(0) != 'w') {
					click[x][y] = true;
					if (inside[x][y] != "0")
						break;
				} else
					break;
			}
			for (int x = i - 1, y = j + 1; y < 8 && x >= 0; x--, y++) {
				if (inside[x][y].charAt(0) != 'w') {
					click[x][y] = true;
					if (inside[x][y] != "0")
						break;
				} else
					break;
			}
			for (int x = i + 1, y = j + 1; y < 8 && x < 8; x++, y++) {
				if (inside[x][y].charAt(0) != 'w') {
					click[x][y] = true;
					if (inside[x][y] != "0")
						break;
				} else
					break;
			}
			for (int x = i + 1, y = j - 1; y >= 0 && x < 8; x++, y--) {
				if (inside[x][y].charAt(0) != 'w') {
					click[x][y] = true;
					if (inside[x][y] != "0")
						break;
				} else
					break;
			}
		}
		if (player == 2) {
			for (int x = i - 1, y = j - 1; y >= 0 && x >= 0; x--, y--) {
				if (inside[x][y].charAt(0) != 'b') {
					click[x][y] = true;
					if (inside[x][y] != "0")
						break;
				} else
					break;
			}
			for (int x = i - 1, y = j + 1; y < 8 && x >= 0; x--, y++) {
				if (inside[x][y].charAt(0) != 'b') {
					click[x][y] = true;
					if (inside[x][y] != "0")
						break;
				} else
					break;
			}
			for (int x = i + 1, y = j + 1; y < 8 && x < 8; x++, y++) {
				if (inside[x][y].charAt(0) != 'b') {
					click[x][y] = true;
					if (inside[x][y] != "0")
						break;
				} else
					break;
			}
			for (int x = i + 1, y = j - 1; y >= 0 && x < 8; x++, y--) {
				if (inside[x][y].charAt(0) != 'b') {
					click[x][y] = true;
					if (inside[x][y] != "0")
						break;
				} else
					break;
			}
		}

	}

	// defines places queen can move
	public void queen_move() {
		System.out.println("clicked queen");
		///// bishop
		bishop_move();
		////// rook
		rook_move();
	}

	// defines places king can move
	public void king_move() {
		if (player == 1) {
			if (i > 0 && j > 0) {
				if (inside[i - 1][j - 1].charAt(0) != 'w')
					click[i - 1][j - 1] = true;
			}
			if (j > 0) {
				if (inside[i][j - 1].charAt(0) != 'w')
					click[i][j - 1] = true;
			}
			if (i > 0) {
				if (inside[i - 1][j].charAt(0) != 'w')
					click[i - 1][j] = true;
			}
			if (j > 0 && i < 7) {
				if (inside[i + 1][j - 1].charAt(0) != 'w')
					click[i + 1][j - 1] = true;
			}
			if (i > 0 && j < 7) {
				if (inside[i - 1][j + 1].charAt(0) != 'w')
					click[i - 1][j + 1] = true;
			}
			if (i < 7 && j < 7) {
				if (inside[i + 1][j + 1].charAt(0) != 'w')
					click[i + 1][j + 1] = true;
			}
			if (j < 7) {
				if (inside[i][j + 1].charAt(0) != 'w')
					click[i][j + 1] = true;
			}
			if (i < 7) {
				if (inside[i + 1][j].charAt(0) != 'w')
					click[i + 1][j] = true;
			}
		}
		if (player == 2) {
			if (i > 0 && j > 0) {
				if (inside[i - 1][j - 1].charAt(0) != 'b')
					click[i - 1][j - 1] = true;
			}
			if (j > 0) {
				if (inside[i][j - 1].charAt(0) != 'b')
					click[i][j - 1] = true;
			}
			if (i > 0) {
				if (inside[i - 1][j].charAt(0) != 'b')
					click[i - 1][j] = true;
			}
			if (j > 0 && i < 7) {
				if (inside[i + 1][j - 1].charAt(0) != 'b')
					click[i + 1][j - 1] = true;
			}
			if (i > 0 && j < 7) {
				if (inside[i - 1][j + 1].charAt(0) != 'b')
					click[i - 1][j + 1] = true;
			}
			if (i < 7 && j < 7) {
				if (inside[i + 1][j + 1].charAt(0) != 'b')
					click[i + 1][j + 1] = true;
			}
			if (j < 7) {
				if (inside[i][j + 1].charAt(0) != 'b')
					click[i][j + 1] = true;
			}
			if (i < 7) {
				if (inside[i + 1][j].charAt(0) != 'b')
					click[i + 1][j] = true;
			}
		}
	}

	public boolean check_pown(int x, int y, int p, String[][] in) {
		if (p == 1 && in[x][y].charAt(0) != 'b') {
			if (y > 0 && x > 0) {
				if (in[x - 1][y - 1].equals("bk"))
					return true;
			}
			if (x > 0 && y < 7)
				if (in[x - 1][y + 1].equals("bk"))
					return true;
		}
		if (p == 2 && in[x][y].charAt(0) != 'w') {
			if (y < 7 && x < 7) {
				if (in[x + 1][y + 1].equals("wk"))
					return true;
			}
			if (x < 7 && y > 0)
				if (in[x + 1][y - 1].equals("wk"))
					return true;
		}
		return false;
	}

	public boolean check_rook(int m, int n, int p, String[][] in) {
		char pl = in[m][n].charAt(0);
		for (int x = m + 1; x < 8; x++) {
			if (in[x][n] != "0") {
				if ((pl == 'w' && in[x][n].equals("bk")&& p==1) || pl == 'b' && in[x][n].equals("wk")&& p==2)
					return true;
				else
					break;
			}

		}
		for (int x = m - 1; x >= 0; x--) {
			if (in[x][n] != "0") {
				if ((pl == 'w' && in[x][n].equals("bk")&& p==1) || pl == 'b' && in[x][n].equals("wk")&& p==2)
					return true;
				else
					break;
			}
		}
		for (int y = n - 1; y >= 0; y--) {
			if (in[m][y] != "0") {
				if ((pl == 'w' && in[m][y].equals("bk")&& p==1) || pl == 'b' && in[m][y].equals("wk")&& p==2)
					return true;
				else
					break;
			}
		}
		for (int y = n + 1; y < 8; y++) {
			if (in[m][y] != "0") {
				if ((pl == 'w' && in[m][y].equals("bk") && p==1) || pl == 'b' && in[m][y].equals("wk")&& p==2)
					return true;
				else
					break;
			}
		}
		return false;

	}

	public boolean check_knight(int x, int y, int p, String[][] in) {
		if (p == 1 && in[x][y].charAt(0)=='w') {
			if (y > 0 && x > 1)
				if (in[x - 2][y - 1].equals("bk"))
					return true;
			if (y < 7 && x > 1) {
				if (in[x - 2][y + 1].equals("bk"))
					return true;
			}
			if (y > 0 && x < 6)
				if (in[x + 2][y - 1].equals("bk"))
					return true;
			if (y < 7 && x < 6) {
				if (in[x + 2][y + 1].equals("bk"))
					return true;
			}
			if (y > 1 && x > 0)
				if (in[x - 1][y - 2].equals("bk"))
					return true;
			if (x < 7 && y > 1) {
				if (in[x + 1][y - 2].equals("bk"))
					return true;
			}
			if (x > 0 && y < 6)
				if (in[x - 1][y + 2].equals("bk"))
					return true;
			if (x < 7 && y < 6) {
				if (in[x + 1][y + 2].equals("bk"))
					return true;
			}

		}
		if (p == 2&& in[x][y].charAt(0)=='b') {
			if (y > 0 && x > 1)
				if (in[x - 2][y - 1].equals("wk"))
					return true;
			if (y < 7 && x > 1) {
				if (in[x - 2][y + 1].equals("wk"))
					return true;
			}
			if (y > 0 && x < 6)
				if (in[x + 2][y - 1].equals("wk"))
					return true;
			if (y < 7 && x < 6) {
				if (in[x + 2][y + 1].equals("wk"))
					return true;
			}
			if (y > 1 && x > 0)
				if (in[x - 1][y - 2].equals("wk"))
					return true;
			if (x < 7 && y > 1) {
				if (in[x + 1][y - 2].equals("wk"))
					return true;
			}
			if (x > 0 && y < 6)
				if (in[x - 1][y + 2].equals("wk"))
					return true;
			if (x < 7 && y < 6) {
				if (in[x + 1][y + 2].equals("wk"))
					return true;
			}

		}
		return false;

	}

	public boolean check_bishop(int m, int n, int p, String[][] in) {

		if (p == 1 && in[m][n].charAt(0) == 'w') {
			for (int x = m - 1, y = n - 1; y >= 0 && x >= 0; x--, y--) {
				if (in[x][y].charAt(0) != 'w') {
					if (in[x][y].charAt(0) == 'b') {
						if (in[x][y].equals("bk"))
							return true;
						else
							break;
					}

				} else
					break;
			}
			for (int x = m - 1, y = n + 1; y < 8 && x >= 0; x--, y++) {
				if (in[x][y].charAt(0) != 'w') {
					if (in[x][y].charAt(0) == 'b') {
						if (in[x][y].equals("bk"))
							return true;
						else
							break;
					}

				} else
					break;
			}
			for (int x = m + 1, y = n + 1; y < 8 && x < 8; x++, y++) {
				if (in[x][y].charAt(0) != 'w') {
					if (in[x][y].charAt(0) == 'b') {
						if (in[x][y].equals("bk"))
							return true;
						else
							break;
					}

				} else
					break;
			}
			for (int x = m + 1, y = n - 1; y >= 0 && x < 8; x++, y--) {
				if (in[x][y].charAt(0) != 'w') {
					if (in[x][y].charAt(0) == 'b') {
						if (in[x][y].equals("bk"))
							return true;
						else
							break;
					}

				} else
					break;
			}
		}
		if (p == 2 && in[m][n].charAt(0) == 'b') {
			for (int x = m - 1, y = n - 1; y >= 0 && x >= 0; x--, y--) {
				if (in[x][y].charAt(0) != 'b') {
					if (in[x][y].charAt(0) == 'w') {
						if (in[x][y].equals("wk"))
							return true;
						else
							break;
					}

				} else
					break;
			}
			for (int x = m - 1, y = n + 1; y < 8 && x >= 0; x--, y++) {
				if (in[x][y].charAt(0) != 'b') {
					if (in[x][y].charAt(0) == 'w') {
						if (in[x][y].equals("wk"))
							return true;
						else
							break;
					}

				} else
					break;
			}
			for (int x = m + 1, y = n + 1; y < 8 && x < 8; x++, y++) {
				if (in[x][y].charAt(0) != 'b') {
					if (in[x][y].charAt(0) == 'w') {
						if (in[x][y].equals("wk"))
							return true;
						else
							break;
					}

				} else
					break;
			}
			for (int x = m + 1, y = n - 1; y >= 0 && x < 8; x++, y--) {
				if (in[x][y].charAt(0) != 'b') {
					if (in[x][y].charAt(0) == 'w') {
						if (in[x][y].equals("wk"))
							return true;
						else
							break;
					}

				} else
					break;
			}
		}
		return false;

	}

	public boolean check_queen(int x, int y, int p, String[][] in) {
		return (check_bishop(x, y, p, in)||check_rook(x, y, p, in));

	}
}
