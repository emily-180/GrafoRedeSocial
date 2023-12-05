package grafos;

import java.util.Set;

public class Node {
	String nome;
    String foto;
    Set<String> interesses;

    public Node(String nome, String foto, Set<String> interesses) {
        this.nome = nome;
        this.foto = foto;
        this.interesses= interesses;
    }
}
