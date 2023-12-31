import 'dart:async';
import 'dart:convert';

import 'package:borescope/borescope.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

import 'borescope_platform_interface.dart';

class MethodChannelBorescope extends BorescopePlatform {
  /// The method channel used to interact with the native platform.
  @visibleForTesting
  final methodChannel = const MethodChannel('com.ibrascan.borescope/main');
  final eventChannel = const EventChannel('com.ibrascan.borescope/image');
  late StreamSubscription<dynamic> _eventSubscription;

  @override
  Future<String?> initBorescope(BorescopeController controller) async {
    if (controller.isRunning) {
      return "error borescope already running";
    }
    final result = await methodChannel.invokeMethod<String>('init');
    if (result == "success initialized") {
      controller.isRunning = true;
    }
    return result;
  }

  @override
  Future<String?> initStream(BorescopeController controller) async {
    if (!controller.isRunning) {
      return "error borescope not running";
    }
    try {
      final result = await methodChannel.invokeMethod<String>('openBorescope');
      _eventSubscription = eventChannel.receiveBroadcastStream().listen((event) {
        //Verify connection problems
        if (event == "noconnection" || event == "noimage" || event == "null") {
          //Create a blank image
          Uint8List blankBytes = const Base64Codec().decode("R0lGODlhAQABAIAAAAAAAP///yH5BAEAAAAALAAAAAABAAEAAAIBRAA7");
          controller.image = Image.memory(
            blankBytes,
            height: 1,
          );
        }
        //Transcode base64 into image
        else {
          List<int> bytes = base64Decode(event);
          controller.image = Image.memory(Uint8List.fromList(bytes));
        }
        controller.imageString = event;
        //Verify if imageChanged function is declarated
        if (controller.imageChanged == null) return;
        controller.imageChanged!();
      });
      return result;
    } catch (error) {
      return "error $error";
    }
  }

  @override
  Future<String?> verifySSID(BorescopeController controller) async {
    if (!controller.isRunning) {
      return "error borescope not initialized";
    }
    final result = await methodChannel.invokeMethod<String>('verifySSID');
    return result;
  }

  @override
  Future<String?> dispose(BorescopeController controller) async {
    if (!controller.isRunning) {
      return "error the borescope is not active";
    }
    if (!controller.isRunning) return "error the stream must be initialized";
    //Disable functions parameters to avoid errors in dispose
    controller.imageChanged = null;
    controller.runningChanged = null;
    final result = await methodChannel.invokeMethod<String>('destroy');
    _eventSubscription.cancel();
    return result;
  }
}
