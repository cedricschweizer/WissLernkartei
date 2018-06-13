package m;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.util.Duration;

import java.util.ArrayList;

public class Controller {

    @FXML TextField card;

    private Timeline timeline = new Timeline();
    private ArrayList<Card> cardTexts = new ArrayList<>();

    private boolean turnState;
    private int currentCard = 0;

    public void turnCard() throws InterruptedException {
        if (timeline.getStatus() == Animation.Status.RUNNING)
            return;
        card.setText("");

        timeline.setCycleCount(2);
        timeline.setAutoReverse(true);
        final KeyValue kv = new KeyValue(card.prefWidthProperty(), 30);
        final KeyFrame kf = new KeyFrame(Duration.millis(600), kv);
        final KeyValue kv2 = new KeyValue(card.layoutXProperty(), card.getLayoutX() + card.getWidth()/2);
        final KeyFrame kf2 = new KeyFrame(Duration.millis(600), kv2);
        timeline.getKeyFrames().add(kf);
        timeline.getKeyFrames().add(kf2);
        timeline.play();

        turnState = !turnState;
        showCard();
    }

    private void addNewCard(String vds, String rs){
        cardTexts.add(new Card(vds, rs));
    }
    public void addSome(){
        addNewCard("noice", "hhhhhh");
        addNewCard("qwer", "uiop");
        addNewCard("asdf", "hjlk");
        addNewCard("ycxv", "vbmn");
        addNewCard("jesus", "christus");
    }
    public void inc(){
        this.currentCard++;
        this.turnState = false;
        showCard();
    }
    public void dec(){
        this.currentCard--;
        this.turnState = false;
        showCard();
    }
    private void showCard(){
        if (!this.turnState)
            card.setText(cardTexts.get(currentCard).getKey());
        else
            card.setText(cardTexts.get(currentCard).getVal());
    }

}