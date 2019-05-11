import java.util.*;

public class Solver {

    static char lastState;
    static char[] orientations = {'F', 'L', 'R', 'U', 'D', 'B'};
    static Random random = new Random();

    static List<String> solve(Cube cube) {
        List<String> solution = new ArrayList<>();
        List<Cube> explored = new ArrayList<>();
        Set<Cube> toExplore = new TreeSet<>();
        toExplore.addAll(generateCubeStates(cube));
        toExplore.addAll(generateCubeStates(cube));
        System.out.println();
        return  solution;
    }

    private static Set<Cube> generateCubeStates(Cube cube) {
        Set<Cube> s = new TreeSet<>();
        for (int i = 0; i < orientations.length; i++) {
                Cube b = new Cube(cube);
                b.scramble(String.valueOf(orientations[i]));
                //Calcular heuristica
                int n = random.nextInt(50);
                b.setHeuristic(n);
                s.add(b);
        }
        return s;
    }

}
