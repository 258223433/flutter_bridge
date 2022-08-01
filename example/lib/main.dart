import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bridge/flutter_bridge.dart';
import 'package:flutter_boost/flutter_boost.dart';
import 'package:provider/provider.dart';

class UserInfo {
  var name = "test";
  var count = 0;

  UserInfo(this.name, this.count);

  factory UserInfo.fromJson(Map<String, dynamic> json) {
    return UserInfo(
      json['name'] as String,
      json['count'] as int,
    );
  }

  Map<String, dynamic> toJson() => <String, dynamic>{
        'name': name,
        'count': count,
      };
}

void main() {
  FlutterBridge.instance().init().registerRoute({
    '/': (settings, uniqueId) {
      return CupertinoPageRoute(
          settings: settings,
          builder: (_) {
            Map<String, dynamic> map =
                settings.arguments as Map<String, dynamic>;
            String data = map['data'] as String;
            return MainPage(
              data: data,
            );
          });
    },
    'mainPage': (settings, uniqueId) {
      return CupertinoPageRoute(
          settings: settings,
          builder: (_) {
            Map<String, dynamic> map =
                settings.arguments as Map<String, dynamic>;
            String data = map['data'] as String;
            return MainPage(
              data: data,
            );
          });
    },
    'simplePage': (settings, uniqueId) {
      return CupertinoPageRoute(
          settings: settings,
          builder: (_) {
            Map<String, dynamic> map =
                settings.arguments as Map<String, dynamic>;
            String data = map['data'] as String;
            return SimplePage(
              data: data,
            );
          });
    },
  });
  PageVisibilityBinding.instance.addGlobalObserver(AppLifecycleObserver());
  runApp(MyApp());
}

///全局生命周期监听示例
class AppLifecycleObserver with GlobalPageVisibilityObserver {
  @override
  void onBackground(Route route) {
    super.onBackground(route);
    print("AppLifecycleObserver - onBackground");
  }

  @override
  void onForeground(Route route) {
    super.onForeground(route);
    print("AppLifecycleObserver - onForground");
  }

  @override
  void onPagePush(Route route) {
    super.onPagePush(route);
    print("AppLifecycleObserver - onPagePush");
  }

  @override
  void onPagePop(Route route) {
    super.onPagePop(route);
    print("AppLifecycleObserver - onPagePop");
  }

  @override
  void onPageHide(Route route) {
    super.onPageHide(route);
    print("AppLifecycleObserver - onPageHide");
  }

  @override
  void onPageShow(Route route) {
    super.onPageShow(route);
    print("AppLifecycleObserver - onPageShow");
  }
}

class MyApp extends StatelessWidget {
  Widget appBuilder(Widget home) {
    return MaterialApp(
      home: home,
      debugShowCheckedModeBanner: true,

      ///必须加上builder参数，否则showDialog等会出问题
      builder: (_, __) {
        return home;
      },
    );
  }

  @override
  Widget build(BuildContext context) {
    return FlutterBridgeApp(
      appBuilder: appBuilder,
    );
  }
}

class MainPage extends StatelessWidget {
  Object data;

  MainPage({required this.data});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Text('Main Page $data'),
            TextButton(
              onPressed: () {
                BoostNavigator.instance.push("simplePage",
                    arguments: {"data": "arguments form flutter"});
              },
              child: const Text('Next'),
            ),
          ],
        ),
      ),
    );
  }
}

class SimplePage extends StatelessWidget {
  Object data;

  SimplePage({required this.data});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            ChangeNotifierProvider<NativeData<UserInfo?>>(
              create: (_) => NativeData<UserInfo?>("count", null, (json) {
                return UserInfo.fromJson(json);
              }),
              child: Column(
                children: [
                  Consumer<NativeData<UserInfo?>>(
                    builder: (context, notifier, child) {
                      return Text("${notifier.value?.name}");
                    },
                  ),
                  Consumer<NativeData<UserInfo?>>(
                    builder: (context, notifier, child) {
                      return Text("${notifier.value?.count}");
                    },
                  ),
                  Consumer<NativeData<UserInfo?>>(
                    builder: (context, notifier, child) {
                      return TextButton(
                        onPressed: () {
                          if (notifier.value != null) {
                            var value  = notifier.value!;
                            var count = value.count + 100;
                            notifier.value = UserInfo(value.name, count);
                          }
                        },
                        child: const Text('+100'),
                      );
                    },
                  ),
                ],
              ),
            ),
            Text('SimplePage $data'),
            TextButton(
              onPressed: () {
                BoostNavigator.instance
                    .push("mainActivity")
                    .then((value) => print('yyzddd:$value'));
              },
              child: const Text('Next'),
            ),
          ],
        ),
      ),
    );
  }
}
