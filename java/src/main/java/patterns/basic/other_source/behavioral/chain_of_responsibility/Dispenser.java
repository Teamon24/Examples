package patterns.basic.other_source.behavioral.chain_of_responsibility;

public class Dispenser {

	private Dispenser chain;
	private Integer nominal;

	public Dispenser(final Integer nominal) {
		this.nominal = nominal;
	}

	public Dispenser setNextChain(Dispenser nextChain) {
		this.chain = nextChain;
		return nextChain;
	}

	public void dispense(Currency currency) {
		DispenseUtils.dispense(chain, currency, getNominal());
	}

	public Integer getNominal() {
		return this.nominal;
	};
}