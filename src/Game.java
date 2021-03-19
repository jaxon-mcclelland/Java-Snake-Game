import javax.swing.JFrame;

public class Game extends JFrame {
	
	public Game() {
		this.add(new GameBoard());
		this.setTitle("Snake");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(true);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}
	
	public static void main(String[] args) {
		
		Game game = new Game();
	}
}
