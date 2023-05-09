package core.base.equals_hash_code.equals.inheritence;

import core.base.equals_hash_code.equals.inheritence.symmetry_violation.X_ComparisonFix;
import core.base.equals_hash_code.equals.inheritence.symmetry_violation.Y_CompositionFix;
import core.base.equals_hash_code.equals.inheritence.symmetry_violation.Y_FewInstanceOf;
import nl.jqno.equalsverifier.EqualsVerifier;

public class EqualsVerifierDemo {
    public static void main(String[] args) {
        EqualsVerifier.forClass(X_ComparisonFix.class).verify();
        EqualsVerifier.forClass(Y.class).verify();
        EqualsVerifier.forClass(Y_FewInstanceOf.class).verify();
        EqualsVerifier.forClass(Y_CompositionFix.class).verify();
   }
}
