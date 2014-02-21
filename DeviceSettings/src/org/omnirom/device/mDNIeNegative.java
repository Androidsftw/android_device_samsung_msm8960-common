/*
 * Copyright (C) 2012 The CyanogenMod Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.omnirom.device;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceManager;
import android.util.AttributeSet;

public class mDNIeNegative extends CheckBoxPreference implements OnPreferenceChangeListener {

    public mDNIeNegative(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setOnPreferenceChangeListener(this);
    }

    private static final String FILE = "/sys/class/mdnie/mdnie/negative";

    public static boolean isSupported() {
        return Utils.fileExists(FILE);
    }

    /**
     * Restore mdnie negative mode setting from SharedPreferences. (Write to kernel.)
     * @param context       The context to read the SharedPreferences from
     */
    public static void restore(Context context) {
        if (!isSupported()) {
            return;
        }

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        Utils.writeValue(FILE, sharedPrefs.getBoolean(DeviceSettings.KEY_MDNIE_NEGATIVE, false) ? "1" : "0");
    }

    public boolean onPreferenceChange(Preference preference, Object newValue) {
        Utils.writeValue(FILE, (Boolean)newValue ? "1" : "0");
        return true;
    }

}
