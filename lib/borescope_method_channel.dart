import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

import 'borescope_platform_interface.dart';

/// An implementation of [BorescopePlatform] that uses method channels.
class MethodChannelBorescope extends BorescopePlatform {
  /// The method channel used to interact with the native platform.
  @visibleForTesting
  final methodChannel = const MethodChannel('borescope');

  @override
  Future<String?> getPlatformVersion() async {
    final version = await methodChannel.invokeMethod<String>('getPlatformVersion');
    return version;
  }
}
