package Searches;

public class Node {
    private int actualBoard[];
    private Node father;
    private int color = 0;
    private int depth = 0;
    private int qtdSons = 0;
    private int cost;

    public Node(Node father) {
        this.father = father;
    }

    public int[] getActualBoard() {
        return actualBoard;
    }

    public void setActualBoard(int[] actualBoard) {
        this.actualBoard = actualBoard;
    }

    public Node getFather() {
        return father;
    }

    public void setFather(Node father) {
        this.father = father;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getMinSon() { return qtdSons; }

    public void setMinSon(int minSon) {
        this.qtdSons = minSon;
    }
}
