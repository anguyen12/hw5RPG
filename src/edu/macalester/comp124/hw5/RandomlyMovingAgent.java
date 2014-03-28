package edu.macalester.comp124.hw5;

import java.util.Random;

/**
 * Created by hp on 3/26/14.
 */
public class RandomlyMovingAgent extends Agent
{
    protected Map map;  // need this so you can know when moves are legal

    public RandomlyMovingAgent(String type, String name, Map map)
    {
        super(type, name);
        setMap(map);
    }

    public void setMap(Map map)
    {
        this.map = map;
    }

    @Override
    public void think()
    {
        Random r = new Random();

        //--- nextInt(x) returns [0-x), a number between 0 and x-1
        //--- Want to move -1 (left/up), 0 (no change) or +1 (right/down)
        int changeInX = r.nextInt(3) - 1; // number between -1 and 1
        int changeInY = r.nextInt(3) - 1;

        //--- If move isn't legal, nothing will happen
        move(x+changeInX, y+changeInY);
    }

    public boolean move(int newX, int newY)
    {
        if (!isLegalMove(newX, newY))
        {
            return false;
        }
        else
        {
            this.x = newX;
            this.y = newY;
            return true;
        }
    }

    protected boolean isLegalMove(int newX, int newY)
    {
        String newTerrainType = map.terrain[newX][newY];
//        System.out.println("new terrain = "+newTerrainType);
        if (map.passibility.containsKey(newTerrainType))
        {
            //--- Impassible terrain, we can't move here
            return false;
        }
        else
        {
            return true;
        }
    }
}

