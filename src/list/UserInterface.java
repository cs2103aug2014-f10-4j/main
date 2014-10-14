package list;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;

public class UserInterface implements IUserInterface {
    
	private static JFrame mainFrame = new JFrame("List");
	private static JTextArea console = new JTextArea();
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
	private static Font fontForDate = new Font("Arial", Font.BOLD, 36);
	private static Font fontForTask = new Font("Arial", Font.PLAIN, 36);
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
		displayNewLine("Title: " + task.getTitle() + " (" + task.getCategory() + ")", fontForDate);

		//**********display the place**********//
		// display the place of the task
		displayNewLine("Place: " + task.getPlace(), fontForTask);

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
		displayNewLine(stringForStartTime, fontForTask);

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
		displayNewLine(stringForEndTime, fontForTask);

		//**********display the notes**********//
		// display the notes of the task
		displayNewLine("Notes: " + task.getNotes(), fontForTask);
	}

	public boolean checkDateIsAppeared(ITask task) {

		// return whether the date held was the same with the date of the task
		return previousDate.equals(task.getEndTime());
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
		displayNewLine(stringOfDateToDisplay, fontForDate);

		// display the task as well
		displayNewTask(task);
	}

	public void displayNewTask(ITask task) {
		
		// get the title of the task to be displayed
		String stringOfTaskToDisplay = task.getTitle();

		// display the contents to the window
		displayNewLine(stringOfTaskToDisplay, fontForTask);
	}

	public static void displayNewLine(String stringToDisplay, Font fontForLabel) {

		// make new label that hold the string to be displayed
		JLabel labelOfString = new JLabel(stringToDisplay);

		// set the size of the label
		labelOfString.setBounds(0, numberOfLines * LABELHEIGHT, LABELWIDTH, LABELHEIGHT);

		// set the font of the Label
		labelOfString.setFont(fontForLabel);

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
		
		// append the letter that appears at the first place
		showInConsole(CONSOLE_ARROWS);

		// set the size of the console
		console.setBounds(0, LISTHEIGHT, CONSOLEWIDTH, CONSOLEHEIGHT);

		// add the label to the container
		mainFrame.getContentPane().add(console);
	}

	public void displayMessageToUser(String message) {
	    showInConsole(message);
	    showInConsole("\n");
	    showInConsole(CONSOLE_ARROWS);
	}
	
	private void showInConsole(String text) {
	    console.append(text);
	    mCursorPosition = console.getText().length();
	}
	
	class EnterAction extends AbstractAction {

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

    @Override
    public void displayCategories(List<ICategory> categories) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean isFull() {
        return isFull;
    }

    @Override
    public void clearDisplay() {
        // TODO Auto-generated method stub
        
    }
}