package jbuttons;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.SecureRandom;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class FireJFrame extends JFrame implements ActionListener {
	private static final long serialVersionUID = 5153894942303340131L;

	private JButton[] firePixelsArray;

	private int fireWidth = 60;
	private int fireHeigth = 60;

	private Color[] fireColorsPalette = { new Color(7, 7, 7), new Color(31, 7, 7), new Color(47, 15, 7),
			new Color(71, 15, 7), new Color(87, 23, 7), new Color(103, 31, 7), new Color(119, 31, 7),
			new Color(143, 39, 7), new Color(159, 47, 7), new Color(175, 63, 7), new Color(191, 71, 7),
			new Color(199, 71, 7), new Color(223, 79, 7), new Color(223, 87, 7), new Color(223, 87, 7),
			new Color(215, 95, 7), new Color(215, 95, 7), new Color(215, 103, 15), new Color(207, 111, 15),
			new Color(207, 119, 15), new Color(207, 127, 15), new Color(207, 135, 23), new Color(207, 135, 23),
			new Color(199, 135, 23), new Color(199, 143, 23), new Color(199, 151, 31), new Color(191, 159, 31),
			new Color(191, 159, 31), new Color(191, 167, 39), new Color(191, 167, 39), new Color(191, 175, 47),
			new Color(183, 175, 47), new Color(183, 183, 47), new Color(183, 183, 55), new Color(207, 207, 111),
			new Color(223, 223, 159), new Color(239, 239, 199), new Color(255, 255, 255) };

	private enum Wind {
		LEFT, CENTER, RIGHT
	};

	private Wind wind = Wind.LEFT;

	private Timer timer = new Timer(50, this);

	private final SecureRandom random = new SecureRandom();

	public FireJFrame() {
		super("Fire of DOOM");

		start();
		initializeWindButtons();
	}

	public void initializeWindButtons() {
		JPanel optionsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

		JButton windLeft = new JButton("Left");
		windLeft.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				wind = Wind.LEFT;

			}
		});

		JButton windCenter = new JButton("Center");
		windCenter.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				wind = Wind.CENTER;

			}
		});

		JButton windRigth = new JButton("Right");
		windRigth.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				wind = Wind.RIGHT;

			}
		});

		optionsPanel.add(windLeft);
		optionsPanel.add(windCenter);
		optionsPanel.add(windRigth);
		add(optionsPanel, BorderLayout.SOUTH);
	}

	public void start() {
		createFireDataStructure();
		createFireSource();

		timer.start();
	}

	public void createFireDataStructure() {
		JPanel firePanel = new JPanel(new GridLayout(fireWidth, fireHeigth));

		int numberOfPixels = fireWidth * fireHeigth;

		firePixelsArray = new JButton[numberOfPixels];

		for (int i = 0; i < numberOfPixels; i++) {
			firePixelsArray[i] = new JButton();
			firePixelsArray[i].setText("0");
			firePixelsArray[i].setBorderPainted(false);
			firePixelsArray[i].setForeground(Color.BLACK);
			firePixelsArray[i].setBackground(Color.BLACK);
			firePanel.add(firePixelsArray[i]);
		}

		add(firePanel, BorderLayout.CENTER);

	}

	public void createFireSource() {
		for (int column = 0; column < fireWidth; column++) {
			int overFlowPixelIndex = fireWidth * fireHeigth;
			int pixelIndex = (overFlowPixelIndex - fireWidth) + column;

			firePixelsArray[pixelIndex].setText("" + 36);
			firePixelsArray[pixelIndex].setForeground(fireColorsPalette[36]);
			firePixelsArray[pixelIndex].setBackground(fireColorsPalette[36]);
		}

	}

	public void calculateFirePropagation() {
		for (int column = 0; column < fireHeigth; column++) {
			for (int row = 0; row < fireWidth; row++) {
				int pixelIndex = column + (fireWidth * row);

				updateFireIntensityPerPixel(pixelIndex);
			}
		}
	}

	public void updateFireIntensityPerPixel(int currentPixelIndex) {
		int belowPixelIndex = currentPixelIndex + fireWidth;

		if (belowPixelIndex >= fireWidth * fireHeigth)
			return;

		int decay = random.nextInt(3);
		int belowPixelFireIntensity = Integer.parseInt(firePixelsArray[belowPixelIndex].getText());
		int newFireIntensity = belowPixelFireIntensity - decay >= 0 ? belowPixelFireIntensity - decay : 0;

		switch (wind) {
		case LEFT:
			try {
				firePixelsArray[currentPixelIndex - decay].setText("" + newFireIntensity);
				firePixelsArray[currentPixelIndex - decay].setBackground(fireColorsPalette[newFireIntensity]);
				firePixelsArray[currentPixelIndex - decay].setForeground(fireColorsPalette[newFireIntensity]);
			} catch (Exception e) {
				firePixelsArray[currentPixelIndex].setText("" + newFireIntensity);
				firePixelsArray[currentPixelIndex].setBackground(fireColorsPalette[newFireIntensity]);
				firePixelsArray[currentPixelIndex].setForeground(fireColorsPalette[newFireIntensity]);
			}
			break;
		case CENTER:
			firePixelsArray[currentPixelIndex].setText("" + newFireIntensity);
			firePixelsArray[currentPixelIndex].setBackground(fireColorsPalette[newFireIntensity]);
			firePixelsArray[currentPixelIndex].setForeground(fireColorsPalette[newFireIntensity]);
			break;
		case RIGHT:
			try {
				firePixelsArray[currentPixelIndex + decay].setText("" + newFireIntensity);
				firePixelsArray[currentPixelIndex + decay].setBackground(fireColorsPalette[newFireIntensity]);
				firePixelsArray[currentPixelIndex + decay].setForeground(fireColorsPalette[newFireIntensity]);
			} catch (Exception e) {
				firePixelsArray[currentPixelIndex].setText("" + newFireIntensity);
				firePixelsArray[currentPixelIndex].setBackground(fireColorsPalette[newFireIntensity]);
				firePixelsArray[currentPixelIndex].setForeground(fireColorsPalette[newFireIntensity]);
			}
			break;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		calculateFirePropagation();
	}

}
