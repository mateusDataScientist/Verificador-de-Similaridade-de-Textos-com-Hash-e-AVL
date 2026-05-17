import java.io.File;
import java.nio.file.Files;

public class Documento {

    private String nome;
    private HashTable tabela;

    public Documento(File arquivo) {

        this.nome = arquivo.getName();
        this.tabela = new HashTable(1000);

        processar(arquivo);
    }

    private void processar(File arquivo) {

        try {

            String texto = Files.readString(arquivo.toPath());

            // minúsculas
            texto = texto.toLowerCase();

            // remove caracteres especiais
            texto = texto.replaceAll(
                    "[^a-zA-Záàâãéèêíïóôõöúçñ ]",
                    " "
            );

            // tokenização
            String[] tokens = texto.split("\\s+");

            for (String palavra : tokens) {

                if (palavra.isBlank()) {
                    continue;
                }

                // remove stop words
                if (StopWords.isStopWord(palavra)) {
                    continue;
                }

                Integer freq = tabela.get(palavra);

                if (freq == null) {
                    tabela.put(palavra, 1);
                } else {
                    tabela.put(palavra, freq + 1);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getNome() {
        return nome;
    }

    public HashTable getTabela() {
        return tabela;
    }
}