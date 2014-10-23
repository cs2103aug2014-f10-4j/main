package list;

import java.util.ArrayList;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.text.Document;
import javax.swing.text.BadLocationException;

import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.font.TextAttribute;
import java.util.List;

import list.ITask.TaskStatus;

public class UserInterface implements IUserInterface {
    private static final Color COLOR_FOR_DATE = Color.BLACK;

    private static UserInterface singletonInstance = null;
    
	private static JFrame mainFrame = new JFrame("List");
	private static JTextArea console = new JTextArea();
	private static JScrollPane scrollPanel = new JScrollPane(console);
	private static int mCursorPosition = 0;
	private final static String CONSOLE_ARROWS = ">> ";
	private final static int MAINFRAMEWIDTH = 700;
	private final static int MAINFRAMEHEIGHT = 700;
	private final static int NUMBER_OF_LINES_ALLOWED = 13;
	private static int CONSOLEWIDTH = MAINFRAMEWIDTH;
	private static int CONSOLEHEIGHT = MAINFRAMEHEIGHT * 2 / 7;
	private static int LISTWIDTH = MAINFRAMEWIDTH;
	private static int LISTHEIGHT = MAINFRAMEHEIGHT - CONSOLEHEIGHT;
	private static int LABELWIDTH = MAINFRAMEWIDTH;
	private static int LABELHEIGHT = LISTHEIGHT / NUMBER_OF_LINES_ALLOWED;
	private static ArrayList<JLabel> arrayListOfJLabel = new ArrayList<JLabel>();
	private static Font fontForDate = new Font("American Typewriter", Font.BOLD, 36);
	private static Font fontForTask = new Font("American Typewriter", Font.PLAIN, 36);
	private static Font fontForDoneTask = null;
	private static Font fontForConsole = new Font("American Typewriter", Font.PLAIN, 12);
	private Date previousDate = null;
	private boolean hasFloatingTask = false;
	private static int numberOfLines = 0;
	private static int numberOfTasks = 0;

	/**
	 * UserInterface is a singleton class.
	 * Use getInstance() get the UserInterface object.
	 */
	private UserInterface() {
	    Map fontAttributes = fontForTask.getAttributes();
	    fontAttributes.put(TextAttribute.STRIKETHROUGH, TextAttribute.STRIKETHROUGH_ON);
	    fontForDoneTask = new Font(fontAttributes);
	    
	    
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
	
	public static UserInterface getInstance() {
	    if (UserInterface.singletonInstance == null) {
	        UserInterface.singletonInstance = new UserInterface();
	    }
	    return UserInterface.singletonInstance;
	}

	@Override
	public void clearDisplay() {
		for (int i = 0; i <  arrayListOfJLabel.size(); i++) {		
			mainFrame.getContentPane().remove(arrayListOfJLabel.get(i));
		}
		mainFrame.repaint();
		arrayListOfJLabel = new ArrayList<JLabel>();
		
		numberOfLines = 0;
		numberOfTasks = 0;
		
		this.previousDate = null;
		this.hasFloatingTask = false;
	}

	@Deprecated
	public void displayNewTaskOrDate(ITask task) {
	    //Do not exceed number of lines
		assert(numberOfLines < NUMBER_OF_LINES_ALLOWED - 1);
		
		// check whether the date is the same with the date of the previous task
		// if it's not the same, display the new date
		// if it's the same, display the task
		if (checkDateIsAppeared(task) || this.hasFloatingTask) {
			displayNewTask(task);
		} else {
			displayNewDate(task);
		}
		
	}

	public void displayTaskDetail(ITask task) {

		//**********display the title and category**********//
		// display the title of the task
	    displayNewLine("Title: " + task.getTitle() + " (" + task.getCategory() + ")", fontForDate, getCategoryColor(task));

		//**********display the place**********//
		// display the place of the task
		displayNewLine("Place: " + task.getPlace(), fontForTask, COLOR_FOR_DATE);

		//**********display the start time**********//
		// string for the start time
		String stringForStartTime = "Start time: ";
		
		// get the start date to be displayed
		Date startDateToDisplay = task.getStartDate();

		// display the start time of the task
		if (startDateToDisplay != null) {
		    displayNewLine(stringForStartTime + startDateToDisplay.getPrettyFormat(), fontForTask, COLOR_FOR_DATE);
		}

		//**********display the end time**********//
		// string for the end time
		String stringForEndTime = "End time: ";
		
		// get the end date to be displayed
		Date endDateToDisplay = task.getEndDate();

		// display the end time of the task
		displayNewLine(stringForEndTime + endDateToDisplay.getPrettyFormat(), fontForTask, COLOR_FOR_DATE);

		//**********display the notes**********//
		// display the notes of the task
		displayNewLine("Notes: " + task.getNotes(), fontForTask, COLOR_FOR_DATE);
	}

	/**
	 * Returns whether the UI already contains another Task with the same 
	 * deadline as the task supplied.
	 * @param task 
	 * @return
	 */
	@Deprecated
	private boolean checkDateIsAppeared(ITask task) {
	    boolean dateHasAppeared = false;
	    if (previousDate != null) {
	        dateHasAppeared = previousDate.equals(task.getEndDate());
	    }
	    assert(task.getEndDate() != null);
	    previousDate = task.getEndDate();
		return dateHasAppeared;
	}
	
	@Deprecated
	public void displayNewDate(ITask task) {

		// get the date to be displayed
		Date dateToDisplay = task.getEndDate();

		// display the contents to the window
		if (dateToDisplay != null) {
		    displayNewLine(dateToDisplay.getPrettyFormat(), fontForDate, COLOR_FOR_DATE);
		} else {
		    displayNewLine("FLOATING", fontForDate, COLOR_FOR_DATE);
		    this.hasFloatingTask = true;
		}

		// display the task as well
		displayNewTask(task);
	}

	@Deprecated
	public void displayNewTask(ITask task) {
		
		// increment numberOfTasks by 1
		numberOfTasks++;
		
		// get the task number to be displayed
		String stringOfTaskToDisplay = Integer.toString(numberOfTasks) + ": ";
		
		// get the title of the task to be displayed
		stringOfTaskToDisplay += task.getTitle();
		
		// display the contents to the window
		if (task.getStatus() == TaskStatus.DONE) {
	        displayNewLine(stringOfTaskToDisplay, fontForDoneTask, getCategoryColor(task));
		} else {
	        displayNewLine(stringOfTaskToDisplay, fontForTask, getCategoryColor(task));
		}
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
	    	assert(Category.getDefaultCategory().getColor() != null);
	        return Category.getDefaultCategory().getColor();
	    } else {
	    	assert(task.getCategory().getColor() != null);
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
	}

	public void setUpConsole() {

		//use key bindings for enter key
		EnterAction enterAction = new EnterAction();
		console.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "doEnterAction");
		console.getActionMap().put("doEnterAction", enterAction);
		
		//use key bindings for delete key
		DeleteAction deleteAction = new DeleteAction();
		console.getInputMap().put(KeyStroke.getKeyStroke("BACK_SPACE"), "doDeleteAction");
		console.getActionMap().put("doDeleteAction", deleteAction);
		
		
		// use key bindings for arrows key
		String[] keys = {"UP", "DOWN", "LEFT", "RIGHT"};
        for (String key : keys) {
            console.getInputMap().put(KeyStroke.getKeyStroke(key), "none");
        }
		
		// set the size of the scrollPanel
		scrollPanel.setBounds(0, LISTHEIGHT, CONSOLEWIDTH, CONSOLEHEIGHT);
		
		// set the font of the console
		console.setFont(fontForConsole);
		
		moveCursorToEnd();

		// add the label to the container
		mainFrame.getContentPane().add(scrollPanel);
	}

	@Override
	public void prepareForUserInput() {
		// append the letter that appears at the first place
		showInConsole("\n");
		showInConsole(CONSOLE_ARROWS);
	    moveCursorToEnd();
	}

	private void moveCursorToEnd() {
		console.setCaretPosition(console.getDocument().getLength());
	}

	@Override
	public void displayMessageToUser(String message) {
	    showInConsole(message);
	    showInConsole("\n");
	}
	
	private void showInConsole(String text) {
	    console.append(text);
	    updateCursorPositionCounter();
	}

	void updateCursorPositionCounter() {
		mCursorPosition = console.getText().length();
	}
	
	private class EnterAction extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent event) {
            String userInput = "";
            try {
                userInput = console.getText(mCursorPosition, console.getText().length() - mCursorPosition);    
            } catch (BadLocationException e) {
                e.printStackTrace(); //who cares?
            }
            
            String reply = Controller.processUserInput(userInput);
            showInConsole("\n");
            displayMessageToUser(reply);
            prepareForUserInput();
        }
	    
	}
	
	private class DeleteAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent event) {
            Document documentInConsole = console.getDocument();
            try {
            	if(mCursorPosition < console.getText().length()){
            		documentInConsole.remove(documentInConsole.getLength() - 1, 1);
            	}
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
        }
	}

    @Override
    public void display(String pageTitle, List<ITask> tasks) {
        // TODO: display page title
        clearDisplay();
        for (ITask task: tasks) {
            displayNewTaskOrDate(task);
        }
        
    }

}