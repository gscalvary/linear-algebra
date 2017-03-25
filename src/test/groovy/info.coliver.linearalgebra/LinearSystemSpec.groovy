package info.coliver.linearalgebra

import spock.lang.Shared
import spock.lang.Specification

class LinearSystemSpec extends Specification {

    @Shared lhs
    @Shared rhs

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

    def 'Given valid arguments ' () {

        given:
        double a = 1.0
        lhs = new Matrix([[a,a], [a,a]])
        rhs = new Vector([a,a])

        when:
        new LinearSystem(lhs, rhs)

        then: 'an illegal argument exception should not be thrown.'
        notThrown IllegalArgumentException
    }
}
