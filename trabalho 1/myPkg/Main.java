package myPkg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Collections;

public class Main {
    private static Decoder dec = new Decoder();
    private static String NAME_FILE = "/home/loreano/Desktop/auals/Artificial/ArtificialT1/trabalho 1/duplos/duplos10ideal.txt";
    private static int SIZE = dec.read_size(NAME_FILE);
    private static int[][] PREFERENCES = dec.read_preferences(NAME_FILE);
    private static int INDEX_APTIDAO = SIZE;

    private static int geracoes = 5;
    private static int rodadas = 5;
    private static int[][] populacoes = new int[geracoes][SIZE + 1];
    private static int[][] intermediario = new int[geracoes][SIZE + 1];

    public static void main(String[] args) {
        init();
        int indexMelhor = 0;
        for (int x = 0; x < rodadas; x++) {
            System.out.println("Rodada " + x);
            calcularAptidao();
            printPopulacao();
            indexMelhor = melhorIndividuo();

            if (verificarSolucao(indexMelhor))
                return;

            crossover();
            populacoes = intermediario;

        }
        calcularAptidao();
        printPopulacao();
        System.out.println("Nao foi possivel encontrar a solucao perfeita.");
        System.out.println("Melhor solucao encontrada: ");
        System.out.println(Arrays.toString(populacoes[indexMelhor]));
        return;
    }

    public static void init() {
        ArrayList<Integer> list = new ArrayList<Integer>(SIZE);
        for (int k = 0; k < SIZE; list.add(k), k++)
            ;

        for (int i = 0; i < geracoes; i++) {
            Collections.shuffle(list);
            for (int j = 0; j < SIZE; j++) {
                populacoes[i][j] = list.get(j);
            }
        }

    }

    public static void printPopulacao() {

        for (int i = 0; i < geracoes; i++) {
            System.out.print("C" + i + ": ");
            for (int j = 0; j < SIZE; j++) {
                System.out.print(populacoes[i][j] + " ");
            }
            System.out.println("APT: " + populacoes[i][INDEX_APTIDAO]);
        }
    }

    public static int aptidao(int individuo, int aluno_a) {

        int aluno_b = populacoes[individuo][aluno_a];

        int[] preferencia_a = PREFERENCES[aluno_a];
        int[] preferencia_b = PREFERENCES[SIZE + aluno_b];

        int aptidao_a = 0;
        int aptidao_b = 0;

        for (int i = 0; i < preferencia_a.length; i++) {
            if ((preferencia_a[i] == (aluno_b + 1)))
                break;
            aptidao_a++;
        }
        for (int i = 0; i < preferencia_b.length; i++) {
            if ((preferencia_b[i] == (aluno_a + 1)))
                break;
            aptidao_b++;
        }

        return aptidao_a + aptidao_b;
    }

    public static void calcularAptidao() {
        for (int i = 0; i < populacoes.length; i++) {
            int apt = 0;
            for (int j = 0; j < SIZE; j++) {
                apt += aptidao(i, j);
            }
            populacoes[i][INDEX_APTIDAO] = apt;
        }
    }

    public static int melhorIndividuo() {
        int melhor = 0;
        for (int i = 0; i < populacoes.length; i++) {
            if (populacoes[i][INDEX_APTIDAO] < populacoes[melhor][INDEX_APTIDAO])
                melhor = i;
        }
        for (int i = 0; i < intermediario[0].length; i++)
            intermediario[0][i] = populacoes[melhor][i];

        return melhor;
    }

    public static boolean verificarSolucao(int geracao) {
        if (populacoes[geracao][INDEX_APTIDAO] == 0) {
            System.out.println("\nAchou a solução ótima. Ela corresponde ao cromossomo : " + geracao);
            for (int i = 0; i < populacoes[geracao].length; i++) {
                System.out.println("Pessoa A" + i + " se juntou com B" + populacoes[geracao][i]);
            }
            return true;
        }
        return false;
    }

    public static void crossover() {

        for (int j = 1; j < geracoes; j = j+= 2) {

            int ind1, ind2;
            ind1 = torneio();
            ind2 = torneio();

            int[][] individuo_mescla = crossoverOX1Aux(ind1, ind2);
            for (int i = 0; i < SIZE; i++) {
                intermediario[j][i] = individuo_mescla[0][i];
                intermediario[j + 1][i] = individuo_mescla[1][i];
            }
            intermediario[j][INDEX_APTIDAO] = 0;
            intermediario[j + 1][INDEX_APTIDAO] = 0;
        }
        return;
    }

    public static int torneio() {
        Random rand = new Random();

        int ind1, ind2;

        ind1 = rand.nextInt(geracoes);
        ind2 = rand.nextInt(geracoes);

        return populacoes[ind1][INDEX_APTIDAO] < populacoes[ind2][INDEX_APTIDAO] ? ind1 : ind2;

    }
    public static int[][] crossoverOX1Aux(int ind1, int ind2){
        int[] filho1 = crossoverOX1(ind1, ind2);
        int[] filho2 = crossoverOX1(ind2, ind1);

        int[][] result = {filho1, filho2};
        return result;
    }
    public static int[] crossoverOX1(int ind1, int ind2) {
        int [] cromossomo1 = new int[populacoes[ind1].length];
        int [] cromossomo2 = new int[populacoes[ind2].length];;

        for(int i = 0 ; i < SIZE + 1; i++){
            cromossomo1[i] = populacoes[ind1][i];
            cromossomo2[i] = populacoes[ind2][i];
        }
        int [] filho = new int[SIZE + 1];
        int qntPreenchida = 0;
        int [] faixa = faixaCrossover();
        
        Arrays.fill(filho, -1);
        
        
        System.out.println("FAIXA RANDOMIZADA:" + Arrays.toString(faixa));
        System.out.println("CROMOSSOMO 1:" + Arrays.toString(cromossomo1));
        System.out.println("CROMOSSOMO 2:" + Arrays.toString(cromossomo2));

        for(int i = faixa[0]; i <= faixa[1]; i++){
            filho[i] = cromossomo1[i];
            int index2 = indexOfIntArray(cromossomo2, cromossomo1[i]);
            cromossomo2[index2] = -1;
            qntPreenchida++;
        }
        int index2 = 0;
        int i = 0;
        while(qntPreenchida < SIZE ){
            if(filho[i] == -1){
                while(cromossomo2[index2] == -1) index2++;

                filho[i] = cromossomo2[index2];
                index2++;

                qntPreenchida++;
            }
            i++;
        }
        System.out.println("FILHO GERADO:" + Arrays.toString(filho));
            return filho;
    }
    
    public static int[] faixaCrossover(){

        Random rand = new Random();

        int min = rand.nextInt(SIZE);
        int max = rand.nextInt(SIZE);
        while(min == max){
            min = rand.nextInt(SIZE);
            max = rand.nextInt(SIZE);
        }

        min = Math.min(min, max);
        max = Math.max(min, max);

        int[] result = {min,max};
        return result;
    }

    public static int indexOfIntArray(int[] array, int key) {
        int returnvalue = -1;
        for (int i = 0; i < array.length; ++i) {
            if (key == array[i]) {
                returnvalue = i;
                break;
            }
        }
        return returnvalue;
    }
}