package myPkg.AlgoGenetico.src;
import java.util.ArrayList;
import java.util.Collections;

public class Populacao {
    private static int[][] populacoes;

    public static void init() {
        populacoes = new int[Configurations.geracoes][Configurations.SIZE + 1];
        ArrayList<Integer> list = new ArrayList<Integer>(Configurations.SIZE);
        for (int k = 0; k < Configurations.SIZE; list.add(k), k++)
            ;

        for (int i = 0; i < Configurations.geracoes; i++) {
            Collections.shuffle(list);
            for (int j = 0; j < Configurations.SIZE; j++) {
                populacoes[i][j] = list.get(j);
            }
        }
    }

    public static int[][] getPopulacoes() {
        return Populacao.populacoes;
    }

    public static void printCromossomo(int individuo) {
        for (int i = 0; i < Configurations.SIZE; i++)
        Log.addToWhole_log(Populacao.populacoes[individuo][i] + " ", 1);
        Log.addToWhole_log("\n", 1);
    }

    public static int[][] setPopulacoes(int[][] newPopulacao) {
        return Populacao.populacoes = newPopulacao;
    }

    public static void printPopulacao(int priority) {
        Log.addToWhole_log("População atual : \n" , priority);
        for (int i = 0; i < Configurations.geracoes; i++) {
            Log.addToWhole_log("C" + i + ": ", priority);
            for (int j = 0; j < Configurations.SIZE; j++) {
                Log.addToWhole_log(populacoes[i][j] + " ", priority);
            }
            Log.addToWhole_log("APT: " + populacoes[i][Configurations.INDEX_APTIDAO] + "\n", priority);
        }
    }

    public static void setPopulacaoPosicao(int individuo, int posicao, int valor) {
        Populacao.populacoes[individuo][posicao] = valor;
    }

    public static int melhorIndividuo() {
        int melhor = 0;
        for (int i = 0; i < Populacao.getPopulacoes().length; i++) {
            if (Populacao.getPopulacoes()[i][Configurations.INDEX_APTIDAO] < Populacao
                    .getPopulacoes()[melhor][Configurations.INDEX_APTIDAO])
                melhor = i;
        }

        for (int i = 0; i < App.intermediario[0].length; i++)
            App.intermediario[0][i] = Populacao.getPopulacoes()[melhor][i];

        return melhor;
    }

}