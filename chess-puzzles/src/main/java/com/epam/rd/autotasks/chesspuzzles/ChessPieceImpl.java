package com.epam.rd.autotasks.chesspuzzles;

public class ChessPieceImpl implements ChessPiece{
    private final Cell cell;
    private final char symbol;

    public ChessPieceImpl(char symbol, Cell cell) {
        this.symbol = symbol;
        this.cell = cell;
    }

    @Override
    public Cell getCell() {
        return cell;
    }

    @Override
    public char toChar() {
        return symbol;
    }
}
