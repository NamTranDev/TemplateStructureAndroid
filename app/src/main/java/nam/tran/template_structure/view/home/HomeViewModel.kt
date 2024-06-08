package nam.tran.template_structure.view.home

import android.os.Bundle
import dagger.hilt.android.lifecycle.HiltViewModel
import nam.tran.common.Logger
import nam.tran.data.usecase._qualifier.HomeUseCaseQualifier
import nam.tran.data.usecase.home.HomeUseCase
import nam.tran.ui.viewmodel.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    @HomeUseCaseQualifier val useCase: HomeUseCase
) : BaseViewModel() {
    override fun onLoad(bundle: Bundle?, isRefresh: Boolean) {
        execution(useCase.testFlow()) {
            Logger.debug(it)
        }
    }
}