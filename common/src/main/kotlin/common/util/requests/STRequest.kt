package common.util.requests

open class STRequest<X> {

    private var headers : Map<String,String> = hashMapOf()
    var requestBody: X?
       private set


    constructor(requestBody: X?){
        this.requestBody = requestBody
    }

    constructor(headers : Map<String,String>,requestBody: X?) : this(requestBody){
        this.headers = headers
        this.requestBody = requestBody
    }

    private val extras = hashMapOf<String,Any>()

    fun addExtra(key : String,value : Any){
        extras.put(key,value)
    }

    fun getHeader(key : String) : String? {
        return headers.get(key)
    }

    fun <M> getExtra(key : String) : M {
        val value = extras.get(key)
        return value as M
    }
}

