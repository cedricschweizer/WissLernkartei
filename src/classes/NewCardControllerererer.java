package classes;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.io.File;

public class NewCardControllerererer {

    private FileChooser fileChooser = new FileChooser();
    private Main main;
    File filet;
    Controller controller;

    public void setMain(Main main){
        this.main = main;
    }

    @FXML
    TextField txtForeground;
    @FXML
    TextField txtBackground;
    @FXML
    TextField txtImgPath;

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
        if (filet != null){
            if (txtForeground.getText().equalsIgnoreCase("") || txtBackground.getText().equalsIgnoreCase(""))
            {
               showInsaneWarning();
               return;
            }
            controller.addNewCard(txtForeground.getText(), txtBackground.getText(), filet.getPath());
            clearAllllll();
            controller.showCard();
            return;
        }

       if (txtForeground.getText().equalsIgnoreCase("") || txtBackground.getText().equalsIgnoreCase(""))
        {
            showInsaneWarning();
            return;
        }
        controller.addNewCard(txtForeground.getText(), txtBackground.getText());
        clearAllllll();
        controller.showCard();
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
