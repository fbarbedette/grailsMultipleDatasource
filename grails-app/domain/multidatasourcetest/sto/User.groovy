package multidatasourcetest.sto

class User {

    String firstName
    String lastName

    static constraints = {
    }

    static mapping = {
        datasources (['DEFAULT', 'stoWrite'])

        id generator: 'sequence', params: [sequence_name: 'seq_user', schema: 'sto']

        table name: "user", schema: "sto"
    }
}
