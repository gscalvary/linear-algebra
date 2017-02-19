package info.coliver.linearalgebra;

import java.util.*;

public class Vector {

    private List<Double> components;

    Vector (List<Double> components) {
        this.components = components;
    }

    public List<Double> getComponents() {
        return components;
    }

    public void setComponents(List<Double> components) {
        this.components = components;
    }

    /**
     * floating point operations: 0
     * memory operations: 1
     * @param vector
     * vector who's components should be copied to this vector
     */
    public void copy(Vector vector) {
        this.components = vector.getComponents();
    }

    /**
     * floating point operations: 0
     * memory operations: n
     * @param vector
     * vector against this vector should be compared
     * @return
     * boolean set to true if the passed vector is equal to this vector
     */
    public boolean equals(Vector vector) {

        List<Double> otherComponents = vector.getComponents();
        int oSize = otherComponents.size();

        if (oSize != this.components.size()) {
            return false;
        }

        for (int i =0; i < oSize; i++) {
            if (!otherComponents.get(i).equals(this.components.get(i))) {
                return false;
            }
        }

        return true;
    }

    /**
     * floating point operations: (x - 1) * n
     * memory operations: (x - 1) * n
     * @param terms
     * list of vectors to be added
     * @return
     * an empty optional or an optional containing the new vector resulting from the addition
     */
    public static Optional<Vector> add(List<Vector> terms) {

        if (terms == null || terms.isEmpty()) {
            return Optional.empty();
        }

        if (terms.size() == 1) {
            return Optional.of(new Vector(terms.get(0).getComponents()));
        }

        int size = terms.get(0).getComponents().size();

        List<Double> sums = new ArrayList<>(size);

        for (Vector term : terms) {
            if (term == null || term.getComponents().size() != size) {
                return Optional.empty();
            }
            for (int j = 0; j < term.getComponents().size(); j++) {
                if (sums.size() <= j) {
                    sums.add(term.getComponents().get(j));
                } else {
                    double componentValue = sums.get(j);
                    componentValue += term.getComponents().get(j);
                    sums.set(j, componentValue);
                }
            }
        }

        return Optional.of(new Vector(sums));
    }

    /**
     * floating point operations: (x - 1) * n
     * memory operations: (x - 1) * n
     * @param terms
     * list of vectors to be added
     * @return
     * an empty optional or an optional containing the new vector resulting from the subtraction
     */
    public static Optional<Vector> subtract(List<Vector> terms) {

        if (terms == null || terms.isEmpty()) {
            return Optional.empty();
        }

        if (terms.size() == 1) {
            return Optional.of(new Vector(terms.get(0).getComponents()));
        }

        int size = terms.get(0).getComponents().size();

        List<Double> differences = new ArrayList<>(size);

        for (Vector term : terms) {
            if (term == null || term.getComponents().size() != size) {
                return Optional.empty();
            }
            for (int j = 0; j < term.getComponents().size(); j++) {
                if (differences.size() <= j) {
                    differences.add(term.getComponents().get(j));
                } else {
                    double componentValue = differences.get(j);
                    componentValue -= term.getComponents().get(j);
                    differences.set(j, componentValue);
                }
            }
        }

        return Optional.of(new Vector(differences));
    }

    /**
     * floating point operations: n
     * memory operations: 2n
     * @param vector
     * the vector to be scaled
     * @param factor
     * the scale factor
     * @return
     * an empty optional or an optional containing the new vector resulting from the scaling
     */
    public static Optional<Vector> scale(Vector vector, double factor) {

        if (vector == null) {
            return Optional.empty();
        }

        List<Double> products = new ArrayList<>();

        vector.getComponents().forEach((component) -> products.add(factor * component));

        return Optional.of(new Vector(products));
    }

    /**
     * floating point operations: 2n
     * memory operations: 3n
     * @param a
     * the scaling factor
     * @param x
     * the vector to be scaled
     * @param y
     * the vector to be added to the result of the scaling operation
     * @return
     * an empty optional or an optional containing the new vector resulting from the axpy operation
     */
    public static Optional<Vector> axpy(double a, Vector x, Vector y) {

        Optional<Vector> ax = Vector.scale(x, a);

        if (!ax.isPresent()) {
            return ax;
        }

        List<Vector> terms = new ArrayList<>();
        terms.add(ax.get());
        terms.add(y);

        return Vector.add(terms);
    }

    /**
     * floating point operations: 2n^2
     * memory operations: 3n^2
     * @param coefficients
     * a list of scaling factors
     * @param vectors
     * a list of vectors to be combined
     * @return
     * an empty optional or an optional containing the new vector resulting from the linear combination
     */
    public static Optional<Vector> linearCombination(List<Double> coefficients, List<Vector> vectors) {

        if (coefficients == null || coefficients.isEmpty() || vectors == null || vectors.isEmpty() || coefficients.size() != vectors.size()) {
            return Optional.empty();
        }

        Optional<Vector> init = createZeroVector(coefficients.size());

        if (!init.isPresent()) {
            return Optional.empty();
        }

        Vector result = init.get();

        for (int i = 0; i < coefficients.size(); i++) {
            Optional<Vector> term = axpy(coefficients.get(i), vectors.get(i), result);
            if (!term.isPresent()) {
                return term;
            }
            result.copy(term.get());
        }

        return Optional.of(result);
    }

    /**
     * floating point operations: 2n
     * memory operations: 2n
     * @param x
     * vector factor
     * @param y
     * vector factor
     * @return
     * an empty optional or an optional containing the dot product result as a Double
     */
    public static Optional<Double> dotProduct(Vector x, Vector y) {

        if (x == null || y == null || x.getComponents().size() != y.getComponents().size()) {
            return Optional.empty();
        }

        Double result = 0.0;

        for (int i = 0; i < x.getComponents().size(); i++) {
            result += x.getComponents().get(i) * y.getComponents().get(i);
        }

        return Optional.of(result);
    }

    /**
     * floating point operations: 2n
     * memory operations: n
     * @param vector
     * the vector who's length will be computed
     * @return
     * a double representing the length of the given vector
     */
    public static Optional<Double> length(Vector vector) {

        if (vector == null) {
            return Optional.empty();
        }

        Optional<Double> vectorDotProduct = Vector.dotProduct(vector, vector);

        return vectorDotProduct.map(aDouble -> Optional.of(Math.sqrt(aDouble))).orElse(vectorDotProduct);
    }

    public static Optional<Vector> matrixMultiplication(Vector vector, Matrix matrix) {

        if (vector == null || matrix == null || vector.getComponents().size() != matrix.getComponents().get(0).size()) {
            return Optional.empty();
        }

        List<Double> components = new ArrayList<>(vector.getComponents().size());

        for (int i = 0; i < matrix.getComponents().size(); i++) {
            Optional<Double> component = Vector.dotProduct(vector, new Vector(matrix.getComponents().get(i)));

            if (!component.isPresent()) {
                return Optional.empty();
            }

            components.add(component.get());
        }

        return Optional.of(new Vector(components));
    }

    private static Optional<Vector> createZeroVector(int size) {

        if (size <= 0) {
            return Optional.empty();
        }

        List<Double> initComponents = new ArrayList<>(Arrays.asList(new Double[size]));
        Collections.fill(initComponents, 0.0);
        return Optional.of(new Vector(initComponents));
    }
}
