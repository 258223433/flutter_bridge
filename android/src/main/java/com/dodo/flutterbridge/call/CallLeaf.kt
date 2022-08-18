package com.dodo.flutterbridge.call

/**
 *     author : liuduo
 *     e-mail : liuduo@gyenno.com
 *     time   : 2022/08/16
 *     desc   :
 *     version: 1.0
 */
interface CallLeaf<S, P> : Handler<P>, InvokerNode<S,P>{
    /**
     * 和父节点连接
     * @param callRoot CallRoot<P, *>
     */
    fun linkParent(callRoot: CallRoot<P,*>){
        callRoot.addHandler(this)
        attachInvoker(callRoot)
    }

    /**
     * 和父节点断开连接
     * @param callRoot CallRoot<P, *>
     */
    fun unlinkParent(callRoot: CallRoot<P,*>){
        callRoot.removeHandler(this)
        detachInvoker(callRoot)
    }
}
