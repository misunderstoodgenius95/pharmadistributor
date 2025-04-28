package pharma.javafxlib;



public class RadioOptions {
    private String label;
    private String id;

    public RadioOptions(String id, String label) {
        this.id = id;
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public String getId() {
        return id;
    }
}
