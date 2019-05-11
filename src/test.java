public class test {
    public static void main(String[] args) {
        Cube c = new Cube(3);
        if (c.isSolved()) System.out.println("solved");
//        c.scramble("U R2 F B R B2 R U2 L B2 R U' D' R2 F R' L B2 U2 F2");
        c.scramble("L2 R2");
        System.out.println(c);
        System.out.println(Solver.solve(c));
    }
}
