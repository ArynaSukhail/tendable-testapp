package com.release.inspectionapp.utils.mvi

interface IView<S : IState> {
    fun render(state: S)
}