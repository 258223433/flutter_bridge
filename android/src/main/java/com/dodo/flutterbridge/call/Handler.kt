package com.dodo.flutterbridge.call

import com.dodo.flutterbridge.call.exception.HandlerNotFoundException
import com.dodo.flutterbridge.call.exception.MutableHandlerException

/**
 *     author : liuduo
 *     e-mail : liuduo@gyenno.com
 *     time   : 2022/08/11
 *     desc   : 可以接收数据并返回结果的handler
 *     version: 1.0
 */

interface Handler<P> : Named {

    /**
     * 接收数据并返回结果
     * @param data P
     * @return Any?
     * @throws HandlerNotFoundException
     * @throws MutableHandlerException
     */
    @Throws(HandlerNotFoundException::class, MutableHandlerException::class)
    fun onCall(data: P): Any
}