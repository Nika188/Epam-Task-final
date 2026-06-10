package com.epam.rd.autotasks.chesspuzzles;

import java.util.Collection;

public class ChessBoardImpl implements ChessBoard {
    private final char[][] board = new char[8][8];

    ChessBoardImpl(Collection<ChessPiece> pieces) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                board[row][col] = '.';
            }
        }
        for (ChessPiece piece : pieces) {
            Cell cell = piece.getCell();
            int col = cell.letter - 'A';
            int row = 8 - cell.number;

            board[row][col] = piece.toChar();
        }
    }

    @Override
    public String state() {
        StringBuilder sb = new StringBuilder();

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                sb.append(board[row][col]);
            }
            if (row < 7) {
                sb.append('\n');
            }
        }

        return sb.toString();
    }
}
