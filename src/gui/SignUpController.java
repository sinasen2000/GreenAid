
import app.responses.Response;

import javafx.fxml.FXML;

import javafx.fxml.Initializable;

import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javafx.stage.Window;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URL;
import java.util.ResourceBundle;

@SuppressWarnings("ALL")
public class SignUpController implements Initializable {

    private static final String url_registration = "http://localhost:8080/registration";

    @FXML
    private Button saveUser;
    @FXML
    private PasswordField password, passwordConfirm;
    @FXML
    private TextField username, firstname, lastname;
    @FXML
    private AnchorPane pane;
    @FXML
    private Text exists, emptyFirst, emptyLast, emptyUser, emptyPass, emptyPassCon;
    @FXML
    private ImageView Home;
    @FXML
    private Text passcheck;

    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1){
        exists.setVisible(false);
        emptyFirst.setVisible(false);
        emptyLast.setVisible(false);
        emptyUser.setVisible(false);
        emptyPass.setVisible(false);
        emptyPassCon.setVisible(false);
        passcheck.setVisible(false);
    }

    // Gets the username from the register screen
    public String getUsername() {
        return username.getText();
    }

    // Gets the Firstname from the register screen
    public String getFirstname(){
        return firstname.getText();
    }

    // Gets the Lastname from the register screen
    public String getLastname(){
        return lastname.getText();
    }

    // Gets the password from the register screen
    public String getPassword() {
        return password.getText();
    }

    // Gets the password confirm from the register screen
    public String getPasswordConfirm() {
        return passwordConfirm.getText();
    }

    // Making the signup Page movable
    public void movingSignup() {
        pane.setOnMousePressed(event -> {
            xOffset = LogInController.stage.getX() - event.getScreenX();
            yOffset = LogInController.stage.getY() - event.getScreenY();
        });
        pane.setOnMouseDragged(event -> {
            LogInController.stage.setX(event.getScreenX() + xOffset);
            LogInController.stage.setY(event.getScreenY() + yOffset);
        });
    }

    // The logout button to go back to the login page
    @FXML
    private void home(){
        Window window = saveUser.getScene().getWindow();
        Stage stage = GoGreenGUI.stage;
        stage.show();
        window.hide();

    }

    // Closing the program
    public void handleClose() {
        System.exit(0);
    }

    // Error messages if the First name field is empty
    private void emptyFirst(){
        emptyFirst.setText("Fill in Firstname");
        emptyFirst.setVisible(true);
    }

    // Error messages if the Last name field is empty
    private void emptyLast(){
        emptyLast.setText("Fill in LastName");
        emptyLast.setVisible(true);
    }

    // Error messages if the Username field is empty
    private void emptyUser(){
        emptyUser.setText("Fill in Username");
        emptyUser.setVisible(true);
    }

    // Error messages if the Password field is empty
    private void emptyPass(){
        emptyPass.setText("Fill in password");
        emptyPass.setVisible(true);
    }

    // Error messages if the Password confirm field is empty
    private void emptyPassCon(){
        emptyPassCon.setText("Fill in password");
        emptyPassCon.setVisible(true);
    }

    // Sets the error messages if empty and/or not matching and if the username already exists, if not saves the user to the database
    @FXML
    private void saveUser(MouseEvent mouseEvent) {

        if(getFirstname().isEmpty()){
            emptyFirst();
        }
        else{
            emptyFirst.setVisible(false);
        }
        if(getLastname().isEmpty()){
            emptyLast();
        }
        else {
            emptyLast.setVisible(false);
        }
        if(getUsername().isEmpty()){
            emptyUser();
        }
        else {
            emptyUser.setVisible(false);
        }
        if(getPassword().isEmpty()){
            emptyPass();
        }
        else {
            emptyPass.setVisible(false);
        }
        if(getPasswordConfirm().isEmpty()) {
            emptyPassCon();
        }
        else {
            emptyPassCon.setVisible(false);
        }

        if (!getPassword().equals(getPasswordConfirm())){
            passcheck.setText("Passwords do not match");
            passcheck.setVisible(true);
        }
        else {
            passcheck.setVisible(false);
        }

        if (getPassword().equals(getPasswordConfirm()) && !getLastname().isEmpty()&& !getFirstname().isEmpty()
            && !getUsername().isEmpty() && !getPassword().isEmpty()) {

            // HttpHeaders
            HttpHeaders headers = new HttpHeaders();
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("first_name", getFirstname());
            params.add("last_name", getLastname());
            params.add("username", getUsername());
            params.add("password", getPassword());

            headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            RestTemplate restTemplate = new RestTemplate();

            // Data attached to the request.
            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

            Response registration = restTemplate.postForObject(url_registration, request, Response.class);

            // Checks if the user already exists
            if (registration.getData().equals("Username is already registered")) {
                exists.setText("Username already exists");
                exists.setVisible(true);
                clearFields();


            }

            // Goes back to the login page
            else {
                    Window window = saveUser.getScene().getWindow();
                    Stage stage = GoGreenGUI.stage;
                    stage.show();
                    window.hide();
            }
        }
        else {

            exists.setText("Fill in open fields");
            exists.setVisible(true);

        }
    }

    // Clear the fields
    private void clearFields() {
        username.setText(null);
        password.clear();
        passwordConfirm.clear();
    }
}


