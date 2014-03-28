package edu.macalester.comp124.hw5;

/**
 * Created by hp on 3/22/14.
 */
public class Item {
    String name;
    int x, y;

    public Item(String name, Map map)
    {
        this.name = name;
        setMap(map);
    }

    protected Map map;
    public void setMap(Map map)
    {
        this.map = map;
    }

}
