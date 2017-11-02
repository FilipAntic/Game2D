package projekat1;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.ArrayList;
import java.util.Random;

import rafgfxlib.GameFrame;
import rafgfxlib.Util;

public class MainFrame extends GameFrame {

	BufferedImage coverImage = Util.loadImage("slika.png");
	BufferedImage crocodileImage = Util.loadImage("crocodile.jpg");
	BufferedImage eagleImage = Util.loadImage("eagle.jpg");
	BufferedImage elephantImage = Util.loadImage("elephant.jpg");
	BufferedImage fishImage = Util.loadImage("fish.jpg");
	BufferedImage giraffeImage = Util.loadImage("giraffe.jpg");
	BufferedImage lionImage = Util.loadImage("lion.jpg");
	BufferedImage monkeyImage = Util.loadImage("monkey.jpeg");
	BufferedImage snakeImage = Util.loadImage("snake.jpg");
	BufferedImage moneyImage = Util.loadImage("money.jpg");
	WritableRaster source = null;
	boolean pogodjenPar = false;
	final int[] ints = new Random().ints(0, 6).distinct().limit(6).toArray();
	WritableRaster raster = Util.createRaster(600, 600, false);
	int sirinaEkrana = getWidth();
	BufferedImage image;
	int visinaEkrana = getHeight();
	boolean first = true;
	MemoryImage prvaOtvorenaSlika = null;
	MemoryImage drugaOtvorenaSlika = null;
	int otvoreneSlike = 0;
	static ArrayList<MemoryImage> imagesInGame = new ArrayList<>();
	static ArrayList<MemoryImage> orderedImages = new ArrayList<>();
	static String[] images = { "crocodile.jpg", "eagle.jpg", "elephant.jpg", "fish.jpg", "giraffe.jpg", "lion.jpg",
			"monkey.jpeg", "snake.jpg" };
	static Koordinate[] cords = { new Koordinate(0, 0), new Koordinate(150, 0), new Koordinate(300, 0),
			new Koordinate(450, 0), new Koordinate(0, 150), new Koordinate(150, 150), new Koordinate(300, 150),
			new Koordinate(450, 150), new Koordinate(0, 300), new Koordinate(150, 300), new Koordinate(300, 300),
			new Koordinate(450, 300), new Koordinate(0, 450), new Koordinate(150, 450), new Koordinate(300, 450),
			new Koordinate(450, 450) };
	Color[] colors = { Color.BLUE, Color.CYAN, Color.GREEN, Color.PINK, Color.YELLOW, Color.WHITE };

	public MainFrame() {
		super("Projekat1 - Igra memorije", 1000, 800);
		image = makeRaster();
		startThread();
	}

	public static void main(String[] args) {
		new MainFrame().initGameWindow();
		generateImages();
		dynamicMemoryMatrix();

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

			for (MemoryImage m : orderedImages) {
				Koordinate k = m.getKoordinate();
				
				if (k.getX() < x && k.getX() + 150 > x && k.getY() < y && k.getY() + 150 > y) {
					try {
						
						if ((k.getX()==drugaOtvorenaSlika.getKoordinate().getX() && k.getY()==drugaOtvorenaSlika.getKoordinate().getY()) || (k.getX()==prvaOtvorenaSlika.getKoordinate().getX() && k.getY()==prvaOtvorenaSlika.getKoordinate().getY())) {
							System.out.println("Udjoh ovde");
							return;
						}
						System.out.println(prvaOtvorenaSlika);
						System.out.println(drugaOtvorenaSlika);
					} catch (Exception e) {
						// TODO: handle exception
					}
					otvoreneSlike++;
					if (otvoreneSlike == 3) {
						if (pogodjenPar) {
							drawSpecificCords(prvaOtvorenaSlika.getKoordinate().getX(),
									prvaOtvorenaSlika.getKoordinate().getY(), "rgb", "pogodak");
							drawSpecificCords(drugaOtvorenaSlika.getKoordinate().getX(),
									drugaOtvorenaSlika.getKoordinate().getY(), "rgb", "pogodak");
							pogodjenPar = false;
						} else {
							drawSpecificCords(prvaOtvorenaSlika.getKoordinate().getX(),
									prvaOtvorenaSlika.getKoordinate().getY(), "cover", "slika");
							drawSpecificCords(drugaOtvorenaSlika.getKoordinate().getX(),
									drugaOtvorenaSlika.getKoordinate().getY(), "cover", "slika");
						}

						prvaOtvorenaSlika = m;
						drawSpecificCords(prvaOtvorenaSlika.getKoordinate().getX(),
								prvaOtvorenaSlika.getKoordinate().getY(), "rgb", prvaOtvorenaSlika.getName());
						drugaOtvorenaSlika = null;
						otvoreneSlike = 1;
					} else if (otvoreneSlike == 1) {
						prvaOtvorenaSlika = m;
						drawSpecificCords(prvaOtvorenaSlika.getKoordinate().getX(),
								prvaOtvorenaSlika.getKoordinate().getY(), "rgb", prvaOtvorenaSlika.getName());
					} else if (otvoreneSlike == 2) {
						drugaOtvorenaSlika = m;
						drawSpecificCords(drugaOtvorenaSlika.getKoordinate().getX(),
								drugaOtvorenaSlika.getKoordinate().getY(), "rgb", drugaOtvorenaSlika.getName());
						if (drugaOtvorenaSlika.getName().equals(prvaOtvorenaSlika.getName())) {
							pogodjenPar = true;
						}
					}
					break;
				}
			}
			image = Util.rasterToImage(raster);
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
	public void render(Graphics2D g, int arg1, int arg2) {
		int x = (sirinaEkrana - 600) / 2;
		int y = (visinaEkrana - 600) / 2;
		g.drawImage(Ilustrator.noiseGenerator(), 0, 0, null);
		g.drawImage(image, x, y, null);

	}

	@Override
	public void update() {
	}

	public BufferedImage makeRaster() {

		int[] rgb = new int[3];

		rgb[0] = 255;
		rgb[1] = 0;
		rgb[2] = 51;

		int j = 0;
		source = coverImage.getRaster();
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

	public static void generateImages() {
		for (int i = 0; i < images.length; i++) {
			String imgName = images[i];
			String[] parts = imgName.split("\\.");
			imagesInGame.add(new MemoryImage(parts[0], imgName));
			imagesInGame.add(new MemoryImage(parts[0], imgName));
		}
	}

	public static void dynamicMemoryMatrix() {
		int[] randomImages = new Random().ints(0, 16).distinct().limit(16).toArray();
		for (int i = 0; i < randomImages.length; i++) {
			MemoryImage img = imagesInGame.get(randomImages[i]);
			img.setKoordinate(cords[i]);
			orderedImages.add(img);
		}

		for (MemoryImage m : orderedImages) {
			System.out.println(m);
		}
	}

	public void drawSpecificCords(int specificX, int specificY, String whatImage, String nameOfImage) {
		int[] rgb = new int[3];
		rgb[0] = 255;
		rgb[1] = 0;
		rgb[2] = 51;
		switch (whatImage) {
		case "rgb":
			switch (nameOfImage) {
			case "monkey":
				source = monkeyImage.getRaster();
				break;
			case "crocodile":
				source = crocodileImage.getRaster();
				break;
			case "eagle":
				source = eagleImage.getRaster();
				break;
			case "elephant":
				source = elephantImage.getRaster();
				break;
			case "fish":
				source = fishImage.getRaster();
				break;
			case "giraffe":
				source = giraffeImage.getRaster();
				break;
			case "lion":
				source = lionImage.getRaster();
				break;
			case "snake":
				source = snakeImage.getRaster();
				break;
			case "pogodak":
				source = moneyImage.getRaster();
				break;
			}

			break;
		case "cover":
			source = coverImage.getRaster();
			break;
		}
		for (int y1 = specificY; y1 < specificY + 150; y1++) {
			for (int x1 = specificX; x1 < specificX + 150; x1++) {
				source.getPixel(x1 - specificX, y1 - specificY, rgb);
				raster.setPixel(x1, y1, rgb);
			}
		}

	}

}
