package com.epam.rd.autotasks.chesspuzzles.config;

import com.epam.rd.autotasks.chesspuzzles.ChessPiece;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Puzzle01 extends ApplicationConfig{
    @Bean ChessPiece e8() { return piece('R', 'E', 8); }
    @Bean ChessPiece b7() { return piece('k', 'B', 7); }
    @Bean ChessPiece c6() { return piece('n', 'C', 6); }
    @Bean ChessPiece d6() { return piece('B', 'D', 6); }
    @Bean ChessPiece f6() { return piece('P', 'F', 6); }
    @Bean ChessPiece c5() { return piece('K', 'C', 5); }
    @Bean ChessPiece d5() { return piece('B', 'D', 5); }
    @Bean ChessPiece c3() { return piece('P', 'C', 3); }
    @Bean ChessPiece d2() { return piece('p', 'D', 2); }
    @Bean ChessPiece e2() { return piece('b', 'E', 2); }


}
