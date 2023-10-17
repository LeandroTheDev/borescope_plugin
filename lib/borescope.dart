
import 'borescope_platform_interface.dart';

class Borescope {
  Future<String?> getPlatformVersion() {
    return BorescopePlatform.instance.getPlatformVersion();
  }
}
