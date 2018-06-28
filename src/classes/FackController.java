package classes;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.Optional;

public class FackController {

    String tmpVS = "";
    String tmpRS = "";
    String tmpFach = "";
    String tmpKat = "";
    String tmpPath = "";

    Database db = new Database();

    private Main main;
    private NewCardControllerererer ncontrol;
    private Controller control;

    @FXML
    TextField txtNewFach;

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
        Alert info = new Alert(Alert.AlertType.INFORMATION);
        ButtonType oggei = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);

        info.getButtonTypes().setAll(oggei);
        info.setTitle("Information");
        info.setHeaderText("Fach erstellt");
        info.setContentText("Das Fach wurde erfolgreich erstellt!");
        Optional<ButtonType> res = info.showAndWait();
        db.insertF(txtNewFach.getText());
        txtNewFach.setText("");
    }

    public void fachBack() throws IOException {
        main.getCreateFackStage().close();
    }

}
