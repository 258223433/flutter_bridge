import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_boost/flutter_boost.dart';

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  Map<String, FlutterBoostRouteFactory?> routerMap = {
    '/': (settings, uniqueId) {
      return MaterialPageRoute(
          settings: settings,
          builder: (_) {
            return MainPage(
              data: '/',
            );
          });
    },
    'mainPage': (settings, uniqueId) {
      return MaterialPageRoute(
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
      return MaterialPageRoute(
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
  };

  Route<dynamic>? routeFactory(RouteSettings settings, String? uniqueId) {
    FlutterBoostRouteFactory? func = routerMap[settings.name];
    return func != null ? func(settings, uniqueId) : null;
  }

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
    return FlutterBoostApp(
      routeFactory,
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
                BoostNavigator.instance.push("nextActivity");
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
            Text('SimplePage $data'),
            TextButton(
              onPressed: () {
                BoostNavigator.instance.push("nextActivity");
              },
              child: const Text('Next'),
            ),
          ],
        ),
      ),
    );
  }
}
