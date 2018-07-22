import javax.swing.JOptionPane;

public class Mine {
	private Game game;
	private int x;
	private int y;
	private boolean bomb;
	private boolean opened;
	private int bombsAround;
	private boolean flag;
	
	public Mine(int x, int y, Game game) {
		
		this.game = game;
		this.x= x;
		this.y = y;
		bomb = false;
		opened = false;
	}
	public boolean bombHere() {	return bomb;	}
	public boolean fieldOpened(){	return opened;	}
	public int getBombsAround(){	return bombsAround; }
	public boolean flag() {	return flag;	}
	
	public void setBomb() {	bomb = true;	}
	public void toggleFlag(){	flag = !flag;	}
	
	public void calculate(){
		for(int yCount = y - 1; yCount <= y + 1; yCount++){
			for(int xCount = x - 1; xCount <= x + 1; xCount++){
				if(game.inBorders(xCount, yCount, false) && game.getField()[xCount][yCount].bombHere()) bombsAround++;
			}
		}
	}
	
	public void openSurroundings(){
		if(bombsAround != 0)	return;
		for(int yCount = y - 1; yCount <= y + 1; yCount++){
			for(int xCount = x - 1; xCount <= x + 1; xCount++){
				if(game.inBorders(xCount, yCount, false))	game.getField()[xCount][yCount].unhide();
			}
		}
	}
	
	public void unhide(){
		opened = true;
	}
	
	public boolean open() {
		if(flag){
			while(true){
				String sure = JOptionPane.showInputDialog("Sure you want to open Field (" + x + "|" + y + ") ?\ny for yes, n for no");
				if(sure.equals("y")) break;
				if(sure.equals("n")) return true;
			}
		}
		opened = true;
		if(bomb) {
			game.printBombs();
			return false;
			//System.exit(0);
		}
		return true;
	}
	
	public String giveChar() {
		if(!opened) {
			if(flag)return "f";
			return "x";
		}
		if(!bomb) {	
			if(bombsAround == 0)	return "_";
			return String.valueOf(bombsAround);
		}
		return "B";
	}
}
