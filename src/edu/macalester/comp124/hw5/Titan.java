package edu.macalester.comp124.hw5;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by hp on 3/20/14.
 */
public class Titan extends RandomlyMovingAgent{

    public static List<Attack> attacks = new LinkedList<>();
    public boolean defeat = false;

    final int randomNumberSeed   = 23;
    Random randomNumberGenerator = new Random(randomNumberSeed);

    public Titan (String type, String name, Map map, int x, int y, boolean defeat)
    {
        super(type, name, map);
        this.x = x;
        this.y = y;
        this.defeat = defeat;
        initialize();
    }

    public void initialize()
    {
        //attacks.clear();
        attacks.add(Attack.getAttack("Step on you"));
        attacks.add(Attack.getAttack("Catch you in hand and crushes you"));
        attacks.add(Attack.getAttack("Bite of a limb"));
        attacks.add(Attack.getAttack("Destroy your gears"));
        attacks.add(Attack.getAttack("Kill your horse"));
    }

}

