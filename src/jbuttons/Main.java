package jbuttons;
import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		FireJFrame fireFrame = new FireJFrame();
		fireFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		fireFrame.setResizable(false);
		fireFrame.setSize(580, 630);
		fireFrame.setLocationRelativeTo(null);
		fireFrame.setVisible(true);
	}

}
