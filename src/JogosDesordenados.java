// Recorri aos conteúdos das aulas , pois estava com dificuldades em entender alguns conceitos.

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class JogosDesordenados {

    private static final String FILENAME = "C:\\Users\\iuri1\\OneDrive\\Documentos\\GitHub\\JogosDesordenados\\src\\JogosDesordenados.csv";
    private static List<Item> itens = new ArrayList<>();

    public static void main(String[] args) {
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        int opcao;

        do {
            System.out.println("[1] Ler arquivo");
            System.out.println("[2] Ordenar por categoria");
            System.out.println("[3] Ordenar por avaliação");
            System.out.println("[4] Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    lerArquivo();
                    break;
                case 2:
                    ordenarPorCategoria();
                    break;
                case 3:
                    ordenarPorAvaliacao();
                    break;
                case 4:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida");
                    break;
            }

        } while (opcao != 4);

        scanner.close();
    }

    private static void lerArquivo() {
        itens.clear();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILENAME))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");

                if (data.length == 3) {
                    // Jogo, Categoria, Avaliação
                    String jogo = data[0];
                    String categoria = data[1];
                    double avaliacao = Double.parseDouble(data[2]);
                    itens.add(new Item(jogo, categoria, avaliacao));
                }
            }

            System.out.println("Arquivo lido com sucesso.");

        } catch (Exception e) {
            System.out.println("Erro ao ler arquivo: " + e.getMessage());
        }
    }

    private static void ordenarPorCategoria() {
        if (itens.isEmpty()) {
            System.out.println("O arquivo ainda não foi lido.");
            return;
        }

        Collections.sort(itens, Comparator.comparing(Item::getCategoria).thenComparing(Item::getJogo));

        salvarArquivoOrdenado("JogosOrdenadosporCategoria.csv");
        System.out.println("Jogos ordenados por categoria e salvos");
    }

    private static void ordenarPorAvaliacao() {
        if (itens.isEmpty()) {
            System.out.println("O arquivo ainda não foi lido.");
            return;
        }

        Collections.sort(itens, Comparator.comparing(Item::getCategoria).thenComparing(Item::getAvaliacao).reversed()
                .thenComparing(Item::getJogo));

        salvarArquivoOrdenado("JogosOrdenadosporAvaliacao.csv");
        System.out.println("Jogos ordenados por avaliação e salvos");
    }

    private static void salvarArquivoOrdenado(String nomeArquivo) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(nomeArquivo))) {
            for (Item item : itens) {
                writer.println(item.getJogo() + "," + item.getCategoria() + "," + item.getAvaliacao());
            }

        } catch (IOException e) {
            System.out.println("Erro ao salvar arquivo ordenado: " + e.getMessage());
        }
    }

    static class Item {
        private String jogo;
        private String categoria;
        private double avaliacao;

        public Item(String jogo, String categoria, double avaliacao) {
            this.jogo = jogo;
            this.categoria = categoria;
            this.avaliacao = avaliacao;
        }

        public String getJogo() {
            return jogo;
        }

        public String getCategoria() {
            return categoria;
        }

        public double getAvaliacao() {
            return avaliacao;
        }
    }
}