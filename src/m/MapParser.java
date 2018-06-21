package m;


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
            if (!card.getImg().equals(""))
                builder.append(card.getImg()).append(";").append("\n");
            else
                builder.append("\n");
        }
        FileUtils.writeStringToFile(new File(this.fileName), builder.toString(), Charset.defaultCharset());
    }

    public ArrayList<Card> loadStackFromFilet() throws IOException {
        ArrayList<Card> tmpList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {

                String[] sLine = line.split(";");
                if (sLine.length == 2){
                    tmpList.add(new Card(sLine[0], sLine[1]));
                }
                else if (sLine.length == 3){
                    tmpList.add(new Card(sLine[0], sLine[1], sLine[2]));
                }
            }
        }
        return tmpList;
    }

}
