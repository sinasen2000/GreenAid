import app.client.Client;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;

import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@SuppressWarnings("ALL")
public class LogInController implements Initializable {

    @FXML
    private Hyperlink register;
    @FXML
    private JFXButton login;
    @FXML
    public AnchorPane pane;
    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private Text invalid;

    public static String sessionCookie = null;

    public static Stage stage = null;

    public static String Name = null;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        invalid.setVisible(false);
        username.setOnKeyPressed(event -> {
            if(event.getCode().equals(KeyCode.ENTER))
                handleLoginClicked();
        });
        password.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER))
                handleLoginClicked();
        });
    }

    // Closes the program
    public void handleClose() {
        System.exit(0);
    }

    //Minimizes the program
    public void handleMinimizeButton() {
        GoGreenGUI.stage.setIconified(true);
    }

    // Goes to the register screen

    /**
     * Controls the register click
     */
    public void handleRegisterClicked() {
        try {

            Parent root = FXMLLoader.load(getClass().getResource("FXML/signUp.fxml"));
            Stage stage = new Stage();
            stage.setTitle("signUp");
            stage.setScene(new Scene(root, 700, 655));
            stage.initStyle(StageStyle.UNDECORATED);
            this.stage = stage;
            stage.show();
            Window window = register.getScene().getWindow();
            window.hide();
        } catch (IOException e) {
            System.out.println("Error found in the handleRegisterClicked class");
        }
    }

    /**
     * Gets the username from the login
     */
    public String getUsername() {
        this.Name = username.getText();
        return username.getText();

    }



    /**
     * Gets the password from the login
     * @return
     */
    public String getPassword() {
        return password.getText();
    }



    /**
     * Goes to the homepage if the information is correct
     */
    public void handleLoginClicked() {

        String response = Client.getSessionCookie(getUsername(), getPassword());

        // Checks if the information is the same as in the database
        if (!response.equals("No cookie found.")) {

            this.sessionCookie = response;
            invalid.setVisible(false);
            password.clear();

            try {

                Parent root = (Parent)FXMLLoader.<Parent>load(getClass().getResource("FXML/HomePage.fxml"));
                Stage stage = new Stage();
                stage.setTitle("HomePage");
                stage.setScene(new Scene(root, 1398, 954));
                stage.initStyle(StageStyle.UNDECORATED);
                this.stage = stage;
                stage.show();
                Window window = login.getScene().getWindow();
                window.hide();
            } catch(IOException e) {
                System.out.println("Error found in the handleLoginClicked");
            } }// Checks if the username field and password field is empty
        else if (getUsername().equals("") && getPassword().equals("") ) {
            invalid.setText("Fill in username and password");
            invalid.setVisible(true);
        }
           // Checks if username field is not empty and if the password field is
        else if (!getUsername().equals("") && getPassword().equals("") ) {
            invalid.setText("Fill in password");
            invalid.setVisible(true);
        }
        // Checks if the username field is empty and the password field is not
        else if (getUsername().equals("") && !getPassword().equals("") ) {
            invalid.setText("Fill in username");
            invalid.setVisible(true);
        }
        /* Sets the error of that username or password is not correct if the combination is not in the database
         on the login screen */
        else {
            invalid.setText("Username or password is incorrect");
            invalid.setVisible(true);

        }


    }


}
