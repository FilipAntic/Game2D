package projekat1;

import java.util.Objects;
import java.util.UUID;

public class MemoryImage {

	private String name;
	private String path;
	private Koordinate koordinate;
	private boolean isOpened;
	private String ID;

	public MemoryImage(String name, String path, Koordinate koordinate) {
		this.name = name;
		this.path = path;
		this.koordinate = koordinate;
		setOpened(false);
		setID(UUID.randomUUID().toString());
	}

	public MemoryImage() {
		setOpened(false);
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

	public boolean isOpened() {
		return isOpened;
	}

	public void setOpened(boolean isOpened) {
		this.isOpened = isOpened;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	@Override
	public String toString() {
		return "MemoryImage [name=" + name + ", path=" + path + ", koordinate=" + koordinate + "]";
	}

	@Override
	public boolean equals(Object o) {
		// self check
		if (this == o)
			return true;
		// null check
		if (o == null)
			return false;
		// type check and cast
		if (getClass() != o.getClass())
			return false;
		MemoryImage image = (MemoryImage) o;
		// field comparison
		return Objects.equals(ID, image.ID);
	}

}
