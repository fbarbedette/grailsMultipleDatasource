package multidatasourcetest.sto

import grails.gorm.transactions.Transactional

@Transactional
class UserService {

    def allFirsts() {
        User.findAll()
    }

    def saveUser() {
        new User(firstName: 'Mario', lastName: 'Lopez').stoWrite.save() // in development works fine, in test no
    }
}
