package com.dodo.flutterbridge

import com.idlefish.flutterboost.FlutterBoost
import com.idlefish.flutterboost.FlutterBoostRouteOptions
import com.idlefish.flutterboost.containers.FlutterBoostFragment
import java.lang.reflect.Type

/**
 *     author : liuduo
 *     e-mail : liuduo@gyenno.com
 *     time   : 2022/07/05
 *     desc   : Flutter路由
 *     version: 1.0
 */

internal class FlutterRouter internal constructor(private val builder: BaseBuilder) {

    fun <C> navigateActivity(caller: C?): FlutterActivityRouterInfo<C> {
        val options = FlutterBoostRouteOptions.Builder()
            .pageName(builder.pageName)
            .arguments(builder.arguments)
            .requestCode(builder.requestCode)
            .build()
        FlutterBoost.instance().open(options)
        return FlutterActivityRouterInfo()
    }

    fun <F : FlutterBoostFragment, C> navigateFragment(caller: C?): FlutterFragmentRouterInfo<F, C> {
        val flutterFragment = FlutterBoostFragment.CachedEngineFragmentBuilder()
            .url(builder.pageName)
            .urlParams(builder.arguments)
            .build<F>()
        return FlutterFragmentRouterInfo(flutterFragment)
    }
}

abstract class BaseBuilder {
    companion object {
        fun activity() = ActivityBuilder<Nothing>()
        fun <F : FlutterBoostFragment> fragment() = FragmentBuilder<F, Nothing>()
    }

    internal lateinit var pageName: String
    internal var arguments: Map<String, Any> = mapOf()
    internal var requestCode = 0

    internal var routerType: FlutterRouterType? = null

    abstract fun pageName(pageName: String): BaseBuilder

    abstract fun arguments(arguments: Map<String, Any>): BaseBuilder

    abstract fun requestCode(requestCode: Int): BaseBuilder

    abstract fun <C> caller(caller: C): BaseBuilder

    abstract fun onCall(onCall: Type): BaseBuilder

    abstract fun navigate(): Any

    protected open fun copyFrom(builder: BaseBuilder) {
        this.routerType = builder.routerType
    }
}


class ActivityBuilder<C> : BaseBuilder() {
    internal var caller: C? = null

    init {
        routerType = FlutterRouterType.Activity
    }

    override fun <C> caller(caller: C): ActivityBuilder<C> {
        val activityBuilder = ActivityBuilder<C>()
        activityBuilder.copyFrom(this)
        return activityBuilder.also {
            it.caller = caller
        }
    }

    override fun onCall(onCall: Type): ActivityBuilder<C> {
        TODO("Not yet implemented")
    }

    override fun pageName(pageName: String): BaseBuilder {
        this.pageName = pageName
        return this
    }

    override fun arguments(arguments: Map<String, Any>): BaseBuilder {
        this.arguments = arguments
        return this
    }

    override fun requestCode(requestCode: Int): BaseBuilder {
        this.requestCode = requestCode
        return this
    }

    override fun navigate(): FlutterActivityRouterInfo<C> {
        return FlutterRouter(this).navigateActivity(caller)
    }
}

class FragmentBuilder<F : FlutterBoostFragment, C> : BaseBuilder() {
    internal var caller: C? = null

    init {

        routerType = FlutterRouterType.Fragment
    }

    override fun <C> caller(caller: C): FragmentBuilder<F, C> {
        val fragmentBuilder = FragmentBuilder<F, C>()
        fragmentBuilder.copyFrom(this)
        return fragmentBuilder.apply {
            this.caller = caller
        }
    }

    override fun onCall(onCall: Type): BaseBuilder {
        TODO("Not yet implemented")
    }

    override fun navigate(): FlutterFragmentRouterInfo<F, C> {
        return FlutterRouter(this).navigateFragment(caller)
    }

    override fun pageName(pageName: String): FragmentBuilder<F, C> {
        this.pageName = pageName
        return this
    }

    override fun arguments(arguments: Map<String, Any>): FragmentBuilder<F, C> {
        this.arguments = arguments
        return this
    }

    override fun requestCode(requestCode: Int): FragmentBuilder<F, C> {
        this.requestCode = requestCode
        return this
    }
}

open class FlutterRouterInfo<C> {
    var caller: C? = null

    fun requireCaller() = caller!!
}

class FlutterActivityRouterInfo<C> : FlutterRouterInfo<C>()

class FlutterFragmentRouterInfo<F, C>(var fragment: F) : FlutterRouterInfo<C>()

enum class FlutterRouterType {
    Activity, Fragment, View
}