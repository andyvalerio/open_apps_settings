/* @Author Dennis Cai - Created 27-06-2024 */
package com.dcai.open_apps_settings

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import io.flutter.Log
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.FlutterPlugin.FlutterPluginBinding
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.PluginRegistry.ActivityResultListener

/**
 * OpenAppsSettingsPlugin
 */
class OpenAppsSettingsPlugin :
    FlutterPlugin,
    MethodCallHandler,
    ActivityResultListener,
    ActivityAware {
    /// The MethodChannel that will handle the communication between Flutter and native Android.
    ///
    /// This local reference serves to register the plugin with the Flutter Engine and unregister it
    /// when the Flutter Engine is detached from the Activity.
    private var channel: MethodChannel? = null
    private var activity: Activity? = null
    private var pendingResult: MethodChannel.Result? = null

    override fun onAttachedToEngine(flutterPluginBinding: FlutterPluginBinding) {
        channel = MethodChannel(flutterPluginBinding.binaryMessenger, "open_apps_settings")
        channel?.setMethodCallHandler(this)
    }

    override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {
        this.pendingResult = result

        if (call.method == "openSettings") {
            val settingCode = call.argument<String>("setting_code")

            when (settingCode) {
                "app_settings" -> openAppSettings()
                "wifi" -> openSettings(
                    Settings.ACTION_WIFI_SETTINGS,
                    RequestCodes.WIFI_SETTINGS
                )

                "bluetooth" -> openSettings(
                    Settings.ACTION_BLUETOOTH_SETTINGS,
                    RequestCodes.BLUETOOTH_SETTINGS
                )

                "accessibility" -> openSettings(
                    Settings.ACTION_ACCESSIBILITY_SETTINGS,
                    RequestCodes.ACCESSIBILITY_SETTINGS
                )

                "add_account" -> openSettings(
                    Settings.ACTION_ADD_ACCOUNT,
                    RequestCodes.ADD_ACCOUNT
                )

                "airplane_mode" -> openSettings(
                    Settings.ACTION_AIRPLANE_MODE_SETTINGS,
                    RequestCodes.AIRPLANE_MODE_SETTINGS
                )

                "apn" -> openSettings(
                    Settings.ACTION_APN_SETTINGS,
                    RequestCodes.APN_SETTINGS
                )

                "all_apps_settings" -> openSettings(
                    Settings.ACTION_APPLICATION_SETTINGS,
                    RequestCodes.APPLICATIONS_SETTINGS
                )

                "battery_saver" -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                    openSettings(
                        Settings.ACTION_BATTERY_SAVER_SETTINGS,
                        RequestCodes.BATTERY_SAVER_SETTINGS
                    )
                } else {
                    result.error(
                        "NOT SUPPORTED",
                        "Field requires API level 22 or higher",
                        null
                    )
                }

                "keyboard" -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    openSettings(
                        Settings.ACTION_HARD_KEYBOARD_SETTINGS,
                        RequestCodes.KEYBOARD_SETTINGS
                    )
                } else {
                    result.error(
                        "NOT SUPPORTED",
                        "Field requires API level 24 or higher",
                        null
                    )
                }

                "data_usage" -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    openSettings(
                        Settings.ACTION_DATA_USAGE_SETTINGS,
                        RequestCodes.DATA_USAGE_SETTINGS
                    )
                } else {
                    openSettings(
                        Settings.ACTION_DATA_ROAMING_SETTINGS,
                        RequestCodes.DATA_USAGE_SETTINGS
                    )
                }

                "date" -> openSettings(
                    Settings.ACTION_DATE_SETTINGS,
                    RequestCodes.DATE_SETTINGS
                )

                "device_info" -> openSettings(
                    Settings.ACTION_DEVICE_INFO_SETTINGS,
                    RequestCodes.DEVICE_INFO_SETTINGS
                )

                "display" -> openSettings(
                    Settings.ACTION_DISPLAY_SETTINGS,
                    RequestCodes.DISPLAY_SETTINGS
                )

                "home" -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    openSettings(
                        Settings.ACTION_HOME_SETTINGS,
                        RequestCodes.HOME_SETTINGS
                    )
                } else {
                    result.error("NOT SUPPORTED", "Field requires API level 21 or higher", null)
                }

                "internal_storage" -> openSettings(
                    Settings.ACTION_INTERNAL_STORAGE_SETTINGS,
                    RequestCodes.INTERNAL_STORAGE_SETTINGS
                )

                "fingerprint_enroll" -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    openSettings(
                        Settings.ACTION_FINGERPRINT_ENROLL,
                        RequestCodes.FINGERPRINT_ENROL
                    )
                } else {
                    result.error("NOT SUPPORTED", "Field requires API level 28 or higher", null)
                }

                "locale" -> openSettings(
                    Settings.ACTION_LOCALE_SETTINGS,
                    RequestCodes.LOCALE_SETTINGS
                )

                "location" -> openSettings(
                    Settings.ACTION_LOCATION_SOURCE_SETTINGS,
                    RequestCodes.LOCATION_SOURCE_SETTINGS
                )

                "privacy" -> openSettings(
                    Settings.ACTION_PRIVACY_SETTINGS,
                    RequestCodes.PRIVACY_SETTINGS
                )

                "battery_optimization" -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    openSettings(
                        Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS,
                        RequestCodes.BATTERY_OPTIMIZATION_SETTINGS
                    )
                } else {
                    result.error("NOT SUPPORTED", "Field required API level 23 or higher", null)
                }

                "nfc" -> openSettings(
                    Settings.ACTION_NFC_SETTINGS,
                    RequestCodes.NFC_SETTING
                )

                "sound" -> openSettings(
                    Settings.ACTION_SOUND_SETTINGS,
                    RequestCodes.SOUND_SETTINGS
                )

                "notification" -> openNotification()

                else -> result.notImplemented()
            }
        } else {
            result.notImplemented()
        }
    }

    override fun onDetachedFromEngine(binding: FlutterPluginBinding) {
        channel?.setMethodCallHandler(null)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?): Boolean {
        Log.e("OpenApps", "!!!!!!!!!!!!!!!!!!!!!!!! HALLOOOO HIER BEN IK")
        Log.e("OpenApps", "requestCode: $requestCode")

        if (RequestCodes.contains(requestCode)) {
            val result = pendingResult
            pendingResult = null

            if (result != null) {
                if (resultCode == Activity.RESULT_OK) {
                    result.success(requestCode.toString())
                }
            }
            return true
        }
        return false
    }

    override fun onAttachedToActivity(binding: ActivityPluginBinding) {
        this.activity = binding.activity
        binding.addActivityResultListener(this)
    }

    override fun onDetachedFromActivityForConfigChanges() {
        this.activity = null
    }

    override fun onReattachedToActivityForConfigChanges(binding: ActivityPluginBinding) {
        this.activity = binding.activity
        binding.addActivityResultListener(this)
    }

    override fun onDetachedFromActivity() {
        this.activity = null
    }

    // Private method to open Application specific setting
    private fun openAppSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        intent.setData(Uri.fromParts("package", activity?.packageName, null))
        activity?.startActivityForResult(intent, RequestCodes.APP_DETAIL_SETTINGS)
    }

    // Private method to open all settings
    private fun openSettings(intentString: String, permissionCode: Int) {
        try {
            val intent = Intent(intentString)
            activity?.startActivityForResult(intent, permissionCode)
        } catch (e: Exception) {
            openAppSettings()
        }
    }

    // Private method to open notification
    private fun openNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
            intent.putExtra(Settings.EXTRA_APP_PACKAGE, activity?.packageName)
            activity?.startActivityForResult(intent, RequestCodes.NOTIFICATION_SETTINGS)
        } else {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            intent.setData(Uri.parse("package:" + activity?.packageName))
            activity?.startActivityForResult(intent, RequestCodes.NOTIFICATION_SETTINGS)
        }
    }
}
