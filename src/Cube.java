import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Cube implements Comparable<Cube> {

    private Face[] faces;
    private int cubeSize;
    private double heuristic;

    //The solver wont perform this move as is an already cheched state
    String lastMove;
    StringBuilder movesToSolve;
    int movesCount;

    Cube(int cubeSize) {
        this.movesCount = 0;
        this.lastMove = "N";
        this.movesToSolve = new StringBuilder();
        this.heuristic = 0;
        this.cubeSize = cubeSize;
        faces = new Face[6];
        char[] orientations = {'F', 'L', 'R', 'U', 'D', 'B'};
        for(int i = 0; i < 6; i++) {
            Face f = new Face(cubeSize, orientations[i]);
            f.fillFace();
            faces[i] = f;
        }
    }

    Cube() {
        this.movesCount = 0;
        faces = new Face[6];
        this.heuristic = -1;
        this.cubeSize = 0;
        this.lastMove = "N";
        this.movesToSolve = new StringBuilder();
    }

    Cube(Cube c) {
        this.movesCount = 0;
        this.lastMove = c.getLastMove();
        this.movesToSolve = new StringBuilder(c.movesToSolve.toString());
        faces = new Face[6];
        this.cubeSize = c.cubeSize;
        this.heuristic = c.getHeuristic();
        for(int k = 0; k < this.faces.length; k++) {
            this.faces[k] = new Face(c.getFaces()[k].getColors(), c.getFaces()[k].getFaceOrientation(), c.cubeSize, c.getFaces()[k].getZ());
//            for(int i = 0; i < this.cubeSize; i++) {
//                for (int j = 0; j < this.cubeSize; j++) {
//                    this.faces[k].getColors()[i][j] = c.getFaces()[k].getColors()[i][j];
//                }
//            }
        }
    }

    public String getLastMove() {
        return lastMove;
    }

    public void setLastMove(String lastMove) {
        this.lastMove = lastMove;
    }

    public double getHeuristic() {
        return heuristic;
    }

    public void setHeuristic(double heuristic) {
        this.heuristic = heuristic;
    }

    @Override
    public int compareTo(@NotNull Cube c) {
        boolean distinct = false;
        for(int k = 0; k < this.faces.length; k++) {
            for(int i = 0; i < this.cubeSize; i++) {
                for (int j = 0; j < this.cubeSize; j++) {
                    if(this.faces[k].getColors()[i][j].getColor() != c.getFaces()[k].getColors()[i][j].getColor()) distinct = true;
                }
            }
        }
        if(!distinct) return 0;
        if(this.heuristic <= c.heuristic) return -1;
        else return 1;
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
                Face fCopy = new Face(f.getColors(), f.getFaceOrientation(), f.getCubeSize(), f.getZ());
                int iAux = cubeSize;
                int jAux = -1;
                int jAuxPrime = -1;
                int iAuxPrime = cubeSize;
                for(int i = 0; i < cubeSize ; i++) {
                    iAux--;
                    jAuxPrime++;
                    for(int j = 0; j < cubeSize; j++){
                        jAux++;
                        iAuxPrime--;
                        int newZ = f.getZ();
                        if(rotation.isPrime()) {
                            f.getColors()[iAuxPrime][jAuxPrime] = fCopy.getColors()[i][j];
                            f.getColors()[iAuxPrime][jAuxPrime].setNewX(iAuxPrime);
                            f.getColors()[iAuxPrime][jAuxPrime].setNewY(jAuxPrime);
                            f.getColors()[iAuxPrime][jAuxPrime].setNewZ(newZ);
                        }
                        else {
                            f.getColors()[jAux][iAux] = fCopy.getColors()[i][j];
                            f.getColors()[jAux][iAux].setNewX(jAux);
                            f.getColors()[jAux][iAux].setNewY(iAux);
                            f.getColors()[jAux][iAux].setNewZ(newZ);
                        }
                    }
                    jAux = -1;
                    iAuxPrime = cubeSize;
                }
                rotateAdjacents(f, rotation);
            }
        }
    }

    private void updateSquares(Square[][] colors, int i, int j, int k, Square[] newColors, int z) {
        colors[i][j] = newColors[k];
        colors[i][j].setNewX(i);
        colors[i][j].setNewY(j);
        colors[i][j].setNewZ(z);
    }

    @SuppressWarnings("Duplicates")
    private void rotateAdjacents(Face f, Rotation r) {
        Face fLeft = getFaceByOrientation(f.getAdjacentFaces()[0]);
        Face fTop= getFaceByOrientation(f.getAdjacentFaces()[1]);
        Face fRight= getFaceByOrientation(f.getAdjacentFaces()[2]);
        Face fBottom= getFaceByOrientation(f.getAdjacentFaces()[3]);
        Square[] left = new Square[cubeSize];
        Square[] top = new Square[cubeSize];
        Square[] right = new Square[cubeSize];
        Square[] bottom = new Square[cubeSize];
        if(fLeft != null && fTop != null && fRight != null && fBottom != null){
            int j = cubeSize-1;
            int maxSize = cubeSize - 1;
            switch (f.getFaceOrientation()) {
                case 'F':
                    for(int i = 0; i < cubeSize; i++) {
                        left[i] = fLeft.getColors()[maxSize-i][maxSize];
                        top[i] = fTop.getColors()[maxSize][i];
                        right[i] = fRight.getColors()[maxSize-i][0];
                        bottom[i] = fBottom.getColors()[0][i];
                    }
                    if(r.isPrime()) {
                        for (int i = 0; i < cubeSize; i++) {
                            updateSquares(fLeft.getColors(), i, maxSize, j, top, fLeft.getZ());
                            updateSquares(fTop.getColors(), maxSize, i, j, right, fTop.getZ());
                            updateSquares(fRight.getColors(), i, 0, j, bottom, fRight.getZ());
                            updateSquares(fBottom.getColors(), 0, i, j, left, fBottom.getZ());
                            j--;
                        }
                    }
                    else {
                        for (int i = 0; i < cubeSize; i++) {
                            updateSquares(fLeft.getColors(), i, maxSize, i, bottom, fLeft.getZ());
                            updateSquares(fTop.getColors(), maxSize, i, i, left, fTop.getZ());
                            updateSquares(fRight.getColors(), i, 0, i, top, fRight.getZ());
                            updateSquares(fBottom.getColors(), 0, i, i, right, fBottom.getZ());
                        }
                    }
                    break;
                case 'B':
                    for (int i = 0; i < cubeSize; i++) {
                        left[i] = fLeft.getColors()[i][maxSize];
                        top[i] = fTop.getColors()[0][maxSize-i];
                        right[i] = fRight.getColors()[i][0];
                        bottom[i] = fBottom.getColors()[maxSize][maxSize-i];


                    }
                    if(r.isPrime()) {
                        for (int i = 0; i < cubeSize; i++) {

                            updateSquares(fLeft.getColors(), i, maxSize, j, top, fLeft.getZ());
                            updateSquares(fTop.getColors(), 0, i, j, right, fTop.getZ());
                            updateSquares(fRight.getColors(),i, 0, j, bottom, fRight.getZ());
                            updateSquares(fBottom.getColors(), maxSize, i, j, left, fBottom.getZ());
                            j--;

//                            fLeft.getColors()[i][maxSize] = top[j];
//                            fLeft.getColors()[i][maxSize].setNewX(i);
//                            fLeft.getColors()[i][maxSize].setNewY(maxSize);
//                            fLeft.getColors()[i][maxSize].setNewZ(fLeft.getZ());
//
//                            fTop.getColors()[0][i] = right[j];
//                            fTop.getColors()[i][maxSize].setNewX(0);
//                            fTop.getColors()[i][maxSize].setNewY(i);
//                            fTop.getColors()[i][maxSize].setNewZ(fTop.getZ());
//
//                            fRight.getColors()[i][0] = bottom[j];
//                            fBottom.getColors()[maxSize][i] = left[j];
//                            j--;
                        }
                    }
                    else {
                        for (int i = 0; i < cubeSize; i++) {
                            updateSquares(fLeft.getColors(), i, maxSize, i, bottom, fLeft.getZ());
                            updateSquares(fTop.getColors(), 0, i, i, left, fTop.getZ());
                            updateSquares(fRight.getColors(), i, 0, i, top, fRight.getZ());
                            updateSquares(fBottom.getColors(), maxSize, i, i, right, fBottom.getZ());
//                            fLeft.getColors()[i][maxSize] = bottom[i];

//
//                            fTop.getColors()[0][i] = left[i];

//
//                            fRight.getColors()[i][0] = top[i];
//                            fBottom.getColors()[maxSize][i] = right[i];
                        }
                    }
                    break;
                case 'U':
                    for (int i = 0; i < cubeSize; i++) {
                        left[i] = fLeft.getColors()[0][i];
                        top[i] = fTop.getColors()[0][i];
                        right[i] = fRight.getColors()[0][i];
                        bottom[i] = fBottom.getColors()[0][i];
                    }
                    if(r.isPrime()) {
                        for (int i = 0; i < cubeSize; i++) {
                            updateSquares(fLeft.getColors(), 0, i, i, top, fLeft.getZ());
                            updateSquares(fTop.getColors(), 0, i, i, right, fTop.getZ());
                            updateSquares(fRight.getColors(),0, i, i, bottom, fRight.getZ());
                            updateSquares(fBottom.getColors(), 0, i, i, left, fBottom.getZ());

//                            fLeft.getColors()[0][i] = top[i];

//
//                            fTop.getColors()[0][i] = right[i];

//
//                            fRight.getColors()[0][i] = bottom[i];
//                            fBottom.getColors()[0][i] = left[i];
                        }
                    }
                    else {
                        for (int i = 0; i < cubeSize; i++) {
                            updateSquares(fLeft.getColors(), 0, i, i, bottom, fLeft.getZ());
                            updateSquares(fTop.getColors(), 0, i, i, left, fTop.getZ());
                            updateSquares(fRight.getColors(), 0, i, i, top, fRight.getZ());
                            updateSquares(fBottom.getColors(), 0, i, i, right, fBottom.getZ());
//                            fLeft.getColors()[0][i] = bottom[i];

//
//                            fTop.getColors()[0][i] = left[i];

//
//                            fRight.getColors()[0][i] = top[i];
//
//
//                            fBottom.getColors()[0][i] = right[i];


                        }
                    }
                    break;
                case 'L':
                    for (int i = 0; i < cubeSize; i++) {
                        left[i] = fLeft.getColors()[maxSize-i][maxSize];
                        top[i] = fTop.getColors()[i][0];
                        right[i] = fRight.getColors()[i][0];
                        bottom[i] = fBottom.getColors()[maxSize-i][0];


                    }
                    if(r.isPrime()) {
                        for (int i = 0; i < cubeSize; i++) {
                            updateSquares(fLeft.getColors(), i, maxSize, j, top, fLeft.getZ());
                            updateSquares(fTop.getColors(), i, 0, i, right, fTop.getZ());
                            updateSquares(fRight.getColors(), i, 0, j, bottom, fRight.getZ());
                            updateSquares(fBottom.getColors(), i, 0, i, left, fBottom.getZ());
//                            fLeft.getColors()[i][maxSize] = top[j];

//
//                            fTop.getColors()[i][0] = right[i];

//
//                            fRight.getColors()[i][0] = bottom[j];
//
//
//                            fBottom.getColors()[i][0] = left[i];


                            j--;
                        }
                    }
                    else {
                        for (int i = 0; i < cubeSize; i++) {
                            updateSquares(fLeft.getColors(), i, maxSize, i, bottom, fLeft.getZ());
                            updateSquares(fTop.getColors(), i, 0, i, left, fTop.getZ());
                            updateSquares(fRight.getColors(), i, 0, i, top, fRight.getZ());
                            updateSquares(fBottom.getColors(), i, 0, i, right, fBottom.getZ());
//                            fLeft.getColors()[i][maxSize] = bottom[i];

//
//                            fTop.getColors()[i][0] = left[i];

//
//                            fRight.getColors()[i][0] = top[i];
//
//
//                            fBottom.getColors()[i][0] = right[i];


                        }
                    }
                    break;
                case 'R':
                    for (int i = 0; i < cubeSize; i++) {
                        left[i] = fLeft.getColors()[i][maxSize];
                        top[i] = fTop.getColors()[maxSize-i][maxSize];
                        right[i] = fRight.getColors()[maxSize-i][0];
                        bottom[i] = fBottom.getColors()[i][maxSize];
                    }
                    if(r.isPrime()) {
                        for (int i = 0; i < cubeSize; i++) {
                            updateSquares(fLeft.getColors(), i, maxSize, j, top, fLeft.getZ());
                            updateSquares(fTop.getColors(), i, maxSize, i, right, fTop.getZ());
                            updateSquares(fRight.getColors(), i, 0, j, bottom, fRight.getZ());
                            updateSquares(fBottom.getColors(), i, maxSize, i, left, fBottom.getZ());
//                            fLeft.getColors()[i][maxSize] = top[j];

//
//                            fTop.getColors()[i][maxSize] = right[i];

//
//                            fRight.getColors()[i][0] = bottom[j];
//
//
//                            fBottom.getColors()[i][maxSize] = left[i];


                            j--;
                        }
                    }
                    else {
                        for (int i = 0; i < cubeSize; i++) {
                            updateSquares(fLeft.getColors(), i, maxSize, i, bottom, fLeft.getZ());
                            updateSquares(fTop.getColors(), i, maxSize, i, left, fTop.getZ());
                            updateSquares(fRight.getColors(), i, 0, i, top, fRight.getZ());
                            updateSquares(fBottom.getColors(), i, maxSize, i, right, fBottom.getZ());
//                            fLeft.getColors()[i][maxSize] = bottom[i];

//
//                            fTop.getColors()[i][maxSize] = left[i];

//
//                            fRight.getColors()[i][0] = top[i];
//
//
//                            fBottom.getColors()[i][maxSize] = right[i];


                        }
                    }
                    break;
                case 'D':
                    for (int i = 0; i < cubeSize; i++) {
                        left[i] = fLeft.getColors()[maxSize][i];
                        top[i] = fTop.getColors()[maxSize][i];
                        right[i] = fRight.getColors()[maxSize][i];
                        bottom[i] = fBottom.getColors()[maxSize][i];
                    }
                    if(r.isPrime()) {
                        for (int i = 0; i < cubeSize; i++) {
                            updateSquares(fLeft.getColors(), maxSize, i, i, top, fLeft.getZ());
                            updateSquares(fTop.getColors(), maxSize, i, i, right, fTop.getZ());
                            updateSquares(fRight.getColors(), maxSize, i, i, bottom, fRight.getZ());
                            updateSquares(fBottom.getColors(), maxSize, i, i, left, fBottom.getZ());
//                            fLeft.getColors()[maxSize][i] = top[i];
//
//
//                            fTop.getColors()[maxSize][i] = right[i];

//
//                            fRight.getColors()[maxSize][i] = bottom[i];
//
//
//                            fBottom.getColors()[maxSize][i] = left[i];


                        }
                    }
                    else {
                        for (int i = 0; i < cubeSize; i++) {
                            updateSquares(fLeft.getColors(), maxSize, i, i, bottom, fLeft.getZ());
                            updateSquares(fTop.getColors(), maxSize, i, i, left, fTop.getZ());
                            updateSquares(fRight.getColors(),maxSize, i, i, top, fRight.getZ());
                            updateSquares(fBottom.getColors(),  maxSize, i, i, right, fBottom.getZ());
//                            fLeft.getColors()[maxSize][i] = bottom[i];
//
//
//                            fTop.getColors()[maxSize][i] = left[i];
//                            fTop.getColors()[i][maxSize].setNewX(maxSize);
//                            fTop.getColors()[i][maxSize].setNewY(i);
//                            fTop.getColors()[i][maxSize].setNewZ(fTop.getZ());
//
//                            fRight.getColors()[maxSize][i] = top[i];
//
//
//                            fBottom.getColors()[maxSize][i] = right[i];


                        }
                    }
                    break;
            }
        }
    }

    public Face[] getFaces() {
        return faces;
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

    public boolean isSolved() {
        Cube solvedStatus = new Cube(cubeSize);
        for(int k = 0; k < 6; k++) {
            for(int i = 0; i < cubeSize; i++) {
                for (int j = 0; j < cubeSize; j++) {
                    if (solvedStatus.getFaces()[k].getColors()[i][j].getColor() != this.getFaces()[k].getColors()[i][j].getColor()) return false;
                }
            }
        }
        return true;
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
                    center.append("|").append(l.getColors()[num][i].getColor());
                }
                center.append("|   ");
                for(int i = 0; i < cubeSize; i++) {
                    center.append("|").append(f.getColors()[num][i].getColor());
                }
                center.append("|   ");
                for(int i = 0; i < cubeSize; i++) {
                    center.append("|").append(r.getColors()[num][i].getColor());
                }
                center.append("|   ");
                for(int i = 0; i < cubeSize; i++) {
                    center.append("|").append(b.getColors()[num][i].getColor());
                }
                center.append("|\n");
            }

            return u.toString()  + center.toString() + "\n" + d.toString() + "=====================================\n\n";
        }
        return "Wrong cube formation";
    }
}
