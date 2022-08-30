package com.dodo.flutterbridge.call

/**
 *     author : liuduo
 *     e-mail : liuduo@gyenno.com
 *     time   : 2022/08/17
 *     desc   : 可以挂载父invoker
 *     version: 1.0
 */
interface InvokeAttachable<P> {

    /**
     * 挂载invoker
     * @param invoker Invoker<P>
     */
    fun attachInvoker(invoker: Invoker<P>)

    /**
     * 卸载invoker
     * @param invoker Invoker<P>
     */
    fun detachInvoker(invoker: Invoker<P>)
}