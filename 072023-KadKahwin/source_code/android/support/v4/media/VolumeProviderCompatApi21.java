package android.support.v4.media;

import android.annotation.TargetApi;
import android.media.VolumeProvider;
import android.support.annotation.RequiresApi;

@TargetApi(21)
@RequiresApi(21)
class VolumeProviderCompatApi21 {
  public static Object createVolumeProvider(int paramInt1, int paramInt2, int paramInt3, final Delegate delegate) {
    return new VolumeProvider(paramInt1, paramInt2, paramInt3) {
        public void onAdjustVolume(int param1Int) {
          delegate.onAdjustVolume(param1Int);
        }
        
        public void onSetVolumeTo(int param1Int) {
          delegate.onSetVolumeTo(param1Int);
        }
      };
  }
  
  public static void setCurrentVolume(Object paramObject, int paramInt) {
    ((VolumeProvider)paramObject).setCurrentVolume(paramInt);
  }
  
  public static interface Delegate {
    void onAdjustVolume(int param1Int);
    
    void onSetVolumeTo(int param1Int);
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\android\support\v4\media\VolumeProviderCompatApi21.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */