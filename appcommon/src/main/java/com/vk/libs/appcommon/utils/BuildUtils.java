package com.vk.libs.appcommon.utils;

import android.os.Build;


/**
 * Little helper class to detect current installed  sdk version
 *
 * @author Hannes Dorfmann
 */
public class BuildUtils {

  public static boolean isMinApi21() {
    return Build.VERSION.SDK_INT >= 21;
  }

//  @TargetApi(21) public static Drawable getBackArrowDrawable(Context context) {
//
//    if (isMinApi21()) {
//      return context.getResources()
//          .getDrawable(R.drawable.ic_action_back, context.getTheme());
//    } else {
//      return context.getResources().getDrawable(R.drawable.ic_action_back);
//    }
//  }
}
