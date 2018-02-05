package Searches;

import java.util.HashMap;
import java.util.LinkedList;

public class BreadthFirstSearch {
    private HashMap<String, Integer> verified;
    private LinkedList<Node> expand;
    private boolean find = false;
    private int qtdExpandNodes = -2;
    private int qtdFathers;
    private int qtdChildrens;

    public BreadthFirstSearch(int[] board, int[] shuffleBoard) {
        this.verified = new HashMap<>();
        this.expand = new LinkedList<>();
        this.qtdFathers = 0;
        this.qtdChildrens = 0;

        Node root = new Node(null);
        root.setActualBoard(shuffleBoard);

        Node n = new Node(null);
        n.setActualBoard(board);
        // Adicionando o modelo objetivo
        addInHash(root, 1);
        addInHash(n, 0);
    }

    private void addInHash(Node node, int type) {
        int[] b = node.getActualBoard().clone();
        this.qtdExpandNodes++;

        String key = "";
        for (int i = 0; i < b.length; i++) {
            key += Integer.toString(b[i]);
        }

        if (type == 0) {
            verified.put(key, 0);
        }

        if (verified.containsKey(key)) {
            if (verified.get(key).equals(0) && type == 1) {
                // Arrumar isso aqui depois
                int cont = 0;
                while (node != null) {
//                    int[] aux = node.getActualBoard().clone();
//                    for (int i = 0; i < aux.length; i++) {
//                        System.out.print(aux[i] + " ");
//                    }
//                    System.out.println();
                    cont++;
                    node = node.getFather();
                }
                System.out.println("Tamanho do caminho " + cont);
                expand.clear();
                find = true;
            }
        } else {
            expand.add(node);
            verified.put(key, type);
        }
    }

    private void expandNodes(Node nodeExpand) {
        this.qtdFathers++;
        int[] board = nodeExpand.getActualBoard().clone();

        int zeroPosition = 0;
        int boardSize = board.length;
        int div = (int) Math.sqrt(boardSize);

        for (int i = 0; i < boardSize; i++) {
            if (board[i] == 0) {
                zeroPosition = i;
                break;
            }
        }
        // Estou na última linha
        if (zeroPosition + div >= boardSize) {
            // Estou na borda da direita
            if (zeroPosition % div == div - 1) {
                leftMove(board, zeroPosition, nodeExpand);
                // Estou na borda da esquerda
            } else if (zeroPosition % div == 0) {
                rightMove(board, zeroPosition, nodeExpand);
            } else {
                rightMove(board, zeroPosition, nodeExpand);
                leftMove(board, zeroPosition, nodeExpand);
            }
            // Criando o segundo estado: Movimento para cima
            upMove(board, zeroPosition, nodeExpand, div);
            // Estou na linha superior
        } else if (zeroPosition - div < 0) {
            // Estou na borda da direita
            if (zeroPosition % div == div - 1) {
                leftMove(board, zeroPosition, nodeExpand);
                // Estou na borda da esquerda
            } else if (zeroPosition % div == 0) {
                rightMove(board, zeroPosition, nodeExpand);
            } else {
                rightMove(board, zeroPosition, nodeExpand);
                leftMove(board, zeroPosition, nodeExpand);
            }
            // Movimento para baixo
            downMove(board, zeroPosition, nodeExpand, div);
            // Estou no meio da bagaça
        } else {
            // Estou na borda da direita
            if (zeroPosition % div == div - 1) {
                leftMove(board, zeroPosition, nodeExpand);
                // Estou na borda da esquerda
            } else if (zeroPosition % div == 0) {
                rightMove(board, zeroPosition, nodeExpand);
            } else {
                rightMove(board, zeroPosition, nodeExpand);
                leftMove(board, zeroPosition, nodeExpand);
            }
            upMove(board, zeroPosition, nodeExpand, div);
            downMove(board, zeroPosition, nodeExpand, div);
        }
    }

    private void leftMove(int[] b, int zeroPosition, Node nodeExpand) {
        this.qtdChildrens++;
        int[] board = b.clone();
        // Criando um novo nó e setando o anterior como pai
        Node n = new Node(nodeExpand);
        // realizando o movimento para esquerda
        board[zeroPosition] = board[zeroPosition - 1];
        board[zeroPosition - 1] = 0;
        // Preenchendo o nó
        n.setActualBoard(board);
        n.setDepth(nodeExpand.getDepth() + 1);
        // Adicionando no hash de nós
        addInHash(n, 1);
    }

    private void rightMove(int[] b, int zeroPosition, Node nodeExpand) {
        this.qtdChildrens++;
        int[] board = b.clone();
        // Criando um novo nó e setando o anterior como pai
        Node n = new Node(nodeExpand);
        // realizando o movimento para direita
        board[zeroPosition] = board[zeroPosition + 1];
        board[zeroPosition + 1] = 0;
        // Preenchendo o nó
        n.setActualBoard(board);
        n.setDepth(nodeExpand.getDepth() + 1);
        // Adicionando no hash de nós
        addInHash(n, 1);
    }

    private void upMove(int[] b, int zeroPosition, Node nodeExpand, int div) {
        this.qtdChildrens++;
        int[] board = b.clone();
        // Criando o segundo estado: Movimento para cima
        Node n = new Node(nodeExpand);
        board[zeroPosition] = board[zeroPosition - div];
        board[zeroPosition - div] = 0;
        zeroPosition -= div;
        // Adicionando a fila
        n.setActualBoard(board);
        n.setDepth(nodeExpand.getDepth() + 1);
        addInHash(n, 1);
    }

    private void downMove(int[] b, int zeroPosition, Node nodeExpand, int div) {
        this.qtdChildrens++;
        int[] board = b.clone();
        // Criando o segundo estado: Movimento para baixo
        Node n = new Node(nodeExpand);
        board[zeroPosition] = board[zeroPosition + div];
        board[zeroPosition + div] = 0;
        // Adicionando a pilha
        n.setActualBoard(board);
        n.setDepth(nodeExpand.getDepth() + 1);
        addInHash(n, 1);
    }

    public void breadthFirstSearch() {
        while (!find) {
            expandNodes(expand.pop());
        }
    }

    public double ramificationFactor() {
        return this.qtdChildrens / (double)this.qtdFathers;
    }

    public int getQtdExpandNodes() {
        return qtdExpandNodes;
    }
}
