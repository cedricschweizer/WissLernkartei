package classes;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

/**
 * class that controls the process of creating a new card
 * @author Cédric Schweizer
 */

public class NewCardControllerererer {

    Database db = new Database();

    private FileChooser fileChooser = new FileChooser();
    private Main main;
    private File filet;
    private Controller controller;
    private String selectedFach;
    private String selectedKat;
    Calendar calendar = Calendar.getInstance();
    Date now = calendar.getTime();
    Timestamp time = new java.sql.Timestamp(now.getTime());

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
        fileChooser.setTitle("Bitte wählen Sie ein Bild");
        filet = fileChooser.showOpenDialog(main.getChusStatsch());
        if (!(filet.getPath().endsWith(".png")|| filet.getPath().endsWith(".jpg")) || filet.getPath().endsWith(".jpeg") || filet.getPath().endsWith(".svg") || !filet.getPath().contains(".")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            ButtonType OK = new ButtonType("OK", ButtonBar.ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(OK);
            alert.setTitle("Falscher Dateityp");
            alert.setHeaderText("Achtung! Falscher Dateityp");
            alert.setContentText("Es können nur Bilder mit den Dateitypen \n.png, .jpg (.jpeg) und .svg \ngeladen werden!");
            alert.show();
            return;
        }
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
        System.out.println("<3<3<3 Nullpointer: "+origin);      //sum interesting debugging
        return true;
    }

    private boolean isObjectEmpty(Object obj, String origin) {
        if(obj != null) {
            return isTextEmpty(obj.toString(), origin);
        }
        System.out.println("<3<3<3 Nullpointer(Object): "+origin);      //same
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
                controller.addNewCard(txtForeground.getText(), txtBackground.getText(), selectedFach, selectedKat, txtImgPath.getText(), 1, time);
                clearAllllll();
                controller.showCard();
                return;
            }

            if (isTextEmpty(txtForeground.getText(), "txtForegroundNULL") || isTextEmpty(txtBackground.getText(), "txtBackgroundNULL")
                    || isObjectEmpty(selectedFach, "cbCreateFachNULL") || isObjectEmpty(selectedKat, "cbCreateKatNULL")) {
                showInsaneWarning();
                return;
            }
            controller.addNewCard(txtForeground.getText(), txtBackground.getText(), selectedFach, selectedKat,1,time);
            clearAllllll();
            controller.showCard();
        } catch (IndexOutOfBoundsException e) {

            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void newFach() { main.createFack(this, controller); }

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
