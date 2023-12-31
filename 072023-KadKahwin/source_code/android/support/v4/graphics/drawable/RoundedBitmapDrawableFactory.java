package android.support.v4.graphics.drawable;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.Build;
import android.support.v4.graphics.BitmapCompat;
import android.support.v4.view.GravityCompat;
import android.util.Log;
import java.io.InputStream;

public final class RoundedBitmapDrawableFactory {
  private static final String TAG = "RoundedBitmapDrawableFactory";
  
  public static RoundedBitmapDrawable create(Resources paramResources, Bitmap paramBitmap) {
    return (RoundedBitmapDrawable)((Build.VERSION.SDK_INT >= 21) ? new RoundedBitmapDrawable21(paramResources, paramBitmap) : new DefaultRoundedBitmapDrawable(paramResources, paramBitmap));
  }
  
  public static RoundedBitmapDrawable create(Resources paramResources, InputStream paramInputStream) {
    RoundedBitmapDrawable roundedBitmapDrawable = create(paramResources, BitmapFactory.decodeStream(paramInputStream));
    if (roundedBitmapDrawable.getBitmap() == null)
      Log.w("RoundedBitmapDrawableFactory", "RoundedBitmapDrawable cannot decode " + paramInputStream); 
    return roundedBitmapDrawable;
  }
  
  public static RoundedBitmapDrawable create(Resources paramResources, String paramString) {
    RoundedBitmapDrawable roundedBitmapDrawable = create(paramResources, BitmapFactory.decodeFile(paramString));
    if (roundedBitmapDrawable.getBitmap() == null)
      Log.w("RoundedBitmapDrawableFactory", "RoundedBitmapDrawable cannot decode " + paramString); 
    return roundedBitmapDrawable;
  }
  
  private static class DefaultRoundedBitmapDrawable extends RoundedBitmapDrawable {
    DefaultRoundedBitmapDrawable(Resources param1Resources, Bitmap param1Bitmap) {
      super(param1Resources, param1Bitmap);
    }
    
    void gravityCompatApply(int param1Int1, int param1Int2, int param1Int3, Rect param1Rect1, Rect param1Rect2) {
      GravityCompat.apply(param1Int1, param1Int2, param1Int3, param1Rect1, param1Rect2, 0);
    }
    
    public boolean hasMipMap() {
      return (this.mBitmap != null && BitmapCompat.hasMipMap(this.mBitmap));
    }
    
    public void setMipMap(boolean param1Boolean) {
      if (this.mBitmap != null) {
        BitmapCompat.setHasMipMap(this.mBitmap, param1Boolean);
        invalidateSelf();
      } 
    }
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\android\support\v4\graphics\drawable\RoundedBitmapDrawableFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */