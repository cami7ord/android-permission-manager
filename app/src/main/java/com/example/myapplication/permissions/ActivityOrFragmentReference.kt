package com.example.myapplication.permissions

import android.app.Activity
import androidx.fragment.app.Fragment

sealed class ActivityOrFragmentReference(val reference: Any) {
    class FragmentReference(fragment: Fragment) : ActivityOrFragmentReference(reference = fragment)
    class ActivityReference(activity: Activity) : ActivityOrFragmentReference(reference = activity)
}