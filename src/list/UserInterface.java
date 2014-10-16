package list;

import java.util.ArrayList;
import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;

import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.util.List;

public class UserInterface implements IUserInterface {
    
	private static JFrame mainFrame = new JFrame("List");
	private static JTextArea console = new JTextArea();
	private static JScrollPane scrollPanel = new JScrollPane(console);
	private static int mCursorPosition = 0;
	private final static String CONSOLE_ARROWS = ">> ";
	private final static int MAINFRAMEWIDTH = 700;
	private final static int MAINFRAMEHEIGHT = 700;
	private final static int NUMBEROFLINESALLOWED = 10;
	private static int CONSOLEWIDTH = MAINFRAMEWIDTH;
	private static int CONSOLEHEIGHT = MAINFRAMEHEIGHT * 2 / 7;
	private static int LISTWIDTH = MAINFRAMEWIDTH;
	private static int LISTHEIGHT = MAINFRAMEHEIGHT - CONSOLEHEIGHT;
	private static int LABELWIDTH = MAINFRAMEWIDTH;
	private static int LABELHEIGHT = LISTHEIGHT / NUMBEROFLINESALLOWED;
	private static ArrayList<JLabel> arrayListOfJLabel = new ArrayList<JLabel>();
	private static Font fontForDate = new Font("American Typewriter", Font.BOLD, 36);
	private static Font fontForTask = new Font("American Typewriter", Font.PLAIN, 36);
	private static Font fontForConsole = new Font("American Typewriter", Font.PLAIN, 12);
	private static Date previousDate = new Date(0, 0, 0);
	private static boolean isFull = false;
	private static int numberOfLines = 0;

	public UserInterface() {
	    
		// set the size of the frame
		mainFrame.setSize(MAINFRAMEWIDTH, MAINFRAMEHEIGHT);

		// set the window location to the center of the screen
		mainFrame.setLocationRelativeTo(null);

		// set that BorderLayout cannot be used in the window
		mainFrame.setLayout(null);

		// set that the application quit when the window closed
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// set the console part on the frame
		setUpConsole();

		// make the window visible to the user
		mainFrame.setVisible(true);
	}

	public void clearAll() {
		for (int i = 0; i <  arrayListOfJLabel.size(); i++) {		
			mainFrame.getContentPane().remove(arrayListOfJLabel.get(i));
		}
		mainFrame.repaint();
		arrayListOfJLabel = new ArrayList<JLabel>();
		numberOfLines = 0;
	}

	@Override
	public void displayNewTaskOrDate(ITask task) throws DisplayFullException {

		// return if the lines are full
		if (numberOfLines == NUMBEROFLINESALLOWED - 1) {
		    throw new DisplayFullException();
		}

		// check whether the date is the same with the date of the previous task
		// if it's not the same, display the new date
		// if it's the same, display the task
		if (!checkDateIsAppeared(task)) {
			displayNewDate(task);
		} else {
			displayNewTask(task);
		}
		
	}

	public void displayTaskDetail(ITask task) {

		//**********display the title and category**********//
		// display the title of the task
	    displayNewLine("Title: " + task.getTitle() + " (" + task.getCategory() + ")", fontForDate, getCategoryColor(task));

		//**********display the place**********//
		// display the place of the task
		displayNewLine("Place: " + task.getPlace(), fontForTask, Color.BLACK);

		//**********display the start time**********//
		// string for the start time
		String stringForStartTime = "Start time: ";
		
		// get the start date to be displayed
		Date startDateToDisplay = task.getStartTime();

		// set the dates to the string to be displayed
		stringForStartTime += startDateToDisplay.getDay();
		stringForStartTime += "/";
		stringForStartTime += startDateToDisplay.getMonth();
		stringForStartTime += "/";
		stringForStartTime += startDateToDisplay.getYear();

		// display the start time of the task
		displayNewLine(stringForStartTime, fontForTask, Color.BLACK);

		//**********display the end time**********//
		// string for the end time
		String stringForEndTime = "End time: ";
		
		// get the end date to be displayed
		Date endDateToDisplay = task.getEndTime();

		// set the dates to the string to be displayed
		stringForEndTime += endDateToDisplay.getDay();
		stringForEndTime += "/";
		stringForEndTime += endDateToDisplay.getMonth();
		stringForEndTime += "/";
		stringForEndTime += endDateToDisplay.getYear();

		// display the end time of the task
		displayNewLine(stringForEndTime, fontForTask, Color.BLACK);

		//**********display the notes**********//
		// display the notes of the task
		displayNewLine("Notes: " + task.getNotes(), fontForTask, Color.BLACK);
	}

	/**
	 * Returns whether the UI already contains another Task with the same 
	 * deadline as the task supplied.
	 * @param task 
	 * @return
	 */
	public boolean checkDateIsAppeared(ITask task) {
	    boolean dateHasAppeared = false;
	    if (previousDate != null) {
	        dateHasAppeared = previousDate.equals(task.getEndTime());
	    }
	    assert(task.getEndTime() != null);
	    previousDate = task.getEndTime();
		return dateHasAppeared;
	}

	public void displayNewDate(ITask task) {

		// get the date to be displayed
		Date dateToDisplay = task.getEndTime();

		String stringOfDateToDisplay = "";

		// set the dates to the string to be displayed
		stringOfDateToDisplay += dateToDisplay.getDay();
		stringOfDateToDisplay += "/";
		stringOfDateToDisplay += dateToDisplay.getMonth();
		stringOfDateToDisplay += "/";
		stringOfDateToDisplay += dateToDisplay.getYear();

		// display the contents to the window
		displayNewLine(stringOfDateToDisplay, fontForDate, getCategoryColor(task));

		// display the task as well
		displayNewTask(task);
	}

	public void displayNewTask(ITask task) {
		
		// get the title of the task to be displayed
		String stringOfTaskToDisplay = task.getTitle();

		// display the contents to the window
		displayNewLine(stringOfTaskToDisplay, fontForTask, getCategoryColor(task));
	}
	
	/**
	 * Returns the color of the category of the given task if the category is not null.
	 * If the task's category is null, will return the color of Category.getDefaultCategory()
	 * @param task
	 * @return <code>java.awt.Color</code>. Will never return <code>null</code>.
	 * @author andhieka
	 */
	private Color getCategoryColor(ITask task) {
	    if (task.getCategory() == null) {
	        return Category.getDefaultCategory().getColor();
	    } else {
	        return task.getCategory().getColor();
	    }
	}

	public static void displayNewLine(String stringToDisplay, Font fontForLabel, Color textColor) {

		// make new label that hold the string to be displayed
		JLabel labelOfString = new JLabel(stringToDisplay);

		// set the size of the label
		labelOfString.setBounds(0, numberOfLines * LABELHEIGHT, LABELWIDTH, LABELHEIGHT);

		// set the font of the Label
		labelOfString.setFont(fontForLabel);

		// set the color of the label
		labelOfString.setForeground(textColor);

		// add the label to the container
		mainFrame.getContentPane().add(labelOfString);

		// save the reference of the JLabel
		arrayListOfJLabel.add(labelOfString);

		// update the 
		SwingUtilities.updateComponentTreeUI(mainFrame);

		// increment the number of the lines already displayed, and if it is maximum, isFull = true
		numberOfLines++;
		if (numberOfLines == NUMBEROFLINESALLOWED - 1) {
		    isFull = true;
		}
	}

	public void setUpConsole() {

		//use key bindings
		EnterAction enterAction = new EnterAction();
		console.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "doEnterAction");
		console.getActionMap().put("doEnterAction", enterAction);
		
		//use key bindings
		DeleteAction deleteAction = new DeleteAction();
		console.getInputMap().put(KeyStroke.getKeyStroke("BACK_SPACE"), "doDeleteAction");
		console.getActionMap().put("doDeleteAction", deleteAction);
		
		// append the letter that appears at the first place
		showInConsole(CONSOLE_ARROWS);

		// set the size of the scrollPanel
		scrollPanel.setBounds(0, LISTHEIGHT, CONSOLEWIDTH, CONSOLEHEIGHT);
		
		// set the font of the console
		console.setFont(fontForConsole);
		
		// set the position of the cursor to the last of the console
		console.setCaretPosition(console.getDocument().getLength());

		// add the label to the container
		mainFrame.getContentPane().add(scrollPanel);
	}

	public void displayMessageToUser(String message) {
	    showInConsole(message);
	    showInConsole("\n");
	    showInConsole(CONSOLE_ARROWS);
	    console.setCaretPosition(console.getDocument().getLength());
	}
	
	private void showInConsole(String text) {
	    console.append(text);
	    mCursorPosition = console.getText().length();
	}
	
	private class EnterAction extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent event) {
            System.out.println("Enter pressed!");
            String userInput = "";
            try {
                userInput = console.getText(mCursorPosition, console.getText().length() - mCursorPosition);    
            } catch (BadLocationException e) {
                e.printStackTrace(); //who cares?
            }
            
            String reply = Controller.processUserInput(userInput);
            showInConsole("\n");
            displayMessageToUser(reply);
        }
	    
	}
	
	private class DeleteAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent event) {
            System.out.println("Delete pressed!");
            
        }
	}

    @Override
    public void displayCategories(List<ICategory> categories) {
        // TODO Auto-generated method stub
    }

    @Override
    public boolean isFull() {
        return isFull;
    }

    @Override
    public void clearDisplay(){
    	
    }
}