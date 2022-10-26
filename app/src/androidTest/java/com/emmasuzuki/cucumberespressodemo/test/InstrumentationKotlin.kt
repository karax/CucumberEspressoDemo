package com.emmasuzuki.cucumberespressodemo.test

import android.os.Bundle
import android.support.test.runner.MonitoringInstrumentation
import com.emmasuzuki.cucumberespressodemo.BuildConfig
import cucumber.api.CucumberOptions
import cucumber.api.android.CucumberInstrumentationCore

@CucumberOptions(features = ["features"], glue = ["com.emmasuzuki.cucumberespressodemo.test"])
class InstrumentationKotlin : MonitoringInstrumentation() {
    private val instrumentationCore = CucumberInstrumentationCore(this)

    override fun onCreate(arguments: Bundle) {
        super.onCreate(arguments)
        val tags = BuildConfig.TEST_TAGS
        if (!tags.isEmpty()) {
            arguments.putString(
                "tags",
                tags.replace(",".toRegex(), "--").replace("\\s".toRegex(), "")
            )
        }
        instrumentationCore.create(arguments)
        start()
    }

    override fun onStart() {
        super.onStart()
        waitForIdleSync()
        instrumentationCore.start()
    }
}
