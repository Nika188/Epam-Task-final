package com.epam.rd.autotasks.chesspuzzles.config;

import com.epam.rd.autotasks.chesspuzzles.ChessPiece;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Puzzle03 extends ApplicationConfig{
    @Bean ChessPiece h8() { return piece('r', 'H', 8); }
    @Bean ChessPiece b7() { return piece('P', 'B', 7); }
    @Bean ChessPiece d7() { return piece('Q', 'D', 7); }
    @Bean ChessPiece e7() { return piece('P', 'E', 7); }
    @Bean ChessPiece f7() { return piece('r', 'F', 7); }
    @Bean ChessPiece g6() { return piece('P', 'G', 6); }
    @Bean ChessPiece d6() { return piece('P', 'D', 6); }
    @Bean ChessPiece a6() { return piece('P', 'A', 6); }
    @Bean ChessPiece g5() { return piece('K', 'G', 5); }
    @Bean ChessPiece d5() { return piece('p', 'D', 5); }
    @Bean ChessPiece b4() { return piece('B', 'B', 4); }
    @Bean ChessPiece c3() { return piece('R', 'C', 3); }
    @Bean ChessPiece g2() { return piece('p', 'G', 2); }
    @Bean ChessPiece h2() { return piece('p', 'H', 2); }
    @Bean ChessPiece c1() { return piece('R', 'C', 1); }
    @Bean ChessPiece d1() { return piece('b', 'D', 1); }
    @Bean ChessPiece h1() { return piece('k', 'H', 1); }

}
