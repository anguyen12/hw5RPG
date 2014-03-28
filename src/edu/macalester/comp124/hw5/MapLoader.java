package edu.macalester.comp124.hw5;

import java.util.List;

/**
 *
 * @author baylor
 */
public class MapLoader
{
	static public String[][] getMap(String fqn)
	{
		int width, height;
		String[][] map;

		List<String> lines = DataLoader.loadLinesFromFile(fqn);
		String[] tokens = lines.get(0).split(" ");

		width  = tokens.length;
		height = lines.size();
		map = new String[width][height];

		for (int y = 0; y < lines.size(); y++)
		{
			String line = lines.get(y);
			tokens = line.split(" ");
			for (int x = 0; x < tokens.length; x++)
			{
				map[x][y] = tokens[x];
			}
		}

		return map;
	}
}
