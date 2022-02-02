package patterns.basic.other_source.creational.prototype;

public abstract class ItemPrototype implements Cloneable {
    private String title;
    private double price;

    public ItemPrototype clone() {
        ItemPrototype clonedItemPrototype = null;
        try {
            clonedItemPrototype = (ItemPrototype) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return clonedItemPrototype;
    }

    public String getTitle() {
        return title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}