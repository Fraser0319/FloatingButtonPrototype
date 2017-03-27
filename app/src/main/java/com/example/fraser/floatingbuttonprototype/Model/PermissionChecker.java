package com.example.fraser.floatingbuttonprototype.Model;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

/**
 *
 * class makes use of code from this tutorial
 *
 * — example of a simple use of a window head
 * https://codingshark.wordpress.com/2015/02/01/how-to-draw-on-top-of-other-applications/?utm_source=androiddevdigest
 * https://github.com/mollyIV/ChatHeads
 */

public class PermissionChecker {

    public final static int REQUIRED_PERMISSION_REQUEST_CODE = 2121;

    private Context mContext;

    public PermissionChecker(Context context) {
        mContext = context;
    }

    @TargetApi(Build.VERSION_CODES.M)
    public boolean isRequiredPermissionGranted() {
        if (isMarshmallowOrHigher()) {
            return Settings.canDrawOverlays(mContext);
        }
        return true;
    }

    @TargetApi(Build.VERSION_CODES.M)
    public Intent createRequiredPermissionIntent() {
        if (isMarshmallowOrHigher()) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + mContext.getPackageName()));
            return intent;
        }
        return null;
    }

    private boolean isMarshmallowOrHigher() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }
}
