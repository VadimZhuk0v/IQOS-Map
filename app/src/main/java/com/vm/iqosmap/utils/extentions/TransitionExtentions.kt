package com.vm.iqosmap.utils.extentions

import androidx.transition.Transition

fun Transition.addFinishListner(event: () -> Unit){
    this.addListener(object : Transition.TransitionListener {
        override fun onTransitionEnd(transition: Transition) {
            event()
        }

        override fun onTransitionResume(transition: Transition) {

        }

        override fun onTransitionPause(transition: Transition) {

        }

        override fun onTransitionCancel(transition: Transition) {

        }

        override fun onTransitionStart(transition: Transition) {

        }

    })
}