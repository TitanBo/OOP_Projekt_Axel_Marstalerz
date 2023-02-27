public class Boot extends Vehicle
{
    public Boot(String classe, String label, String typ, String constDate, String color, String pries, String label1, String typ1, String constDate1, String color1, String pries1)
    {
        super(classe, label, constDate, typ, color, pries);
        this.label = label1;
        this.typ = typ1;
        this.constDate = constDate1;
        this.color = color1;
        this.pries = pries1;
    }
}
