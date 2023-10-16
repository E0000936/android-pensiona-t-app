package com.mx.profuturo.android.pensionat.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class RequestPermissionUtil {

    public static final int REQUEST_CODE = 11;

    public interface CheckPermissionsListener {
        void onPermissionsGranted();
    }

    /**
     * Clase que valida los permisos necesarios para ejecutar correctamente la aplicacion
     */
    public static void checkPermissions(Context mContext, CheckPermissionsListener listener) {
        //Se validan individualmente los permisos para poder dar un mejor manteminieto al codigo

        List<String> permList = new ArrayList<>();//Permisos a solicitar
        List<String> permListAux = new ArrayList<>();//Permisos a solicitar

        //permListAux.add(Manifest.permission.READ_EXTERNAL_STORAGE);

        //A partir del SDK 16 ya no se puede solicitar el permiso READ_LOGS, solo lo puede
        //pedir un dispositivo con root
//        if (ContextCompat.checkSelfPermission(activity.getApplicationContext(),
//                Manifest.permission.READ_LOGS) != PackageManager.PERMISSION_GRANTED) {
//            permList.add(Manifest.permission.READ_LOGS);
//        }

        if(permListAux.size() >= 1){
            for (String permission : permListAux) {
                if ((ContextCompat.checkSelfPermission(mContext.getApplicationContext(), permission) != PackageManager.PERMISSION_GRANTED))
                {
                    permList.add(permission);
                }
            }
        }


        //A partir del SDK 29 ya no es necesario pedir el permiso de escritura
        // WRITE_EXTERNAL_STORAGE el permiso siempre devuelve 'PERMISSION_DENIED'
        // pero el sistema no lo pide al usuario
        if ((ContextCompat.checkSelfPermission(mContext.getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) &&
                (ContextCompat.checkSelfPermission(mContext.getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                && Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            permList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            permList.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }

        if (permList.isEmpty()) {
            if(listener!= null) listener.onPermissionsGranted();
        } else {
            String[] permissions = new String[permList.size()];

            ActivityCompat.requestPermissions((Activity) mContext, permList.toArray(permissions),
                    REQUEST_CODE);
        }
    }

    /**
     * Se valida la respuesta de la peticion de los permisos
     * @param permissions lista de permisos
     * @param grantResults lista de resultados concedidos
     * @return <code>true</code> - si todos los permisos fueron concedidos
     * <p>
     * <code>false</code> - si al menos un permisos no fue concedido
     */
    public static boolean allPermissionsGranted(String[] permissions, int[] grantResults) {
        for (int count = 0; count < permissions.length; count++) {
            if (grantResults[count] != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }

        return true;
    }

    /**
     * Se muestra al usuario la pantalla de Configuración de la aplicación.
     * @param activity actividad donde se llama la peticion de permisos
     */
    public static void showAppSettings(Activity activity) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
        intent.setData(uri);
        activity.startActivity(intent);
        Toast.makeText(activity.getApplicationContext(), "Por favor conceda el acceso",
                Toast.LENGTH_LONG).show();
    }

    public static boolean checkOnePermission(Activity mActivity){
        if (Build.VERSION.SDK_INT >= 23) {
            if (mActivity.checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
                return false;
            }
        } else {
            return true;
        }
    }

}