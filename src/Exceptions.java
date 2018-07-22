import javax.swing.JOptionPane;

public class Exceptions {
	
	public static void gameOver() throws ArithmeticException{
		JOptionPane.showMessageDialog(null, "Game over", "Minesweeper", JOptionPane.PLAIN_MESSAGE);
		throw new ArithmeticException("");
	}
}
