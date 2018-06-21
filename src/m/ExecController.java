package m;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.*;
import java.util.Arrays;

public class ExecController {

    @FXML
    TextArea txtOut;
    @FXML
    TextField txtArg;

    Process process;
    Alert alert;

    private Main main;
    public void setMain(Main main){
        this.main = main;
    }
    public void run() throws IOException {
        if (!txtArg.getText().equals("")) {
            process = new ProcessBuilder(new File(".").getCanonicalPath()+"/src/m/hug/hack.exe", txtArg.getText()).start();
        }
        else {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Failure");
            alert.setHeaderText("You missed something");
            alert.setContentText("You have to specify a valid argument");
            alert.show();
        }
    }
    public void returnHome(){
        main.MeinWindou();
    }
}
