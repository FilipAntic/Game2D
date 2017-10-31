package projekat1;

public class MemoryImage {

	private String name;
	private String path;
	private int x;
	private int y;

	public MemoryImage(String name, String path, int x, int y) {
		this.name = name;
		this.path = path;
		this.x = x;
		this.y = y;
	}

	public MemoryImage() {

	}

	public MemoryImage(String name, String path) {
		super();
		this.name = name;
		this.path = path;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

}
