package projekat1;

public class MemoryImage {

	private String name;
	private String path;
	private Koordinate koordinate;

	public MemoryImage(String name, String path, Koordinate koordinate) {
		this.name = name;
		this.path = path;
		this.koordinate = koordinate;
	}

	public MemoryImage() {

	}

	public MemoryImage(String name, String path) {
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

	public Koordinate getKoordinate() {
		return koordinate;
	}

	public void setKoordinate(Koordinate koordinate) {
		this.koordinate = koordinate;
	}

	@Override
	public String toString() {
		return "MemoryImage [name=" + name + ", path=" + path + ", koordinate=" + koordinate + "]";
	}

	
}
