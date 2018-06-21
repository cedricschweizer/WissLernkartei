package classes;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {


    private Stage primaryStage = new Stage();
    private Stage chusStatsch = new Stage();

    @Override
    public void start(Stage primaryStage) throws Exception {
        MeinWindou();
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
            primaryStage.setTitle("");
            primaryStage.show();

            controller.initListener();
            controller.setAPane(pane);

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
            chusStatsch.setTitle("New uwu");
            chusStatsch.show();

        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public Stage getChusStatsch(){
        return chusStatsch;
    }
    public Stage getPrimaryStage(){return primaryStage;}

    public static void main(String[] args) {
        launch(args);
    }
}
