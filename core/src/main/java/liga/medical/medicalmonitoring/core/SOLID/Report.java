package liga.medical.medicalmonitoring.core.SOLID;

public interface Report {
    void generateDoc();
    void generatePDF();
    void generateDocx();
    void generateTxt();
    void generatePng();
    void generateJpeg();
    void generateHTML();
}
