package classes;

public class Card {
    private String Key;
    private String Val;
    private String img = "";
    private String fach = "";
    private String kategorie = "";

    public Card(String Key, String Val, String Fach, String Kategorie){
        this.Key = Key;
        this.Val = Val;
    }
    public Card(String Key, String Val, String img, String Fach, String Kategorie){
        this(Key, Val, Fach, Kategorie);
        this.img = img;
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
}