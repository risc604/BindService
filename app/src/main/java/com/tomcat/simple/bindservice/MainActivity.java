package com.tomcat.simple.bindservice;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity
{
    private BindService         cbs;
    private ServiceConnection   serviceConnection;
    private Context             context;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;
        serviceConnection = new ServiceConnection()
        {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service)
            {
                cbs = ((BindService.MyBinder)service).getService();
                Toast.makeText(context, cbs + "", Toast.LENGTH_SHORT).show();
                Log.i("mylog", "onServiceConnected()".toString());
            }

            @Override
            public void onServiceDisconnected(ComponentName name)
            {
                cbs = null;
                Log.i("mylog", "onServiceDisconnected()".toString());
            }
        };

        //controlProcess();
    }

    /*
    private void controlProcess()
    {
        context = this;

        serviceConnection = new ServiceConnection()
        {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service)
            {
                cbs = ((BindService.MyBinder)service).getService();
                Toast.makeText(context, cbs + "", Toast.LENGTH_SHORT).show();
                Log.i("mylog", "onServiceConnected()".toString());
            }

            @Override
            public void onServiceDisconnected(ComponentName name)
            {
                cbs = null;
                Log.i("mylog", "onServiceDisconnected()".toString());
            }
        };
    }
    */

    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.bindBtn:
                Intent bindIntent = new Intent(context, BindService.class);
                bindService(bindIntent, serviceConnection, Context.BIND_AUTO_CREATE);   //create service to bind.
                Toast.makeText(context, "Bind Service 已啟動！", Toast.LENGTH_SHORT).show();
                break;

            case R.id.invokeBtn:
                if (cbs != null)
                {
                    cbs.run();
                }
                else
                {
                    Toast.makeText(context, "cbs = null", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.unbindBtn:
                unbindService(serviceConnection);
                Toast.makeText(context, "Bind Service 已關閉！", Toast.LENGTH_SHORT).show();
                break;

            default:
                break;
        }
    }
}
