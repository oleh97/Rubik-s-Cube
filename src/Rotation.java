public class Rotation {

    private char orientation;
    private char prime;
    private int n;

    public Rotation(char orientation) {
        this.orientation = orientation;
        this.prime = 0;
        this.n = 1;
    }

    public Rotation(char orientation, char prime) {
        this.orientation = orientation;
        this.prime = prime;
        this.n = 1;
    }

    public Rotation(char orientation, int n) {
        this.orientation = orientation;
        this.n = n;
        this.prime = 0;
    }

    public Rotation(char orientation, char prime, int n) {
        this.orientation = orientation;
        this.prime = prime;
        this.n = n;
    }

    public char getOrientation() {
        return orientation;
    }

    public void setOrientation(char orientation) {
        this.orientation = orientation;
    }

    public char getPrime() {
        return prime;
    }

    public void setPrime(char prime) {
        this.prime = prime;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }
}
