package classes;

import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Optional;

/**
 * controller for the window "Fach" | inserts Fach in table fach in DB
 * @author Thierry Beer
 */

public class FackController {


    Database db = new Database();

    private Main main;
    private NewCardControllerererer ncontrol;
    private Controller control;
    private String fack = "";

    @FXML
    TextField txtNewFach;
    @FXML
    Text txtCreatedFack;

    public void setMain(Main main){
        this.main = main;
    }
    public void setNcontrol(NewCardControllerererer ncontrol){
        this.ncontrol = ncontrol;
    }
    public void setControl(Controller control){
        this.control = control;
    }

    public void crtNewFach() {

        Alert alert = new Alert(Alert.AlertType.WARNING);
        ButtonType OK = new ButtonType("OK", ButtonBar.ButtonData.CANCEL_CLOSE);
        if(txtNewFach.getText().equals("")||txtNewFach.getText().contains(" ")) {
            alert.getButtonTypes().setAll(OK);
            alert.setTitle("Achtung");
            alert.setHeaderText("Falsches Format");
            alert.setContentText("Das Fach darf keine Leerzeichen enthalten oder gar leer sein!");
            alert.show();
            txtNewFach.setText("");
            return;
        }
        db.insertF(txtNewFach.getText());
        txtCreatedFack.setText("");
        fack = txtNewFach.getText();
        txtCreatedFack.setText("Kategorie '"+fack+"' erstellt!");
        txtCreatedFack.setTextAlignment(TextAlignment.CENTER);
        txtNewFach.setText("");
        FadeTransition ft = new FadeTransition(Duration.millis(2000), txtCreatedFack);
        ft.setFromValue(1.0);
        ft.setToValue(0.1);
        ft.setCycleCount(Timeline.INDEFINITE);
        ft.setAutoReverse(true);
        ft.play();
    }

    public void fachBack() throws IOException {
        main.getCreateFackStage().close();
    }

}
