package com.dodo.flutterbridge

import android.app.Activity
import android.app.Application
import android.content.Intent
import com.dodo.flutterbridge.common.Constant
import com.dodo.flutterbridge.data.FlutterLiveData
import com.idlefish.flutterboost.FlutterBoost
import com.idlefish.flutterboost.FlutterBoostDelegate
import com.idlefish.flutterboost.FlutterBoostRouteOptions
import com.idlefish.flutterboost.containers.FlutterBoostActivity
import com.idlefish.flutterboost.containers.FlutterBoostFragment
import io.flutter.embedding.android.FlutterActivityLaunchConfigs
import java.util.concurrent.atomic.AtomicBoolean

/**
 *     author : liuduo
 *     e-mail : liuduo@gyenno.com
 *     time   : 2022/07/05
 *     desc   : Flutter初始化
 *     version: 1.0
 */

object FlutterBridge {

    /**
     * flutter的上下文信息
     */
    val flutterContext = FlutterContext

    /**
     * 防止多次初始化
     */
    private val hasInit = AtomicBoolean(false)

    /**
     * 路由列表，记录外部配置的路由
     */
    private val routeList =
        arrayListOf<(currentActivity: Activity, options: FlutterRouteOptions) -> Unit>()

    /**
     * flutter初始化
     * @param context Application
     * @param onInit Function0<Unit>? 初始化成功后调用
     * @return FlutterInitializer
     */
    fun init(context: Application, onInit: (() -> Unit)? = null): FlutterBridge {
        if (hasInit.compareAndSet(false, true)) {
            FlutterBoost.instance().setup(context, object : FlutterBoostDelegate {
                override fun pushNativeRoute(options: FlutterBoostRouteOptions) {

                    routeList.forEach { route ->
                        route(
                            FlutterBoost.instance().currentActivity(),
                            options.toFlutterRouteOptions()
                        )
                    }
                }

                override fun pushFlutterRoute(options: FlutterBoostRouteOptions) {
                    val intent: Intent = FlutterBoostActivity.CachedEngineIntentBuilder(
                        FlutterBoostActivity::class.java
                    ).backgroundMode(FlutterActivityLaunchConfigs.BackgroundMode.transparent)
                        .destroyEngineWithActivity(false)
                        .uniqueId(options.uniqueId())
                        .url(options.pageName())
                        .urlParams(options.arguments())
                        .build(FlutterBoost.instance().currentActivity())
                    FlutterBoost.instance().currentActivity()
                        .startActivityForResult(intent, options.requestCode())
                }
            }) { engine ->
                FlutterContext.globalEngine = engine
                FlutterContext.globalChannel =
                    FlutterMethodChannel(engine, Constant.Channel.GLOBAL_FLUTTER_CHANNEL_NAME)
                val ready =
                    FlutterLiveData(Constant.Method.FLUTTER_CHANNEL_METHOD_READY, clazz = Int::class.java)
                ready.observeForever {
                    onInit?.invoke()
                    ready.dispose()
                }
            }
        } else {
            throw IllegalStateException("FlutterInitializer 不能初始化两次")
        }
        return this
    }

    /**
     * 注册路由
     * @param router Function2<[@kotlin.ParameterName] Activity, [@kotlin.ParameterName] FlutterRouteOptions, Unit>
     */
    fun registerRoute(router: (currentActivity: Activity, options: FlutterRouteOptions) -> Unit) {
        routeList.add(router)
    }


    fun activity(): ActivityBuilder<Nothing> {
        return BaseBuilder.activity()
    }

    fun <F : FlutterBoostFragment> fragment(): FragmentBuilder<F, Nothing> {
        return BaseBuilder.fragment<F>()
    }
}

/**
 * 路由信息
 * @property pageName String
 * @property arguments Map<String, Any>?
 * @property requestCode Int
 * @property uniqueId String?
 * @property opaque Boolean
 * @constructor
 */
class FlutterRouteOptions(
    val pageName: String,
    val arguments: Map<String, Any>? = null,
    val requestCode: Int = 0,
    val uniqueId: String? = null,
    val opaque: Boolean = false,
)

internal fun FlutterBoostRouteOptions.toFlutterRouteOptions(): FlutterRouteOptions {
    return FlutterRouteOptions(pageName(), arguments(), requestCode(), uniqueId(), opaque())
}