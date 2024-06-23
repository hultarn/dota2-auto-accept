package autoaccept;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Main
{
	static BufferedImage img = null;
	static boolean isAccepted = false;
	JFrame myFrame;
	
	public Main() throws Exception
	{
		myFrame = new JFrame("autoacc");
		myFrame.pack();
	    myFrame.setLocationByPlatform(true);
	    myFrame.setVisible(true);    
	    
		fakeMain();
	}
	
	public void acceptGame() throws Exception 
	{
		Robot robot = new Robot();
		BufferedImage screenShot = robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
		int a = 90;
		screenShot = screenShot.getSubimage((1920 / 2) - 768 / 2, (1080 / 2) - 256 / 2 - 50 + a, 768, 256 - a);
		if (compareImages(screenShot, img)) 
		{
			isAccepted = true;
			click(1920 / 2, 1080 / 2);
		}
	}

	public void fakeMain() throws Exception
	{
		try {
			img = ImageIO.read(new File("C:\\dotapic.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		loop();
	}
	
	public void loop() throws Exception 
	{
		while (true) 
		{
			Thread.sleep(3000);
			if (!isAccepted) {
				acceptGame();

			} else 
			{
				myFrame.dispose();
				System.exit(0);
			}
		}
	}
	
	
	public static void main(String[] args) throws Exception {
		new Main();
	}
	
	
	public static void click(int x, int y) throws AWTException 
	{
		Point p = MouseInfo.getPointerInfo().getLocation();
		int x1 = (int) p.getX();
		int y1 = (int) p.getY();		
		
		Robot bot = new Robot();
		bot.mouseMove(x, y);
		bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
		
		bot.mouseMove(x1, y1);
	}
	
	public static boolean compareImages(BufferedImage imgA, BufferedImage imgB) 
	{
		if (imgA.getWidth() != imgB.getWidth() || imgA.getHeight() != imgB.getHeight()) {
			return false;
		}

		int width = imgA.getWidth();
		int height = imgA.getHeight();

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (imgA.getRGB(x, y) != imgB.getRGB(x, y)) {
					return false;
				}
			}
		}

		return true;
	}	
}
