package com.teamsluis.thelastninjageek;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.KeyStroke;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.teamsluis.thelastninjageek.data.Answer;
import com.teamsluis.thelastninjageek.data.DataPersister;
import com.teamsluis.thelastninjageek.data.Question;

public class Main {
	public static int choice = 0;

	public static JLabel opPic = new JLabel();
	public static JFrame gameWindow;
	public static Container oldPane = new Container();
	public static JLabel newPane = new JLabel();
	
	private final static String xmlLocation = "resources/data/questions.xml";

	public void createGameWindow() {

		// Create and display window frames.
		gameWindow = new JFrame("The Last Ninja Geek™");
		gameWindow.getContentPane().setLayout(null);
		gameWindow.setPreferredSize(new Dimension(800, 600));
		gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameWindow.setResizable(false); // just for now
		gameWindow.pack();
		gameWindow.setVisible(true);
		oldPane = gameWindow.getContentPane();

		// Images --
		// This(and the one in options) is set to my local file path to get it
		// working change it with yours
		ImageIcon picLabel = new ImageIcon(
				"/home/shade/git/TheLastNinjaGeek/TheLastNinjaGeek/resources/images/ninja.jpg");

		newPane.setIcon(picLabel);
		gameWindow.setContentPane(newPane);
	}

	public static void displayMenu() {
		newPane.requestFocus(false);
		// List
		String[] bName = { "SINGLE PLAYER", " MULTY PLAYER", "     OPTIONS",
				"       RULES", "        EXIT" };
		JLabel[] menuList = new JLabel[bName.length];
		int gap = 340;
		for (int i = 0; i < menuList.length; i++) {
			menuList[i] = new JLabel();
			menuList[i].setFont(new java.awt.Font("Tahoma", 0, 18));
			menuList[i].setText(bName[i]);
			menuList[i].setBounds(330, gap, 145, 30);
			menuList[i].setOpaque(true);
			menuList[i].setBackground(Color.BLACK);
			gameWindow.add(menuList[i]);
			gap += 40;
		}

		// Key EVENTS
		final Color bkgr = menuList[0].getBackground();
		menuList[choice].setBackground(Color.white);
		newPane.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0),
				"up");
		newPane.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0),
				"enter");
		newPane.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0),
				"down");
		newPane.getActionMap().put("up", new AbstractAction() {

			public void actionPerformed(ActionEvent e) {
				menuList[choice].setBackground(bkgr);
				choice--;
				if (choice < 0) {
					choice = 4;
				}
				menuList[choice].setBackground(Color.white);
			}
		});
		newPane.getActionMap().put("down", new AbstractAction() {

			public void actionPerformed(ActionEvent e) {
				menuList[choice].setBackground(bkgr);
				choice++;
				if (choice > 4) {
					choice = 0;
				}
				menuList[choice].setBackground(Color.white);
			}
		});

		newPane.getActionMap().put("enter", new AbstractAction() {

			public void actionPerformed(ActionEvent e) {
				Data.setNinja();
				gameWindow.setContentPane(oldPane);

				if (choice == 0) {
					// CategoriesMenu.displayCategories();
					Game.gameComencing();
				} else if (choice == 1 && Data.ninjas > 1) {
					Multyplayer.displayMulty();
				} else if (choice == 2) {
					Options.optionsPanel();
				} else if (choice == 4) {
					WindowEvent wEv = new WindowEvent(gameWindow,
							WindowEvent.WINDOW_CLOSING);
					Toolkit.getDefaultToolkit().getSystemEventQueue()
							.postEvent(wEv);
				}

			}
		});
	}

	public static void main(String[] args) {

		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Main main = new Main();
				main.createGameWindow();
				displayMenu();
			}
		});
	}

	private static void DataPersisterDemo() {
		try {
			Map<String, java.util.List<Question>> someVar = DataPersister.loadCategoriesFromXmlFile(xmlLocation);

			for (Map.Entry<String, java.util.List<Question>> entry : someVar
					.entrySet()) {
				System.out.println(entry.getKey());

				for (Question question : entry.getValue()) {
					System.out.println(question.getValue());

					for (Answer answer : question.getAnswers()) {
						System.out.println(answer.getId());
						System.out.println(answer.getValue());
					}
				}

				System.out.println("---------------------------");
			}

		} catch (ParserConfigurationException | SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
