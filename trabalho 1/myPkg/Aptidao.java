package myPkg;

public class Aptidao {
    private static Decoder dec = new Decoder();
    private static int[][] PREFERENCES = dec.read_preferences(Configurations.NAME_FILE);

    public static void calcularAptidao() {
        for (int i = 0; i < Populacao.getPopulacoes().length; i++) {
            int apt = 0;
            for (int j = 0; j < Configurations.SIZE; j++) {
                apt += aptidao(i, j);
            }
            Populacao.setPopulacaoPosicao(i, Configurations.INDEX_APTIDAO, apt);
        }
    }

    public static int aptidao(int individuo, int aluno_a) {

        int aluno_b = Populacao.getPopulacoes()[individuo][aluno_a];

        int[] preferencia_a = PREFERENCES[aluno_a];
        int[] preferencia_b = PREFERENCES[Configurations.SIZE + aluno_b];

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

}
