package multidatasourcetest.sto

import grails.gorm.transactions.Transactional
import grails.testing.gorm.DataTest
import grails.testing.services.ServiceUnitTest
import org.grails.orm.hibernate.HibernateDatastore
import org.springframework.transaction.PlatformTransactionManager
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification

class UserServiceSpec extends Specification implements ServiceUnitTest<UserService>, DataTest {

    @Shared @AutoCleanup HibernateDatastore hibernateDatastore
    @Shared PlatformTransactionManager transactionManager

    void setupSpec() {
        Map configuration = [
                'hibernate.cache.use_second_level_cache':false,
                'dataSources.stoWrite.pooled':true,
                'dataSource.driverClassName':'org.postgresql.Driver',
                'dataSource.url':'jdbc:postgresql://127.0.0.1:5432/multidatasource',
                'dataSource.username':'test',
                'dataSource.password':'test'
        ]
        hibernateDatastore = new HibernateDatastore(configuration, User)
        transactionManager = hibernateDatastore.getTransactionManager()
    }

    @Transactional
    def setup() {
        new User(firstName: 'test first name', lastName: 'test last name').save() // works with default datasource not with stoWrite datasource
    }

    @Transactional
    def cleanup() {
        User.last().delete() // works with the default datasource not with stoWrite datasource
    }

    @Transactional
    void "test : allFirsts"() {
        when:
        def firsts = service.allFirsts()

        then:
        firsts.size() >= 1
    }

    @Transactional
    void "test : save"() {
        when:
        service.saveUser()
        println "size: ${User.getAll().size()}"

        then:
        User.getAll().size() >= 1
    }
}
