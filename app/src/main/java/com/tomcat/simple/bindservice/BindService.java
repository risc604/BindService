package com.tomcat.simple.bindservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import java.util.Date;

public class BindService extends Service
{
    private final IBinder   mBinder = new MyBinder();
    private Handler         handler = new Handler();
    private Runnable        showTime = new Runnable()
    {
        @Override
        public void run()
        {
            Log.i("mylog", new Date().toString());
            handler.postDelayed(this, 1000);    // 1s
        }
    };

    public class MyBinder extends Binder
    {
        public BindService getService()
        {
            return BindService.this;
        }
    }

    public void run()   // for public called function.
    {
        handler.post(showTime);
    }

    /*
    public BindService()
    {
    }
    */

    @Override
    public IBinder onBind(Intent intent0)
    {
        // TODO: Return the communication channel to the service.
        //throw new UnsupportedOperationException("Not yet implemented");
        Log.d("mylog", "onBind()");
        return mBinder;
    }

    public void onRebind(Intent intent)
    {
        Log.d("mylog", "onRebind()");
        super.onRebind(intent);
    }

    public boolean onUnbind(Intent intent)
    {
        Log.d("mylog", "onUnbind()");
        handler.removeCallbacks(showTime);
        return super.onUnbind(intent);
    }
}
