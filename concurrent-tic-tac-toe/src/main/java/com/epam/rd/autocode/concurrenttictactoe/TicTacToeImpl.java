package com.epam.rd.autocode.concurrenttictactoe;

import java.util.Arrays;

public class TicTacToeImpl implements TicTacToe {
    static Object lock = new Object();
    static GameCoordinator coordinator = new GameCoordinator();
    private final char[][] board = new char[3][3];
    private char lastMark = ' ';

    public TicTacToeImpl() {
        coordinator.turn ='X';
        coordinator.finished=false;
        for (char[] row : board) {
            Arrays.fill(row, ' ');
        }
    }

    @Override
    public synchronized void setMark(int x, int y, char mark) {
        if (board[x][y] != ' ') {
            throw new IllegalArgumentException("Cell already occupied");
        }
        lastMark=mark;
        board[x][y] = mark;
    }

    @Override
    public synchronized char[][] table() {
        char[][] copy = new char[3][3];
        for (int i = 0; i < 3; i++) {
            copy[i] = Arrays.copyOf(board[i], 3);
        }
        return copy;
    }

    @Override
    public char lastMark() {
        return lastMark;
    }
}
