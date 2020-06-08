package com.saulordz.dictionary.base

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnit
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import toothpick.Toothpick
import toothpick.testing.ToothPickRule

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
abstract class BaseActivityTest {

  @Rule @JvmField val mockitoRule = MockitoJUnit.rule()
  @Rule @JvmField val toothpickRule: ToothPickRule = ToothPickRule(this)

  @Before
  fun setUp() {
    toothpickRule.inject(this)
  }

  @After
  fun cleanUp() {
    Toothpick.reset()
  }

  inline fun <reified T : AppCompatActivity> letActivity(intent: Intent? = null, noinline action: ((T) -> Unit)? = null) {
    val controller = Robolectric.buildActivity(T::class.java, intent)
    val activity = controller.get()
    toothpickRule.setScopeName(activity)

    controller.create()

    action?.invoke(activity)

    activity?.finish()
    controller.destroy()
  }
}