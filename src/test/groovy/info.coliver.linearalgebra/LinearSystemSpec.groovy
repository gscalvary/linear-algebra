package info.coliver.linearalgebra

import spock.lang.Shared
import spock.lang.Specification

class LinearSystemSpec extends Specification {

    @Shared ls
    @Shared lhs
    @Shared rhs
    @Shared pivot
    @Shared solvedLs

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
        rhs = new Matrix([[a,a]])

        when:
        new LinearSystem(lhs, rhs)

        then: 'an illegal argument exception should be thrown.'
        thrown IllegalArgumentException
    }

    def 'Given left and right hand sides of unequal height ' () {

        given:
        double a = 1.0
        lhs = new Matrix([[a,a], [a,a]])
        rhs = new Matrix([[a,a,a]])

        when:
        new LinearSystem(lhs, rhs)

        then: 'an illegal argument exception should be thrown.'
        thrown IllegalArgumentException
    }

    def 'Given pivot and right hand sides of unequal height ' () {

        given:
        double a = 1.0
        lhs = new Matrix([[a,a], [a,a]])
        rhs = new Matrix([[a,a]])
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
        rhs = new Matrix([[a,a]])
        pivot = new Vector([a,a,a])

        when:
        LinearSystem ls = new LinearSystem(lhs, rhs)
        ls.setPivot(pivot)

        then: 'an illegal argument exception should be thrown.'
        thrown IllegalArgumentException
    }

    def 'Given valid arguments to the constructor' () {

        given:
        double a = 1.0
        lhs = new Matrix([[a,a], [a,a]])
        rhs = new Matrix([[a,a]])

        when:
        LinearSystem ls = new LinearSystem(lhs, rhs)

        then: 'an illegal argument exception should not be thrown and the size should be correct.'
        notThrown IllegalArgumentException
        assert ls.getSize() == 2
    }

    def 'When passing a null argument to Gauss-Jordan transform' () {

        expect: 'the return of an empty optional'
        assert LinearSystem.gaussJordanTransform(null) == Optional.empty()
    }

    def 'When passing compatible arguments to Gauss-Jordan transform' () {

        given:
        double a = 0.0
        double b = 1.0
        double c = 2.0
        double d = 3.0
        double e = 7.0
        lhs = new Matrix([[b,d,b], [b,b,e], [c,e,b]])
        rhs = new Matrix([[-b,-e,e]])
        ls = new LinearSystem(lhs, rhs)
        def transformedLhs = new Matrix([[b,a,a], [a,b,a], [a,a,b]])
        def transformedRhs = new Matrix([[c,b,-c]])

        when: 'the system undergoes Gauss-Jordan transformation'
        solvedLs = LinearSystem.gaussJordanTransform(ls)

        then: 'the return of the linear system solution'
        assert solvedLs.get().getLhs().getComponents() == transformedLhs.getComponents()
        assert solvedLs.get().getRhs().getComponents() == transformedRhs.getComponents()
    }

    def 'When passing compatible arguments to Gauss-Jordan transform which require pivoting' () {

        given:
        double a = 0.0
        double b = 1.0
        double c = 2.0
        double f = 4.0
        double g = 8.0
        double h = 6.0
        lhs = new Matrix([[a,f,h], [f,g,-f], [-c,h,c]])
        rhs = new Matrix([[a,a,a], [a,a,a], [a,a,a]])
        ls = new LinearSystem(lhs, rhs)

        when: 'the system undergoes Gauss-Jordan transformation'
        solvedLs = LinearSystem.gaussJordanTransform(ls)

        then: 'the return of the linear system solution'
        assert solvedLs.get().getLhs().getComponents() == [[b, a, a], [a, b, a], [a, a, b]]
        assert solvedLs.get().getRhs().getComponents() == [[a, a, a], [a,a,a], [a,a,a]]
        assert solvedLs.get().getPivot().getComponents() == [b, a, a]
    }

    def 'When passing compatible arguments to Gauss-Jordan transform and the system has multiple right hand sides' () {

        given:
        double a = 0.0
        double b = 1.0
        double c = 2.0
        double d = 3.0
        double e = 4.0
        double f = 5.0
        double g = 7.0
        double h = 8.0
        double i = 9.0
        double j = 11.0
        double k = 13.0
        lhs = new Matrix([[-c,c,-e], [c,-d,d], [-f,g,-g]])
        rhs = new Matrix([[-g,j,-i], [h,-k,i]])
        ls = new LinearSystem(lhs, rhs)

        when: 'the system undergoes Gauss-Jordan transformation'
        solvedLs = LinearSystem.gaussJordanTransform(ls)

        then: 'the return of the linear system solution for all right hand sides'
        assert solvedLs.get().getLhs().getComponents() == [[b,a,a], [a,b,a], [a,a,b]]
        assert solvedLs.get().getRhs().getComponents() == [[-b,-c,b], [c,b,-c]]
    }
}
