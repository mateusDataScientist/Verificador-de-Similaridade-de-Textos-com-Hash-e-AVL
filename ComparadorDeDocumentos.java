import java.util.HashSet;
import java.util.Set;

public class ComparadorDeDocumentos {

    public static double cosseno(Documento d1, Documento d2) {

        HashTable t1 = d1.getTabela();
        HashTable t2 = d2.getTabela();

        Set<String> palavras = new HashSet<>();

        palavras.addAll(t1.keySet());
        palavras.addAll(t2.keySet());

        double produto = 0;
        double norma1 = 0;
        double norma2 = 0;

        for (String palavra : palavras) {

            int f1 = t1.get(palavra) == null
                    ? 0
                    : t1.get(palavra);

            int f2 = t2.get(palavra) == null
                    ? 0
                    : t2.get(palavra);

            produto += f1 * f2;

            norma1 += f1 * f1;
            norma2 += f2 * f2;
        }

        if (norma1 == 0 || norma2 == 0) {
            return 0;
        }

        return produto /
                (Math.sqrt(norma1) * Math.sqrt(norma2));
    }
}