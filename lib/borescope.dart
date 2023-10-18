import 'borescope_platform_interface.dart';

class Borescope {
  Future<String?> initBorescope(BorescopeController controller) {
    return BorescopePlatform.instance.initBorescope(controller);
  }

  Future<String?> initStream(BorescopeController controller) {
    return BorescopePlatform.instance.initStream(controller);
  }

  Future<String?> verifySSID() {
    return BorescopePlatform.instance.verifySSID();
  }

  Future<String?> dispose() {
    return BorescopePlatform.instance.dispose();
  }
}

class BorescopeController {
  String imageString = "noimage";
}
