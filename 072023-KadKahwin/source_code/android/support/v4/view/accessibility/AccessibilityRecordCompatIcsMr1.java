package android.support.v4.view.accessibility;

import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.view.accessibility.AccessibilityRecord;

@TargetApi(15)
@RequiresApi(15)
class AccessibilityRecordCompatIcsMr1 {
  public static int getMaxScrollX(Object paramObject) {
    return ((AccessibilityRecord)paramObject).getMaxScrollX();
  }
  
  public static int getMaxScrollY(Object paramObject) {
    return ((AccessibilityRecord)paramObject).getMaxScrollY();
  }
  
  public static void setMaxScrollX(Object paramObject, int paramInt) {
    ((AccessibilityRecord)paramObject).setMaxScrollX(paramInt);
  }
  
  public static void setMaxScrollY(Object paramObject, int paramInt) {
    ((AccessibilityRecord)paramObject).setMaxScrollY(paramInt);
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\android\support\v4\view\accessibility\AccessibilityRecordCompatIcsMr1.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */