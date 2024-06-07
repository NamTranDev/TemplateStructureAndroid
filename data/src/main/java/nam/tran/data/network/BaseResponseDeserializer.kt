package nam.tran.data.network

import com.google.gson.*
import nam.tran.common.error.CommonErrorException
import nam.tran.data.model.response.BaseResponse
import java.lang.reflect.Type

class BaseResponseDeserializer : JsonDeserializer<BaseResponse> {

    companion object{
        fun unknowError(): CommonErrorException {
            return CommonErrorException()
        }
    }

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): BaseResponse {
        return BaseResponse()
    }
}