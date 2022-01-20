package dbms.hibernate.entity.mapping.enumartion.user_type;

public enum Role implements LabeledEnum {
    ADMIN("admin"), USER("user"), ANONYMOUS("anon");

    private final String label;

    Role(String label) {
        this.label = label;
    }

    @Override
    public String getLabel() {
        return label;
    }
}
