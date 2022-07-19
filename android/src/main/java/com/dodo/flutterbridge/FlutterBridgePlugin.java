package com.dodo.flutterbridge;

import androidx.annotation.NonNull;

import com.idlefish.flutterboost.FlutterBoostPlugin;

import io.flutter.embedding.engine.plugins.FlutterPlugin;

/**
 * author : liuduo
 * e-mail : liuduo@gyenno.com
 * time   : 2022/07/19
 * desc   :
 * version: 1.0
 */
public class FlutterBridgePlugin implements FlutterPlugin {
    @Override
    public void onAttachedToEngine(@NonNull FlutterPluginBinding binding) {
        binding.getFlutterEngine().getPlugins().add(new FlutterBoostPlugin());
    }

    @Override
    public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
        binding.getFlutterEngine().getPlugins().remove(FlutterBoostPlugin.class);
    }
}
