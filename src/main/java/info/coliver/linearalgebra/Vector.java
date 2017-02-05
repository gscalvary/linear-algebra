package info.coliver.linearalgebra;

import java.util.ArrayList;

public class Vector {

    private ArrayList<Integer> components;

    Vector (ArrayList<Integer> components) {
        this.components = components;
    }

    public ArrayList<Integer> getComponents() {
        return components;
    }

    public void setComponents(ArrayList<Integer> components) {
        this.components = components;
    }

    public void assign(Vector vector) {
        this.components = vector.getComponents();
    }

    public boolean equals(Vector vector) {

        ArrayList<Integer> otherComponents = vector.getComponents();
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
}
