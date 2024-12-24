package lab1.var4;

public class Sandwich extends Food{
    private String filling1 = null;
    private String filling2 = null;
    public Sandwich(String filling1, String filling2) {
        super("sandwich");
        this.filling1 = filling1;
        this.filling2 = filling2;
    }
    @Override
    public void consume() {
        System.out.println(this + " Съеден, " + this.calculateCalories() + " калорий");
    }

    public String getfilling1() {
        return filling1;
    }
    public String getfilling2() {
        return filling2;
    }
    public void setfilling1(String filling1) {
        this.filling1 = filling1;
    }
    public void setfilling2(String filling2) {
        this.filling2 = filling2;
    }


    public boolean equals(Object arg0) {
        if (super.equals(arg0)) {
            if (!(arg0 instanceof Sandwich)) return false;
            return  filling1.equalsIgnoreCase(((Sandwich)arg0).getfilling1())
                    && filling2.equalsIgnoreCase(((Sandwich)arg0).getfilling2());
        } else
            return false;
    }

    public int calculateCalories() {
        return (filling1.length() + filling2.length() + 20) * 5;
    }

    public String toString() {
        return super.toString() + " 1 - '" + filling1.toUpperCase() + "' 2 - '" + filling2.toUpperCase() + "'";
    }
}
