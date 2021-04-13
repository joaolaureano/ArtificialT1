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
    private static int rodadas = 1;
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

        for (int j = 1; j < geracoes; j += 2) {

            int ind1, ind2;
            ind1 = torneio();
            ind2 = torneio();

            int[][] individuo_mescla = crossoverOBX(ind1, ind2);
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

    public static int[] sorteiaIndices() {
        Random rand = new Random();
        int max, min;
        max = populacoes.length;
        min = 1;

        int n_crossover = rand.nextInt(max - min) + min;

        int[] index_sorteadas = new int[n_crossover];
        for (int i = 0; i < n_crossover; i++) {
            index_sorteadas[i] = rand.nextInt(SIZE);

        }
        return index_sorteadas;
    }

    public static int[][] genesSorteados(int ind1, int ind2, int[] indices) {
        int[][] genes_sorteados = new int[2][indices.length];
        for (int i = 0; i < indices.length; i++) {
            genes_sorteados[0][i] = populacoes[ind1][indices[i]];
            genes_sorteados[1][i] = populacoes[ind2][indices[i]];
        }

        return genes_sorteados;
    }

    public static int[][] crossoverOBX(int ind1, int ind2) {
        int[] cromossomo1Original = populacoes[ind1];
        int[] cromossomo2Original = populacoes[ind2];

        System.out.println("ANTES");
        System.out.println(Arrays.toString(cromossomo1Original));
        System.out.println(Arrays.toString(cromossomo2Original));

        int[] cromossomo1Modificado = new int[SIZE + 1];
        int[] cromossomo2Modificado = new int[SIZE + 1];

        int[] index_sorteadas = sorteiaIndices();
        ArrayList<Integer> index_sorteadas_list = arrayToArrayList(index_sorteadas);

        int[][] genes_sorteados = genesSorteados(ind1, ind2, index_sorteadas);

        int[][] valoresNaoModificados = inserirValoresNaoSorteados(cromossomo1Original, cromossomo2Original,
                index_sorteadas_list);

        cromossomo1Modificado = valoresNaoModificados[0];
        cromossomo2Modificado = valoresNaoModificados[1];

        ArrayList<Integer> cromossomo1Original_list =
        arrayToArrayList(cromossomo1Original);
        ArrayList<Integer> cromossomo2Original_list =
        arrayToArrayList(cromossomo2Original);

        for (int i = 0; i < index_sorteadas.length; i++) {

        int novaPosicao1, novaPosicao2;
        int valorArray1, valorArray2;

        valorArray1 = genes_sorteados[0][i];
        valorArray2 = genes_sorteados[1][i];

        novaPosicao1 = cromossomo2Original_list.indexOf(valorArray1);
        novaPosicao2 = cromossomo1Original_list.indexOf(valorArray2);

        cromossomo1Modificado[novaPosicao1] = valorArray1;
        cromossomo2Modificado[novaPosicao2] = valorArray2;
        }
        System.out.println("DEPOIS");
        System.out.println(Arrays.toString(cromossomo1Modificado));
        System.out.println(Arrays.toString(cromossomo2Modificado));
        int[][] resultado = { cromossomo1Modificado, cromossomo2Modificado };
        return resultado;
    }

    public static ArrayList<Integer> arrayToArrayList(int[] array) {
        ArrayList<Integer> arrayList = new ArrayList<>();
        for (int i : array)
            arrayList.add(i);
        return arrayList;
    }

    public static int[][] inserirValoresNaoSorteados(int[] cromossomo1, int[] cromossomo2,
            ArrayList<Integer> index_sorteadas_list) {

        int[] cromossomo1Modificado, cromossomo2Modificado;
        cromossomo1Modificado = new int[cromossomo1.length];
        cromossomo2Modificado = new int[cromossomo2.length];
        for (int i = 0; i < SIZE; i++) {
            if (!index_sorteadas_list.contains(i)) {
                cromossomo1Modificado[i] = cromossomo1[i];
                cromossomo2Modificado[i] = cromossomo2[i];
            }
        }
        int[][] result = { cromossomo1Modificado, cromossomo2Modificado };
        return result;
    }
}