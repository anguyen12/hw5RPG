package edu.macalester.comp124.hw5;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 *
 * @author baylor
 */
public class MouseClickHandler extends MouseAdapter
{
	private MainForm listener;
	private String   componentID;

	public MouseClickHandler(MainForm listener, String componentID)
	{
		this.listener = listener;
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
		Point clickPosition = e.getPoint();
		int x = clickPosition.x;
		int y = clickPosition.y;
		listener.processMouseClick(x, y, componentID);
	}
}
