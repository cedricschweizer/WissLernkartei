package classes;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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

    String loadDBFach = "";
    String loadDBKat = "";

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

        ResultSet rs = db.select("Select vorderseite, hinterseite, bild, fach, kategorie from WLK where fach like '"+loadDBFach+"' and kategorie like '"+loadDBKat+"';");
        ArrayList<Card> tmpList = new ArrayList<>();
        try {
            while (rs.next()) {
                if (rs.getString("bild").equals("")) {
                    tmpList.add(new Card(rs.getString("vorderseite"), rs.getString("hinterseite"), rs.getString("fach"),
                            rs.getString("kategorie")));
                } else {
                    tmpList.add(new Card(rs.getString("vorderseite"), rs.getString("hinterseite"), rs.getString("fach"),
                            rs.getString("kategorie"), rs.getString("bild")));
                }
            }
            controller.cardTexts = tmpList;
            controller.showCard();
            main.getLoadDbStage().close();
        } catch (Exception e) {
            e.printStackTrace();
            if(loadDBKat == "" || loadDBFach == "") {
                Alert warnungAusfuellen = new Alert(Alert.AlertType.WARNING);
                ButtonType oggei = new ButtonType("Ja, ich habe verstanden und zeige mich demütig uwu", ButtonBar.ButtonData.CANCEL_CLOSE);
                warnungAusfuellen.setTitle("Achtung");
                warnungAusfuellen.setHeaderText("Achtung");
                warnungAusfuellen.setContentText("Bitte wählen Sie je ein Element aus! Falls noch kein Element vorhanden ist, erstellen Sie bitte einen neuen Stapel und speichern Sie diesen ab.");
                Optional<ButtonType> res = warnungAusfuellen.showAndWait();
                if (res.get() == oggei)
                    return;
            }
            else {
                if(!loadDBFach.equals("") && !loadDBKat.equals("")) {
                    Alert warnung = new Alert(Alert.AlertType.ERROR);
                    ButtonType ok = new ButtonType("OK", ButtonBar.ButtonData.CANCEL_CLOSE);
                    warnung.setTitle("Kategoriefehler");
                    warnung.setHeaderText("Fehler beim Laden der Daten");
                    warnung.setContentText("Es scheint so, als wäre in diesem Fach / dieser Kategorie noch keine Daten enthalten. Bitte wählen Sie ein anderes Fach / eine andere Kategorie oder erstellen Sie neue Fächer / Kategorien!");
                    Optional<ButtonType> res = warnung.showAndWait();
                    if (res.get() == ok)
                        return;
                }
                else {
                    Alert warnung = new Alert(Alert.AlertType.ERROR);
                    ButtonType ok = new ButtonType("OK", ButtonBar.ButtonData.CANCEL_CLOSE);
                    warnung.setTitle("FEHLER");
                    warnung.setHeaderText("Fehler beim Laden der Daten");
                    warnung.setContentText("Fehler beim Laden der Daten! Versuchen Sie die Applikation neu zu starten und versuchen Sie es anschliessend noch einmal. Falls dieser Fehler erneut auftritt, kontaktieren Sie bitte den Administrator!");
                    Optional<ButtonType> res = warnung.showAndWait();
                    if (res.get() == ok)
                        return;
                }
            }
        }
    }

    public void loadCBFach() {
        ResultSet rsLoadFach = db.select("Select distinct fach from fach;");

        try {
            while (rsLoadFach.next()) {
                cbLoadDBFach.getItems().addAll(rsLoadFach.getString("fach"));
                System.out.print("Fach: ");
                System.out.print(rsLoadFach.getString("fach"));
                cbLoadDBFach.getSelectionModel().selectedItemProperty()
                        .addListener(new ChangeListener<String>() {
                            public void changed(ObservableValue<? extends String> observable,
                                                String oldValue, String newValue) {
                                loadDBFach = newValue;
                                System.out.println(loadDBFach);
                            }
                        });
            }
            cbLoadDBFach = new ComboBox();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void loadCBKat() {
        ResultSet rsLoadKat = db.select("Select distinct kategorie from kat;");

        try {
            while (rsLoadKat.next()) {
                cbLoadDBKat.getItems().addAll(rsLoadKat.getString("kategorie"));
                System.out.print("Kategorie: ");
                System.out.print(rsLoadKat.getString("kategorie"));
                cbLoadDBKat.getSelectionModel().selectedItemProperty()
                        .addListener(new ChangeListener<String>() {
                            public void changed(ObservableValue<? extends String> observable,
                                                String oldValue, String newValue) {
                                loadDBKat = newValue;
                                System.out.println(loadDBKat);
                            }
                        });
            }
            cbLoadDBKat = new ComboBox();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
