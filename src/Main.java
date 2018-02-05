import Searches.BestFirstSearchAStar;
import Searches.BreadthFirstSearch;
import Searches.DepthFirstSearch;
import Searches.IterativeDeepingAStar;
import Utils.BasicFunctions;

public class Main {
    public static void main(String[] args) {
        BasicFunctions bs = new BasicFunctions();
        bs.loadArchive("8_puzzle.txt");

        System.out.println("\nBusca em largura");
        double t0 = System.currentTimeMillis();
        BreadthFirstSearch bfs = new BreadthFirstSearch(bs.getBoard(), bs.getShuffleBoard());
        bfs.breadthFirstSearch();
        System.out.println("Tempo: " + (System.currentTimeMillis() - t0) / 1000);
        System.out.println("Quantidade de nós expandidos: " + bfs.getQtdExpandNodes());
        System.out.println("Fator de ramificação: " + bfs.ramificationFactor());
        System.out.println("|---------------------------------------------------------|");

        System.out.println("Busca em profundidade");
        t0 = System.currentTimeMillis();
        DepthFirstSearch dfs = new DepthFirstSearch(bs.getBoard(), bs.getShuffleBoard());
        dfs.depthFirstSearch();
        System.out.println("Tempo: " + (System.currentTimeMillis() - t0) / 1000);
        System.out.println("Quantidade de nós expandidos: " + dfs.totalExpandedNodes());
        System.out.println("Fator de ramificação: " + dfs.branchingFactor());
        System.out.println("|---------------------------------------------------------|");

        System.out.println("A*");
        t0 = System.currentTimeMillis();
        BestFirstSearchAStar a = new BestFirstSearchAStar(bs.getBoard(), bs.getShuffleBoard());
        a.bestFirstSearchAStar();
        System.out.println("Tempo: " + (System.currentTimeMillis() - t0) / 1000);
        System.out.println("Quantidade de nós expandidos: " + a.getQtdExpandNodes());
        System.out.println("Fator de ramificação: " + a.ramificationFactor());
        System.out.println("|---------------------------------------------------------|");

        System.out.println("IDA*");
        t0 = System.currentTimeMillis();
        IterativeDeepingAStar ida = new IterativeDeepingAStar(bs.getBoard(), bs.getShuffleBoard());
        ida.iterativeDeepingAStar();
        System.out.println("Tempo: " + (System.currentTimeMillis() - t0) / 1000);
        System.out.println("Quantidade de nós expandidos: " + ida.getQtdExpandNodes());
        System.out.println("Fator de ramificação: " + ida.ramificationFactor());
        System.out.println("|---------------------------------------------------------|");
    }
}
