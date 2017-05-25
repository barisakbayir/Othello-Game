import java.util.ArrayList;
import java.util.Scanner;


public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int game_counter = 0;
		int black_win_counter = 0;
		int white_win_counter = 0;
		int draw_win_counter = 0;
		
		while(game_counter != 10){
			
			Scanner scanner = new Scanner(System.in);
			
			String [][] game_board = new String[8][8];
			String [][] white_board = new String[8][8];
			String [][] black_board = new String[8][8];
	
			ArrayList<Integer> row_column = new ArrayList<Integer>();
			
			int exit = 1;
			int black_exit = 1;
			int white_exit = 1;	
			int white_flag = 0;
			int black_flag = 0;
			int row = 0;
			int column = 0;
			int white_score = 0;
			int black_score = 0;
			int move_counterX = 0;
			int move_counterY = 0;
			String result = "";
			
			Board board = new Board();
			game_board = board.createBoard();
			
			AlphaBetaPruning x = new AlphaBetaPruning();
			BlackStrategy b = new BlackStrategy();
			
			while (exit == 1){
				MyTreeNode<String> root = new MyTreeNode<>("");
				//board.printBoard(game_board);
				black_board = board.checkPossibleBlackMoves(game_board);
				board.printBoard(black_board);
				black_exit = 1;
				white_score = 0;
				black_score = 0;
				black_flag = 0;
				for (int i = 0; i < 8; i++){
					for (int k = 0; k < 8; k++){
						if (black_board[i][k] == "*")
							black_flag ++;
						if (game_board[i][k] == "O")
							white_score ++;
						if (game_board[i][k] == "X")
							black_score ++;	
					}
				}
				System.out.println("White's score : " + white_score);
				System.out.println("Black's score : " + black_score);
				if (black_flag == 0 /*&& white_flag != 0*/)
					System.out.println("There is no possible movement for black!!!");
				else {
					while (black_exit == 1){
						System.out.println("Black's turn!");
						
						row_column = b.strategyOfBlackMove(black_board, game_board);
	
						row = row_column.get(0);
						column = row_column.get(1);
						
						//System.out.println("Enter row");
						//row = scanner.nextInt();
						//System.out.println("Enter column");
						//column = scanner.nextInt();
						
						if (row >= 0 && column >= 0 && row <= 7 && column <= 7 && black_board[row][column] == "*"){
							game_board[row][column] = "X";
							black_exit = 0;
							move_counterX ++;
							System.out.println("movement counter X:" + move_counterX);
							root.setData(String.valueOf(row) + "," + String.valueOf(column));
						}
						else
							System.out.println("Wrong place!!!");
					}
					board.makeNodesBlack(game_board, row, column);
				}
	
				//board.printBoard(game_board);
				white_board = board.checkPossibleWhiteMoves(game_board);
	
				System.out.println("white_board possible : ");
				board.printBoard(white_board);
	
	
				white_exit = 1;
				white_score = 0;
				black_score = 0;
				white_flag = 0;
				for (int i = 0; i < 8; i++){
					for (int k = 0; k < 8; k++){
						if (white_board[i][k] == "*")
							white_flag ++;
						if (game_board[i][k] == "O")
							white_score ++;
						if (game_board[i][k] == "X")
							black_score ++;	
					}
				}
				System.out.println("White's score : " + white_score);
				System.out.println("Black's score : " + black_score);
				if (white_flag == 0)
					System.out.println("There is no possible movement for white!!!");
				else{
					
					result = x.createAlphaBetaTree(game_board, white_board, black_board, root);				// alpha beta
	
					row = Character.getNumericValue(result.charAt(0));
					column = Character.getNumericValue(result.charAt(2));
					
					while (white_exit == 1){
						System.out.println("White's turn!");
						/*System.out.println("Enter row");
						row = scanner.nextInt();
						System.out.println("Enter column");
						column = scanner.nextInt();*/
		
						if (row >= 0 && column >= 0 && row <= 7 && column <= 7 && white_board[row][column] == "*"){
							game_board[row][column] = "O";
							white_exit = 0;
							move_counterY ++;
							System.out.println("movement counter Y:" + move_counterY);
						}
						else
							System.out.println("Wrong place!!!");
					}
					board.makeNodesWhite(game_board, row, column);
				}
				scanner = new Scanner(System.in);
				if (black_flag == 0 && white_flag == 0){
					
					if (white_score > black_score){
						System.out.println("White won!!!");
						System.out.println("White score: " + white_score);
						System.out.println("Black score: " + black_score);
						white_win_counter ++;
					}
					else if (black_score > white_score){
						System.out.println("Black won!!!");
						System.out.println("White score: " + white_score);
						System.out.println("Black score: " + black_score);
						black_win_counter ++;
					}
					else{
						System.out.println("Draw !!!");
						draw_win_counter ++;
					}
					
					exit = 0;
				}
			}
			scanner.close();
	
			game_counter ++;
		}

		System.out.println();
		System.out.println("***********************************");
		System.out.println();
		System.out.println("         Played " + game_counter + " times.");
		System.out.println();
		System.out.println("***********************************");
		System.out.println();
		System.out.println("Number of black wins : " + black_win_counter);
		System.out.println("Number of white wins : " + white_win_counter);
		System.out.println("Number of draws : " + draw_win_counter);
	}

}
