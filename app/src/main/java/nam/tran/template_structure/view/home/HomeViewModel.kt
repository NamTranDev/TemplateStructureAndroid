package nam.tran.template_structure.view.home

import androidx.lifecycle.ViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import nam.tran.data.usecase._qualifier.HomeUseCaseQualifier
import nam.tran.data.usecase.home.HomeUseCase
import javax.inject.Inject

@ViewModelScoped
class HomeViewModel @Inject constructor(
    @HomeUseCaseQualifier useCase : HomeUseCase
) : ViewModel()