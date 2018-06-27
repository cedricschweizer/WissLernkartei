package classes;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class LoadDBController {

    private Main main;
    private Controller controller;
    String sqlString = "";
    Database db = new Database();

    String sqlFach;
    String sqlKat;

    @FXML
    ComboBox cbLoadDBFach;
    @FXML
    ComboBox cbLoadDBKat;

    public void setMain(Main main){
        this.main = main;
    }
    public void setNativeController(Controller controller){
        this.controller = controller;
    }

    public void loadStackDB() throws SQLException {

        ResultSet result = db.select("Select fach, kategorie from WLK;");
        while (result.next()) {
            cbLoadDBFach.getItems().add(result.getString("fach"));
            cbLoadDBKat.getItems().add(result.getString("kategorie"));
        }

        Alert alertWarning = new Alert(Alert.AlertType.WARNING);
        ButtonType cont = new ButtonType("Ja");
        ButtonType canc = new ButtonType("Abbrechen", ButtonBar.ButtonData.CANCEL_CLOSE);
        if(cbLoadDBKat.getValue() == "" || cbLoadDBFach.getValue() == "") {
            alertWarning.getButtonTypes().setAll(cont, canc);
            alertWarning.setTitle("Nicht alle Felder ausgefüllt!");
            alertWarning.setHeaderText("Achtung!");
            alertWarning.setContentText("Weiterfahren ohne Kategorie/Fach?");
            Optional<ButtonType> res = alertWarning.showAndWait();
            if (res.get() == canc)
                return;
        }

        sqlString = "where fach like '" + cbLoadDBFach.getValue() + "' and kategorie like '" + cbLoadDBKat.getValue()+"';";

        ResultSet rs = db.select("Select vorderseite, hinterseite, bild, fach, kategorie from WLK " + sqlString);
        ArrayList<Card> tmpList = new ArrayList<>();
        while (rs.next()){
            if (rs.getString("bild").equals("")){
                tmpList.add(new Card(rs.getString("vorderseite"), rs.getString("hinterseite"), rs.getString("fach"),
                        rs.getString("kategorie")));
            }
            else{
                tmpList.add(new Card(rs.getString("vorderseite"), rs.getString("hinterseite"), rs.getString("fach"),
                        rs.getString("kategorie"), rs.getString("bild")));
            }
        }
        controller.cardTexts = tmpList;
        controller.showCard();
        main.getLoadDbStage().close();
    }
}
