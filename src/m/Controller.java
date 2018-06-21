package m;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
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


    private Timeline timeline = new Timeline();
    private ArrayList<Card> cardTexts = new ArrayList<>();
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

    public void addNewCard(String vds, String rs) {
        cardTexts.add(new Card(vds, rs));
    }
    public void addNewCard(String vds, String rs, String imgPath){
        cardTexts.add(new Card(vds, rs, imgPath));
    }

    public void init() {
        addNewCard("tescht", "notescht");
        card.widthProperty().addListener((observable, oldValue, newValue) ->
                showCard(newValue.intValue())
        );
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

    private void showCard() {
        Image img = new Image("file:"+null);
        imgBitchSan.setImage(img);
        lblCount.setText(String.valueOf(this.currentCard));

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

    public void saveStack() throws IOException {
        MapParser mapParser = new MapParser("C:\\Users\\Beer Thierry\\Desktop\\Bewerbung\\tescht.cards");
        mapParser.writeCurrentStack(cardTexts);
    }
    public void loadStack() throws IOException {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        ButtonType cont = new ButtonType("Continue");
        ButtonType nope = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(cont, nope);
        Optional<ButtonType> res = alert.showAndWait();
        if (res.get() == nope)
            return;

        FileChooser fc = new FileChooser();
        fc.setTitle("Plis chus filet uwu");
        File filet = fc.showOpenDialog(main.getPrimaryStage());
        MapParser mapParser = new MapParser(filet.getAbsolutePath());
        cardTexts = mapParser.loadStackFromFilet();
    }
    public void execWindow(){
        main.ExecWin();
    }
    public void bsod() throws IOException {
        Script.runYT();
    }

}