package com.dodo.flutterbridge.call

/**
 *     author : liuduo
 *     e-mail : liuduo@gyenno.com
 *     time   : 2022/08/16
 *     desc   :
 *     version: 1.0
 */
interface CallChild<S, P> : CallHandler<P>, CallInvoker<S, P> {

    fun attach(parent: CallGroup<P, *>) {
        this.parent = parent
        parent.addCallHandler(this)
    }

    override
    fun detach() {
        if (parent is CallHandleable<*>) {
            (parent as CallHandleable<P>?)?.removeCallHandler(this)
        }
        super.detach()
    }
}
