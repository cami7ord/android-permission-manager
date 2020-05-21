package com.example.myapplication.permissions

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

sealed class ActivityOrFragmentReference(val reference: Any) {
    class FragmentReference(fragment: Fragment) : ActivityOrFragmentReference(reference = fragment)
    class ActivityReference(activity: AppCompatActivity) : ActivityOrFragmentReference(reference = activity)
}
