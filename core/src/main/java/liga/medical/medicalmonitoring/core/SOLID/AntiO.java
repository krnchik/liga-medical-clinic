package liga.medical.medicalmonitoring.core.SOLID;

public class AntiO {

    public void sendMessage(String typeMessage, String message) {
        switch (typeMessage) {
            case "sms":
                System.out.println("Send sms: " + message);
                break;
            case "email":
                System.out.println("Send email: " + message);
                break;
            case "telegram":
                System.out.println("Send telegram: " + message);
                break;
            case "vk":
                System.out.println("Send vk: " + message);
                break;
            default:
                System.out.println("Wrong type message");
                break;
        }
    }
}
