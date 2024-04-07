package helpers;

public record AttributeData(
        String name,
        String type,
        String modifier,
        String value,
        String defaultValue
) {
    public AttributeData(String name, String type, String modifier, String value) {
        this(name, type, modifier, value, "");
    }

    public AttributeData(String name, String type, String modifier) {
        this(name, type, modifier, "", "");
    }

    public AttributeData(String name, String type) {
        this(name, type, "", "", "");
    }

    public AttributeData(String name) {
        this(name, "", "", "", "");
    }


}
