package com.release.inspectionapp.utils

sealed class NavAction {
    data class ChangeGraph(val setGraph: Int) : NavAction()
    data class Forward(val navigate: Int) : NavAction()
    data class ForwardWithBundle(
        val navigate: Int,
        val bundle: Int
    ) : NavAction()
}