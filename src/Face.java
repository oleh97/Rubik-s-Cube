import java.util.zip.CheckedOutputStream;

public class Face {

    private Square[][] colors;
    private char faceOrientation;
    private int cubeSize;
    private char[] adjacentFaces;
    private int z;

    Face(int cubeSize, char faceOrientation) {
        this.colors = new Square[cubeSize][cubeSize];
        this.faceOrientation = faceOrientation;
        this.cubeSize = cubeSize;
        switch (faceOrientation) {
            case 'F':
                z = 0;
                adjacentFaces = new char[]{'L','U','R','D'};
                break;
            case 'L':
                z = 1;
                adjacentFaces = new char[]{'B','U','F','D'};
                break;
            case 'R':
                z = 2;
                adjacentFaces = new char[]{'F','U','B','D'};
                break;
            case 'U':
                z = 3;
                adjacentFaces = new char[]{'L','B','R','F'};
                break;
            case 'D':
                z = 4;
                adjacentFaces = new char[]{'L','F','R','B'};
                break;
            case 'B':
                z = 5;
                adjacentFaces = new char[]{'R','U','L','D'};
                break;
        }
    }

    Face(Square[][] colors, char faceOrientation, int cubeSize, int z) {
        this.colors = new Square[cubeSize][cubeSize];
        this.z = z;
//        for(int i = 0; i < cubeSize; i++) {
//            System.arraycopy(colors[i], 0, this.colors[i], 0, cubeSize);
//        }
        for(int i = 0; i < cubeSize; i++) {
            for(int j = 0; j < cubeSize; j++) {
                this.colors[i][j] = new Square(colors[i][j].getX(), colors[i][j].getY(), colors[i][j].getZ(), colors[i][j].getColor());
                this.colors[i][j].setNewX(colors[i][j].getNewX());
                this.colors[i][j].setNewY(colors[i][j].getNewY());
                this.colors[i][j].setNewZ(colors[i][j].getNewZ());
            }
        }
        this.faceOrientation = faceOrientation;
        this.cubeSize = cubeSize;
        switch (faceOrientation) {
            case 'F':
                this.z = 0;
                adjacentFaces = new char[]{'L','U','R','D'};
                break;
            case 'L':
                this.z = 1;
                adjacentFaces = new char[]{'B','U','F','D'};
                break;
            case 'R':
                this.z = 2;
                adjacentFaces = new char[]{'F','U','B','D'};
                break;
            case 'U':
                this.z = 3;
                adjacentFaces = new char[]{'L','B','R','F'};
                break;
            case 'D':
                this.z = 4;
                adjacentFaces = new char[]{'L','F','R','B'};
                break;
            case 'B':
                this.z = 5;
                adjacentFaces = new char[]{'R','U','L','D'};
                break;
        }
    }

    void fillFace() {
        char color = getColor();
        for(int i = 0 ; i < cubeSize; i++) {
            for(int j = 0; j < cubeSize; j++) {
                colors[i][j] = new Square(i,j, this.z, color);
            }
        }
    }

    public int getZ() {
        return this.z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    char[] getAdjacentFaces() {
        return adjacentFaces;
    }

    public char getColor() {
        switch (faceOrientation) {
            case 'F':
                return 'G';
            case 'L':
                return 'O';
            case 'R':
                return 'R';
            case 'U':
                return 'W';
            case 'D':
                return 'Y';
            case 'B':
                return 'B';
        }
        return '-';
    }

    Square[][] getColors() {
        return colors;
    }

    public void setColors(Square[][] newColors) {
        this.colors = newColors;
    }

    char getFaceOrientation() {
        return faceOrientation;
    }

    int getCubeSize() {
        return cubeSize;
    }

    @Override
    public String toString() {

        StringBuilder face = new StringBuilder("          |");
        for(Square[] colorRow : colors) {
            for(Square color : colorRow) {
                String line = color.getColor() +"|";
                face.append(line);
            }
            face.append("\n          |");
        }
        face.deleteCharAt(face.lastIndexOf("|"));

        return face.toString() + "\n";
    }
}
