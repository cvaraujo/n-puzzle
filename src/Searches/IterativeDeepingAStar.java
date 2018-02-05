package Searches;

import java.util.HashMap;
import java.util.LinkedList;

public class IterativeDeepingAStar {
    private HashMap<String, Integer> verified;
    private LinkedList<Node> expand;
    private boolean find = false;
    //private int[] objective;
    private int totalExpand;
    private int bound;
    private int minimum = Integer.MAX_VALUE;
    private Node root;
    private Node objective;
    private int qtdExpandNodes = -2;
    private int qtdFathers;
    private int qtdChildrens;

    public IterativeDeepingAStar(int[] board, int[] shuffleBoard) {
        this.verified = new HashMap<>();
        this.expand = new LinkedList<>();
        this.totalExpand = 0;
        this.qtdFathers = 0;
        this.qtdChildrens = 0;

        this.root = new Node(null);
        this.root.setActualBoard(shuffleBoard.clone());

        this.objective = new Node(null);
        this.objective.setActualBoard(board);
        // distância de Manhattan da raiz
        this.bound = manhattanDistance(shuffleBoard, (int) Math.sqrt(shuffleBoard.length));
        this.root.setCost(this.bound);
        // Adicionando o modelo objetivo
        addInHash(this.root, 1);
        addInHash(this.objective, 0);
    }

    private void addInHash(Node node, int type) {
        int[] b = node.getActualBoard();
        this.qtdExpandNodes++;
        String key = "";
        for (int i = 0; i < b.length; i++) {
            key += Integer.toString(b[i]);
        }

        if (type == 0) {
            this.verified.put(key, type);
        }

        if (this.verified.containsKey(key)) {
            if (this.verified.get(key) == 0 && type == 1) {
                int cont = 0;
                while (node != null) {
//                        int[] aux = node.getActualBoard().clone();
//                        for (int i = 0; i < aux.length; i++) {
//                            System.out.print(aux[i] + " ");
//                        }
//                        System.out.println();
                    node = node.getFather();
                    cont++;
                }
                System.out.println("Tamanho do caminho " + cont);
                this.find = true;
                this.expand.clear();
                this.verified.clear();
            }
        } else {
            this.verified.put(key, type);
            this.expand.add(node);
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
            Node n1 = null, n2 = null, n3 = null;
            // Estou na borda da direita
            if (zeroPosition % div == div - 1) {
                n1 = leftMove(board, zeroPosition, nodeExpand);
                // Estou na borda da esquerda
            } else if (zeroPosition % div == 0) {
                n1 = rightMove(board, zeroPosition, nodeExpand);
            } else {
                n1 = rightMove(board, zeroPosition, nodeExpand);
                n2 = leftMove(board, zeroPosition, nodeExpand);
            }
            // Criando o segundo estado: Movimento para cima
            n3 = upMove(board, zeroPosition, nodeExpand, div);
            // Configurando o valor dos filhos do IDA
            if (n2 != null) {
                if (n1.getCost() <= this.bound) addInHash(n1, 1);
                else if (this.minimum > n1.getCost()) this.minimum = n1.getCost();

                if (n2.getCost() <= this.bound) addInHash(n2, 1);
                else if (this.minimum > n2.getCost()) this.minimum = n2.getCost();

                if (n3.getCost() <= this.bound) addInHash(n3, 1);
                else if (this.minimum > n3.getCost()) this.minimum = n3.getCost();

            } else {
                if (n1.getCost() <= this.bound) addInHash(n1, 1);
                else if (this.minimum > n1.getCost()) this.minimum = n1.getCost();

                if (n3.getCost() <= this.bound) addInHash(n3, 1);
                else if (this.minimum > n3.getCost()) this.minimum = n3.getCost();
            }

            // Estou na linha superior
        } else if (zeroPosition - div < 0) {
            Node n1 = null, n2 = null, n3 = null;

            // Estou na borda da direita
            if (zeroPosition % div == div - 1) {
                n1 = leftMove(board, zeroPosition, nodeExpand);
                // Estou na borda da esquerda
            } else if (zeroPosition % div == 0) {
                n1 = rightMove(board, zeroPosition, nodeExpand);
            } else {
                n1 = rightMove(board, zeroPosition, nodeExpand);
                n2 = leftMove(board, zeroPosition, nodeExpand);
            }
            // Movimento para baixo
            n3 = downMove(board, zeroPosition, nodeExpand, div);

            // Configurando o valor dos filhos do IDA
            if (n2 != null) {
                if (n1.getCost() <= this.bound) addInHash(n1, 1);
                else if (this.minimum > n1.getCost()) this.minimum = n1.getCost();

                if (n2.getCost() <= this.bound) addInHash(n2, 1);
                else if (this.minimum > n2.getCost()) this.minimum = n2.getCost();

                if (n3.getCost() <= this.bound) addInHash(n3, 1);
                else if (this.minimum > n3.getCost()) this.minimum = n3.getCost();

            } else {
                if (n1.getCost() <= this.bound) addInHash(n1, 1);
                else if (this.minimum > n1.getCost()) this.minimum = n1.getCost();

                if (n3.getCost() <= this.bound) addInHash(n3, 1);
                else if (this.minimum > n3.getCost()) this.minimum = n3.getCost();
            }

            // Estou no meio da bagaça
        } else {
            Node n1 = null, n2 = null, n3 = null, n4 = null;
            // Estou na borda da direita
            if (zeroPosition % div == div - 1) {
                n1 = leftMove(board, zeroPosition, nodeExpand);
                // Estou na borda da esquerda
            } else if (zeroPosition % div == 0) {
                n1 = rightMove(board, zeroPosition, nodeExpand);
            } else {
                n1 = rightMove(board, zeroPosition, nodeExpand);
                n2 = leftMove(board, zeroPosition, nodeExpand);
            }
            n3 = upMove(board, zeroPosition, nodeExpand, div);
            n4 = downMove(board, zeroPosition, nodeExpand, div);

            if (n2 != null) {
                if (n1.getCost() <= this.bound) addInHash(n1, 1);
                else if (this.minimum > n1.getCost()) this.minimum = n1.getCost();

                if (n2.getCost() <= this.bound) addInHash(n2, 1);
                else if (this.minimum > n2.getCost()) this.minimum = n2.getCost();

                if (n3.getCost() <= this.bound) addInHash(n3, 1);
                else if (this.minimum > n3.getCost()) this.minimum = n3.getCost();

                if (n4.getCost() <= this.bound) addInHash(n4, 1);
                else if (this.minimum > n4.getCost()) this.minimum = n4.getCost();

            } else {
                if (n1.getCost() <= this.bound) addInHash(n1, 1);
                else if (this.minimum > n1.getCost()) this.minimum = n1.getCost();

                if (n3.getCost() <= this.bound) addInHash(n3, 1);
                else if (this.minimum > n3.getCost()) this.minimum = n3.getCost();

                if (n4.getCost() <= this.bound) addInHash(n4, 1);
                else if (this.minimum > n4.getCost()) this.minimum = n4.getCost();
            }
        }
    }

    private Node leftMove(int[] b, int zeroPosition, Node nodeExpand) {
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
        return n;
    }


    private Node rightMove(int[] b, int zeroPosition, Node nodeExpand) {
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
        return n;
    }


    private Node upMove(int[] b, int zeroPosition, Node nodeExpand, int div) {
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
        // System.out.println("Coloquei pra 0 no movimento pra cima: " + n.getCost());
        //addInHash(n, 1);
        return n;
    }


    private Node downMove(int[] b, int zeroPosition, Node nodeExpand, int div) {
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
        return n;
    }

    private int manhattanDistance(int[] b, int div) {
        int[] board = b.clone();
        int obj[] = this.objective.getActualBoard().clone();
        int boardSize = board.length;
        int distance = 0;
        for (int i = 0; i < boardSize; i++) {
            if (b[i] != obj[i]) {
                int j = 0;
                for (j = 0; j < boardSize; j++)
                    if (b[i] == obj[j]) break;
                int l1 = i / div;
                int c1 = i % div;
                int l2 = j / div;
                int c2 = j % div;
                distance += (Math.abs(l2 - l1) + Math.abs(c2 - c1));
            }
        }
        //System.out.println("Manhattan: " + distance + " Bound: " + this.bound);
        return distance;
    }

    public void iterativeDeepingAStar() {
        while (!this.find) {
            if (this.expand.isEmpty()) {
                this.verified.clear();
                this.bound = this.minimum;
                addInHash(this.objective, 0);
                addInHash(this.root, 1);
                this.minimum = Integer.MAX_VALUE;
                this.qtdFathers = 0;
                this.qtdChildrens = 0;
                this.qtdExpandNodes = -2;
            } else expandNodes(this.expand.pop());
        }
    }

    public double ramificationFactor() {
        return this.qtdChildrens / (double)this.qtdFathers;
    }

    public int getQtdExpandNodes() {
        return qtdExpandNodes;
    }

}


