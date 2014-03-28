package edu.macalester.comp124.hw5;

/**
 * Created by hp on 3/22/14.
 */
public class Attack {
    public int damage;
    public String name = "";

    public static Attack getAttack(String attackName)
    {
        Attack attack = new Attack();

        //--- Case statements are like a big IF-THEN statement
        if (attackName.equals("Stab its eyes"))
        {
            attack.name = "stab its eyes";
            attack.damage  = 5;
        }
        else if (attackName.equals("Cut achilles heel"))
        {
            attack.name = "cut achilles heel";
            attack.damage  = 10;
        }
        else if (attackName.equals("Slice off back of neck"))
        {
            attack.name = "slice off back of neck";
            attack.damage  = 30;
        }
        else if (attackName.equals("Cut off fingertips"))
        {
            attack.name = "cut off fingertips";
            attack.damage  = 5;
        }
        else if (attackName.equals("Fire canons at it"))
        {
            attack.name = "fire canons at it";
            attack.damage  = 3;
        }
        else if (attackName.equals("Step on you"))
        {
            attack.name = "step on you";
            attack.damage  = 35;
        }
        else if (attackName.equals("Catch you in hand and crushes you"))
        {
            attack.name = "catch you in hand and crushed you";
            attack.damage  = 45;
        }
        else if (attackName.equals("Bite off a limb"))
        {
            attack.name = "bite off a limb";
            attack.damage  = 25;
        }
        else if (attackName.equals("Destroy your gears"))
        {
            attack.name = "destroy your gear";
            attack.damage  = 10;
        }
        else if (attackName.equals("Kill your horse"))
        {
            attack.name = "kill your horse";
            attack.damage  = 5;
        }
        return attack;
    }
}
