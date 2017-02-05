package info.coliver.linearalgebra

import spock.lang.Shared
import spock.lang.Specification

class VectorSpec extends Specification {

    @Shared vector
    @Shared components

    def setup() {

        def xComponent = new Integer(0)
        def yComponent = new Integer(0)
        def zComponent = new Integer(1)
        components = new ArrayList<Integer>()
        components.add(xComponent)
        components.add(yComponent)
        components.add(zComponent)

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
        def xComponent = new Integer(1)
        def yComponent = new Integer(0)
        def zComponent = new Integer(0)
        def newComponents = new ArrayList<Integer>()
        newComponents.add(xComponent)
        newComponents.add(yComponent)
        newComponents.add(zComponent)

        when: 'setting those components'
        vector.setComponents(newComponents)

        then: 'the vector is updated with those components.'
        assert newComponents == vector.getComponents()
    }

    def 'Vectors may be tested for equality' () {
        given: 'a vector with the same components as another'
        def xComponent = new Integer(0)
        def yComponent = new Integer(0)
        def zComponent = new Integer(1)
        def newComponents = new ArrayList<Integer>()
        newComponents.add(xComponent)
        newComponents.add(yComponent)
        newComponents.add(zComponent)
        def equalVector = new Vector(newComponents)

        expect: 'those vectors should be equal.'
        assert equalVector.equals(vector)
    }

    def 'Vectors with a different number of components are unequal' () {
        given: 'a vector with different components from another'
        def wComponent = new Integer(0)
        def xComponent = new Integer(0)
        def yComponent = new Integer(1)
        def zComponent = new Integer(0)
        def newComponents = new ArrayList<Integer>()
        newComponents.add(wComponent)
        newComponents.add(xComponent)
        newComponents.add(yComponent)
        newComponents.add(zComponent)
        def unequalVector = new Vector(newComponents)

        expect: 'those vectors should not be equal.'
        assert !unequalVector.equals(vector)
    }

    def 'Vectors with components of different values are unequal' () {
        given: 'a vector with different components from another'
        def xComponent = new Integer(0)
        def yComponent = new Integer(1)
        def zComponent = new Integer(0)
        def newComponents = new ArrayList<Integer>()
        newComponents.add(xComponent)
        newComponents.add(yComponent)
        newComponents.add(zComponent)
        def unequalVector = new Vector(newComponents)

        expect: 'those vectors should not be equal.'
        assert !unequalVector.equals(vector)
    }

    def 'Vectors may be assigned to one another' () {
        given: 'a new vector'
        def wComponent = new Integer(0)
        def xComponent = new Integer(0)
        def yComponent = new Integer(1)
        def zComponent = new Integer(0)
        def newComponents = new ArrayList<Integer>()
        newComponents.add(wComponent)
        newComponents.add(xComponent)
        newComponents.add(yComponent)
        newComponents.add(zComponent)
        def newVector = new Vector(newComponents)

        when: 'assigning that new vector to an older vector'
        vector.assign(newVector)

        then: 'the two vectors should be equal.'
        assert vector.equals(newVector)
    }
}
