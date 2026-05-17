import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        if (args.length < 3) {

            System.out.println(
                    "Uso: java Main <diretorio> <limiar> <modo>"
            );

            return;
        }

        String diretorio = args[0];

        double limiar =
                Double.parseDouble(args[1]);

        String modo = args[2];

        File pasta = new File(diretorio);

        if (!pasta.exists() || !pasta.isDirectory()) {

            System.out.println("Diretório inválido.");

            return;
        }

        File[] arquivos =
                pasta.listFiles((dir, name) ->
                        name.endsWith(".txt"));

        if (arquivos == null || arquivos.length == 0) {

            System.out.println(
                    "Nenhum arquivo txt encontrado."
            );

            return;
        }

        ArrayList<Documento> documentos =
                new ArrayList<>();

        for (File arquivo : arquivos) {

            documentos.add(
                    new Documento(arquivo)
            );
        }

        AVLTree avl = new AVLTree();

        int comparacoes = 0;

        for (int i = 0; i < documentos.size(); i++) {

            for (int j = i + 1;
                 j < documentos.size();
                 j++) {

                Documento d1 = documentos.get(i);
                Documento d2 = documentos.get(j);

                double similaridade =
                        ComparadorDeDocumentos
                                .cosseno(d1, d2);

                Resultado r = new Resultado(
                        d1.getNome(),
                        d2.getNome(),
                        similaridade
                );

                avl.insert(similaridade, r);

                comparacoes++;
            }
        }

        StringBuilder saida =
                new StringBuilder();

        saida.append(
                "=== VERIFICADOR DE SIMILARIDADE ===\n"
        );

        saida.append(
                "Documentos processados: "
        ).append(documentos.size()).append("\n");

        saida.append(
                "Comparações realizadas: "
        ).append(comparacoes).append("\n\n");

        if (modo.equals("lista")) {

            saida.append(
                    "Pares com similaridade >= "
            ).append(limiar).append("\n");

            saida.append(
                    "-----------------------------\n"
            );

            avl.printAboveThreshold(
                    limiar,
                    saida
            );

        } else if (modo.equals("topK")) {

            if (args.length < 4) {

                System.out.println(
                        "Informe o valor de K."
                );

                return;
            }

            int k =
                    Integer.parseInt(args[3]);

            ArrayList<Resultado> lista =
                    new ArrayList<>();

            avl.reverseOrder(lista);

            saida.append("TOP ")
                    .append(k)
                    .append("\n");

            saida.append(
                    "-----------------------------\n"
            );

            for (int i = 0;
                 i < Math.min(k, lista.size());
                 i++) {

                saida.append(lista.get(i))
                        .append("\n");
            }

        } else if (modo.equals("busca")) {

            if (args.length < 5) {

                System.out.println(
                        "Informe os dois arquivos."
                );

                return;
            }

            String nome1 = args[3];
            String nome2 = args[4];

            Documento d1 = null;
            Documento d2 = null;

            for (Documento d : documentos) {

                if (d.getNome().equals(nome1)) {
                    d1 = d;
                }

                if (d.getNome().equals(nome2)) {
                    d2 = d;
                }
            }

            if (d1 == null || d2 == null) {

                System.out.println(
                        "Arquivos não encontrados."
                );

                return;
            }

            double sim =
                    ComparadorDeDocumentos
                            .cosseno(d1, d2);

            saida.append("Comparando: ")
                    .append(nome1)
                    .append(" <-> ")
                    .append(nome2)
                    .append("\n");

            saida.append(
                    "Similaridade: "
            ).append(
                    String.format("%.2f", sim)
            ).append("\n");
        }

        saida.append("\n");

        saida.append("Rotações AVL:\n");

        saida.append(
                avl.getRotationStats()
        );

        System.out.println(saida);

        try {

            PrintWriter writer =
                    new PrintWriter(
                            "resultado.txt"
                    );

            writer.println(saida);

            writer.close();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}