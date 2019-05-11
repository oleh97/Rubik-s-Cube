public class Rotation {

    private char orientation;
    private boolean isPrime;
    private int n;

    Rotation(char orientation) {
        this.orientation = orientation;
        this.isPrime = false;
        this.n = 1;
    }

    Rotation(char orientation, boolean isPrime) {
        this.orientation = orientation;
        this.isPrime = isPrime;
        this.n = 1;
    }

    Rotation(char orientation, int n) {
        this.orientation = orientation;
        this.n = n;
        this.isPrime = false;
    }

    Rotation(char orientation, boolean isPrime, int n) {
        this.orientation = orientation;
        this.isPrime = isPrime;
        this.n = n;
    }

    char getOrientation() {
        return orientation;
    }

    public void setOrientation(char orientation) {
        this.orientation = orientation;
    }

    boolean isPrime() {
        return isPrime;
    }

    public void setIsPrime(boolean isPrime) {
        this.isPrime = isPrime;
    }

    int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }
}
