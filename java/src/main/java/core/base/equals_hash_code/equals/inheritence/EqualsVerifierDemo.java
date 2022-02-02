package core.base.equals_hash_code.equals.inheritence;

import core.base.equals_hash_code.equals.inheritence.symmetry_violation.A_ClassComparisonFix;
import core.base.equals_hash_code.equals.inheritence.symmetry_violation.B_CompositionFix;
import core.base.equals_hash_code.equals.inheritence.symmetry_violation.B_FewInstanceOf;
import nl.jqno.equalsverifier.EqualsVerifier;

public class EqualsVerifierDemo {
    public static void main(String[] args) {
        EqualsVerifier.forClass(A_ClassComparisonFix.class).verify();
        EqualsVerifier.forClass(B.class).verify();
        EqualsVerifier.forClass(B_FewInstanceOf.class).verify();
        EqualsVerifier.forClass(B_CompositionFix.class).verify();
   }
}
