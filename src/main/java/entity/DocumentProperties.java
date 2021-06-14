package entity;

public class DocumentProperties {

    private String documentName;

    private String documentTitle;

    private String documentNumber;

    private String documentDate;

    private String section;

    private String subsection;

    private String classification;

    public DocumentProperties(String documentName, String documentTitle, String documentNumber, String documentDate, String section, String subsection, String classification) {
        this.documentTitle = documentTitle;
        this.documentName = documentName;
        this.documentNumber = documentNumber;
        this.documentDate = documentDate;
        this.section = section;
        this.subsection = subsection;
        this.classification = classification;
    }

    public String getDocumentTitle() {
        return documentTitle;
    }

    public String getDocumentName() {
        return documentName;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public String getDocumentDate() {
        return documentDate;
    }

    public String getSection() {
        return section;
    }

    public String getSubsection() {
        return subsection;
    }

    public String getClassification() {
        return classification;
    }
}
