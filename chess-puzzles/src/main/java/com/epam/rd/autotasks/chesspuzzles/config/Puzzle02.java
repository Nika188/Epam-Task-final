package com.epam.rd.autotasks.chesspuzzles.config;

import com.epam.rd.autotasks.chesspuzzles.ChessPiece;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Puzzle02 extends ApplicationConfig{
    @Bean ChessPiece h8() { return piece('q', 'H', 8); }
    @Bean ChessPiece f4() { return piece('Q', 'F', 4); }
    @Bean ChessPiece d3() { return piece('K', 'D', 3); }
    @Bean ChessPiece b2() { return piece('k', 'B', 2); }
    @Bean ChessPiece e2() { return piece('P', 'E', 2); }

}
