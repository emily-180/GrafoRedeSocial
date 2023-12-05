
package grafos;

public class Aresta {
    Node vertice;
    String relacionamentos;

    public Aresta(Node destino, String relacionamentos) {
        this.vertice = destino;
        this.relacionamentos = relacionamentos;
    }
}
