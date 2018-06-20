package m;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.ArrayList;

public class Controller {

    private Main main;

    @FXML
    TextField card;
    @FXML
    Text lblCount;
    @FXML
    ImageView imgBitchSan;


    private Timeline timeline = new Timeline();
    private ArrayList<Card> cardTexts = new ArrayList<>();
    private Image iameg;

    private boolean turnState;
    private int currentCard = 0;


    public void setMain(Main mao) {
        this.main = mao;
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
        /*

        card.setText("");
        timeline.setCycleCount(2);
        timeline.setAutoReverse(true);
        final KeyValue kv = new KeyValue(card.opacityProperty(), 0);
        final KeyFrame kf = new KeyFrame(Duration.millis(50), kv);
        timeline.getKeyFrames().add(kf);
        timeline.play();
        turnState = !turnState;
        */
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

    public void addSome() {

        addNewCard("noice", "hhhhhh");
        addNewCard("qwer", "uiop");
        addNewCard("asdf", "hjlk");
        addNewCard("ycxv", "vbmn");
        addNewCard("jesus", "christus");
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

}