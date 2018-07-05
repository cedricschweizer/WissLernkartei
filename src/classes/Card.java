package classes;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

/**
 * Card class with several constructors and attributes for the card
 * @author Thierry Beer, Cédric Schweizer
 */


public class Card {
    private String Key;
    private String Val;
    private String img = "";
    private String fach = "";
    private String kategorie = "";
    private int stack = 1;
    private int id = -1;
    private boolean isTrap;
    public boolean isLearned;
    public boolean isAnswered;
    Calendar calendar = Calendar.getInstance();
    Date now = calendar.getTime();
    Timestamp time = new java.sql.Timestamp(now.getTime());


    public Card(String Key, String Val, String Fach, String Kategorie, int Stackl, Timestamp time){
        this.Key = Key;
        this.Val = Val;
        this.fach = Fach;
        this.kategorie = Kategorie;
        this.stack = Stackl;
        this.time = time;
    }
    public Card(String Key, String Val, String Fach, String Kategorie, String img, int Stackl, Timestamp time){
        this(Key, Val, Fach, Kategorie, Stackl, time);
        this.img = img;
    }
    public Card(int id, String Key, String Val, String Fach, String Kategorie, int Stackl, Timestamp time){
        this(Key, Val, Fach, Kategorie, Stackl, time);
        this.id = id;
    }
    public Card(int id, String Key, String Val, String Fach, String Kategorie, String img, int Stackl, Timestamp time){
        this(Key, Val, Fach, Kategorie, Stackl, time);
        this.id = id;
        this.img = img;
    }

    public void muuf() {
        if(stack < 5) {
            stack++;
        }
    }
    public boolean hasDiggttscher(){
        if (img != null)
            if (!img.equals(""))
                return true;
        return false;
    }
    public void setEis() {
        stack = 1;
    }
    public String getKey(){
        return this.Key;
    }
    public String getVal() { return this.Val;}
    public String getImg(){return this.img;}
    public String getFach(){return this.fach;}
    public String getKategorie(){return this.kategorie;}
    public boolean getTrap(){return this.isTrap;}
    public boolean isLearned() {return this.isLearned;}
    public boolean isAnswered() {return this.isAnswered;}
    public void setTrap(boolean b){
        this.isTrap = b;
    }
    public int getStack() {return this.stack;}
    public Timestamp getTime() {
        return new Timestamp(now.getTime());
    }
    public int getId(){
        return this.id;
    }
}