# open_apps_settings

A Flutter Plugin for opening phone settings from Android and iOS apps. It is a fully customizable plugin that allows you to execute functions when returning from the app setting page.

This fork fixes a bug in the forked repository that caused the plugin to send a `result.success()` to all MethodChannels, instead of only its own.

## Installation

Add following dependency in pubspec.yaml file:

```yaml
open_apps_settings: ^0.2.0
```

Install by running:

```bash
flutter pub get 
```

## Usage

Usage implementation using a ElevatedButton 'onPressed' event.

```Dart
import 'package:open_apps_settings/open_apps_settings.dart';
import 'package:open_apps_settings/settings_enum.dart';

Widget build(BuildContext context) {
  return Row(
    children: <Widget>[
      ElevatedButton(onPressed: () {
        OpenAppsSettings.openAppsSettings(settingsCode: SettingsCode.APP_SETTINGS,
          onCompletion: _function);},
          child: Text("OPEN APP SETTINGS")),
    ],
  );
}
```

openAppsSettings function takes 2 arguments. The first one is required and accepts the Setting code. The second is optional and accepts a function. The function will be executed after returning from the settings page.

```Dart
Function _function = () {
  print("do stuff here After returning back to setting page!");
};
```

iOS only supports single settings pages instead of custom setting pages.

## SettingsCode

```Dart
 enum SettingsCode {
    APP_SETTINGS,
    BLUETOOTH,
    WIFI,
    ACCESSIBILITY,
    ADD_ACCOUNT,
    AIRPLANE_MODE,
    APN,
    ALL_APPS_SETTINGS,
    BATTERY_SAVER,
    KEYBOARD,
    DATA_USAGE,
    DATE,
    DEVICE_INFO,
    DISPLAY,
    HOME,
    INTERNAL_STORAGE,
    FINGERPRINT_ENROLL,
    LOCALE,
    LOCATION,
    PRIVACY,
    BATTERY_OPTIMIZATION,
    NFC,
    SOUND,
    NOTIFICATION,
}
```

## Contributing

Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

## License

[MIT](https://choosealicense.com/licenses/mit/)
