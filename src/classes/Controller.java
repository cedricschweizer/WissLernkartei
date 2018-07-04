package classes;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Optional;

public class Controller {

    private Main main;
    private int rightAnswers = 0;
    private int falseAnswers = 0;

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
    Text txtAktuellesFach;
    @FXML
    Text txtAktuelleKat;
    @FXML
    Text txtSolution;
    @FXML
    Button btnInc;


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
        cardTexts.get(currentCard).isLearned = true;
        rightAnswers++;
        cardTexts.get(currentCard).setTrap(true);
        learnedPoints.setText(""+rightAnswers);
        setCBDisable(true);
        cardTexts.get(currentCard).muuf();
        db.update(cardTexts.get(currentCard));
        Alert info = new Alert(Alert.AlertType.NONE);
        ButtonType jawoll = new ButtonType("Ja, speichern");
        ButtonType nain = new ButtonType("Nein, ich kann selber speichern du Affe", ButtonBar.ButtonData.CANCEL_CLOSE);
        info.getButtonTypes().removeAll();
        info.getButtonTypes().addAll(jawoll, nain);
        info.setResizable(true);

        info.setTitle("Fortschritt speichern");
        info.setHeaderText("GUT GEMACHT!");
        info.setContentText("Sie haben alle Karten durchgelernt. Möchten Sie Ihren Fortschritt speichern?");
        for (Card kaads : cardTexts) {
            if (!kaads.isLearned())
                return;
        }
        Optional<ButtonType> buttons = info.showAndWait();
        if (buttons.get() == jawoll) {
            saveStackDB();
            return;
        } else return;
    }

    public void answeredWrong() {
        cardTexts.get(currentCard).isLearned = false;
        falseAnswers++;
        cardTexts.get(currentCard).setTrap(true);
        wrongPoints.setText(""+falseAnswers);
        setCBDisable(true);
        cardTexts.get(currentCard).setEis();
        db.update(cardTexts.get(currentCard));
    }
    private void setCBDisable(boolean b){
        falseAnswer.setDisable(b);
        rightAnswer.setDisable(b);
        falseAnswer.setSelected(false);
        rightAnswer.setSelected(false);
        falseAnswer.setVisible(!b);
        rightAnswer.setVisible(!b);
    }

    public void addNewCard(String vds, String rs, String fach, String kategorie, int stackl, Timestamp time) {
        cardTexts.add(new Card(vds, rs, fach, kategorie, stackl, time));
    }
    public void addNewCard(String vds, String rs, String imgPath, String fach, String kategorie, int staggl, Timestamp time){
        cardTexts.add(new Card(vds, rs, imgPath, fach, kategorie, staggl, time));
    }

    public void initListener() {
        card.widthProperty().addListener((observable, oldValue, newValue) ->
                showCard(newValue.intValue())
        );
    }

    public void showLoadDB(){
        Alert warnungDatenverlust = new Alert(Alert.AlertType.NONE);
        ButtonType oge = new ButtonType("OK");
        ButtonType cancel = new ButtonType("Abbrechen", ButtonBar.ButtonData.CANCEL_CLOSE);
        warnungDatenverlust.getButtonTypes().removeAll();
        warnungDatenverlust.getButtonTypes().addAll(oge, cancel);

        warnungDatenverlust.setTitle("Warnung");
        warnungDatenverlust.setHeaderText("Datenverlust");
        warnungDatenverlust.setContentText("Alle nicht gespeicherten Änderungen werden verworfen. Speichern Sie alle neu erstellten Karten ab oder speichern Sie ihren Fortschritt");
        Optional<ButtonType> buttons = warnungDatenverlust.showAndWait();
        if (buttons.get() == oge) {
            main.LoadDB(this);
        }
    }

    public void inc() {
        if (currentCard + 1 > cardTexts.size() - 1)
            return;
        setCBDisable(true);
        this.currentCard++;
        this.turnState = false;
        showCard();
    }

    public void dec() {
        if (currentCard - 1 < 0)
            return;
        setCBDisable(true);
        this.currentCard--;
        this.turnState = false;
        showCard();
    }

    public void showCard() {
        Image img = new Image("file:"+null);
        imgBitchSan.setImage(img);
        lblCount.setText(String.valueOf(this.currentCard+1) + "/" + String.valueOf(cardTexts.size()));

        if (!this.turnState) {
            txtSolution.setVisible(false);

            card.setText(cardTexts.get(currentCard).getKey());
            setCBDisable(true);
        }
        else {
            card.setText(cardTexts.get(currentCard).getVal());
            if (!cardTexts.get(currentCard).getTrap()){
                txtSolution.setVisible(true);
                setCBDisable(false);
            }
        }
        if (cardTexts.get(currentCard).hasDiggttscher()){
            iameg = new Image("file:" + cardTexts.get(currentCard).getImg());
            imgBitchSan.setImage(iameg);
        }
        txtAktuellesFach.setText(cardTexts.get(currentCard).getFach());
        txtAktuelleKat.setText(cardTexts.get(currentCard).getKategorie());
    }

    private void showCard(int x) {
        if (x <= 40) {
            showCard();
        }
    }

    public void makeNew() {
        main.ChusWindou(this);
    }

    public void saveStackDB(){
        if(!cardTexts.isEmpty()) {
            for (Card cards : cardTexts) {
                if (cards.getId() == -1){
                    db.insert(cards.getKey(), cards.getVal(), cards.getImg(), cards.getFach(), cards.getKategorie(), String.valueOf(cards.getStack()), cards.getTime());
                    System.out.println(" " + cards.getKey().toString() + " " + cards.getVal().toString() + " " + cards.getImg().toString() + " " + cards.getFach().toString() + " " + cards.getKategorie().toString() + " " + String.valueOf(cards.getStack()) + " " + String.valueOf(cards.getTime()));

                } else {
                    db.updateCards("Update WLK set time = "+cards.getTime()+" where id = "+cards.getId() +";");
                }
            }
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Daten gespeichert");
            alert.setHeaderText("Datenbank erfolgreich gespeichert!");
            alert.setContentText("Ihre Daten wurden erfolgreich gespeichert!");
            alert.showAndWait();
        } else {
            Alert saveNothing = new Alert(Alert.AlertType.ERROR);
            saveNothing.setTitle("Daten nicht gefunden");
            saveNothing.setHeaderText("Keine in der Datenbank speicherbaren Daten gefunden");
            saveNothing.setContentText("Es wurden entweder keine Daten zum Speichern in der Datenbank gefunden oder Sie haben versucht, einen leeren Stapel zu speichern. Bitte versuchen Sie es erneut bzw. erstellen Sie einen neuen Stapel!");
            saveNothing.showAndWait();
        }
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
            db.createSuperSafetyTabulettteee();

            cardTexts.clear();
            lblCount.setText(1 + "/" + 1);
            rightAnswers = 0;
            learnedPoints.setText(""+rightAnswers);
            falseAnswers = 0;
            falseAnswer.setText(""+0);
            txtAktuellesFach.setText("");
            txtAktuelleKat.setText("");
            card.setText("");
            txtSolution.setVisible(false);

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
        Alert warnungDatenverlust = new Alert(Alert.AlertType.NONE);
        ButtonType oge = new ButtonType("OK");
        ButtonType cancel = new ButtonType("Abbrechen", ButtonBar.ButtonData.CANCEL_CLOSE);
        warnungDatenverlust.getButtonTypes().removeAll();
        warnungDatenverlust.getButtonTypes().addAll(oge, cancel);

        warnungDatenverlust.setTitle("Warnung");
        warnungDatenverlust.setHeaderText("Wirklich beenden? Achtung Datenverlust");
        warnungDatenverlust.setContentText("Alle nicht gespeicherten Änderungen werden verworfen. Speichern Sie alle neu erstellten Karten ab oder speichern Sie ihren Fortschritt.");
        Optional<ButtonType> buttons = warnungDatenverlust.showAndWait();
        if(buttons.get() == oge)
        System.exit(0);
        else return;
    }

    public void execWindow(){
        main.SSP();
    }
    public void bsod() throws IOException {
        Script.runYT();
    }
    public void setCurrentCard(int x){
        this.currentCard = x;
    }
}