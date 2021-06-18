package com.lfaiska.topredditsreader

import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.graphics.drawable.VectorDrawable
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher


class VectorDrawableMatcher(private val expectedId: Int) : TypeSafeMatcher<View?>(View::class.java) {
    var resourceName: String? = null

    override fun matchesSafely(target: View?): Boolean {
        if (target !is ImageView) {
            return false
        }

        val imageView: ImageView = target

        if (expectedId < 0) {
            return imageView.drawable == null
        }

        val resources: Resources = target.context.resources
        val expectedDrawable: Drawable? = ContextCompat.getDrawable(target.context, expectedId)

        resourceName = resources.getResourceEntryName(expectedId)

        val bitmap = (imageView.drawable as VectorDrawable).toBitmap()
        val expectedBitmap = (expectedDrawable as VectorDrawable).toBitmap()

        return bitmap.sameAs(expectedBitmap)
    }

    override fun describeTo(description: Description) {
        description.appendText("with drawable from resource id: ")
        description.appendValue(expectedId)
        if (resourceName != null) {
            description.appendText("[")
            description.appendText(resourceName)
            description.appendText("]")
        }
    }
}