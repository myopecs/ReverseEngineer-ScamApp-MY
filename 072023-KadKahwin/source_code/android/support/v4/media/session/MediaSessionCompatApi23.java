package android.support.v4.media.session;

import android.annotation.TargetApi;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.RequiresApi;

@TargetApi(23)
@RequiresApi(23)
class MediaSessionCompatApi23 {
  public static Object createCallback(Callback paramCallback) {
    return new CallbackProxy<Callback>(paramCallback);
  }
  
  public static interface Callback extends MediaSessionCompatApi21.Callback {
    void onPlayFromUri(Uri param1Uri, Bundle param1Bundle);
  }
  
  static class CallbackProxy<T extends Callback> extends MediaSessionCompatApi21.CallbackProxy<T> {
    public CallbackProxy(T param1T) {
      super(param1T);
    }
    
    public void onPlayFromUri(Uri param1Uri, Bundle param1Bundle) {
      ((MediaSessionCompatApi23.Callback)this.mCallback).onPlayFromUri(param1Uri, param1Bundle);
    }
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\android\support\v4\media\session\MediaSessionCompatApi23.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */