package classes;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;

public class KataController {

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
    TextField txtNewKat;

    public void setNcontrol(NewCardControllerererer ncontrol){
        this.ncontrol = ncontrol;
    }
    public void setControl(Controller control){
        this.control = control;
    }

    public void setMain(Main main){
        this.main = main;
    }


    public void crtNewKat() {
        db.insertK(txtNewKat.getText());
    }

    public void katBack() throws IOException {
        tmpVS = ncontrol.txtForeground.getText();
        tmpRS = ncontrol.txtBackground.getText();
        tmpPath = ncontrol.txtImgPath.getText();
        db.createTableTmp();
        db.insertTmp(tmpVS, tmpRS, tmpPath);

        main.getCreateKataStage().close();
        main.ChusWindou(control);
    }

}
