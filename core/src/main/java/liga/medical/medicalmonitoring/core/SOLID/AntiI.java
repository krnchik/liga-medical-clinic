package liga.medical.medicalmonitoring.core.SOLID;

import java.io.File;
import java.io.IOException;

public class AntiI implements Report{

    @Override
    public void generateDoc() {
        File file = new File("report.doc");
        try {
            if (file.createNewFile())
                System.out.println("Report create");
            else
                System.out.println("Report already exists");
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    @Override
    public void generatePDF() {
        File file = new File("report.pdf");
        try {
            if (file.createNewFile())
                System.out.println("Report create");
            else
                System.out.println("Report already exists");
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    @Override
    public void generateDocx() {
    }

    @Override
    public void generateTxt() {
    }

    @Override
    public void generatePng() {
    }

    @Override
    public void generateJpeg() {
    }

    @Override
    public void generateHTML() {
    }
}
