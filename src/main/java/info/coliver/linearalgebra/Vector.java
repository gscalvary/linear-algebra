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

    public void assign(Vector vector) {
        this.components = vector.getComponents();
    }

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

    public static Optional<Vector> add(List<Vector> terms) {

        if (terms == null || terms.isEmpty()) {
            return Optional.empty();
        }

        if (terms.size() == 1) {
            return Optional.of(new Vector(terms.get(0).getComponents()));
        }

        int size = terms.get(0).getComponents().size();

        List<Double> sums = new ArrayList<>(size);

        for (int i = 0; i < terms.size(); i++) {
            if (terms.get(i) == null || terms.get(i).getComponents().size() != size) {
                return Optional.empty();
            }
            for (int j = 0; j < terms.get(i).getComponents().size(); j++) {
                if (sums.size() <= j) {
                    sums.add(terms.get(i).getComponents().get(j));
                } else {
                    double componentValue = sums.get(j);
                    componentValue += terms.get(i).getComponents().get(j);
                    sums.set(j, componentValue);
                }
            }
        }

        return Optional.of(new Vector(sums));
    }

    public static Optional<Vector> subtract(List<Vector> terms) {

        if (terms == null || terms.isEmpty()) {
            return Optional.empty();
        }

        if (terms.size() == 1) {
            return Optional.of(new Vector(terms.get(0).getComponents()));
        }

        int size = terms.get(0).getComponents().size();

        List<Double> differences = new ArrayList<>(size);

        for (int i = 0; i < terms.size(); i++) {
            if (terms.get(i) == null || terms.get(i).getComponents().size() != size) {
                return Optional.empty();
            }
            for (int j = 0; j < terms.get(i).getComponents().size(); j++) {
                if (differences.size() <= j) {
                    differences.add(terms.get(i).getComponents().get(j));
                } else {
                    double componentValue = differences.get(j);
                    componentValue -= terms.get(i).getComponents().get(j);
                    differences.set(j, componentValue);
                }
            }
        }

        return Optional.of(new Vector(differences));
    }

    public static Optional<Vector> scale(Vector vector, double factor) {

        if (vector == null) {
            return Optional.empty();
        }

        List<Double> products = new ArrayList<>();

        vector.getComponents().forEach((component) -> products.add(factor * component));

        return Optional.of(new Vector(products));
    }

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

    public static Optional<Vector> linearCombination(List<Double> coefficients, List<Vector> vectors) {

        if (coefficients == null || coefficients.isEmpty() || vectors == null || vectors.isEmpty() || coefficients.size() != vectors.size()) {
            return Optional.empty();
        }

        List<Double> initComponents = new ArrayList<>(Arrays.asList(new Double[coefficients.size()]));
        Collections.fill(initComponents, 0.0);
        Vector result = new Vector(initComponents);

        for (int i = 0; i < coefficients.size(); i++) {
            Optional<Vector> term = axpy(coefficients.get(i), vectors.get(i), result);
            if (!term.isPresent()) {
                return term;
            }
            result.assign(term.get());
        }

        return Optional.of(result);
    }

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

    public static Optional<Double> length(Vector vector) {

        if (vector == null) {
            return Optional.empty();
        }

        Optional<Double> vectorDotProduct = Vector.dotProduct(vector, vector);

        if (!vectorDotProduct.isPresent()) {
            return vectorDotProduct;
        }

        return Optional.of(Math.sqrt(vectorDotProduct.get()));
    }
}
