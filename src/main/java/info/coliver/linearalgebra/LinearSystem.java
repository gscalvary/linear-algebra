package info.coliver.linearalgebra;

public class LinearSystem {

    private Matrix lhs;
    private Vector rhs;

    LinearSystem (Matrix lhs, Vector rhs) {

        if (rhs == null || lhs == null) {
            throw new IllegalArgumentException("Neither the right or left hand sides of the system may be null.");
        }

        if (lhs.getComponents().size() != lhs.getComponents().get(0).size()) {
            throw new IllegalArgumentException("The height and width of the right hand side of the system must be equal.");
        }

        if (rhs.getComponents().size() != lhs.getComponents().size()) {
            throw new IllegalArgumentException("The height of both the right and left hand sides of the system must be equal.");
        }

        this.rhs = rhs;
        this.lhs = lhs;
    }

    public Vector getRhs() {
        return rhs;
    }

    public Matrix getLhs() {
        return lhs;
    }
}
