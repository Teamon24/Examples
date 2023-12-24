package dbms.hibernate.relashonship.one_to_many;

import lombok.Getter;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 */
public interface Abstractions {
}

@Getter
@MappedSuperclass
abstract class ItemAbstract {
    @Id
    @Column(name = "id", updatable = false)
    @Basic(fetch = FetchType.EAGER)
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="item_id_sequence_generator")
    @SequenceGenerator(
        name="item_id_sequence_generator",
        sequenceName="ITEMS_IDS_SEQUENCE",
        allocationSize = 1
    )
    private Integer id;
}

@Getter
@MappedSuperclass
abstract class CartAbstract<T extends ItemAbstract> {

    @Id
    @Column(name = "id", updatable = false)
    @Basic(fetch = FetchType.EAGER)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="cart_id_sequence_generator")
    @SequenceGenerator(
        name="cart_id_sequence_generator",
        sequenceName="CARTS_IDS_SEQUENCE",
        allocationSize = 1
    )
    private Integer id;

    protected abstract Collection<T> getItems();

    public List<Integer> getItemsIds() {
        return this.getItems().stream().map(ItemAbstract::getId).collect(Collectors.toList());
    }

    public void addItems(Collection<T> items) {
        this.getItems().addAll(items);
    }

    @SafeVarargs
    public final CartAbstract<T> addItems(T... items) {
        this.getItems().addAll(Arrays.asList(items));
        return this;
    }
}
