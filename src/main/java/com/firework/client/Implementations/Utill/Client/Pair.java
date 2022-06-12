package com.firework.client.Implementations.Utill.Client;

import java.util.ArrayList;

public class Pair {
    public Object one;
    public Object two;

    public Pair(Object one, Object two) {
        this.one = one;
        this.two = two;
    }

    public Pair() {
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        ArrayList<Object> objects = new ArrayList<Object>();
        objects.add(this.one);
        objects.add(this.two);
        return objects.contains(((Pair)o).one) && objects.contains(((Pair)o).two);
    }

    public static boolean containsPair(ArrayList<Pair> pairs, Pair pair) {
        for (Pair pair2 : pairs) {
            if (!pair2.equals(pair)) continue;
            return true;
        }
        return false;
    }
}
