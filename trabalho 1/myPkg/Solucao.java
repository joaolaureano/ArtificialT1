package myPkg;

public class Solucao {

    public static boolean verificarSolucao(int geracao) {
        if (Populacao.getPopulacoes()[geracao][Configurations.INDEX_APTIDAO] == 0) {
            System.out.println("\nAchou a solução ótima. Ela corresponde ao cromossomo : " + geracao);
            for (int i = 0; i < Populacao.getPopulacoes()[geracao].length; i++) {
                System.out.println("Pessoa A" + i + " se juntou com B" + Populacao.getPopulacoes()[geracao][i]);
            }
            return true;
        }
        return false;
    }
}