package Utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

public class BasicFunctions {

    private int N;
    private int[] board;
    private int[] shuffleBoard;

    public void loadArchive(String archiveName) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(archiveName));
            String instanceSize = br.readLine();
            int voidSpace = 0;
            N = Integer.parseInt(instanceSize) + 1;

            board = new int[N];
            shuffleBoard = new int[N];

            String line = "";
            while (br.ready()) {
                line += br.readLine();
            }

            String[] values = line.split(" ");
            for (int i = 0; i < N; i++) {
                board[i] = Integer.parseInt(values[i]);
                if (board[i] == 0) {
                    voidSpace = i;
                }
            }

            shuffle(voidSpace);
        } catch (IOException io) {
            System.out.println("Error:" + io.getCause());
        }
    }

    private void shuffle(int i) {
        Random rand = new Random();
        int move = 0;
        int div = (int) Math.sqrt(N);
        int totalShuffle = 30;
        shuffleBoard = board.clone();

        for (int s : shuffleBoard) {
            System.out.print(s + " ");
        }
        System.out.println();

        while (totalShuffle > 0) {
            if (shuffleBoard[i] == 0) {
                // Estou na linha de baixo
                if (i + div >= N) {
                    // Estou na borda da direita
                    if (i % div == div - 1) {
                        move = rand.nextInt(2);
                        if (move % 2 == 1) {
                            // Faço a troca pra uma posição antecessora
                            shuffleBoard[i] = shuffleBoard[i - 1];
                            shuffleBoard[i - 1] = 0;
                            i -= 1;
                        } else {
                            // Faço a troca pra uma posição acima
                            shuffleBoard[i] = shuffleBoard[i - div];
                            shuffleBoard[i - div] = 0;
                            i = i - div;
                        }
                        // Estou na borda da esquerda
                    } else if (i % div == 0) {
                        move = rand.nextInt(2);
                        if (move % 2 == 0) {
                            // Faço a troca pra uma posição sucessora
                            shuffleBoard[i] = shuffleBoard[i + 1];
                            shuffleBoard[i + 1] = 0;
                            i += 1;
                        } else {
                            // Faço a troca pra uma posição acima
                            shuffleBoard[i] = shuffleBoard[i - div];
                            shuffleBoard[i - div] = 0;
                            i = i - div;
                        }
                        // Estou no meio
                    } else {
                        move = rand.nextInt(3);
                        if (move == 1) {
                            // Faço a troca pra uma posição sucessora
                            shuffleBoard[i] = shuffleBoard[i + 1];
                            shuffleBoard[i + 1] = 0;
                            i += 1;
                        } else if (move == 0) {
                            // Faço a troca pra uma posição antecessora
                            shuffleBoard[i] = shuffleBoard[i - 1];
                            shuffleBoard[i - 1] = 0;
                            i -= 1;
                        } else {
                            // Faço a troca pra uma posição acima
                            shuffleBoard[i] = shuffleBoard[i - div];
                            shuffleBoard[i - div] = 0;
                            i -= div;
                        }

                    }
                    // Estou na linha superior
                } else if (i - div < 0) {
                    // Estou na borda da direita
                    if (i % div == div - 1) {
                        move = rand.nextInt(2);
                        if (move % 2 == 1) {
                            // Faço a troca pra uma posição antecessora
                            shuffleBoard[i] = shuffleBoard[i - 1];
                            shuffleBoard[i - 1] = 0;
                            i -= 1;
                        } else {
                            // Faço a troca pra uma posição abaixo
                            shuffleBoard[i] = shuffleBoard[i + div];
                            shuffleBoard[i + div] = 0;
                            i += div;
                        }
                        // Estou na borda da esquerda
                    } else if (i % div == 0) {
                        move = rand.nextInt(2);
                        if (move % 2 == 0) {
                            // Faço a troca pra uma posição sucessora
                            shuffleBoard[i] = shuffleBoard[i + 1];
                            shuffleBoard[i + 1] = 0;
                            i += 1;
                        } else {
                            // Faço a troca pra uma posição abaixo
                            shuffleBoard[i] = shuffleBoard[i + div];
                            shuffleBoard[i + div] = 0;
                            i += div;
                        }
                        // Estou no meio
                    } else {
                        move = rand.nextInt(3);
                        if (move == 1) {
                            // Faço a troca pra uma posição sucessora
                            shuffleBoard[i] = shuffleBoard[i + 1];
                            shuffleBoard[i + 1] = 0;
                            i += 1;
                        } else if (move == 0) {
                            // Faço a troca pra uma posição antecessora
                            shuffleBoard[i] = shuffleBoard[i - 1];
                            shuffleBoard[i - 1] = 0;
                            i -= 1;
                        } else {
                            // Faço a troca pra uma posição abaixo
                            shuffleBoard[i] = shuffleBoard[i + div];
                            shuffleBoard[i + div] = 0;
                            i += div;
                        }

                    }
                    // Estou no meio da bagaça
                } else {
                    // Estou na borda da direita
                    if (i % div == div - 1) {
                        move = rand.nextInt(3);
                        if (move == 1) {
                            // Faço a troca pra uma posição antecessora
                            shuffleBoard[i] = shuffleBoard[i - 1];
                            shuffleBoard[i - 1] = 0;
                            i -= 1;
                        } else if (move == 2) {
                            // Faço a troca pra uma posição abaixo
                            shuffleBoard[i] = shuffleBoard[i + div];
                            shuffleBoard[i + div] = 0;
                            i += div;
                        } else {
                            // Faço a troca pra uma posição acima
                            shuffleBoard[i] = shuffleBoard[i - div];
                            shuffleBoard[i - div] = 0;
                            i -= div;
                        }
                        // Estou na borda da esquerda
                    } else if (i % div == 0) {
                        move = rand.nextInt(3);
                        if (move == 1) {
                            // Faço a troca pra uma posição sucessora
                            shuffleBoard[i] = shuffleBoard[i + 1];
                            shuffleBoard[i + 1] = 0;
                            i += 1;
                        } else if (move == 2) {
                            // Faço a troca pra uma posição abaixo
                            shuffleBoard[i] = shuffleBoard[i + div];
                            shuffleBoard[i + div] = 0;
                            i += div;
                        } else {
                            // Faço a troca pra uma posição acima
                            shuffleBoard[i] = shuffleBoard[i - div];
                            shuffleBoard[i - div] = 0;
                            i -= div;
                        }
                    } else {
                        move = rand.nextInt(4);
                        if (move == 1) {
                            // Faço a troca pra uma posição sucessora
                            shuffleBoard[i] = shuffleBoard[i + 1];
                            shuffleBoard[i + 1] = 0;
                            i += 1;
                        } else if (move == 0) {
                            // Faço a troca pra uma posição antecessora
                            shuffleBoard[i] = shuffleBoard[i - 1];
                            shuffleBoard[i - 1] = 0;
                            i -= 1;
                        } else if (move == 2) {
                            // Faço a troca pra uma posição abaixo
                            shuffleBoard[i] = shuffleBoard[i + div];
                            shuffleBoard[i + div] = 0;
                            i += div;
                        } else {
                            // Faço a troca pra uma posição acima
                            shuffleBoard[i] = shuffleBoard[i - div];
                            shuffleBoard[i - div] = 0;
                            i -= div;
                        }
                    }
                }
            }
            totalShuffle--;
        }

        for (int s : shuffleBoard) {
            System.out.print(s + " ");
        }
    }

    public int getN() {
        return N;
    }

    public void setN(int n) {
        N = n;
    }

    public int[] getBoard() {
        return board;
    }

    public void setBoard(int[] board) {
        this.board = board;
    }

    public int[] getShuffleBoard() {
        return shuffleBoard;
    }

    public void setShuffleBoard(int[] shuffleBoard) {
        this.shuffleBoard = shuffleBoard;
    }
}