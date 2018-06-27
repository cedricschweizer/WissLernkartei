package classes;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;

public class FackController {

    String tmpVS = "";
    String tmpRS = "";
    String tmpFach = "";
    String tmpKat = "";
    String tmpPath = "";

    Database db = new Database();

    private Main main;
    private NewCardControllerererer ncontrol;
    private Controller control;

    @FXML
    TextField txtNewFach;

    public void setMain(Main main){
        this.main = main;
    }
    public void setNcontrol(NewCardControllerererer ncontrol){
        this.ncontrol = ncontrol;
    }
    public void setControl(Controller control){
        this.control = control;
    }

    public void crtNewFach() {
        db.insertF(txtNewFach.getText());
    }

    public void fachBack() throws IOException {
        tmpVS = ncontrol.txtForeground.getText();
        tmpRS = ncontrol.txtBackground.getText();
        tmpPath = ncontrol.txtImgPath.getText();
        db.createTableTmp();
        db.insertTmp(tmpVS, tmpRS, tmpPath);

        main.getCreateFackStage().close();
        main.ChusWindou(control);   //RESTARTING SCHTATSCH

    }

}
