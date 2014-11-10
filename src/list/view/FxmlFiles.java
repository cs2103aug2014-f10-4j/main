//@author A0126722L
package list.view;

public class FxmlFiles {
	
	//Root Window.fxml
	//@author A0126722L
//	<?xml version="1.0" encoding="UTF-8"?>
//
//	<?import javafx.scene.text.*?>
//	<?import javafx.geometry.*?>
//	<?import javafx.scene.control.*?>
//	<?import javafx.scene.image.*?>
//	<?import java.lang.*?>
//	<?import javafx.scene.layout.*?>
//	<?import javafx.scene.layout.Pane?>
//
//	<Pane fx:id="rootPane" maxHeight="400.0" maxWidth="600.0" minHeight="400.0" minWidth="600.0" prefHeight="400.0" prefWidth="600.0" style="-fx-background-color: #6d6969;" xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1" fx:controller="list.view.RootWindowController">
//	   <children>
//	      <ToolBar prefHeight="40.0" prefWidth="600.0" style="-fx-background-color: #2E2E2E;">
//	         <items>
//	            <Button fx:id="buttonToCategory" maxHeight="25.0" maxWidth="25.0" minHeight="25.0" minWidth="25.0" mnemonicParsing="false" prefHeight="25.0" prefWidth="25.0" style="-fx-border-style: none; -fx-background-color: #2E2E2E;">
//	               <graphic>
//	                  <ImageView fitHeight="20.0" fitWidth="20.0" pickOnBounds="true" preserveRatio="true">
//	                     <image>
//	                        <Image url="@icon_menu.png" />
//	                     </image>
//	                  </ImageView>
//	               </graphic>
//	            </Button>
//	            <Button fx:id="buttonToPrev" maxHeight="25.0" maxWidth="25.0" minHeight="25.0" minWidth="25.0" mnemonicParsing="false" prefHeight="25.0" prefWidth="25.0" style="-fx-border-style: none; -fx-background-color: #2E2E2E;">
//	               <graphic>
//	                  <ImageView fitHeight="20.0" fitWidth="20.0" pickOnBounds="true" preserveRatio="true">
//	                     <image>
//	                        <Image url="@icon_left.png" />
//	                     </image>
//	                  </ImageView>
//	               </graphic>
//	            </Button>
//	            <Button fx:id="buttonToNext" maxHeight="25.0" maxWidth="25.0" minHeight="25.0" minWidth="25.0" mnemonicParsing="false" prefHeight="25.0" prefWidth="25.0" style="-fx-border-style: none; -fx-background-color: #2E2E2E;">
//	               <graphic>
//	                  <ImageView fitHeight="20.0" fitWidth="20.0" pickOnBounds="true" preserveRatio="true">
//	                     <image>
//	                        <Image url="@icon_right.png" />
//	                     </image>
//	                  </ImageView>
//	               </graphic>
//	            </Button>
//	            <Button fx:id="buttonToHome" maxHeight="25.0" maxWidth="25.0" minHeight="25.0" minWidth="25.0" mnemonicParsing="false" prefHeight="25.0" prefWidth="25.0" style="-fx-border-style: none; -fx-background-color: #2E2E2E;">
//	               <graphic>
//	                  <ImageView fitHeight="20.0" fitWidth="20.0" pickOnBounds="true" preserveRatio="true">
//	                     <image>
//	                        <Image url="@icon_home.png" />
//	                     </image>
//	                  </ImageView>
//	               </graphic>
//	            </Button>
//	            <Region maxWidth="435.0" minWidth="33.0" prefHeight="30.0" prefWidth="173.0" />
//	            <Label fx:id="labelPageTitle" alignment="CENTER_RIGHT" maxWidth="286.0" minWidth="286.0" prefHeight="23.0" prefWidth="286.0" textFill="#eeeeeebf">
//	               <font>
//	                  <Font name="Helvetica Neue Light" size="18.0" />
//	               </font>
//	            </Label>
//	         </items>
//	      </ToolBar>
//	      <TextField fx:id="console" layoutY="372.0" prefHeight="28.0" prefWidth="600.0" promptText="Enter command" style="-fx-background-color: #585858; -fx-text-fill: white;" />
//	      
//	   </children>
//	</Pane>
	//@author A0126722L
	
	//Task Overview.fxml
	//@author A0126722L
//	<?xml version="1.0" encoding="UTF-8"?>
//
//	<?import javafx.geometry.*?>
//	<?import java.lang.*?>
//	<?import javafx.scene.control.*?>
//	<?import javafx.scene.layout.*?>
//	<?import javafx.scene.text.*?>
//	<?import javafx.scene.layout.Pane?>
//
//	<Pane fx:id="tasksContainer" prefHeight="332.0" prefWidth="600.0" style="-fx-background-color: #6d6969;" xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1" fx:controller="list.view.TaskOverviewController">
//	   <children>
//	      <Label fx:id="labelFeedback" layoutX="5.0" layoutY="300.0" prefHeight="30.0" prefWidth="590.0" style="-fx-background-radius: 3px; -fx-background-color: white; -fx-opacity: 0%;" text="Notification Centre">
//	            <padding>
//	            <Insets bottom="5.0" left="10.0" right="10.0" top="5.0" />
//	            </padding>
//	         <font>
//	            <Font size="14.0" />
//	         </font>
//	         </Label>
//	   </children>
//	</Pane>
	//@author A0126722L
	
	//Task Detail.fxml
	//@author A0126722L
//	<?xml version="1.0" encoding="UTF-8"?>
//
//	<?import javafx.scene.text.*?>
//	<?import javafx.scene.control.*?>
//	<?import java.lang.*?>
//	<?import javafx.scene.layout.*?>
//	<?import javafx.scene.layout.AnchorPane?>
//
//	<Pane fx:id="pane" prefHeight="270.0" prefWidth="366.0" style="-fx-background-color: #333333;" xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1" fx:controller="list.view.TaskDetailController">
//	   <children>
//	      <TextField fx:id="taskTitle" layoutX="14.0" layoutY="15.0" prefHeight="60.0" prefWidth="338.0" style="-fx-background-color: #333333; -fx-text-fill: #ffffff;" text="Semester 1">
//	         <font>
//	            <Font name="Helvetica Neue Light" size="28.0" />
//	         </font>
//	      </TextField>
//	      <TextField fx:id="taskStartDate" layoutX="89.0" layoutY="72.0" prefHeight="20.0" prefWidth="272.0" style="-fx-background-color: #333333; -fx-text-fill: #ffffff;" text="3 / 8 / 2014">
//	         <font>
//	            <Font name="Helvetica Neue Light" size="16.0" />
//	         </font>
//	      </TextField>
//	      <Label layoutX="15.0" layoutY="78.0" text="Start Date:" textFill="WHITE">
//	         <font>
//	            <Font name="Helvetica Neue Light" size="16.0" />
//	         </font>
//	      </Label>
//	      <Label layoutX="20.0" layoutY="104.0" prefHeight="20.0" prefWidth="69.0" text="End Date:" textFill="WHITE">
//	         <font>
//	            <Font name="Helvetica Neue Light" size="16.0" />
//	         </font>
//	      </Label>
//	      <Label layoutX="42.0" layoutY="207.0" text="Status:" textFill="WHITE">
//	         <font>
//	            <Font name="Helvetica Neue Light" size="16.0" />
//	         </font>
//	      </Label>
//	      <Label layoutX="46.0" layoutY="156.0" text="Place:" textFill="WHITE">
//	         <font>
//	            <Font name="Helvetica Neue Light" size="16.0" />
//	         </font>
//	      </Label>
//	      <Label layoutX="43.0" layoutY="182.0" text="Notes:" textFill="WHITE">
//	         <font>
//	            <Font name="Helvetica Neue Light" size="16.0" />
//	         </font>
//	      </Label>
//	      <TextField fx:id="taskEndDate" layoutX="89.0" layoutY="98.0" prefHeight="20.0" prefWidth="272.0" style="-fx-background-color: #333333; -fx-text-fill: #ffffff;" text="6 / 12 / 2014">
//	         <font>
//	            <Font name="Helvetica Neue Light" size="16.0" />
//	         </font>
//	      </TextField>
//	      <TextField fx:id="taskCategory" layoutX="88.0" layoutY="124.0" prefHeight="32.0" prefWidth="272.0" style="-fx-background-color: #333333; -fx-text-fill: #FE9A2E;" text="School">
//	         <font>
//	            <Font name="Helvetica Neue Light" size="16.0" />
//	         </font>
//	      </TextField>
//	      <TextField fx:id="taskPlace" layoutX="88.0" layoutY="150.0" prefHeight="32.0" prefWidth="272.0" style="-fx-background-color: #333333; -fx-text-fill: #ffffff;" text="NUS">
//	         <font>
//	            <Font name="Helvetica Neue Light" size="16.0" />
//	         </font>
//	      </TextField>
//	      <TextField fx:id="taskNotes" layoutX="87.0" layoutY="176.0" prefHeight="32.0" prefWidth="272.0" style="-fx-background-color: #333333; -fx-text-fill: #ffffff;" text="Tired with lots of work...">
//	         <font>
//	            <Font name="Helvetica Neue Light" size="16.0" />
//	         </font>
//	      </TextField>
//	      <CheckBox fx:id="taskStatus" layoutX="102.0" layoutY="209.0" mnemonicParsing="false" text="CheckBox" />
//	      <Button fx:id="buttonDone" layoutX="285.0" layoutY="233.0" maxWidth="95.0" minWidth="26.0" mnemonicParsing="false" prefHeight="26.0" prefWidth="64.0" style="-fx-background-color: #585858;" text="Done">
//	         <font>
//	            <Font name="Helvetica Neue Bold" size="12.0" />
//	         </font>
//	      </Button>
//	      <Label layoutX="21.0" layoutY="130.0" text="Category:" textFill="WHITE">
//	         <font>
//	            <Font name="Helvetica Neue Light" size="16.0" />
//	         </font>
//	      </Label>
//	   </children>
//	</Pane>
	
	//Categories.fxml
	//@author A0126722L
//	<?xml version="1.0" encoding="UTF-8"?>
//
//	<?import java.lang.*?>
//	<?import javafx.scene.control.*?>
//	<?import javafx.scene.control.ScrollPane?>
//
//
//	<ScrollPane fx:id="paneContainer" prefHeight="332.0" prefWidth="140.0" style="-fx-background-color: #333333; -fx-background: #333333;" xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1" fx:controller="list.view.CategoriesController">
//		<!-- TODO Add Nodes -->
//	</ScrollPane>
	//@author A0126722L
	
	//Help.fxml
	//@author A0126722L
//	<?xml version="1.0" encoding="UTF-8"?>
//
//	<?import javafx.scene.text.*?>
//	<?import javafx.scene.layout.*?>
//	<?import javafx.scene.image.*?>
//	<?import java.lang.*?>
//	<?import javafx.scene.control.*?>
//	<?import javafx.scene.control.ScrollPane?>
//
//	<ScrollPane fx:id="scrollPane" prefHeight="333.0" prefWidth="500.0" style="-fx-background-color: #333333;" xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1" fx:controller="list.view.HelpController">
//	   <content>
//	      <Pane prefHeight="1883.0" prefWidth="498.0">
//	         <children>
//	            <ImageView fx:id="imageHelp" cache="true" fitHeight="1903.0" fitWidth="498.0" pickOnBounds="true" preserveRatio="true">
//	               <image>
//	                  <Image url="@help_page_small.png" />
//	               </image></ImageView>
//	            <Button fx:id="buttonDone" layoutX="416.0" layoutY="1849.0" mnemonicParsing="false" prefHeight="26.0" prefWidth="64.0" style="-fx-background-color: #585858;" text="Done">
//	               <font>
//	                  <Font name="Helvetica Neue Light" size="12.0" />
//	               </font>
//	            </Button>
//	         </children>
//	      </Pane>
//	   </content>
//	</ScrollPane>
	//@author A0126722L
	
	//Congratulations.fxml
	//@author A0126722L
	
//	<?xml version="1.0" encoding="UTF-8"?>
//
//	<?import javafx.scene.effect.*?>
//	<?import javafx.scene.text.*?>
//	<?import javafx.scene.control.*?>
//	<?import javafx.scene.image.*?>
//	<?import java.lang.*?>
//	<?import javafx.scene.layout.*?>
//	<?import javafx.scene.layout.AnchorPane?>
//
//	<Pane fx:id="pane" prefHeight="277.0" prefWidth="360.0" style="-fx-background-color: #333333;" xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1" fx:controller="list.view.CongratulationsController">
//	   <children>
//	      <ImageView fitHeight="70.0" fitWidth="70.0" layoutX="12.0" layoutY="12.0" pickOnBounds="true" preserveRatio="true">
//	         <image>
//	            <Image url="@congratulations.png" />
//	         </image>
//	      </ImageView>
//	      <ListView fx:id="listView" layoutX="31.0" layoutY="92.0" prefHeight="147.0" prefWidth="298.0" />
//	      <Label layoutX="96.0" layoutY="23.0" text="Congratulations!" textFill="WHITE">
//	         <font>
//	            <Font name="Helvetica Neue Light" size="24.0" />
//	         </font>
//	      </Label>
//	      <Label layoutX="89.0" layoutY="53.0" text="You've done everything today!" textFill="WHITE">
//	         <font>
//	            <Font name="Helvetica Neue Light" size="14.0" />
//	         </font>
//	      </Label>
//	      <Label layoutX="92.0" layoutY="71.0" text="Want to do something more?" textFill="WHITE">
//	         <font>
//	            <Font name="Helvetica Neue Light" size="14.0" />
//	         </font>
//	      </Label>
//	      <Button fx:id="buttonDone" layoutX="265.0" layoutY="244.0" maxWidth="95.0" minWidth="26.0" mnemonicParsing="false" prefHeight="26.0" prefWidth="64.0" style="-fx-background-color: #585858;" text="Done">
//	         <font>
//	            <Font name="Helvetica Neue Bold" size="12.0" />
//	         </font>
//	      </Button>
//	   </children>
//	</Pane>
	//@author A0126722L
	
	//@author A0126722L
	/*
	 * design of congratulations.png
	 * 
	 * design of help_page_small.png
	 * 
	 * design of help_page.png
	 * 
	 * design of icon_bar.png
	 * 
	 * design of icon_calender.png
	 * 
	 * design of icon_home.png
	 * 
	 * design of icon_left.png
	 * 
	 * design of icon_menu.png
	 * 
	 * design of icon_right.png
	 * 
	 * design of list_logo.png
	 */
	//@author A0126722L
}
