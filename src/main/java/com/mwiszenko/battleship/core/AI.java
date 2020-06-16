package com.mwiszenko.battleship.core;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;

// AI implementation
// shuffles all possible tiles, so the next move is always random, no tactics implemented

public class AI {
    private final ArrayList<AbstractMap.SimpleEntry<Integer, Integer>> possibleMoves;

    public AI() {
        possibleMoves = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                AbstractMap.SimpleEntry<Integer, Integer> entry = new AbstractMap.SimpleEntry<>(i, j);
                possibleMoves.add(entry);
            }
        }
        Collections.shuffle(possibleMoves);
    }

    public AbstractMap.SimpleEntry<Integer, Integer> getNextMove() {
        AbstractMap.SimpleEntry<Integer, Integer> entry = possibleMoves.get(0);
        possibleMoves.remove(0);
        return entry;
    }
}