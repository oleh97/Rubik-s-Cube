public class test {
    public static void main(String[] args) {
        Cube c = new Cube(3);
        //Permutar centros
//        c.scramble("L'RFB'L'RU'DLR'U'D");

        c.scramble("F' D B' R' U2 F R' F' R2 L2 B' U F' B2 R' F B2 U' D' R L' F2 B U D");
        System.out.println(c);

    }
}
