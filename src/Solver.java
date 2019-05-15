import jdk.swing.interop.SwingInterOpUtils;

import java.util.*;
import java.util.function.BinaryOperator;

public class Solver {

    static String[] moves = {"F", "L", "R", "U", "D", "B", "F2", "L2", "R2", "U2", "D2", "B2", "F'", "L'", "R'", "U'", "D'", "B'"};
    static Random random = new Random();
    static TreeSet<Cube> alreadyVisited = new TreeSet<>();

    static String solve(Cube cube) {
        TreeSet<Cube> toExplore = new TreeSet<>();
//        TreeSet<Cube> explored = new TreeSet<>();
        cube.setHeuristic(manhattanDistance(cube));
        double threshold = cube.getHeuristic();
        double newThreshold = threshold;
        boolean changed = false;
        toExplore.add(cube);
        while (true) {
            while (!toExplore.isEmpty()) {
                Cube c = toExplore.first();
                toExplore.remove(c);
                if(c.isSolved()) return c.movesToSolve.toString();
                if(c.movesCount <= 20) {
//                System.out.println(c.movesCount);
                    TreeSet<Cube> newNodes = generateCubeStates(c);
                    while (!newNodes.isEmpty()) {
                        if(newNodes.first().getHeuristic() < threshold) {
                            toExplore.add(newNodes.first());
                        }
                        else if(newNodes.first().getHeuristic() > newThreshold && !changed){
                            newThreshold = newNodes.first().getHeuristic();
                            changed = true;
                            newNodes.clear();
                        }
//                        else newNodes.clear();
                        if(!newNodes.isEmpty()) newNodes.remove(newNodes.first());
                    }
//                toExplore.addAll(generateCubeStates(c));
                }
//                alreadyVisited.add(c);
//            explored.add(c);
//            System.out.println((c.getHeuristic()));
            }
            threshold = newThreshold;
            toExplore = new TreeSet<>();
            toExplore.add(cube);
            changed = false;
        }
//        }
    }

    private static TreeSet<Cube> generateCubeStates(Cube cube) {
        TreeSet<Cube> s = new TreeSet<>();
        String aux;
        for (int i = 0; i < moves.length; i++) {
            Cube b = new Cube(cube);
            if(cube.getLastMove() != null) {
                if(cube.getLastMove().length() > 1) {
                    aux = String.valueOf(cube.getLastMove().charAt(0));
                }
                else {
                    aux = cube.getLastMove() + "\'";
                }
                if (!moves[i].equals(aux)) {
                    b.scramble(moves[i]);
                    b.setLastMove(moves[i]);
                    b.movesToSolve.append(moves[i]).append(" ");
                    b.movesCount = cube.movesCount +1;
                    double mh = manhattanDistance(b);
//                    System.out.println("^Depth: "+b.movesCount+"| "+ moves[i] + "|" + mh);
                    b.setHeuristic(mh + b.movesCount);
                    s.add(b);
                }
            }
        }
        return s;
    }

    private static int checkMovesToPlaceCorner(Square front, Square side, Square cover) {
        int neighbours = 0;
        int oriented = 0;
        if(front.getCurrentFace() != front.getOpositeFace()) neighbours++;
        if(side.getCurrentFace() != side.getOpositeFace()) neighbours++;
        if(cover.getCurrentFace() != cover.getOpositeFace()) neighbours++;

        if(front.getColor() == front.getCurrentFace()) oriented++;
        if(side.getColor() == side.getCurrentFace()) oriented++;
        if(cover.getColor() == cover.getCurrentFace()) oriented++;

        if(neighbours == 3 && oriented == 3) return 0;
        if(neighbours >= 2 && oriented >= 1) return 1;
        if(oriented == 0 && neighbours == 3) return 2;
        if(oriented >= 1 && neighbours == 0) return 2;
        if(oriented == 1 && neighbours == 1) return 2;
        if(oriented == 0 && neighbours == 0) return 3;
        if(oriented == 0 && neighbours == 2) return 3;
        return 0;
    }

    private static int checkMovesToPlaceEdge(Square front, Square opposite) {
        int neighbours = 0;
        int oriented = 0;
        if(front.getCurrentFace() != front.getOpositeFace()) neighbours++;
        if(opposite.getCurrentFace() != opposite.getOpositeFace()) neighbours++;

        if(front.getColor() == front.getCurrentFace()) oriented++;
        if(opposite.getColor() == opposite.getCurrentFace()) oriented++;

        if(neighbours == 2 && oriented == 1) return 1;
        if(neighbours == 2 && oriented == 0)
            if(front.getNewX() == front.getX() && front.getNewY() == front.getNewY())return 2;
            else return 3;
        if(oriented ==1 && neighbours == 1) return 2;
        if(neighbours == 0) return 4;
        if(neighbours == 1 && oriented == 0) return 3;
        return 0;
    }

    public static double manhattanDistance(Cube c) {
        double sumCorners = 0;
        double sumEdges = 0;
        for(Face f : c.getFaces()) {
            for(Square[] s : f.getColors()) {
                for (Square sq : s) {
                    //8 corners
                    if ((f.getColor() == 'G' || f.getColor() == 'B') && (Math.abs(sq.getNewX()-sq.getNewY())== 0 || Math.abs(sq.getNewX()-sq.getNewY())== 2)) {
                        switch (f.getColor()) {
                            case 'G':
                                if(sq.getNewX() == 0 && sq.getNewY() == 0) {
                                    Face fLeft = c.getFaceByOrientation(f.getAdjacentFaces()[0]);
                                    Face fTop= c.getFaceByOrientation(f.getAdjacentFaces()[1]);
                                    sumCorners += checkMovesToPlaceCorner(sq, fLeft.getColors()[0][2], fTop.getColors()[2][0]);
                                }
                                else if(sq.getNewX() == 0 && sq.getNewY() == 2) {
                                    Face fTop= c.getFaceByOrientation(f.getAdjacentFaces()[1]);
                                    Face fRight= c.getFaceByOrientation(f.getAdjacentFaces()[2]);
                                    sumCorners += checkMovesToPlaceCorner(sq, fRight.getColors()[0][0], fTop.getColors()[2][2]);
                                }
                                else if(sq.getNewX() == 2 && sq.getNewY() == 0) {
                                    Face fLeft = c.getFaceByOrientation(f.getAdjacentFaces()[0]);
                                    Face fBottom= c.getFaceByOrientation(f.getAdjacentFaces()[3]);
                                    sumCorners += checkMovesToPlaceCorner(sq, fLeft.getColors()[2][2], fBottom.getColors()[0][0]);
                                }
                                else if(sq.getNewX() == 2 && sq.getNewY() == 2) {
                                    Face fRight= c.getFaceByOrientation(f.getAdjacentFaces()[2]);
                                    Face fBottom= c.getFaceByOrientation(f.getAdjacentFaces()[3]);
                                    sumCorners += checkMovesToPlaceCorner(sq, fRight.getColors()[2][0], fBottom.getColors()[0][2]);
                                }
                                break;
                            case 'B':
                                if(sq.getNewX() == 0 && sq.getNewY() == 0) {
                                    Face fLeft = c.getFaceByOrientation(f.getAdjacentFaces()[0]);
                                    Face fTop= c.getFaceByOrientation(f.getAdjacentFaces()[1]);
                                    sumCorners += checkMovesToPlaceCorner(sq, fLeft.getColors()[0][2], fTop.getColors()[0][2]);
                                }
                                else if(sq.getNewX() == 0 && sq.getNewY() == 2) {
                                    Face fTop= c.getFaceByOrientation(f.getAdjacentFaces()[1]);
                                    Face fRight= c.getFaceByOrientation(f.getAdjacentFaces()[2]);
                                    sumCorners += checkMovesToPlaceCorner(sq, fRight.getColors()[0][0], fTop.getColors()[0][0]);
                                }
                                else if(sq.getNewX() == 2 && sq.getNewY() == 0) {
                                    Face fLeft = c.getFaceByOrientation(f.getAdjacentFaces()[0]);
                                    Face fBottom= c.getFaceByOrientation(f.getAdjacentFaces()[3]);
                                    sumCorners += checkMovesToPlaceCorner(sq, fLeft.getColors()[2][2], fBottom.getColors()[2][2]);
                                }
                                else if(sq.getNewX() == 2 && sq.getNewY() == 2) {
                                    Face fRight= c.getFaceByOrientation(f.getAdjacentFaces()[2]);
                                    Face fBottom= c.getFaceByOrientation(f.getAdjacentFaces()[3]);
                                    sumCorners += checkMovesToPlaceCorner(sq, fRight.getColors()[2][0], fBottom.getColors()[2][0]);
                                }
                                break;
                        }
                    }
                    //24 edges but, but we'll process them in pairs
                    //12 pair edges
                    else if(((sq.getNewX() == 0 && sq.getNewY() == 1) || (sq.getNewX() == 1 && sq.getNewY() == 0) || (sq.getNewX() == 1 && sq.getNewY() == 2) || (sq.getNewX() == 2 && sq.getNewY() == 1))  ) {
                        switch (f.getColor()) {
                            case 'G':
                                if(sq.getNewX() == 0 && sq.getNewY() == 1) {
                                    Face fTop = c.getFaceByOrientation(f.getAdjacentFaces()[1]);
                                    sumEdges += checkMovesToPlaceEdge(sq, fTop.getColors()[2][1]);
                                }
                                else if(sq.getNewX() == 1 && sq.getNewY() == 0) {
                                    Face fLeft = c.getFaceByOrientation(f.getAdjacentFaces()[0]);
                                    sumEdges += checkMovesToPlaceEdge(sq, fLeft.getColors()[1][2]);
                                }
                                else if(sq.getNewX() == 1 && sq.getNewY() == 2) {
                                    Face fRight= c.getFaceByOrientation(f.getAdjacentFaces()[2]);
                                    sumEdges += checkMovesToPlaceEdge(sq, fRight.getColors()[1][0]);
                                }
                                else if(sq.getNewX() == 2 && sq.getNewY() == 1) {
                                    Face fBottom= c.getFaceByOrientation(f.getAdjacentFaces()[3]);
                                    sumEdges += checkMovesToPlaceEdge(sq, fBottom.getColors()[0][1]);
                                }
                                break;
                            case 'B':
                                if(sq.getNewX() == 0 && sq.getNewY() == 1) {
                                    Face fTop = c.getFaceByOrientation(f.getAdjacentFaces()[1]);
                                    sumEdges += checkMovesToPlaceEdge(sq, fTop.getColors()[0][1]);
                                }
                                else if(sq.getNewX() == 1 && sq.getNewY() == 0) {
                                    Face fLeft = c.getFaceByOrientation(f.getAdjacentFaces()[0]);
                                    sumEdges += checkMovesToPlaceEdge(sq, fLeft.getColors()[1][2]);
                                }
                                else if(sq.getNewX() == 1 && sq.getNewY() == 2) {
                                    Face fRight= c.getFaceByOrientation(f.getAdjacentFaces()[2]);
                                    sumEdges += checkMovesToPlaceEdge(sq, fRight.getColors()[1][0]);
                                }
                                else if(sq.getNewX() == 2 && sq.getNewY() == 1) {
                                    Face fBottom= c.getFaceByOrientation(f.getAdjacentFaces()[3]);
                                    sumEdges += checkMovesToPlaceEdge(sq, fBottom.getColors()[2][1]);
                                }
                                break;
                            case 'O':
                                if(sq.getNewX() == 0 && sq.getNewY() == 1) {
                                    Face fTop = c.getFaceByOrientation(f.getAdjacentFaces()[1]);
                                    sumEdges += checkMovesToPlaceEdge(sq, fTop.getColors()[1][0]);
                                }
                                else if(sq.getNewX() == 2 && sq.getNewY() == 1) {
                                    Face fBottom= c.getFaceByOrientation(f.getAdjacentFaces()[3]);
                                    sumEdges += checkMovesToPlaceEdge(sq, fBottom.getColors()[1][0]);
                                }
                                break;
                            case 'R':
                                if(sq.getNewX() == 0 && sq.getNewY() == 1) {
                                    Face fTop = c.getFaceByOrientation(f.getAdjacentFaces()[1]);
                                    sumEdges += checkMovesToPlaceEdge(sq, fTop.getColors()[1][2]);
                                }
                                else if(sq.getNewX() == 2 && sq.getNewY() == 1) {
                                    Face fBottom= c.getFaceByOrientation(f.getAdjacentFaces()[3]);
                                    sumEdges += checkMovesToPlaceEdge(sq, fBottom.getColors()[1][2]);
                                }
                                break;
                        }
                    }
                }
            }
        }
        return (sumCorners+sumEdges)/4;
    }
}

