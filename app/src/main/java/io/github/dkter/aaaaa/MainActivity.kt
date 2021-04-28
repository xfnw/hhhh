/*
 * Copyright (c) David Teresi 2021.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package io.github.dkter.aaaaa

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*

class MainActivity: AppCompatActivity(), TextWatcher {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val preferences = getSharedPreferences(
            getString(R.string.preferenceFileKey),
            Context.MODE_PRIVATE,
        )

        val testBox = findViewById<EditText>(R.id.testBox)
        testBox.addTextChangedListener(this)

        val hapticFeedbackSwitch = findViewById<Switch>(
            R.id.enableHapticFeedback
        )
        hapticFeedbackSwitch.isChecked = preferences.getBoolean(
            getString(R.string.hapticFeedbackKey),
            true,
        )
        hapticFeedbackSwitch.setOnCheckedChangeListener(
            { view: CompoundButton, enabled: Boolean ->
                with (preferences.edit()) {
                    putBoolean(getString(R.string.hapticFeedbackKey), enabled)
                    commit()
                }

                if (!enabled) {
                    val toast = Toast.makeText(
                        /*context=*/this,
                        /*text=*/getString(
                            R.string.toastDisableHapticFeedback
                        ),
                        /*duration=*/Toast.LENGTH_SHORT,
                    )
                    toast.show()
                }
            }
        )
    }

    fun keyboardSettings(v: View) {
        val intent = Intent(
            android.provider.Settings.ACTION_INPUT_METHOD_SETTINGS
        )
        startActivity(intent)
    }

    fun closeKeyboardSettingsReminder(v: View) {
        val toast = Toast.makeText(
            /*context=*/this,
            /*text=*/getString(R.string.toastFeatureNotImplemented),
            /*duration=*/Toast.LENGTH_SHORT,
        )
        toast.show()
    }

    override fun beforeTextChanged(
        s: CharSequence?, start: Int, count: Int, after: Int
    ) {
        // This is necessary because this class implements TextWatcher
    }

    override fun afterTextChanged(editable: Editable) {
        // This is necessary because this class implements TextWatcher
    }

    override fun onTextChanged(
        text: CharSequence?, start: Int, before: Int, count: Int
    ) {
        if (text != null) {
            val withoutA = text.replace("a".toRegex(), "")
            val errorField = findViewById<TextView>(R.id.testBoxErrorField)
            if (!withoutA.isBlank()) {
                errorField.text = getString(R.string.errorInvalidCharacters)
            }
            else {
                errorField.text = ""
            }
        }
    }
}
