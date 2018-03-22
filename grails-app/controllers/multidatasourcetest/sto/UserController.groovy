package multidatasourcetest.sto

class UserController {

    def userService

    def index() {
        userService.saveUser()
        def users = userService.allFirsts()
        [users: users]
    }
}
