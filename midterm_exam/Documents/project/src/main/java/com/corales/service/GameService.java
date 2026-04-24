package com.corales.service;

public class GameService {

    public static int getWinner(int m1, int m2) {
        if (m1 == m2) return 0;

        if ((m1 == 0 && m2 == 2) ||
            (m1 == 1 && m2 == 0) ||
            (m1 == 2 && m2 == 1)) {
            return 1;
        }

        return 2;
    }
}