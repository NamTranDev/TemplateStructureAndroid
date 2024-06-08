package nam.tran.data.usecase.home

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import nam.tran.data.network.api.ApiServices
import java.util.concurrent.TimeoutException
import javax.inject.Inject
import kotlin.time.Duration

class HomeUseCaseImpl @Inject constructor(apiServices: ApiServices) : HomeUseCase {
    override fun testFlow(): Flow<Boolean> {
        return flow {
            delay(5000)
            throw TimeoutException()
        }
    }
}