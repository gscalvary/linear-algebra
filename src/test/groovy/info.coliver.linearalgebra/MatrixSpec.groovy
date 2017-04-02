package info.coliver.linearalgebra

import spock.lang.Shared
import spock.lang.Specification

class MatrixSpec extends Specification {

    @Shared matrix
    @Shared components
    @Shared vector

    def setup() {

        double x0x0 = 1.0
        double x0x1 = 0.0
        double x1x0 = 0.0
        double x1x1 = 1.0
        components = [[x0x0, x0x1], [x1x0, x1x1]]
        matrix = new Matrix(components)
    }

    def 'Matrix components may be retrieved' () {

        when: 'retrieving those components'
        def matrixComponents = matrix.getComponents()

        then: 'they match those passed to the constructor.'
        assert matrixComponents == components
    }

    def 'Matrix components may be changed' () {

        given: 'new components'
        double x0x0 = 0.0
        double x0x1 = 1.0
        double x1x0 = 1.0
        double x1x1 = 0.0
        def newComponents = [[x0x0, x0x1], [x1x0, x1x1]]

        when: 'setting those components'
        matrix.setComponents(newComponents)

        then: 'the matrix is updated with those components.'
        assert newComponents == matrix.getComponents()
    }

    def 'When passing a null argument to the zero matrix method' () {

        expect: 'an empty Optional to be returned'
        assert Matrix.zero(null) == Optional.empty()
    }

    def 'Zero matrices may be generated' () {

        given: 'a matrix'
        double x0x0 = 1.0
        double x0x1 = 1.0
        double x1x0 = 1.0
        double x1x1 = 1.0
        def newComponents = [[x0x0, x0x1], [x1x0, x1x1]]
        matrix = new Matrix(newComponents)
        def zeroComponents = [[0.0, 0.0], [0.0, 0.0]]
        def answerMatrix = new Matrix(zeroComponents)

        when: 'converting that matrix to a zero matrix'
        def zeroMatrix = Matrix.zero(matrix)

        then: 'a matrix of the appropriate size is returned having all zero components.'
        assert zeroMatrix.get().getComponents() == answerMatrix.getComponents()
    }

    def 'When passing a null argument to the identity matrix method' () {

        expect: 'an empty Optional to be returned'
        assert Matrix.identity(null) == Optional.empty()
    }

    def 'The identity matrix method should only operate on matrices of equal height and width' () {

        given: 'a matrix of unequal height and width'
        double x0x0 = 1.0
        double x0x1 = 1.0
        double x1x0 = 1.0
        double x1x1 = 1.0
        double x2x0 = 1.0
        double x2x1 = 1.0
        def newComponents = [[x0x0, x0x1], [x1x0, x1x1], [x2x0, x2x1]]
        matrix = new Matrix(newComponents)

        expect: 'an empty optional to be returned from the identity matrix method'
        assert Matrix.identity(matrix) == Optional.empty()
    }

    def 'Identity matrices may be generated' () {

        given: 'a matrix'
        double x0x0 = 1.0
        double x0x1 = 1.0
        double x1x0 = 1.0
        double x1x1 = 1.0
        def newComponents = [[x0x0, x0x1], [x1x0, x1x1]]
        matrix = new Matrix(newComponents)
        def identityComponents = [[1.0, 0.0], [0.0, 1.0]]
        def answerMatrix = new Matrix(identityComponents)

        when: 'converting that matrix to an identity matrix'
        def identityMatrix = Matrix.identity(matrix)

        then: 'a matrix of the appropriate size is returned having the correct components.'
        assert identityMatrix.get().getComponents() == answerMatrix.getComponents()
    }

    def 'When passing a null argument to the diagonal matrix method' () {

        expect: 'an empty Optional to be returned'
        assert Matrix.diagonal(null, null) == Optional.empty()
    }

    def 'The diagonal matrix method should only operate on matrices of equal height and width' () {

        given: 'a matrix of unequal height and width and a vector'
        double x0x0 = 1.0
        double x0x1 = 1.0
        double x1x0 = 1.0
        double x1x1 = 1.0
        double x2x0 = 1.0
        double x2x1 = 1.0
        def mComponents = [[x0x0, x0x1], [x1x0, x1x1], [x2x0, x2x1]]
        matrix = new Matrix(mComponents)
        double x0y0 = 2.0
        double x0y1 = 3.0
        def vComponents = [x0y0, x0y1]
        vector = new Vector(vComponents)

        expect: 'an empty optional to be returned from the identity matrix method'
        assert Matrix.diagonal(matrix, vector) == Optional.empty()
    }

    def 'The diagonal matrix method should only operate if the diagonal vector is of the same width as the matrix' () {

        given: 'a matrix of equal height and width and a vector of a differing width'
        double x0x0 = 1.0
        double x0x1 = 1.0
        double x1x0 = 1.0
        double x1x1 = 1.0
        def mComponents = [[x0x0, x0x1], [x1x0, x1x1]]
        matrix = new Matrix(mComponents)
        double x0y0 = 2.0
        double x0y1 = 3.0
        double x0y2 = 4.0
        def vComponents = [x0y0, x0y1, x0y2]
        vector = new Vector(vComponents)

        expect: 'an empty optional to be returned from the identity matrix method'
        assert Matrix.diagonal(matrix, vector) == Optional.empty()
    }

    def 'Diagonal matrices may be generated' () {

        given: 'a matrix of equal height and width and a vector of equal width'
        double x0x0 = 1.0
        double x0x1 = 1.0
        double x0x2 = 1.0
        double x1x0 = 1.0
        double x1x1 = 1.0
        double x1x2 = 1.0
        double x2x0 = 1.0
        double x2x1 = 1.0
        double x2x2 = 1.0
        def mComponents = [[x0x0, x0x1, x0x2], [x1x0, x1x1, x1x2], [x2x0, x2x1, x2x2]]
        matrix = new Matrix(mComponents)
        double x0y0 = 2.0
        double x0y1 = 3.0
        double x0y2 = 4.0
        def vComponents = [x0y0, x0y1, x0y2]
        vector = new Vector(vComponents)
        double a0a0 = 2.0
        double a0a1 = 0.0
        double a0a2 = 0.0
        double a1a0 = 0.0
        double a1a1 = 3.0
        double a1a2 = 0.0
        double a2a0 = 0.0
        double a2a1 = 0.0
        double a2a2 = 4.0
        def aComponents = [[a0a0, a0a1, a0a2], [a1a0, a1a1, a1a2], [a2a0, a2a1, a2a2]]
        def answerMatrix = new Matrix(aComponents)

        when: 'converting that matrix to a diagonal matrix'
        def diagonalMatrix = Matrix.diagonal(matrix, vector)

        then: 'a matrix of the appropriate size is returned having the correct components.'
        assert diagonalMatrix.get().getComponents() == answerMatrix.getComponents()
    }

    def 'When passing a null argument to the upper triangular matrix method' () {

        expect: 'an empty Optional to be returned'
        assert Matrix.upperTriangular(null) == Optional.empty()
    }

    def 'The upper triangular matrix method should only operate on matrices of equal height and width' () {

        given: 'a matrix of unequal height and width'
        double x0x0 = 1.0
        double x0x1 = 1.0
        double x1x0 = 1.0
        double x1x1 = 1.0
        double x2x0 = 1.0
        double x2x1 = 1.0
        def newComponents = [[x0x0, x0x1], [x1x0, x1x1], [x2x0, x2x1]]
        matrix = new Matrix(newComponents)

        expect: 'an empty optional to be returned from the upper triangular matrix method'
        assert Matrix.upperTriangular(matrix) == Optional.empty()
    }

    def 'Upper triangular matrices may be generated' () {

        given: 'a matrix'
        double x0x0 = 1.0
        double x0x1 = 1.0
        double x1x0 = 1.0
        double x1x1 = 1.0
        def newComponents = [[x0x0, x0x1], [x1x0, x1x1]]
        matrix = new Matrix(newComponents)
        def upperTriangularComponents = [[1.0, 0.0], [1.0, 1.0]]
        def answerMatrix = new Matrix(upperTriangularComponents)

        when: 'converting that matrix to an upper triangular matrix'
        def upperTriangular = Matrix.upperTriangular(matrix)

        then: 'a matrix of the appropriate size is returned having the correct components.'
        assert upperTriangular.get().getComponents() == answerMatrix.getComponents()
    }

    def 'When passing a null argument to the strictly upper triangular matrix method' () {

        expect: 'an empty Optional to be returned'
        assert Matrix.strictlyUpperTriangular(null) == Optional.empty()
    }

    def 'The strictly upper triangular matrix method should only operate on matrices of equal height and width' () {

        given: 'a matrix of unequal height and width'
        double x0x0 = 1.0
        double x0x1 = 1.0
        double x1x0 = 1.0
        double x1x1 = 1.0
        double x2x0 = 1.0
        double x2x1 = 1.0
        def newComponents = [[x0x0, x0x1], [x1x0, x1x1], [x2x0, x2x1]]
        matrix = new Matrix(newComponents)

        expect: 'an empty optional to be returned from the strictly upper triangular matrix method'
        assert Matrix.strictlyUpperTriangular(matrix) == Optional.empty()
    }

    def 'Strickly upper triangular matrices may be generated' () {

        given: 'a matrix'
        double x0x0 = 1.0
        double x0x1 = 1.0
        double x1x0 = 1.0
        double x1x1 = 1.0
        def newComponents = [[x0x0, x0x1], [x1x0, x1x1]]
        matrix = new Matrix(newComponents)
        def strictlyUpperTriangularComponents = [[0.0, 0.0], [1.0, 0.0]]
        def answerMatrix = new Matrix(strictlyUpperTriangularComponents)

        when: 'converting that matrix to a strictly upper triangular matrix'
        def strictlyUpperTriangular = Matrix.strictlyUpperTriangular(matrix)

        then: 'a matrix of the appropriate size is returned having the correct components.'
        assert strictlyUpperTriangular.get().getComponents() == answerMatrix.getComponents()
    }

    def 'When passing a null argument to the unit upper triangular matrix method' () {

        expect: 'an empty Optional to be returned'
        assert Matrix.unitUpperTriangular(null) == Optional.empty()
    }

    def 'The unit upper triangular matrix method should only operate on matrices of equal height and width' () {

        given: 'a matrix of unequal height and width'
        double x0x0 = 1.0
        double x0x1 = 1.0
        double x1x0 = 1.0
        double x1x1 = 1.0
        double x2x0 = 1.0
        double x2x1 = 1.0
        def newComponents = [[x0x0, x0x1], [x1x0, x1x1], [x2x0, x2x1]]
        matrix = new Matrix(newComponents)

        expect: 'an empty optional to be returned from the unit triangular matrix method'
        assert Matrix.unitUpperTriangular(matrix) == Optional.empty()
    }

    def 'Unit upper triangular matrices may be generated' () {

        given: 'a matrix'
        double x0x0 = 2.0
        double x0x1 = 2.0
        double x1x0 = 2.0
        double x1x1 = 2.0
        def newComponents = [[x0x0, x0x1], [x1x0, x1x1]]
        matrix = new Matrix(newComponents)
        def unitUpperTriangularComponents = [[1.0, 0.0], [2.0, 1.0]]
        def answerMatrix = new Matrix(unitUpperTriangularComponents)

        when: 'converting that matrix to a unit upper triangular matrix'
        def unitUpperTriangular = Matrix.unitUpperTriangular(matrix)

        then: 'a matrix of the appropriate size is returned having the correct components.'
        assert unitUpperTriangular.get().getComponents() == answerMatrix.getComponents()
    }

    def 'When passing a null argument to the lower triangular matrix method' () {

        expect: 'an empty Optional to be returned'
        assert Matrix.lowerTriangular(null) == Optional.empty()
    }

    def 'The lower triangular matrix method should only operate on matrices of equal height and width' () {

        given: 'a matrix of unequal height and width'
        double x0x0 = 1.0
        double x0x1 = 1.0
        double x1x0 = 1.0
        double x1x1 = 1.0
        double x2x0 = 1.0
        double x2x1 = 1.0
        def newComponents = [[x0x0, x0x1], [x1x0, x1x1], [x2x0, x2x1]]
        matrix = new Matrix(newComponents)

        expect: 'an empty optional to be returned from the lower triangular matrix method'
        assert Matrix.upperTriangular(matrix) == Optional.empty()
    }

    def 'Lower triangular matrices may be generated' () {

        given: 'a matrix'
        double x0x0 = 2.0
        double x0x1 = 2.0
        double x1x0 = 2.0
        double x1x1 = 2.0
        def newComponents = [[x0x0, x0x1], [x1x0, x1x1]]
        matrix = new Matrix(newComponents)
        def lowerTriangularComponents = [[2.0, 2.0], [0.0, 2.0]]
        def answerMatrix = new Matrix(lowerTriangularComponents)

        when: 'converting that matrix to a lower triangular matrix'
        def lowerTriangular = Matrix.lowerTriangular(matrix)

        then: 'a matrix of the appropriate size is returned having the correct components.'
        assert lowerTriangular.get().getComponents() == answerMatrix.getComponents()
    }

    def 'When passing a null argument to the strictly lower triangular matrix method' () {

        expect: 'an empty Optional to be returned'
        assert Matrix.strictlyLowerTriangular(null) == Optional.empty()
    }

    def 'The strictly lower triangular matrix method should only operate on matrices of equal height and width' () {

        given: 'a matrix of unequal height and width'
        double x0x0 = 1.0
        double x0x1 = 1.0
        double x1x0 = 1.0
        double x1x1 = 1.0
        double x2x0 = 1.0
        double x2x1 = 1.0
        def newComponents = [[x0x0, x0x1], [x1x0, x1x1], [x2x0, x2x1]]
        matrix = new Matrix(newComponents)

        expect: 'an empty optional to be returned from the strictly lower triangular matrix method'
        assert Matrix.strictlyLowerTriangular(matrix) == Optional.empty()
    }

    def 'Strickly lower triangular matrices may be generated' () {

        given: 'a matrix'
        double x0x0 = 2.0
        double x0x1 = 2.0
        double x1x0 = 2.0
        double x1x1 = 2.0
        def newComponents = [[x0x0, x0x1], [x1x0, x1x1]]
        matrix = new Matrix(newComponents)
        def strictlyLowerTriangularComponents = [[0.0, 2.0], [0.0, 0.0]]
        def answerMatrix = new Matrix(strictlyLowerTriangularComponents)

        when: 'converting that matrix to a strictly lower triangular matrix'
        def strictlyLowerTriangular = Matrix.strictlyLowerTriangular(matrix)

        then: 'a matrix of the appropriate size is returned having the correct components.'
        assert strictlyLowerTriangular.get().getComponents() == answerMatrix.getComponents()
    }

    def 'When passing a null argument to the unit lower triangular matrix method' () {

        expect: 'an empty Optional to be returned'
        assert Matrix.unitLowerTriangular(null) == Optional.empty()
    }

    def 'The unit lower triangular matrix method should only operate on matrices of equal height and width' () {

        given: 'a matrix of unequal height and width'
        double x0x0 = 1.0
        double x0x1 = 1.0
        double x1x0 = 1.0
        double x1x1 = 1.0
        double x2x0 = 1.0
        double x2x1 = 1.0
        def newComponents = [[x0x0, x0x1], [x1x0, x1x1], [x2x0, x2x1]]
        matrix = new Matrix(newComponents)

        expect: 'an empty optional to be returned from the unit triangular matrix method'
        assert Matrix.unitLowerTriangular(matrix) == Optional.empty()
    }

    def 'Unit lower triangular matrices may be generated' () {

        given: 'a matrix'
        double x0x0 = 2.0
        double x0x1 = 2.0
        double x1x0 = 2.0
        double x1x1 = 2.0
        def newComponents = [[x0x0, x0x1], [x1x0, x1x1]]
        matrix = new Matrix(newComponents)
        def unitLowerTriangularComponents = [[1.0, 2.0], [0.0, 1.0]]
        def answerMatrix = new Matrix(unitLowerTriangularComponents)

        when: 'converting that matrix to a unit lower triangular matrix'
        def unitLowerTriangular = Matrix.unitLowerTriangular(matrix)

        then: 'a matrix of the appropriate size is returned having the correct components.'
        assert unitLowerTriangular.get().getComponents() == answerMatrix.getComponents()
    }

    def 'When passing a null argument to the transpose matrix method' () {

        expect: 'an empty Optional to be returned'
        assert Matrix.transpose(null) == Optional.empty()
    }

    def 'Transposed matrices may be generated' () {

        given: 'a matrix'
        double x0x0 = 1.0
        double x0x1 = 4.0
        double x0x2 = 7.0
        double x1x0 = 2.0
        double x1x1 = 5.0
        double x1x2 = 8.0
        double x2x0 = 3.0
        double x2x1 = 6.0
        double x2x2 = 9.0
        def newComponents = [[x0x0, x0x1, x0x2, x0x2], [x1x0, x1x1, x1x2, x1x2], [x2x0, x2x1, x2x2, x2x2]]
        matrix = new Matrix(newComponents)
        def transposeTriangularComponents = [[x0x0, x1x0, x2x0], [x0x1, x1x1, x2x1], [x0x2, x1x2, x2x2], [x0x2, x1x2, x2x2]]
        def answerMatrix = new Matrix(transposeTriangularComponents)

        when: 'transposing that matrix'
        def transposedMatrix = Matrix.transpose(matrix)

        then: 'a matrix of the appropriate size is returned having the correct components.'
        assert transposedMatrix.get().getComponents() == answerMatrix.getComponents()
    }

    def 'When passing a null argument to the symmetrize from lower triangle matrix method' () {

        expect: 'an empty Optional to be returned'
        assert Matrix.symmetrizeFromLowerTriangular(null) == Optional.empty()
    }

    def 'The symmetrize from lower triangle matrix method should only operate on matrices of equal height and width' () {

        given: 'a matrix of unequal height and width'
        double x0x0 = 1.0
        double x0x1 = 1.0
        double x1x0 = 1.0
        double x1x1 = 1.0
        double x2x0 = 1.0
        double x2x1 = 1.0
        def newComponents = [[x0x0, x0x1], [x1x0, x1x1], [x2x0, x2x1]]
        matrix = new Matrix(newComponents)

        expect: 'an empty optional to be returned from the symmetrize from lower triangle matrix method'
        assert Matrix.symmetrizeFromLowerTriangular(matrix) == Optional.empty()
    }

    def 'Matrices made symmetrical to their lower triangle may be generated' () {

        given: 'a matrix'
        double x0x0 = 1.0
        double x0x1 = 4.0
        double x0x2 = 7.0
        double x1x0 = 2.0
        double x1x1 = 5.0
        double x1x2 = 8.0
        double x2x0 = 3.0
        double x2x1 = 6.0
        double x2x2 = 9.0
        def newComponents = [[x0x0, x0x1, x0x2], [x1x0, x1x1, x1x2], [x2x0, x2x1, x2x2]]
        matrix = new Matrix(newComponents)
        def symFromLowerTriangularComponents = [[x0x0, x0x1, x0x2], [x0x1, x1x1, x1x2], [x0x2, x1x2, x2x2]]
        def answerMatrix = new Matrix(symFromLowerTriangularComponents)

        when: 'making that matrix symmetrical'
        def symmetrizedMatrix = Matrix.symmetrizeFromLowerTriangular(matrix)

        then: 'a matrix of the appropriate size is returned having the correct components.'
        assert symmetrizedMatrix.get().getComponents() == answerMatrix.getComponents()
    }

    def 'When passing a null argument to the symmetrize from upper triangle matrix method' () {

        expect: 'an empty Optional to be returned'
        assert Matrix.symmetrizeFromUpperTriangular(null) == Optional.empty()
    }

    def 'The symmetrize from upper triangle matrix method should only operate on matrices of equal height and width' () {

        given: 'a matrix of unequal height and width'
        double x0x0 = 1.0
        double x0x1 = 1.0
        double x1x0 = 1.0
        double x1x1 = 1.0
        double x2x0 = 1.0
        double x2x1 = 1.0
        def newComponents = [[x0x0, x0x1], [x1x0, x1x1], [x2x0, x2x1]]
        matrix = new Matrix(newComponents)

        expect: 'an empty optional to be returned from the symmetrize from lower triangle matrix method'
        assert Matrix.symmetrizeFromUpperTriangular(matrix) == Optional.empty()
    }

    def 'Matrices made symmetrical to their upper triangle may be generated' () {

        given: 'a matrix'
        double x0x0 = 1.0
        double x0x1 = 4.0
        double x0x2 = 7.0
        double x1x0 = 2.0
        double x1x1 = 5.0
        double x1x2 = 8.0
        double x2x0 = 3.0
        double x2x1 = 6.0
        double x2x2 = 9.0
        def newComponents = [[x0x0, x0x1, x0x2], [x1x0, x1x1, x1x2], [x2x0, x2x1, x2x2]]
        matrix = new Matrix(newComponents)
        def symFromUpperTriangularComponents = [[x0x0, x1x0, x2x0], [x1x0, x1x1, x2x1], [x2x0, x2x1, x2x2]]
        def answerMatrix = new Matrix(symFromUpperTriangularComponents)

        when: 'making that matrix symmetrical'
        def symmetrizedMatrix = Matrix.symmetrizeFromUpperTriangular(matrix)

        then: 'a matrix of the appropriate size is returned having the correct components.'
        assert symmetrizedMatrix.get().getComponents() == answerMatrix.getComponents()
    }

    def 'When passing a null argument to the scale matrix method' () {

        expect: 'an empty Optional to be returned'
        assert Matrix.scale(null, 1.0) == Optional.empty()
    }

    def 'Matrices may be scaled' () {

        given: 'a matrix'
        double x0x0 = 1.0
        double x0x1 = 4.0
        double x0x2 = 7.0
        double x1x0 = 2.0
        double x1x1 = 5.0
        double x1x2 = 8.0
        double x2x0 = 3.0
        double x2x1 = 6.0
        double x2x2 = 9.0
        def newComponents = [[x0x0, x0x1, x0x2], [x1x0, x1x1, x1x2], [x2x0, x2x1, x2x2]]
        matrix = new Matrix(newComponents)
        def scaledComponents = [[2.0, 8.0, 14.0], [4.0, 10.0, 16.0], [6.0, 12.0, 18.0]]
        def answerMatrix = new Matrix(scaledComponents)

        when: 'scaling that matrix'
        def scaledMatrix = Matrix.scale(matrix, 2.0)

        then: 'a matrix of the appropriate size is returned having the correct components.'
        assert scaledMatrix.get().getComponents() == answerMatrix.getComponents()
    }

    def 'When passing a null argument to Matrix add' () {

        expect: 'the return of an empty optional'
        assert Matrix.add(null) == Optional.empty()
    }

    def 'When passing an empty list to Matrix add' () {

        expect: 'the return of an empty optional'
        assert Matrix.add(new ArrayList<Matrix>()) == Optional.empty()
    }

    def 'When passing a single matrix to Matrix add' () {

        given:
        double a = 1.0
        double b = 2.0
        def components = [[a,b], [a,b]]
        matrix = new Matrix(components)
        def addends = new ArrayList<Matrix>()
        addends.add(matrix)

        expect: 'the return of an equivalent matrix'
        def result = Matrix.add(addends)
        assert result.get().getComponents() == matrix.getComponents()
    }

    def 'When passing matrices of different sizes to Matrix add' () {

        given:
        double a = 1.0
        double b = 2.0
        def components1 = [[a,b], [a,b]]
        def matrix1 = new Matrix(components1)
        double c = 3.0
        def components2 = [[a,b,c], [a,b,c]]
        def matrix2 = new Matrix(components2)
        def addends = new ArrayList<Matrix>()
        addends.addAll(matrix1, matrix2)

        expect: 'the return of an empty optional'
        assert Matrix.add(addends) == Optional.empty()
    }

    def 'When passing matrices of the same size to Matrix add' () {

        given:
        double a = 1.0
        double b = 2.0
        def components1 = [[a,b], [a,b]]
        def matrix1 = new Matrix(components1)
        def components2 = [[a,b], [a,b]]
        def matrix2 = new Matrix(components2)
        def addends = new ArrayList<Matrix>()
        addends.addAll(matrix1, matrix2)
        double c = 4.0
        def answerComponents = [[b,c], [b,c]]
        def answerMatrix = new Matrix(answerComponents)

        expect: 'a matrix of the correct size to be returned'
        assert Matrix.add(addends).get().getComponents() == answerMatrix.getComponents()
    }

    def 'When passing an empty list to Matrix subtract' () {

        expect: 'the return of an empty optional'
        assert Matrix.subtract(new ArrayList<Matrix>()) == Optional.empty()
    }

    def 'When passing a single matrix to Matrix subtract' () {

        given:
        double a = 1.0
        double b = 2.0
        def components = [[a,b], [a,b]]
        matrix = new Matrix(components)
        def terms = new ArrayList<Matrix>()
        terms.add(matrix)

        expect: 'the return of an equivalent matrix'
        def result = Matrix.subtract(terms)
        assert result.get().getComponents() == matrix.getComponents()
    }

    def 'When passing matrices of different sizes to Matrix subtract' () {

        given:
        double a = 1.0
        double b = 2.0
        def components1 = [[a,b], [a,b]]
        def matrix1 = new Matrix(components1)
        double c = 3.0
        def components2 = [[a,b,c], [a,b,c]]
        def matrix2 = new Matrix(components2)
        def terms = new ArrayList<Matrix>()
        terms.addAll(matrix1, matrix2)

        expect: 'the return of an empty optional'
        assert Matrix.subtract(terms) == Optional.empty()
    }

    def 'When passing matrices of the same size to Matrix subtract' () {

        given:
        double a = 1.0
        double b = 2.0
        def components1 = [[a,b], [a,b]]
        def matrix1 = new Matrix(components1)
        def components2 = [[a,b], [a,b]]
        def matrix2 = new Matrix(components2)
        def terms = new ArrayList<Matrix>()
        terms.addAll(matrix1, matrix2)
        double c = 0.0
        def answerComponents = [[c,c], [c,c]]
        def answerMatrix = new Matrix(answerComponents)

        expect: 'a matrix of the correct size to be returned'
        assert Matrix.subtract(terms).get().getComponents() == answerMatrix.getComponents()
    }

    def 'When passing a null argument to Matrix multiplication' () {

        expect: 'the return of an empty optional'
        assert Matrix.matrixMultiplication(null, null) == Optional.empty()
    }

    def 'When passing matrices of incorrect sizes to Matrix multiplication' () {

        given:
        double a = 1.0
        double b = 2.0
        double c = 0.0
        def components1 = [[a,b], [a,b]]
        def matrix1 = new Matrix(components1)
        def components2 = [[a,b,c], [a,b,c]]
        def matrix2 = new Matrix(components2)

        expect: 'the return of an empty optional'
        assert Matrix.matrixMultiplication(matrix1, matrix2) == Optional.empty()
    }

    def 'When passing matrices of the correct sizes to Matrix multiplication' () {

        given:
        double c = 2.0
        double d = -1.0
        double e = 1.0
        double f = 0.0
        double g = 3.0
        double h = 4.0
        double i = -2.0
        double j = 8.0
        double k = 5.0
        def components1 = [[c, d, e, d], [f, e, g, e], [e, f, e, e]]
        def a = new Matrix(components1)
        def components2 = [[c, f, e], [e, e, f], [c, f, e], [e, e, f]]
        def b = new Matrix(components2)
        def answerComponents = [[h, i, g], [j, c, g], [k, e, c]]
        def answerMatrix = new Matrix(answerComponents)

        expect: 'the return of a matrix with the correct elements'
        assert Matrix.matrixMultiplication(b, a).get().getComponents() == answerMatrix.getComponents()
    }

    def 'When passing a null argument to swap rows' () {

        expect: 'the return of an empty optional'
        assert Matrix.swapRows(null, 0, 1) == Optional.empty()
    }

    def 'When passing a negative index or equal indices to swap rows' () {

        given:
        double a = 0.0
        def matrix = new Matrix([[a,a], [a,a]])

        expect: 'the return of the given matrix'
        assert Matrix.swapRows(matrix, -1, 0).get().getComponents() == matrix.getComponents()
        assert Matrix.swapRows(matrix, 0, -1).get().getComponents() == matrix.getComponents()
        assert Matrix.swapRows(matrix, 0, 0).get().getComponents() == matrix.getComponents()
    }

    def 'When passing valid arguments to swap rows' () {

        given:
        double a = 0.0
        double b = 1.0
        def matrix = new Matrix([[b,a,a], [a,b,a], [a,a,b]])

        expect: 'the return of the appropriate matrix'
        assert Matrix.swapRows(matrix, 1, 0).get().getComponents() == [[a,b,a], [b,a,a], [a,a,b]]
    }
}
