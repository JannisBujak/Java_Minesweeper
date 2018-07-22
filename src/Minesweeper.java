import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.logging.Handler;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Minesweeper implements ActionListener, MouseListener, MouseMotionListener {
	
	private Game game;
	
	private boolean gameOver;
	
	private final int WIDTH = 1200, HEIGHT = 800;
	public JFrame jframe;
	public Renderer renderer;
	public Timer timer;
	private int xFields = 10;
	private int yFields = 10;
	private int numberOfBombs = 15;
	
	
	public Minesweeper() {
		
		game = new Game(xFields, yFields, numberOfBombs);
		

		jframe = new JFrame();
		timer = new Timer(20, this);
		renderer = new Renderer(this);
		jframe.add(renderer);
		jframe.setResizable(false);
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.setSize(WIDTH + 6, HEIGHT + 30);
		jframe.setVisible(true);
		
		jframe.addMouseListener(this);
		jframe.addMouseMotionListener(this);
		
		
		timer.start();
	}
	
	public void makeTurn() {
		
		while(true) {
			String findX = JOptionPane.showInputDialog("Insert a number for x");	
			if(findX == null) break;
			if(findX.equals("")){
				game.toggleDestroy(); continue;
			}
			String findY = JOptionPane.showInputDialog("Insert a number for y");
			if(findY == null) break;
			if(findY.equals("")){
				game.toggleDestroy(); continue;
			}
			
			System.out.println("(" + findX + "|" + findY + ")");
			
			int xInput = (Integer.parseInt(findX) - 1);
			int yInput = (Integer.parseInt(findY) - 1);
			
			if(game.legal(xInput, yInput, true))	game.turn(xInput, yInput);
			
		}
		
	}
	
	public void repaint(Graphics g) {
		
		if(gameOver) {
			
			for(int y = 0; y < yFields; y++) {
				for(int x = 0; x < xFields; x++) {
					
					if(this.game.getField()[x][y].bombHere()) {
						g.setColor(Color.red.darker());
					}else{
						g.setColor(Color.white);
					}
					
					g.fillRect(x * WIDTH / xFields, y * HEIGHT / yFields, WIDTH / xFields, HEIGHT / yFields);
					
					g.setColor(Color.black);
					g.drawRect(x * WIDTH / xFields, y * HEIGHT / yFields, WIDTH / xFields, HEIGHT / yFields);
					
					
					
				}
			}
			g.setColor(Color.RED);
			g.setFont(new Font("Arial", 1, 200));
			g.drawString("Game over!" , 50, HEIGHT / 2);
			
		}else {
			for(int y = 0; y < yFields; y++) {
				for(int x = 0; x < xFields; x++) {
					
					
					String stoneHere = game.getField()[x][y].giveChar();
					
					if(stoneHere.equals("f")) {
						g.setColor(Color.red);
					}else if(stoneHere.equals("x")) {
						g.setColor(Color.GREEN.darker());
					}else if(stoneHere.equals("_")) {
						g.setColor(Color.white);
					}else if(stoneHere.equals("B")) {
						g.setColor(Color.red.darker());
					}else {
						g.setColor(Color.GREEN.darker());
					}
					
					g.fillRect(x * WIDTH / xFields, y * HEIGHT / yFields, WIDTH / xFields, HEIGHT / yFields);
					
					g.setColor(Color.black);
					g.drawRect(x * WIDTH / xFields, y * HEIGHT / yFields, WIDTH / xFields, HEIGHT / yFields);
					
					
					
				}
			}
			for(int y = 0; y < yFields; y++) {
				for(int x = 0; x < xFields; x++) {
					
					String stoneHere = game.getField()[x][y].giveChar();
					
					if(!(stoneHere.equals("f") || stoneHere.equals("x") ||stoneHere.equals("_") || stoneHere.equals("B"))) {
						g.setColor(Color.yellow);
						g.setFont(new Font("Arial", 1, 100));
						g.drawString(stoneHere , x * WIDTH / xFields + 30, (y + 1) * HEIGHT / yFields);
						
					}
				}
			}
		}
		
		
		
		
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		renderer.repaint();
	}
	
	
	public static void main(String[] args) {	

		
		Minesweeper m = new Minesweeper();
		
	}
	
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		
		System.out.println("Click");
		
		int mouseX = jframe.getMousePosition().x;
		int mouseY = jframe.getMousePosition().y;
		
		System.out.println(mouseX + " " + mouseY);
		System.out.println((int)(mouseX - 10 / (WIDTH/xFields)));
		System.out.println((int)(mouseY / (HEIGHT/yFields)));
		int pressedX = (int)((mouseX - 10)/ (WIDTH/xFields));
		int pressedY =  (int)((mouseY - 30) / (HEIGHT/yFields));
		
		if(!game.turn(pressedX, pressedY)) {
			gameOver = true;
		}
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {}

	@Override
	public void mouseExited(MouseEvent arg0) {}

	@Override
	public void mousePressed(MouseEvent arg0) {}

	@Override
	public void mouseReleased(MouseEvent arg0) {}

	@Override
	public void mouseDragged(MouseEvent arg0) {}

	@Override
	public void mouseMoved(MouseEvent arg0) {}

	

}
