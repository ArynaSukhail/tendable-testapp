package com.release.domain.usecase

abstract class BaseUseCase<out Type, in Params> where Type : Any {

    abstract suspend fun execute(params: Params): Type
}

object None
