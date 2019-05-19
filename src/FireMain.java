import javax.swing.JFrame;

public class FireMain {

	public static void main(String[] args) {
		FireFrame fireFrame = new FireFrame();
		fireFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		fireFrame.setResizable(false);
		fireFrame.setSize(580, 630);
		fireFrame.setLocationRelativeTo(null);
		fireFrame.setVisible(true);
	}

}
