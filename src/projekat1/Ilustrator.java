package projekat1;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

import rafgfxlib.ImageViewer;
import rafgfxlib.Util;


public class Ilustrator {
	
	private static BufferedImage blurImage = Util.loadImage("slika.png");
	private static WritableRaster source = blurImage.getRaster();
	private static WritableRaster target = Util.createRaster(blurImage.getWidth(), blurImage.getHeight(), false);
	static float power = 8.0f;
	static float size = 0.8f;
	
//	private static BufferedImage image = new BufferedImage(MainFrame.WIDTH,MainFrame.HEIGHT, BufferedImage.TYPE_INT_RGB);
//	private static Random random = new Random();
//	
//	public static BufferedImage noiseGenerator(){
//		 
//		 
//	    	for(int y = 0; y < MainFrame.WIDTH; y++){
//	    		System.out.println("tu si 1");
//	    		for(int x = 0; x < MainFrame.HEIGHT; x++){
//	        		image.setRGB(x, y, random.nextInt(0xFFFFFF));
//	        		System.out.println("tu si 2");
//	        	}
//	    	}
//			return image;
//	    }
	
	
	public static BufferedImage noiseGenerator(){
		int[] black = { 0, 0, 0 };
		int[] white = { 255, 255, 255 };
		
		int octaves = 10;
		
		int octaveSize = 2;
		
		float persistence = 0.75f;
		
		int width = (int)Math.pow(octaveSize, octaves);
		int height = width;
		
		WritableRaster target = Util.createRaster(width, height, false);
		
		float[][] tempMap = new float[width][height];
		
		float[][] finalMap = new float[width][height];
		
		float multiplier = 1.0f;
		
		for(int o = 0; o < octaves; ++o)
		{
			float[][] octaveMap = new float[octaveSize][octaveSize];
			
			for(int x = 0; x < octaveSize; ++x)
			{
				for(int y = 0; y < octaveSize; ++y)
				{
					octaveMap[x][y] = ((float)Math.random() - 0.5f) * 2.0f;
					
				}
			}
			
			Util.floatMapRescale(octaveMap, tempMap);
			
			Util.floatMapMAD(tempMap, finalMap, multiplier);
			
			octaveSize *= 2;
			
			multiplier *= persistence;
		}
		
		Util.mapFloatMapToRaster(finalMap, -1.0f, 1.0f, black, white, target);
		
		return Util.rasterToImage(target);
		
		
		
	}
	
	public static BufferedImage blurGenerator() {
		int rgb[] = new int[3];
		
		
		for(int y = 0; y < blurImage.getHeight(); y++)
		{			
			for(int x = 0; x < blurImage.getWidth(); x++)
			{
				float srcX = (float)(x + Math.sin(y * size) * power);
				float srcY = (float)(y + Math.cos(x * size) * power);
				Util.bilSample(source, srcX, srcY, rgb);
				target.setPixel(x, y, rgb);
				
			}
			power = power - 0.001f;
			size = size - 0.0001f;
			if(power<7 || size <0.7){
			power = 8.0f;
			size= 0.8f;
			}
		}
		System.out.println(power);
		System.out.println(size);
		
		return Util.rasterToImage(target);
	}
	
	public static void bilinearSample(WritableRaster src, float u, float v, int[] color) {
		
		int width = src.getWidth();
		int height = src.getHeight();
		
		//u = Util.clamp(u - 0.5f, 0.0f, width - 1.0f);
		//v = Util.clamp(v - 0.5f, 0.0f, height - 1.0f);
		
		int[] UL = new int[3];
		int[] UR = new int[3];
		int[] LL = new int[3];
		int[] LR = new int[3];

		int x0 = (int)u;
		int y0 = (int)v;
		int x1 = x0 + 1;
		int y1 = y0 + 1;
		
		if(x1 >= width) x1 = width - 1;
		if(y1 >= height) y1 = height - 1;
		
		float fX = u - x0;
		float fY = v - y0;
		
		src.getPixel(x0, y0, UL);
		src.getPixel(x1, y0, UR);
		src.getPixel(x0, y1, LL);
		src.getPixel(x1, y1, LR);
		
		int[] a = new int[3];
		int[] b = new int[3];
		
		Util.lerpRGBi(UL, UR, fX, a);
		Util.lerpRGBi(LL, LR, fX, b);
		Util.lerpRGBi(a, b, fY, color);
	}
	
	public static BufferedImage bilinearSize() {
		
		int scaleW = 200;
		int scaleH = 200;
		
		WritableRaster resultBilinear = Util.createRaster(scaleW, scaleH, false);
		
		int rgb[] = new int[3];
		
		for(int y = 0; y < scaleW; y++)
		{
			float fy = (float)y / scaleW;
			for(int x = 0; x < scaleH; x++)
			{
				float fx = (float)x / scaleH;
				
				float srcX = fx * source.getWidth();
				float srcY = fy * source.getHeight();
				
				bilinearSample(source, srcX, srcY, rgb);
				resultBilinear.setPixel(x, y, rgb);
			}
		}
		
		return Util.rasterToImage(resultBilinear);
	}
		
		
}
