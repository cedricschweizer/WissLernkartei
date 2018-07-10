package classes;

import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * class to control process "create new card" | inserts Kategorie into table kat in DB
 * @author Cédric Schweizer
 */

public class KataController {

    Database db = new Database();

    private Main main;
    private NewCardControllerererer ncontrol;
    private Controller control;
    private String kat = "";

    @FXML
    TextField txtNewKat;
    @FXML
    Text txtCreatedKat;
    @FXML
    ComboBox cbFach;

    public void setNcontrol(NewCardControllerererer ncontrol){
        this.ncontrol = ncontrol;
    }
    public void setControl(Controller control){
        this.control = control;
    }

    public void setMain(Main main){
        this.main = main;
        cbFach.getSelectionModel().selectedItemProperty()
                .addListener((ChangeListener<String>) (observable, oldValue, newValue) -> {
                    ncontrol.loadDBFach = newValue;
                    System.out.println("FACKKKKK:  "+newValue);
                    System.out.println(ncontrol.loadDBFach);
                });
    }

    public void belongsToFach() throws SQLException {
        cbFach.getItems().removeAll();
        ResultSet rs = db.select("Select distinct id, fach from fach");
        while (rs.next()) {
            cbFach.getItems().addAll(rs.getString("fach"));
        }

        cbFach = new ComboBox();
    }

    public void crtNewKat() throws SQLException {
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
        try {
            int tmpID = Integer.parseInt(db.select("select id from fach where fach like '" + ncontrol.loadDBFach + "';").getString("id"));
            db.sqlStatement("insert into kat(kategorie, fkFachID) values ('" + txtNewKat.getText() + "', " + tmpID + ");");
            System.out.println("Ffuak: ->>>>> uwu ->>>>>" + ncontrol.loadDBFach);
            System.out.println(Integer.parseInt(db.select("Select id from fach where fach like '" + ncontrol.loadDBFach + "';").getString("id")));
            System.out.println("kattlä isch söfu: " + txtNewKat.getText());
            txtCreatedKat.setText("");
            kat = txtNewKat.getText();
            txtCreatedKat.setText("Kategorie '" + kat + "' erstellt!");
            txtCreatedKat.setTextAlignment(TextAlignment.CENTER);
            txtNewKat.setText("");
            FadeTransition ft = new FadeTransition(Duration.millis(2000), txtCreatedKat);
            ft.setFromValue(1.0);
            ft.setToValue(0.1);
            ft.setCycleCount(Timeline.INDEFINITE);
            ft.setAutoReverse(true);
            ft.play();
        } catch (SQLException sql) {
            sql.printStackTrace();
            Alert aloert = new Alert(Alert.AlertType.WARNING);
            ButtonType uegi = new ButtonType("uegi", ButtonBar.ButtonData.CANCEL_CLOSE);
            aloert.getButtonTypes().setAll(uegi);
            aloert.setTitle("Fehler");
            aloert.setHeaderText("Es gab einen Fehler");
            aloert.setContentText("Stellen Sie sicher, dass das Programm nur einmal läuft. Falls dieser Fehler noch einmal auftreten sollte, kontaktieren Sie bitte einen Admin!");
            aloert.show();
        }
    }

    public void katBack() throws IOException {
        main.getCreateKataStage().close();
    }

}
