package com.dodo.flutterbridge.call

/**
 *     author : liuduo
 *     e-mail : liuduo@gyenno.com
 *     time   : 2022/08/17
 *     desc   :
 *     version: 1.0
 */
interface InvokeAttachable<P> {
    fun attachInvoker(invoker: Invoker<P>)
    fun detachInvoker(invoker: Invoker<P>)
}