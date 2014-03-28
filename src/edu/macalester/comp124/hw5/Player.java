package edu.macalester.comp124.hw5;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author baylor
 */
public class Player extends Agent{

    public static List<Attack> attacks = new LinkedList<>();


	public Player()
	{
		super("player", "player");
        initialize();
	}

    public void initialize()
    {
        attacks.clear();
        attacks.add(Attack.getAttack("Stab its eyes"));
        attacks.add(Attack.getAttack("Cut achilles heel"));
        attacks.add(Attack.getAttack("Slice off back of neck"));
        attacks.add(Attack.getAttack("Cut off fingertips"));
        attacks.add(Attack.getAttack("Fire canons at it"));
    }

	@Override
	public void think()
	{
		//--- We don't think, the player thinks for us
	}
}
