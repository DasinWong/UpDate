package com.dasinwong.update;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dasinwong.permissionhelper.PermissionHelper;
import com.dasinwong.permissionhelper.PermissionListener;
import com.dasinwong.permissionhelper.PermissionResult;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import dalvik.system.BaseDexClassLoader;

public class MainActivity extends AppCompatActivity {

    private TextView tv_version;

    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PermissionHelper.with(this).auto(new PermissionListener() {
            @Override
            public void onComplete(Map<String, PermissionResult> resultMap) {
                BaseDexClassLoader
            }
        });
        tv_version = findViewById(R.id.tv_version);
        tv_version.setText(VersionUtil.getVersionName(this));
    }

    public native void bsPatch(String oldApkPath, String patchPath, String newApkPath);

    @SuppressLint("StaticFieldLeak")
    public void update(View view) {
        new AsyncTask<Void, Void, File>() {
            @Override
            protected File doInBackground(Void... voids) {
                String oldApkPath = getApplicationInfo().sourceDir;
                String patchPath = new File(Environment.getExternalStorageDirectory(), "patch").getAbsolutePath();
                String newApkPath = createFile();
                Log.e("HAHA",oldApkPath);
                Log.e("HAHA",patchPath);
                Log.e("HAHA",newApkPath);
                bsPatch(oldApkPath, patchPath, newApkPath);
                return new File(newApkPath);
            }

            @Override
            protected void onPostExecute(File file) {
                super.onPostExecute(file);
                Toast.makeText(MainActivity.this, "增量更新结束", Toast.LENGTH_SHORT).show();
            }
        }.execute();
    }

    private String createFile() {
        File apk = new File(Environment.getExternalStorageDirectory(), "new.apk");
        if (!apk.exists()) {
            try {
                apk.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return apk.getAbsolutePath();
    }
}
