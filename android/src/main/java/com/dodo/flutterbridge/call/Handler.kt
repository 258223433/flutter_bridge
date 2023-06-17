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
     * @return Any
     * @throws HandlerNotFoundException
     * @throws MutableHandlerException
     */
    //由于早期版本的Android的StandardMessageCodec不支持写入null,所以没有定义为Any?,但是可以返回Unit来替代
    @Throws(HandlerNotFoundException::class, MutableHandlerException::class)
    fun onCall(data: P): Any
}