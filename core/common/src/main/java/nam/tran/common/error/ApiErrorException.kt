package nam.tran.common.error

class ApiErrorException(val code: Int?, message: String?) : Exception(message ?: "Mã lỗi : $code")