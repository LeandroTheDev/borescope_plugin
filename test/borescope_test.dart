import 'package:flutter_test/flutter_test.dart';
import 'package:borescope/borescope.dart';
import 'package:borescope/borescope_platform_interface.dart';
import 'package:borescope/borescope_method_channel.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

class MockBorescopePlatform
    with MockPlatformInterfaceMixin
    implements BorescopePlatform {

  @override
  Future<String?> getPlatformVersion() => Future.value('42');
}

void main() {
  final BorescopePlatform initialPlatform = BorescopePlatform.instance;

  test('$MethodChannelBorescope is the default instance', () {
    expect(initialPlatform, isInstanceOf<MethodChannelBorescope>());
  });

  test('getPlatformVersion', () async {
    Borescope borescopePlugin = Borescope();
    MockBorescopePlatform fakePlatform = MockBorescopePlatform();
    BorescopePlatform.instance = fakePlatform;

    expect(await borescopePlugin.getPlatformVersion(), '42');
  });
}
