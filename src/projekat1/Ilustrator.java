package projekat1;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Ilustrator {
	public static BufferedImage image; 
	public static Random random;
	
	
	public static void main(String[] args) {
		
//		System.out.println(randomColor());
		
	}
	
	public static BufferedImage noiseGenerator(){
		image = new BufferedImage(MainFrame.WIDTH, MainFrame.HEIGHT, BufferedImage.TYPE_INT_RGB);
		random = new Random();
		for(int y = 0; y< MainFrame.HEIGHT; y++) {
			for(int x = 0; y< MainFrame.WIDTH; x++) {
				image.setRGB(x, y, random.nextInt(0xFFFFFF));
			}
		}
		return image;
		
		
		
	}
		
	public static int randomColor(){
		
		Random random = new Random();
		
		return random.nextInt(256);
		
	}
	
	

}
