package com.epam.rd.autocode.concurrenttictactoe;

public class GameCoordinator {
    char turn = 'X';
    boolean finished = false;

    boolean checkWin(char[][] b, char m) {
        for (int i = 0; i < 3; i++) {
            if (b[i][0] == m && b[i][1] == m && b[i][2] == m) {
                return true;
            }
            if (b[0][i] == m && b[1][i] == m && b[2][i] == m) {
                return true;
            }
        }
        return (b[0][0] == m && b[1][1] == m && b[2][2] == m) ||
                (b[0][2] == m && b[1][1] == m && b[2][0] == m);
    }

    boolean isBoardFull(char[][] b) {
        for (char[] row : b)
            for (char c : row)
                if (c == ' ')
                    return false;
        return true;
    }
}
