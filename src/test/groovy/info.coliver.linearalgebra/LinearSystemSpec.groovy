package info.coliver.linearalgebra

import spock.lang.Shared
import spock.lang.Specification

class LinearSystemSpec extends Specification {

    @Shared ls
    @Shared lhs
    @Shared rhs
    @Shared pivot

    def 'Given a null left hand or right hand side ' () {

        given:
        lhs = null
        rhs = null

        when:
        new LinearSystem(lhs, rhs)

        then: 'an illegal argument exception to be thrown.'
        thrown IllegalArgumentException
    }

    def 'Given a left hand side of unequal height and width ' () {

        given:
        double a = 1.0
        lhs = new Matrix([[a,a], [a,a], [a,a]])
        rhs = new Vector([a,a])

        when:
        new LinearSystem(lhs, rhs)

        then: 'an illegal argument exception should be thrown.'
        thrown IllegalArgumentException
    }

    def 'Given left and right hand sides of unequal height ' () {

        given:
        double a = 1.0
        lhs = new Matrix([[a,a], [a,a]])
        rhs = new Vector([a,a,a])

        when:
        new LinearSystem(lhs, rhs)

        then: 'an illegal argument exception should be thrown.'
        thrown IllegalArgumentException
    }

    def 'Given pivot and right hand sides of unequal height ' () {

        given:
        double a = 1.0
        lhs = new Matrix([[a,a], [a,a]])
        rhs = new Vector([a,a])
        pivot = new Vector([a,a,a])

        when:
        new LinearSystem(lhs, rhs, pivot)

        then: 'an illegal argument exception should be thrown.'
        thrown IllegalArgumentException
    }

    def 'When setting the pivot of a system to a pivot of unequal size' () {

        given:
        double a = 1.0
        lhs = new Matrix([[a,a], [a,a]])
        rhs = new Vector([a,a])
        pivot = new Vector([a,a,a])

        when:
        LinearSystem ls = new LinearSystem(lhs, rhs)
        ls.setPivot(pivot)

        then: 'an illegal argument exception should be thrown.'
        thrown IllegalArgumentException
    }

    def 'Given valid arguments ' () {

        given:
        double a = 1.0
        lhs = new Matrix([[a,a], [a,a]])
        rhs = new Vector([a,a])

        when:
        LinearSystem ls = new LinearSystem(lhs, rhs)

        then: 'an illegal argument exception should not be thrown and the size should be correct.'
        notThrown IllegalArgumentException
        assert ls.getSize() == 2
    }

    def 'When passing a null argument to Gaussian transform' () {

        expect: 'the return of an empty optional'
        assert LinearSystem.gaussianTransform(null) == Optional.empty()
    }

    def 'When passing compatible arguments to Gaussian transform' () {

        given:
        double a = 0.0
        double b = 1.0
        double c = 2.0
        double d = 3.0
        double e = 7.0
        double f = 4.0
        lhs = new Matrix([[b,d,b], [b,b,e], [c,e,b]])
        rhs = new Vector([-b,-e,e])
        ls = new LinearSystem(lhs, rhs)
        def transformedLhs = new Matrix([[b,a,a], [b,-c,a], [c,b,c]])
        def transformedRhs = new Vector([-b,-f,-f])

        expect: 'the return of the equivalent upper triangular system'
        assert LinearSystem.gaussianTransform(ls).get().getLhs().getComponents() == transformedLhs.getComponents()
        assert LinearSystem.gaussianTransform(ls).get().getRhs().getComponents() == transformedRhs.getComponents()
    }

    def 'When passing compatible arguments to Gaussian transform which require pivoting' () {

        given:
        double a = 0.0
        double b = 1.0
        double c = 2.0
        double f = 4.0
        double g = 8.0
        double h = 6.0
        double i = 15.0
        lhs = new Matrix([[a,f,h], [f,g,-f], [-c,h,c]])
        rhs = new Vector([a,a,a])
        ls = new LinearSystem(lhs, rhs)

        expect: 'the return of the equivalent upper triangular system'
        assert LinearSystem.gaussianTransform(ls).get().getLhs().getComponents() == [[f,a,a], [g,f,a], [h,-c,-i]]
        assert LinearSystem.gaussianTransform(ls).get().getRhs().getComponents() == [a,a,a]
        assert LinearSystem.gaussianTransform(ls).get().getPivot().getComponents() == [b,a,a]
    }
}
