package Searches;

import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Queue;

public class BestFirstSearchAStar {
    private HashMap<String, Integer> verified;
    private Queue<Node> expand;
    private boolean find = false;
    private int[] objective;
    private int qtdExpandNodes = -2;
    private int qtdFathers;
    private int qtdChildrens;

    public BestFirstSearchAStar(int[] board, int[] shuffleBoard) {
        this.qtdFathers = 0;
        this.qtdChildrens = 0;
        this.verified = new HashMap<>();
        this.expand = new PriorityQueue<>(new Comparator<Node>() {
            @Override
            public int compare(Node n1, Node n2) {
                return n1.getCost() < n2.getCost() ? -1 : 1;
            }
        });
        this.objective = board.clone();

        Node root = new Node(null);
        root.setActualBoard(shuffleBoard);
        // distância de Manhattan da raiz
        root.setCost(manhattanDistance(shuffleBoard, (int) Math.sqrt(shuffleBoard.length)));

        Node n = new Node(null);
        n.setActualBoard(board);
        // Adicionando o modelo objetivo
        addInHash(root, 1);
        addInHash(n, 0);
    }

    private void addInHash(Node node, int type) {
        int[] b = node.getActualBoard();
        this.qtdExpandNodes++;

        String key = "";
        for (int i = 0; i < b.length; i++) {
            key += Integer.toString(b[i]);
        }

        if (type == 0) {
            verified.put(key, type);
        }

        if (verified.containsKey(key)) {
            if (verified.get(key).equals(0) && type == 1) {
                // Arrumar isso aqui depois
                int cont = 0;
                while (node != null) {
                    cont++;
//                    int[] aux = node.getActualBoard().clone();
//                    for (int i = 0; i < aux.length; i++) {
//                        System.out.print(aux[i] + " ");
//                    }
//                    System.out.println();
                    node = node.getFather();
                }
                System.out.println("Tamanho do caminho " + cont);
                expand.clear();
                find = true;
            }
        } else {
            verified.put(key, type);
            expand.add(node);
        }
    }


    private void expandNodes(Node nodeExpand) {
        int[] board = nodeExpand.getActualBoard().clone();
        this.qtdFathers++;

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
        n.setCost(nodeExpand.getCost() + manhattanDistance(board, (int) Math.sqrt(board.length)));
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
        n.setCost(nodeExpand.getCost() + manhattanDistance(board, (int) Math.sqrt(board.length)));
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
        n.setCost(nodeExpand.getCost() + manhattanDistance(board, div));

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
        n.setCost(nodeExpand.getCost() + manhattanDistance(board, div));
        addInHash(n, 1);
    }


    private int manhattanDistance(int[] b, int div) {
        int[] board = b.clone();
        int boardSize = board.length;
        int distance = 0;
        for (int i = 0; i < boardSize; i++) {
            if (b[i] != objective[i]) {
                int j = 0;
                for (j = 0; j < boardSize; j++)
                    if (b[i] == objective[j]) break;
                int l1 = i / div;
                int c1 = i % div;
                int l2 = j / div;
                int c2 = j % div;
                distance += (Math.abs(l2 - l1) + Math.abs(c2 - c1));
            }
        }
        return distance;
    }

    public void bestFirstSearchAStar() {
        while (!find) {
            //System.out.println(expand.peek().getColor());
            expandNodes(expand.poll());
        }
    }

    public double ramificationFactor() {
        return (double)this.qtdChildrens / this.qtdFathers;
    }

    public int getQtdExpandNodes() {
        return qtdExpandNodes;
    }
}
