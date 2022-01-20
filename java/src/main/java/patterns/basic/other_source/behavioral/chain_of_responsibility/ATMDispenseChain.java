package patterns.basic.other_source.behavioral.chain_of_responsibility;

import java.util.Comparator;
import java.util.List;

public class ATMDispenseChain {

	private List<Dispenser> dispensers;

	public ATMDispenseChain() {

		dispensers = List.of(
			new Dispenser(100),
			new Dispenser(10),
			new Dispenser(5)
		);

		dispensers.stream().reduce((acc, next) -> {
			acc.setNextChain(next);
			acc = next;
			return acc;
		});
	}

	public Currency dispense(final Integer requestedMoney) {
		Currency currency = new Currency(requestedMoney);
		this.dispensers.get(0).dispense(currency);
		return currency;
	}

	public Integer getMinimalNominal() {
		return dispensers
			.stream()
			.min(Comparator.comparing(Dispenser::getNominal))
			.get()
			.getNominal();
	}
}