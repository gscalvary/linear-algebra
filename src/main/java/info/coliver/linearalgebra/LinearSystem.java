package info.coliver.linearalgebra;

import java.util.List;
import java.util.Optional;

public class LinearSystem {

    private Matrix lhs;
    private Vector rhs;
    private Vector pivot;
    private int size;

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
        this.size = lhs.getComponents().size();

        Optional<Vector> pivot = Vector.createZeroVector(this.size);
        pivot.ifPresent(vector -> this.pivot = vector);
    }

    LinearSystem (Matrix lhs, Vector rhs, Vector pivot) {

        if (rhs == null || lhs == null) {
            throw new IllegalArgumentException("Neither the right or left hand sides of the system may be null.");
        }

        if (lhs.getComponents().size() != lhs.getComponents().get(0).size()) {
            throw new IllegalArgumentException("The height and width of the right hand side of the system must be equal.");
        }

        if (rhs.getComponents().size() != lhs.getComponents().size()) {
            throw new IllegalArgumentException("The height of both the right and left hand sides of the system must be equal.");
        }

        if (rhs.getComponents().size() != pivot.getComponents().size()) {
            throw new IllegalArgumentException("The size of the pivot must equal the size of the system.");
        }

        this.rhs = rhs;
        this.lhs = lhs;
        this.pivot = pivot;
        this.size = lhs.getComponents().size();
    }

    public Vector getRhs() {
        return rhs;
    }

    public Matrix getLhs() {
        return lhs;
    }

    public Vector getPivot() {
        return pivot;
    }

    public void setPivot(Vector pivot) {

        if (this.pivot.getComponents().size() != pivot.getComponents().size()) {
            throw new IllegalArgumentException("The size of the pivot must equal the size of the system.");
        }

        this.pivot = pivot;
    }

    public int getSize() {
        return size;
    }

    /**
     * Compute the Gauss-Jordan transform of the given linear system.
     * floating point operations: 2n^3
     * @param linearSystem
     * A linear system to be transformed
     * @return
     * The linear system solution.
     */
    public static Optional<LinearSystem> gaussJordanTransform(LinearSystem linearSystem) {

        if (linearSystem == null) {
            return Optional.empty();
        }

        Matrix transformedLhs = linearSystem.getLhs();
        Vector transformedRhs = linearSystem.getRhs();

        for (int i = 0; i < linearSystem.getSize(); i++ ) {

            Optional<Matrix> transform = Matrix.identity(transformedLhs);

            if (!transform.isPresent()) {
                return Optional.empty();
            }

            List<Double> newTransformColumn = transform.get().getComponents().get(i);
            List<Double> matrixColumn = transformedLhs.getComponents().get(i);

            Double divisor = matrixColumn.get(i);

            if (divisor == 0.0) {
                Optional<LinearSystem> systemPivoted = pivot(new LinearSystem(transformedLhs, transformedRhs), i);
                if (systemPivoted.isPresent()) {
                    transformedLhs = systemPivoted.get().getLhs();
                    transformedRhs = systemPivoted.get().getRhs();
                    linearSystem.setPivot(systemPivoted.get().getPivot());
                    matrixColumn = transformedLhs.getComponents().get(i);
                    divisor = matrixColumn.get(i);
                } else {
                    return Optional.empty();
                }
            } else {
                Vector newPivot = linearSystem.getPivot();
                newPivot.getComponents().set(i, 0.0);
                linearSystem.setPivot(newPivot);
            }

            for (int j = 0; j < linearSystem.getSize(); j++) {
                Double dividend = 1.0;
                if (j == i) {
                    newTransformColumn.set(j, dividend / divisor);
                } else {
                    dividend = matrixColumn.get(j);
                    newTransformColumn.set(j, dividend / divisor * -1);
                }
            }

            transform.get().getComponents().set(i, newTransformColumn);

            Optional<Matrix> stepIMatrix = Matrix.matrixMultiplication(transform.get(), transformedLhs);

            if (!stepIMatrix.isPresent()) {
                return Optional.empty();
            }

            transformedLhs = stepIMatrix.get();

            Optional<Matrix> transposedTransform = Matrix.transpose(transform.get());

            if (!transposedTransform.isPresent()) {
                return Optional.empty();
            }

            Optional<Vector> stepIVector = Vector.matrixMultiplication(transformedRhs, transposedTransform.get());

            if (!stepIVector.isPresent()) {
                return Optional.empty();
            }

            transformedRhs = stepIVector.get();
        }

        return Optional.of(new LinearSystem(transformedLhs, transformedRhs, linearSystem.getPivot()));
    }

    private static Optional<LinearSystem> pivot(LinearSystem linearSystem, int i) {

        if (linearSystem == null) {
            return Optional.empty();
        }

        List<Double> ithColumn = linearSystem.getLhs().getComponents().get(i);

        for (int j = i + 1; j < ithColumn.size(); j++) {
            if (ithColumn.get(j) != 0) {

                // calculate a permutation matrix
                Optional<Matrix> permutation = Matrix.identity(linearSystem.getLhs());
                if (!permutation.isPresent()) {
                    return Optional.empty();
                }

                Matrix permutationMatrix = permutation.get();
                permutation = Matrix.swapRows(permutationMatrix, j, i);
                if (!permutation.isPresent()) {
                    return Optional.empty();
                }

                // multiply the transformedLhs and the transformedRhs by the permutation matrix
                Optional<Matrix> newLhs = Matrix.matrixMultiplication(permutation.get(), linearSystem.getLhs());
                Optional<Vector> newRhs = Vector.matrixMultiplication(linearSystem.getRhs(), permutation.get());
                if (!newLhs.isPresent() || !newRhs.isPresent()) {
                    return Optional.empty();
                }

                // update the pivot vector
                List<Double> pivot = linearSystem.getPivot().getComponents();
                pivot.set(i, (double)(j - i));
                Vector newPivot = new Vector(pivot);

                return Optional.of(new LinearSystem(newLhs.get(), newRhs.get(), newPivot));
            }
        }

        return Optional.of(linearSystem);
    }
}
