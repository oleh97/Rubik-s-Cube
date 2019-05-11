public class test {
    public static void main(String[] args) {
        Cube c = new Cube(3);
        if (c.isSolved()) System.out.println("solved");
        c.scramble("L2 U2 F L' B2 F U' L' U2 L2 F B U2 L' D F2 B L D F B' U D' B2 L");
        System.out.println(c);
//        Solver.solve(c);
    }
}
