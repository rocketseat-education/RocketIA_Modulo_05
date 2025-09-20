package com.rocketseat.rocketia.ui.utils

import android.view.View
import androidx.constraintlayout.helper.widget.Flow
import androidx.test.espresso.matcher.BoundedMatcher
import org.hamcrest.Description
import org.hamcrest.Matcher

fun withFlowContaining(vararg expectedIds: Int): Matcher<View> {
    return object : BoundedMatcher<View, Flow>(Flow::class.java), io.mockk.Matcher<View> {
        override fun matchesSafely(item: Flow?): Boolean {
            val refs = item?.referencedIds?.toSet().orEmpty()
            return expectedIds.all { id -> refs.contains(id) }
        }

        override fun describeTo(description: Description?) {
            description?.appendText("Flow containing referencedIds: ${expectedIds.joinToString()}")
        }

        // Este método pertence ao io.mockk.Matcher e deve ser removido
        override fun match(arg: View?): Boolean { // <--- REMOVER
            return arg is Flow && matchesSafely(arg)
        }
    }
}