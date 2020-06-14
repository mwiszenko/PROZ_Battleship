package com.mwiszenko.battleship.utils;

import com.mwiszenko.battleship.core.Ship;

import java.util.ArrayList;
import java.util.Collections;

public class SetupProvider {
    private final ArrayList<Ship[]> shipSetups;

    public SetupProvider() {
        shipSetups = new ArrayList<>();
        initList();
    }

    private void initList() {
        addSetup(5, 8, 'h', 4, 4, 'h', 8, 6, 'v', 1, 6, 'h',
                1, 1, 'h');
        addSetup(8, 9, 'h', 0, 8, 'h', 2, 3, 'h', 9, 0, 'v',
                7, 3, 'v');
        addSetup(7, 9, 'h', 6, 4, 'h', 2, 1, 'v', 3, 6, 'h',
                1, 8, 'h');
        addSetup(6, 5, 'h', 0, 1, 'h', 1, 4, 'v', 4, 8, 'h',
                4, 2, 'v');
        addSetup(4, 9, 'h', 0, 9, 'h', 7, 9, 'h', 1, 4, 'v',
                3, 7, 'h');
        addSetup(4, 8, 'h', 2, 4, 'v', 4, 4, 'v', 2, 2, 'h',
                7, 2, 'v');
        addSetup(1, 8, 'h', 8, 1, 'v', 8, 6, 'v', 1, 2, 'h',
                0, 6, 'h');
        addSetup(1, 2, 'v', 4, 3, 'h', 1, 5, 'v', 4, 7, 'h',
                2, 9, 'h');
        addSetup(6, 9, 'h', 1, 2, 'h', 1, 5, 'v', 7, 3, 'v',
                4, 5, 'v');
        addSetup(8, 4, 'v', 1, 1, 'v', 1, 5, 'v', 5, 8, 'h',
                4, 1, 'h');
        Collections.shuffle(shipSetups);
    }

    private void addSetup(int c1, int r1, char v1, int c2, int r2, char v2, int c3, int r3, char v3,
                          int c4, int r4, char v4, int c5, int r5, char v5) {
        Ship[] setup = new Ship[5];
        setup[0] = new Ship(0, 2, c1, r1, v1);
        setup[1] = new Ship(1, 3, c2, r2, v2);
        setup[2] = new Ship(2, 3, c3, r3, v3);
        setup[3] = new Ship(3, 4, c4, r4, v4);
        setup[4] = new Ship(4, 5, c5, r5, v5);
        shipSetups.add(setup);
    }

    public Ship[] getSetup() {
        Ship[] setup = shipSetups.get(0);
        shipSetups.remove(0);
        return setup;
    }
}
