public class Square {

    private char color;
    private int x;
    private int y;
    private int z;

    private int newX;
    private int newY;
    private int newZ;

    public Square(int x, int y, int z, char color) {
        this.x = x;
        this.y = y;
        this.z = z;

        this.newX = -1;
        this.newY = -1;
        this.newZ = -1;

        this.color = color;
    }

    public int manhattan() {
        int sumX = 0;
        int sumY = 0;
        int sumZ = 0;
        if(this.newX != -1) {
            sumX = Math.abs(this.x - this.newX);
        }
        if(this.newY != -1) {
            sumY = Math.abs(this.y - this.newY);
        }
        if(this.newZ != -1) {
            sumZ = Math.abs(this.z - this.newZ);
        }

        return (sumX+sumY+sumZ);
    }

    public int getNewX() {
        return newX;
    }

    public void setNewX(int newX) {
        this.newX = newX;
    }

    public int getNewY() {
        return newY;
    }

    public void setNewY(int newY) {
        this.newY = newY;
    }

    public int getNewZ() {
        return newZ;
    }

    public void setNewZ(int newZ) {
        this.newZ = newZ;
    }

    public char getColor() {
        return color;
    }

    public void setColor(char color) {
        this.color = color;
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

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }
}
