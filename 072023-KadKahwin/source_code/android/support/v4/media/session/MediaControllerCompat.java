package android.support.v4.media.session;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.support.annotation.VisibleForTesting;
import android.support.v4.app.BundleCompat;
import android.support.v4.app.SupportActivity;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.RatingCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public final class MediaControllerCompat {
  static final String COMMAND_ADD_QUEUE_ITEM = "android.support.v4.media.session.command.ADD_QUEUE_ITEM";
  
  static final String COMMAND_ADD_QUEUE_ITEM_AT = "android.support.v4.media.session.command.ADD_QUEUE_ITEM_AT";
  
  static final String COMMAND_ARGUMENT_INDEX = "android.support.v4.media.session.command.ARGUMENT_INDEX";
  
  static final String COMMAND_ARGUMENT_MEDIA_DESCRIPTION = "android.support.v4.media.session.command.ARGUMENT_MEDIA_DESCRIPTION";
  
  static final String COMMAND_GET_EXTRA_BINDER = "android.support.v4.media.session.command.GET_EXTRA_BINDER";
  
  static final String COMMAND_REMOVE_QUEUE_ITEM = "android.support.v4.media.session.command.REMOVE_QUEUE_ITEM";
  
  static final String COMMAND_REMOVE_QUEUE_ITEM_AT = "android.support.v4.media.session.command.REMOVE_QUEUE_ITEM_AT";
  
  static final String TAG = "MediaControllerCompat";
  
  private final MediaControllerImpl mImpl;
  
  private final MediaSessionCompat.Token mToken;
  
  public MediaControllerCompat(Context paramContext, MediaSessionCompat.Token paramToken) throws RemoteException {
    if (paramToken == null)
      throw new IllegalArgumentException("sessionToken must not be null"); 
    this.mToken = paramToken;
    if (Build.VERSION.SDK_INT >= 24) {
      this.mImpl = new MediaControllerImplApi24(paramContext, paramToken);
      return;
    } 
    if (Build.VERSION.SDK_INT >= 23) {
      this.mImpl = new MediaControllerImplApi23(paramContext, paramToken);
      return;
    } 
    if (Build.VERSION.SDK_INT >= 21) {
      this.mImpl = new MediaControllerImplApi21(paramContext, paramToken);
      return;
    } 
    this.mImpl = new MediaControllerImplBase(this.mToken);
  }
  
  public MediaControllerCompat(Context paramContext, MediaSessionCompat paramMediaSessionCompat) {
    if (paramMediaSessionCompat == null)
      throw new IllegalArgumentException("session must not be null"); 
    this.mToken = paramMediaSessionCompat.getSessionToken();
    if (Build.VERSION.SDK_INT >= 24) {
      this.mImpl = new MediaControllerImplApi24(paramContext, paramMediaSessionCompat);
      return;
    } 
    if (Build.VERSION.SDK_INT >= 23) {
      this.mImpl = new MediaControllerImplApi23(paramContext, paramMediaSessionCompat);
      return;
    } 
    if (Build.VERSION.SDK_INT >= 21) {
      this.mImpl = new MediaControllerImplApi21(paramContext, paramMediaSessionCompat);
      return;
    } 
    this.mImpl = new MediaControllerImplBase(this.mToken);
  }
  
  public static MediaControllerCompat getMediaController(Activity paramActivity) {
    MediaControllerExtraData mediaControllerExtraData;
    Object object1 = null;
    if (paramActivity instanceof SupportActivity) {
      mediaControllerExtraData = (MediaControllerExtraData)((SupportActivity)paramActivity).getExtraData(MediaControllerExtraData.class);
      if (mediaControllerExtraData != null) {
        MediaControllerCompat mediaControllerCompat = mediaControllerExtraData.getMediaController();
      } else {
        mediaControllerExtraData = null;
      } 
      return (MediaControllerCompat)mediaControllerExtraData;
    } 
    Object object = object1;
    if (Build.VERSION.SDK_INT >= 21) {
      Object object2 = MediaControllerCompatApi21.getMediaController((Activity)mediaControllerExtraData);
      object = object1;
      if (object2 != null) {
        object = MediaControllerCompatApi21.getSessionToken(object2);
        try {
          return new MediaControllerCompat((Context)mediaControllerExtraData, MediaSessionCompat.Token.fromToken(object));
        } catch (RemoteException remoteException) {
          Log.e("MediaControllerCompat", "Dead object in getMediaController.", (Throwable)remoteException);
          return null;
        } 
      } 
    } 
    return (MediaControllerCompat)object;
  }
  
  public static void setMediaController(Activity paramActivity, MediaControllerCompat paramMediaControllerCompat) {
    if (paramActivity instanceof SupportActivity)
      ((SupportActivity)paramActivity).putExtraData(new MediaControllerExtraData(paramMediaControllerCompat)); 
    if (Build.VERSION.SDK_INT >= 21) {
      Object object = null;
      if (paramMediaControllerCompat != null)
        object = MediaControllerCompatApi21.fromToken((Context)paramActivity, paramMediaControllerCompat.getSessionToken().getToken()); 
      MediaControllerCompatApi21.setMediaController(paramActivity, object);
    } 
  }
  
  public void addQueueItem(MediaDescriptionCompat paramMediaDescriptionCompat) {
    this.mImpl.addQueueItem(paramMediaDescriptionCompat);
  }
  
  public void addQueueItem(MediaDescriptionCompat paramMediaDescriptionCompat, int paramInt) {
    this.mImpl.addQueueItem(paramMediaDescriptionCompat, paramInt);
  }
  
  public void adjustVolume(int paramInt1, int paramInt2) {
    this.mImpl.adjustVolume(paramInt1, paramInt2);
  }
  
  public boolean dispatchMediaButtonEvent(KeyEvent paramKeyEvent) {
    if (paramKeyEvent == null)
      throw new IllegalArgumentException("KeyEvent may not be null"); 
    return this.mImpl.dispatchMediaButtonEvent(paramKeyEvent);
  }
  
  public Bundle getExtras() {
    return this.mImpl.getExtras();
  }
  
  public long getFlags() {
    return this.mImpl.getFlags();
  }
  
  public Object getMediaController() {
    return this.mImpl.getMediaController();
  }
  
  public MediaMetadataCompat getMetadata() {
    return this.mImpl.getMetadata();
  }
  
  public String getPackageName() {
    return this.mImpl.getPackageName();
  }
  
  public PlaybackInfo getPlaybackInfo() {
    return this.mImpl.getPlaybackInfo();
  }
  
  public PlaybackStateCompat getPlaybackState() {
    return this.mImpl.getPlaybackState();
  }
  
  public List<MediaSessionCompat.QueueItem> getQueue() {
    return this.mImpl.getQueue();
  }
  
  public CharSequence getQueueTitle() {
    return this.mImpl.getQueueTitle();
  }
  
  public int getRatingType() {
    return this.mImpl.getRatingType();
  }
  
  public int getRepeatMode() {
    return this.mImpl.getRepeatMode();
  }
  
  public PendingIntent getSessionActivity() {
    return this.mImpl.getSessionActivity();
  }
  
  public MediaSessionCompat.Token getSessionToken() {
    return this.mToken;
  }
  
  public TransportControls getTransportControls() {
    return this.mImpl.getTransportControls();
  }
  
  @VisibleForTesting
  boolean isExtraBinderReady() {
    return (this.mImpl instanceof MediaControllerImplApi21) ? ((((MediaControllerImplApi21)this.mImpl).mExtraBinder != null)) : false;
  }
  
  public boolean isShuffleModeEnabled() {
    return this.mImpl.isShuffleModeEnabled();
  }
  
  public void registerCallback(Callback paramCallback) {
    registerCallback(paramCallback, null);
  }
  
  public void registerCallback(Callback paramCallback, Handler paramHandler) {
    if (paramCallback == null)
      throw new IllegalArgumentException("callback cannot be null"); 
    Handler handler = paramHandler;
    if (paramHandler == null)
      handler = new Handler(); 
    this.mImpl.registerCallback(paramCallback, handler);
  }
  
  public void removeQueueItem(MediaDescriptionCompat paramMediaDescriptionCompat) {
    this.mImpl.removeQueueItem(paramMediaDescriptionCompat);
  }
  
  public void removeQueueItemAt(int paramInt) {
    this.mImpl.removeQueueItemAt(paramInt);
  }
  
  public void sendCommand(String paramString, Bundle paramBundle, ResultReceiver paramResultReceiver) {
    if (TextUtils.isEmpty(paramString))
      throw new IllegalArgumentException("command cannot be null or empty"); 
    this.mImpl.sendCommand(paramString, paramBundle, paramResultReceiver);
  }
  
  public void setVolumeTo(int paramInt1, int paramInt2) {
    this.mImpl.setVolumeTo(paramInt1, paramInt2);
  }
  
  public void unregisterCallback(Callback paramCallback) {
    if (paramCallback == null)
      throw new IllegalArgumentException("callback cannot be null"); 
    this.mImpl.unregisterCallback(paramCallback);
  }
  
  public static abstract class Callback implements IBinder.DeathRecipient {
    private final Object mCallbackObj;
    
    MessageHandler mHandler;
    
    boolean mHasExtraCallback;
    
    boolean mRegistered = false;
    
    public Callback() {
      if (Build.VERSION.SDK_INT >= 21) {
        this.mCallbackObj = MediaControllerCompatApi21.createCallback(new StubApi21());
        return;
      } 
      this.mCallbackObj = new StubCompat();
    }
    
    private void setHandler(Handler param1Handler) {
      this.mHandler = new MessageHandler(param1Handler.getLooper());
    }
    
    public void binderDied() {
      onSessionDestroyed();
    }
    
    public void onAudioInfoChanged(MediaControllerCompat.PlaybackInfo param1PlaybackInfo) {}
    
    public void onExtrasChanged(Bundle param1Bundle) {}
    
    public void onMetadataChanged(MediaMetadataCompat param1MediaMetadataCompat) {}
    
    public void onPlaybackStateChanged(PlaybackStateCompat param1PlaybackStateCompat) {}
    
    public void onQueueChanged(List<MediaSessionCompat.QueueItem> param1List) {}
    
    public void onQueueTitleChanged(CharSequence param1CharSequence) {}
    
    public void onRepeatModeChanged(int param1Int) {}
    
    public void onSessionDestroyed() {}
    
    public void onSessionEvent(String param1String, Bundle param1Bundle) {}
    
    public void onShuffleModeChanged(boolean param1Boolean) {}
    
    private class MessageHandler extends Handler {
      private static final int MSG_DESTROYED = 8;
      
      private static final int MSG_EVENT = 1;
      
      private static final int MSG_UPDATE_EXTRAS = 7;
      
      private static final int MSG_UPDATE_METADATA = 3;
      
      private static final int MSG_UPDATE_PLAYBACK_STATE = 2;
      
      private static final int MSG_UPDATE_QUEUE = 5;
      
      private static final int MSG_UPDATE_QUEUE_TITLE = 6;
      
      private static final int MSG_UPDATE_REPEAT_MODE = 9;
      
      private static final int MSG_UPDATE_SHUFFLE_MODE = 10;
      
      private static final int MSG_UPDATE_VOLUME = 4;
      
      public MessageHandler(Looper param2Looper) {
        super(param2Looper);
      }
      
      public void handleMessage(Message param2Message) {
        if (!MediaControllerCompat.Callback.this.mRegistered)
          return; 
        switch (param2Message.what) {
          default:
            return;
          case 1:
            MediaControllerCompat.Callback.this.onSessionEvent((String)param2Message.obj, param2Message.getData());
            return;
          case 2:
            MediaControllerCompat.Callback.this.onPlaybackStateChanged((PlaybackStateCompat)param2Message.obj);
            return;
          case 3:
            MediaControllerCompat.Callback.this.onMetadataChanged((MediaMetadataCompat)param2Message.obj);
            return;
          case 5:
            MediaControllerCompat.Callback.this.onQueueChanged((List<MediaSessionCompat.QueueItem>)param2Message.obj);
            return;
          case 6:
            MediaControllerCompat.Callback.this.onQueueTitleChanged((CharSequence)param2Message.obj);
            return;
          case 9:
            MediaControllerCompat.Callback.this.onRepeatModeChanged(((Integer)param2Message.obj).intValue());
            return;
          case 10:
            MediaControllerCompat.Callback.this.onShuffleModeChanged(((Boolean)param2Message.obj).booleanValue());
            return;
          case 7:
            MediaControllerCompat.Callback.this.onExtrasChanged((Bundle)param2Message.obj);
            return;
          case 4:
            MediaControllerCompat.Callback.this.onAudioInfoChanged((MediaControllerCompat.PlaybackInfo)param2Message.obj);
            return;
          case 8:
            break;
        } 
        MediaControllerCompat.Callback.this.onSessionDestroyed();
      }
      
      public void post(int param2Int, Object param2Object, Bundle param2Bundle) {
        param2Object = obtainMessage(param2Int, param2Object);
        param2Object.setData(param2Bundle);
        param2Object.sendToTarget();
      }
    }
    
    private class StubApi21 implements MediaControllerCompatApi21.Callback {
      public void onAudioInfoChanged(int param2Int1, int param2Int2, int param2Int3, int param2Int4, int param2Int5) {
        MediaControllerCompat.Callback.this.onAudioInfoChanged(new MediaControllerCompat.PlaybackInfo(param2Int1, param2Int2, param2Int3, param2Int4, param2Int5));
      }
      
      public void onExtrasChanged(Bundle param2Bundle) {
        MediaControllerCompat.Callback.this.onExtrasChanged(param2Bundle);
      }
      
      public void onMetadataChanged(Object param2Object) {
        MediaControllerCompat.Callback.this.onMetadataChanged(MediaMetadataCompat.fromMediaMetadata(param2Object));
      }
      
      public void onPlaybackStateChanged(Object param2Object) {
        if (MediaControllerCompat.Callback.this.mHasExtraCallback)
          return; 
        MediaControllerCompat.Callback.this.onPlaybackStateChanged(PlaybackStateCompat.fromPlaybackState(param2Object));
      }
      
      public void onQueueChanged(List<?> param2List) {
        MediaControllerCompat.Callback.this.onQueueChanged(MediaSessionCompat.QueueItem.fromQueueItemList(param2List));
      }
      
      public void onQueueTitleChanged(CharSequence param2CharSequence) {
        MediaControllerCompat.Callback.this.onQueueTitleChanged(param2CharSequence);
      }
      
      public void onSessionDestroyed() {
        MediaControllerCompat.Callback.this.onSessionDestroyed();
      }
      
      public void onSessionEvent(String param2String, Bundle param2Bundle) {
        if (MediaControllerCompat.Callback.this.mHasExtraCallback && Build.VERSION.SDK_INT < 23)
          return; 
        MediaControllerCompat.Callback.this.onSessionEvent(param2String, param2Bundle);
      }
    }
    
    private class StubCompat extends IMediaControllerCallback.Stub {
      public void onEvent(String param2String, Bundle param2Bundle) throws RemoteException {
        MediaControllerCompat.Callback.this.mHandler.post(1, param2String, param2Bundle);
      }
      
      public void onExtrasChanged(Bundle param2Bundle) throws RemoteException {
        MediaControllerCompat.Callback.this.mHandler.post(7, param2Bundle, (Bundle)null);
      }
      
      public void onMetadataChanged(MediaMetadataCompat param2MediaMetadataCompat) throws RemoteException {
        MediaControllerCompat.Callback.this.mHandler.post(3, param2MediaMetadataCompat, (Bundle)null);
      }
      
      public void onPlaybackStateChanged(PlaybackStateCompat param2PlaybackStateCompat) throws RemoteException {
        MediaControllerCompat.Callback.this.mHandler.post(2, param2PlaybackStateCompat, (Bundle)null);
      }
      
      public void onQueueChanged(List<MediaSessionCompat.QueueItem> param2List) throws RemoteException {
        MediaControllerCompat.Callback.this.mHandler.post(5, param2List, (Bundle)null);
      }
      
      public void onQueueTitleChanged(CharSequence param2CharSequence) throws RemoteException {
        MediaControllerCompat.Callback.this.mHandler.post(6, param2CharSequence, (Bundle)null);
      }
      
      public void onRepeatModeChanged(int param2Int) throws RemoteException {
        MediaControllerCompat.Callback.this.mHandler.post(9, Integer.valueOf(param2Int), (Bundle)null);
      }
      
      public void onSessionDestroyed() throws RemoteException {
        MediaControllerCompat.Callback.this.mHandler.post(8, (Object)null, (Bundle)null);
      }
      
      public void onShuffleModeChanged(boolean param2Boolean) throws RemoteException {
        MediaControllerCompat.Callback.this.mHandler.post(10, Boolean.valueOf(param2Boolean), (Bundle)null);
      }
      
      public void onVolumeInfoChanged(ParcelableVolumeInfo param2ParcelableVolumeInfo) throws RemoteException {
        if (param2ParcelableVolumeInfo != null) {
          MediaControllerCompat.PlaybackInfo playbackInfo = new MediaControllerCompat.PlaybackInfo(param2ParcelableVolumeInfo.volumeType, param2ParcelableVolumeInfo.audioStream, param2ParcelableVolumeInfo.controlType, param2ParcelableVolumeInfo.maxVolume, param2ParcelableVolumeInfo.currentVolume);
        } else {
          param2ParcelableVolumeInfo = null;
        } 
        MediaControllerCompat.Callback.this.mHandler.post(4, param2ParcelableVolumeInfo, (Bundle)null);
      }
    }
  }
  
  private class MessageHandler extends Handler {
    private static final int MSG_DESTROYED = 8;
    
    private static final int MSG_EVENT = 1;
    
    private static final int MSG_UPDATE_EXTRAS = 7;
    
    private static final int MSG_UPDATE_METADATA = 3;
    
    private static final int MSG_UPDATE_PLAYBACK_STATE = 2;
    
    private static final int MSG_UPDATE_QUEUE = 5;
    
    private static final int MSG_UPDATE_QUEUE_TITLE = 6;
    
    private static final int MSG_UPDATE_REPEAT_MODE = 9;
    
    private static final int MSG_UPDATE_SHUFFLE_MODE = 10;
    
    private static final int MSG_UPDATE_VOLUME = 4;
    
    public MessageHandler(Looper param1Looper) {
      super(param1Looper);
    }
    
    public void handleMessage(Message param1Message) {
      if (!this.this$0.mRegistered)
        return; 
      switch (param1Message.what) {
        default:
          return;
        case 1:
          this.this$0.onSessionEvent((String)param1Message.obj, param1Message.getData());
          return;
        case 2:
          this.this$0.onPlaybackStateChanged((PlaybackStateCompat)param1Message.obj);
          return;
        case 3:
          this.this$0.onMetadataChanged((MediaMetadataCompat)param1Message.obj);
          return;
        case 5:
          this.this$0.onQueueChanged((List<MediaSessionCompat.QueueItem>)param1Message.obj);
          return;
        case 6:
          this.this$0.onQueueTitleChanged((CharSequence)param1Message.obj);
          return;
        case 9:
          this.this$0.onRepeatModeChanged(((Integer)param1Message.obj).intValue());
          return;
        case 10:
          this.this$0.onShuffleModeChanged(((Boolean)param1Message.obj).booleanValue());
          return;
        case 7:
          this.this$0.onExtrasChanged((Bundle)param1Message.obj);
          return;
        case 4:
          this.this$0.onAudioInfoChanged((MediaControllerCompat.PlaybackInfo)param1Message.obj);
          return;
        case 8:
          break;
      } 
      this.this$0.onSessionDestroyed();
    }
    
    public void post(int param1Int, Object param1Object, Bundle param1Bundle) {
      param1Object = obtainMessage(param1Int, param1Object);
      param1Object.setData(param1Bundle);
      param1Object.sendToTarget();
    }
  }
  
  private class StubApi21 implements MediaControllerCompatApi21.Callback {
    public void onAudioInfoChanged(int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5) {
      this.this$0.onAudioInfoChanged(new MediaControllerCompat.PlaybackInfo(param1Int1, param1Int2, param1Int3, param1Int4, param1Int5));
    }
    
    public void onExtrasChanged(Bundle param1Bundle) {
      this.this$0.onExtrasChanged(param1Bundle);
    }
    
    public void onMetadataChanged(Object param1Object) {
      this.this$0.onMetadataChanged(MediaMetadataCompat.fromMediaMetadata(param1Object));
    }
    
    public void onPlaybackStateChanged(Object param1Object) {
      if (this.this$0.mHasExtraCallback)
        return; 
      this.this$0.onPlaybackStateChanged(PlaybackStateCompat.fromPlaybackState(param1Object));
    }
    
    public void onQueueChanged(List<?> param1List) {
      this.this$0.onQueueChanged(MediaSessionCompat.QueueItem.fromQueueItemList(param1List));
    }
    
    public void onQueueTitleChanged(CharSequence param1CharSequence) {
      this.this$0.onQueueTitleChanged(param1CharSequence);
    }
    
    public void onSessionDestroyed() {
      this.this$0.onSessionDestroyed();
    }
    
    public void onSessionEvent(String param1String, Bundle param1Bundle) {
      if (this.this$0.mHasExtraCallback && Build.VERSION.SDK_INT < 23)
        return; 
      this.this$0.onSessionEvent(param1String, param1Bundle);
    }
  }
  
  private class StubCompat extends IMediaControllerCallback.Stub {
    public void onEvent(String param1String, Bundle param1Bundle) throws RemoteException {
      this.this$0.mHandler.post(1, param1String, param1Bundle);
    }
    
    public void onExtrasChanged(Bundle param1Bundle) throws RemoteException {
      this.this$0.mHandler.post(7, param1Bundle, (Bundle)null);
    }
    
    public void onMetadataChanged(MediaMetadataCompat param1MediaMetadataCompat) throws RemoteException {
      this.this$0.mHandler.post(3, param1MediaMetadataCompat, (Bundle)null);
    }
    
    public void onPlaybackStateChanged(PlaybackStateCompat param1PlaybackStateCompat) throws RemoteException {
      this.this$0.mHandler.post(2, param1PlaybackStateCompat, (Bundle)null);
    }
    
    public void onQueueChanged(List<MediaSessionCompat.QueueItem> param1List) throws RemoteException {
      this.this$0.mHandler.post(5, param1List, (Bundle)null);
    }
    
    public void onQueueTitleChanged(CharSequence param1CharSequence) throws RemoteException {
      this.this$0.mHandler.post(6, param1CharSequence, (Bundle)null);
    }
    
    public void onRepeatModeChanged(int param1Int) throws RemoteException {
      this.this$0.mHandler.post(9, Integer.valueOf(param1Int), (Bundle)null);
    }
    
    public void onSessionDestroyed() throws RemoteException {
      this.this$0.mHandler.post(8, (Object)null, (Bundle)null);
    }
    
    public void onShuffleModeChanged(boolean param1Boolean) throws RemoteException {
      this.this$0.mHandler.post(10, Boolean.valueOf(param1Boolean), (Bundle)null);
    }
    
    public void onVolumeInfoChanged(ParcelableVolumeInfo param1ParcelableVolumeInfo) throws RemoteException {
      if (param1ParcelableVolumeInfo != null) {
        MediaControllerCompat.PlaybackInfo playbackInfo = new MediaControllerCompat.PlaybackInfo(param1ParcelableVolumeInfo.volumeType, param1ParcelableVolumeInfo.audioStream, param1ParcelableVolumeInfo.controlType, param1ParcelableVolumeInfo.maxVolume, param1ParcelableVolumeInfo.currentVolume);
      } else {
        param1ParcelableVolumeInfo = null;
      } 
      this.this$0.mHandler.post(4, param1ParcelableVolumeInfo, (Bundle)null);
    }
  }
  
  private static class MediaControllerExtraData extends SupportActivity.ExtraData {
    private final MediaControllerCompat mMediaController;
    
    MediaControllerExtraData(MediaControllerCompat param1MediaControllerCompat) {
      this.mMediaController = param1MediaControllerCompat;
    }
    
    MediaControllerCompat getMediaController() {
      return this.mMediaController;
    }
  }
  
  static interface MediaControllerImpl {
    void addQueueItem(MediaDescriptionCompat param1MediaDescriptionCompat);
    
    void addQueueItem(MediaDescriptionCompat param1MediaDescriptionCompat, int param1Int);
    
    void adjustVolume(int param1Int1, int param1Int2);
    
    boolean dispatchMediaButtonEvent(KeyEvent param1KeyEvent);
    
    Bundle getExtras();
    
    long getFlags();
    
    Object getMediaController();
    
    MediaMetadataCompat getMetadata();
    
    String getPackageName();
    
    MediaControllerCompat.PlaybackInfo getPlaybackInfo();
    
    PlaybackStateCompat getPlaybackState();
    
    List<MediaSessionCompat.QueueItem> getQueue();
    
    CharSequence getQueueTitle();
    
    int getRatingType();
    
    int getRepeatMode();
    
    PendingIntent getSessionActivity();
    
    MediaControllerCompat.TransportControls getTransportControls();
    
    boolean isShuffleModeEnabled();
    
    void registerCallback(MediaControllerCompat.Callback param1Callback, Handler param1Handler);
    
    void removeQueueItem(MediaDescriptionCompat param1MediaDescriptionCompat);
    
    void removeQueueItemAt(int param1Int);
    
    void sendCommand(String param1String, Bundle param1Bundle, ResultReceiver param1ResultReceiver);
    
    void setVolumeTo(int param1Int1, int param1Int2);
    
    void unregisterCallback(MediaControllerCompat.Callback param1Callback);
  }
  
  static class MediaControllerImplApi21 implements MediaControllerImpl {
    private HashMap<MediaControllerCompat.Callback, ExtraCallback> mCallbackMap = new HashMap<MediaControllerCompat.Callback, ExtraCallback>();
    
    protected final Object mControllerObj;
    
    private IMediaSession mExtraBinder;
    
    private List<MediaControllerCompat.Callback> mPendingCallbacks = new ArrayList<MediaControllerCompat.Callback>();
    
    public MediaControllerImplApi21(Context param1Context, MediaSessionCompat.Token param1Token) throws RemoteException {
      this.mControllerObj = MediaControllerCompatApi21.fromToken(param1Context, param1Token.getToken());
      if (this.mControllerObj == null)
        throw new RemoteException(); 
      requestExtraBinder();
    }
    
    public MediaControllerImplApi21(Context param1Context, MediaSessionCompat param1MediaSessionCompat) {
      this.mControllerObj = MediaControllerCompatApi21.fromToken(param1Context, param1MediaSessionCompat.getSessionToken().getToken());
      requestExtraBinder();
    }
    
    private void processPendingCallbacks() {
      if (this.mExtraBinder == null)
        return; 
      synchronized (this.mPendingCallbacks) {
        Iterator<MediaControllerCompat.Callback> iterator = this.mPendingCallbacks.iterator();
        while (true) {
          if (iterator.hasNext()) {
            MediaControllerCompat.Callback callback = iterator.next();
            ExtraCallback extraCallback = new ExtraCallback(callback);
            this.mCallbackMap.put(callback, extraCallback);
            callback.mHasExtraCallback = true;
            try {
              this.mExtraBinder.registerCallbackListener(extraCallback);
              continue;
            } catch (RemoteException remoteException) {
              Log.e("MediaControllerCompat", "Dead object in registerCallback.", (Throwable)remoteException);
            } 
          } else {
            break;
          } 
          this.mPendingCallbacks.clear();
          return;
        } 
        this.mPendingCallbacks.clear();
        return;
      } 
    }
    
    private void requestExtraBinder() {
      sendCommand("android.support.v4.media.session.command.GET_EXTRA_BINDER", null, new ExtraBinderRequestResultReceiver(this, new Handler()));
    }
    
    public void addQueueItem(MediaDescriptionCompat param1MediaDescriptionCompat) {
      if ((getFlags() & 0x4L) == 0L)
        throw new UnsupportedOperationException("This session doesn't support queue management operations"); 
      Bundle bundle = new Bundle();
      bundle.putParcelable("android.support.v4.media.session.command.ARGUMENT_MEDIA_DESCRIPTION", (Parcelable)param1MediaDescriptionCompat);
      sendCommand("android.support.v4.media.session.command.ADD_QUEUE_ITEM", bundle, null);
    }
    
    public void addQueueItem(MediaDescriptionCompat param1MediaDescriptionCompat, int param1Int) {
      if ((getFlags() & 0x4L) == 0L)
        throw new UnsupportedOperationException("This session doesn't support queue management operations"); 
      Bundle bundle = new Bundle();
      bundle.putParcelable("android.support.v4.media.session.command.ARGUMENT_MEDIA_DESCRIPTION", (Parcelable)param1MediaDescriptionCompat);
      bundle.putInt("android.support.v4.media.session.command.ARGUMENT_INDEX", param1Int);
      sendCommand("android.support.v4.media.session.command.ADD_QUEUE_ITEM_AT", bundle, null);
    }
    
    public void adjustVolume(int param1Int1, int param1Int2) {
      MediaControllerCompatApi21.adjustVolume(this.mControllerObj, param1Int1, param1Int2);
    }
    
    public boolean dispatchMediaButtonEvent(KeyEvent param1KeyEvent) {
      return MediaControllerCompatApi21.dispatchMediaButtonEvent(this.mControllerObj, param1KeyEvent);
    }
    
    public Bundle getExtras() {
      return MediaControllerCompatApi21.getExtras(this.mControllerObj);
    }
    
    public long getFlags() {
      return MediaControllerCompatApi21.getFlags(this.mControllerObj);
    }
    
    public Object getMediaController() {
      return this.mControllerObj;
    }
    
    public MediaMetadataCompat getMetadata() {
      Object object = MediaControllerCompatApi21.getMetadata(this.mControllerObj);
      return (object != null) ? MediaMetadataCompat.fromMediaMetadata(object) : null;
    }
    
    public String getPackageName() {
      return MediaControllerCompatApi21.getPackageName(this.mControllerObj);
    }
    
    public MediaControllerCompat.PlaybackInfo getPlaybackInfo() {
      Object object = MediaControllerCompatApi21.getPlaybackInfo(this.mControllerObj);
      return (object != null) ? new MediaControllerCompat.PlaybackInfo(MediaControllerCompatApi21.PlaybackInfo.getPlaybackType(object), MediaControllerCompatApi21.PlaybackInfo.getLegacyAudioStream(object), MediaControllerCompatApi21.PlaybackInfo.getVolumeControl(object), MediaControllerCompatApi21.PlaybackInfo.getMaxVolume(object), MediaControllerCompatApi21.PlaybackInfo.getCurrentVolume(object)) : null;
    }
    
    public PlaybackStateCompat getPlaybackState() {
      if (this.mExtraBinder != null)
        try {
          return this.mExtraBinder.getPlaybackState();
        } catch (RemoteException remoteException) {
          Log.e("MediaControllerCompat", "Dead object in getPlaybackState.", (Throwable)remoteException);
        }  
      Object object = MediaControllerCompatApi21.getPlaybackState(this.mControllerObj);
      return (object != null) ? PlaybackStateCompat.fromPlaybackState(object) : null;
    }
    
    public List<MediaSessionCompat.QueueItem> getQueue() {
      List<Object> list = MediaControllerCompatApi21.getQueue(this.mControllerObj);
      return (list != null) ? MediaSessionCompat.QueueItem.fromQueueItemList(list) : null;
    }
    
    public CharSequence getQueueTitle() {
      return MediaControllerCompatApi21.getQueueTitle(this.mControllerObj);
    }
    
    public int getRatingType() {
      if (Build.VERSION.SDK_INT < 22 && this.mExtraBinder != null)
        try {
          return this.mExtraBinder.getRatingType();
        } catch (RemoteException remoteException) {
          Log.e("MediaControllerCompat", "Dead object in getRatingType.", (Throwable)remoteException);
        }  
      return MediaControllerCompatApi21.getRatingType(this.mControllerObj);
    }
    
    public int getRepeatMode() {
      if (this.mExtraBinder != null)
        try {
          return this.mExtraBinder.getRepeatMode();
        } catch (RemoteException remoteException) {
          Log.e("MediaControllerCompat", "Dead object in getRepeatMode.", (Throwable)remoteException);
        }  
      return 0;
    }
    
    public PendingIntent getSessionActivity() {
      return MediaControllerCompatApi21.getSessionActivity(this.mControllerObj);
    }
    
    public MediaControllerCompat.TransportControls getTransportControls() {
      Object object = MediaControllerCompatApi21.getTransportControls(this.mControllerObj);
      return (object != null) ? new MediaControllerCompat.TransportControlsApi21(object) : null;
    }
    
    public boolean isShuffleModeEnabled() {
      if (this.mExtraBinder != null)
        try {
          return this.mExtraBinder.isShuffleModeEnabled();
        } catch (RemoteException remoteException) {
          Log.e("MediaControllerCompat", "Dead object in isShuffleModeEnabled.", (Throwable)remoteException);
        }  
      return false;
    }
    
    public final void registerCallback(MediaControllerCompat.Callback param1Callback, Handler param1Handler) {
      ExtraCallback extraCallback;
      MediaControllerCompatApi21.registerCallback(this.mControllerObj, param1Callback.mCallbackObj, param1Handler);
      if (this.mExtraBinder != null) {
        param1Callback.setHandler(param1Handler);
        extraCallback = new ExtraCallback(param1Callback);
        this.mCallbackMap.put(param1Callback, extraCallback);
        param1Callback.mHasExtraCallback = true;
        try {
          this.mExtraBinder.registerCallbackListener(extraCallback);
          return;
        } catch (RemoteException null) {
          Log.e("MediaControllerCompat", "Dead object in registerCallback.", (Throwable)null);
          return;
        } 
      } 
      null.setHandler((Handler)extraCallback);
      synchronized (this.mPendingCallbacks) {
        this.mPendingCallbacks.add(null);
        return;
      } 
    }
    
    public void removeQueueItem(MediaDescriptionCompat param1MediaDescriptionCompat) {
      if ((getFlags() & 0x4L) == 0L)
        throw new UnsupportedOperationException("This session doesn't support queue management operations"); 
      Bundle bundle = new Bundle();
      bundle.putParcelable("android.support.v4.media.session.command.ARGUMENT_MEDIA_DESCRIPTION", (Parcelable)param1MediaDescriptionCompat);
      sendCommand("android.support.v4.media.session.command.REMOVE_QUEUE_ITEM", bundle, null);
    }
    
    public void removeQueueItemAt(int param1Int) {
      if ((getFlags() & 0x4L) == 0L)
        throw new UnsupportedOperationException("This session doesn't support queue management operations"); 
      Bundle bundle = new Bundle();
      bundle.putInt("android.support.v4.media.session.command.ARGUMENT_INDEX", param1Int);
      sendCommand("android.support.v4.media.session.command.REMOVE_QUEUE_ITEM_AT", bundle, null);
    }
    
    public void sendCommand(String param1String, Bundle param1Bundle, ResultReceiver param1ResultReceiver) {
      MediaControllerCompatApi21.sendCommand(this.mControllerObj, param1String, param1Bundle, param1ResultReceiver);
    }
    
    public void setVolumeTo(int param1Int1, int param1Int2) {
      MediaControllerCompatApi21.setVolumeTo(this.mControllerObj, param1Int1, param1Int2);
    }
    
    public final void unregisterCallback(MediaControllerCompat.Callback param1Callback) {
      MediaControllerCompatApi21.unregisterCallback(this.mControllerObj, param1Callback.mCallbackObj);
      if (this.mExtraBinder != null)
        try {
          ExtraCallback extraCallback = this.mCallbackMap.remove(param1Callback);
          if (extraCallback != null)
            this.mExtraBinder.unregisterCallbackListener(extraCallback); 
          return;
        } catch (RemoteException null) {
          Log.e("MediaControllerCompat", "Dead object in unregisterCallback.", (Throwable)null);
          return;
        }  
      synchronized (this.mPendingCallbacks) {
        this.mPendingCallbacks.remove(null);
        return;
      } 
    }
    
    private static class ExtraBinderRequestResultReceiver extends ResultReceiver {
      private WeakReference<MediaControllerCompat.MediaControllerImplApi21> mMediaControllerImpl;
      
      public ExtraBinderRequestResultReceiver(MediaControllerCompat.MediaControllerImplApi21 param2MediaControllerImplApi21, Handler param2Handler) {
        super(param2Handler);
        this.mMediaControllerImpl = new WeakReference<MediaControllerCompat.MediaControllerImplApi21>(param2MediaControllerImplApi21);
      }
      
      protected void onReceiveResult(int param2Int, Bundle param2Bundle) {
        MediaControllerCompat.MediaControllerImplApi21 mediaControllerImplApi21 = this.mMediaControllerImpl.get();
        if (mediaControllerImplApi21 == null || param2Bundle == null)
          return; 
        MediaControllerCompat.MediaControllerImplApi21.access$002(mediaControllerImplApi21, IMediaSession.Stub.asInterface(BundleCompat.getBinder(param2Bundle, "android.support.v4.media.session.EXTRA_BINDER")));
        mediaControllerImplApi21.processPendingCallbacks();
      }
    }
    
    private class ExtraCallback extends IMediaControllerCallback.Stub {
      private MediaControllerCompat.Callback mCallback;
      
      ExtraCallback(MediaControllerCompat.Callback param2Callback) {
        this.mCallback = param2Callback;
      }
      
      public void onEvent(final String event, final Bundle extras) throws RemoteException {
        this.mCallback.mHandler.post(new Runnable() {
              public void run() {
                MediaControllerCompat.MediaControllerImplApi21.ExtraCallback.this.mCallback.onSessionEvent(event, extras);
              }
            });
      }
      
      public void onExtrasChanged(Bundle param2Bundle) throws RemoteException {
        throw new AssertionError();
      }
      
      public void onMetadataChanged(MediaMetadataCompat param2MediaMetadataCompat) throws RemoteException {
        throw new AssertionError();
      }
      
      public void onPlaybackStateChanged(final PlaybackStateCompat state) throws RemoteException {
        this.mCallback.mHandler.post(new Runnable() {
              public void run() {
                MediaControllerCompat.MediaControllerImplApi21.ExtraCallback.this.mCallback.onPlaybackStateChanged(state);
              }
            });
      }
      
      public void onQueueChanged(List<MediaSessionCompat.QueueItem> param2List) throws RemoteException {
        throw new AssertionError();
      }
      
      public void onQueueTitleChanged(CharSequence param2CharSequence) throws RemoteException {
        throw new AssertionError();
      }
      
      public void onRepeatModeChanged(final int repeatMode) throws RemoteException {
        this.mCallback.mHandler.post(new Runnable() {
              public void run() {
                MediaControllerCompat.MediaControllerImplApi21.ExtraCallback.this.mCallback.onRepeatModeChanged(repeatMode);
              }
            });
      }
      
      public void onSessionDestroyed() throws RemoteException {
        throw new AssertionError();
      }
      
      public void onShuffleModeChanged(final boolean enabled) throws RemoteException {
        this.mCallback.mHandler.post(new Runnable() {
              public void run() {
                MediaControllerCompat.MediaControllerImplApi21.ExtraCallback.this.mCallback.onShuffleModeChanged(enabled);
              }
            });
      }
      
      public void onVolumeInfoChanged(ParcelableVolumeInfo param2ParcelableVolumeInfo) throws RemoteException {
        throw new AssertionError();
      }
    }
    
    class null implements Runnable {
      public void run() {
        this.this$1.mCallback.onSessionEvent(event, extras);
      }
    }
    
    class null implements Runnable {
      public void run() {
        this.this$1.mCallback.onPlaybackStateChanged(state);
      }
    }
    
    class null implements Runnable {
      public void run() {
        this.this$1.mCallback.onRepeatModeChanged(repeatMode);
      }
    }
    
    class null implements Runnable {
      public void run() {
        this.this$1.mCallback.onShuffleModeChanged(enabled);
      }
    }
  }
  
  private static class ExtraBinderRequestResultReceiver extends ResultReceiver {
    private WeakReference<MediaControllerCompat.MediaControllerImplApi21> mMediaControllerImpl;
    
    public ExtraBinderRequestResultReceiver(MediaControllerCompat.MediaControllerImplApi21 param1MediaControllerImplApi21, Handler param1Handler) {
      super(param1Handler);
      this.mMediaControllerImpl = new WeakReference<MediaControllerCompat.MediaControllerImplApi21>(param1MediaControllerImplApi21);
    }
    
    protected void onReceiveResult(int param1Int, Bundle param1Bundle) {
      MediaControllerCompat.MediaControllerImplApi21 mediaControllerImplApi21 = this.mMediaControllerImpl.get();
      if (mediaControllerImplApi21 == null || param1Bundle == null)
        return; 
      MediaControllerCompat.MediaControllerImplApi21.access$002(mediaControllerImplApi21, IMediaSession.Stub.asInterface(BundleCompat.getBinder(param1Bundle, "android.support.v4.media.session.EXTRA_BINDER")));
      mediaControllerImplApi21.processPendingCallbacks();
    }
  }
  
  private class ExtraCallback extends IMediaControllerCallback.Stub {
    private MediaControllerCompat.Callback mCallback;
    
    ExtraCallback(MediaControllerCompat.Callback param1Callback) {
      this.mCallback = param1Callback;
    }
    
    public void onEvent(final String event, final Bundle extras) throws RemoteException {
      this.mCallback.mHandler.post(new Runnable() {
            public void run() {
              MediaControllerCompat.MediaControllerImplApi21.ExtraCallback.this.mCallback.onSessionEvent(event, extras);
            }
          });
    }
    
    public void onExtrasChanged(Bundle param1Bundle) throws RemoteException {
      throw new AssertionError();
    }
    
    public void onMetadataChanged(MediaMetadataCompat param1MediaMetadataCompat) throws RemoteException {
      throw new AssertionError();
    }
    
    public void onPlaybackStateChanged(final PlaybackStateCompat state) throws RemoteException {
      this.mCallback.mHandler.post(new Runnable() {
            public void run() {
              MediaControllerCompat.MediaControllerImplApi21.ExtraCallback.this.mCallback.onPlaybackStateChanged(state);
            }
          });
    }
    
    public void onQueueChanged(List<MediaSessionCompat.QueueItem> param1List) throws RemoteException {
      throw new AssertionError();
    }
    
    public void onQueueTitleChanged(CharSequence param1CharSequence) throws RemoteException {
      throw new AssertionError();
    }
    
    public void onRepeatModeChanged(final int repeatMode) throws RemoteException {
      this.mCallback.mHandler.post(new Runnable() {
            public void run() {
              MediaControllerCompat.MediaControllerImplApi21.ExtraCallback.this.mCallback.onRepeatModeChanged(repeatMode);
            }
          });
    }
    
    public void onSessionDestroyed() throws RemoteException {
      throw new AssertionError();
    }
    
    public void onShuffleModeChanged(final boolean enabled) throws RemoteException {
      this.mCallback.mHandler.post(new Runnable() {
            public void run() {
              MediaControllerCompat.MediaControllerImplApi21.ExtraCallback.this.mCallback.onShuffleModeChanged(enabled);
            }
          });
    }
    
    public void onVolumeInfoChanged(ParcelableVolumeInfo param1ParcelableVolumeInfo) throws RemoteException {
      throw new AssertionError();
    }
  }
  
  class null implements Runnable {
    public void run() {
      this.this$1.mCallback.onSessionEvent(event, extras);
    }
  }
  
  class null implements Runnable {
    public void run() {
      this.this$1.mCallback.onPlaybackStateChanged(state);
    }
  }
  
  class null implements Runnable {
    public void run() {
      this.this$1.mCallback.onRepeatModeChanged(repeatMode);
    }
  }
  
  class null implements Runnable {
    public void run() {
      this.this$1.mCallback.onShuffleModeChanged(enabled);
    }
  }
  
  static class MediaControllerImplApi23 extends MediaControllerImplApi21 {
    public MediaControllerImplApi23(Context param1Context, MediaSessionCompat.Token param1Token) throws RemoteException {
      super(param1Context, param1Token);
    }
    
    public MediaControllerImplApi23(Context param1Context, MediaSessionCompat param1MediaSessionCompat) {
      super(param1Context, param1MediaSessionCompat);
    }
    
    public MediaControllerCompat.TransportControls getTransportControls() {
      Object object = MediaControllerCompatApi21.getTransportControls(this.mControllerObj);
      return (object != null) ? new MediaControllerCompat.TransportControlsApi23(object) : null;
    }
  }
  
  static class MediaControllerImplApi24 extends MediaControllerImplApi23 {
    public MediaControllerImplApi24(Context param1Context, MediaSessionCompat.Token param1Token) throws RemoteException {
      super(param1Context, param1Token);
    }
    
    public MediaControllerImplApi24(Context param1Context, MediaSessionCompat param1MediaSessionCompat) {
      super(param1Context, param1MediaSessionCompat);
    }
    
    public MediaControllerCompat.TransportControls getTransportControls() {
      Object object = MediaControllerCompatApi21.getTransportControls(this.mControllerObj);
      return (object != null) ? new MediaControllerCompat.TransportControlsApi24(object) : null;
    }
  }
  
  static class MediaControllerImplBase implements MediaControllerImpl {
    private IMediaSession mBinder;
    
    private MediaSessionCompat.Token mToken;
    
    private MediaControllerCompat.TransportControls mTransportControls;
    
    public MediaControllerImplBase(MediaSessionCompat.Token param1Token) {
      this.mToken = param1Token;
      this.mBinder = IMediaSession.Stub.asInterface((IBinder)param1Token.getToken());
    }
    
    public void addQueueItem(MediaDescriptionCompat param1MediaDescriptionCompat) {
      try {
        if ((this.mBinder.getFlags() & 0x4L) == 0L)
          throw new UnsupportedOperationException("This session doesn't support queue management operations"); 
      } catch (RemoteException remoteException) {
        Log.e("MediaControllerCompat", "Dead object in addQueueItem.", (Throwable)remoteException);
        return;
      } 
      this.mBinder.addQueueItem((MediaDescriptionCompat)remoteException);
    }
    
    public void addQueueItem(MediaDescriptionCompat param1MediaDescriptionCompat, int param1Int) {
      try {
        if ((this.mBinder.getFlags() & 0x4L) == 0L)
          throw new UnsupportedOperationException("This session doesn't support queue management operations"); 
      } catch (RemoteException remoteException) {
        Log.e("MediaControllerCompat", "Dead object in addQueueItemAt.", (Throwable)remoteException);
        return;
      } 
      this.mBinder.addQueueItemAt((MediaDescriptionCompat)remoteException, param1Int);
    }
    
    public void adjustVolume(int param1Int1, int param1Int2) {
      try {
        this.mBinder.adjustVolume(param1Int1, param1Int2, (String)null);
        return;
      } catch (RemoteException remoteException) {
        Log.e("MediaControllerCompat", "Dead object in adjustVolume.", (Throwable)remoteException);
        return;
      } 
    }
    
    public boolean dispatchMediaButtonEvent(KeyEvent param1KeyEvent) {
      if (param1KeyEvent == null)
        throw new IllegalArgumentException("event may not be null."); 
      try {
        this.mBinder.sendMediaButton(param1KeyEvent);
      } catch (RemoteException remoteException) {
        Log.e("MediaControllerCompat", "Dead object in dispatchMediaButtonEvent.", (Throwable)remoteException);
      } 
      return false;
    }
    
    public Bundle getExtras() {
      try {
        return this.mBinder.getExtras();
      } catch (RemoteException remoteException) {
        Log.e("MediaControllerCompat", "Dead object in getExtras.", (Throwable)remoteException);
        return null;
      } 
    }
    
    public long getFlags() {
      try {
        return this.mBinder.getFlags();
      } catch (RemoteException remoteException) {
        Log.e("MediaControllerCompat", "Dead object in getFlags.", (Throwable)remoteException);
        return 0L;
      } 
    }
    
    public Object getMediaController() {
      return null;
    }
    
    public MediaMetadataCompat getMetadata() {
      try {
        return this.mBinder.getMetadata();
      } catch (RemoteException remoteException) {
        Log.e("MediaControllerCompat", "Dead object in getMetadata.", (Throwable)remoteException);
        return null;
      } 
    }
    
    public String getPackageName() {
      try {
        return this.mBinder.getPackageName();
      } catch (RemoteException remoteException) {
        Log.e("MediaControllerCompat", "Dead object in getPackageName.", (Throwable)remoteException);
        return null;
      } 
    }
    
    public MediaControllerCompat.PlaybackInfo getPlaybackInfo() {
      try {
        ParcelableVolumeInfo parcelableVolumeInfo = this.mBinder.getVolumeAttributes();
        return new MediaControllerCompat.PlaybackInfo(parcelableVolumeInfo.volumeType, parcelableVolumeInfo.audioStream, parcelableVolumeInfo.controlType, parcelableVolumeInfo.maxVolume, parcelableVolumeInfo.currentVolume);
      } catch (RemoteException remoteException) {
        Log.e("MediaControllerCompat", "Dead object in getPlaybackInfo.", (Throwable)remoteException);
        return null;
      } 
    }
    
    public PlaybackStateCompat getPlaybackState() {
      try {
        return this.mBinder.getPlaybackState();
      } catch (RemoteException remoteException) {
        Log.e("MediaControllerCompat", "Dead object in getPlaybackState.", (Throwable)remoteException);
        return null;
      } 
    }
    
    public List<MediaSessionCompat.QueueItem> getQueue() {
      try {
        return this.mBinder.getQueue();
      } catch (RemoteException remoteException) {
        Log.e("MediaControllerCompat", "Dead object in getQueue.", (Throwable)remoteException);
        return null;
      } 
    }
    
    public CharSequence getQueueTitle() {
      try {
        return this.mBinder.getQueueTitle();
      } catch (RemoteException remoteException) {
        Log.e("MediaControllerCompat", "Dead object in getQueueTitle.", (Throwable)remoteException);
        return null;
      } 
    }
    
    public int getRatingType() {
      try {
        return this.mBinder.getRatingType();
      } catch (RemoteException remoteException) {
        Log.e("MediaControllerCompat", "Dead object in getRatingType.", (Throwable)remoteException);
        return 0;
      } 
    }
    
    public int getRepeatMode() {
      try {
        return this.mBinder.getRepeatMode();
      } catch (RemoteException remoteException) {
        Log.e("MediaControllerCompat", "Dead object in getRepeatMode.", (Throwable)remoteException);
        return 0;
      } 
    }
    
    public PendingIntent getSessionActivity() {
      try {
        return this.mBinder.getLaunchPendingIntent();
      } catch (RemoteException remoteException) {
        Log.e("MediaControllerCompat", "Dead object in getSessionActivity.", (Throwable)remoteException);
        return null;
      } 
    }
    
    public MediaControllerCompat.TransportControls getTransportControls() {
      if (this.mTransportControls == null)
        this.mTransportControls = new MediaControllerCompat.TransportControlsBase(this.mBinder); 
      return this.mTransportControls;
    }
    
    public boolean isShuffleModeEnabled() {
      try {
        return this.mBinder.isShuffleModeEnabled();
      } catch (RemoteException remoteException) {
        Log.e("MediaControllerCompat", "Dead object in isShuffleModeEnabled.", (Throwable)remoteException);
        return false;
      } 
    }
    
    public void registerCallback(MediaControllerCompat.Callback param1Callback, Handler param1Handler) {
      if (param1Callback == null)
        throw new IllegalArgumentException("callback may not be null."); 
      try {
        this.mBinder.asBinder().linkToDeath(param1Callback, 0);
        this.mBinder.registerCallbackListener((IMediaControllerCallback)param1Callback.mCallbackObj);
        param1Callback.setHandler(param1Handler);
        param1Callback.mRegistered = true;
        return;
      } catch (RemoteException remoteException) {
        Log.e("MediaControllerCompat", "Dead object in registerCallback.", (Throwable)remoteException);
        param1Callback.onSessionDestroyed();
        return;
      } 
    }
    
    public void removeQueueItem(MediaDescriptionCompat param1MediaDescriptionCompat) {
      try {
        if ((this.mBinder.getFlags() & 0x4L) == 0L)
          throw new UnsupportedOperationException("This session doesn't support queue management operations"); 
      } catch (RemoteException remoteException) {
        Log.e("MediaControllerCompat", "Dead object in removeQueueItem.", (Throwable)remoteException);
        return;
      } 
      this.mBinder.removeQueueItem((MediaDescriptionCompat)remoteException);
    }
    
    public void removeQueueItemAt(int param1Int) {
      try {
        if ((this.mBinder.getFlags() & 0x4L) == 0L)
          throw new UnsupportedOperationException("This session doesn't support queue management operations"); 
      } catch (RemoteException remoteException) {
        Log.e("MediaControllerCompat", "Dead object in removeQueueItemAt.", (Throwable)remoteException);
        return;
      } 
      this.mBinder.removeQueueItemAt(param1Int);
    }
    
    public void sendCommand(String param1String, Bundle param1Bundle, ResultReceiver param1ResultReceiver) {
      try {
        this.mBinder.sendCommand(param1String, param1Bundle, new MediaSessionCompat.ResultReceiverWrapper(param1ResultReceiver));
        return;
      } catch (RemoteException remoteException) {
        Log.e("MediaControllerCompat", "Dead object in sendCommand.", (Throwable)remoteException);
        return;
      } 
    }
    
    public void setVolumeTo(int param1Int1, int param1Int2) {
      try {
        this.mBinder.setVolumeTo(param1Int1, param1Int2, (String)null);
        return;
      } catch (RemoteException remoteException) {
        Log.e("MediaControllerCompat", "Dead object in setVolumeTo.", (Throwable)remoteException);
        return;
      } 
    }
    
    public void unregisterCallback(MediaControllerCompat.Callback param1Callback) {
      if (param1Callback == null)
        throw new IllegalArgumentException("callback may not be null."); 
      try {
        this.mBinder.unregisterCallbackListener((IMediaControllerCallback)param1Callback.mCallbackObj);
        this.mBinder.asBinder().unlinkToDeath(param1Callback, 0);
        param1Callback.mRegistered = false;
        return;
      } catch (RemoteException remoteException) {
        Log.e("MediaControllerCompat", "Dead object in unregisterCallback.", (Throwable)remoteException);
        return;
      } 
    }
  }
  
  public static final class PlaybackInfo {
    public static final int PLAYBACK_TYPE_LOCAL = 1;
    
    public static final int PLAYBACK_TYPE_REMOTE = 2;
    
    private final int mAudioStream;
    
    private final int mCurrentVolume;
    
    private final int mMaxVolume;
    
    private final int mPlaybackType;
    
    private final int mVolumeControl;
    
    PlaybackInfo(int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5) {
      this.mPlaybackType = param1Int1;
      this.mAudioStream = param1Int2;
      this.mVolumeControl = param1Int3;
      this.mMaxVolume = param1Int4;
      this.mCurrentVolume = param1Int5;
    }
    
    public int getAudioStream() {
      return this.mAudioStream;
    }
    
    public int getCurrentVolume() {
      return this.mCurrentVolume;
    }
    
    public int getMaxVolume() {
      return this.mMaxVolume;
    }
    
    public int getPlaybackType() {
      return this.mPlaybackType;
    }
    
    public int getVolumeControl() {
      return this.mVolumeControl;
    }
  }
  
  public static abstract class TransportControls {
    public abstract void fastForward();
    
    public abstract void pause();
    
    public abstract void play();
    
    public abstract void playFromMediaId(String param1String, Bundle param1Bundle);
    
    public abstract void playFromSearch(String param1String, Bundle param1Bundle);
    
    public abstract void playFromUri(Uri param1Uri, Bundle param1Bundle);
    
    public abstract void prepare();
    
    public abstract void prepareFromMediaId(String param1String, Bundle param1Bundle);
    
    public abstract void prepareFromSearch(String param1String, Bundle param1Bundle);
    
    public abstract void prepareFromUri(Uri param1Uri, Bundle param1Bundle);
    
    public abstract void rewind();
    
    public abstract void seekTo(long param1Long);
    
    public abstract void sendCustomAction(PlaybackStateCompat.CustomAction param1CustomAction, Bundle param1Bundle);
    
    public abstract void sendCustomAction(String param1String, Bundle param1Bundle);
    
    public abstract void setRating(RatingCompat param1RatingCompat);
    
    public abstract void setRepeatMode(int param1Int);
    
    public abstract void setShuffleModeEnabled(boolean param1Boolean);
    
    public abstract void skipToNext();
    
    public abstract void skipToPrevious();
    
    public abstract void skipToQueueItem(long param1Long);
    
    public abstract void stop();
  }
  
  static class TransportControlsApi21 extends TransportControls {
    protected final Object mControlsObj;
    
    public TransportControlsApi21(Object param1Object) {
      this.mControlsObj = param1Object;
    }
    
    public void fastForward() {
      MediaControllerCompatApi21.TransportControls.fastForward(this.mControlsObj);
    }
    
    public void pause() {
      MediaControllerCompatApi21.TransportControls.pause(this.mControlsObj);
    }
    
    public void play() {
      MediaControllerCompatApi21.TransportControls.play(this.mControlsObj);
    }
    
    public void playFromMediaId(String param1String, Bundle param1Bundle) {
      MediaControllerCompatApi21.TransportControls.playFromMediaId(this.mControlsObj, param1String, param1Bundle);
    }
    
    public void playFromSearch(String param1String, Bundle param1Bundle) {
      MediaControllerCompatApi21.TransportControls.playFromSearch(this.mControlsObj, param1String, param1Bundle);
    }
    
    public void playFromUri(Uri param1Uri, Bundle param1Bundle) {
      if (param1Uri == null || Uri.EMPTY.equals(param1Uri))
        throw new IllegalArgumentException("You must specify a non-empty Uri for playFromUri."); 
      Bundle bundle = new Bundle();
      bundle.putParcelable("android.support.v4.media.session.action.ARGUMENT_URI", (Parcelable)param1Uri);
      bundle.putParcelable("android.support.v4.media.session.action.ARGUMENT_EXTRAS", (Parcelable)param1Bundle);
      sendCustomAction("android.support.v4.media.session.action.PLAY_FROM_URI", bundle);
    }
    
    public void prepare() {
      sendCustomAction("android.support.v4.media.session.action.PREPARE", (Bundle)null);
    }
    
    public void prepareFromMediaId(String param1String, Bundle param1Bundle) {
      Bundle bundle = new Bundle();
      bundle.putString("android.support.v4.media.session.action.ARGUMENT_MEDIA_ID", param1String);
      bundle.putBundle("android.support.v4.media.session.action.ARGUMENT_EXTRAS", param1Bundle);
      sendCustomAction("android.support.v4.media.session.action.PREPARE_FROM_MEDIA_ID", bundle);
    }
    
    public void prepareFromSearch(String param1String, Bundle param1Bundle) {
      Bundle bundle = new Bundle();
      bundle.putString("android.support.v4.media.session.action.ARGUMENT_QUERY", param1String);
      bundle.putBundle("android.support.v4.media.session.action.ARGUMENT_EXTRAS", param1Bundle);
      sendCustomAction("android.support.v4.media.session.action.PREPARE_FROM_SEARCH", bundle);
    }
    
    public void prepareFromUri(Uri param1Uri, Bundle param1Bundle) {
      Bundle bundle = new Bundle();
      bundle.putParcelable("android.support.v4.media.session.action.ARGUMENT_URI", (Parcelable)param1Uri);
      bundle.putBundle("android.support.v4.media.session.action.ARGUMENT_EXTRAS", param1Bundle);
      sendCustomAction("android.support.v4.media.session.action.PREPARE_FROM_URI", bundle);
    }
    
    public void rewind() {
      MediaControllerCompatApi21.TransportControls.rewind(this.mControlsObj);
    }
    
    public void seekTo(long param1Long) {
      MediaControllerCompatApi21.TransportControls.seekTo(this.mControlsObj, param1Long);
    }
    
    public void sendCustomAction(PlaybackStateCompat.CustomAction param1CustomAction, Bundle param1Bundle) {
      MediaControllerCompatApi21.TransportControls.sendCustomAction(this.mControlsObj, param1CustomAction.getAction(), param1Bundle);
    }
    
    public void sendCustomAction(String param1String, Bundle param1Bundle) {
      MediaControllerCompatApi21.TransportControls.sendCustomAction(this.mControlsObj, param1String, param1Bundle);
    }
    
    public void setRating(RatingCompat param1RatingCompat) {
      Object object = this.mControlsObj;
      if (param1RatingCompat != null) {
        Object object1 = param1RatingCompat.getRating();
      } else {
        param1RatingCompat = null;
      } 
      MediaControllerCompatApi21.TransportControls.setRating(object, param1RatingCompat);
    }
    
    public void setRepeatMode(int param1Int) {
      Bundle bundle = new Bundle();
      bundle.putInt("android.support.v4.media.session.action.ARGUMENT_REPEAT_MODE", param1Int);
      sendCustomAction("android.support.v4.media.session.action.SET_REPEAT_MODE", bundle);
    }
    
    public void setShuffleModeEnabled(boolean param1Boolean) {
      Bundle bundle = new Bundle();
      bundle.putBoolean("android.support.v4.media.session.action.ARGUMENT_SHUFFLE_MODE_ENABLED", param1Boolean);
      sendCustomAction("android.support.v4.media.session.action.SET_SHUFFLE_MODE_ENABLED", bundle);
    }
    
    public void skipToNext() {
      MediaControllerCompatApi21.TransportControls.skipToNext(this.mControlsObj);
    }
    
    public void skipToPrevious() {
      MediaControllerCompatApi21.TransportControls.skipToPrevious(this.mControlsObj);
    }
    
    public void skipToQueueItem(long param1Long) {
      MediaControllerCompatApi21.TransportControls.skipToQueueItem(this.mControlsObj, param1Long);
    }
    
    public void stop() {
      MediaControllerCompatApi21.TransportControls.stop(this.mControlsObj);
    }
  }
  
  static class TransportControlsApi23 extends TransportControlsApi21 {
    public TransportControlsApi23(Object param1Object) {
      super(param1Object);
    }
    
    public void playFromUri(Uri param1Uri, Bundle param1Bundle) {
      MediaControllerCompatApi23.TransportControls.playFromUri(this.mControlsObj, param1Uri, param1Bundle);
    }
  }
  
  static class TransportControlsApi24 extends TransportControlsApi23 {
    public TransportControlsApi24(Object param1Object) {
      super(param1Object);
    }
    
    public void prepare() {
      MediaControllerCompatApi24.TransportControls.prepare(this.mControlsObj);
    }
    
    public void prepareFromMediaId(String param1String, Bundle param1Bundle) {
      MediaControllerCompatApi24.TransportControls.prepareFromMediaId(this.mControlsObj, param1String, param1Bundle);
    }
    
    public void prepareFromSearch(String param1String, Bundle param1Bundle) {
      MediaControllerCompatApi24.TransportControls.prepareFromSearch(this.mControlsObj, param1String, param1Bundle);
    }
    
    public void prepareFromUri(Uri param1Uri, Bundle param1Bundle) {
      MediaControllerCompatApi24.TransportControls.prepareFromUri(this.mControlsObj, param1Uri, param1Bundle);
    }
  }
  
  static class TransportControlsBase extends TransportControls {
    private IMediaSession mBinder;
    
    public TransportControlsBase(IMediaSession param1IMediaSession) {
      this.mBinder = param1IMediaSession;
    }
    
    public void fastForward() {
      try {
        this.mBinder.fastForward();
        return;
      } catch (RemoteException remoteException) {
        Log.e("MediaControllerCompat", "Dead object in fastForward.", (Throwable)remoteException);
        return;
      } 
    }
    
    public void pause() {
      try {
        this.mBinder.pause();
        return;
      } catch (RemoteException remoteException) {
        Log.e("MediaControllerCompat", "Dead object in pause.", (Throwable)remoteException);
        return;
      } 
    }
    
    public void play() {
      try {
        this.mBinder.play();
        return;
      } catch (RemoteException remoteException) {
        Log.e("MediaControllerCompat", "Dead object in play.", (Throwable)remoteException);
        return;
      } 
    }
    
    public void playFromMediaId(String param1String, Bundle param1Bundle) {
      try {
        this.mBinder.playFromMediaId(param1String, param1Bundle);
        return;
      } catch (RemoteException remoteException) {
        Log.e("MediaControllerCompat", "Dead object in playFromMediaId.", (Throwable)remoteException);
        return;
      } 
    }
    
    public void playFromSearch(String param1String, Bundle param1Bundle) {
      try {
        this.mBinder.playFromSearch(param1String, param1Bundle);
        return;
      } catch (RemoteException remoteException) {
        Log.e("MediaControllerCompat", "Dead object in playFromSearch.", (Throwable)remoteException);
        return;
      } 
    }
    
    public void playFromUri(Uri param1Uri, Bundle param1Bundle) {
      try {
        this.mBinder.playFromUri(param1Uri, param1Bundle);
        return;
      } catch (RemoteException remoteException) {
        Log.e("MediaControllerCompat", "Dead object in playFromUri.", (Throwable)remoteException);
        return;
      } 
    }
    
    public void prepare() {
      try {
        this.mBinder.prepare();
        return;
      } catch (RemoteException remoteException) {
        Log.e("MediaControllerCompat", "Dead object in prepare.", (Throwable)remoteException);
        return;
      } 
    }
    
    public void prepareFromMediaId(String param1String, Bundle param1Bundle) {
      try {
        this.mBinder.prepareFromMediaId(param1String, param1Bundle);
        return;
      } catch (RemoteException remoteException) {
        Log.e("MediaControllerCompat", "Dead object in prepareFromMediaId.", (Throwable)remoteException);
        return;
      } 
    }
    
    public void prepareFromSearch(String param1String, Bundle param1Bundle) {
      try {
        this.mBinder.prepareFromSearch(param1String, param1Bundle);
        return;
      } catch (RemoteException remoteException) {
        Log.e("MediaControllerCompat", "Dead object in prepareFromSearch.", (Throwable)remoteException);
        return;
      } 
    }
    
    public void prepareFromUri(Uri param1Uri, Bundle param1Bundle) {
      try {
        this.mBinder.prepareFromUri(param1Uri, param1Bundle);
        return;
      } catch (RemoteException remoteException) {
        Log.e("MediaControllerCompat", "Dead object in prepareFromUri.", (Throwable)remoteException);
        return;
      } 
    }
    
    public void rewind() {
      try {
        this.mBinder.rewind();
        return;
      } catch (RemoteException remoteException) {
        Log.e("MediaControllerCompat", "Dead object in rewind.", (Throwable)remoteException);
        return;
      } 
    }
    
    public void seekTo(long param1Long) {
      try {
        this.mBinder.seekTo(param1Long);
        return;
      } catch (RemoteException remoteException) {
        Log.e("MediaControllerCompat", "Dead object in seekTo.", (Throwable)remoteException);
        return;
      } 
    }
    
    public void sendCustomAction(PlaybackStateCompat.CustomAction param1CustomAction, Bundle param1Bundle) {
      sendCustomAction(param1CustomAction.getAction(), param1Bundle);
    }
    
    public void sendCustomAction(String param1String, Bundle param1Bundle) {
      try {
        this.mBinder.sendCustomAction(param1String, param1Bundle);
        return;
      } catch (RemoteException remoteException) {
        Log.e("MediaControllerCompat", "Dead object in sendCustomAction.", (Throwable)remoteException);
        return;
      } 
    }
    
    public void setRating(RatingCompat param1RatingCompat) {
      try {
        this.mBinder.rate(param1RatingCompat);
        return;
      } catch (RemoteException remoteException) {
        Log.e("MediaControllerCompat", "Dead object in setRating.", (Throwable)remoteException);
        return;
      } 
    }
    
    public void setRepeatMode(int param1Int) {
      try {
        this.mBinder.setRepeatMode(param1Int);
        return;
      } catch (RemoteException remoteException) {
        Log.e("MediaControllerCompat", "Dead object in setRepeatMode.", (Throwable)remoteException);
        return;
      } 
    }
    
    public void setShuffleModeEnabled(boolean param1Boolean) {
      try {
        this.mBinder.setShuffleModeEnabled(param1Boolean);
        return;
      } catch (RemoteException remoteException) {
        Log.e("MediaControllerCompat", "Dead object in setShuffleModeEnabled.", (Throwable)remoteException);
        return;
      } 
    }
    
    public void skipToNext() {
      try {
        this.mBinder.next();
        return;
      } catch (RemoteException remoteException) {
        Log.e("MediaControllerCompat", "Dead object in skipToNext.", (Throwable)remoteException);
        return;
      } 
    }
    
    public void skipToPrevious() {
      try {
        this.mBinder.previous();
        return;
      } catch (RemoteException remoteException) {
        Log.e("MediaControllerCompat", "Dead object in skipToPrevious.", (Throwable)remoteException);
        return;
      } 
    }
    
    public void skipToQueueItem(long param1Long) {
      try {
        this.mBinder.skipToQueueItem(param1Long);
        return;
      } catch (RemoteException remoteException) {
        Log.e("MediaControllerCompat", "Dead object in skipToQueueItem.", (Throwable)remoteException);
        return;
      } 
    }
    
    public void stop() {
      try {
        this.mBinder.stop();
        return;
      } catch (RemoteException remoteException) {
        Log.e("MediaControllerCompat", "Dead object in stop.", (Throwable)remoteException);
        return;
      } 
    }
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\android\support\v4\media\session\MediaControllerCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */