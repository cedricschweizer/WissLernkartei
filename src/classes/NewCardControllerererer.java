package classes;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class NewCardControllerererer {

    Database db = new Database();

    private FileChooser fileChooser = new FileChooser();
    private Main main;
    File filet;
    Controller controller;

    public void setMain(Main main) throws IOException {
        this.main = main;

        try {
            ResultSet rs = db.select("select tmpVS, tmpRS, tmpPath from tmp;");

            txtForeground.setText(rs.getString("tmpVS"));
            txtBackground.setText(rs.getString("tmpRS"));
            txtImgPath.setText(rs.getString("tmpPath"));
        } catch(Exception e) {
            e.printStackTrace();
        }

        db.deleteTableTmp();
    }

    @FXML
    TextField txtForeground;
    @FXML
    TextField txtBackground;
    @FXML
    TextField txtImgPath;
    @FXML
    ComboBox cbCreateFach;
    @FXML
    ComboBox cbCreateKat;


    public void setInitialController(Controller controller){
        this.controller = controller;
    }
    public void chooseFile(){
        fileChooser.setTitle("Plis chus filet uwu");
        filet = fileChooser.showOpenDialog(main.getChusStatsch());
        txtImgPath.setText(filet.getPath());
    }
    private void clearAllllll(){
        txtImgPath.setText("");
        txtForeground.setText("");
        txtBackground.setText("");
        filet = null;
    }
    public void createNew(){
        try {
            if (filet != null) {
                if (txtForeground.getText().equalsIgnoreCase("") || txtBackground.getText().equalsIgnoreCase("")
                        || cbCreateFach.getValue().equals("") || cbCreateKat.getValue().equals("")) {
                    showInsaneWarning();
                    return;
                }
                controller.addNewCard(txtForeground.getText(), txtBackground.getText(), cbCreateFach.getValue().toString(), cbCreateKat.getValue().toString(), txtImgPath.getText());
                clearAllllll();
                controller.showCard();
                return;
            }

            if (txtForeground.getText().equalsIgnoreCase("") || txtBackground.getText().equalsIgnoreCase("")
                    || cbCreateFach.getValue().toString().equals("") || cbCreateKat.getValue().toString().equals("")) {
                showInsaneWarning();
                return;
            }
            controller.addNewCard(txtForeground.getText(), txtBackground.getText(), cbCreateFach.getValue().toString().toLowerCase(), cbCreateKat.getValue().toString().toLowerCase());
            clearAllllll();
            controller.showCard();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void newFach() {
        main.createFack(this, controller);
    }

    public void newKat() {
        main.createKata(this, controller);
    }

    public void fachDropped() {
        System.out.println("fachDropped");
        ResultSet rsFach = db.select("Select distinct fach from fach;");
        try {
            while (rsFach.next()){
                cbCreateFach.getItems().addAll(rsFach.getString("fach"));
                System.out.println("Added new Fach");
                System.out.println(rsFach.getString("fach"));
            }
        cbCreateFach = new ComboBox();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void katDropped() {
        System.out.println("katDropped");
        ResultSet rsKat = db.select("Select distinct kategorie from kat;");
        try {
            while (rsKat.next()){
                cbCreateKat.getItems().addAll(rsKat.getString("kategorie"));
                System.out.println("Added new Kategorie");
                System.out.println(rsKat.getString("kategorie"));
            }
            cbCreateKat = new ComboBox();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void finish(){
        main.getChusStatsch().close();
    }
    private void showInsaneWarning(){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("NOPE");
            alert.setHeaderText("You missed something :c");
            alert.setContentText("In order to erfassen a new Karte, you have to füllen les boxes des textes");
            alert.showAndWait();
    }
}
