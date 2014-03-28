package edu.macalester.comp124.hw5;


import java.util.Scanner;

/**
 *
 * @author baylor
 */
public class Application
{

	public static void main(String[] args)
	{
		//--- The game is where all the interesting stuff happens
		//--- Formally, it's called the Model
		//--- The thing that draws the picture is called the View
		//--- The thing that lets players select actions is the Controller
        //--- Allow player to enter name;
       /* System.out.println("Enter your name soldier: ");
        Scanner userInput = new Scanner(System.in);
        String name = userInput.nextLine();
        System.out.println(name + ", are you willing to give your heart up for humanity? (yes/no)");
        String reply = userInput.nextLine();
        if (reply.equals("yes"))*/
        {
            Game theGame = new Game();

            // TODO: Load character screen, create/edit character
            CharacterDesignerForm characterDesign = new CharacterDesignerForm();
            characterDesign.setVisible(true);

            //--- The map screen is a View of our game
            //--- It's also our Controller when navigating the map
            MainForm mapScreen = new MainForm(theGame);
            mapScreen.setVisible(true);
        }
        /*else
        {
            System.out.println("Pray for our safety, " + name);
        }*/
	}
}
