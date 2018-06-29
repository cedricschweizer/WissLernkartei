package classes;

import com.sun.org.apache.bcel.internal.generic.NEW;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import static java.awt.Color.green;

public class Controller {

    private Main main;
    private int rightAnswers = 0;
    private int usefulInt = 0;
    private int falseAnswers = 0;
    private int uselessInt = 0;
    boolean playerWentBack;
    private int cardVal;
    boolean isDecreasable;
    int TexteSize;
    String[] Texte;

    @FXML
    TextField card;
    @FXML
    Text lblCount;
    @FXML
    ImageView imgBitchSan;
    @FXML
    AnchorPane vidPane;
    @FXML
    CheckBox rightAnswer;
    @FXML
    CheckBox falseAnswer;
    @FXML
    Text learnedPoints;
    @FXML
    Text wrongPoints;
    @FXML
    Text trueOrFalse;


    private Database db = new Database();
    private Timeline timeline = new Timeline();
    public ArrayList<Card> cardTexts = new ArrayList<>();
    private Image iameg;
    private AnchorPane defaultPane;

    private boolean turnState;
    private int currentCard = 0;


    public void setMain(Main mao) {
        this.main = mao;
    }
    public void setAPane(AnchorPane pane){
        this.defaultPane = pane;
        playAnimation();
    }
    private void playAnimation(){
        MediaPlayer player = new MediaPlayer( new Media(getClass().getResource("../res/animation.mp4").toExternalForm()));
        MediaView mediaView = new MediaView(player);

        mediaView.setFitHeight(vidPane.getHeight());
        mediaView.setFitWidth(vidPane.getWidth());
        vidPane.getChildren().add(mediaView);
        player.setCycleCount(MediaPlayer.INDEFINITE);
        player.play();
    }

    public void turnCard() throws InterruptedException {

        if (cardTexts.isEmpty())
            return;
        if (timeline.getStatus() == Animation.Status.RUNNING)
            return;
        card.setText("");


        timeline.setCycleCount(2);
        timeline.setAutoReverse(true);
        final KeyValue kv = new KeyValue(card.prefWidthProperty(), 30);
        final KeyFrame kf = new KeyFrame(Duration.millis(300), kv);
        final KeyValue kv2 = new KeyValue(card.layoutXProperty(), card.getLayoutX() + card.getWidth() / 2);
        final KeyFrame kf2 = new KeyFrame(Duration.millis(300), kv2);
        timeline.getKeyFrames().add(kf);
        timeline.getKeyFrames().add(kf2);
        timeline.play();
        turnState = !turnState;
    }

    public void answeredRight() {
        rightAnswers++;
        cardTexts.get(currentCard).setTrap(true);
        learnedPoints.setText(""+rightAnswers);
        setCBDisable(true);
    }

    public void answeredWrong() {
        falseAnswers++;
        cardTexts.get(currentCard).setTrap(true);
        wrongPoints.setText(""+falseAnswers);
        setCBDisable(true);
    }
    private void setCBDisable(boolean b){
        falseAnswer.setDisable(b);
        rightAnswer.setDisable(b);
        falseAnswer.setSelected(false);
        rightAnswer.setSelected(false);
        falseAnswer.setVisible(!b);
        rightAnswer.setVisible(!b);
    }

    public void addNewCard(String vds, String rs, String fach, String kategorie) {
        cardTexts.add(new Card(vds, rs, fach, kategorie));
    }
    public void addNewCard(String vds, String rs, String imgPath, String fach, String kategorie){
        cardTexts.add(new Card(vds, rs, imgPath, fach, kategorie));
    }

    public void initListener() {
        card.widthProperty().addListener((observable, oldValue, newValue) ->
                showCard(newValue.intValue())
        );
    }

    public void showLoadDB(){
        main.LoadDB(this);
    }

    public void inc() {

        if (currentCard + 1 > cardTexts.size() - 1)
            return;
        this.currentCard++;
        this.turnState = false;
        showCard();
    }

    public void dec() {
        if (currentCard - 1 < 0)
            return;
        this.currentCard--;
        this.turnState = false;
        showCard();
    }

    public void showCard() {
        Image img = new Image("file:"+null);
        imgBitchSan.setImage(img);
        lblCount.setText(String.valueOf(this.currentCard+1) + "/" + String.valueOf(cardTexts.size()));

        if (!this.turnState) {
            card.setText(cardTexts.get(currentCard).getKey());
        }
        else {
            card.setText(cardTexts.get(currentCard).getVal());
            if (!cardTexts.get(currentCard).getTrap()){
                setCBDisable(false);
            }
        }
        if (cardTexts.get(currentCard).hasDiggttscher()){
            iameg = new Image("file:" + cardTexts.get(currentCard).getImg());
            imgBitchSan.setImage(iameg);
        }
    }

    private void showCard(int x) {
        if (x <= 40) {
            showCard();
        }
    }

    public void makeNew() {
        main.ChusWindou(this);
    }

    public void saveStackFile() throws IOException {
        String saveFile;

        FileChooser fc = new FileChooser();
        fc.setTitle("Wo soll Ihr Filet gespeichert werden?");
        FileChooser.ExtensionFilter filtu = new FileChooser.ExtensionFilter("TXT Files (*.txt)","*.txt");
        fc.getExtensionFilters().add(filtu);
        File filette = fc.showSaveDialog(main.getPrimaryStage());
        MapParser mapParser = new MapParser(filette.getAbsolutePath());
        mapParser.writeCurrentStack(cardTexts);
    }
    public void saveStackDB(){
        for (Card cards:cardTexts) {
            db.insert(cards.getKey(),cards.getVal(),cards.getImg(),cards.getFach(),cards.getKategorie());
            System.out.println(""+cards.getKey().toString()+""+cards.getVal().toString()+""+cards.getImg().toString()+""+cards.getFach().toString()+""+cards.getKategorie().toString());
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Daten gespeichert");
        alert.setHeaderText("Datenbank erfolgreich gespeichert!");
        alert.setContentText("Ihre Daten wurden erfolgreich gespeichert!");
        alert.showAndWait();
    }
    public void loadStack() throws IOException {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        ButtonType cont = new ButtonType("Continue");
        ButtonType nope = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(cont, nope);
        alert.setTitle("Obstacles foreseen uwu");
        alert.setHeaderText("Attention");
        alert.setContentText("All changes on the current stack will be lost.\nContinue?");
        Optional<ButtonType> res = alert.showAndWait();
        if (res.get() == nope)
            return;

        FileChooser fc = new FileChooser();
        fc.setTitle("Plis chus filet uwu");
        File filet = fc.showOpenDialog(main.getPrimaryStage());
        MapParser mapParser = new MapParser(filet.getAbsolutePath());
        cardTexts = mapParser.loadStackFromFilet();
        showCard();
    }

    public void delDB() throws IOException, InterruptedException, SQLException {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        ButtonType ja = new ButtonType("JA, löschen");
        ButtonType nein = new ButtonType("NEIN", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(ja, nein);
        alert.setTitle("Löschen der Datenbank");
        alert.setHeaderText("WARNUNG");
        alert.setContentText("Möchten Sie wirklich alle vorhandenen Daten löschen?");
        Optional<ButtonType> res = alert.showAndWait();
        if (res.get() == ja) {
            Connection c = DriverManager.getConnection(db.url);
            c.close();

            Script.delDB();
            System.out.println("Pausing 2 sec.");
            Thread.sleep(2000);
            System.out.println("Resuming program.");

            db.connect();
            db.createTable();
            db.createTableF();
            db.createTableK();
            db.createTableTmp();

            Alert deleted = new Alert(Alert.AlertType.INFORMATION);
            ButtonType OK = new ButtonType("OK", ButtonBar.ButtonData.CANCEL_CLOSE);

            deleted.getButtonTypes().setAll(OK);
            deleted.setTitle("Datenbank gelöscht");
            deleted.setHeaderText("Information");
            deleted.setContentText("Datenbank wurde erfolgreich gelöscht.\nBitte erstellen Sie einen neuen Stapel.");
            Optional<ButtonType> del = deleted.showAndWait();
            if (del.get() == OK)
                return;
        }
    }

    public void closeApp() {
        System.exit(0);
    }

    public void execWindow(){
        main.ExecWin();
    }
    public void bsod() throws IOException {
        Script.runYT();
    }
}