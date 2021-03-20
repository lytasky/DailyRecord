package com.lytasky.dailyrecord.utils

import android.content.Context
import android.graphics.Bitmap
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import com.lytasky.dailyrecord.R
import permissions.dispatcher.PermissionRequest

/**
 *  Created by Liaoxinhui on 2021/3/14 8:55 PM
 */
object ViewUtils {
    fun showRationaleDialog(
        context: Context,
        @StringRes messageResId: Int,
        request: PermissionRequest
    ) {
        AlertDialog.Builder(context)
            .setPositiveButton(R.string.dialog_button_allow) { _, _ -> request.proceed() }
            .setNegativeButton(R.string.dialog_button_deny) { _, _ -> request.cancel() }
            .setCancelable(false)
            .setMessage(messageResId)
            .show()
    }

    fun addPicImageView(linearLayout: LinearLayout, context: Context):ImageView {

        val imageView = ImageView(context)
        var layoutParams = LinearLayout.LayoutParams(
            context.resources.getDimensionPixelSize(R.dimen.pic_snapshot_wh),
            context.resources.getDimensionPixelSize(R.dimen.pic_snapshot_wh)
        );
        layoutParams.setMargins(0, 0, DisplayUtils.dp2px(8.0f), 0)
        imageView.layoutParams = layoutParams;
        imageView.scaleType = ImageView.ScaleType.FIT_XY
        linearLayout.addView(imageView);
        return imageView;
    }
}