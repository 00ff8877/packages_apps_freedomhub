/*
 * Copyright (C) 2017 AIMROM
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

package com.aim.freedomhub.categories;

import net.margaritov.preference.colorpicker.ColorPickerPreference;
import android.graphics.Color;
import android.content.Context;
import android.os.Bundle;
import android.os.UserHandle;
import android.content.ContentResolver;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceScreen;
import android.support.v7.preference.Preference.OnPreferenceChangeListener;
import android.provider.Settings;
import android.provider.SearchIndexableResource;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settings.search.Indexable;
import com.aim.freedomhub.preference.CustomSeekBarPreference;

import com.android.internal.logging.nano.MetricsProto;

import java.util.ArrayList;
import java.util.List;

public class quicksettings extends SettingsPreferenceFragment implements Preference.OnPreferenceChangeListener {
    
    private static final String QS_PANEL_ALPHA = "qs_panel_alpha";
    private static final String QS_PANEL_COLOR = "qs_panel_color";
    private ColorPickerPreference mQsPanelColor;
    private CustomSeekBarPreference mQsPanelAlpha;
    private CustomSeekBarPreference mQsRowsPort;
    private CustomSeekBarPreference mQsRowsLand;
    private CustomSeekBarPreference mQsColumnsPort;
    private CustomSeekBarPreference mQsColumnsLand;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.quick_settings);
		
        final ContentResolver resolver = getActivity().getContentResolver();

        int value = Settings.System.getIntForUser(resolver,
                Settings.System.QS_ROWS_PORTRAIT, 3, UserHandle.USER_CURRENT);
        mQsRowsPort = (CustomSeekBarPreference) findPreference("qs_rows_portrait");
        mQsRowsPort.setValue(value);
        mQsRowsPort.setOnPreferenceChangeListener(this);

        value = Settings.System.getIntForUser(resolver,
                Settings.System.QS_ROWS_LANDSCAPE, 2, UserHandle.USER_CURRENT);
        mQsRowsLand = (CustomSeekBarPreference) findPreference("qs_rows_landscape");
        mQsRowsLand.setValue(value);
        mQsRowsLand.setOnPreferenceChangeListener(this);

        value = Settings.System.getIntForUser(resolver,
                Settings.System.QS_COLUMNS_PORTRAIT, 5, UserHandle.USER_CURRENT);
        mQsColumnsPort = (CustomSeekBarPreference) findPreference("qs_columns_portrait");
        mQsColumnsPort.setValue(value);
        mQsColumnsPort.setOnPreferenceChangeListener(this);

        value = Settings.System.getIntForUser(resolver,
                Settings.System.QS_COLUMNS_LANDSCAPE, 5, UserHandle.USER_CURRENT);
        mQsColumnsLand = (CustomSeekBarPreference) findPreference("qs_columns_landscape");
        mQsColumnsLand.setValue(value);
        mQsColumnsLand.setOnPreferenceChangeListener(this);

	 mQsPanelAlpha = (CustomSeekBarPreference) findPreference(QS_PANEL_ALPHA);
         int qsPanelAlpha = Settings.System.getIntForUser(getContentResolver(),
                 Settings.System.QS_PANEL_BG_ALPHA, 255, UserHandle.USER_CURRENT);
         mQsPanelAlpha.setValue(qsPanelAlpha);
         mQsPanelAlpha.setOnPreferenceChangeListener(this);

	mQsPanelColor = (ColorPickerPreference) findPreference(QS_PANEL_COLOR);
         int QsColor = Settings.System.getIntForUser(getContentResolver(),
                 Settings.System.QS_PANEL_BG_COLOR, Color.WHITE, UserHandle.USER_CURRENT);
         mQsPanelColor.setNewPreviewColor(QsColor);
         mQsPanelColor.setOnPreferenceChangeListener(this);
    }
	
    @Override
    public void onResume() {
        super.onResume();
    }
	
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        final ContentResolver resolver = getActivity().getContentResolver();
        if (preference == mQsRowsPort) {
            int val = (Integer) newValue;
            Settings.System.putIntForUser(getContentResolver(),
                    Settings.System.QS_ROWS_PORTRAIT, val, UserHandle.USER_CURRENT);
            return true;
        } else if (preference == mQsRowsLand) {
            int val = (Integer) newValue;
            Settings.System.putIntForUser(getContentResolver(),
                    Settings.System.QS_ROWS_LANDSCAPE, val, UserHandle.USER_CURRENT);
            return true;
        } else if (preference == mQsColumnsPort) {
            int val = (Integer) newValue;
            Settings.System.putIntForUser(getContentResolver(),
                    Settings.System.QS_COLUMNS_PORTRAIT, val, UserHandle.USER_CURRENT);
            return true;
        } else if (preference == mQsColumnsLand) {
            int val = (Integer) newValue;
            Settings.System.putIntForUser(getContentResolver(),
                    Settings.System.QS_COLUMNS_LANDSCAPE, val, UserHandle.USER_CURRENT);
            return true;
	} else if (preference == mQsPanelAlpha) {
             int bgAlpha = (Integer) newValue;
             Settings.System.putIntForUser(getContentResolver(),
                     Settings.System.QS_PANEL_BG_ALPHA, bgAlpha,
                     UserHandle.USER_CURRENT);
             return true;
	} else if (preference == mQsPanelColor) {
             int bgColor = (Integer) newValue;
             Settings.System.putIntForUser(getContentResolver(),
                     Settings.System.QS_PANEL_BG_COLOR, bgColor,
                     UserHandle.USER_CURRENT);
             return true;
        }
        return false;
    }
	
    @Override
    public int getMetricsCategory() {
        return MetricsProto.MetricsEvent.AIM;
    }
}
