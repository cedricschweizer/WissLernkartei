package classes;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {

    Database db = new Database();

    private Stage primaryStage = new Stage();
    private Stage chusStatsch = new Stage();
    private Stage loadDbStage = new Stage();
    private Stage createKataStage = new Stage();
    private Stage createFackStage = new Stage();

    @Override
    public void start(Stage primaryStage) throws Exception {
        MeinWindou();
        db.connect();
        db.createTable();
        db.createTableF();
        db.createTableK();
        db.createTableTmp();
        db.createSuperSafetyTabulettteee();
    }

    public void MeinWindou(){
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("../views/view.fxml"));
            AnchorPane pane = loader.load();
            Controller controller = loader.getController();
            controller.setMain(this);

            Scene scene = new Scene(pane);
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.setTitle("WISS Lernkartei");
            primaryStage.show();

            controller.initListener();
            controller.setAPane(pane);

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void createKata(NewCardControllerererer ncontrol, Controller control){
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("../views/createKata.fxml"));
            AnchorPane pane = loader.load();
            KataController controller = loader.getController();
            controller.setMain(this);
            controller.setNcontrol(ncontrol);
            controller.setControl(control);

            Scene scene = new Scene(pane);
            createKataStage.setScene(scene);
            createKataStage.setResizable(false);
            createKataStage.setTitle("");
            createKataStage.show();


        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void createFack(NewCardControllerererer ncontrol, Controller control){
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("../views/createFack.fxml"));
            AnchorPane pane = loader.load();
            FackController controller = loader.getController();
            controller.setMain(this);
            controller.setNcontrol(ncontrol);
            controller.setControl(control);

            Scene scene = new Scene(pane);
            createFackStage.setScene(scene);
            createFackStage.setResizable(false);
            createFackStage.setTitle("");
            createFackStage.show();


        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void LoadDB(Controller controller){
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("../views/LoadDB.fxml"));
            AnchorPane pane = loader.load();
            LoadDBController LDBcontroller = loader.getController();
            LDBcontroller.setMain(this);
            LDBcontroller.setNativeController(controller);

            Scene scene = new Scene(pane);
            loadDbStage.setScene(scene);
            loadDbStage.setResizable(false);
            loadDbStage.setTitle("Specify plis");
            loadDbStage.show();

        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public void ExecWin(){
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("../views/exec.fxml"));
            AnchorPane pane = loader.load();
            ExecController cntrl = loader.getController();
            cntrl.setMain(this);

            Scene scene = new Scene(pane);
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.setTitle("Exec uwu");
            primaryStage.show();

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void SSP(){
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("../views/safetyUWU.fxml"));
            AnchorPane pane = loader.load();
            SafetyUWU cntrl = loader.getController();
            cntrl.setMain(this);

            Scene scene = new Scene(pane);
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.setTitle("entréè süpé bien mot de passe stp!");
            primaryStage.show();

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void ChusWindou(Controller controller){
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("../views/Create.fxml"));
            AnchorPane pane = loader.load();
            NewCardControllerererer newController = loader.getController();
            newController.setMain(this);
            newController.setInitialController(controller);

            Scene scene = new Scene(pane);
            chusStatsch.setScene(scene);
            chusStatsch.setResizable(false);
            chusStatsch.setTitle("Neue Karte erstellen");
            chusStatsch.show();

        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public Stage getChusStatsch(){
        return chusStatsch;
    }
    public Stage getPrimaryStage(){return primaryStage;}
    public Stage getLoadDbStage(){return loadDbStage;}
    public Stage getCreateKataStage(){return createKataStage;}
    public Stage getCreateFackStage(){return createFackStage;}

    public static void main(String[] args) {
        launch(args);
    }
}
