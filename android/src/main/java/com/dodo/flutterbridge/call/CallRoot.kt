package com.dodo.flutterbridge.call

/**
 *     author : liuduo
 *     e-mail : liuduo@gyenno.com
 *     time   : 2022/08/18
 *     desc   :
 *     version: 1.0
 */
interface CallRoot<S, P>:HandlerNode<S,P>,Invoker<S>{
    /**
     * 和子节点连接
     * @param callLeaf CallLeaf<*, S>
     */
    fun linkChild(callLeaf: CallLeaf<*,S>){
        callLeaf.attachInvoker(this)
        addHandler(callLeaf)
    }

    /**
     * 和子节点断开连接
     * @param callLeaf CallLeaf<*, S>
     */
    fun unlinkChild(callLeaf: CallLeaf<*,S>){
        callLeaf.detachInvoker(this)
        removeHandler(callLeaf)
    }
}