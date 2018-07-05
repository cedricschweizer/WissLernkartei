package classes;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.Optional;

/**
 * class to control process "create new card" | inserts Kategorie into table kat in DB
 * @author Cédric Schweizer
 */

public class KataController {

    Database db = new Database();

    private Main main;
    private NewCardControllerererer ncontrol;
    private Controller control;

    @FXML
    TextField txtNewKat;

    public void setNcontrol(NewCardControllerererer ncontrol){
        this.ncontrol = ncontrol;
    }
    public void setControl(Controller control){
        this.control = control;
    }

    public void setMain(Main main){
        this.main = main;
    }


    public void crtNewKat() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        ButtonType OK = new ButtonType("OK", ButtonBar.ButtonData.CANCEL_CLOSE);
        if(txtNewKat.getText().equals("")||txtNewKat.getText().contains(" ")) {
            alert.getButtonTypes().setAll(OK);
            alert.setTitle("Achtung");
            alert.setHeaderText("Falsches Format");
            alert.setContentText("Die Kategorie darf keine Leerzeichen enthalten oder gar leer sein!");
            alert.show();
            txtNewKat.setText("");
            return;
        }
        Alert info = new Alert(Alert.AlertType.INFORMATION);
        ButtonType oggei = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);

        info.getButtonTypes().setAll(oggei);
        info.setTitle("Information");
        info.setHeaderText("Kategorie erstellt");
        info.setContentText("Die Kategorie wurde erfolgreich erstellt!");
        Optional<ButtonType> res = info.showAndWait();
        db.insertK(txtNewKat.getText());
        txtNewKat.setText("");
    }

    public void katBack() throws IOException {
        main.getCreateKataStage().close();
    }

}
