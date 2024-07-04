/* @Author Dennis Cai - Created 27-06-2024 */
package com.bdrthermea.open_apps_settings

object RequestCodes {
    const val APP_DETAIL_SETTINGS: Int = 101
    const val GENERAL_SETTINGS: Int = 102
    const val WIFI_SETTINGS: Int = 103
    const val BLUETOOTH_SETTINGS: Int = 104
    const val ACCESSIBILITY_SETTINGS: Int = 105
    const val ADD_ACCOUNT: Int = 106
    const val AIRPLANE_MODE_SETTINGS: Int = 107
    const val APN_SETTINGS: Int = 108
    const val APPLICATIONS_SETTINGS: Int = 109
    const val APP_SETTINGS: Int = 110
    const val BATTERY_SAVER_SETTINGS: Int = 111
    const val KEYBOARD_SETTINGS: Int = 112
    const val DATA_USAGE_SETTINGS: Int = 113
    const val DATE_SETTINGS: Int = 114
    const val DEVICE_INFO_SETTINGS: Int = 115
    const val DISPLAY_SETTINGS: Int = 116
    const val HOME_SETTINGS: Int = 117
    const val INTERNAL_STORAGE_SETTINGS: Int = 118
    const val BIOMETRIC_ENROLL: Int = 119
    const val LOCALE_SETTINGS: Int = 120
    const val LOCATION_SOURCE_SETTINGS: Int = 121
    const val MANAGE_ALL_APPLICATIONS_SETTINGS: Int = 122
    const val PRIVACY_SETTINGS: Int = 123
    const val BATTERY_OPTIMIZATION_SETTINGS: Int = 124
    const val NFC_SETTING: Int = 125
    const val SOUND_SETTINGS: Int = 126
    const val NOTIFICATION_SETTINGS: Int = 127

    fun contains(value: Int): Boolean {
        val fields = RequestCodes::class.java.declaredFields

        for (field in fields) {
            try {
                if (field.getInt(null) == value) {
                    return true
                }
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            }
        }
        return false
    }
}
