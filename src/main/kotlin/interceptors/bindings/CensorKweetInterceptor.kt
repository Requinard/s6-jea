package interceptors.bindings

import util.Open
import javax.interceptor.AroundInvoke
import javax.interceptor.Interceptor
import javax.interceptor.InvocationContext

@Open
@Interceptor
@CensorKweetInterceptorBinding
class CensorKweetInterceptor {
    val badWords = mapOf(
        "oracle" to "sodexo"
    )

    @AroundInvoke
    fun censor(ic: InvocationContext): Any {
        val string = ic.parameters[0] ?: return ic.proceed()

        if (string is String) {
            badWords.forEach { ic.parameters[0] = (ic.parameters[0] as String).replace(it.key, it.value) }
        }

        return ic.proceed()
    }
}
