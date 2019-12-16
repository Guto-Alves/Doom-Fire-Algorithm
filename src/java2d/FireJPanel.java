package java2d;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Arrays;

import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.LineBorder;

public class FireJPanel extends JPanel {
	private static final long serialVersionUID = -5626875942362550941L;

	private int[] firePixels;

	private final int TOTAL_PIXELS = 100; // 100 x 100

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

	public enum Direction {
		LEFT, RIGHT, CENTER
	}

	private Direction direction = Direction.LEFT;

	private boolean debug;

	public FireJPanel() {
		setBorder(new LineBorder(Color.BLACK));
		setBackground(Color.WHITE);
		start();
	}

	public void start() {
		createFireDataStructure();
		createFireSource();

		new Timer(50, event -> {
			calculateFirePropagation();
			renderFire();
		}).start();
	}

	public void createFireDataStructure() {
		int numberOfPixels = TOTAL_PIXELS * TOTAL_PIXELS;

		firePixels = new int[numberOfPixels];

		Arrays.fill(firePixels, 0);
	}

	public void createFireSource() {
		int overflowPixelIndex = TOTAL_PIXELS * TOTAL_PIXELS;

		for (int column = 0; column < TOTAL_PIXELS; column++) {
			int pixelIndex = (overflowPixelIndex - TOTAL_PIXELS) + column;

			firePixels[pixelIndex] = 36;
		}
	}

	public void calculateFirePropagation() {
		for (int column = 0; column < TOTAL_PIXELS; column++) {
			for (int row = 0; row < TOTAL_PIXELS; row++) {
				int pixelIndex = column + (TOTAL_PIXELS * row);

				updateFireIntensityPerPixel(pixelIndex);
			}
		}
	}

	public void updateFireIntensityPerPixel(int currentPixel) {
		int belowPixelIndex = currentPixel + TOTAL_PIXELS;

		if (belowPixelIndex >= TOTAL_PIXELS * TOTAL_PIXELS)
			return;

		int decay = (int) Math.floor(Math.random() * 3);
		int belowPixelFireIntensity = firePixels[belowPixelIndex];
		int newFireIntensity = belowPixelFireIntensity - decay >= 0 ? belowPixelFireIntensity - decay : 0;

		switch (direction) {
		case LEFT:
			firePixels[currentPixel - decay >= 0 ? currentPixel - decay : currentPixel] = newFireIntensity;
			break;
		case CENTER:
			firePixels[currentPixel] = newFireIntensity;
			break;
		case RIGHT:
			firePixels[currentPixel + decay < TOTAL_PIXELS * TOTAL_PIXELS ? currentPixel + decay
					: currentPixel] = newFireIntensity;
			break;
		}
	}

	public void renderFire() {
		repaint();
		revalidate();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g;

		int pixelWidth = getWidth() / TOTAL_PIXELS;
		int pixelHeight = getHeight() / TOTAL_PIXELS;

		for (int row = 0; row < TOTAL_PIXELS; row++) {
			for (int column = 0; column < TOTAL_PIXELS; column++) {
				int pixelIndex = column + (TOTAL_PIXELS * row);
				int fireIntensity = firePixels[pixelIndex];

				int x = column * pixelWidth;
				int y = row * pixelHeight;

				if (debug) {
					g2d.setColor(Color.BLACK);
					g2d.drawRect(x, y, pixelWidth, pixelHeight);

					g2d.setFont(new Font("Monospaced", Font.PLAIN, 8));
					g2d.drawString(String.format("%d", pixelIndex), x + 2, y + 11);

					g2d.setFont(new Font("Monospaced", Font.BOLD, 16));
					g2d.drawString(String.format("%d", fireIntensity), x + (pixelWidth / 2 - 2),
							y + (pixelHeight / 2 + 3));
				} else {
					g2d.setColor(fireColorsPalette[fireIntensity]);
					g2d.fillRect(x, y, pixelWidth, pixelHeight);
				}
			}
		}
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public boolean isDebug() {
		return debug;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}
}
