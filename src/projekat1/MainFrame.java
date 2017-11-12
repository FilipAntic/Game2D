package projekat1;

import java.awt.AlphaComposite;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import rafgfxlib.GameFrame;
import rafgfxlib.Util;

public class MainFrame extends GameFrame {

	// Konstante vezane za prozor
	private final int sirinaEkrana = getWidth();
	private final int visinaEkrana = getHeight();

	// Promenljive potrebne za rad sa aplikacijom
	private Map<String, BufferedImage> imageMap;
	private MemoryImage cursoredImage;
	private MemoryImage clickedImage;
	private boolean isClickedImageEffectOver;
	/**
	 * Svaki krug od kad je pokrenuta animacija za kliknutu sliku se ovaj brojac
	 * povecava i kada dodje do 20 prekida se stanje te animacije
	 */
	private int clickedImageCounter;
	private Koordinate firstFallCords;
	private Koordinate secondFallCords;
	private double vx;
	private double vy;
	private double secondvx;
	private double secondvy;
	private WritableRaster source;
	private Koordinate congratsStringCords;
	private float alpha = 0.0f;
	private boolean pogodjenPar;
	/**
	 * Koliko parova je pogodjeno.
	 */
	private int countPogodjenPar;
	private int snoozingColor;
	private WritableRaster raster;
	private BufferedImage image;
	private BufferedImage noiseImage;
	private int color;
	private boolean isFinishedGame;
	private MemoryImage prvaOtvorenaSlika;
	private int prvaOtvorenaSlikaInt;
	private MemoryImage drugaOtvorenaSlika;
	private int drugaOtvorenaSlikaInt;
	private int otvoreneSlike;
	private static ArrayList<MemoryImage> imagesInGame;
	private static ArrayList<MemoryImage> orderedImages;
	private boolean isMouseDown;
	private Particle[] parts;
	private int bunnyKeyPressed;
	private boolean startingGame;
	private int arrowY;
	private boolean arrowGoingUp;
	private String state;
	private int bugsBunnyImage;
	private String bugsBunnySide;
	private String bugsBunnyImageName;
	private int bugsBunnyFalling;
	private int confettiDestY;
	private int confettiMatrixH;
	private int noiseImageCounter;
	private boolean noiseUp;
	private int counterForPostIntro;
	private float composite;
	private int bunnyDancingCounter;

	public MainFrame() {
		super("Projekat1 - Igra memorije", 1000, 800);
		initialize();
		image = makeRaster();
		for (int i = 0; i < Constants.PARTICLE_MAX; i++) {
			parts[i] = new Particle();
		}
		genEx(1000, 800, 3.0f, 200, 100);
		startThread();
	}

	public static void main(String[] args) {
		new MainFrame().initGameWindow();
		generateImages();
		dynamicMemoryMatrix();

	}

	@Override
	public void handleKeyDown(int arg0) {
		if (isKeyDown(KeyEvent.VK_LEFT)) {
			bunnyKeyPressed -= 10;
		} else if (isKeyDown(KeyEvent.VK_RIGHT)) {
			bunnyKeyPressed += 10;
		}
	}

	@Override
	public void handleKeyUp(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void handleMouseDown(int arg0, int arg1, GFMouseButton arg2) {
		// TODO Auto-generated method stub
		isMouseDown = true;

	}

	@Override
	public void handleMouseMove(int arg0, int arg1) {
		cursoredImage = null;
		int x = arg0 - ((sirinaEkrana - 600) / 2);
		int y = arg1 - ((visinaEkrana - 600) / 2);
		for (MemoryImage m : orderedImages) {
			Koordinate k = m.getKoordinate();
			if (k.getX() < x && k.getX() + 150 > x && k.getY() < y && k.getY() + 150 > y) {
				cursoredImage = m;
			}
		}
		if (isFinishedGame) {
			setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		} else {
			if (x >= 0 && x < 600 && y >= 0 && y < 600) {
				setCursor(new Cursor(Cursor.HAND_CURSOR));
			} else {
				setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		}

	}

	@Override
	public void handleMouseUp(int arg0, int arg1, GFMouseButton arg2) {
		isMouseDown = false;
		clickedImage = null;
		int x = arg0 - ((sirinaEkrana - 600) / 2);
		int y = arg1 - ((visinaEkrana - 600) / 2);
		if (x >= 0 && x < 600 && y >= 0 && y < 600 && alpha >= 1.0f) {
			int count = 0;
			for (MemoryImage m : orderedImages) {
				Koordinate k = m.getKoordinate();
				if (k.getX() < x && k.getX() + 150 > x && k.getY() < y && k.getY() + 150 > y) {
					clickedImage = m;
					if (m.isOpened()) {
						return;
					}
					try {
						if ((k.getX() == prvaOtvorenaSlika.getKoordinate().getX()
								&& k.getY() == prvaOtvorenaSlika.getKoordinate().getY())
								|| (k.getX() == drugaOtvorenaSlika.getKoordinate().getX()
										&& k.getY() == drugaOtvorenaSlika.getKoordinate().getY())) {
							System.err.println("Odabrana je ista slika");
							return;
						}
					} catch (Exception e) {
					}
					otvoreneSlike++;
					if (otvoreneSlike == 3) {
						if (pogodjenPar) {
							drawSpecificCords(prvaOtvorenaSlika.getKoordinate().getX(),
									prvaOtvorenaSlika.getKoordinate().getY(), "background", "map");
							drawSpecificCords(drugaOtvorenaSlika.getKoordinate().getX(),
									drugaOtvorenaSlika.getKoordinate().getY(), "background", "map");
							orderedImages.get(prvaOtvorenaSlikaInt).setOpened(true);
							orderedImages.get(drugaOtvorenaSlikaInt).setOpened(true);
							pogodjenPar = false;
						} else {
							drawSpecificCords(prvaOtvorenaSlika.getKoordinate().getX(),
									prvaOtvorenaSlika.getKoordinate().getY(), "cover", "slika");
							drawSpecificCords(drugaOtvorenaSlika.getKoordinate().getX(),
									drugaOtvorenaSlika.getKoordinate().getY(), "cover", "slika");
						}
						prvaOtvorenaSlika.setFacedUp(false);
						drugaOtvorenaSlika.setFacedUp(false);
						prvaOtvorenaSlika = m;
						prvaOtvorenaSlika.setFacedUp(true);
						prvaOtvorenaSlikaInt = count;
						drawSpecificCords(prvaOtvorenaSlika.getKoordinate().getX(),
								prvaOtvorenaSlika.getKoordinate().getY(), "rgb", prvaOtvorenaSlika.getName());
						drugaOtvorenaSlika = null;
						otvoreneSlike = 1;
					} else if (otvoreneSlike == 1) {
						prvaOtvorenaSlika = m;
						prvaOtvorenaSlika.setFacedUp(true);
						prvaOtvorenaSlikaInt = count;
						drawSpecificCords(prvaOtvorenaSlika.getKoordinate().getX(),
								prvaOtvorenaSlika.getKoordinate().getY(), "rgb", prvaOtvorenaSlika.getName());
					} else if (otvoreneSlike == 2) {
						drugaOtvorenaSlika = m;
						drugaOtvorenaSlika.setFacedUp(true);
						drugaOtvorenaSlikaInt = count;
						drawSpecificCords(drugaOtvorenaSlika.getKoordinate().getX(),
								drugaOtvorenaSlika.getKoordinate().getY(), "rgb", drugaOtvorenaSlika.getName());
						if (drugaOtvorenaSlika.getName().equals(prvaOtvorenaSlika.getName())) {
							firstFallCords.setX(prvaOtvorenaSlika.getKoordinate().getX() + 200);
							firstFallCords.setY(prvaOtvorenaSlika.getKoordinate().getY() + 100);
							secondFallCords.setX(drugaOtvorenaSlika.getKoordinate().getX() + 200);
							secondFallCords.setY(drugaOtvorenaSlika.getKoordinate().getY() + 100);
							pogodjenPar = true;
							drawSpecificCords(prvaOtvorenaSlika.getKoordinate().getX(),
									prvaOtvorenaSlika.getKoordinate().getY(), "background", "map");
							drawSpecificCords(drugaOtvorenaSlika.getKoordinate().getX(),
									drugaOtvorenaSlika.getKoordinate().getY(), "background", "map");
							composite = 1.0f;
							countPogodjenPar++;
						}
						if (countPogodjenPar == 8) {
							System.out.println("Presao si igricu matori");
							drawSpecificCords(prvaOtvorenaSlika.getKoordinate().getX(),
									prvaOtvorenaSlika.getKoordinate().getY(), "background", "map");
							drawSpecificCords(drugaOtvorenaSlika.getKoordinate().getX(),
									drugaOtvorenaSlika.getKoordinate().getY(), "background", "map");
							isFinishedGame = true;
							genEx(1000, 800, 3.0f, 200, 2);
						}
					}
					break;
				}
				count++;
			}
			image = Util.rasterToImage(raster);
		}

	}

	@Override
	public void handleWindowDestroy() {

	}

	@Override
	public void handleWindowInit() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(Graphics2D g, int arg1, int arg2) {
		switch (state) {
		case "Intro":
			g.drawImage(Util.loadImage("backgroundIntro.jpg"), 0, 0, null);
			g.drawImage(Util.loadImage("holePNG.png"), 700, 500, null);
			if (bugsBunnySide.equals("right")) {
				bugsBunnyImageName = "" + bugsBunnyImage;
			} else if (bugsBunnySide.equals("left")) {
				bugsBunnyImageName = bugsBunnyImage + "flip";
			}
			g.drawImage(Util.loadImage("bunnyWalk/bunnyWalk" + bugsBunnyImageName + ".png"), bunnyKeyPressed,
					bugsBunnyFalling, null);
			System.out.println(bunnyKeyPressed);

			g.setFont(new Font("Verdana", Font.PLAIN, 20));
			g.drawString("Start", 750 + 15, arrowY - 20);
			g.drawImage(Util.loadImage("arrow.png"), 750, arrowY, null);
			g.drawString("Quit", 75 + 15, arrowY - 20);
			g.drawImage(Util.loadImage("arrow.png"), 75, arrowY, null);
			break;
		case "Game":
			int x = (sirinaEkrana - 600) / 2;
			int y = (visinaEkrana - 600) / 2;

			g.drawImage(imageMap.get("noise" + noiseImageCounter), 0, 0, null);

			if (startingGame) {
				g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
				g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			}

			g.drawImage(image, x, y, null);
			if (pogodjenPar && isClickedImageEffectOver) {
				try {
					g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, composite));
					g.drawImage(imageMap.get(prvaOtvorenaSlika.getName()), firstFallCords.getX(), firstFallCords.getY(),
							null);
					g.drawImage(imageMap.get(drugaOtvorenaSlika.getName()), secondFallCords.getX(),
							secondFallCords.getY(), null);

				} catch (Exception e) {

				}
			}

			try {
				if (cursoredImage != null && cursoredImage.isFacedUp() == false && cursoredImage.isOpened() == false
						&& isMouseDown == false) {
					g.drawImage(Ilustrator.blurGenerator(), cursoredImage.getKoordinate().getX() + 200,
							cursoredImage.getKoordinate().getY() + 100, null);
				}
				if (clickedImage != null && clickedImageCounter < 20) {
					isClickedImageEffectOver = false;
					g.drawImage(Ilustrator.bilinearSize(), clickedImage.getKoordinate().getX() + 175,
							clickedImage.getKoordinate().getY() + 75, null);
					clickedImageCounter++;
				} else {
					isClickedImageEffectOver = true;
					clickedImageCounter = 0;
					clickedImage = null;
				}
				if (isMouseDown) {
					g.drawImage(Ilustrator.clickedImage(), cursoredImage.getKoordinate().getX() + 200,
							cursoredImage.getKoordinate().getY() + 100, null);
				}
			} catch (Exception e) {
			}
			break;
		case "Postintro":
			g.drawImage(Util.loadImage("pozornica.jpg"), 0, 0, null);
			g.setColor(Constants.colors[color]);
			g.setFont(new Font("Algerian", Font.BOLD, 110));
			g.drawString("Cestitamo!", congratsStringCords.getX(), congratsStringCords.getY());
			g.drawImage(Util.loadImage("bunnyDancing/bunnyDancing" + (bunnyDancingCounter + 1) + ".png"), 350, 450,
					null);
			AffineTransform transform = new AffineTransform();
			int i = 0;
			for (Particle p : parts) {
				if (p.getLife() <= 0)
					continue;
				transform.setToIdentity();
				transform.translate(randomInt(1000), randomInt(800));
				transform.rotate(p.getAngle());
				transform.translate(-16.0, -16.0);

				try {
					transform.setToIdentity();
					transform.translate(p.getPosX(), p.getPosY());
					transform.rotate(p.getAngle());
					transform.translate(-16.0, -16.0);
					float compositeAlpha = (float) p.getLife() / (float) p.getLifeMax();
					if (compositeAlpha < 0.0f || compositeAlpha > 1.0f) {
						compositeAlpha = 0.5f;
					}
					g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, compositeAlpha));
					g.drawImage(imageMap.get("particles" + i), transform, null);
					i++;
					if (i > 20) {
						i = 0;
					}
				} catch (Exception e) {
					System.err.println(e);
				}
			}

			// for (int i = 0; i < 20; i++) {
			// g.drawImage(imageMap.get("particles" + i), randomInt(1000),
			// randomInt(800), null);
			// }
			break;
		}
	}

	@Override
	public void update() {
		composite -= 0.005;
		if (composite < 0.0f) {
			composite = 0;
		}
		if (state.equals("Postintro")) {
			bunnyDancingCounter++;
			if (bunnyDancingCounter > 47) {
				bunnyDancingCounter = 0;
			}
			confettiDestY += 1;
			confettiMatrixH += 1;
			if (confettiMatrixH > 5) {
				confettiMatrixH = 0;
			}
			if (confettiDestY > 900) {
				confettiDestY = 0;
			}
		}
		if (isFinishedGame) {
			if (counterForPostIntro > 100) {
				state = "Postintro";
				if (snoozingColor == 21) {
					snoozingColor = 1;
				}
				if (snoozingColor % 20 == 0) {
					color++;
				}
				if (color == 6) {
					color = 0;
				}

				snoozingColor++;
			}
			counterForPostIntro++;
		}
		if (arrowGoingUp) {
			arrowY -= 1;
		} else {
			arrowY += 1;
		}

		if (arrowY > 100) {
			arrowGoingUp = true;
		} else if (arrowY < 50) {
			arrowGoingUp = false;
		}
		if (isKeyDown(KeyEvent.VK_LEFT) && bunnyKeyPressed > 191) {
			bunnyKeyPressed -= 10;
			bugsBunnyImage++;
			bugsBunnySide = "left";
		} else if (isKeyDown(KeyEvent.VK_RIGHT) && bunnyKeyPressed > 191) {
			bunnyKeyPressed += 10;
			bugsBunnyImage++;
			bugsBunnySide = "right";
		}

		if (bunnyKeyPressed < 191 && bugsBunnyFalling < 900 && state.equals("Intro")) {
			bugsBunnyFalling += 20;
		}
		if (bugsBunnyFalling >= 900) {
			sleep(1000);
			System.exit(0);
		}

		if (bugsBunnyImage > 12) {
			bugsBunnyImage = 1;
		}

		if (bunnyKeyPressed < 0) {
			bunnyKeyPressed = 0;
		} else if (bunnyKeyPressed > 650) {
			bunnyKeyPressed = 0;
			state = "Game";
			bugsBunnyFalling = 0;
		}
		if (pogodjenPar && isClickedImageEffectOver) {
			bouncingImage();
		}

		if (true) {
			int ix = 0;
			for (Particle p : parts) {
				if (p.getLife() <= 0) {
					continue;
				}
				p.setLife(p.getLife() - 1);
				p.setPosX(p.getPosX() + p.getdX());
				p.setPosY(p.getPosY() + p.getdY());
				p.setdX(p.getdX() * 0.99f);
				p.setdY(p.getdY() * 0.99f);
				p.setdY(p.getdY() + 0.1f);
				p.setAngle(p.getAngle() + p.getRot());
				p.setRot(p.getRot() * 0.99f);
				ix++;
			}

		}

		if (state.equals("Game")) {
			alpha += 0.03f;
			if (noiseUp) {
				noiseImageCounter++;
			} else {
				noiseImageCounter--;
			}
			if (noiseImageCounter > 50) {
				noiseImageCounter = 50;
				noiseUp = false;
			} else if (noiseImageCounter < 1) {
				noiseImageCounter = 1;
				noiseUp = true;
			}
		}
		if (alpha >= 1.0f) {
			alpha = 1.0f;
			startingGame = false;
		}
		genEx(randomInt(1000), randomInt(800), 3.0f, 200, 20);
	}

	public void sleep(int sleep) {
		try {
			Thread.sleep(sleep);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public BufferedImage makeRaster() {
		int[] rgb = new int[3];

		rgb[0] = 255;
		rgb[1] = 0;
		rgb[2] = 51;

		int j = 0;
		source = imageMap.get("cover").getRaster();
		for (int i = 0; i <= 15; i++) {
			Koordinate koordinata = Constants.cords[j];
			int kordX = koordinata.getX();
			int kordY = koordinata.getY();
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
		for (int i = 0; i < Constants.images.length; i++) {
			String imgName = Constants.images[i];
			String[] parts = imgName.split("\\.");
			imagesInGame.add(new MemoryImage(parts[0], imgName));
			imagesInGame.add(new MemoryImage(parts[0], imgName));
		}
	}

	public static void dynamicMemoryMatrix() {
		int[] randomImages = new Random().ints(0, 16).distinct().limit(16).toArray();
		for (int i = 0; i < randomImages.length; i++) {
			MemoryImage img = imagesInGame.get(randomImages[i]);
			img.setKoordinate(Constants.cords[i]);
			orderedImages.add(img);
		}
	}

	public void drawSpecificCords(int specificX, int specificY, String whatImage, String nameOfImage) {
		int[] rgb = new int[3];
		rgb[0] = 255;
		rgb[1] = 0;
		rgb[2] = 51;
		switch (whatImage) {
		case "rgb":
			source = imageMap.get(nameOfImage).getRaster();
			break;
		case "cover":
			source = imageMap.get("cover").getRaster();
			break;
		case "background":
			source = imageMap.get("map").getRaster();
			for (int y1 = specificY; y1 < specificY + 150; y1++) {
				for (int x1 = specificX; x1 < specificX + 150; x1++) {
					source.getPixel(x1, y1, rgb);
					raster.setPixel(x1, y1, rgb);
				}
			}
			return;

		}
		for (int y1 = specificY; y1 < specificY + 150; y1++) {
			for (int x1 = specificX; x1 < specificX + 150; x1++) {
				source.getPixel(x1 - specificX, y1 - specificY, rgb);
				raster.setPixel(x1, y1, rgb);
			}
		}

	}

	public BufferedImage loadImage(String imageName) {
		return Ilustrator.resizeImage(Util.loadImage(imageName), 150, 150);
	}

	public void bouncingImage() {
		firstFallCords.setX((int) (firstFallCords.getX() + vx));
		firstFallCords.setY((int) (firstFallCords.getY() + vy));

		// bounce Y (don't bounce on top)
		if (firstFallCords.getY() >= getHeight() - 150) {
			firstFallCords.setY(getHeight() - 150);
			vy = -(vy * Constants.elasticity);
		}

		// bounce X
		if ((firstFallCords.getX() >= getWidth() - 150) || (firstFallCords.getX() <= 0)) {
			firstFallCords.setX((firstFallCords.getX() < (0 + 150) ? (0) : (getWidth() - 150)));
			// WALLS
			// LIMIT
			vx = -(vx * Constants.elasticity);
		}

		// compute gravity
		vy += Constants.gravity;

		// compute frictions
		vx *= Constants.airDrag;
		vy *= Constants.airDrag;
		if (firstFallCords.getY() >= (getHeight() - 150)) { // grounded
			vx *= Constants.groundFriction;
		}

		// ----------------------------druga
		// slika----------------------------------------

		secondFallCords.setX((int) (secondFallCords.getX() + secondvx));
		secondFallCords.setY((int) (secondFallCords.getY() + secondvy));

		// bounce Y (don't bounce on top)
		if (secondFallCords.getY() >= getHeight() - 150) {
			secondFallCords.setY(getHeight() - 150); // (!) GROUND LIMIT
			secondvy = -(secondvy * Constants.elasticity);
		}

		// bounce X
		if ((secondFallCords.getX() >= getWidth() - 150) || (secondFallCords.getX() <= 0)) {

			secondFallCords.setX((secondFallCords.getX() < (0 + 150) ? (0) : (getWidth() - 150))); // (!)
			// WALLS
			// LIMIT
			secondvx = -(secondvx * Constants.elasticity);
		}

		// compute gravity
		secondvy += Constants.gravity;

		// compute frictions
		secondvx *= Constants.airDrag;
		secondvy *= Constants.airDrag;
		if (secondFallCords.getY() >= (getHeight() - 150)) { // grounded
			secondvx *= Constants.groundFriction;
		}
		System.out.println(vy);
	}

	private void initialize() {
		secondFallCords = new Koordinate(0, 0);
		firstFallCords = new Koordinate(0, 0);
		imageMap = new HashMap<String, BufferedImage>();
		cursoredImage = null;
		clickedImage = null;
		isClickedImageEffectOver = true;
		clickedImageCounter = 0;
		congratsStringCords = new Koordinate(150, 150);
		raster = Util.createRaster(600, 600, false);
		vx = Math.random() * 50;
		vy = 0;
		secondvx = Math.random() * 50;
		secondvy = 0;
		source = null;
		pogodjenPar = false;
		countPogodjenPar = 0;
		snoozingColor = 0;
		color = 0;
		isFinishedGame = false;
		prvaOtvorenaSlika = null;
		prvaOtvorenaSlikaInt = 0;
		drugaOtvorenaSlika = null;
		drugaOtvorenaSlikaInt = 0;
		otvoreneSlike = 0;
		imagesInGame = new ArrayList<>();
		orderedImages = new ArrayList<>();
		isMouseDown = false;
		parts = new Particle[Constants.PARTICLE_MAX];
		bunnyKeyPressed = 350;
		startingGame = true;
		arrowY = 50;
		arrowGoingUp = false;
		state = "Postintro";
		bugsBunnyImage = 1;
		bugsBunnySide = "right";
		bugsBunnyFalling = 400;
		confettiDestY = 0;
		confettiMatrixH = 0;
		noiseImageCounter = 1;
		counterForPostIntro = 0;
		noiseImageCounter = 1;
		fillMap();
		noiseUp = true;
		composite = 1.0f;
		bunnyDancingCounter = 0;
	}

	public void fillMap() {
		BufferedImage mapImage = Util.loadImage("map.jpg");
		BufferedImage coverImage = loadImage("slika.png");
		BufferedImage crocodileImage = loadImage("animals/crocodile.jpg");
		BufferedImage eagleImage = loadImage("animals/eagle.jpg");
		BufferedImage elephantImage = loadImage("animals/elephant.jpg");
		BufferedImage fishImage = loadImage("animals/fish.jpg");
		BufferedImage giraffeImage = loadImage("animals/giraffe.jpg");
		BufferedImage lionImage = loadImage("animals/lion.jpg");
		BufferedImage monkeyImage = loadImage("animals/monkey.jpeg");
		BufferedImage snakeImage = loadImage("animals/snake.jpg");
		imageMap.put("map", mapImage);
		imageMap.put("cover", coverImage);
		imageMap.put("crocodile", crocodileImage);
		imageMap.put("eagle", eagleImage);
		imageMap.put("elephant", elephantImage);
		imageMap.put("fish", fishImage);
		imageMap.put("giraffe", giraffeImage);
		imageMap.put("lion", lionImage);
		imageMap.put("monkey", monkeyImage);
		imageMap.put("snake", snakeImage);
		for (int counter = 1; counter < 51; counter++) {
			imageMap.put("noise" + counter, Util.loadImage("noise/noise" + counter + ".png"));
		}
		for (int counter = 0; counter < 20; counter++) {
			imageMap.put("particles" + counter, Util.loadImage("particles/particle" + (counter + 1) + ".png"));
		}

	}

	private void genEx(float cX, float cY, float radius, int life, int count) {
		for (Particle p : parts) {
			if (p.getLife() <= 0) {
				p.setLife((int) (Math.random() * life * 0.5) + life / 2);
				p.setLifeMax((int) (Math.random() * life * 0.5) + life / 2);
				p.setPosX(randomInt((int) cX));
				p.setPosY(randomInt((int) cY));
				double angle = Math.random() * Math.PI * 2.0;
				double speed = Math.random() * radius;
				p.setdX((float) (Math.cos(angle) * speed));
				p.setdY((float) (Math.sin(angle) * speed));
				p.setAngle((float) (Math.random() * Math.PI * 2.0));
				p.setRot((float) (Math.random() - 0.5) * 0.3f);
				count--;
				if (count <= 0)
					return;
			}
		}
	}

	public int randomInt(int range) {
		Random rand = new Random();
		if (range <= 0) {
			range = 1000;
		}
		return rand.nextInt(range);
	}
}
