package com.epam.rd.autocode.startegy.cards;

import java.util.*;
public class CardDealingStrategies {
    public static CardDealingStrategy texasHoldemCardDealingStrategy() {
        return (deck, players) -> {
            Map<String, List<Card>> stacks = deal(deck,players,2);

            List<Card> community = new ArrayList<>();
            for (int i=0;i<5; i++) {
                community.add(deck.dealCard());
            }
            stacks.put("Community", community);

            stacks= Collections.unmodifiableMap(remaining(deck, stacks));

            return stacks;
        };
    }

    public static CardDealingStrategy classicPokerCardDealingStrategy() {
        return (deck, players) -> {
            Map<String, List<Card>> stacks = deal(deck,players,5);

            stacks= Collections.unmodifiableMap(remaining(deck, stacks));

            return stacks;
        };
    }

    public static CardDealingStrategy bridgeCardDealingStrategy(){
        return (deck, players) -> {
            Map<String, List<Card>> stacks = deal(deck,players,13);

            return stacks;
        };
    }

    public static CardDealingStrategy foolCardDealingStrategy(){
        return (deck, players) -> {
            Map<String, List<Card>> stacks = deal(deck,players,6);

            List<Card> trump = new ArrayList<>();
            trump.add(deck.dealCard());
            stacks.put("Trump card", trump);

            stacks= Collections.unmodifiableMap(remaining(deck, stacks));

            return stacks;
        };
    }
    public static Map<String, List<Card>> deal(Deck deck, int players, int cardsPerPlayer){
        Map<String, List<Card>> stacks = new LinkedHashMap<>();
        for (int p=1;p<=players; p++) {
            stacks.put("Player " + p, new ArrayList<>());
        }

        for (int i=0;i<cardsPerPlayer; i++) {
            for (int p =1;p<=players;p++) {
                stacks.get("Player " + p).add(deck.dealCard());
            }
        }
        return stacks;
    }
    public static Map<String, List<Card>> remaining(Deck deck, Map<String, List<Card>> stacks){
        stacks.put("Remaining", new ArrayList<>(deck.restCards()));
        return stacks;
    }

}
