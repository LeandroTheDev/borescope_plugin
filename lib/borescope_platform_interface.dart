import 'package:borescope/borescope.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

import 'borescope_method_channel.dart';

abstract class BorescopePlatform extends PlatformInterface {
  //Declarations
  BorescopePlatform() : super(token: _token);
  static final Object _token = Object();
  static BorescopePlatform _instance = MethodChannelBorescope();
  static BorescopePlatform get instance => _instance;

  //Plugin Instanciation
  static set instance(BorescopePlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Future<String?> initBorescope(BorescopeController controller) {
    throw UnimplementedError('initBorescope not implemented');
  }

  Future<String?> initStream(BorescopeController controller) {
    throw UnimplementedError('initStream not implemented');
  }

  Future<String?> verifySSID(BorescopeController controller) {
    throw UnimplementedError('verifySSID not implemented');
  }

  Future<String?> dispose(BorescopeController controller) {
    throw UnimplementedError('dispose not implemented');
  }
}
