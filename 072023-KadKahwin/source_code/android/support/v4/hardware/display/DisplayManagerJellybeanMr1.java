package android.support.v4.hardware.display;

import android.annotation.TargetApi;
import android.content.Context;
import android.hardware.display.DisplayManager;
import android.support.annotation.RequiresApi;
import android.view.Display;

@TargetApi(17)
@RequiresApi(17)
final class DisplayManagerJellybeanMr1 {
  public static Display getDisplay(Object paramObject, int paramInt) {
    return ((DisplayManager)paramObject).getDisplay(paramInt);
  }
  
  public static Object getDisplayManager(Context paramContext) {
    return paramContext.getSystemService("display");
  }
  
  public static Display[] getDisplays(Object paramObject) {
    return ((DisplayManager)paramObject).getDisplays();
  }
  
  public static Display[] getDisplays(Object paramObject, String paramString) {
    return ((DisplayManager)paramObject).getDisplays(paramString);
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\android\support\v4\hardware\display\DisplayManagerJellybeanMr1.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */