/*
 * Copyright (C) Profuturo All rights reserved. Todos los derechos reservados 2019.
 * mailto:
 *
 * Tramites digitales solo se puede distribuir con la autorización de Profuturo
 * License as published by Profuturo. Licencia publicada por Profuturo
 * version 1 .
 *
 * Tramites digitales is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 */
package com.mx.profuturo.android.pensionat.presentation.digitalizacion;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;
import com.mx.profuturo.android.pensionat.R;
import com.mx.profuturo.android.pensionat.data.local.db.realm.DBHelperRealm;
import com.mx.profuturo.android.pensionat.data.local.preferences.Sesion;
import com.mx.profuturo.android.pensionat.domain.model.Captura;
import com.mx.profuturo.android.pensionat.domain.model.Digitalizacion;
import com.mx.profuturo.android.pensionat.domain.model.Expediente;
import com.mx.profuturo.android.pensionat.presentation.busqueda.PrincipalActivity;
import com.mx.profuturo.android.pensionat.presentation.dialogs.AlertaDialogo;
import com.mx.profuturo.android.pensionat.presentation.dialogs.DialogoPresenter;
import com.mx.profuturo.android.pensionat.presentation.firmaFormulario.FormatoFormularioActivity;
import com.mx.profuturo.android.pensionat.utils.Constantes;
import com.mx.profuturo.android.pensionat.utils.CustomFilesManager;
import com.mx.profuturo.android.pensionat.utils.Utilidades;

import java.io.File;
import java.util.List;

import io.realm.RealmList;

/**
 * <h1>DigitalizacionFragment</h1>
 * Clase que contiene la lógica de la captura de las imagénes.
 *
 * @author Jose Antonio Acevedo Trejo
 * @version 1.0
 * @since 2019-03-20
 */
public class DigitalizacionFragment extends Fragment implements DigitalizacionAdaptador.OnItemClickListener {
    private static final int PHOTO_FILE = 1;
    private static final String DESC_OBSERVACIONES = "OBSERVACIONES";
    private Context contexto;
    private List<Digitalizacion> documentos;
    private TextView txtDocumento;
    private TextView txtDocumentoAyuda;
    private GridView gridCapturas;
    private int documentoActual = 0;
    private ImageButton btnCapturar;
    private DigitalizacionAdaptador adapter;
    private EditText edtComentarios;
    private TextInputLayout layoutComentarios;
    private TextView txtRequeridas;
    private Button btnSiguiente;

    public DigitalizacionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        contexto = container.getContext();
        PrincipalActivity principalActivity = (PrincipalActivity) getActivity();
        if (principalActivity != null) {
            principalActivity.cambiarTituloBarra(contexto.getString(R.string.digitalizacion_titulo), contexto.getString(R.string.digitalizacion_subtitulo));
        }
        return inflater.inflate(R.layout.fragmento_digitalizacion, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        Expediente expediente = DBHelperRealm.obtenerExpediente();
        edtComentarios = view.findViewById(R.id.edt_digitalizacion_observaciones);
        if (expediente.getObservaciones() != null) {
            edtComentarios.setText(expediente.getObservaciones());
        }
        TextView txtEmpleado = view.findViewById(R.id.txt_digitalizacion_numero_empleado);
        String numeroEmpleado = getString(R.string.busqueda_empleado, Sesion.getInstancia(getContext()).getNumeroEmpleado());
        txtEmpleado.setText(numeroEmpleado);
        txtRequeridas = view.findViewById(R.id.txt_digitalizacion_requeridas);
        TextView tvTitularComponente = view.findViewById(R.id.txt_digitalizacion_titular);
        tvTitularComponente.setText(DBHelperRealm.obtenerTitularPoliza());
        edtComentarios.addTextChangedListener(new ValidarObservaciones());
        layoutComentarios = view.findViewById(R.id.layout_digitalizacion_observaciones);
        documentos = DBHelperRealm.obtenerDigitalizaciones();
        TextView txtPeticion = view.findViewById(R.id.txt_digitalizacion_peticion);
        txtPeticion.setText(expediente.getPeticion());
        TextView txtTramite = view.findViewById(R.id.txt_digitalizacion_tramite);
        txtTramite.setText(expediente.getTramite());
        TextView txtSubtramite = view.findViewById(R.id.txt_digitalizacion_subtramite);
        txtSubtramite.setText(expediente.getSubtramite());
        txtDocumento = view.findViewById(R.id.txt_digitalizacion_documento);
        txtDocumentoAyuda = view.findViewById(R.id.txt_digitalizacion_ayuda);
        btnCapturar = view.findViewById(R.id.btn_digitalizacion_capturar);
        btnCapturar.setOnClickListener((View v) -> capturarDocumento());
        btnSiguiente = view.findViewById(R.id.btn_digitalizar_siguiente);
        Button btnCancelar = view.findViewById(R.id.btn_digitalizar_cancelar);
        btnCancelar.setOnClickListener((View v) -> mostrarDialogoCancelar());
        RecyclerView recycler = view.findViewById(R.id.recycler_digitalizacion_documentos);
        LinearLayoutManager lManager = new LinearLayoutManager(contexto);
        recycler.setLayoutManager(lManager);
        adapter = new DigitalizacionAdaptador(documentos, contexto);
        adapter.listener = this;
        recycler.setAdapter(adapter);
        gridCapturas = view.findViewById(R.id.grid_digitalizacion_capturas);
        gridCapturas.setOnItemClickListener((AdapterView<?> parent, View v, int position, long id) -> {
            Digitalizacion documento = documentos.get(documentoActual);
            Captura captura = documento.getCapturasArchivo().get(position);
            if (captura != null && captura.getEstatus() == 1) {
                mostrarVistaPrevia(documento.getDescripcion(), captura);
            }
        });
        View viewPrincipal = view.findViewById(R.id.view_principal);
        viewPrincipal.setOnClickListener((View v) -> esconderTeclado(viewPrincipal));
        onItemClick(null, 0);
        btnSiguiente.setOnClickListener((View v) -> {
            Intent intent = new Intent(getContext(), FormatoFormularioActivity.class);
            startActivity(intent);
        });
    }

    private void mostrarVistaPrevia(String descripcion, Captura captura) {
        Intent intent = new Intent(getActivity(), VistaPreviaActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("nombreDocumento", "file://".concat(captura.getRuta()));
        bundle.putInt("numeroCaptura", captura.getNumero());
        bundle.putString("descDocumento", descripcion);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void digitalizacionDocumento(Digitalizacion documento) {
        if (documento != null) {
            Digitalizacion digitalizacion = new Digitalizacion();
            digitalizacion.setDescripcion(documento.getDescripcion());
            digitalizacion.setTomarCaptura(0);
            if (edtComentarios.getText().toString().isEmpty()) {
                if (documento.getTomarCaptura() != 0) {
                    digitalizacion.setTomarCaptura(0);
                    DBHelperRealm.actualizarDigitalizacion(digitalizacion);
                    actualizarCapturasRequeridas();
                }
            } else {
                if (documento.getTomarCaptura() != 1) {
                    digitalizacion.setTomarCaptura(1);
                    DBHelperRealm.actualizarDigitalizacion(digitalizacion);
                    actualizarCapturasRequeridas();
                }
            }
        }
    }

    private void capturarDocumento() {
        btnCapturar.setEnabled(false);
        if (documentos.size() > documentoActual) {
            Digitalizacion documento = documentos.get(documentoActual);
            digitalizarDocumento(documento);
        }
    }

    private void digitalizarDocumento(Digitalizacion documento) {
        if (documento.getTomarCaptura() < documento.getCapturas()) {
            int numeroCaptura = 0;
            for (int x = 0; x < documento.getCapturasArchivo().size(); x++) {
                Captura capturaRealm = documento.getCapturasArchivo().get(x);
                if (capturaRealm != null && capturaRealm.getEstatus() == 0) {
                    numeroCaptura = capturaRealm.getNumero();
                    break;
                }
            }
            iniciarMotorImagenes(documento.getDescripcion().trim(), numeroCaptura);
        }
    }

    private void iniciarMotorImagenes(String descripcion, int numeroCaptura) {
        try {
            String nombreDocumento = descripcion + "_" + numeroCaptura;
            Bundle bundle = new Bundle();
            Intent launchIntent = new Intent();
            launchIntent.setComponent(new ComponentName(Constantes.Packages.PACKAGE_MOTOR_IMAGENES, Constantes.Packages.PACKAGE_MOTOR_IMAGENES + ".CameraUI"));
            bundle.putString("nombreDocumento", nombreDocumento);
            bundle.putString("rutaDestino", "Documentos/");
            bundle.putBoolean("esCamara", true);
            launchIntent.putExtras(bundle);
            startActivityForResult(launchIntent, PHOTO_FILE);
        } catch (Exception e) {
            DialogoPresenter dialogoPresenter = new DialogoPresenter();
            dialogoPresenter.mensajeAlerta(contexto, getString(R.string.error), getString(R.string.error_motor_imagenes));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        actualizarCapturasRequeridas();
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        btnCapturar.setEnabled(true);

        /** Metodo que realiza todas las tareas para Android 11 **/
        //CustomFilesManager.getInstance().executeWorksAndroid11(data);
        /** ===== **/

        if (requestCode == PHOTO_FILE && data != null) {
            try {
                //String nombre = getFileDirFromUri(data.getData());

                /** se setea la imagen apartir de un String enviado desde el motor de imagenes **/
                String nombre = CustomFilesManager.getInstance().getFilePathFromIntent(data,"rutaImagen");
                /** === **/

                String nombreImagen = "";
                String ruta = nombre.substring(0, nombre.lastIndexOf('/') + 1);
                Uri parser = Uri.parse(nombre);
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    nombreImagen = parser.getLastPathSegment().substring(0, parser.getLastPathSegment().indexOf('.'));
                }else{
                    //nombreImagen = nombre.substring(nombre.lastIndexOf('/') + 1, nombre.indexOf('.'));
                    nombreImagen = parser.getLastPathSegment().substring(0, parser.getLastPathSegment().indexOf('.'));
                }
                String pathMotor = Sesion.getInstancia(contexto).getPathMotor();
                if (pathMotor != null && pathMotor.isEmpty()) {
                    Sesion.getInstancia(contexto).setPathMotor(ruta);
                }

                Digitalizacion documento = documentos.get(documentoActual);
                Digitalizacion digitalizacion = new Digitalizacion();
                int numeroCaptura = documento.getTomarCaptura() + 1;
                digitalizacion.setTomarCaptura(numeroCaptura);
                digitalizacion.setDescripcion(documento.getDescripcion());
                digitalizacion.setIdParamEnvioProceso(documento.getIdParamEnvioProceso());

                int numeroCapturaActual = obtenerNumeroCaptura(documento);
                RealmList<Captura> capturas = listaCapturaDoc(numeroCapturaActual, documento, nombre, nombreImagen.concat(".jpeg"));
                digitalizacion.setCapturasArchivo(capturas);

                DBHelperRealm.actualizarDigitalizacion(digitalizacion);
                actualizarCapturasRequeridas();
            } catch (Exception e) {
                DialogoPresenter dialogoPresenter = new DialogoPresenter();
                dialogoPresenter.mensajeAlerta(contexto, getString(R.string.error), getString(R.string.digitalizacion_error_captura));
            }
        }
    }

    public RealmList<Captura> listaCapturaDoc(int numeroCapturaActual, Digitalizacion documento, String ruta, String nombre) {
        RealmList<Captura> capturas = new RealmList<>();
        if (documento.getCapturasArchivo() != null) {
            for (int x = 0; x < documento.getCapturasArchivo().size(); x++) {
                Captura capturaRealm = documento.getCapturasArchivo().get(x);
                if (capturaRealm != null) {
                    capturas.add(crearObjetoCaptura(numeroCapturaActual, x, capturaRealm, ruta, nombre));
                }
            }
        }
        return capturas;
    }

    public Captura crearObjetoCaptura(int numeroCapturaActual, int x, Captura capAnterior, String ruta, String nombre) {
        Captura captura = new Captura();
        captura.setNumero(x + 1); // Número que indica la posición de la captura en la vista
        captura.setIdTipoDoc(capAnterior.getIdTipoDoc());
        captura.setIdParamEnvioProceso(capAnterior.getIdParamEnvioProceso());
        captura.setDescripcion(capAnterior.getDescripcion());
        captura.setCargaExitosa(0);
        if (x == numeroCapturaActual - 1) {
            captura.setEstatus(1);
            captura.setNombre(nombre);
            captura.setRuta(ruta);
        } else {
            if (capAnterior.getEstatus() != null) {
                captura.setEstatus(capAnterior.getEstatus());
            }
            if (capAnterior.getNombre() != null) {
                captura.setNombre(capAnterior.getNombre());
            }
            if (capAnterior.getRuta() != null) {
                captura.setRuta(capAnterior.getRuta());
            }
        }
        return captura;
    }

    public int obtenerNumeroCaptura(Digitalizacion documento) {
        int numeroCapturaActual = 0;
        for (int x = 0; x < documento.getCapturasArchivo().size(); x++) {
            Captura capturaRealm = documento.getCapturasArchivo().get(x);
            if (capturaRealm != null && capturaRealm.getEstatus() == 0) {
                numeroCapturaActual = capturaRealm.getNumero();
                break;
            }
        }
        return numeroCapturaActual;
    }

    public void actualizarCapturasRequeridas() {
        documentos = DBHelperRealm.obtenerDigitalizaciones();
        if (documentos.size() > documentoActual) {
            Digitalizacion documento = documentos.get(documentoActual);
            if (documento.getTomarCaptura().equals(documento.getCapturas())) {
                btnCapturar.setEnabled(false);
            } else {
                btnCapturar.setEnabled(true);
            }
            CapturasAdaptador capturasAdaptador = new CapturasAdaptador(getContext(), documento.getCapturasArchivo());
            gridCapturas.setAdapter(capturasAdaptador);
            adapter.notifyDataSetChanged();
            boolean validacion = validarBotonSiguiente(documentos);
            habilitarBotonIngresar(validacion);
        }
    }

    @Override
    public void onItemClick(View itemView, int position) {
        if (position > documentos.size() || position < 0) {
            documentoActual = 0;
            adapter.setPosicionSeleccionada(0);
        } else {
            documentoActual = position;
        }

        Digitalizacion documento = documentos.get(documentoActual);
        txtDocumento.setText(documento.getDescripcion());
        txtDocumentoAyuda.setText(documento.getAyuda());
        actualizarCapturasRequeridas();

        if (documento.getDescripcion().equals(DESC_OBSERVACIONES)) {
            habilitarSeccionComentarios(true);
        } else {
            if (itemView != null) {
                esconderTeclado(itemView);
            }
            habilitarSeccionComentarios(false);
        }
    }

    public void habilitarSeccionComentarios(boolean habilitar) {
        int activar = View.GONE;
        int desactivar = View.VISIBLE;
        if (habilitar) {
            activar = View.VISIBLE;
            desactivar = View.GONE;
        }
        edtComentarios.setVisibility(activar);
        layoutComentarios.setVisibility(activar);
        txtDocumentoAyuda.setVisibility(desactivar);
        btnCapturar.setVisibility(desactivar);
        txtRequeridas.setVisibility(desactivar);
        gridCapturas.setVisibility(desactivar);
    }

    public boolean validarBotonSiguiente(List<Digitalizacion> documentos) {
        boolean todosCompletados = true;
        for (Digitalizacion documento : documentos) {
            if (documento.getEsObligatorio() == 1) {
                if (documento.getTomarCaptura() == 0) {
                    todosCompletados = false;
                    break;
                }
            }
        }

        return todosCompletados;
    }

    //muestra una ventana de dialogo donde aceptas cancelar el tramite o cancelar
    private void mostrarDialogoCancelar() {
        String titulo = contexto.getString(R.string.componente_familiar_cancelar_tramite);
        String mensaje = contexto.getString(R.string.componente_familiar_cancelar_aviso);
        String confirmacion = contexto.getString(R.string.componente_familiar_cancelar_confirmacion);
        AlertaDialogo cancelarDialogo = AlertaDialogo.getInstancia(titulo, mensaje, confirmacion);
        cancelarDialogo.setDelegado(() -> {
            Utilidades.limpiarDatosTramite(contexto);
            if (getActivity() != null) {
                ((PrincipalActivity) getActivity()).cancelarTramite();
            }
        });

        if (getFragmentManager() != null) {
            cancelarDialogo.show(getFragmentManager(), "");
        }
    }

    private void habilitarBotonIngresar(boolean habilitar) {
        btnSiguiente.setEnabled(habilitar);
        Drawable fondoBoton = habilitar ? contexto.getDrawable(R.drawable.fondo_boton_azul) : contexto.getDrawable(R.drawable.fondo_boton_gris);
        btnSiguiente.setBackground(fondoBoton);
    }

    private void esconderTeclado(View vista) {
        InputMethodManager inputMethodManager = (InputMethodManager) contexto.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(vista.getWindowToken(), 0);
    }

    private class ValidarObservaciones implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            //no se ocupa porque solo se necesita despues de que cambiaste el texto
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //no se ocupa porque solo se necesita despues de que cambiaste el texto
        }

        @Override
        public void afterTextChanged(Editable s) {
            String comentarios = edtComentarios.getText().toString();
            comentarios = comentarios.replace("\n", " ");
            DBHelperRealm.actualizarObservacionesExpediente(comentarios);
            if (documentos.size() > documentoActual) {
                Digitalizacion documento = documentos.get(documentoActual);
                digitalizacionDocumento(documento);
            }
        }
    }
}
