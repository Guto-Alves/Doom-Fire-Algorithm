package java2d;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class MainWindow extends JFrame {
	private static final long serialVersionUID = -3880026026104218593L;

	public MainWindow() {
		setTitle("Doom Fire!");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(new Dimension(516, 409));
		setResizable(false);
		setLocationRelativeTo(null);

		FireJPanel fireJPanel = new FireJPanel();

		JPanel controlJPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

		JButton modeDebugJButton = new JButton("Toggle Mode Debug");
		modeDebugJButton.addActionListener(event -> {
			fireJPanel.setDebug(!fireJPanel.isDebug());
		});

		JPanel windDirectionJPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		windDirectionJPanel.setBorder(new TitledBorder(new LineBorder(Color.BLACK), "Wind Direction"));

		JButton leftJButton = new JButton("Left");
		leftJButton.addActionListener(event -> {
			fireJPanel.setDirection(FireJPanel.Direction.LEFT);
		});

		JButton centerJButton = new JButton("Center");
		centerJButton.addActionListener(event -> {
			fireJPanel.setDirection(FireJPanel.Direction.CENTER);
		});

		JButton rightJButton = new JButton("Right");
		rightJButton.addActionListener(event -> {
			fireJPanel.setDirection(FireJPanel.Direction.RIGHT);
		});

		windDirectionJPanel.add(leftJButton);
		windDirectionJPanel.add(centerJButton);
		windDirectionJPanel.add(rightJButton);

		controlJPanel.add(windDirectionJPanel);
		controlJPanel.add(modeDebugJButton);

		add(fireJPanel, BorderLayout.CENTER);
		add(controlJPanel, BorderLayout.SOUTH);
	}

}
