import java.util.ArrayList;
import java.util.List;

public class Cube {

    private Face[] faces;
    private int cubeSize;

    Cube(int cubeSize) {
        this.cubeSize = cubeSize;
        faces = new Face[6];
        for(int i = 0; i < 6; i++) {
            char[] orientations = {'F', 'L', 'R', 'U', 'D', 'B'};
            Face f = new Face(cubeSize, orientations[i]);
            f.fillFace();
            faces[i] = f;
        }
    }


    void scramble(String r) {
        //ex: F'LR2F2B'
        List<Rotation> rotations = new ArrayList<>();
        r = r.toUpperCase();
        String[] rs = r.split("\\s+(?=([FLRUDB][2']?))");
        for(String rotationCase : rs) {
            Rotation rotation;
            if(rotationCase.length() == 1) {
                rotation = new Rotation(rotationCase.charAt(0));
            }
            else if(rotationCase.length() == 2) {
                if(rotationCase.charAt(1) == '\'') {
                    rotation = new Rotation(rotationCase.charAt(0), true);
                }
                else {
                    rotation = new Rotation(rotationCase.charAt(0), 2);
                }
            }
            else  {
                rotation = new Rotation(rotationCase.charAt(0), true , 2);
            }
            rotations.add(rotation);
        }
        for(Rotation rot : rotations) {
            rotate(rot);
        }
    }

    private void rotate(Rotation rotation) {
        Face f = getFaceByOrientation(rotation.getOrientation());
        if(f != null) {
            for(int k = 0; k < rotation.getN(); k++) {
                Face fCopy = new Face(f.getColors(), f.getFaceOrientation(), f.getCubeSize());
                int iAux = 3;
                int jAux = -1;
                int jAuxPrime = -1;
                int iAuxPrime = 3;
                for(int i = 0; i < cubeSize ; i++) {
                    iAux--;
                    jAuxPrime++;
                    for(int j = 0; j < cubeSize; j++){
                        jAux++;
                        iAuxPrime--;
                        if(rotation.isPrime()) {
                            f.getColors()[iAuxPrime][jAuxPrime] = fCopy.getColors()[i][j];
                        }
                        else {
                            f.getColors()[jAux][iAux] = fCopy.getColors()[i][j];
                        }
                    }
                    jAux = -1;
                    iAuxPrime = 3;
                }
                rotateAdjacents(f, rotation);
            }
        }
    }

    private void rotateAdjacents(Face f, Rotation r) {
        Face fLeft = getFaceByOrientation(f.getAdjacentFaces()[0]);
        Face fTop= getFaceByOrientation(f.getAdjacentFaces()[1]);
        Face fRight= getFaceByOrientation(f.getAdjacentFaces()[2]);
        Face fBottom= getFaceByOrientation(f.getAdjacentFaces()[3]);
        char[] left;
        char[] top;
        char[] right;
        char[] bottom;
        if(fLeft != null && fTop != null && fRight != null && fBottom != null){
            switch (f.getFaceOrientation()) {
                case 'F':
                    left = new char[]{fLeft.getColors()[2][2], fLeft.getColors()[1][2], fLeft.getColors()[0][2]};
                    top = new char[]{fTop.getColors()[2][0], fTop.getColors()[2][1], fTop.getColors()[2][2]};
                    right = new char[]{fRight.getColors()[2][0], fRight.getColors()[1][0], fRight.getColors()[0][0]};
                    bottom = new char[]{fBottom.getColors()[0][0], fBottom.getColors()[0][1], fBottom.getColors()[0][2]};
                    if(r.isPrime()) {
                        int j = 2;
                        for (int i = 0; i < 3; i++) {
                            fLeft.getColors()[i][2] = top[j];
                            fTop.getColors()[2][i] = right[j];
                            fRight.getColors()[i][0] = bottom[j];
                            fBottom.getColors()[0][i] = left[j];
                            j--;
                        }
                    }
                    else {
                        for (int i = 0; i < 3; i++) {
                            fLeft.getColors()[i][2] = bottom[i];
                            fTop.getColors()[2][i] = left[i];
                            fRight.getColors()[i][0] = top[i];
                            fBottom.getColors()[0][i] = right[i];
                        }
                    }
                    break;
                case 'B':
                    left = new char[]{fLeft.getColors()[0][2], fLeft.getColors()[1][2], fLeft.getColors()[2][2]};
                    top = new char[]{fTop.getColors()[0][2], fTop.getColors()[0][1], fTop.getColors()[0][0]};
                    right = new char[]{fRight.getColors()[0][0], fRight.getColors()[1][0], fRight.getColors()[2][0]};
                    bottom = new char[]{fBottom.getColors()[2][2], fBottom.getColors()[2][1], fBottom.getColors()[2][0]};
                    if(r.isPrime()) {
                        int j = 2;
                        for (int i = 0; i < 3; i++) {
                            fLeft.getColors()[i][2] = top[j];
                            fTop.getColors()[0][i] = right[j];
                            fRight.getColors()[i][0] = bottom[j];
                            fBottom.getColors()[2][i] = left[j];
                            j--;
                        }
                    }
                    else {
                        for (int i = 0; i < 3; i++) {
                            fLeft.getColors()[i][2] = bottom[i];
                            fTop.getColors()[0][i] = left[i];
                            fRight.getColors()[i][0] = top[i];
                            fBottom.getColors()[2][i] = right[i];
                        }
                    }
                    break;
                case 'U':
                    left = new char[]{fLeft.getColors()[0][0], fLeft.getColors()[0][1], fLeft.getColors()[0][2]};
                    top = new char[]{fTop.getColors()[0][0], fTop.getColors()[0][1], fTop.getColors()[0][2]};
                    right = new char[]{fRight.getColors()[0][0], fRight.getColors()[0][1], fRight.getColors()[0][2]};
                    bottom = new char[]{fBottom.getColors()[0][0], fBottom.getColors()[0][1], fBottom.getColors()[0][2]};
                    if(r.isPrime()) {
                        for (int i = 0; i < 3; i++) {
                            fLeft.getColors()[0][i] = top[i];
                            fTop.getColors()[0][i] = right[i];
                            fRight.getColors()[0][i] = bottom[i];
                            fBottom.getColors()[0][i] = left[i];
                        }
                    }
                    else {
                        for (int i = 0; i < 3; i++) {
                            fLeft.getColors()[0][i] = bottom[i];
                            fTop.getColors()[0][i] = left[i];
                            fRight.getColors()[0][i] = top[i];
                            fBottom.getColors()[0][i] = right[i];
                        }
                    }
                    break;
                case 'L':
                    left = new char[]{fLeft.getColors()[2][2], fLeft.getColors()[1][2], fLeft.getColors()[0][2]};
                    top = new char[]{fTop.getColors()[0][0], fTop.getColors()[1][0], fTop.getColors()[2][0]};
                    right = new char[]{fRight.getColors()[0][0], fRight.getColors()[1][0], fRight.getColors()[2][0]};
                    bottom = new char[]{fBottom.getColors()[2][0], fBottom.getColors()[1][0], fBottom.getColors()[0][0]};
                    if(r.isPrime()) {
                        int j = 2;
                        for (int i = 0; i < 3; i++) {
                            fLeft.getColors()[i][2] = top[j];
                            fTop.getColors()[i][0] = right[i];
                            fRight.getColors()[i][0] = bottom[j];
                            fBottom.getColors()[i][0] = left[i];
                            j--;
                        }
                    }
                    else {
                        for (int i = 0; i < 3; i++) {
                            fLeft.getColors()[i][2] = bottom[i];
                            fTop.getColors()[i][0] = left[i];
                            fRight.getColors()[i][0] = top[i];
                            fBottom.getColors()[i][0] = right[i];
                        }
                    }
                    break;
                case 'R':
                    left = new char[]{fLeft.getColors()[0][2], fLeft.getColors()[1][2], fLeft.getColors()[2][2]};
                    top = new char[]{fTop.getColors()[2][2], fTop.getColors()[1][2], fTop.getColors()[0][2]};
                    right = new char[]{fRight.getColors()[2][0], fRight.getColors()[1][0], fRight.getColors()[0][0]};
                    bottom = new char[]{fBottom.getColors()[0][2], fBottom.getColors()[1][2], fBottom.getColors()[2][2]};
                    if(r.isPrime()) {
                        int j = 2;
                        for (int i = 0; i < 3; i++) {
                            fLeft.getColors()[i][2] = top[j];
                            fTop.getColors()[i][2] = right[i];
                            fRight.getColors()[i][0] = bottom[j];
                            fBottom.getColors()[i][2] = left[i];
                            j--;
                        }
                    }
                    else {
                        for (int i = 0; i < 3; i++) {
                            fLeft.getColors()[i][2] = bottom[i];
                            fTop.getColors()[i][2] = left[i];
                            fRight.getColors()[i][0] = top[i];
                            fBottom.getColors()[i][2] = right[i];
                        }
                    }
                    break;
                case 'D':
                    left = new char[]{fLeft.getColors()[2][0], fLeft.getColors()[2][1], fLeft.getColors()[2][2]};
                    top = new char[]{fTop.getColors()[2][0], fTop.getColors()[2][1], fTop.getColors()[2][2]};
                    right = new char[]{fRight.getColors()[2][0], fRight.getColors()[2][1], fRight.getColors()[2][2]};
                    bottom = new char[]{fBottom.getColors()[2][0], fBottom.getColors()[2][1], fBottom.getColors()[2][2]};
                    if(r.isPrime()) {
                        for (int i = 0; i < 3; i++) {
                            fLeft.getColors()[2][i] = top[i];
                            fTop.getColors()[2][i] = right[i];
                            fRight.getColors()[2][i] = bottom[i];
                            fBottom.getColors()[2][i] = left[i];
                        }
                    }
                    else {
                        for (int i = 0; i < 3; i++) {
                            fLeft.getColors()[2][i] = bottom[i];
                            fTop.getColors()[2][i] = left[i];
                            fRight.getColors()[2][i] = top[i];
                            fBottom.getColors()[2][i] = right[i];
                        }
                    }
                    break;
            }
        }
    }

    private Face getFaceByOrientation(char orientation) {
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
        StringBuilder center = new StringBuilder();

        Face l = getFaceByOrientation('L');
        Face f = getFaceByOrientation('F');
        Face r = getFaceByOrientation('R');
        Face b = getFaceByOrientation('B');

        Face u = getFaceByOrientation('U');
        Face d = getFaceByOrientation('D');

        if(l != null && f != null && r != null && b != null && u != null && d != null) {
            for(int num = 0; num < cubeSize; num++) {
                for(int i = 0; i < cubeSize; i++) {
                    center.append("|").append(l.getColors()[num][i]);
                }
                center.append("|   ");
                for(int i = 0; i < cubeSize; i++) {
                    center.append("|").append(f.getColors()[num][i]);
                }
                center.append("|   ");
                for(int i = 0; i < cubeSize; i++) {
                    center.append("|").append(r.getColors()[num][i]);
                }
                center.append("|   ");
                for(int i = 0; i < cubeSize; i++) {
                    center.append("|").append(b.getColors()[num][i]);
                }
                center.append("|\n");
            }

            return u.toString()  + center.toString() + "\n" + d.toString() + "=====================================\n\n";
        }
        return "Wrong cube formation";
    }
}
