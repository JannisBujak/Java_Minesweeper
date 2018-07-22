
import java.awt.Graphics;
import javax.swing.JPanel;


public class Renderer extends JPanel
{
	
	
	private static final long serialVersionUID = 1L;
	private Minesweeper minesweeper;
	
	
	public Renderer(Minesweeper minesweeper) {
		this.minesweeper = minesweeper;
	}
	
	
	@Override
	protected void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
		
		minesweeper.repaint(g);
	}
	
}
