import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Cube {

    private Face[] faces;
    private int cubeSize;
    private final char[] orientations = {'F', 'L', 'R', 'U', 'D', 'B'};

    public Cube(int cubeSize) {
        this.cubeSize = cubeSize;
        faces = new Face[6];
        for(int i = 0; i < 6; i++) {
            Face f = new Face(cubeSize, orientations[i]);
            f.fillFace();
            faces[i] = f;
        }
    }


    public void rotate(char face) {
        Face f = getFaceByOrientation(face);
        Face fCopy = new Face(f.getColors(), f.getFaceOrientation(), f.getCubeSize());
        int iAux = 3;
        int jAux = -1;
        for(int i = 0; i < cubeSize ; i++) {
            iAux--;
            for(int j = 0; j < cubeSize; j++){
                jAux++;
                f.getColors()[jAux][iAux] = fCopy.getColors()[i][j];
            }
            jAux = -1;
        }
        rotateAdjacents(f);
    }

    public void rotateAdjacents(Face f) {
        Face fLeft = getFaceByOrientation(f.getAdjacentFaces()[0]);
        Face fTop= getFaceByOrientation(f.getAdjacentFaces()[1]);
        Face fRight= getFaceByOrientation(f.getAdjacentFaces()[2]);
        Face fBottom= getFaceByOrientation(f.getAdjacentFaces()[3]);
        switch (f.getFaceOrientation()) {
            case 'F':
                char[] left = new char[]{fLeft.getColors()[2][2],fLeft.getColors()[1][2],fLeft.getColors()[0][2]};
                char[] top = new char[]{fTop.getColors()[2][0],fTop.getColors()[2][1],fTop.getColors()[2][2]};
                char[] right = new char[]{fRight.getColors()[2][0],fRight.getColors()[1][0],fRight.getColors()[0][0]};
                char[] bottom = new char[]{fBottom.getColors()[0][0],fBottom.getColors()[0][1],fBottom.getColors()[0][2]};

                for(int i = 0; i<3; i++) {
                    fLeft.getColors()[i][2] = bottom[i];
                    fTop.getColors()[2][i] = left[i];
                    fRight.getColors()[i][0] = top[i];
                    fBottom.getColors()[0][i] = right[i];
                }
                break;
            case 'B':
                left = new char[]{fLeft.getColors()[0][2],fLeft.getColors()[1][2],fLeft.getColors()[2][2]};
                top = new char[]{fTop.getColors()[0][2],fTop.getColors()[0][1],fTop.getColors()[0][0]};
                right = new char[]{fRight.getColors()[0][0],fRight.getColors()[1][0],fRight.getColors()[2][0]};
                bottom = new char[]{fBottom.getColors()[2][2],fBottom.getColors()[2][1],fBottom.getColors()[2][0]};
                for(int i = 0; i<3; i++) {
                    fLeft.getColors()[i][2] = bottom[i];
                    fTop.getColors()[0][i] = left[i];
                    fRight.getColors()[i][0] = top[i];
                    fBottom.getColors()[2][i] = right[i];
                }
                break;
            case 'U':
                left = new char[]{fLeft.getColors()[0][0],fLeft.getColors()[0][1],fLeft.getColors()[0][2]};
                top = new char[]{fTop.getColors()[0][0],fTop.getColors()[0][1],fTop.getColors()[0][2]};
                right = new char[]{fRight.getColors()[0][0],fRight.getColors()[0][1],fRight.getColors()[0][2]};
                bottom = new char[]{fBottom.getColors()[0][0],fBottom.getColors()[0][1],fBottom.getColors()[0][2]};
                for(int i = 0; i<3; i++) {
                    fLeft.getColors()[0][i] = bottom[i];
                    fTop.getColors()[0][i] = left[i];
                    fRight.getColors()[0][i] = top[i];
                    fBottom.getColors()[0][i] = right[i];
                }
                break;
        }
    }

    public Face getFaceByOrientation(char orientation) {
        switch (orientation) {
            case 'F':
                return faces[0];
            case 'L':
                return faces[1];
            case 'R':
                return faces[2];
            case 'U':
                return faces[3];
            case 'D':
                return faces[4];
            case 'B':
                return faces[5];
        }
        return null;
    }

    @Override
    public String toString() {
        List<StringBuilder> facesToString = new ArrayList<>();

        String center = "";

        Face l = getFaceByOrientation('L');
        Face f = getFaceByOrientation('F');
        Face r = getFaceByOrientation('R');
        Face b = getFaceByOrientation('B');

        Face u = getFaceByOrientation('U');
        Face d = getFaceByOrientation('D');

        for(int num = 0; num < cubeSize; num++) {
            for(int i = 0; i < cubeSize; i++) {
                center += "|" + l.getColors()[num][i];
            }
            center += "|   ";
            for(int i = 0; i < cubeSize; i++) {
                center += "|" + f.getColors()[num][i];
            }
            center += "|   ";
            for(int i = 0; i < cubeSize; i++) {
                center += "|" + r.getColors()[num][i];
            }
            center += "|   ";
            for(int i = 0; i < cubeSize; i++) {
                center += "|" + b.getColors()[num][i];
            }
            center += "|\n";
        }

        return u.toString() + "\n" + center + "\n" + d.toString();
    }
}
