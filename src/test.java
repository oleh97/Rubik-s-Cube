public class test {
    public static void main(String[] args) {
        Cube c = new Cube(3);
        System.out.println(c);
//        c.rotate('U');
        c.rotate('U');
        c.rotate('B');
        c.rotate('F');
        c.rotate('U');
        c.rotate('B');
        c.rotate('B');
        c.rotate('U');
        c.rotate('F');

        c.rotate('F');
        c.rotate('U');
        c.rotate('U');
        c.rotate('F');
        c.rotate('B');
        c.rotate('F');
        c.rotate('B');
        c.rotate('F');
        c.rotate('U');
        c.rotate('B');
        c.rotate('U');
        c.rotate('F');


        System.out.println(c);

    }
}
