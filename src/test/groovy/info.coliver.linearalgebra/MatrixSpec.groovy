package info.coliver.linearalgebra

import spock.lang.Shared
import spock.lang.Specification

class MatrixSpec extends Specification {

    @Shared matrix
    @Shared components

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
}
