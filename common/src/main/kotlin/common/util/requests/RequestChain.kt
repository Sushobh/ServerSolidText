package common.util.requests

open class RequestChain<X,Y> (private val requestBody : STRequest<X>) {

    private var items = arrayListOf<ChainItem<X, Y>>()
    private var currentIndex = 0
    fun addItem(chainItem: ChainItem<X, Y>) : RequestChain<X,Y>{
        items.add(chainItem)
        return this
    }

    suspend fun next() : Y {
        items.getOrNull(currentIndex++)?.let {
            return it.processRequest(requestBody,this)
        }
        throw Exception("Request chain out of bounds")
    }


    companion object {
        fun <X,Y > new(requestBody : STRequest<X>): RequestChain<X, Y> {
            return RequestChain(requestBody)
        }
    }
}


fun interface ChainItem<X,Y> {
    suspend fun processRequest(input : STRequest<X>,chain : RequestChain<X, Y>) : Y
}