package common.util.requests

open class STResponse<X> {
    var body : X? = null
    var error : STErrorBody? = null

    constructor(body : X){
        this.body = body
    }

    constructor(error : STErrorBody){
        this.error = error
    }
}

class STErrorBody(val message : String? = null,val status : String)