package com.dodo.flutterbridge.call

/**
 *     author : liuduo
 *     e-mail : liuduo@gyenno.com
 *     time   : 2022/08/11
 *     desc   : 可以向上传递并且可以向下传递的双向节点
 *     version: 1.0
 */
interface CallNode<S, P> : CallRoot<S, P>, CallLeaf<S, P>