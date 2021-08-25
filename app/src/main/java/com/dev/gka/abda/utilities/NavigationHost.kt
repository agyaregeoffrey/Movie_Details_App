package com.dev.gka.abda.utilities

import android.os.Bundle
import androidx.fragment.app.Fragment


/**
 * A host (typically an `Activity`} that can display fragments and knows how to respond to
 * navigation events.
 */
interface NavigationHost {
    /**
     * Trigger a navigation to the specified fragment, optionally adding a transaction to the back
     * stack to make this navigation reversible.
     */
    fun navigateTo(fragment: Fragment, bundle: Bundle, addToBackstack: Boolean)
}
