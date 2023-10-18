import 'dart:convert';
import 'dart:typed_data';

import 'package:flutter/material.dart';

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
  Image image = Image.memory(
    const Base64Codec().decode("R0lGODlhAQABAIAAAAAAAP///yH5BAEAAAAALAAAAAABAAEAAAIBRAA7"),
    height: 1,
  );
}
