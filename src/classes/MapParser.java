package classes;


import javafx.scene.control.Alert;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class MapParser {

    private String fileName;

    public MapParser(String fileName){
        this.fileName = fileName;
    }

    public void writeCurrentStack(ArrayList<Card> obowintotuo) throws IOException {
        StringBuilder builder = new StringBuilder();
        for (Card card:obowintotuo) {
            builder.append(card.getKey()).append(";");
            builder.append(card.getVal()).append(";");
            builder.append(card.getFach()).append(";");
            builder.append(card.getKategorie()).append(";");
            if (!card.getImg().equals(""))
                builder.append(card.getImg()).append(";").append("\n");
            else
                builder.append("\n");
        }
        FileUtils.writeStringToFile(new File(this.fileName), builder.toString(), Charset.defaultCharset());
    }

    public ArrayList<Card> loadStackFromFilet() throws IOException {
        ArrayList<Card> tmpList = new ArrayList<>();

        if (!fileName.endsWith(".txt")) {
            showInsaneError();
            return tmpList;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {

                String[] sLine = line.split(";");
                System.out.println(sLine.toString());
                if (sLine.length == 4){
                    tmpList.add(new Card(sLine[0], sLine[1], sLine[2], sLine[3]));
                }
                else if (sLine.length == 5){
                    tmpList.add(new Card(sLine[0], sLine[1], sLine[2], sLine[3], sLine[4]));
                }
            }
        }
        return tmpList;
    }
    private void showInsaneError(){
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle("uwu");
        a.setHeaderText("Type mismatch");
        a.setContentText("This File cannot be read.");
        a.showAndWait();
    }

}
