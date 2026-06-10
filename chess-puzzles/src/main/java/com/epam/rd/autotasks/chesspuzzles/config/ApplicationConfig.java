package com.epam.rd.autotasks.chesspuzzles.config;

import com.epam.rd.autotasks.chesspuzzles.Cell;
import com.epam.rd.autotasks.chesspuzzles.ChessPiece;
import com.epam.rd.autotasks.chesspuzzles.ChessPieceImpl;

public class ApplicationConfig {
    protected ChessPiece piece(char symbol, char letter, int number) {
        return new ChessPieceImpl(symbol, Cell.cell(letter, number));
    }
}
