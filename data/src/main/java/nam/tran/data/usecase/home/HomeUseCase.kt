package nam.tran.data.usecase.home

import kotlinx.coroutines.flow.Flow

interface HomeUseCase{
    fun testFlow() : Flow<Boolean>
}