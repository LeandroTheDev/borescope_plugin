import 'package:borescope/borescope.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

import 'borescope_platform_interface.dart';

class MethodChannelBorescope extends BorescopePlatform {
  /// The method channel used to interact with the native platform.
  @visibleForTesting
  final methodChannel = const MethodChannel('com.ibrascan.borescope/main');
  final eventChannel = const EventChannel('com.ibrascan.borescope/image');

  //Handler
  bool isRunning = false;

  @override
  Future<String?> initBorescope(BorescopeController controller) async {
    final result = await methodChannel.invokeMethod<String>('init');
    if (result == "success initialized") {
      isRunning = true;
    }
    return result;
  }

  @override
  Future<String?> initStream(BorescopeController controller) async {
    if (!isRunning) {
      return "error borescope not running";
    }
    try {
      eventChannel.receiveBroadcastStream().listen((event) {
        controller.imageString = event;
      });
      return "success stream";
    } catch (error) {
      return error.toString();
    }
  }

  @override
  Future<String?> verifySSID() async {
    final result = await methodChannel.invokeMethod<String>('verifySSID');
    return result;
  }

  @override
  Future<String?> dispose() async {
    final result = await methodChannel.invokeMethod<String>('destroy');
    return result;
  }
}
