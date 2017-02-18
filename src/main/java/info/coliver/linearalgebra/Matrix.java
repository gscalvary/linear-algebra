package info.coliver.linearalgebra;

import java.util.List;

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
}
