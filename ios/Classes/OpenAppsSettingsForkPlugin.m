#import "OpenAppsSettingsForkPlugin.h"
#if __has_include(<open_apps_settings_fork/open_apps_settings_fork-Swift.h>)
#import <open_apps_settings_fork/open_apps_settings_fork-Swift.h>
#else
// Support project import fallback if the generated compatibility header
// is not copied when this plugin is created as a library.
// https://forums.swift.org/t/swift-static-libraries-dont-copy-generated-objective-c-header/19816
#import "open_apps_settings_fork-Swift.h"
#endif

@implementation OpenAppsSettingsForkPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftOpenAppsSettingsForkPlugin registerWithRegistrar:registrar];
}
@end
