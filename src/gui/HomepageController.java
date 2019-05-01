import app.client.Client;
import app.models.ActivityProjection;
import app.models.User;

import app.models.UserProjection;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXSlider;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import static java.time.temporal.ChronoUnit.DAYS;


/**
 * Class containing all the methods used in the Home Page.
 */
@SuppressWarnings("ALL")
public class HomepageController implements Initializable {
    @FXML
    private AnchorPane pane;
    @FXML
    private ComboBox<String> comboBox;
    @FXML
    private ImageView Home , badge, place, userImage;
    @FXML
    private Text activityText, field, firstName, lastName, level, xp, introText, errorUser;
    @FXML
    private TextField friend;
    @FXML
    private HBox hBox;
    @FXML
    private VBox followersPane, followingPane , history , list, actiBox;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private ProgressIndicator progress;
    @FXML
    private BarChart<String, Double> leaderBoard;
    @FXML
    private CategoryAxis xAxis;
    @FXML
    private NumberAxis yAxis;
    @FXML
    private TextFlow info, recommendation;
    @FXML
    private ChoiceBox<String> choiceBox;
    @FXML
    private WebView browser;
    @FXML
    private StackPane levelPopUp , rankPopUp, newPhoto, infoTree;


    private long categoryId;
    private double xOffset;
    private double yOffset;
    private double levelPercentage ;
    private double levelNumber ;
    double temp;
    static String no1 = null;

    private User user = Client.getUserDetails(LogInController.sessionCookie);
    private List<ActivityProjection> activities = Client.getUserActivities(LogInController.sessionCookie);
    private List<UserProjection> followers = Client.getUserFollowedBy(LogInController.sessionCookie);
    private List<UserProjection> following = Client.getUserFollowings(LogInController.sessionCookie);
    private List <UserProjection> top20leaderboard = Client.getLeaderboard(LogInController.sessionCookie);
    private String advice = Client.getRecommendation(LogInController.sessionCookie);

    /**
     * Called to initialize a controller. FXMLLoader will automatically launch the code at start.
     * @param arg0
     * @param arg1
     */
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        //Sets the User's username, First Name, Last Name and the number 1 person
        field.setText(LogInController.Name + "\n");
        firstName.setText("First name: " + user.getFirst_name());
        lastName.setText("Last name: " + user.getLast_name());
        no1 = top20leaderboard.get(0).getUsername();

        //Initialize the ComboBox elements
        comboBox.getItems().removeAll(comboBox.getItems());
        comboBox.getItems().addAll("Eating A Vegetarian Meal", "Buying Local Produce", "Biking instead of Driving", "Using Public Transport instead of Driving", "Installing Solar Panels", "Lowering the Temperature of your Home");

        //Initialize the Choice Box elements (different Leader Boards)
        choiceBox.getItems().addAll("Friends", "Top 20");

        xp.setVisible(false);

        //Initialize the Text in Info Tab
        text();

        //Sets the Bar Policies for ScrollPanes
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        //Sets the Users Progress Bar (level)
        experience();
        progress.setProgress(levelPercentage / 100.0);

        //Sets the List of Activities, List of Followers, Sets number 1 rank , Achievements , List of Followings, the Recommendation Tab
        showUserActivities();
        Recommendation();
        ranking();
        achievements();
        setFriends(followers, followersPane);
        setFriends(following, followingPane);

        //Creates a listener in order to change
        leaderboardChoosed();

        //Sets the Computer Game in the "Game" tab
        String url = "https://www3.epa.gov/recyclecity/";
        browser.setZoom(0.7);
        WebEngine webEngine = browser.getEngine();
        webEngine.load(url);
        browser.getEngine().setUserStyleSheetLocation(getClass().getResource("/CSS/webView.css").toExternalForm());

        //Sets the User's profile picture
        setProfilePicture();
    }

    /**
     * shows info on the tree when it is hovered
     */
    public void setInfoTree(){
        Text temp = new Text();
        temp.setText("This tree evolves in relation to the XP you are gaining. Take good care of it !");

        JFXDialogLayout dialogLayout = new JFXDialogLayout();
        dialogLayout.setBody(temp);

        JFXDialog dialog = new JFXDialog(infoTree, dialogLayout, JFXDialog.DialogTransition.NONE);

        infoTree.setOnMouseExited(event -> dialog.close());

        dialog.show();
    }

    /**
     * Sets the user's profile picture in relation to the one he choose
     */
    public void setProfilePicture(){
        int choosed = user.getProfile_picture();

        switch (choosed){
            case 0:
                break;
            case 1:
                userImage.setImage(new Image("images/girl1.png"));
                break;
            case 2:
                userImage.setImage(new Image("images/guy1.png"));
                break;
            case 3:
                userImage.setImage(new Image("images/girl2.png"));
                break;
            case 4:
                userImage.setImage(new Image("images/guy2.png"));
                break;
            default:
                System.out.println("The image linked to this number does not exists");
        }
    }

    /**
     * Changes the int linked to the image in the DB
     */
    public void chooseNewProfile(){
        HBox hBox = new HBox();
        hBox.setSpacing(5);

        Image image1 = new Image("images/girl1.png");
        ImageView avatar1 = new ImageView(image1);
        avatar1.setFitHeight(160);
        avatar1.setFitWidth(160);
        avatar1.setOnMouseEntered(event -> avatar1.setCursor(Cursor.HAND));

        Image image2 = new Image("images/guy1.png");
        ImageView avatar2 = new ImageView(image2);
        avatar2.setFitHeight(160);
        avatar2.setFitWidth(160);
        avatar2.setOnMouseEntered(event -> avatar2.setCursor(Cursor.HAND));

        Image image3 = new Image("images/girl2.png");
        ImageView avatar3 = new ImageView(image3);
        avatar3.setFitHeight(160);
        avatar3.setFitWidth(160);
        avatar3.setOnMouseEntered(event -> avatar3.setCursor(Cursor.HAND));

        Image image4 = new Image("images/guy2.png");
        ImageView avatar4 = new ImageView(image4);
        avatar4.setFitHeight(160);
        avatar4.setFitWidth(160);
        avatar4.setOnMouseEntered(event -> avatar4.setCursor(Cursor.HAND));

        hBox.getChildren().addAll(avatar1, avatar2, avatar3, avatar4);

        JFXDialogLayout dialogLayout = new JFXDialogLayout();
        dialogLayout.setHeading(new Text("Here are the possible photos"));
        dialogLayout.setBody(hBox);

        JFXDialog dialog = new JFXDialog(newPhoto, dialogLayout, JFXDialog.DialogTransition.CENTER);

        JFXButton button = new JFXButton("Close");
        button.setOnAction(event -> {
            dialog.close();
        });

        avatar1.setOnMouseClicked(event -> {
            Client.updateProfilePicture(LogInController.sessionCookie, 1);
            userImage.setImage(image1);
            dialog.close();
        });
        avatar2.setOnMouseClicked(event -> {
            Client.updateProfilePicture(LogInController.sessionCookie, 2);
            userImage.setImage(image2);
            dialog.close();
        });
        avatar3.setOnMouseClicked(event -> {
            Client.updateProfilePicture(LogInController.sessionCookie, 3);
            userImage.setImage(image3);
            dialog.close();
        });
        avatar4.setOnMouseClicked(event -> {
            Client.updateProfilePicture(LogInController.sessionCookie, 4);
            userImage.setImage(image4);
            dialog.close();

        });

        dialogLayout.setActions(button);

        dialog.show();
    }

    /**
     * Checks is the user is the current rank number 1
     */
    public void ranking(){
        if(no1.equals(user.getUsername()) ){
            Image image = new Image("images/number1.png");
            place.setImage(image);
            place.setVisible(true);

        }
       else {
           place.setVisible(false);
           rankCongratz();

        }
    }

    /**
     * Checks if you are the new rank and gives a popup
     */
    public void rankCongratz(){
        if (top20leaderboard.get(0).getUsername().equals(user.getUsername())) {
            place.setVisible(true);
            Image image = new Image("images/number1.png");
            place.setImage(image);

           /* ImageView imageView = new ImageView();
            imageView.setImage(image);
            imageView.setFitHeight(100);
            imageView.setFitWidth(100);*/
            JFXDialogLayout dialogLayout = new JFXDialogLayout();
            dialogLayout.setHeading(new Text("Hey, " + user.getUsername() + ", Congratulations! \nyou have just hit Rank 1!!! \n" +"You have earned this:"));
            //dialogLayout.setBody(imageView);


            JFXDialog dialog = new JFXDialog(rankPopUp, dialogLayout, JFXDialog.DialogTransition.NONE);

            dialog.setMaxWidth(rankPopUp.getMaxWidth());
            dialog.setMaxHeight(rankPopUp.getMaxHeight());

            JFXButton buttonX = new JFXButton("Close");

            buttonX.setOnAction(event -> dialog.close());

            dialogLayout.setActions(buttonX);

            dialog.show();
        }
        else {
            place.setVisible(false);
        }
    }

    /**
     * Minimizes the window
     */

    public void handleMinimizeButton() {
        LogInController.stage.setIconified(true);
    }

    /**
     * Sets achievements for levels
     */
    public  void achievements(){
        if(levelNumber == 1) {
            Image image = new Image("images/lvl1.png");
            badge.setImage(image);
            badge.setFitHeight(200);
            badge.setFitWidth(100);
            badge.setY(-45);

        }
        else if((int) levelNumber ==  2 ) {
            Image image = new Image("images/sprout.png");
            badge.setImage(image);
            badge.setFitWidth(72);
            badge.setFitHeight(59);
            badge.setPreserveRatio(true);
            badge.setY(0);

        }
        else if ((int) levelNumber == 3){
            Image image = new Image("images/sprout.png");
            badge.setImage(image);
            badge.setPreserveRatio(false);
            badge.setFitWidth(72);
            badge.setFitHeight(72);
            badge.setY(-13);
        }
        else if((int) levelNumber == 4){
            Image image = new Image("images/sprout.png");
            badge.setImage(image);
            badge.setPreserveRatio(false);
            badge.setFitWidth(72);
            badge.setFitHeight(82);
            badge.setY(-20);

        }
        else if ((int) levelNumber >= 5 && (int) levelNumber < 10){
            Image image = new Image("images/tree.png");
            badge.setImage(image);
            badge.setPreserveRatio(false);
            badge.setFitHeight(130);
            badge.setFitWidth(100);
            badge.setY(-70 );
        }
        else if ((int) levelNumber >= 10 && (int) levelNumber <15){
            Image image = new Image("images/forrest.png");
            badge.setImage(image);
            badge.setPreserveRatio(false);
            badge.setFitHeight(130);
            badge.setFitWidth(100);
            badge.setY(-65 );
        }
        else if ((int) levelNumber >=15){
            Image image = new Image("images/final.png");
            badge.setImage(image);
            badge.setPreserveRatio(false);
            badge.setFitHeight(130);
            badge.setFitWidth(100);
            badge.setY(-67  );
        }
    }

    /**
     * Program shuts down
     */
    public void handleClose() {
        System.exit(0);
    }

    /**
     * Method used in order to be able to drag the Window around the screen
     */
    public void movingHome() {
        pane.setOnMousePressed(event -> {
            xOffset = LogInController.stage.getX() - event.getScreenX();
            yOffset = LogInController.stage.getY() - event.getScreenY();
        });
        pane.setOnMouseDragged(event -> {
            LogInController.stage.setX(event.getScreenX() + xOffset);
            LogInController.stage.setY(event.getScreenY() + yOffset);
        });
    }

    /**
     * Returns to the Login Page
     */
    @FXML
    private void Home(){
        Window window = Home.getScene().getWindow();
        Stage stage = GoGreenGUI.stage;
        stage.show();
        window.hide();
    }

    /**
     * xp text set to visible when ProgressBar hovered
     */
    public void Hover(){
        experience();
        xp.setText(new DecimalFormat("#.##").format(levelPercentage)+ "/100");
        xp.setVisible(true);
    }

    /**
     * xp Text set to invisible when the ProgressBar isn't hovered anymore
     */
    public void exit(){
        xp.setVisible(false);
    }

    /**
     * Recommendation Algorithm used to show a recommendation
     */
    public void Recommendation (){
        recommendation.getChildren().clear();
        Text text = new Text();
        text.setFont(Font.font("Calibre", 18));
        text.setFill(Color.WHITE);
        text.setText(advice);
        recommendation.getChildren().add(text);
    }

    /**
     * Method used to "refresh" the lists when an activity is added / removed, or a friend is added / removed, o
     */
    public  void refresh(){
        user = Client.getUserDetails(LogInController.sessionCookie);
        activities = Client.getUserActivities(LogInController.sessionCookie);
        followers = Client.getUserFollowedBy(LogInController.sessionCookie);
        following = Client.getUserFollowings(LogInController.sessionCookie);
        top20leaderboard = Client.getLeaderboard(LogInController.sessionCookie);
        advice = Client.getRecommendation(LogInController.sessionCookie);
    }

    /**
     * Method used to show all the activities from the user in a Pane
     */
    public void showUserActivities() {
        int sz = activities.size();

        if(activities.isEmpty()){
            history.getChildren().clear();
            introText.setText("Dear " + user.getUsername() + ", here is your list of activities!");
            introText.setFont(Font.font(18));
            Text start = new Text();
            start.setText("No activities");
            history.getChildren().add(start);

        }
        else {
            history.getChildren().clear();

            introText.setText("Dear " + activities.get(0).getUsername() + ", here is your list of activities!");

            for (int i = 0; i < sz; i++) {
                StackPane stackPane = new StackPane();
                HBox hBox = new HBox();
                Text temp = new Text();
                Text cross = new Text();
                String ret = i + 1 + " - ";

                switch (activities.get(i).getCategory()) {
                    case "Eating a vegetarian meal":
                        ret += "You have eaten ";
                        ret += (int) activities.get(i)
                                .getAmount() + " serving(s) of a vegetarian meal and that gave you ";
                        ret += new DecimalFormat("#.##")
                                .format(activities.get(i).getXp_points()) + " XP!";
                        break;
                    case "Buying local produce":
                        ret += "You have bought ";
                        ret += (int) activities.get(i)
                                .getAmount() + " local product(s) and that gave you ";
                        ret += new DecimalFormat("#.##")
                                .format(activities.get(i).getXp_points()) + " XP!";
                        break;
                    case "Using bike instead of car":
                        ret += "You have cycled instead of driving for ";
                        ret += activities.get(i)
                                .getAmount() + " km and that gave you ";
                        ret += new DecimalFormat("#.##")
                                .format(activities.get(i).getXp_points()) + " XP!";
                        break;
                    case "Using public transport instead of car":
                        ret += "You have used public transports instead of driving for ";
                        ret += activities.get(i)
                                .getAmount() + " km and that gave you ";
                        ret += new DecimalFormat("#.##")
                                .format(activities.get(i).getXp_points()) + " XP!";
                        break;
                    case "Installing solar panels":
                        ret += "You have been using solar panels for ";
                        ret += (int) activities
                                .get(i).getAmount() + " days and that gave you ";
                        ret += new DecimalFormat("#.##")
                                .format(activities.get(i).getXp_points()) + " XP!";
                        break;
                    case "Lowering the temperature of your home":
                        ret += "You have lowered the temperature of your home of ";
                        ret += new DecimalFormat("#.##")
                                .format(activities.get(i).getAmount()) + " °C and that gave you ";
                        ret += new DecimalFormat("#.##")
                                .format(activities.get(i).getXp_points()) + " XP!";
                        break;
                    default:
                        ret += "Unknown activity...";

                }
                temp.setText(ret);
                temp.setFill(Color.WHITE);

                cross.setOnMouseClicked(event -> alertActivity(stackPane, temp.getText(), cross));

                hBoxHovered(hBox, cross);

                hBox.getChildren().addAll(temp, cross);
                stackPane.getChildren().add(hBox);

                history.getChildren().add(stackPane);
            }
        }
    }

    /**
     * Method used to show an Alert asking if the activity should really be removed.
     * @param stackPane
     * @param txt
     * @param cross
     */
    public void alertActivity(StackPane stackPane, String txt, Text cross) {
        String[] txtSplit = txt.split(" - ");
        int i = Integer.parseInt(txtSplit[0]) - 1;

        JFXDialogLayout dialogLayout = new JFXDialogLayout();
        dialogLayout.setBody(new Text("Are you sure you want to delete " + activities
                .get(i).getCategory() + " ?"));

        JFXDialog dialog = new JFXDialog(stackPane, dialogLayout, JFXDialog.DialogTransition.CENTER);

        JFXButton buttonYes = new JFXButton("Yes");
        JFXButton buttonNo = new JFXButton("No");

        buttonYes.setOnAction(event -> {
            activities_removed(cross);
            dialog.close();
        });
        buttonNo.setOnAction(event -> dialog.close());

        dialogLayout.setActions(buttonYes, buttonNo);

        dialog.show();
    }

    /**
     * Adds Listener on ChoiceBox from LeaderBoard tab
     */
    public void leaderboardChoosed() {
        setLeaderBoard();

        choiceBox.getSelectionModel().selectedItemProperty()
                .addListener( (v,oldValue,newValue) -> {
            if ((choiceBox.getValue()).equals("Friends")){
                setLeaderBoard();
            }
            else {
                top20leaderboard();
            }
        }  );
    }

    /**
     * Sets the Spinner and text when an activity is submitted.
     */
    public void activityChoosed(){
        hBox.getChildren().clear();

        String activity = comboBox.getValue();

        switch(activity) {

            case "Eating A Vegetarian Meal":
                categoryId = 1;
                break;
            case "Buying Local Produce":
                categoryId = 2;
                break;
            case "Biking instead of Driving":
                categoryId = 3;
                break;
            case "Using Public Transport instead of Driving" :
                categoryId = 4;
                break;
            case "Installing Solar Panels":
                categoryId = 5;
                break;
            case "Lowering the Temperature of your Home":
                categoryId = 6;
                break;

        }

        JFXButton addActivity = new JFXButton("Add Activity !");
        addActivity.setTextFill(Color.WHITE);
        addActivity.setStyle("-fx-background-color:  #1D7874");
        addActivity.setButtonType(JFXButton.ButtonType.RAISED);

        Spinner<Double> spinner = new Spinner<>();
        spinner.setEditable(true);
        JFXDatePicker datePick = new JFXDatePicker();
        JFXSlider slider = new JFXSlider();

        //Sets a different initialValue (Spinner) for different activities
        if (!(activity.equals("Biking instead of Driving") || activity.equals("Using Public Transport instead of Driving"))) {
            SpinnerValueFactory<Double> spinnerVal = new SpinnerValueFactory.DoubleSpinnerValueFactory(1, Double.MAX_VALUE, 1);
            spinner.setValueFactory(spinnerVal);

            hBox.getChildren().addAll(spinner, addActivity);
        }
        else {
            SpinnerValueFactory<Double> spinnerDoubleVal = new SpinnerValueFactory.DoubleSpinnerValueFactory(0, Double.MAX_VALUE, 0.1);
            spinner.setValueFactory(spinnerDoubleVal);

            hBox.getChildren().addAll(spinner, addActivity);
        }

        //Sets different texts for every activities
        if ("Eating A Vegetarian Meal".equals(activity)) {
            activityText.setText("How many servings have you eaten ?");
        } else if ("Biking instead of Driving".equals(activity) || "Using Public Transport instead of Driving".equals(activity)) {
            activityText.setText("For how many kilometer have you used it ?");
        } else if ("Buying Local Produce".equals(activity)) {
            activityText.setText("How many times have you bought local produces ?");
        } else if ("Installing Solar Panels".equals(activity)) {
            activityText.setText("Since when have you been using Solar Panels ?");

            hBox.getChildren().clear();

            datePick.setDefaultColor(Color.valueOf("#1D7874"));
            datePick.getStylesheets().add(getClass().getResource("/CSS/DatePicker.css").toExternalForm());
            datePick.setDayCellFactory(picker -> new DateCell() {
                public void updateItem(LocalDate date, boolean empty) {
                    super.updateItem(date, empty);
                    LocalDate today = LocalDate.now();

                    setDisable(empty || date.compareTo(today) > 0 );
                }
            });
            datePick.setPromptText("Pick a Date");

            hBox.getChildren().addAll(datePick, addActivity);
        } else if ("Lowering the Temperature of your Home".equals(activity)) {
            activityText.setText("How many degrees have you been lowering your home's temperature each day by?");
            hBox.getChildren().clear();

            slider.getStylesheets().add(getClass().getResource("/CSS/Slider.css").toExternalForm());

            slider.setPrefWidth(270);
            slider.setMin(0.1);
            slider.setMax(10);

            slider.setShowTickLabels(true);

            slider.setValueFactory(mySlider ->
                Bindings.createStringBinding(
                        () -> (new DecimalFormat("#.#").format(mySlider.getValue())), mySlider.valueProperty()
                )
            );

            hBox.getChildren().addAll(slider, addActivity);
        }

        //Listener added to change the value of the spinner when it has been entered manually
        spinner.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                spinner.increment(0);
            }
        });

        addActivity.setOnMouseClicked(event ->{
            hBox.getChildren().clear();
            activityText.setText("");

            if(activity.equals("Installing Solar Panels")) {
                LocalDate date = datePick.getValue();
                LocalDate now = LocalDate.now();
                double amount = DAYS.between(date, now);
                Client.addActivity(LogInController.sessionCookie, categoryId, amount);
            }
            else if(activity.equals("Lowering the Temperature of your Home")){
                double amount = slider.getValue();
                Client.addActivity(LogInController.sessionCookie, categoryId, amount);
            }
            else {
                double amount = spinner.getValue();
                Client.addActivity(LogInController.sessionCookie, categoryId, amount);
            }

            // Refreshing the user and getting the new info
            refresh();
            experience();
            progress.setProgress(levelPercentage / 100.0);
            if ((int) levelNumber > temp) {
                levelUp(levelNumber);
            }

            Recommendation();
            showUserActivities();
            setLeaderBoard();
        });
    }

    /**
     * Sets the levels of experience exponentially
     */
    public void experience(){
        if(user.getExperience_points() < 1){
            levelNumber = 1;
            temp = levelNumber;
            levelPercentage = (user.getExperience_points()/2)*100;

        }
        else {
            temp = (int) levelNumber;

            levelNumber = Math.log(user.getExperience_points()) / Math.log(2) + 1;
            levelPercentage = (user.getExperience_points() - Math.pow(2, ((int) (levelNumber - 1)))) / (Math.pow(2, ((int) levelNumber)) - Math.pow(2, (int) (levelNumber - 1))) * 100;
        }

        achievements();
        ranking();
        level.setText("lvl"+ (int) levelNumber);
    }

    /**
     * JFXDialog pops up when when increase in level
     * @param levelNumber
     */
    public void levelUp(double levelNumber){

        JFXDialogLayout dialogLayout = new JFXDialogLayout();
        dialogLayout.setBody(new Text("Hey, " + user.getUsername() +", Congratulations! \nyou have just leveled up to level " + (int) levelNumber + "!"));


        JFXDialog dialog = new JFXDialog(levelPopUp, dialogLayout, JFXDialog.DialogTransition.NONE);

        dialog.setMaxWidth(levelPopUp.getMaxWidth());
        dialog.setMaxHeight(levelPopUp.getMaxHeight());

        JFXButton buttonX = new JFXButton("Close");

        buttonX.setOnAction(event -> dialog.close());

        dialogLayout.setActions(buttonX);

        dialog.show();
    }

    /**
     * Sets the Top20 LeaderBoard
     */
    public void top20leaderboard(){
        leaderBoard.setVisible(false);
        list.setVisible(true);
        list.getChildren().clear();

        int sz = top20leaderboard.size();

        Text empty = new Text();
        empty.setText("\n");
        //list.getChildren().add(empty);

        Text title = new Text();
        title.setText(" Top 20 LeaderBoard");
        title.setFont(Font.font(18));
        title.setTextAlignment(TextAlignment.CENTER);
        title.setFill(Color.WHITE);
        list.getChildren().add(title);

        if(top20leaderboard.isEmpty()){
            Text start = new Text();
            start.setText("No activities");
            start.setFill(Color.WHITE);
            list.getChildren().add(start);
        }
        else {
            for (int i = 0; i < sz; i++) {
                HBox hBox = new HBox();
                Text temp = new Text();

                String ret = "  ";
                ret += i + 1 + " - ";
                ret += top20leaderboard.get(i).getUsername() + " with ";
                ret += new DecimalFormat("#.##").format(top20leaderboard.get(i).getExperience_points()) + " XP points " ;

                temp.setText(ret);
                temp.setFont(Font.font(18));
                temp.setFill(Color.WHITE);

                hBox.getChildren().addAll(temp);

                list.getChildren().add(hBox);
            }
        }
        list.setAlignment(Pos.CENTER);
    }

    /**
     * Sets the Friends LeaderBoard
     */
    public void setLeaderBoard(){
        choiceBox.setValue("Friends");

        leaderBoard.setVisible(true);
        list.setVisible(false);
        xAxis.getCategories().clear();
        leaderBoard.getData().clear();

        int sz = following.size();

        yAxis.setTickLabelFill(Color.WHITE);
        xAxis.setTickLabelFill(Color.WHITE);
        xAxis.getCategories().add("Your Score");

        XYChart.Series<String, Double> chart = new XYChart.Series<>();
        chart.getData().add(new XYChart.Data<>("Your Score", user.getExperience_points()));

        if(!following.isEmpty()) {
            for (int i = 0; i < sz; i++) {
                xAxis.getCategories().add(following.get(i).getUsername());
                chart.getData().add(new XYChart.Data<>(following.get(i).getUsername(), following.get(i).getExperience_points()));
            }
            leaderBoard.setBarGap(0);
            leaderBoard.getData().addAll(chart);

        }
        else {
            leaderBoard.getData().addAll(chart);
            leaderBoard.setBarGap(200);
        }
    }

    /**
     * Method used to set the Followers and Followings using the respective Lists created.
     * @param follow
     * @param pane
     */
    public void setFriends(List<UserProjection> follow, VBox pane){
        int sz = follow.size();

        pane.getChildren().clear();

        if(!follow.isEmpty()) {
            for (int i = 0; i < sz; i++) {
                StackPane stackPane = new StackPane();
                HBox hBox = new HBox();
                Text temp = new Text();
                Text cross = new Text();
                temp.setFill(Color.WHITE);

                String ret = i + 1 + " - " + follow.get(i).getUsername();

                temp.setText(ret);

                if(pane.equals(followingPane)) {
                    hBoxHovered(hBox, cross);
                    temp.setOnMouseEntered(event -> temp.setCursor(Cursor.HAND));
                    temp.setOnMouseClicked(event -> seeFriend(stackPane, temp.getText()));
                }

                cross.setOnMouseClicked(event -> followerRemoved(temp));

                hBox.getChildren().addAll(temp, cross);
                stackPane.getChildren().add(hBox);
                pane.getChildren().add(stackPane);
            }
        }
    }

    /**
     * Methods that shows the User's following friends information
     * @param stackPane
     * @param txt
     */
    public void seeFriend(StackPane stackPane, String txt){
        String[] txtSplit = txt.split(" - ");

        int i = Integer.parseInt(txtSplit[0]) - 1;
        double level;

        ProgressIndicator userIndic = new ProgressIndicator();
        if(following.get(i).getExperience_points() < 1){
            level = 1;
            userIndic.setProgress((following.get(i).getExperience_points()/2));

        }
        else {
            level = ((int) (Math.log(following.get(i).getExperience_points()) / Math.log(2) + 1));
            userIndic.setProgress((following.get(i).getExperience_points() - Math.pow(2, ((int) (level - 1)))) / (Math.pow(2, ((int) level)) - Math.pow(2, (int) (level - 1))));
        }

        VBox vBox = new VBox();
        userIndic.setPrefSize(130, 115);

        double xp = following.get(i).getExperience_points();
        Text points = new Text("XP points collected: " + new DecimalFormat("#.##").format(xp));

        Text levelTxt = new Text("He/She is currently on level: " + ((int)level));

        vBox.getChildren().addAll(userIndic, points, levelTxt);

        JFXDialogLayout dialogLayout = new JFXDialogLayout();
        dialogLayout.setHeading(new Text("This is " + following.get(i).getUsername() + " Score !"));
        dialogLayout.setBody(vBox);

        JFXDialog dialog = new JFXDialog(stackPane, dialogLayout, JFXDialog.DialogTransition.CENTER);
        dialog.setMaxWidth(311);

        JFXButton button = new JFXButton("Close");
        button.setOnAction(event -> dialog.close());

        dialogLayout.setActions(button);

        dialog.show();
    }

    /**
     * Removes activity from List activities and refreshes the activities' List
     * @param cross
     */
    public void activities_removed(Text cross){
        HBox temp = (HBox)cross.getParent();
        ObservableList<Node> list = temp.getChildren();

        Text user_info = (Text)list.get(0);

        String[] user_split = user_info.getText().split(" -");
        int nbr = Integer.parseInt(user_split[0]) - 1;

        Client.removeActivity(LogInController.sessionCookie, activities.get(nbr).getId());

        refresh();
        setLeaderBoard();
        experience();
        no1 = top20leaderboard.get(0).getUsername();
        ranking();

        showUserActivities();
        progress.setProgress(levelPercentage/100.0);
    }

    /**
     * Sets the cross to visible when Text Hovered
     * @param hBox
     * @param cross
     */
    public void hBoxHovered(HBox hBox, Text cross){
        cross.setText("❌");
        cross.setVisible(false);
        hBox.setSpacing(15);

        hBox.setOnMouseEntered(event -> {
            cross.setVisible(true);
            cross.setCursor(Cursor.HAND);
        });
        hBox.setOnMouseExited(event -> cross.setVisible(false));
    }

    /**
     * Removes follower from List followers and refreshes the followers' List
     * @param text
     */
    public void followerRemoved(Text text){
        String[] temp = text.getText().split(" - ");

        Client.removeFollow(LogInController.sessionCookie, temp[1]);
        refresh();
        setFriends(following, followingPane);
        setLeaderBoard();
    }

    /**
     * Adds a Friend in the Following Pane, when "follow a friend" is clicked
     */
    public void addFollow(){
        errorUser.setText("");
        String add = friend.getText();

        String response = Client.addFollow(LogInController.sessionCookie, add);

        if(!response.equals("Your followings have been updated!")) {
            errorUser.setText(response);
            errorUser.setFill(Color.RED);
            errorUser.setFont(Font.font(20));
        }

        refresh();
        setFriends(following, followingPane);
        setLeaderBoard();
    }

    /**
     * Sets the text seen in the "Info" tab
     */
    public void text(){
        Text text = new Text();
        text.setFont(Font.font("Algerian",18));
        text.setText("How do we calculate your XP Points? \n");
        text.setFill(Color.WHITE);
        info.getChildren().add(text);

        Text text1 = new Text();
        text1.setFont(Font.font(16));
        text1.setFill(Color.WHITE);
        text1.setText("As you know, a user earns XP Points based on the activity done." +
                "We calculated the XP Points based on the amount of Carbon Dioxide that activity saves." +
                "Because we couldn't find any external APIs that we found directly applicable for our activity scope" +
                "and we decided to store each activity's value on the database.\n\n" +
                "Using the internet (only reliable .gov, .net and well-known .com websites) we found the amount" +
                "of carbon dioxide that activity saves. In some cases, for example using public transportation instead of" +
                "a car, we needed to find how many CO2 a car uses per km and how many CO2 a transportation vehicle" +
                "uses per km and we calculated the deficit.\n\n" +
                "Because of the available information online, we used the" +
                "following parameters for adding an activity:\n" +
                "• Eating a vegetarian meal/per serving\n" +
                "• Buying a local produce/per number of items\n" +
                "• Using a bike instead of car/per km\n" +
                "• Using public transport instead of car/per km\n" +
                "• Lowering the temperature of your home by 1 degree Celcius/per day\n" +
                "• Installing solar panels/per day\n");

        Text text2 = new Text();
        text2.setFill(Color.WHITE);
        text2.setText("After the calculation of the amount of carbon dioxide saved, we divided each number by 100 to have different xp points for each activity. We decided to divide by 100 because we thought that it will be easier to convert xp points from amount of co2 saved and vice versa. This also helped us along the design process.  If we used 1000 or more, the value of points would have been too low. We also considered the level ranking of the user and wanted to keep the calculation for gamification as simple as possible for the sustainability of the project.\n\n");

        Text text3 = new Text();
        text3.setFill(Color.WHITE);
        text3.setText("How do the recommendations work? \n" );
        text3.setFont(Font.font("Calibre", FontWeight.BOLD,16));

        Text text4 = new Text();
        text4.setFill(Color.WHITE);
        text4.setText("Simple. Efficient. Easy-to-understand. We track your activities and categorize them into 3 groups which are food, household, transportation. Some activities like eating a vegetarian meal only belong to one category, that being food. But some like buying local produce belong to more than categories like food and transportation as this both contributes to the prevention of transfer of products and the usage of local food. Based on the number of activities done on each super-category, we give you some recommendations from our recommendation pool. We hope you'll like them!");

        Text text5 = new Text();
        text5.setFill(Color.WHITE);
        text5.setText("Credits\n" +
                "\n" +
                "The TEAM \n" +
                "Sina Sen\n" +
                "Cosmin Octavian Pene\n" +
                "Sven Van Collenburg\n" +
                "Warren Akrum\n" +
                "Julien Lamon\n" +
                "Tommaso Tofacchi\n" +
                "\n" +
                "The Consultant\n" +
                "Issa Hanou\n" +
                "\n" +
                "The TEAM Base\n" +
                "Pulse Technology Room\n" +
                "\n" +
                "\n" +
                "Special thanks to everyone who supported us! ❤\uD83C\uDF33");
        info.getChildren().addAll(text1, text2, text3, text4, text5);
    }
}

