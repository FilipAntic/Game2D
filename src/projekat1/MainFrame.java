package projekat1;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.Random;

import rafgfxlib.GameFrame;
import rafgfxlib.Util;

public class MainFrame extends GameFrame {

	final int[] ints = new Random().ints(0, 6).distinct().limit(6).toArray();
	WritableRaster raster = Util.createRaster(600, 600, false);
	int sirinaEkrana = getWidth();
	BufferedImage image;
	int visinaEkrana = getHeight();
	boolean first = true;
	Koordinate[] cords = { new Koordinate(0, 0), new Koordinate(150, 0), new Koordinate(300, 0), new Koordinate(450, 0),
			new Koordinate(0, 150), new Koordinate(150, 150), new Koordinate(300, 150), new Koordinate(450, 150),
			new Koordinate(0, 300), new Koordinate(150, 300), new Koordinate(300, 300), new Koordinate(450, 300),
			new Koordinate(0, 450), new Koordinate(150, 450), new Koordinate(300, 450), new Koordinate(450, 450) };
	Color[] colors = { Color.BLUE, Color.CYAN, Color.GREEN, Color.PINK, Color.YELLOW, Color.WHITE };

	public MainFrame() {
		super("Projekat1 - Igra memorije", 1000, 800);
		image = makeRaster();
		startThread();
	}

	public static void main(String[] args) {
		new MainFrame().initGameWindow();

	}

	@Override
	public void handleKeyDown(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void handleKeyUp(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void handleMouseDown(int arg0, int arg1, GFMouseButton arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void handleMouseMove(int arg0, int arg1) {
		int x = arg0 - ((sirinaEkrana - 600) / 2);
		int y = arg1 - ((visinaEkrana - 600) / 2);
		if (x >= 0 && x < 600 && y >= 0 && y < 600) {
			setCursor(new Cursor(Cursor.HAND_CURSOR));
		} else {
			setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
	}

	@Override
	public void handleMouseUp(int arg0, int arg1, GFMouseButton arg2) {
		int x = arg0 - ((sirinaEkrana - 600) / 2);
		int y = arg1 - ((visinaEkrana - 600) / 2);
		if (x >= 0 && x < 600 && y >= 0 && y < 600) {
			System.out.println("To je to");
			int[] rgb = new int[3];

			rgb[0] = 255;
			rgb[1] = 0;
			rgb[2] = 51;
			for (int y1 = 0; y1 < 150; y1++) {
				for (int x1 = 0; x1 < 150; x1++) {
					raster.setPixel(x1, y1, rgb);
				}
			}
			image = Util.rasterToImage(raster);
			update();
		}
	}

	@Override
	public void handleWindowDestroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void handleWindowInit() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(Graphics2D arg0, int arg1, int arg2) {
		int x = (sirinaEkrana - 600) / 2;
		int y = (visinaEkrana - 600) / 2;
		arg0.drawImage(image, x, y, null);
	}

	@Override
	public void update() {
	}

	public BufferedImage makeRaster() {

		BufferedImage image = Util.loadImage("slika.png");
		WritableRaster source = image.getRaster();
		int[] rgb = new int[3];

		rgb[0] = 255;
		rgb[1] = 0;
		rgb[2] = 51;

		int j = 0;
		for (int i = 0; i <= 15; i++) {
			Koordinate koordinata = cords[j];
			int kordX = koordinata.x;
			int kordY = koordinata.y;
			for (int y = kordY; y < kordY + 150; y++) {
				for (int x = kordX; x < kordX + 150; x++) {
					source.getPixel(x - kordX, y - kordY, rgb);
					raster.setPixel(x, y, rgb);
				}
			}
			j++;
		}

		return Util.rasterToImage(raster);
	}
}
