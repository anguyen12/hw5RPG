package edu.macalester.comp124.hw5;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.HashMap;

/**
 *
 * @author baylor
 */
public class MainForm extends javax.swing.JFrame
{
	//<editor-fold defaultstate="collapsed" desc="properties and constants">
	int originalTileWidth = 32, originalTileHeight = 32;
	int tileWidth = originalTileWidth, tileHeight = originalTileHeight;
	float scalingFactor = 1f;

	BufferedImage backBufferContainer;
	Graphics frontBuffer, backBuffer;
	ImageLibrary imageLibrary;
	HashMap<String, Image> scaledImages = new HashMap<>();

	Game game;	// We are the view and controller, game is the model
	//</editor-fold>

	//<editor-fold defaultstate="collapsed" desc="constructors and initialization">
	public MainForm(Game game)
	{
		this.game = game;

		initComponents();
		setLocationRelativeTo(null);

		loadKeyBindings();
		drawingPanel.addMouseListener(new MouseClickHandler(this, "map panel"));

		frontBuffer = drawingPanel.getGraphics();
		backBufferContainer  = new BufferedImage(
				drawingPanel.getWidth(), drawingPanel.getHeight(),
				BufferedImage.TYPE_INT_RGB);
		backBuffer = backBufferContainer.getGraphics();

		tileWidth  *= scalingFactor;
		tileHeight *= scalingFactor;
		loadImages();
		repaint();
	}

	private void loadImages()
	{
		imageLibrary = new ImageLibrary();
		resizeImages();
	}

	private void loadKeyBindings()
	{
		KeyboardFocusManager.getCurrentKeyboardFocusManager()
				.addKeyEventDispatcher(new KeyEventDispatcher()
		{
			@Override
			public boolean dispatchKeyEvent(KeyEvent e)
			{
				boolean keyWasHandled = processKeyPress(e);
				return keyWasHandled;
			}
		});
	}
	//</editor-fold>

	//<editor-fold defaultstate="collapsed" desc="event handling">
	private boolean processKeyPress(KeyEvent e)
	{
		if (e.getID() == KeyEvent.KEY_PRESSED)
		{
			switch(e.getKeyCode())
			{
				case KeyEvent.VK_LEFT:
				case KeyEvent.VK_KP_LEFT:
				case KeyEvent.VK_A:
					game.movePlayer('w');
					repaint();
					return true;	// we handled this key, no one else should hear about it
				case KeyEvent.VK_UP:
				case KeyEvent.VK_KP_UP:
				case KeyEvent.VK_W:
					game.movePlayer('n');
					repaint();
					return true;
				case KeyEvent.VK_RIGHT:
				case KeyEvent.VK_KP_RIGHT:
				case KeyEvent.VK_D:
					game.movePlayer('e');
					repaint();
					return true;
				case KeyEvent.VK_DOWN:
				case KeyEvent.VK_KP_DOWN:
				case KeyEvent.VK_S:
					game.movePlayer('s');
					repaint();
					return true;

				//--- does not resize scaledImages, they're still original size
				//--- either clips image or padds it with blank
				case KeyEvent.VK_PLUS:
				case KeyEvent.VK_EQUALS:
				case KeyEvent.VK_ADD:
					scalingFactor *= 1.5f;
					tileWidth  = (int)(originalTileWidth  * scalingFactor);
					tileHeight = (int)(originalTileHeight * scalingFactor);
					resizeImages();
					repaint();
					return true;
				case KeyEvent.VK_MINUS:
				case KeyEvent.VK_SUBTRACT:
					scalingFactor *= 0.5f;
					tileWidth  = (int)(originalTileWidth  * scalingFactor);
					tileHeight = (int)(originalTileHeight * scalingFactor);
					resizeImages();
					repaint();
					return true;
			}
		}
		//--- This is stupid but it's needed to keep hot keys like the
		//---	+ button from showing up in a text box
		else if (e.getID() == KeyEvent.KEY_TYPED)
		{
			switch(e.getKeyChar())
			{
				case '+':
				case '=':
				case '-':
				case 'w':
				case 'a':
				case 's':
				case 'd':
					return true;
			}
		}

		return false;
	}

	/**
	 * The MouseClickHandler will call this method when a mouse button is clicked.
	 * It will also tell us which part of the form they clicked on. We'll
	 * call different methods depending on which thing they clicked on.
	 * @param x X coordinate of the pixel they clicked on, relative to the top left corner
	 * @param y Y coordinate of the pixel they clicked on, relative to the top left corner
	 */
	public void processMouseClick(int x, int y, String componentID)
	{
		if (componentID.equals("map panel"))
		{
			onClickedOnMap(x,y);
		}
	}

	/**
	 * They clicked on the main map panel.
	 * @param x X coordinate of the pixel they clicked on, relative to the top left corner
	 * @param y Y coordinate of the pixel they clicked on, relative to the top left corner
	 */
	private void onClickedOnMap(int x, int y)
	{
		int tileX = (int)(x / tileWidth);
		int tileY = (int)(y / tileHeight);
	}
	//</editor-fold>

	//<editor-fold defaultstate="collapsed" desc="drawing">
	private void drawTerrain()
	{
		for (int y = 0; y < game.map.getHeight(); y++)
		{
			for (int x = 0; x< game.map.getWidth(); x++)
			{
				String tileType = game.map.terrain[x][y];
				Image  image  = scaledImages.get(tileType);
				backBuffer.drawImage(image, x*tileWidth, y*tileHeight, null);
			}
		}
	}

	private void drawItems()
	{
		for (int y = 0; y < game.map.getHeight(); y++)
		{
			for (int x = 0; x< game.map.getWidth(); x++)
			{
				String tileType = game.map.items[x][y];
				if (null != tileType)
				{
					Image  image  = scaledImages.get(tileType);
					backBuffer.drawImage(image, x*tileWidth, y*tileHeight, null);
				}
			}
		}
	}

	private void drawAgents()
	{
		for (Agent agent : game.agents)
		{
//            System.out.println("drawing agent " + agent.name + " at " + agent.x+","+ agent.y+" image "+ agent.type);
            Image image = scaledImages.get(agent.type);
			backBuffer.drawImage(image,
					agent.x*tileWidth, agent.y*tileHeight,
					null);
		}
	}

	@Override
	public void paint(Graphics g)
	{
		super.paint(g);

		//--- Clear out the old picture we made
		backBuffer.setColor(Color.LIGHT_GRAY);
		backBuffer.fillRect(0, 0, 2048, 2048);

		drawTerrain();
		drawItems();
		drawAgents();

		//--- We drew everything to an offscreen image to avoid flicker,
		//---	now we copy that offscreen image to the screen
		frontBuffer.drawImage(backBufferContainer, 0, 0, null);
	}

	private Image resize(Image original)
	{
		Image scaled  = original.getScaledInstance(tileWidth, tileHeight, Image.SCALE_SMOOTH);
		return scaled;
	}

	private void resizeImages()
	{
		for (String key : imageLibrary.images.keySet())
		{
			Image original = imageLibrary.images.get(key);
			Image scaled   = resize(original);
			scaledImages.put(key, scaled);
		}
	}
	//</editor-fold>

	//<editor-fold defaultstate="collapsed" desc="auto-generated stuff">
	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        drawingPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("1902 PokeExample");

        drawingPanel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout drawingPanelLayout = new javax.swing.GroupLayout(drawingPanel);
        drawingPanel.setLayout(drawingPanelLayout);
        drawingPanelLayout.setHorizontalGroup(
            drawingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 905, Short.MAX_VALUE)
        );
        drawingPanelLayout.setVerticalGroup(
            drawingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 567, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(drawingPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(drawingPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

	/**
	 * @param args the command line arguments
	 */
//	public static void main(String args[])
//	{
//		/* Set the Nimbus look and feel */
//		//<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//		 * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
//		 */
//		try
//		{
//			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels())
//			{
//				if ("Nimbus".equals(info.getName()))
//				{
//					javax.swing.UIManager.setLookAndFeel(info.getClassName());
//					break;
//				}
//			}
//		}
//		catch (ClassNotFoundException ex)
//		{
//			java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//		}
//		catch (InstantiationException ex)
//		{
//			java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//		}
//		catch (IllegalAccessException ex)
//		{
//			java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//		}
//		catch (javax.swing.UnsupportedLookAndFeelException ex)
//		{
//			java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//		}
//		//</editor-fold>
//
//		/* Create and display the form */
//		java.awt.EventQueue.invokeLater(new Runnable()
//		{
//			public void run()
//			{
//				new MainForm().setVisible(true);
//			}
//		});
//	}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel drawingPanel;
    // End of variables declaration//GEN-END:variables

	//</editor-fold>

}
