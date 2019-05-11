public class Face {

    private char[][] colors;
    private char faceOrientation;
    private int cubeSize;
    private char[] adjacentFaces;

    Face(int cubeSize, char faceOrientation) {
        this.colors = new char[cubeSize][cubeSize];
        this.faceOrientation = faceOrientation;
        this.cubeSize = cubeSize;
        switch (faceOrientation) {
            case 'F':
                adjacentFaces = new char[]{'L','U','R','D'};
                break;
            case 'L':
                adjacentFaces = new char[]{'B','U','F','D'};
                break;
            case 'R':
                adjacentFaces = new char[]{'F','U','B','D'};
                break;
            case 'U':
                adjacentFaces = new char[]{'L','B','R','F'};
                break;
            case 'D':
                adjacentFaces = new char[]{'L','F','R','B'};
                break;
            case 'B':
                adjacentFaces = new char[]{'R','U','L','D'};
                break;
        }
    }

    Face(char[][] colors, char faceOrientation, int cubeSize) {
        this.colors = new char[cubeSize][cubeSize];
        for(int i = 0; i < cubeSize; i++) {
            System.arraycopy(colors[i], 0, this.colors[i], 0, cubeSize);
        }
        this.faceOrientation = faceOrientation;
        this.cubeSize = cubeSize;
    }

    void fillFace() {
        char color = getColor();
        for(int i = 0 ; i < cubeSize; i++) {
            for(int j = 0; j < cubeSize; j++) {
                colors[i][j] = color;
            }
        }
    }

    char[] getAdjacentFaces() {
        return adjacentFaces;
    }

    private char getColor() {
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

    char[][] getColors() {
        return colors;
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
        for(char[] colorRow : colors) {
            for(char color : colorRow) {
                String line = color + "|";
                face.append(line);
            }
            face.append("\n          |");
        }
        face.deleteCharAt(face.lastIndexOf("|"));

        return face.toString() + "\n";
    }
}
