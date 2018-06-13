package m;

public class Card {
    private String Key;
    private String Val;

    public Card(String Key, String Val){
        this.Key = Key;
        this.Val = Val;
    }

    public String getKey(){
        return this.Key;
    }

    public String getVal() {
        return this.Val;
    }
}
