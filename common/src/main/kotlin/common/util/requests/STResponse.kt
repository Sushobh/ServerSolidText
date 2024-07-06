package common.util.requests

import java.lang.Error

open class STResponse<X> {
    var body : X? = null
    var error : STErrorBody? = null

    constructor(body : X?,error: STErrorBody?){
        this.body = body
        this.error = error
    }



}

class STErrorBody(val message : String? = null,val status : String)