package com.epam.rd.autocode.factory.plot;

import java.util.Arrays;

public class MarvelPlot implements Plot{
    private final Character[] heroes;
    private final EpicCrisis epicCrisis;
    private final Character villain;

    public MarvelPlot(Character[] heroes, EpicCrisis epicCrisis, Character villain) {
        this.heroes = heroes;
        this.epicCrisis = epicCrisis;
        this.villain = villain;
    }

    @Override
    public String toString() {
        StringBuilder heroesStr = new StringBuilder();
        for (int i = 0; i < heroes.length; i++) {
            if (i > 0) heroesStr.append(", ");
            heroesStr.append("brave ").append(heroes[i].name());
        }

        return String.format(
                "%s threatens the world. But %s on guard. So, no way that intrigues of %s overcome the willpower of inflexible heroes",
                epicCrisis.name(),
                heroesStr.toString(),
                villain.name()
        );

    }
}
