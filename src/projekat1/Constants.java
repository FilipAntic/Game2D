package projekat1;

import java.awt.Color;

public class Constants {

	public static final double gravity = 0.98;
	public static final double elasticity = 0.8;
	public static final double airDrag = 0.99;
	public static final double groundFriction = 0.98;
	public static final Color[] colors = { Color.BLUE, Color.CYAN, Color.GREEN, Color.PINK, Color.YELLOW, Color.WHITE };
	public static final Koordinate[] cords = { new Koordinate(0, 0), new Koordinate(150, 0), new Koordinate(300, 0),
			new Koordinate(450, 0), new Koordinate(0, 150), new Koordinate(150, 150), new Koordinate(300, 150),
			new Koordinate(450, 150), new Koordinate(0, 300), new Koordinate(150, 300), new Koordinate(300, 300),
			new Koordinate(450, 300), new Koordinate(0, 450), new Koordinate(150, 450), new Koordinate(300, 450),
			new Koordinate(450, 450) };
	public static final String[] images = { "crocodile.jpg", "eagle.jpg", "elephant.jpg", "fish.jpg", "giraffe.jpg",
			"lion.jpg", "monkey.jpeg", "snake.jpg" };
	public static final int PARTICLE_MAX = 16;
}
