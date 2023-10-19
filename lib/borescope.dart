import 'dart:convert';

import 'package:flutter/material.dart';

import 'borescope_platform_interface.dart';

class Borescope {
  Future<String?> initBorescope(BorescopeController controller) {
    return BorescopePlatform.instance.initBorescope(controller);
  }

  Future<String?> initStream(BorescopeController controller) {
    return BorescopePlatform.instance.initStream(controller);
  }

  Future<String?> verifySSID(BorescopeController controller) {
    return BorescopePlatform.instance.verifySSID(controller);
  }

  Future<String?> dispose(BorescopeController controller) {
    return BorescopePlatform.instance.dispose(controller);
  }
}

class BorescopeController {
  //Borescope
  final Borescope _borescope = Borescope();
  //Handler Functions
  Function? runningChanged;
  Function? imageChanged;
  //Handler Parameters
  bool isRunning = false;
  String imageString = "noimage";
  Image image = Image.memory(
    const Base64Codec().decode("R0lGODlhAQABAIAAAAAAAP///yH5BAEAAAAALAAAAAABAAEAAAIBRAA7"),
    height: 1,
  );

  //Simple init Borescope
  Future<String> initBorescope() async {
    String conditions(List<String?> condition) {
      String errors = "";
      //Swipe conditions
      for (int i = 0; i < condition.length; i++) {
        //Check nullable
        if (condition[i] == null) errors = "$errors null";
        //Check errors
        if (condition[i]!.contains("error")) {
          errors = "$errors ${condition[i]!}";
        }
      }
      //Return
      if (errors.isEmpty) return "success";
      return errors;
    }

    List<String?> result = [];
    //Initializing Plugin
    result.add(await _borescope.initBorescope(this));
    //Verifing SSID
    result.add(await _borescope.verifySSID(this));
    //Starting Stream
    result.add(await _borescope.initStream(this));
    //Check for errors
    final condition = conditions(result);
    if (condition != "success") {
      return condition;
    }
    //Running
    if (runningChanged == null) return "success";
    runningChanged!();
    return "success";
  }

  //Dipose Borescope
  disposeBorescope() {
    _borescope.dispose(this);
  }

  BorescopeController({this.runningChanged, this.imageChanged});
}
