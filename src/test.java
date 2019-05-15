public class test {
    public static void main(String[] args) {
        long startTime;
        long endTime;
        Cube c;

//        * * * * * * * * *
//        * PASSED TESTS  *
//        * * * * * * * * *
        c = new Cube(3);
        c.scramble("U R L2 U' F' U2 F'");
        startTime = System.nanoTime();
        System.out.println(Solver.solve(c));
        endTime = System.nanoTime();
        System.out.println((endTime - startTime)/1000000  + " ms");

//        c = new Cube(3);
//        c.scramble("U D2 F2 U' D2 F B2");
//        startTime = System.nanoTime();
//        System.out.println(Solver.solve(c));
//        endTime = System.nanoTime();
//        System.out.println((endTime - startTime)/1000000  + " ms");

        c = new Cube(3);
        c.scramble("L' F2 U2 F2 B R2 D");
        startTime = System.nanoTime();
        System.out.println(Solver.solve(c));
        endTime = System.nanoTime();
        System.out.println((endTime - startTime)/1000000  + " ms");

        c = new Cube(3);
        c.scramble("R D' R2 F' R D' F");
        startTime = System.nanoTime();
        System.out.println(Solver.solve(c));
        endTime = System.nanoTime();
        System.out.println((endTime - startTime)/1000000  + " ms");

        c = new Cube(3);
        c.scramble("U2 F D2 L2 U2 B2 F");
        startTime = System.nanoTime();
        System.out.println(Solver.solve(c));
        endTime = System.nanoTime();
        System.out.println((endTime - startTime)/1000000  + " ms");
//
        c = new Cube(3);
        c.scramble("D' L2 R2 U2 L' D2 R2 D' U R2");
        startTime = System.nanoTime();
        System.out.println(Solver.solve(c));
        endTime = System.nanoTime();
        System.out.println((endTime - startTime)/1000000  + " ms");

        c = new Cube(3);
        c.scramble("F L R U D B F2 L2 R2");
        startTime = System.nanoTime();
        System.out.println(Solver.solve(c));
        endTime = System.nanoTime();
        System.out.println((endTime - startTime)/1000000  + " ms");




    }
}
