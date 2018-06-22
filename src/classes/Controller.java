package classes;

import com.sun.org.apache.bcel.internal.generic.NEW;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class Controller {

    private Main main;

    @FXML
    TextField card;
    @FXML
    Text lblCount;
    @FXML
    ImageView imgBitchSan;
    @FXML
    AnchorPane vidPane;

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
    }

    public void delDB() throws IOException, InterruptedException {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        ButtonType ja = new ButtonType("JA, löschen");
        ButtonType nein = new ButtonType("NEIN", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(ja, nein);
        alert.setTitle("Löschen der Datenbank");
        alert.setHeaderText("WARNUNG");
        alert.setContentText("Möchten Sie wirklich alle vorhandenen Daten löschen?");
        Optional<ButtonType> res = alert.showAndWait();
        if (res.get() == ja) {
            Script.delDB();
            Thread.sleep(1000);
            db.connect();
            db.createTable();
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