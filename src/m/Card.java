package m;

public class Card {
    private String Key;
    private String Val;
    private String img = "";

    public Card(String Key, String Val){
        this.Key = Key;
        this.Val = Val;
    }
    public Card(String Key, String Val, String img){
        this(Key, Val);
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
}