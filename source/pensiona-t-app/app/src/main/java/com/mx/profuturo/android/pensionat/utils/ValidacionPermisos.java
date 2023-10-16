package com.mx.profuturo.android.pensionat.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.mx.profuturo.android.pensionat.data.local.db.realm.RealmConfiguracion;

import java.util.ArrayList;

public class ValidacionPermisos {

    public static final int REQUEST_CODE = 11;
    public final int REQUEST_ALL_PERMISSIONS = 123;
    public int REQUEST_CODE_RESULT = 0;

    public PermissionsResult listener = null;

    public ArrayList<StringPermission> permissions = new ArrayList<>();
    public boolean minSdk29 = false;

    private static volatile ValidacionPermisos instance;
    public Activity mActivity;

    private ValidacionPermisos(Activity mActivity){
        minSdk29 = Build.VERSION.SDK_INT <= Build.VERSION_CODES.Q;
        this.mActivity = mActivity;
    }

    public static ValidacionPermisos getInstance(Activity mActivity){
        if(instance == null){
            synchronized (ValidacionPermisos.class){
                if(instance == null) instance = new ValidacionPermisos(mActivity);
            }
        }
        return instance;
    }

    public ValidacionPermisos setListener(PermissionsResult listener){
        this.listener = listener;
        return this;
    }

    public ValidacionPermisos addArrayPermission(ArrayList<StringPermission> permissions){
        for(int i=0; i<=permissions.size()-1; i++){
            if(minSdk29){
                this.permissions.add(new StringPermission(permissions.get(i).Permission, permissions.get(i).Code));
            }else{
                if(!permissions.get(i).Permission.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE))
                    this.permissions.add(new StringPermission(permissions.get(i).Permission, permissions.get(i).Code));
            }

        }
        return this;
    }

    public ValidacionPermisos addPermission(String permission, int Code){
        if(minSdk29){
            this.permissions.add(new StringPermission(permission, Code));
        }else{
            if(!permission.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE))
                this.permissions.add(new StringPermission(permission, Code));
        }
        return this;
    }

    public void check(){
        validarPermisosApp();
    }

    public void validarPermisosApp() {
        //A partir de api 29 ya no es necesario pedir el permiso de escritura WRITE_EXTERNAL_STORAGE
        // el permiso siempre devuelve 'false' pero el sistema no lo pide al usuario
        //initPermissions();
        for(StringPermission permission: permissions){
            if(ContextCompat.checkSelfPermission(RealmConfiguracion.getContext(), permission.Permission) != PackageManager.PERMISSION_GRANTED){

                String[] newPermissions = new String[permissions.size()];

                for(int i=0; i<=this.permissions.size()-1; i++){
                    newPermissions[i] = permissions.get(i).Permission;
                }

                if(REQUEST_CODE_RESULT == 0){
                    ActivityCompat.requestPermissions(mActivity,newPermissions,
                            REQUEST_ALL_PERMISSIONS);
                }else{
                    ActivityCompat.requestPermissions(mActivity,newPermissions,
                            REQUEST_CODE_RESULT);
                }


            }
        }
    }

    public ValidacionPermisos setCodeResult(int CodeResult){
        this.REQUEST_CODE_RESULT = CodeResult;
        return this;
    }

    public void isPermissionGranted(boolean permission) {
        if (!permission) {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", mActivity.getPackageName(), null);
            intent.setData(uri);
            RealmConfiguracion.getContext().startActivity(intent);
            Toast.makeText(RealmConfiguracion.getContext(), "Por favor conceda el acceso", Toast.LENGTH_LONG).show();
        }
    }

    public static class StringPermission{
        public int Code = 0;
        public String Permission = "";
        public StringPermission(String Permission, int Code ){
            this.Code = Code;
            this.Permission = Permission;
        }
    }


}


