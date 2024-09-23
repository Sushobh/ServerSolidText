package common.util.requests

import kotlinx.serialization.Serializable
import java.lang.Error

@kotlinx.serialization.Serializable
open class STResponse<X> {
    var body : X? = null
    var error : STErrorBody? = null

    constructor(body : X?,error: STErrorBody?){
        this.body = body
        this.error = error
    }
}

@Serializable
class STErrorBody(val message : String? = null,val status : String)