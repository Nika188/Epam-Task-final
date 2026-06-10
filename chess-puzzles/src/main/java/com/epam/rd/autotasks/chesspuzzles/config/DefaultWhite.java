package com.epam.rd.autotasks.chesspuzzles.config;

import com.epam.rd.autotasks.chesspuzzles.ChessPiece;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DefaultWhite extends ApplicationConfig{
    @Bean ChessPiece a2() { return piece('p', 'A', 2); }
    @Bean ChessPiece b2() { return piece('p', 'B', 2); }
    @Bean ChessPiece c2() { return piece('p', 'C', 2); }
    @Bean ChessPiece d2() { return piece('p', 'D', 2); }
    @Bean ChessPiece e2() { return piece('p', 'E', 2); }
    @Bean ChessPiece f2() { return piece('p', 'F', 2); }
    @Bean ChessPiece g2() { return piece('p', 'G', 2); }
    @Bean ChessPiece h2() { return piece('p', 'H', 2); }

    @Bean ChessPiece a1() { return piece('r', 'A', 1); }
    @Bean ChessPiece b1() { return piece('n', 'B', 1); }
    @Bean ChessPiece c1() { return piece('b', 'C', 1); }
    @Bean ChessPiece d1() { return piece('q', 'D', 1); }
    @Bean ChessPiece e1() { return piece('k', 'E', 1); }
    @Bean ChessPiece f1() { return piece('b', 'F', 1); }
    @Bean ChessPiece g1() { return piece('n', 'G', 1); }
    @Bean ChessPiece h1() { return piece('r', 'H', 1); }
}
