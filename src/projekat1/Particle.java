package projekat1;

public class Particle {

	private float posX;
	private float posY;
	private float dX;
	private float dY;
	private int life = 0;
	private int lifeMax = 0;
	private int imageID = 0;
	private float angle = 0.0f;
	private float rot = 0.0f;
	public float getPosX() {
		return posX;
	}
	public void setPosX(float posX) {
		this.posX = posX;
	}
	public float getPosY() {
		return posY;
	}
	public void setPosY(float posY) {
		this.posY = posY;
	}
	public float getdX() {
		return dX;
	}
	public void setdX(float dX) {
		this.dX = dX;
	}
	public float getdY() {
		return dY;
	}
	public void setdY(float dY) {
		this.dY = dY;
	}
	public int getLife() {
		return life;
	}
	public void setLife(int life) {
		this.life = life;
	}
	public int getLifeMax() {
		return lifeMax;
	}
	public void setLifeMax(int lifeMax) {
		this.lifeMax = lifeMax;
	}
	public int getImageID() {
		return imageID;
	}
	public void setImageID(int imageID) {
		this.imageID = imageID;
	}
	public float getAngle() {
		return angle;
	}
	public void setAngle(float angle) {
		this.angle = angle;
	}
	public float getRot() {
		return rot;
	}
	public void setRot(float rot) {
		this.rot = rot;
	}
	@Override
	public String toString() {
		return "Particle [posX=" + posX + ", posY=" + posY + ", dX=" + dX + ", dY=" + dY + ", life=" + life
				+ ", lifeMax=" + lifeMax + ", imageID=" + imageID + ", angle=" + angle + ", rot=" + rot + "]";
	}
	
	
}
