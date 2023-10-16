package com.mx.profuturo.android.pensionat.utils;

import android.content.ClipData;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.FileUtils;
import android.provider.OpenableColumns;
import android.util.Log;
import android.widget.Toast;

import com.mx.profuturo.android.pensionat.data.local.db.realm.RealmConfiguracion;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class CustomFilesManager {
    /**
     * Objeto clipdata para pasar la informacion
     **/
    private ClipData clipDatas = null;
    /**
     * Custom class de archivo y ui por archivo
     **/
    private ArrayList<FilesAndUri> files = null;
    /**
     * Variable para el singleton
     **/
    private static volatile CustomFilesManager instance;
    /**
     * Intent
     **/
    private Intent mIntent = null;

    /**
     * Singleton de la clase
     **/
    public static CustomFilesManager getInstance() {
        if (instance == null) {
            synchronized (CustomFilesManager.class) {
                if (instance == null) instance = new CustomFilesManager();
            }
        }
        return instance;
    }

    /**
     * Construtor que inicializa todas las variables
     **/
    private CustomFilesManager() {
        files = new ArrayList<>();
        mIntent = new Intent();
        mIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        mIntent.setAction(Intent.ACTION_PICK);
        mIntent.setAction(Intent.ACTION_SEND);
        mIntent.setAction(Intent.ACTION_SEND_MULTIPLE);
        mIntent.setAction(Intent.EXTRA_ALLOW_MULTIPLE);
        mIntent.setType("image/*");
    }

    /**
     * Se regresa el Intentt inicializado con los parametros principales
     **/
    public Intent getIntent() {
        return mIntent;
    }

    /**
     * Se regresa el clipData con todos los uri de los archivos añadidos
     **/
    public ClipData getClipDatas() {
        return clipDatas;
    }

    /**
     * Se regresa el custom class
     **/
    public ArrayList<FilesAndUri> getFilesAndUris() {
        return files;
    }

    /**
     * Se utiliza para borar ls archivo agregados a la lista
     **/
    public void clearFiles() {
        files.clear();
    }

    /**
     * Se añaden los archivos a una custom class que lleva el Archivo y su Uri
     * Este metodo va solo para las aplicaciones que comparten los archivos, motores o librerias
     * Este mtodo va de la mano con la configuracion del FILE PROVIDER en el manifest
     * y en archivo de file_paths XML de la carpeta resources.
     **/
    /*public void addFile(File file) {
        Uri uri = FileProvider.getUriForFile(LogAplication.getContext(), ReporteErrores.getAuthority(), file);
        if (!checkExists(file)) {
            files.add(new FilesAndUri(file, uri));
            if (clipDatas == null) clipDatas = ClipData.newRawUri(null, uri);
            clipDatas.addItem(new ClipData.Item(uri));
        }

    }*/

    /**
     * Checa si el archivo que se agrega al arreglo existe o no dentro del mismo arreglo
     * para no duplicar las entradas.
     * Este metodo va solo para las aplicaciones que comparten los archivos, motores o librerias
     * Este mtodo va de la mano con la configuracion del FILE PROVIDER en el manifest
     * y en archivo de file_paths XML de la carpeta resources.
     **/
    /*public Boolean checkExists(File file) {
        Boolean exist = false;
        Uri uri = FileProvider.getUriForFile(LogAplication.getContext(), LogAplication.getAuthority(), file);
        for (FilesAndUri archivo : files) {
            Uri uriLastPath = FileProvider.getUriForFile(LogAplication.getContext(), LogAplication.getAuthority(), archivo.getOriginFile());
            String lastPathOrigin = uri.getLastPathSegment();
            String lastPathNew = uri.getLastPathSegment();
            if (lastPathNew.equalsIgnoreCase(lastPathOrigin)){
                exist = true;
                break;
            }
        }
        return exist;
    }*/

    /**
     * Metodo que compara y devuelve la imagen desde un cursor o desde el almacenamiento interno
     **/
    public Bitmap getImageForCursorPosition(Cursor cursor, int position) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            Uri uri = Uri.parse(cursor.getString(position));
            return searchNameFile(uri);
        }
        return BitmapFactory.decodeFile(cursor.getString(position));
    }

    /** Metodo para regresar un dato String que viene adjunto en el Intent **/
    public String getPathFromIntent(Intent mIntent, Object type, String key){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            return searchNameFileFromIntent(mIntent.getData());
        }
        return mIntent.getStringExtra(key);
    }

    public String searchNameFileFromIntent(Uri uri) {
        String nameFile = uri.getLastPathSegment();
        String pathFile = "";
        for (FilesAndUri archivos : files) {
            Uri originFile = Uri.fromFile(archivos.getOriginFile());
            if (nameFile.equalsIgnoreCase(originFile.getLastPathSegment())) {
                pathFile = archivos.getDestinyUriStr();
                break;
            }
        }
        return pathFile;
    }

    /**
     * Metodo para buscar la imagen creada en la aplicacion anfritiona
     **/
    public Bitmap searchNameFile(Uri uri) {
        String nameFile = uri.getLastPathSegment();
        Bitmap pathFile = null;
        for (FilesAndUri archivos : files) {
            Uri originFile = Uri.fromFile(archivos.getOriginFile());
            if (nameFile.equalsIgnoreCase(originFile.getLastPathSegment())) {
                pathFile = archivos.getBitMap();
                break;
            }
        }
        return pathFile;
    }

    /**
     * Se aniade la uri en el clipdata
     **/
    public void addItemClipData(Uri uri) {
        if (clipDatas == null) {
            clipDatas = ClipData.newRawUri(null, uri);
        }else{
            clipDatas.addItem(new ClipData.Item(uri));
        }

    }

    /**
     * Funcion que crea el archivo, la uri original, y el Str del nuevo destino
     **/
    public void getUriStringForUris() {
        for (int i = 0; i <= clipDatas.getItemCount() - 1; i++) {
            Uri uriAux = clipDatas.getItemAt(i).getUri();
            String newFolder = getFileDirFromUri(uriAux);
            File destinationPath = new File(RealmConfiguracion.getContext().getExternalFilesDir(null) + File.separator + newFolder);
            if (!destinationPath.exists()) {
                if (destinationPath.mkdirs()) {
                    Log.wtf("FOLDER", "SE CREO CORRECTAMENTE");
                }
            }
            files.add(new FilesAndUri(new File(uriAux.getPath()), uriAux, uriAux.getPath(), destinationPath, Uri.fromFile(destinationPath), newFolder));
        }
    }

    /**
     * Funcion creada para la creacion y replica de las imagenes obtenidas
     **/
    public void createFoldersDestinations() {
        ContentResolver resolver = RealmConfiguracion.getContext().getContentResolver();
        for (FilesAndUri filesAndUri : files) {
            File destinationFile = new File(filesAndUri.getDestinyfile(), filesAndUri.getOriginUri().getLastPathSegment());
            if (!destinationFile.exists()) destinationFile.mkdir();
            try {
                InputStream iStream = resolver.openInputStream(filesAndUri.getOriginUri());
                Bitmap newBitmap = BitmapFactory.decodeStream(iStream);
                resolver.delete(filesAndUri.getOriginUri(), null, null);
                filesAndUri.setDestinyUriStr(destinationFile.getAbsolutePath());
                filesAndUri.setBitMap(newBitmap);
            } catch (Exception e) {
                Log.wtf("ERROR EXCEPTION", e.getMessage());
            }
        }
    }

    /**
     * Funcion creada por Marco para devolver la ruta de la app que mando a llamar al motor
     **/
    public String getFileDirFromUri(Uri imageUri) {
        String uAuthority = imageUri.getAuthority();
        List<String> uSegments = imageUri.getPathSegments();
        boolean pathStart = false;
        StringBuilder sbPath = new StringBuilder();

        for (int count = 0; count < uSegments.size() - 1; count++) {
            //Se previene la duplicacion del folder "files"
            if (pathStart && !uSegments.get(count).equalsIgnoreCase("files"))
                sbPath.append(uSegments.get(count)).append(File.separator);

            //Despues del segmento de nombre del paquete se empieza a tomar el folder para
            //duplicar el archivo
            if (uAuthority.contains(uSegments.get(count)))
                pathStart = true;
        }

        return sbPath.toString();
    }

    /** Metodo que ejecuta todas las tareas a realizar para android 11 **/
    public void executeWorksAndroid11(Intent data){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            if(null != data){
                if(data.getClipData() != null){
                    ClipData imageFinger = data.getClipData();
                    for (int i=0;i<imageFinger.getItemCount();i++) {
                        addItemClipData(imageFinger.getItemAt(i).getUri());
                    }
                    getUriStringForUris();
                    createFoldersDestinations();
                }
            }

        }
    }

    public String getFilePathFromIntent(Intent intent, String key) {
        String filePath = "";

        if(intent == null) return filePath;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q && intent.getData() != null) {
            ContentResolver contentResolver = RealmConfiguracion.getContext().getContentResolver();
            Uri imageUri = intent.getData();
            String destinationFolder = getFileDirFromUri2(imageUri);
            File destinationPath =  new File(RealmConfiguracion.getContext().getExternalFilesDir(null)
                    + File.separator + destinationFolder);

            //if(!destinationPath.exists()) destinationPath.mkdir();

            if(!destinationPath.exists()) destinationPath.mkdirs();
            File destinationFile = new File(destinationPath, imageUri.getLastPathSegment());
            try{
                Files.deleteIfExists(destinationFile.toPath());
                //File destinationFile = new File(destinationPath, imageUri.getLastPathSegment());
                FileUtils.copy(contentResolver.openInputStream(imageUri), new FileOutputStream(destinationFile));

                //if(!destinationFile.exists()) destinationFile.mkdir();
                //Files.deleteIfExists(destinationFile.toPath());
                Cursor cursor =  contentResolver.query(imageUri,null, null, null, null);
                contentResolver.delete(imageUri, null, null);
                cursor.close();
                return destinationFile.getPath();
            }catch(IOException ex){
                filePath = intent.getStringExtra(key);
                ex.printStackTrace();
            }

        }

        filePath = intent.getStringExtra(key);

        return filePath;
    }

    /**
     * Obtiene la carpeta donde se guardo el archivo obtenido a partir de FileProvider para hacer
     * la copia en el almacenamiento de la aplicacion
     * @param imageUri Uri proporcionada por el FileProvider
     * @return ruta de la carpeta donde se guardara la copia del arhivo
     */
    private String getFileDirFromUri2(Uri imageUri) {
        String uAuthority = imageUri.getAuthority();
        List<String> uSegments = imageUri.getPathSegments();
        boolean pathStart = false;
        StringBuilder sbPath = new StringBuilder();

        for(int count = 0; count < uSegments.size() - 1; count++) {
            //Se previene la duplicacion del folder "files"
            if(pathStart && !uSegments.get(count).equalsIgnoreCase("files"))
                sbPath.append(uSegments.get(count)).append(File.separator);

            //Despues del segmento de nombre del paquete se empieza a tomar el folder para
            //duplicar el archivo
            if(uAuthority.contains(uSegments.get(count)))
                pathStart = true;
        }

        return sbPath.toString();
    }

    /**
     * Custom class que lleva el archivo, uri, y uristr origen y destino por cada archivo
     **/
    public static class FilesAndUri {
        public File originFile = null;
        public Uri originUri = null;
        public String originUriStr = "";
        public File destinyfile = null;
        public Uri destinyUri = null;
        public String destinyUriStr = "";
        public Bitmap bitMap = null;

        public FilesAndUri(File originFile, Uri originUri) {
            this.originFile = originFile;
            this.originUri = originUri;
        }

        public FilesAndUri(File originFile, Uri originUri, String originUriStr) {
            this.originFile = originFile;
            this.originUri = originUri;
            this.originUriStr = originUriStr;
        }

        public FilesAndUri(File originfile, Uri originUri, String originUriStr, File destinyfile, Uri destinyUri, String destinyUriStr) {
            this.originFile = originfile;
            this.originUri = originUri;
            this.originUriStr = originUriStr;
            this.destinyfile = destinyfile;
            this.destinyUri = destinyUri;
            this.destinyUriStr = destinyUriStr;
        }

        public File getOriginFile() {
            return originFile;
        }

        public void setOriginFile(File originFile) {
            this.originFile = originFile;
        }

        public Uri getOriginUri() {
            return originUri;
        }

        public void setOriginUri(Uri originUri) {
            this.originUri = originUri;
        }

        public String getOriginUriStr() {
            return originUriStr;
        }

        public void setOriginUriStr(String originUriStr) {
            this.originUriStr = originUriStr;
        }

        public File getDestinyfile() {
            return destinyfile;
        }

        public void setDestinyfile(File destinyfile) {
            this.destinyfile = destinyfile;
        }

        public Uri getDestinyUri() {
            return destinyUri;
        }

        public void setDestinyUri(Uri destinyUri) {
            this.destinyUri = destinyUri;
        }

        public String getDestinyUriStr() {
            return destinyUriStr;
        }

        public void setDestinyUriStr(String destinyUriStr) {
            this.destinyUriStr = destinyUriStr;
        }

        public Bitmap getBitMap() {
            return bitMap;
        }

        public void setBitMap(Bitmap bitMap) {
            this.bitMap = bitMap;
        }
    }

}
