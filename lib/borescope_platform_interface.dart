import 'package:plugin_platform_interface/plugin_platform_interface.dart';

import 'borescope_method_channel.dart';

abstract class BorescopePlatform extends PlatformInterface {
  /// Constructs a BorescopePlatform.
  BorescopePlatform() : super(token: _token);

  static final Object _token = Object();

  static BorescopePlatform _instance = MethodChannelBorescope();

  /// The default instance of [BorescopePlatform] to use.
  ///
  /// Defaults to [MethodChannelBorescope].
  static BorescopePlatform get instance => _instance;

  /// Platform-specific implementations should set this with their own
  /// platform-specific class that extends [BorescopePlatform] when
  /// they register themselves.
  static set instance(BorescopePlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Future<String?> getPlatformVersion() {
    throw UnimplementedError('platformVersion() has not been implemented.');
  }
}
