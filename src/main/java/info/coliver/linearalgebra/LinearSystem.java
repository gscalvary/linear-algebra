package info.coliver.linearalgebra;

import java.util.List;
import java.util.Optional;

public class LinearSystem {

    private Matrix lhs;
    private Vector rhs;
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
    }

    public Vector getRhs() {
        return rhs;
    }

    public Matrix getLhs() {
        return lhs;
    }

    public int getSize() {
        return size;
    }

    /**
     * Compute the Gaussian transform of the given linear system.
     * @param linearSystem
     * A linear system to be transformed
     * @return
     * The equivalent upper triangular system.
     */
    public static Optional<LinearSystem> gaussianTransform(LinearSystem linearSystem) {

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

            for (int j = i + 1; j < linearSystem.getSize(); j++) {
                Double dividend = matrixColumn.get(j);
                if (divisor == 0.0) {
                    newTransformColumn.set(j, 0.0);
                } else {
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

        return Optional.of(new LinearSystem(transformedLhs, transformedRhs));
    }
}
