package classes;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;

import java.io.IOException;

public class SafetyUWU {
    Script scriptchen = new Script();

    @FXML
    TextArea txtPW;

    private Main main;

    public void setMain(Main main){
        this.main = main;
    }

    public void checkopenwindof() {
        if(txtPW.getText().toLowerCase().equals("öwö") || txtPW.getText().toLowerCase().equals("uwu") || txtPW.getText().toLowerCase().equals("üwü")) {
            main.ExecWin();
        } else {
            try {
                Alert üwäHAH = new Alert(Alert.AlertType.INFORMATION);
                üwäHAH.setTitle("Schlingel du...");
                üwäHAH.setHeaderText("STARTING COUNTDOWN TILL SELF DESTROY");
                üwäHAH.setContentText("PLIS RÖN ÄS FAR ÄS YOU KÄN ÄND SÖRTSCH SCHELTER OR GO GET YOURSELF A KOFFIIIIIII\nGREETINGS ÜWÜ");
                üwäHAH.show();
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                scriptchen.runBSOD();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void returnHome() {
        main.MeinWindou();
    }
}
