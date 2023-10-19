import 'package:flutter/material.dart';

import 'package:borescope/borescope.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({super.key});

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  final _borescopePlugin = Borescope();
  final _borescopeController = BorescopeController();

  //Debug
  String initResult = "Init Result";
  String streamResult = "Stream Result";
  String ssidResult = "SSID Result";
  String disposeResult = "Dispose Result";

  //Initialization
  void initBorescope() async {
    late final String? result;
    try {
      result = await _borescopePlugin.initBorescope(_borescopeController);
    } catch (error) {
      result = error.toString();
    }
    setState(() {
      initResult = result!;
    });
  }

  void streamBorescope() async {
    late final String? result;
    try {
      result = await _borescopePlugin.initStream(_borescopeController);
    } catch (error) {
      result = error.toString();
    }
    setState(() {
      disposeResult = "Dispose Result";
      streamResult = result!;
    });
  }

  void verifySSID() async {
    late final String? result;
    try {
      result = await _borescopePlugin.verifySSID(_borescopeController);
    } catch (error) {
      result = error.toString();
    }
    setState(() {
      ssidResult = result!;
    });
  }

  void disposeBorescope() async {
    late final String? result;
    try {
      result = await _borescopePlugin.dispose(_borescopeController);
    } catch (error) {
      result = error.toString();
    }
    setState(() {
      if (!result!.contains("error the borescope is not active")) streamResult = "closed";
      disposeResult = result;
    });
  }

  @override
  void dispose() {
    super.dispose();
    disposeBorescope();
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
          appBar: AppBar(
            title: const Text('Plugin example app'),
          ),
          body: SingleChildScrollView(
            child: Column(
              children: [
                //Init Result
                Center(
                  child: Text(initResult),
                ),
                //Init Button
                Center(
                  child: ElevatedButton(
                    onPressed: () => initBorescope(),
                    child: const Text("Init Borescope"),
                  ),
                ),
                //SSID Result
                Center(
                  child: Text(ssidResult),
                ),
                //SSID Button
                Center(
                  child: ElevatedButton(
                    onPressed: () => verifySSID(),
                    child: const Text("Verify SSID"),
                  ),
                ),
                //Stream Result
                Center(
                  child: Text(streamResult),
                ),
                //Stream Button
                Center(
                  child: ElevatedButton(
                    onPressed: () => streamBorescope(),
                    child: const Text("Stream Borescope"),
                  ),
                ),
                //Dipose Result
                Center(
                  child: Text(disposeResult),
                ),
                //Dispose Button
                Center(
                  child: ElevatedButton(
                    onPressed: () => disposeBorescope(),
                    child: const Text("Dispose Borescope"),
                  ),
                ),
                //Image Base64
                Center(child: Text(_borescopeController.imageString)),
              ],
            ),
          )),
    );
  }
}
