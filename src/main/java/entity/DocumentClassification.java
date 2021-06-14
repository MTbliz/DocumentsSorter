package entity;

public class DocumentClassification {

    private String section;

    private String subsection;

    private String classification;

    public DocumentClassification(String section, String subsection, String classification) {
        this.section = section;
        this.subsection = subsection;
        this.classification = classification;
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
