package architecture.patterns.basic.monad.ex2;

import com.google.common.collect.Lists;
import dbms.hibernate.hbm.entity.Address;
import dbms.hibernate.hbm.entity.Person;
import utils.ConcurrencyUtils;

import java.time.LocalDate;
import java.util.Date;
import java.util.concurrent.CompletableFuture;

public final class Demo {
    public static void main(String[] args) {
        Identity<Integer> idString = new Identity<>("abc").map(String::length);

        Person person = new Person().setAddress(new Address().setStreet("Sesam street"));

        new Identity<>(person)
            .map(Person::getAddress)
            .map(Address::getStreet)
            .map(street -> street.substring(0, 3))
            .map(String::toLowerCase)
            .map(String::getBytes);

        ListFunctor<Person> customers = new ListFunctor<>(Lists.newArrayList(person));

        customers
            .map(Person::getAddress)
            .map(Address::getStreet);

        Nullable<String> str = Nullable.of("42");
        Nullable<Nullable<Integer>> num = str.map(Demo::tryParse);

        Nullable<Integer> num3 = num.join();

        Nullable<Date> date1 = Nullable.of(1_000_000_000).map(Date::new);
        Nullable<Date> date2 = num3.map(Date::new);

        ConcurrencyUtils.get(
            loadPerson(42)
                .thenCompose(Demo::getAddress)
                .thenAcceptAsync(Demo::getStreet));


        CustomMonad<Integer> monthMonad = CustomMonad.of(1);
        CustomMonad<Integer> dayOfMonth = CustomMonad.of(1);

        CustomMonad<?> date = monthMonad.flatMap(month -> dayOfMonth.map(day -> LocalDate.of(2016, month, day)));

        System.out.println(date);

    }

    static Nullable<Integer> tryParse(String s) {
        try {
            final int i = Integer.parseInt(s);
            return Nullable.of(i);
        } catch (NumberFormatException e) {
            return Nullable.empty();
        }
    }

    static CompletableFuture<Person> loadPerson(int id) {
        return CompletableFuture.supplyAsync(() -> {
            ConcurrencyUtils.sleep(1);
            return ((Person) new Person().setId(id));
        });
    }

    static CompletableFuture<Address> getAddress(Person person) {
        return CompletableFuture.completedFuture(person.getAddress());
    }

    static CompletableFuture<String> getStreet(Address address) {
        return CompletableFuture.completedFuture(address.getStreet());
    }
}