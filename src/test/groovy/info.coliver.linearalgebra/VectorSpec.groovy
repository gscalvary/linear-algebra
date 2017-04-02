package info.coliver.linearalgebra

import spock.lang.Shared
import spock.lang.Specification

class VectorSpec extends Specification {

    @Shared vector
    @Shared components

    def setup() {

        double a = 0.0
        double b = 1.0
        components = [a,a,b]
        vector = new Vector(components)
    }

    def 'Vector components may be retrieved' () {

        when: 'retrieving those components'
        def vectorComponents = vector.getComponents()

        then: 'they match those passed to the constructor.'
        assert vectorComponents == components
    }

    def 'Vector components may be changed' () {

        given: 'new components'
        double a = 1.0
        double b = 0.0
        def newComponents = [a,b,b]

        when: 'setting those components'
        vector.setComponents(newComponents)

        then: 'the vector is updated with those components.'
        assert newComponents == vector.getComponents()
    }

    def 'Vectors may be tested for equality' () {

        given: 'a vector with the same components as another'
        double a = 0.0
        double b = 1.0
        def newComponents = [a,a,b]
        def equalVector = new Vector(newComponents)

        expect: 'those vectors should be equal.'
        assert equalVector.equals(vector)
    }

    def 'Vectors with a different number of components are unequal' () {

        given: 'a vector with different components from another'
        double a = 0.0
        double b = 1.0
        def newComponents = [a,a,b,a]
        def unequalVector = new Vector(newComponents)

        expect: 'those vectors should not be equal.'
        assert !unequalVector.equals(vector)
    }

    def 'Vectors with components of different values are unequal' () {

        given: 'a vector with different components from another'
        double a = 0.0
        double b = 1.0
        def newComponents = [a,b,a]
        def unequalVector = new Vector(newComponents)

        expect: 'those vectors should not be equal.'
        assert !unequalVector.equals(vector)
    }

    def 'Vectors may be copied to one another' () {

        given: 'a new vector'
        double a = 0.0
        double b = 1.0
        def newComponents = [a,a,b,a]
        def newVector = new Vector(newComponents)

        when: 'copying that new vector to an older vector'
        vector.copy(newVector)

        then: 'the two vectors should be equal.'
        assert vector.equals(newVector)
    }

    def 'When passing a null argument to Vector add' () {

        expect: 'the return of an empty optional'
        assert Vector.add(null) == Optional.empty()
    }

    def 'When passing an empty list to Vector add' () {

        expect: 'the return of an empty optional'
        assert Vector.add(new ArrayList<Vector>()) == Optional.empty()
    }

    def 'When passing a single vector to Vector add' () {

        given:
        double a = 1.0
        double b = 2.0
        def components = [a,b]
        def vector = new Vector(components)
        def addends = new ArrayList<Vector>()
        addends.add(vector)

        expect: 'the return of an equivalent vector'
        def result = Vector.add(addends)
        assert result.get() == vector
    }

    def 'When passing vectors of a different size to Vector add' () {

        given:
        double a = 1.0
        double b = 2.0
        def components1 = [a,b]
        def vector1 = new Vector(components1)
        double c = 3.0
        def components2 = [a,b,c]
        def vector2 = new Vector(components2)
        def addends = new ArrayList<Vector>()
        addends.addAll(vector1, vector2)

        expect: 'the return of an empty optional'
        assert Vector.add(addends) == Optional.empty()
    }

    def 'When passing vectors with of the same size to Vector add' () {

        given:
        double a = 1.0
        double b = 2.0
        def components1 = [a,b]
        def vector1 = new Vector(components1)
        def components2 = [a,b]
        def vector2 = new Vector(components2)
        def addends = new ArrayList<Vector>()
        addends.addAll(vector1, vector2)
        double c = 4.0
        def answerComponents = [b,c]
        def answerVector = new Vector(answerComponents)

        expect: 'a vector of the correct magnitude and direction to be returned'
        assert Vector.add(addends).get() == answerVector
    }

    def 'When passing a null argument to Vector scale' () {

        expect: 'the return of an empty optional'
        assert Vector.scale(null, -0.5) == Optional.empty()
    }

    def 'When scaling a vector' () {

        given:
        double a = 2.0
        double b = 3.0
        def components = [a,b]
        def vector = new Vector(components)
        double factor = 4.0
        double c = 8.0
        double d = 12.0
        def answerComponents = [c,d]
        def answerVector = new Vector(answerComponents)

        expect: 'a vector of the correct magnitude and direction to be returned'
        assert Vector.scale(vector, factor).get() == answerVector
    }

    def 'When passing a null argument to Vector subtract' () {

        expect: 'the return of an empty optional'
        assert Vector.subtract(null) == Optional.empty()
    }

    def 'When passing an empty list to Vector subtract' () {

        expect: 'the return of an empty optional'
        assert Vector.subtract(new ArrayList<Vector>()) == Optional.empty()
    }

    def 'When passing a single vector to Vector subtract' () {

        given:
        double a = 1.0
        double b = 2.0
        def components = [a,b]
        def vector = new Vector(components)
        def terms = new ArrayList<Vector>()
        terms.add(vector)

        expect: 'the return of an equivalent vector'
        def result = Vector.subtract(terms)
        assert result.get() == vector
    }

    def 'When passing vectors of a different size to Vector subtract' () {

        given:
        double a = 1.0
        double b = 2.0
        def components1 = [a,b]
        def vector1 = new Vector(components1)
        double c = 3.0
        def components2 = [a,b,c]
        def vector2 = new Vector(components2)
        def terms = new ArrayList<Vector>()
        terms.addAll(vector1, vector2)

        expect: 'the return of an empty optional'
        assert Vector.subtract(terms) == Optional.empty()
    }

    def 'When passing vectors of the same size to Vector subtract' () {

        given:
        double a = 1.0
        double b = 2.0
        def components1 = [a,b]
        def vector1 = new Vector(components1)
        def components2 = [a,b]
        def vector2 = new Vector(components2)
        def terms = new ArrayList<Vector>()
        terms.addAll(vector1, vector2)
        double c = 0.0
        def answerComponents = [c,c]
        def answerVector = new Vector(answerComponents)

        expect: 'a vector of the correct magnitude and direction to be returned'
        assert Vector.subtract(terms).get() == answerVector
    }

    def 'When passing a null Vector to axpy' () {

        expect: 'an empty Optional to be returned'
        double f = 2.0
        assert Vector.axpy(f, null, null) == Optional.empty()
    }

    def 'When passing vectors of the same size to Vector axpy' () {

        given:
        double a = 1.0
        double b = 2.0
        def components1 = [a,a]
        def vector1 = new Vector(components1)
        def components2 = [a,a]
        def vector2 = new Vector(components2)
        double c = 3.0
        def answerComponents = [c,c]
        def answerVector = new Vector(answerComponents)

        expect: 'a vector of the correct magnitude and direction to be returned'
        assert Vector.axpy(b, vector1, vector2).get() == answerVector
    }

    def 'When passing null arguments to Vector linear combination' () {

        expect: 'an empty Optional to be returned'
        assert Vector.linearCombination(null, null) == Optional.empty()
    }

    def 'When passing arguments of unequal size to Vector linear combination' () {

        given:
        double a = 1.0
        def components = [a,a]
        def vector = new Vector(components)
        def addends = new ArrayList<Vector>()
        addends.add(vector)

        expect: 'an empty Optional to be returned'
        assert Vector.linearCombination(components, addends) == Optional.empty()
    }

    def 'When passing arguments of equal size to Vector linear combination' () {

        given:
        double a = 1.0
        def components = [a,a]
        def vector = new Vector(components)
        def addends = new ArrayList<Vector>()
        addends.add(vector)
        addends.add(vector)
        double b = 2.0
        def coefficients = [b,b]
        double c = 4.0
        def answerComponents = [c,c]
        def answerVector = new Vector(answerComponents)

        expect: 'a vector of the correct magnitude and direction to be returned'
        assert Vector.linearCombination(coefficients, addends).get() == answerVector
    }

    def 'When passing null arguments to Vector dot product' () {

        expect: 'an empty Optional to be returned'
        assert Vector.dotProduct(null, null) == Optional.empty()
    }

    def 'When passing vectors of unequal size to Vector dot product' () {

        given:
        double a = 0.0
        def xComponents = [a]
        def x = new Vector(xComponents)
        def yComponents = [a,a]
        def y = new Vector(yComponents)

        expect: 'an empty Optional to be returned'
        assert Vector.dotProduct(x, y) == Optional.empty()
    }

    def 'When passing vectors of equal size to Vector dot product' () {

        given:
        double a = 2.0
        def xComponents = [a,a]
        def x = new Vector(xComponents)
        def yComponents = [a,a]
        def y = new Vector(yComponents)
        double b = 8.0

        expect: 'the correct scalar to be returned'
        assert Vector.dotProduct(x, y).get() == b
    }

    def 'When passing a null argument to Vector dot length' () {

        expect: 'an empty Optional to be returned'
        assert Vector.length(null) == Optional.empty()
    }

    def 'When passing a vector Vector length' () {

        given:
        double a = 2.0
        def components = [a,a,a,a]
        def vector = new Vector(components)
        double b = 4.0

        expect: 'the correct scalar to be returned'
        assert Vector.length(vector).get() == b
    }

    def 'When passing null arguments to Vector matrix multiplication' () {

        expect: 'an empty Optional to be returned'
        assert Vector.matrixMultiplication(null, null) == Optional.empty()
    }

    def 'When passing a vector and matrix of unequal size to Vector matrix multiplication' () {

        given:
        double x0 = 0.0
        double x1 = 0.0
        def vectorComponents = [x0, x1]
        def vector = new Vector(vectorComponents)
        double x0x0 = 0.0
        double x0x1 = 0.0
        double x0x2 = 0.0
        double x1x0 = 0.0
        double x1x1 = 0.0
        double x1x2 = 0.0
        double x2x0 = 0.0
        double x2x1 = 0.0
        double x2x2 = 0.0
        def matrixComponents = [[x0x0, x0x1, x0x2],[x1x0, x1x1, x1x2],[x2x0, x2x1, x2x2]]
        def matrix = new Matrix(matrixComponents)

        expect: 'an empty Optional to be returned'
        assert Vector.matrixMultiplication(vector, matrix) == Optional.empty()
    }

    def 'When passing a vector and matrix of equal sizes to Vector matrix multiplication' () {

        given:
        double x0 = 1.0
        double x1 = 1.0
        double x2 = 1.0
        def vectorComponents = [x0, x1, x2]
        def vector = new Vector(vectorComponents)
        double x0x0 = 1.0
        double x0x1 = 2.0
        double x0x2 = 3.0
        double x1x0 = 4.0
        double x1x1 = 5.0
        double x1x2 = 6.0
        double x2x0 = 7.0
        double x2x1 = 8.0
        double x2x2 = 9.0
        def matrixComponents = [[x0x0, x0x1, x0x2],[x1x0, x1x1, x1x2],[x2x0, x2x1, x2x2]]
        def matrix = new Matrix(matrixComponents)
        double y0 = 6.0
        double y1 = 15.0
        double y2 = 24.0
        vectorComponents = [y0, y1, y2]
        def resultantVector = new Vector(vectorComponents)

        expect: 'a vector of the correct magnitude and direction to be returned'
        assert Vector.matrixMultiplication(vector, matrix).get() == resultantVector
    }

    def 'When creating a zero filled Vector' () {

        given:
        double x0 = 0.0

        expect: 'a vector of the correct size with zeroes in all components to be returned'
        assert Vector.createZeroVector(3).get().getComponents() == [x0,x0,x0]
    }
}
