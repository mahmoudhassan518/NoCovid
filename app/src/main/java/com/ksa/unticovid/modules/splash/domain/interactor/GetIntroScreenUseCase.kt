package com.ksa.unticovid.modules.splash.domain.interactor

import com.ksa.unticovid.base.BaseUseCase
import com.ksa.unticovid.modules.user.domain.interactor.GetLocalUserUseCase
import com.mahmoud.myframework.modules.splash.domain.IntroScreen
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

class GetIntroScreenUseCase @Inject constructor(
    private val getLocalUserUseCase: GetLocalUserUseCase,
) : BaseUseCase<Unit, IntroScreen>() {

    override fun invoke(param: Unit): Flow<IntroScreen> =
        getLocalUserUseCase(Unit).flatMapLatest {
            flowOf(IntroScreen.HOME)
        }.catch {
            emit(IntroScreen.USER_MANAGEMENT)
        }
}
