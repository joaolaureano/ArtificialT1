package myPkg.AlgoGenetico.src;

public class Configurations {
    public static String NAME_FILE = "data/duplos6ideal.txt";
    public static int SIZE = new Decoder().read_size(NAME_FILE);
    public static int INDEX_APTIDAO = SIZE;
    public static int geracoes = 5;
    public static int rodadas = 200;
    public static int NUM_MUTACAO = Math.max(1, SIZE / 2);
}