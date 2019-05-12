public class test {
    public static void main(String[] args) {
        Cube c = new Cube(3);
        if (c.isSolved()) System.out.println("solved");
//        c.scramble("U R2 F B R B2 R U2 L B2 R U' D' R2 F R' L B2 U2 F2");
        c.scramble("U2 L' D F2 U L R");
//        c.scramble("B2 D2 U2 R2 L2 B U2 B L2 B' U2 B F U' F2 B2 L U2 D2 L2 B D' F B' L R2 F2 B R F U' F' U' F B' L2 D F' U2 R' B U' B2 U2 R L' B' D' R' D' U' F' B' R2 U B U L R B' F' U B L2 F L2 F U2 F B D L2 U' B' U' D2 F R' U L U L' D L' U' B U' F' B2 U D' B' R' L' D2 F2 U2 L' B' R'");
        System.out.println(c);
        System.out.println(Solver.solve(c));
    }
}
