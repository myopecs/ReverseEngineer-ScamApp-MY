package android.support.v4.os;

import android.os.AsyncTask;
import android.os.Build;

public final class AsyncTaskCompat {
  public static <Params, Progress, Result> AsyncTask<Params, Progress, Result> executeParallel(AsyncTask<Params, Progress, Result> paramAsyncTask, Params... paramVarArgs) {
    if (paramAsyncTask == null)
      throw new IllegalArgumentException("task can not be null"); 
    if (Build.VERSION.SDK_INT >= 11) {
      AsyncTaskCompatHoneycomb.executeParallel(paramAsyncTask, paramVarArgs);
      return paramAsyncTask;
    } 
    paramAsyncTask.execute((Object[])paramVarArgs);
    return paramAsyncTask;
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\android\support\v4\os\AsyncTaskCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */