package myPkg;


public class Configurations {
    private static Decoder dec = new Decoder();
    public static String NAME_FILE = "/home/loreano/Desktop/auals/Artificial/ArtificialT1/trabalho 1/duplos/duplos10ideal.txt";
    public static int SIZE = dec.read_size(NAME_FILE);

    public static int INDEX_APTIDAO = SIZE;

    public static int geracoes = 5;
    public static int rodadas = 200;

    public static int NUM_MUTACAO = SIZE / 2;
}