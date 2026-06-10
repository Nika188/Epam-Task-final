package com.epam.rd.autotasks.chesspuzzles.config;

import com.epam.rd.autotasks.chesspuzzles.ChessPiece;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DefaultBlack extends ApplicationConfig{
    @Bean ChessPiece a8() { return piece('R', 'A', 8); }
    @Bean ChessPiece b8() { return piece('N', 'B', 8); }
    @Bean ChessPiece c8() { return piece('B', 'C', 8); }
    @Bean ChessPiece d8() { return piece('Q', 'D', 8); }
    @Bean ChessPiece e8() { return piece('K', 'E', 8); }
    @Bean ChessPiece f8() { return piece('B', 'F', 8); }
    @Bean ChessPiece g8() { return piece('N', 'G', 8); }
    @Bean ChessPiece h8() { return piece('R', 'H', 8); }

    @Bean ChessPiece a7() { return piece('P', 'A', 7); }
    @Bean ChessPiece b7() { return piece('P', 'B', 7); }
    @Bean ChessPiece c7() { return piece('P', 'C', 7); }
    @Bean ChessPiece d7() { return piece('P', 'D', 7); }
    @Bean ChessPiece e7() { return piece('P', 'E', 7); }
    @Bean ChessPiece f7() { return piece('P', 'F', 7); }
    @Bean ChessPiece g7() { return piece('P', 'G', 7); }
    @Bean ChessPiece h7() { return piece('P', 'H', 7); }
}
