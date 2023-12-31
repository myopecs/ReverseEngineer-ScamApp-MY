package android.support.v4.view;

import android.view.View;
import android.view.ViewParent;

public class NestedScrollingChildHelper {
  private boolean mIsNestedScrollingEnabled;
  
  private ViewParent mNestedScrollingParent;
  
  private int[] mTempNestedScrollConsumed;
  
  private final View mView;
  
  public NestedScrollingChildHelper(View paramView) {
    this.mView = paramView;
  }
  
  public boolean dispatchNestedFling(float paramFloat1, float paramFloat2, boolean paramBoolean) {
    return (isNestedScrollingEnabled() && this.mNestedScrollingParent != null) ? ViewParentCompat.onNestedFling(this.mNestedScrollingParent, this.mView, paramFloat1, paramFloat2, paramBoolean) : false;
  }
  
  public boolean dispatchNestedPreFling(float paramFloat1, float paramFloat2) {
    return (isNestedScrollingEnabled() && this.mNestedScrollingParent != null) ? ViewParentCompat.onNestedPreFling(this.mNestedScrollingParent, this.mView, paramFloat1, paramFloat2) : false;
  }
  
  public boolean dispatchNestedPreScroll(int paramInt1, int paramInt2, int[] paramArrayOfint1, int[] paramArrayOfint2) {
    boolean bool = false;
    null = bool;
    if (isNestedScrollingEnabled()) {
      null = bool;
      if (this.mNestedScrollingParent != null) {
        if (paramInt1 != 0 || paramInt2 != 0) {
          byte b1;
          byte b2;
          if (paramArrayOfint2 != null) {
            this.mView.getLocationInWindow(paramArrayOfint2);
            b2 = paramArrayOfint2[0];
            b1 = paramArrayOfint2[1];
          } else {
            b1 = 0;
            b2 = 0;
          } 
          int[] arrayOfInt = paramArrayOfint1;
          if (paramArrayOfint1 == null) {
            if (this.mTempNestedScrollConsumed == null)
              this.mTempNestedScrollConsumed = new int[2]; 
            arrayOfInt = this.mTempNestedScrollConsumed;
          } 
          arrayOfInt[0] = 0;
          arrayOfInt[1] = 0;
          ViewParentCompat.onNestedPreScroll(this.mNestedScrollingParent, this.mView, paramInt1, paramInt2, arrayOfInt);
          if (paramArrayOfint2 != null) {
            this.mView.getLocationInWindow(paramArrayOfint2);
            paramArrayOfint2[0] = paramArrayOfint2[0] - b2;
            paramArrayOfint2[1] = paramArrayOfint2[1] - b1;
          } 
          if (arrayOfInt[0] == 0) {
            null = bool;
            return (arrayOfInt[1] != 0) ? true : null;
          } 
        } else {
          null = bool;
          if (paramArrayOfint2 != null) {
            paramArrayOfint2[0] = 0;
            paramArrayOfint2[1] = 0;
            return false;
          } 
          return null;
        } 
      } else {
        return null;
      } 
    } else {
      return null;
    } 
    return true;
  }
  
  public boolean dispatchNestedScroll(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int[] paramArrayOfint) {
    boolean bool2 = false;
    boolean bool1 = bool2;
    if (isNestedScrollingEnabled()) {
      bool1 = bool2;
      if (this.mNestedScrollingParent != null) {
        if (paramInt1 != 0 || paramInt2 != 0 || paramInt3 != 0 || paramInt4 != 0) {
          byte b1;
          byte b2;
          if (paramArrayOfint != null) {
            this.mView.getLocationInWindow(paramArrayOfint);
            b2 = paramArrayOfint[0];
            b1 = paramArrayOfint[1];
          } else {
            b1 = 0;
            b2 = 0;
          } 
          ViewParentCompat.onNestedScroll(this.mNestedScrollingParent, this.mView, paramInt1, paramInt2, paramInt3, paramInt4);
          if (paramArrayOfint != null) {
            this.mView.getLocationInWindow(paramArrayOfint);
            paramArrayOfint[0] = paramArrayOfint[0] - b2;
            paramArrayOfint[1] = paramArrayOfint[1] - b1;
          } 
          return true;
        } 
      } else {
        return bool1;
      } 
    } else {
      return bool1;
    } 
    bool1 = bool2;
    if (paramArrayOfint != null) {
      paramArrayOfint[0] = 0;
      paramArrayOfint[1] = 0;
      return false;
    } 
    return bool1;
  }
  
  public boolean hasNestedScrollingParent() {
    return (this.mNestedScrollingParent != null);
  }
  
  public boolean isNestedScrollingEnabled() {
    return this.mIsNestedScrollingEnabled;
  }
  
  public void onDetachedFromWindow() {
    ViewCompat.stopNestedScroll(this.mView);
  }
  
  public void onStopNestedScroll(View paramView) {
    ViewCompat.stopNestedScroll(this.mView);
  }
  
  public void setNestedScrollingEnabled(boolean paramBoolean) {
    if (this.mIsNestedScrollingEnabled)
      ViewCompat.stopNestedScroll(this.mView); 
    this.mIsNestedScrollingEnabled = paramBoolean;
  }
  
  public boolean startNestedScroll(int paramInt) {
    if (hasNestedScrollingParent())
      return true; 
    if (isNestedScrollingEnabled()) {
      ViewParent viewParent = this.mView.getParent();
      View view = this.mView;
      while (viewParent != null) {
        if (ViewParentCompat.onStartNestedScroll(viewParent, view, this.mView, paramInt)) {
          this.mNestedScrollingParent = viewParent;
          ViewParentCompat.onNestedScrollAccepted(viewParent, view, this.mView, paramInt);
          return true;
        } 
        if (viewParent instanceof View)
          view = (View)viewParent; 
        viewParent = viewParent.getParent();
      } 
    } 
    return false;
  }
  
  public void stopNestedScroll() {
    if (this.mNestedScrollingParent != null) {
      ViewParentCompat.onStopNestedScroll(this.mNestedScrollingParent, this.mView);
      this.mNestedScrollingParent = null;
    } 
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\android\support\v4\view\NestedScrollingChildHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */