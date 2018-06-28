package classes;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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
    private String selectedFach;
    private String selectedKat;

    public void setMain(Main main) throws IOException {
        this.main = main;
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
    @FXML
    Button btnDruePuenkt;


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

    private boolean isTextEmpty(String txt, String origin) {
        if(txt != null) {
            if (txt.equals(""))return true;
            return false;
        }
        System.out.println("<3<3<3 Nullpointer: "+origin);
        return true;
    }

    private boolean isObjectEmpty(Object obj, String origin) {
        if(obj != null) {
            return isTextEmpty(obj.toString(), origin);
        }
        System.out.println("<3<3<3 Nullpointer(Object): "+origin);
        return true;
    }

    public void createNew(){
        try {
            if (filet != null) {
                if (isTextEmpty(txtForeground.getText(), "txtForeground") || isTextEmpty(txtBackground.getText(), "txtBackground")
                        || isObjectEmpty(selectedFach, "cbCreateFach") || isObjectEmpty(selectedKat, "cbCreateKat")) {
                    showInsaneWarning();
                    return;
                }
                controller.addNewCard(txtForeground.getText(), txtBackground.getText(), selectedFach, selectedKat, txtImgPath.getText());
                clearAllllll();
                controller.showCard();
                return;
            }

            if (isTextEmpty(txtForeground.getText(), "txtForegroundNULL") || isTextEmpty(txtBackground.getText(), "txtBackgroundNULL")
                    || isObjectEmpty(selectedFach, "cbCreateFachNULL") || isObjectEmpty(selectedKat, "cbCreateKatNULL")) {
                showInsaneWarning();
                return;
            }
            controller.addNewCard(txtForeground.getText(), txtBackground.getText(), selectedFach, selectedKat);
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
        cbCreateFach.getItems().clear();
        System.out.println("fachDropped");
        ResultSet rsFach = db.select("Select distinct fach from fach;");
        try {
            while (rsFach.next()){
                cbCreateFach.getItems().addAll(rsFach.getString("fach"));
                System.out.print("Added new Fach: ");
                System.out.print(rsFach.getString("fach"));
                if (selectedFach != null) {
                    cbCreateFach.setValue(selectedFach);
                }
                cbCreateFach.getSelectionModel().selectedItemProperty()
                        .addListener(new ChangeListener<String>() {
                            public void changed(ObservableValue<? extends String> observable,
                                                String oldValue, String newValue) {
                                selectedFach = newValue;
                                checkStatsch();
                            }
                        });
            }
        //cbCreateFach = new ComboBox();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void katDropped() {
        cbCreateKat.getItems().clear();
        System.out.println("katDropped");
        ResultSet rsKat = db.select("Select distinct kategorie from kat;");
        try {
            while (rsKat.next()){
                cbCreateKat.getItems().addAll(rsKat.getString("kategorie"));
                System.out.println("Added new Kategorie");
                System.out.println(rsKat.getString("kategorie"));
                if (selectedKat != null) {
                    cbCreateKat.setValue(selectedKat);
                }
                cbCreateKat.getSelectionModel().selectedItemProperty()
                        .addListener(new ChangeListener<String>() {
                            public void changed(ObservableValue<? extends String> observable,
                                                String oldValue, String newValue) {
                                selectedKat = newValue;
                                checkStatsch();
                            }
                        });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void checkStatsch() {
        if (selectedFach!=null && selectedKat!= null) {
            if(!selectedFach.equals("") && !selectedKat.equals("")) {
                txtForeground.setDisable(false);
                txtBackground.setDisable(false);
                txtImgPath.setDisable(false);
                btnDruePuenkt.setDisable(false);
            }
        }
    }

    public void finish(){
        main.getChusStatsch().close();
    }
    private void showInsaneWarning(){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Warnung");
            alert.setHeaderText("Sie haben etwas vergessen!");
            alert.setContentText("Bitte füllen Sie alle benötigten Elemente aus (Bildpath nicht nötig)!");
            alert.showAndWait();
    }
}
