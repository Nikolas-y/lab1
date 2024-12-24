package lab1.var4;

public class Cheese extends Food {

    public Cheese() {
        super("Сыр");
    }
    public void consume() {
        System.out.println(this + " съедедн, " + this.calculateCalories() + " калорий");
    }
    public int calculateCalories() {
        return 200;
    }
}
