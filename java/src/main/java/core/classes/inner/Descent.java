package core.classes.inner;

/**
 *
 */
class Descent extends Outer {
    public static void static_method() {
        Outer.static_method();
        System.out.println("Static method of outer class");
    }

    @Override
    public int[] method() {

        super.method();
        int[] is = new int[6];
        is[0] = super._public_1;
        is[1] = this._public_1;
        is[2] = super._public_2;
        is[3] = this._public_2;
        is[4] = super._public_3;
        is[5] = this._public_3;
        return is;
    }
}
