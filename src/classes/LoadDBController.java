package classes;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

/**
 * this class controls the loading (selecting) of the data from the database
 * @author Cédric Schweizer, Thierry Beer
 */

public class LoadDBController {

    private Main main;
    private Controller controller;
    Database db = new Database();

    String loadDBFach = "";
    String loadDBKat = "";
    String loadDBStack = "";
    boolean böleanKattlä;
    boolean böleanFack;
    boolean böleanStaggl;

    @FXML
    ComboBox cbLoadDBFach;
    @FXML
    ComboBox cbLoadDBKat;
    @FXML
    ComboBox cbLoadDBStack;
    @FXML
    Button btnLoad;
    @FXML
    Text textDate;
    @FXML
    Button btnConfirm;

    public void setMain(Main main){
        this.main = main;
    }

    public void setNativeController(Controller controller){
        this.controller = controller;
    }

    public void loadStackDB() throws SQLException {
        ResultSet rs = db.select("Select id, vorderseite, hinterseite, bild, fach, kategorie, stack, time from WLK where fach like '" + loadDBFach + "' and kategorie like '" + loadDBKat + "' and stack like '" + loadDBStack + "';");
        ArrayList<Card> tmpList = new ArrayList<>();
        try {
            while (rs.next()) {
                if (rs.getString("bild").equals("")) {
                    tmpList.add(new Card(Integer.parseInt(rs.getString("id")), rs.getString("vorderseite"), rs.getString("hinterseite"), rs.getString("fach"),
                            rs.getString("kategorie"), Integer.parseInt(rs.getString("stack")), rs.getTimestamp("time")));
                } else {
                    tmpList.add(new Card(Integer.parseInt(rs.getString("id")), rs.getString("vorderseite"), rs.getString("hinterseite"), rs.getString("fach"),
                            rs.getString("kategorie"), rs.getString("bild"), Integer.parseInt(rs.getString("stack")), rs.getTimestamp("time")));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (loadDBKat == "" || loadDBFach == "" || loadDBStack == "") {
                Alert warnungAusfuellen = new Alert(Alert.AlertType.WARNING);
                ButtonType oggei = new ButtonType("Ja, ich habe verstanden und zeige mich demütig uwu", ButtonBar.ButtonData.CANCEL_CLOSE);
                warnungAusfuellen.setTitle("Achtung");
                warnungAusfuellen.setHeaderText("Achtung");
                warnungAusfuellen.setContentText("Bitte wählen Sie je ein Element aus! Falls noch kein Element vorhanden ist, erstellen Sie bitte einen neuen Stapel und speichern Sie diesen ab.");
                Optional<ButtonType> res = warnungAusfuellen.showAndWait();
                if (res.get() == oggei)
                    return;
            } else {
                if (!loadDBFach.equals("") && !loadDBKat.equals("") && !loadDBStack.equals("")) {
                    Alert warnung = new Alert(Alert.AlertType.ERROR);
                    ButtonType ok = new ButtonType("OK", ButtonBar.ButtonData.CANCEL_CLOSE);
                    warnung.setTitle("Kategoriefehler");
                    warnung.setHeaderText("Fehler beim Laden der Daten");
                    warnung.setContentText("Es scheint so, als wäre in diesem Fach / dieser Kategorie noch keine Daten enthalten. Bitte wählen Sie ein anderes Fach / eine andere Kategorie oder erstellen Sie neue Fächer / Kategorien!");
                    Optional<ButtonType> res = warnung.showAndWait();
                    if (res.get() == ok)
                        return;
                } else {
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
        controller.cardTexts = tmpList;
        controller.setCurrentCard(0);
        controller.showCard();
        main.getLoadDbStage().close();
    }

    public void loadCBfach() {
        checkStatsch();
        ResultSet rsLoadFach = db.select("Select distinct fach from fach;");
        try {
            if(!böleanFack) {
                while (rsLoadFach.next()) {
                    cbLoadDBFach.getItems().addAll(rsLoadFach.getString("fach"));
                    böleanFack = true;
                    System.out.print("Fach: ");
                    System.out.print(rsLoadFach.getString("fach"));
                }
            }
            cbLoadDBFach.getSelectionModel().selectedItemProperty()
                    .addListener((ChangeListener<String>) (observable, oldValue, newValue) -> {
                        loadDBFach = newValue;
                        System.out.println(loadDBFach);
                    });
            System.out.println(""+loadDBFach);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadCBkat() {
        checkStatsch();
        ResultSet rsLoadKat = db.select("Select distinct kategorie from kat;");
        try {
            if(!böleanKattlä) {
                while (rsLoadKat.next()) {
                    cbLoadDBKat.getItems().addAll(rsLoadKat.getString("kategorie"));
                    böleanKattlä = true;
                    System.out.print("Kategorie: ");
                    System.out.print(rsLoadKat.getString("kategorie"));
                }
            }
            cbLoadDBKat.getSelectionModel().selectedItemProperty()
                    .addListener(new ChangeListener<String>() {
                        public void changed(ObservableValue<? extends String> observable,
                                            String oldValue, String newValue) {
                            loadDBKat = newValue;
                            System.out.println(loadDBKat);
                        }
                    });
            System.out.println(""+loadDBKat);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadCBstack() {
        checkStatsch();
        ResultSet rsLoadStack = db.select("Select distinct stack from WLK where fach like '" + loadDBFach + "' and kategorie like '" + loadDBKat + "';");
        try {
            if (!böleanStaggl){
                while (rsLoadStack.next()) {
                    cbLoadDBStack.getItems().addAll(rsLoadStack.getString("stack"));
                    böleanStaggl = true;
                    System.out.print("Stacki: ");
                    System.out.print(rsLoadStack.getString("stack"));
                }
            }
            cbLoadDBStack.getSelectionModel().selectedItemProperty()
                    .addListener(new ChangeListener<String>() {
                        public void changed(ObservableValue<? extends String> observable,
                                            String oldValue, String newValue) {
                            loadDBStack = newValue;
                            System.out.println(loadDBStack);
                        }
                    });
            System.out.println(""+loadDBStack);
            if(cbLoadDBStack.getItems().isEmpty()) {
                main.LoadDB(controller);
                Alert fehler = new Alert(Alert.AlertType.ERROR);
                fehler.setTitle("Error");
                fehler.setHeaderText("Fehler!");
                fehler.setContentText("Fach und Kategorie passen nicht zusammen oder es sind noch keine Stapel darin vorhanden!");
                fehler.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void confirm() {
        if(!loadDBFach.equals("") && !loadDBKat.equals("") && !loadDBStack.equals("")) {
            setDate();
            btnConfirm.setDisable(true);
            btnLoad.setDisable(false);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Felder nicht ausgefüllt!");
            alert.setContentText("Sie haben nicht alle Felder ausgefüllt. Bitte tun Sie dies, bevor Sie auf 'Bestätigen' drücken.");
            alert.show();
        }
    }

    public void setDate() {
        if(!loadDBFach.equals("") && !loadDBKat.equals("") && !loadDBStack.equals("")) {
            ResultSet rsDate = db.select("Select time from WLK where fach like '"+loadDBFach+"' and kategorie like '"+loadDBKat+"' and stack like '"+loadDBStack+"' order by time desc;");
            System.out.println("select durchgeführt");
            try {
                textDate.setText("Zuletzt gelernt: \n\n"+rsDate.getString("time"));
                System.out.println("Date selected!");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void checkStatsch() {
        if(!loadDBFach.equals("") && !loadDBKat.equals("") && !loadDBStack.equals("")) {
            cbLoadDBStack.setDisable(true);
            cbLoadDBFach.setDisable(true);
            cbLoadDBKat.setDisable(true);
        }
    }

    public void riiset() {
        main.LoadDB(controller);
    }

}
