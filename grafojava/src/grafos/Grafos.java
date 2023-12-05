package grafos;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Grafos {
	public static void main(String[] args) {
        Graph redeSocial = new Graph();
        Scanner scanner = new Scanner(System.in);
        int op;

        do {
            op = menuOperacoes(scanner, redeSocial);
        } while (op != 0);
    }

    private static int menuOperacoes(Scanner scanner, Graph redeSocial) {
        String nome;
        String foto;
        String interessesString;
        String nomeOrigem;
        String nomeDestino;
        System.out.println("Operações:");
        System.out.println("1- Salvar e carregar os dados de um arquivo texto ou csv.");
        System.out.println("2- Adição de novos contatos (nós) à rede social.");
        System.out.println("3- Verificar alcance de um membro.");
        System.out.println("4- Listar contatos.");
        System.out.println("5- Adição ou remoção de conexões entre contatos (arestas).");
        System.out.println("0- Sair");

        int opcao = scanner.nextInt();
        scanner.nextLine();
        switch (opcao) {
            case 1:
            	System.out.println("1- Salvar dados para arquivo.");
            	System.out.println("2- Carregar dados de arquivo.");

            	int op = scanner.nextInt();
            	scanner.nextLine();

            	switch (op) {
            	    case 1:
            	        redeSocial.salvarRedeSocial();
            	        System.out.println("Dados salvos com sucesso!");
            	        break;
            	    case 2:
            	        redeSocial.carregarRedeSocial();
            	        System.out.println("Dados carregados com sucesso!");
            	        break;
            	    default:
            	        System.out.println("Opção inválida");
            	        break;
            	}
            break;
            case 2:
            	System.out.println("Digite o nome: ");
                nome = scanner.nextLine();
                System.out.println("Digite a foto: ");
                foto = scanner.nextLine();
                System.out.println("Digite os interesses separados por vírgula: ");
                interessesString = scanner.nextLine();
                String[] interessesArray = interessesString.split(", ");
                Set<String> interesses = new HashSet<>(Arrays.asList(interessesArray));
                Node novoNo = new Node(nome, foto, interesses);
                redeSocial.adicionarNo(novoNo);
                System.out.println("Novo contato adicionado!");
                break;
            case 3:
            	System.out.println("Digite o nome de origem: ");
                nomeOrigem = scanner.nextLine();
                System.out.println("Digite o nome de destino: ");
                nomeDestino = scanner.nextLine();
                Node origem = redeSocial.buscarNoPorNome(nomeOrigem);
                Node destino = redeSocial.buscarNoPorNome(nomeDestino);
                if (origem != null && destino != null) {
                    boolean alcance = redeSocial.verificarAlcance(origem, destino);
                    if (alcance) {
                        System.out.println("Há um caminho entre " + nomeOrigem + " e " + nomeDestino);
                    } else {
                        System.out.println("Não há um caminho entre " + nomeOrigem + " e " + nomeDestino);
                    }
                } else {
                    System.out.println("Membros não encontrados na rede.");
                }
            break;
            case 4:
        	   System.out.println("Digite o nome do membro: ");
               String nomeMembro = scanner.nextLine();
               Node membro = redeSocial.buscarNoPorNome(nomeMembro);

               if (membro != null) {
                   redeSocial.listarContatos(membro);
               } else {
                   System.out.println("Membro não encontrado na rede.");
               }
            break;
            case 5:
            	System.out.println("1- Adicionar Conexão");
                System.out.println("2- Remover Conexão");
                int opc = scanner.nextInt();
                scanner.nextLine(); 
                switch (opc) {
                    case 1:
                    	System.out.println("Digite o nome do membro de origem: ");
                        nomeOrigem = scanner.nextLine();

                        System.out.println("Digite o nome do membro de destino: ");
                        nomeDestino = scanner.nextLine();

                        System.out.println("Digite o relacionamento: ");
                        String relacionamento = scanner.nextLine();

                        Node origemA = redeSocial.buscarNoPorNome(nomeOrigem);
                        Node destinoA = redeSocial.buscarNoPorNome(nomeDestino);

                        if (origemA != null && destinoA != null) {
                            redeSocial.adicionarConexao(origemA, destinoA, relacionamento);
                        } else {
                            System.out.println("Membros não encontrados na rede.");
                        }                 
                    break;
                    case 2:
                    	System.out.println("Digite o nome do membro de origem: ");
                        nomeOrigem = scanner.nextLine();

                        System.out.println("Digite o nome do membro de destino: ");
                        nomeDestino = scanner.nextLine();

                        Node origemR = redeSocial.buscarNoPorNome(nomeOrigem);
                        Node destinoR = redeSocial.buscarNoPorNome(nomeDestino);

                        if (origemR != null && destinoR != null) {
                            redeSocial.removerConexao(origemR, destinoR);
                        } else {
                            System.out.println("Membros não encontrados na rede.");
                        }
                        break;
                    default:
                        System.out.println("Opção inválida");
                        break;
                }
                break;
            case 0:
                System.out.println("Saindo");
                break;
            default:
                System.out.println("Opção inválida");
                break;
        }

        return opcao;
    }
    
   
}
