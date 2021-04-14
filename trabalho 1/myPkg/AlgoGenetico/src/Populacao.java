

import java.util.ArrayList;
import java.util.Collections;

public class Populacao {
    private static int[][] populacoes = new int[Configurations.geracoes][Configurations.SIZE + 1];

    public static void init() {
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

    public static void printCromossomo(int individuo){
        for(int i = 0; i < Configurations.SIZE; i++)
            System.out.print(Populacao.populacoes[individuo][i] + " ");
            System.out.println();

    }
    public static int[][] setPopulacoes(int[][] newPopulacao) {
        return Populacao.populacoes = newPopulacao;
    }

    public static void printPopulacao() {
        for (int i = 0; i < Configurations.geracoes; i++) {
            System.out.print("C" + i + ": ");
            for (int j = 0; j < Configurations.SIZE; j++) {
                System.out.print(populacoes[i][j] + " ");
            }
            System.out.println("APT: " + populacoes[i][Configurations.INDEX_APTIDAO]);
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