package architecture.patterns.basic.other_source.creational.prototype;

import java.util.Hashtable;

public class ItemRegistry {
    private Hashtable<String, ItemPrototype> map = new Hashtable();
    public ItemRegistry() {
        loadCache();
    }

    public ItemPrototype createBasicItem(String type) {
        return map.get(type).clone();
    }

    private void loadCache() {
        Book book = createBook("Design Patterns", 20.00, 235);
        map.put("Book", book);

        CD cd = createCd("Various", 10.00, 700);
        map.put("CD", cd);
    }

    private CD createCd(String title, double price, int size) {
        CD cd = new CD();
        cd.setTitle(title);
        cd.setPrice(price);
        cd.setSize(size);
        return cd;
    }

    private Book createBook(String title, double price, int pages) {
        Book book = new Book();
        book.setTitle(title);
        book.setPrice(price);
        book.setPages(pages);
        return book;
    }
}