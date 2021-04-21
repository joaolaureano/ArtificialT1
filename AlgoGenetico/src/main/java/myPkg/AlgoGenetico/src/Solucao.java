package myPkg.AlgoGenetico.src;

public class Solucao {

    public static boolean verificarSolucao(int geracao) {
        if (Populacao.getPopulacoes()[geracao][Configurations.INDEX_APTIDAO] == 0) {
            Populacao.printPopulacao(2);
            Log.addToWhole_log("\nAchou a solução ótima. Ela corresponde ao cromossomo : " + geracao + "\n", 2);
            for (int i = 0; i < Populacao.getPopulacoes()[geracao].length; i++) {
                Log.addToWhole_log("Pessoa A" + i + " se juntou com B" + Populacao.getPopulacoes()[geracao][i] + "\n", 2);
            }
            return true;
        }
        return false;
    }
}