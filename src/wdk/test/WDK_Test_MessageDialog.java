package wdk.test;

import static wdk.WDK_StartupConstants.CLOSE_BUTTON_LABEL;
import wdk.gui.MessageDialog;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.stage.Stage;

/**
 * This class is used to test our MessageDialog class. Using a separate
 * class like this lets us setup simple tests without all the other
 * stuff in the way.
 * 
 * @author Yael
 */
public class WDK_Test_MessageDialog extends Application {
    /**
     * This method performs our simple test, where we simply open our
     * message dialog and the close it.
     * 
     * @param primaryStage 
     */
    @Override
    public void start(Stage primaryStage) {
        MessageDialog ed = new MessageDialog(primaryStage, CLOSE_BUTTON_LABEL);
        ed.show("Test Message");
    }    

    /**
     * This test application starts here, and simply begins the
     * initialization of the JavaFX application.
     * 
     * @param args 
     */
    public static void main(String[] args) {
        launch(args);
    }
}