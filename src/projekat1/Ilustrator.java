package projekat1;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.nio.BufferUnderflowException;
import java.util.Random;

import rafgfxlib.ImageViewer;
import rafgfxlib.Util;

public class Ilustrator {

	private static BufferedImage coverImage = Util.loadImage("slika.png");
	private static WritableRaster source = coverImage.getRaster();
	private static WritableRaster target = Util.createRaster(coverImage.getWidth(), coverImage.getHeight(), false);
	static float power = 8.0f;
	static float size = 0.8f;

	/*
	 * Noise
	 */
	private static BufferedImage noiseImage = new BufferedImage(MainFrame.WIDTH, MainFrame.HEIGHT,
			BufferedImage.TYPE_INT_RGB);
	private static Random random = new Random();
	private static double time = 0;

	public static BufferedImage noiseGeneratro() {
		time += 0.01;

		for (int y = 0; y < MainFrame.HEIGHT; y++) {
			for (int x = 0; x < MainFrame.WIDTH; x++) {
				double dx = (double) x / MainFrame.HEIGHT;
				double dy = (double) y / MainFrame.HEIGHT;
				int frequency = 6;
				double noise = noise((dx * time) + time, (dy * time) + time, time);
				noise = (noise - 1) / 2;
				int b = (int) (noise * 0xFF);
				int g = b * 0x100;
				int r = b * 0x10000;
				int finalValue = r;
				noiseImage.setRGB(x, y, finalValue);
			}
		}
		return noiseImage;
	}

	public static double noise(double x, double y, double z) {
		int X = (int) Math.floor(x) & 255, // FIND UNIT CUBE THAT
				Y = (int) Math.floor(y) & 255, // CONTAINS POINT.
				Z = (int) Math.floor(z) & 255;
		x -= Math.floor(x); // FIND RELATIVE X,Y,Z
		y -= Math.floor(y); // OF POINT IN CUBE.
		z -= Math.floor(z);
		double u = fade(x), // COMPUTE FADE CURVES
				v = fade(y), // FOR EACH OF X,Y,Z.
				w = fade(z);
		int A = p[X] + Y, AA = p[A] + Z, AB = p[A + 1] + Z, // HASH COORDINATES OF
				B = p[X + 1] + Y, BA = p[B] + Z, BB = p[B + 1] + Z; // THE 8 CUBE CORNERS,

		return lerp(w, lerp(v, lerp(u, grad(p[AA], x, y, z), // AND ADD
				grad(p[BA], x - 1, y, z)), // BLENDED
				lerp(u, grad(p[AB], x, y - 1, z), // RESULTS
						grad(p[BB], x - 1, y - 1, z))), // FROM 8
				lerp(v, lerp(u, grad(p[AA + 1], x, y, z - 1), // CORNERS
						grad(p[BA + 1], x - 1, y, z - 1)), // OF CUBE
						lerp(u, grad(p[AB + 1], x, y - 1, z - 1), grad(p[BB + 1], x - 1, y - 1, z - 1))));
	}

	static double fade(double t) {
		return t * t * t * (t * (t * 6 - 15) + 10);
	}

	static double lerp(double t, double a, double b) {
		return a + t * (b - a);
	}

	static double grad(int hash, double x, double y, double z) {
		int h = hash & 15; // CONVERT LO 4 BITS OF HASH CODE
		double u = h < 8 ? x : y, // INTO 12 GRADIENT DIRECTIONS.
				v = h < 4 ? y : h == 12 || h == 14 ? x : z;
		return ((h & 1) == 0 ? u : -u) + ((h & 2) == 0 ? v : -v);
	}

	static final int p[] = new int[512], permutation[] = { 151, 160, 137, 91, 90, 15, 131, 13, 201, 95, 96, 53, 194,
			233, 7, 225, 140, 36, 103, 30, 69, 142, 8, 99, 37, 240, 21, 10, 23, 190, 6, 148, 247, 120, 234, 75, 0, 26,
			197, 62, 94, 252, 219, 203, 117, 35, 11, 32, 57, 177, 33, 88, 237, 149, 56, 87, 174, 20, 125, 136, 171, 168,
			68, 175, 74, 165, 71, 134, 139, 48, 27, 166, 77, 146, 158, 231, 83, 111, 229, 122, 60, 211, 133, 230, 220,
			105, 92, 41, 55, 46, 245, 40, 244, 102, 143, 54, 65, 25, 63, 161, 1, 216, 80, 73, 209, 76, 132, 187, 208,
			89, 18, 169, 200, 196, 135, 130, 116, 188, 159, 86, 164, 100, 109, 198, 173, 186, 3, 64, 52, 217, 226, 250,
			124, 123, 5, 202, 38, 147, 118, 126, 255, 82, 85, 212, 207, 206, 59, 227, 47, 16, 58, 17, 182, 189, 28, 42,
			223, 183, 170, 213, 119, 248, 152, 2, 44, 154, 163, 70, 221, 153, 101, 155, 167, 43, 172, 9, 129, 22, 39,
			253, 19, 98, 108, 110, 79, 113, 224, 232, 178, 185, 112, 104, 218, 246, 97, 228, 251, 34, 242, 193, 238,
			210, 144, 12, 191, 179, 162, 241, 81, 51, 145, 235, 249, 14, 239, 107, 49, 192, 214, 31, 181, 199, 106, 157,
			184, 84, 204, 176, 115, 121, 50, 45, 127, 4, 150, 254, 138, 236, 205, 93, 222, 114, 67, 29, 24, 72, 243,
			141, 128, 195, 78, 66, 215, 61, 156, 180 };
	static {
		for (int i = 0; i < 256; i++)
			p[256 + i] = p[i] = permutation[i];
	}

	// public static BufferedImage noiseGenerator() {
	// int[] black = { 0, 0, 0 };
	// int[] white = { 255, 255, 255 };
	//
	// int octaves = 10;
	//
	// int octaveSize = 2;
	//
	// float persistence = 0.75f;
	//
	// int width = (int) Math.pow(octaveSize, octaves);
	// int height = width;
	//
	// WritableRaster target = Util.createRaster(width, height, false);
	//
	// float[][] tempMap = new float[width][height];
	//
	// float[][] finalMap = new float[width][height];
	//
	// float multiplier = 1.0f;
	//
	// for (int o = 0; o < octaves; ++o) {
	// float[][] octaveMap = new float[octaveSize][octaveSize];
	//
	// for (int x = 0; x < octaveSize; ++x) {
	// for (int y = 0; y < octaveSize; ++y) {
	// octaveMap[x][y] = ((float) Math.random() - 0.5f) * 2.0f;
	//
	// }
	// }
	//
	// Util.floatMapRescale(octaveMap, tempMap);
	//
	// Util.floatMapMAD(tempMap, finalMap, multiplier);
	//
	// octaveSize *= 2;
	//
	// multiplier *= persistence;
	// }
	//
	// Util.mapFloatMapToRaster(finalMap, -1.0f, 1.0f, black, white, target);
	//
	// return Util.rasterToImage(target);
	//
	// }

	public static BufferedImage blurGenerator() {
		int rgb[] = new int[3];

		for (int y = 0; y < coverImage.getHeight(); y++) {
			for (int x = 0; x < coverImage.getWidth(); x++) {
				float srcX = (float) (x + Math.sin(y * size) * power);
				float srcY = (float) (y + Math.cos(x * size) * power);
				Util.bilSample(source, srcX, srcY, rgb);
				target.setPixel(x, y, rgb);

			}
			power = power - 0.001f;
			size = size - 0.0001f;
			if (power < 7 || size < 0.7) {
				power = 8.0f;
				size = 0.8f;
			}
		}

		return Util.rasterToImage(target);
	}

	public static void bilinearSample(WritableRaster src, float u, float v, int[] color) {

		int width = src.getWidth();
		int height = src.getHeight();

		// u = Util.clamp(u - 0.5f, 0.0f, width - 1.0f);
		// v = Util.clamp(v - 0.5f, 0.0f, height - 1.0f);

		int[] UL = new int[3];
		int[] UR = new int[3];
		int[] LL = new int[3];
		int[] LR = new int[3];

		int x0 = (int) u;
		int y0 = (int) v;
		int x1 = x0 + 1;
		int y1 = y0 + 1;

		if (x1 >= width)
			x1 = width - 1;
		if (y1 >= height)
			y1 = height - 1;

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

		for (int y = 0; y < scaleW; y++) {
			float fy = (float) y / scaleW;
			for (int x = 0; x < scaleH; x++) {
				float fx = (float) x / scaleH;

				float srcX = fx * source.getWidth();
				float srcY = fy * source.getHeight();

				bilinearSample(source, srcX, srcY, rgb);
				resultBilinear.setPixel(x, y, rgb);
			}
		}

		return Util.rasterToImage(resultBilinear);
	}

	public static BufferedImage resizeImage(BufferedImage image, int scaleW, int scaleH) {
		WritableRaster source = image.getRaster();
		WritableRaster target = Util.createRaster(scaleW, scaleH, false);

		int rgb[] = new int[3];

		for (int y = 0; y < scaleW; y++) {
			double fy = (double) y / scaleW;

			for (int x = 0; x < scaleH; x++) {
				double fx = (double) x / scaleH;

				double srcX = fx * source.getWidth();
				double srcY = fy * source.getHeight();

				int pX = clamp((int) srcX, 0, source.getWidth() - 1);
				int pY = clamp((int) srcY, 0, source.getHeight() - 1);
				source.getPixel(pX, pY, rgb);

				target.setPixel(x, y, rgb);
			}
		}
		return Util.rasterToImage(target);
	}

	public static int clamp(int value, int min, int max) {
		if (value < min)
			return min;
		if (value > max)
			return max;
		return value;
	}

	public static BufferedImage clickedImage() {
		int rgb[] = new int[3];
		int[] blackArray = { 0, 0, 0 };
		BufferedImage coverSmallerImage = resizeImage(coverImage, 138, 138);
		for (int y = 0; y < coverImage.getHeight(); y++) {
			for (int x = 0; x < coverImage.getWidth(); x++) {
				if (x < 12 || y < 12) {
					target.setPixel(x, y, blackArray);
				} else {
					coverSmallerImage.getRaster().getPixel(x - 12, y - 12, rgb);
					target.setPixel(x, y, rgb);
				}
			}
		}
		return Util.rasterToImage(target);
	}
}
