package classes;

public class Card {
    private String Key;
    private String Val;
    private String img = "";
    private String fach = "";
    private String kategorie = "";
    private int stack = 1;
    private boolean isTrap;

    public Card(String Key, String Val, String Fach, String Kategorie, int Stackl){
        this.Key = Key;
        this.Val = Val;
        this.fach = Fach;
        this.kategorie = Kategorie;
        this.stack = Stackl;
    }
    public Card(String Key, String Val, String Fach, String Kategorie, String img, int Stackl){
        this(Key, Val, Fach, Kategorie, Stackl);
        this.img = img;
    }

    public void muuf() {
        if(stack < 5) {
            stack++;
        }
    }

    public void setEis(){
        stack = 1;
    }

    public String getKey(){
        return this.Key;
    }
    public boolean hasDiggttscher(){
        if (img != null)
            if (!img.equals(""))
                return true;
        return false;
    }
    public String getVal() { return this.Val;
    }
    public String getImg(){return this.img;}
    public String getFach(){return this.fach;}
    public String getKategorie(){return this.kategorie;}
    public boolean getTrap(){return this.isTrap;}
    public void setTrap(boolean b){
        this.isTrap = b;
    }
    public int getStack() {return this.stack;}
}