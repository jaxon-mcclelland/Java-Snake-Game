import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;


public class GameBoard extends JPanel implements ActionListener {
	
	final static int boardWidth = 1300;
	final static int boardHeight = 750;
	final static int unitSize = 25;
	static final int maxUnits = (boardWidth*boardHeight)/(unitSize*unitSize);
	static final int delay = 100;
	
	final int x[] = new int[maxUnits];
	final int y[] = new int[maxUnits];
	
	int bodyParts = 5;
	int score;
	int foodX;
	int foodY;
	boolean isRunning = false;
	private Image food;
	Random r = new Random();
	Timer timer;
	// set init direction
	char direction = 'R';
	
	public GameBoard() {
		this.setPreferredSize(new Dimension(boardWidth, boardHeight));
		this.setBackground(Color.blue);
		this.setFocusable(true);
		this.addKeyListener(new TAdapter());
		initGame();
		
	}
	
	public void initGame() {
		loadImages();
		newFood();
		isRunning = true;
		timer = new Timer(delay,this);
		timer.start();
		
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}
	public void loadImages() {
		ImageIcon iif = new ImageIcon("src/resources/food.png");
		food = iif.getImage();	

		
	}
	public void draw(Graphics g) {
		if(isRunning) {
			g.drawImage(food, foodX, foodY, this);	
			for(int i = 0; i < bodyParts; i++) {
				g.setColor(Color.yellow);
				g.fillRect(x[i], y[i], unitSize, unitSize);
			}
		}
	}
	public void move() {
		for(int i = bodyParts; i > 0; i--) {
			x[i] = x[i-1];
			y[i] = y[i-1];
		}
		switch(direction) {
		case 'U':
			y[0] = y[0] - unitSize;
			break;
		case 'D':
			y[0] = y[0] + unitSize;
			break;
		case 'L':
			x[0] = x[0] - unitSize;
			break;
		case 'R':
			x[0] = x[0] + unitSize;
			break;
		}
		
	}
	public void checkFood() {
		if((x[0] == foodX) && (y[0] == foodY)) {
			bodyParts++;
			score++;
			newFood();
		}
	}
	public void checkCollision() {
		for(int i = bodyParts; i > 0; i--) {
			if((x[0]  == x[i]) && y[0] == y[i]) {
				isRunning = false;
			}
			if((x[0] < 0) || (x[0] > boardWidth) || (y[0] < 0) || (y[0] > boardHeight)) {
				isRunning = false;
			}
			if(!isRunning) {
				timer.stop();
			}
		}
	}
	public void newFood() {
		foodX = r.nextInt((int)(boardWidth/unitSize))*unitSize;
		foodY = r.nextInt((int)(boardHeight/unitSize))*unitSize;
	}
	public class TAdapter extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_A:
				if(direction != 'R') {
					direction = 'L';
				}
				break;
			case KeyEvent.VK_D:
				if(direction != 'L') {
					direction = 'R';
				}
				break;
			case KeyEvent.VK_W:
				if(direction != 'D') {
					direction = 'U';
				}
				break;
			case KeyEvent.VK_S:
				if(direction != 'U') {
					direction = 'D';
				}
				break;
			}
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(isRunning) {
			move();
			checkFood();
			checkCollision();
		}
		repaint();
	}
	

	
	
}