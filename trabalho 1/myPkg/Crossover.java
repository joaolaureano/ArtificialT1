package myPkg;

import java.util.Arrays;
import java.util.Random;

public class Crossover {

    public static void crossover() {

        for (int j = 1; j < Configurations.geracoes; j = j += 2) {

            int ind1, ind2;
            ind1 = torneio();
            ind2 = torneio();

            int[][] individuo_mescla = crossoverOX1Aux(ind1, ind2);
            for (int i = 0; i < Configurations.SIZE; i++) {
                Main.intermediario[j][i] = individuo_mescla[0][i];
                Main.intermediario[j + 1][i] = individuo_mescla[1][i];
            }
            Main.intermediario[j][Configurations.INDEX_APTIDAO] = 0;
            Main.intermediario[j + 1][Configurations.INDEX_APTIDAO] = 0;
        }
        return;
    }

    public static int torneio() {
        Random rand = new Random();

        int ind1, ind2;

        ind1 = rand.nextInt(Configurations.geracoes);
        ind2 = rand.nextInt(Configurations.geracoes);
        while(ind1 == ind2)
            ind2 = rand.nextInt(Configurations.geracoes);

        return Populacao.getPopulacoes()[ind1][Configurations.INDEX_APTIDAO] < Populacao.getPopulacoes()[ind2][Configurations.INDEX_APTIDAO]
                ? ind1
                : ind2;
    }

    public static int[][] crossoverOX1Aux(int ind1, int ind2) {
        int[] filho1 = crossoverOX1(ind1, ind2);
        int[] filho2 = crossoverOX1(ind2, ind1);

        int[][] result = { filho1, filho2 };
        return result;
    }

    public static int[] crossoverOX1(int ind1, int ind2) {
        int[] cromossomo1 = new int[Populacao.getPopulacoes()[ind1].length];
        int[] cromossomo2 = new int[Populacao.getPopulacoes()[ind2].length];
        ;

        for (int i = 0; i < Configurations.SIZE + 1; i++) {
            cromossomo1[i] = Populacao.getPopulacoes()[ind1][i];
            cromossomo2[i] = Populacao.getPopulacoes()[ind2][i];
        }
        int[] filho = new int[Configurations.SIZE + 1];
        int qntPreenchida = 0;
        int[] faixa = faixaCrossover();

        Arrays.fill(filho, -1);

        System.out.println("FAIXA RANDOMIZADA:" + Arrays.toString(faixa));
        System.out.println("CROMOSSOMO 1:" + Arrays.toString(cromossomo1));
        System.out.println("CROMOSSOMO 2:" + Arrays.toString(cromossomo2));

        for (int i = faixa[0]; i <= faixa[1]; i++) {
            filho[i] = cromossomo1[i];
            int index2 = indexOfIntArray(cromossomo2, cromossomo1[i]);
            cromossomo2[index2] = -1;
            qntPreenchida++;
        }
        int index2 = 0;
        int i = 0;
        while (qntPreenchida < Configurations.SIZE) {
            if (filho[i] == -1) {
                while (cromossomo2[index2] == -1)
                    index2++;

                filho[i] = cromossomo2[index2];
                index2++;

                qntPreenchida++;
            }
            i++;
        }
        System.out.println("FILHO GERADO:" + Arrays.toString(filho));
        return filho;
    }

    public static int[] faixaCrossover() {

        Random rand = new Random();

        int min = rand.nextInt(Configurations.SIZE);
        int max = rand.nextInt(Configurations.SIZE);
        while (min == max) {
            min = rand.nextInt(Configurations.SIZE);
            max = rand.nextInt(Configurations.SIZE);
        }

        min = Math.min(min, max);
        max = Math.max(min, max);

        int[] result = { min, max };
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
