public class Vehicle
{
    protected String vihicleTyp;
    protected String label;
    protected String typ;
    protected String constDate;
    protected String color;
    protected String pries;
    public Vehicle(String classe, String label, String typ, String constDate, String color, String preis)
    {
        this.vihicleTyp = classe;
        this.label = label;
        this.typ = typ;
        this.constDate = constDate;
        this.color = color;
        this.pries = preis;
    }
    public String getVihicleTyp() {
        return vihicleTyp;
    }
    public void setVihicleTyp(String vihicleTyp) {
        this.vihicleTyp = vihicleTyp;
    }
    public String getLabel() {
        return label;
    }
    public void setLabel(String label) {
        this.label = label;
    }
    public String getTyp() {
        return typ;
    }
    public void setTyp(String typ) {
        this.typ = typ;
    }
    public String getConstDate() {
        return constDate;
    }
    public void setConstDate(String constDate) {
        this.constDate = constDate;
    }
    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }
    public String getPrice() {
        return pries;
    }
    public void setPries(String pries) {
        this.pries = pries;
    }
}
