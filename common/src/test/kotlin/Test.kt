import common.util.requests.STRequest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Test {

    class Boom {
       val watts  = 544
    }

    @Test
    fun justATest(){

        val stRequest = STRequest<String>("")
        stRequest.addExtra("key",Boom())

        val boom = stRequest.getExtra<Boom>("ke")

        Assertions.assertTrue(boom is Boom)

    }


}