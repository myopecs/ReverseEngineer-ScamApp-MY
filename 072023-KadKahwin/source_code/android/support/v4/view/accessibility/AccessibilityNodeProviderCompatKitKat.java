package android.support.v4.view.accessibility;

import android.annotation.TargetApi;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityNodeProvider;
import java.util.List;

@TargetApi(19)
@RequiresApi(19)
class AccessibilityNodeProviderCompatKitKat {
  public static Object newAccessibilityNodeProviderBridge(final AccessibilityNodeInfoBridge bridge) {
    return new AccessibilityNodeProvider() {
        public AccessibilityNodeInfo createAccessibilityNodeInfo(int param1Int) {
          return (AccessibilityNodeInfo)bridge.createAccessibilityNodeInfo(param1Int);
        }
        
        public List<AccessibilityNodeInfo> findAccessibilityNodeInfosByText(String param1String, int param1Int) {
          return (List)bridge.findAccessibilityNodeInfosByText(param1String, param1Int);
        }
        
        public AccessibilityNodeInfo findFocus(int param1Int) {
          return (AccessibilityNodeInfo)bridge.findFocus(param1Int);
        }
        
        public boolean performAction(int param1Int1, int param1Int2, Bundle param1Bundle) {
          return bridge.performAction(param1Int1, param1Int2, param1Bundle);
        }
      };
  }
  
  static interface AccessibilityNodeInfoBridge {
    Object createAccessibilityNodeInfo(int param1Int);
    
    List<Object> findAccessibilityNodeInfosByText(String param1String, int param1Int);
    
    Object findFocus(int param1Int);
    
    boolean performAction(int param1Int1, int param1Int2, Bundle param1Bundle);
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\android\support\v4\view\accessibility\AccessibilityNodeProviderCompatKitKat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */