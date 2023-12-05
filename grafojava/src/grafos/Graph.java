
package grafos;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Graph {
	private Map<Node, LinkedList<Aresta>> MeuGrafo;

    public Graph() {
        this.MeuGrafo = new HashMap<>();
    }

    public void adicionarNo(Node no) {
        if (!MeuGrafo.containsKey(no)) 
            MeuGrafo.put(no, new LinkedList<>()); 
    }
    
    public void adicionarAresta(Node origem, Node destino, String relacionamento) {
        if (!MeuGrafo.containsKey(origem) || !MeuGrafo.containsKey(destino)) {
            throw new IllegalArgumentException("Os nós de origem e destino devem existir no grafo.");
        }
        MeuGrafo.get(origem).add(new Aresta(destino, relacionamento));
        MeuGrafo.get(destino).add(new Aresta(origem, relacionamento));
    }

    public void listarContatos(Node membro) {    
        if (MeuGrafo.containsKey(membro)) {
            System.out.println("Contatos de " + membro.nome + ":");
            for (Aresta vizinho : MeuGrafo.get(membro)) {
                System.out.println("- " + vizinho.vertice.nome + " (" + vizinho.relacionamentos + ")");
            }
        } else {
            System.out.println(membro.nome+ " não está na rede.");
        }
    }

    public boolean verificarAlcance(Node origem, Node destino) {
        Set<Node> visitados = new HashSet<>();
        return existeCaminho(origem, destino, visitados);
    }

    private boolean existeCaminho(Node noAtual, Node destino, Set<Node> visitados) {
        if (noAtual == destino) {
            System.out.print("Conexões: ");
            for (Node visitado : visitados) {
                System.out.print(visitado.nome + " ");
            }
            System.out.println();
            return true;
        }
        visitados.add(noAtual);
        LinkedList<Aresta> adjacencias = MeuGrafo.get(noAtual);
        if (adjacencias != null) {
            for (Aresta adjacente : adjacencias) {
                if (!visitados.contains(adjacente.vertice) && existeCaminho(adjacente.vertice, destino, visitados)) {
                    return true;
                }
            }
        }
        return false;
    }


    
    public void salvarRedeSocial() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("rede_social.txt"))) {
            for (Map.Entry<Node, LinkedList<Aresta>> entry : MeuGrafo.entrySet()) {
                Node no = entry.getKey();
                LinkedList<Aresta> vizinhos = entry.getValue();
                writer.println("Dados de " + no.nome + ":");
                writer.println("- Foto: " + no.foto);
                writer.println("- Interesses: " + no.interesses);
                for (Aresta vizinho : vizinhos) {
                    writer.println(no.nome + " " + vizinho.vertice.nome + " " + vizinho.relacionamentos);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void carregarRedeSocial() {
        try (Scanner scanner = new Scanner(new File("rede_social.txt"))) {
            Node noAtual = null;

            while (scanner.hasNextLine()) {
                String linha = scanner.nextLine();

                if (linha.startsWith("Dados de")) {
                    // Iniciar um novo nó
                    String nome = linha.split(":")[1].trim();
                    String foto = scanner.nextLine().split(":")[1].trim();
                    Set<String> interesses = new HashSet<>(Arrays.asList(scanner.nextLine().split(":")[1].trim().split(", ")));
                    noAtual = new Node(nome, foto, interesses);
                    adicionarNo(noAtual);
                } else if (linha.startsWith("-")) {
                    // Adicionar conexões ao nó atual
                    String[] dadosAresta = linha.substring(2).split(" ");
                    String nomeDestino = dadosAresta[0];
                    String relacionamento = dadosAresta[1];
                    Node destino = buscarNoPorNome(nomeDestino);
                    if (destino != null) {
                        adicionarAresta(noAtual, destino, relacionamento);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }




	public Node buscarNoPorNome(String nome) {
	        for (Node no : MeuGrafo.keySet()) {
	            if (no.nome.equals(nome)) {
	                return no;
	            }
	        }
	        return null;
	}

	public void adicionarConexao(Node origem, Node destino, String relacionamento) {
	    if (MeuGrafo.containsKey(origem) && MeuGrafo.containsKey(destino)) {
	        if (!existeAresta(origem, destino)) {
	            MeuGrafo.get(origem).add(new Aresta(destino, relacionamento));
	            MeuGrafo.get(destino).add(new Aresta(origem, relacionamento));
	            System.out.println("Conexão adicionada entre " + origem.nome + " e " + destino.nome);
	        } else {
	            System.out.println("Já existe uma conexão entre " + origem.nome + " e " + destino.nome);
	        }
	    } else {
	        System.out.println("Os nós de origem e destino devem existir no grafo.");
	    }
	}

	public void removerConexao(Node origem, Node destino) {
	    if (MeuGrafo.containsKey(origem) && MeuGrafo.containsKey(destino)) {
	        if (existeAresta(origem, destino)) {
	            MeuGrafo.get(origem).removeIf(aresta -> aresta.vertice.equals(destino));
	            MeuGrafo.get(destino).removeIf(aresta -> aresta.vertice.equals(origem));
	            System.out.println("Conexão removida entre " + origem.nome + " e " + destino.nome);
	        } else {
	            System.out.println("Não existe uma conexão entre " + origem.nome + " e " + destino.nome);
	        }
	    } else {
	        System.out.println("Os nós de origem e destino devem existir no grafo.");
	    }
	}
	
	private boolean existeAresta(Node origem, Node destino) {
	    LinkedList<Aresta> adjacencias = MeuGrafo.get(origem);
	    if (adjacencias != null) {
	        for (Aresta adjacente : adjacencias) {
	            if (adjacente.vertice.equals(destino)) {
	                return true;
	            }
	        }
	    }
	    return false;
	}

}

