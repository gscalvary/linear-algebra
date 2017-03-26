package info.coliver.linearalgebra;

import java.util.*;
import java.util.stream.Collectors;

public class Matrix {

    private List<List<Double>> components;

    Matrix (List<List<Double>> components) {
        this.components = components;
    }

    public List<List<Double>> getComponents() {
        return components;
    }

    public void setComponents(List<List<Double>> components) {
        this.components = components;
    }

    public static Optional<Matrix> zero(Matrix matrix) {

        if (matrix == null) {
            return Optional.empty();
        }

        List<List<Double>> components = new ArrayList<>();

        for (List<Double> inputColumn : matrix.getComponents()) {
            List<Double> outputColumn = inputColumn.stream().map(component -> 0.0).collect(Collectors.toList());
            components.add(outputColumn);
        }
        return Optional.of(new Matrix(components));
    }

    public static Optional<Matrix> identity(Matrix matrix) {

        if (matrix == null) {
            return Optional.empty();
        }

        // identity matrices must be square
        int m = matrix.getComponents().size();
        if (m != matrix.getComponents().get(0).size()) {
            return Optional.empty();
        }

        List<List<Double>> components = new ArrayList<>();

        for (int i = 0; i < matrix.getComponents().size(); i++) {
            List<Double> outputColumn = new ArrayList<>(m);
            List<Double> inputColumn = matrix.getComponents().get(i);
            for (int j = 0; j < inputColumn.size(); j++) {
                if (i == j) {
                    outputColumn.add(1.0);
                } else {
                    outputColumn.add(0.0);
                }
            }
            components.add(outputColumn);
        }
        return Optional.of(new Matrix(components));
    }

    public static Optional<Matrix> diagonal(Matrix matrix, Vector vector) {

        if (matrix == null || vector == null) {
            return Optional.empty();
        }

        // diagonal matrices must be square
        int m = matrix.getComponents().size();
        if (m != matrix.getComponents().get(0).size()) {
            return Optional.empty();
        }

        if (m != vector.getComponents().size()) {
            return Optional.empty();
        }

        List<List<Double>> components = new ArrayList<>();
        List<Double> diagonalComponents = vector.getComponents();

        for (int i = 0; i < matrix.getComponents().size(); i++) {
            List<Double> outputColumn = new ArrayList<>(m);
            List<Double> inputColumn = matrix.getComponents().get(i);
            for (int j = 0; j < inputColumn.size(); j++) {
                if (i == j) {
                    outputColumn.add(diagonalComponents.get(i));
                } else {
                    outputColumn.add(0.0);
                }
            }
            components.add(outputColumn);
        }
        return Optional.of(new Matrix(components));
    }

    public static Optional<Matrix> upperTriangular(Matrix matrix) {

        if (matrix == null) {
            return Optional.empty();
        }

        // triangular matrices must be square
        int m = matrix.getComponents().size();
        if (m != matrix.getComponents().get(0).size()) {
            return Optional.empty();
        }

        List<List<Double>> components = new ArrayList<>();

        for (int i = 0; i < matrix.getComponents().size(); i++) {
            List<Double> outputColumn = new ArrayList<>(m);
            List<Double> inputColumn = matrix.getComponents().get(i);
            for (int j = 0; j < inputColumn.size(); j++) {
                if (i < j) {
                    outputColumn.add(0.0);
                } else {
                    outputColumn.add(inputColumn.get(j));
                }
            }
            components.add(outputColumn);
        }
        return Optional.of(new Matrix(components));
    }

    public static Optional<Matrix> strictlyUpperTriangular(Matrix matrix) {

        if (matrix == null) {
            return Optional.empty();
        }

        // triangular matrices must be square
        int m = matrix.getComponents().size();
        if (m != matrix.getComponents().get(0).size()) {
            return Optional.empty();
        }

        List<List<Double>> components = new ArrayList<>();

        for (int i = 0; i < matrix.getComponents().size(); i++) {
            List<Double> outputColumn = new ArrayList<>(m);
            List<Double> inputColumn = matrix.getComponents().get(i);
            for (int j = 0; j < inputColumn.size(); j++) {
                if (i <= j) {
                    outputColumn.add(0.0);
                } else {
                    outputColumn.add(inputColumn.get(j));
                }
            }
            components.add(outputColumn);
        }
        return Optional.of(new Matrix(components));
    }

    public static Optional<Matrix> unitUpperTriangular(Matrix matrix) {

        if (matrix == null) {
            return Optional.empty();
        }

        // triangular matrices must be square
        int m = matrix.getComponents().size();
        if (m != matrix.getComponents().get(0).size()) {
            return Optional.empty();
        }

        List<List<Double>> components = new ArrayList<>();

        for (int i = 0; i < matrix.getComponents().size(); i++) {
            List<Double> outputColumn = new ArrayList<>(m);
            List<Double> inputColumn = matrix.getComponents().get(i);
            for (int j = 0; j < inputColumn.size(); j++) {
                if (i < j) {
                    outputColumn.add(0.0);
                } else if (i == j){
                    outputColumn.add(1.0);
                } else {
                    outputColumn.add(inputColumn.get(j));
                }
            }
            components.add(outputColumn);
        }
        return Optional.of(new Matrix(components));
    }

    public static Optional<Matrix> lowerTriangular(Matrix matrix) {

        if (matrix == null) {
            return Optional.empty();
        }

        // triangular matrices must be square
        int m = matrix.getComponents().size();
        if (m != matrix.getComponents().get(0).size()) {
            return Optional.empty();
        }

        List<List<Double>> components = new ArrayList<>();

        for (int i = 0; i < matrix.getComponents().size(); i++) {
            List<Double> outputColumn = new ArrayList<>(m);
            List<Double> inputColumn = matrix.getComponents().get(i);
            for (int j = 0; j < inputColumn.size(); j++) {
                if (i > j) {
                    outputColumn.add(0.0);
                } else {
                    outputColumn.add(inputColumn.get(j));
                }
            }
            components.add(outputColumn);
        }
        return Optional.of(new Matrix(components));
    }

    public static Optional<Matrix> strictlyLowerTriangular(Matrix matrix) {

        if (matrix == null) {
            return Optional.empty();
        }

        // triangular matrices must be square
        int m = matrix.getComponents().size();
        if (m != matrix.getComponents().get(0).size()) {
            return Optional.empty();
        }

        List<List<Double>> components = new ArrayList<>();

        for (int i = 0; i < matrix.getComponents().size(); i++) {
            List<Double> outputColumn = new ArrayList<>(m);
            List<Double> inputColumn = matrix.getComponents().get(i);
            for (int j = 0; j < inputColumn.size(); j++) {
                if (i >= j) {
                    outputColumn.add(0.0);
                } else {
                    outputColumn.add(inputColumn.get(j));
                }
            }
            components.add(outputColumn);
        }
        return Optional.of(new Matrix(components));
    }

    public static Optional<Matrix> unitLowerTriangular(Matrix matrix) {

        if (matrix == null) {
            return Optional.empty();
        }

        // triangular matrices must be square
        int m = matrix.getComponents().size();
        if (m != matrix.getComponents().get(0).size()) {
            return Optional.empty();
        }

        List<List<Double>> components = new ArrayList<>();

        for (int i = 0; i < matrix.getComponents().size(); i++) {
            List<Double> outputColumn = new ArrayList<>(m);
            List<Double> inputColumn = matrix.getComponents().get(i);
            for (int j = 0; j < inputColumn.size(); j++) {
                if (i > j) {
                    outputColumn.add(0.0);
                } else if (i == j){
                    outputColumn.add(1.0);
                } else {
                    outputColumn.add(inputColumn.get(j));
                }
            }
            components.add(outputColumn);
        }
        return Optional.of(new Matrix(components));
    }

    public static Optional<Matrix> transpose(Matrix matrix) {

        if (matrix == null) {
            return Optional.empty();
        }

        List<List<Double>> components = new ArrayList<>();

        for (int i = 0; i < matrix.getComponents().size(); i++) {
            for (int j = 0; j < matrix.getComponents().get(i).size(); j++) {
                if (i == 0) {
                    List<Double> outputColumn = new ArrayList<>();
                    outputColumn.add(matrix.getComponents().get(i).get(j));
                    components.add(outputColumn);
                } else {
                    components.get(j).add(matrix.getComponents().get(i).get(j));
                }
            }
        }

        return Optional.of(new Matrix(components));
    }

    public static Optional<Matrix> symmetrizeFromLowerTriangular(Matrix matrix) {

        if (matrix == null) {
            return Optional.empty();
        }

        // symmetrical matrices must be square
        int m = matrix.getComponents().size();
        if (m != matrix.getComponents().get(0).size()) {
            return Optional.empty();
        }

        List<List<Double>> components = new ArrayList<>();

        for (int i = 0; i < matrix.getComponents().size(); i++) {
            for (int j = 0; j < matrix.getComponents().get(i).size(); j++) {
                if (i == 0) {
                    List<Double> outputColumn = new ArrayList<>();
                    outputColumn.add(matrix.getComponents().get(i).get(j));
                    components.add(outputColumn);
                } else {
                    if (i <= j) {
                        components.get(j).add(matrix.getComponents().get(i).get(j));
                    } else {
                        components.get(j).add(matrix.getComponents().get(j).get(i));
                    }
                }
            }
        }

        return Optional.of(new Matrix(components));
    }

    public static Optional<Matrix> symmetrizeFromUpperTriangular(Matrix matrix) {

        if (matrix == null) {
            return Optional.empty();
        }

        // symmetrical matrices must be square
        int m = matrix.getComponents().size();
        if (m != matrix.getComponents().get(0).size()) {
            return Optional.empty();
        }

        List<List<Double>> components = new ArrayList<>();

        for (int i = 0; i < matrix.getComponents().size(); i++) {
            for (int j = 0; j < matrix.getComponents().get(i).size(); j++) {
                if (i == 0) {
                    List<Double> outputColumn = new ArrayList<>();
                    outputColumn.add(matrix.getComponents().get(j).get(i));
                    components.add(outputColumn);
                } else {
                    if (i >= j) {
                        components.get(j).add(matrix.getComponents().get(i).get(j));
                    } else {
                        components.get(j).add(matrix.getComponents().get(j).get(i));
                    }
                }
            }
        }

        return Optional.of(new Matrix(components));
    }

    public static Optional<Matrix> scale(Matrix matrix, Double factor) {

        if (matrix == null) {
            return Optional.empty();
        }

        List<List<Double>> components = new ArrayList<>();

        for (int i = 0; i < matrix.getComponents().size(); i++) {
            Optional<Vector> columnVector = Vector.scale(new Vector(matrix.getComponents().get(i)), factor);

            if (!columnVector.isPresent()) {
                return Optional.empty();
            }

            components.add(columnVector.get().getComponents());
        }

        return Optional.of(new Matrix(components));
    }

    public static Optional<Matrix> add(List<Matrix> terms) {

        if (terms == null || terms.isEmpty()) {
            return Optional.empty();
        }

        if (terms.size() == 1) {
            return Optional.of(new Matrix(terms.get(0).getComponents()));
        }

        List<List<Double>> components = new ArrayList<>();

        int size = terms.get(0).getComponents().size();

        for (int i = 0; i < terms.size(); i++) {

            List<Vector> columnVectors = new ArrayList<>();

            for (Matrix term : terms) {

                if (size != term.getComponents().size()) {
                    return Optional.empty();
                }

                columnVectors.add(new Vector(term.getComponents().get(i)));
            }

            Optional<Vector> columnSumVector = Vector.add(columnVectors);

            if (!columnSumVector.isPresent()) {
                return Optional.empty();
            }

            components.add(columnSumVector.get().getComponents());
        }

        return Optional.of(new Matrix(components));
    }

    public static Optional<Matrix> subtract(List<Matrix> terms) {

        if (terms == null || terms.isEmpty()) {
            return Optional.empty();
        }

        if (terms.size() == 1) {
            return Optional.of(new Matrix(terms.get(0).getComponents()));
        }

        List<List<Double>> components = new ArrayList<>();

        int size = terms.get(0).getComponents().size();

        for (int i = 0; i < terms.size(); i++) {

            List<Vector> columnVectors = new ArrayList<>();

            for (Matrix term : terms) {

                if (size != term.getComponents().size()) {
                    return Optional.empty();
                }

                columnVectors.add(new Vector(term.getComponents().get(i)));
            }

            Optional<Vector> columnSumVector = Vector.subtract(columnVectors);

            if (!columnSumVector.isPresent()) {
                return Optional.empty();
            }

            components.add(columnSumVector.get().getComponents());
        }

        return Optional.of(new Matrix(components));
    }

    /**
     * memory operations: 4n^2
     * floating point operations: 2n^3
     * @param a
     * matrix
     * @param b
     * matrix
     * @return
     * matrix resulting from the ordered multiplication of matrices a and b
     */
    public static Optional<Matrix> matrixMultiplication(Matrix a, Matrix b) {

        if (a == null || b == null) {
            return Optional.empty();
        }

        int aHeight = a.getComponents().get(0).size();
        int aWidth = a.getComponents().size();
        int bHeight = b.getComponents().get(0).size();
        int bWidth = b.getComponents().size();

        if (aHeight != bWidth || aWidth != bHeight) {
            return Optional.empty();
        }

        List<List<Double>> components = new ArrayList<>();

        for (int i = 0; i < aHeight; i++) {
            // create a vector out of each row of a
            List<Double> aRowComponents = new ArrayList<>();
            for (int j = 0; j < aWidth; j++) {
                aRowComponents.add(a.getComponents().get(j).get(i));
            }
            Vector aRowVector = new Vector(aRowComponents);

            // use matrix vector multiplication to compute each output matrix column
            Optional<Vector> ithColumn = Vector.matrixMultiplication(aRowVector, b);

            if (!ithColumn.isPresent()) {
                return Optional.empty();
            }

            components.add(ithColumn.get().getComponents());
        }

        return Matrix.transpose(new Matrix(components));
    }
}
