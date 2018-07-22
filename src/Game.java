import java.util.Random;
import javax.swing.JOptionPane;

public class Game {
	
	private Mine[][] field;
	private int xSize;
	private int ySize;
	private int numberOfBombs;
	private boolean destroy;
	
	
	//constructor
	public Game(int xSize, int ySize, int numberOfBombs) {
		
		this.xSize = xSize;
		this.ySize = ySize;
		this.numberOfBombs= numberOfBombs;
		destroy = true;
		field = new Mine[xSize][ySize];
		
		//Filling the field[][] with bombs that are no bombs, later set several bombs
		for(int arrY = 0; arrY < ySize; arrY++) {
			 for(int arrX = 0; arrX < xSize; arrX++) {
				 field[arrX][arrY] = new Mine(arrX, arrY, this);
			 }
		 }
		 
		 //Spawning 'numberOfBombs' bombs on the field
		 Random randomNum = new Random();
		 for(int i = 0; i < numberOfBombs; i++) {
			 while(true) {
				 int potentialX = randomNum.nextInt(xSize);
				 int potentialY = randomNum.nextInt(ySize);
				 if(!field[potentialX][potentialY].bombHere()) {
					 field[potentialX][potentialY].setBomb();
					 break;
				 }
			 }
			 
		 }
		 
		 //For all bombs calculate(), count bombs around
		 for(int arrY = 0; arrY < ySize; arrY++) {
			 for(int arrX = 0; arrX < xSize; arrX++) {
				 field[arrX][arrY].calculate();
			 }
		 }		 
	}
	
	
	public Mine[][] getField(){	return field;	}
	public int getXSize() {	return xSize;	}
	public int getYSize() {	return ySize;	}
	
	
	public void toggleDestroy(){
		destroy = !destroy;
		if(destroy)		System.out.println("Destroying enabled");	
		if(!destroy)	System.out.println("Setting flags now");	
	}
	
	
	
	public boolean inBorders(int x, int y, boolean print) {
		
		if(print){
			if(x < 0) 		System.out.println("x has to be higher than 0!");
			
			if(x >= xSize)	System.out.println("x has to be lower than/equal to " + xSize + "!");
		
			if(y < 0) 		System.out.println("y has to be higher than 0!");
			
			if(y >= ySize)	System.out.println("y has to be lower than/equal to " + ySize + "!");
			
		}
		
		return (x >= 0 && x < xSize && y >= 0 && y < ySize);
			
	}
	
	public boolean legal(int x, int y, boolean print) {
		if(inBorders(x, y, print)) {
			if(field[x][y].fieldOpened()) {
				if(print)	System.out.println("The field is already opened");
				return false;
			}
			return true;
		}
		return false;
	}
	
	public void print() {
		String text = numberOfBombs +" Bombs left";
		text += "\n  ";
		for(int x = 0; x < xSize; x++)	text += ("|" + (x+1));
		text += ("|\n");
		
		for(int y = 0; y < ySize; y++) {
			text += (y + 1 + " ");
			for(int x = 0; x < xSize; x++) {
				text += "|";
				text += field[x][y].giveChar();
			}
			text += "|\n";
		}
		for(int x = 0; x <= xSize; x++)	text += ("__");
		text += ("\n");
		
		System.out.print(text);
	}
	
	
	void printBombs(){
		String text = "  ";
		for(int x = 0; x < xSize; x++)	text += ("|" + (x+1));
		text += ("|\n");
		
		for(int y = 0; y < ySize; y++) {
			text += (y + 1 + " ");
			for(int x = 0; x < xSize; x++) {
				text += "|";
				if(field[x][y].bombHere()) {	text += "B";}
				else {	text += "_";	}
			}
			text += "|\n";
		}
		for(int x = 0; x <= xSize; x++)	text += ("__");
		text += ("\n");
		
		System.out.print(text);
	}
	
	
	private void unhideAllAroundZero(){
		boolean shouldBreak;
		for(int i = 0; i < xSize * ySize; i++){
			
			shouldBreak = true; 
			for(int arrY = 0; arrY < ySize; arrY++){
				for(int arrX = 0; arrX < xSize; arrX++) {
					if(inBorders(arrX, arrY, false) && field[arrX][arrY].getBombsAround() == 0 && field[arrX][arrY].fieldOpened()){
						field[arrX][arrY].openSurroundings();
						shouldBreak = false;
					}
				}
			}
			if(shouldBreak) break;
		}
	}
	
	
	public boolean turn(int x, int y) {
		if(destroy){
			if(!field[x][y].open())	return false;
			System.out.println("Turn done");
			unhideAllAroundZero();
			return true;
		}else{
			field[x][y].toggleFlag();
			if(field[x][y].flag()) {	
				this.numberOfBombs--;
			}else this.numberOfBombs++;
			return true;
		}
		
		//print();
	}
}
