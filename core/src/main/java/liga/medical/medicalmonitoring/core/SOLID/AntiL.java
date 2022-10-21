package liga.medical.medicalmonitoring.core.SOLID;

public class AntiL extends Bird{
    @Override
    public void fly() {
        System.out.println("I'm Penguin, I can't fly");
        throw new UnsupportedOperationException();
    }
}
