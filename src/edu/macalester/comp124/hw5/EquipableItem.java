package edu.macalester.comp124.hw5;

/**
 *
 * @author baylor
 */
public class EquipableItem
{
	public String id, description;
	public int strengthModifier, speedModifier, constitutionModifier;
	public int meleeAttackModifire, rangedAttackModifier, defenseModifier;

	public EquipableItem(String id, String description)
	{
		this.id = id;
		this.description = description;
	}

	@Override
	public String toString()
	{
		return description;
	}
}
