import jdk.swing.interop.SwingInterOpUtils;

import java.util.*;

public class Solver {

    static String[] moves = {"F", "L", "R", "U", "D", "B", "F2", "L2", "R2", "U2", "D2", "B2", "F'", "L'", "R'", "U'", "D'", "B'"};
    static Random random = new Random();

    static String solve(Cube cube) {
        TreeSet<Cube> toExplore = new TreeSet<>();
//        TreeSet<Cube> explored = new TreeSet<>();
        cube.setHeuristic(manhattanDistance(cube));
        toExplore.add(cube);
        while (!toExplore.isEmpty()) {
            Cube c = toExplore.first();
            if(c.isSolved()) return c.movesToSolve.toString();
            if(c.movesCount <= 20) {
//                System.out.println(c.movesCount);
                TreeSet<Cube> newNodes = generateCubeStates(c);
                while (!newNodes.isEmpty()) {
                    if(newNodes.first().getHeuristic() < c.getHeuristic()) {
                        toExplore.add(newNodes.first());
                    }
                    newNodes.remove(newNodes.first());
                }
//                toExplore.addAll(generateCubeStates(c));
            }
            toExplore.remove(c);
//            explored.add(c);
//            System.out.println((c.getHeuristic()));
        }
        return "";
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
                    b.movesToSolve.append(moves[i]);
                    b.movesCount = cube.movesCount +1;
                    double mh = manhattanDistance(b);
//                    System.out.println("^Depth: "+b.movesCount+"| "+ moves[i] + "|" + mh);
                    b.setHeuristic(mh);
                    s.add(b);
                }
            }
        }
        return s;
    }

    private static double manhattanDistance(Cube c) {
        int sum = 0;
        for(Face f : c.getFaces()) {
            for(Square[] s : f.getColors()) {
                for (Square sq : s) {
                    sum += sq.manhattan();
                }
            }
        }
        return sum/8;
    }
}

