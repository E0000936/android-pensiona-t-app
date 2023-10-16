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
package com.mx.profuturo.android.pensionat.presentation.tramite;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.mx.profuturo.android.pensionat.R;
import com.mx.profuturo.android.pensionat.data.local.db.realm.DBHelperRealm;
import com.mx.profuturo.android.pensionat.data.local.preferences.Sesion;
import com.mx.profuturo.android.pensionat.domain.model.Categoria;
import com.mx.profuturo.android.pensionat.domain.model.Documento;
import com.mx.profuturo.android.pensionat.domain.model.Solicitante;
import com.mx.profuturo.android.pensionat.domain.model.Telefono;
import com.mx.profuturo.android.pensionat.presentation.busqueda.GenericoAdaptador;
import com.mx.profuturo.android.pensionat.presentation.busqueda.PrincipalActivity;
import com.mx.profuturo.android.pensionat.presentation.dialogs.AlertaDialogo;
import com.mx.profuturo.android.pensionat.presentation.dialogs.DialogAutentication;
import com.mx.profuturo.android.pensionat.presentation.dialogs.DialogoPresenter;
import com.mx.profuturo.android.pensionat.presentation.digitalizacion.DigitalizacionFragment;
import com.mx.profuturo.android.pensionat.presentation.login.MainActivity;
import com.mx.profuturo.android.pensionat.utils.ManejoArchivosHelper;
import com.mx.profuturo.android.pensionat.utils.TimerCierreSesion;
import com.mx.profuturo.android.pensionat.utils.VariablesGlobales;
import com.mx.profuturo.android.pensionat.vistasCustomizadas.Progress;
import com.mx.profuturo.android.pensionat.vistasCustomizadas.SpinnerCaja;
import com.mx.profuturo.grupo.launchermau.core.rules.BusinessLine;
import com.mx.profuturo.grupo.launchermau.core.tags.MDCatalog;
import com.mx.profuturo.grupo.launchermau.core.tags.TagResponse;
import com.mx.profuturo.grupo.launchermau.core.tags.TagsBuilder;
import com.mx.profuturo.grupo.launchermau.data.local.ConfLauncher;
import com.mx.profuturo.grupo.launchermau.data.model.response_launcher.ResponseLauncher;
import com.mx.profuturo.grupo.launchermau.main.LauncherMAU;
import com.mx.profuturo.grupo.launchermau.main.builder.MauBuilder;
import com.mx.profuturo.grupo.launchermau.main.callback.OnResponseLauncher;
import com.mx.profuturo.grupo.launchermau.utils.ConverterProcedure;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.realm.RealmList;

/**
 * <h1>TramiteFragment</h1>
 * Clase selección de trámite.
 *
 * @author Everis
 * @version 1.0
 * @since 2019-03-25
 */
public class TramiteFragment extends Fragment
        implements SpinnerCaja.OnSpinnerEventsListener,
        AdapterView.OnItemSelectedListener, ITramiteVista {

    private static final String TAG = TramiteFragment.class.getSimpleName();
    private TramitePresenter tramitePresenter;
    private SpinnerCaja spTipoTramite;
    private SpinnerCaja spSubtipoTramite;
    private View viewDerecha;
    private View viewIzquierda;
    private Context contexto;
    private RecyclerView recyclerDocumentos;
    private TextView txtTramite;
    private TextView txtSubtramite;
    private Button btnSiguiente;
    private LinearLayout btnTramite;
    private LinearLayout btnQueja;
    private LinearLayout btnInvestigacion;
    private TextView txtBtnTramite;
    private TextView txtBtnQueja;
    private TextView txtBtnInvestigacion;
    private String peticion;
    private DialogoPresenter dialogoPresenter;
    private RealmList<Documento> listaDocumentos = new RealmList<>();
    private int idSubtramitePrevio = 0;
    private int idTramitePrevio = 0;

    private String process = "";
    private String subprocess = "";

    private LauncherMAU launcherMAU ;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        contexto = container.getContext();
        PrincipalActivity principalActivity = (PrincipalActivity) getActivity();
        if (principalActivity != null) {
            principalActivity.cambiarTituloBarra(contexto.getString(R.string.tramite_titulo), contexto.getString(R.string.tramite_subtitulo));
        }
        return inflater.inflate(R.layout.fragmento_seleccion_tramite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        tramitePresenter = new TramitePresenter(contexto, this);
        dialogoPresenter = new DialogoPresenter();
        TextView txtEmpleado = view.findViewById(R.id.txt_tramite_numero_empleado);
        String numeroEmpleado = getString(R.string.busqueda_empleado, Sesion.getInstancia(getContext()).getNumeroEmpleado());
        txtEmpleado.setText(numeroEmpleado);
        TextView tvTitularComponente = view.findViewById(R.id.txt_tramite_titular);
        tvTitularComponente.setText(DBHelperRealm.obtenerTitularPoliza());

        btnTramite = view.findViewById(R.id.btn_tramites_caja1);
        btnTramite.setOnClickListener((View v) -> {
            limpiarValoresPrevios();
            seleccionarTramite();
        });
        txtBtnTramite = view.findViewById(R.id.txt_tramite_caja1);
        btnQueja = view.findViewById(R.id.btn_tramites_caja2);
        btnQueja.setEnabled(true);
        btnQueja.setOnClickListener((View v) -> {
            limpiarValoresPrevios();
            seleccionarQueja();
        });
        txtBtnQueja = view.findViewById(R.id.txt_tramite_caja2);
        btnInvestigacion = view.findViewById(R.id.btn_tramites_caja3);
        btnInvestigacion.setEnabled(true);
        btnInvestigacion.setOnClickListener((View v) -> {
            limpiarValoresPrevios();
            seleccionarInvestigacion();
        });
        txtBtnInvestigacion = view.findViewById(R.id.txt_tramite_caja3);
        viewDerecha = view.findViewById(R.id.view_tramite_derecha);
        viewIzquierda = view.findViewById(R.id.view_tramite_izquierda);
        spTipoTramite = view.findViewById(R.id.spinner_tramite_tipo);
        spTipoTramite.setOnItemSelectedListener(this);
        spTipoTramite.setSpinnerEventsListener(this);
        spSubtipoTramite = view.findViewById(R.id.spinner_tramite_subtipo);
        spSubtipoTramite.setSpinnerEventsListener(this);
        spSubtipoTramite.setOnItemSelectedListener(this);
        txtTramite = view.findViewById(R.id.txt_tramite_titulo_ayuda);
        txtSubtramite = view.findViewById(R.id.txt_tramite_subtitulo_ayuda);
        btnSiguiente = view.findViewById(R.id.btn_tramite_siguiente);
        btnSiguiente.setOnClickListener((View v) -> {
            if (VariablesGlobales.getInstance().folio) {
                limpiarValoresPrevios();
                guardarTramite();
            }
            else startProcessMAU();
        });
        Button btnCancelar = view.findViewById(R.id.btn_tramite_cancelar);
        btnCancelar.setOnClickListener((View v) -> mostrarDialogoCancelar());
        recyclerDocumentos = view.findViewById(R.id.recycler_tramite_documentos);
        LinearLayoutManager llm = new LinearLayoutManager(contexto);
        recyclerDocumentos.setLayoutManager(llm);
        VariablesGlobales.getInstance().progressCompleto = true;
        tramitePresenter.validarExpedienteExistente();

        //creating instance of launcher
        if (launcherMAU == null) launcherMAU = LauncherMAU.getInstance(requireActivity(), onResponseLauncher);
        if (dialogoPresenter == null) dialogoPresenter = new DialogoPresenter();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        ((GenericoAdaptador) parent.getAdapter()).setPosicionSeleccionada(position);
        switch (parent.getId()) {
            case R.id.spinner_tramite_tipo:
                idTramitePrevio = position;
                actualizarTramiteSeleccionado(idTramitePrevio, idSubtramitePrevio);
                break;
            case R.id.spinner_tramite_subtipo:
                seleccionarSubtramite(position);
                break;
            default:
        }
    }

    private void startProcessMAU() {
        Progress.mostrar(requireContext());

        //Validate status response engine
        String chainResponse = ConfLauncher.getStringByTag(ConfLauncher.TAG_STATUS_ENROLMENT, requireContext());
        ResponseLauncher responseLauncher = new Gson().fromJson(chainResponse, ResponseLauncher.class);
        boolean openMAU = false;
        if (Objects.nonNull(responseLauncher)) {
            openMAU = !(Objects.equals(responseLauncher.getStatus_engine(), TagResponse.OMIT_CURP) ||
                    Objects.equals(responseLauncher.getStatus_engine(), TagResponse.CURP_IS_A_MINIOR) ||
                    !responseLauncher.haveOneAuthAvailable());
        }

        if (openMAU) {
            ConverterProcedure converterProcedure = ConverterProcedure.getRuleConfig(process, subprocess);
            if (converterProcedure == null) {
                Progress.ocultar();
                dialogoPresenter.mensajeAlerta(requireContext(), "Motor Autenticacion Universal", "No encuentra el tramite configurado");
                return;
            }
            process = converterProcedure.getProcess();
            subprocess = converterProcedure.getSubprocess();
            Solicitante solicitante = VariablesGlobales.getInstance().solicitante;
            launcherMAU.isSessionActive(solicitante.getCurp().trim().toUpperCase());
        } else {
            limpiarValoresPrevios();
            guardarTramite();
        }
    }

    private void showAlertDoYouHaveAnyMethodAuth(){
        Progress.ocultar();
        DialogAutentication dialogAutentication = new DialogAutentication();
        dialogAutentication.setListenerAceptar( dialog -> {
            dialogAutentication.dismiss();
            Progress.mostrar(getContext());
            callMauEngine();
        });
        dialogAutentication.setListenerCancel( dialog -> {
            dialogAutentication.dismiss();
            Progress.mostrar(getContext());
            limpiarValoresPrevios();
            guardarTramite();
        });
        dialogAutentication.show(requireActivity().getSupportFragmentManager(), "haveAuth");
    }

    private void callMauEngine() {
        Solicitante solicitante = VariablesGlobales.getInstance().solicitante;
        String poliza = DBHelperRealm.obtenerExpediente().getPoliza();
        MauBuilder mauBuilder = MauBuilder.getInstance(
                solicitante.getCurp().trim().toUpperCase(),
                solicitante.getNombre().trim().toUpperCase(),
                solicitante.getApPaterno().trim().toUpperCase(),
                solicitante.getApMaterno().trim().toUpperCase(),
                poliza,
                process,
                process,
                TagsBuilder.ORIGIN_PENSIONAT,
                BusinessLine.ASEGURADORA_LINE,
                MDCatalog.TYPE_USER_CLIENT,
                process,
                subprocess
        );
        String idAdviser = Sesion.getInstancia(getContext()).getNumeroEmpleado();
        mauBuilder.setEmail_client(solicitante.getCorreo());
        mauBuilder.setPhone_client(getCelPhoneNumber(solicitante.getTelefonos()));
        mauBuilder.setId_adviser(idAdviser);
        mauBuilder.setTimeOutSession(TimerCierreSesion.getTimForCloseSession());
        launcherMAU.setMauBuilder(mauBuilder);
        launcherMAU.startMAU();
    }

    private String getCelPhoneNumber(@NonNull List<Telefono> telefonos) {
        for (Telefono telefono : telefonos) {
            if (telefono.getTipo() != null && telefono.getTipo().equals("MOVIL")) {
                return telefono.getNumero();
            }
        }
        return "";
    }

    private final OnResponseLauncher onResponseLauncher = new OnResponseLauncher() {
        @Override
        public void onError(String log, int code) {
            Progress.ocultar();
            TimerCierreSesion.setIsMauRunning(false);
            if (Objects.equals(log, TagResponse.MAU_BUILDER_NOT_FOUND))
                dialogoPresenter.mensajeAlerta(requireContext(), "Motor Autenticacion Universal", "No se ha podido crear la instancia");
            else if (Objects.equals(log, TagResponse.NO_MAU_INSTALLED))
                dialogoPresenter.mensajeAlerta(requireContext(), getString(R.string.error), getString(R.string.error_motor_mau));
            else
                dialogoPresenter.mensajeAlerta(requireContext(), "Motor Autenticacion Universal", "Ha ocurrido un error en la validación de los campos " + log);
        }

        @Override
        public void onSuccess(@Nullable Intent intent) {
            Progress.ocultar();
            if (intent != null) {
                TimerCierreSesion.setIsMauRunning(true);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                resultLauncher.launch(intent);
            }
        }

        @Override
        public void isSessionActive(boolean isSessionActive) {
            if (isSessionActive) {
                Progress.ocultar();
                limpiarValoresPrevios();
                guardarTramite();
            } else {
                Progress.ocultar();
                showAlertDoYouHaveAnyMethodAuth();
            }
        }
    };

    private final ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        TimerCierreSesion.setIsMauRunning(false);
        if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
            String resultEngine = result.getData().getStringExtra(TagResponse.MAU_RESPONSE);
            ResponseLauncher responseLauncher = new Gson().fromJson(resultEngine, ResponseLauncher.class);
            if (Objects.equals(responseLauncher.getStatus_engine(), TagResponse.AUTH_SUCCESS)) {
                limpiarValoresPrevios();
                guardarTramite();
            }
        } else if (result.getResultCode() == Activity.RESULT_CANCELED && result.getData() != null) {
            String resultEngine = result.getData().getStringExtra(TagResponse.MAU_RESPONSE);
            ResponseLauncher responseLauncher = new Gson().fromJson(resultEngine, ResponseLauncher.class);
            if (!Objects.isNull(responseLauncher) && responseLauncher.getStatus_engine().equals(TagResponse.SESSION_TIMEOUT)) {
                ((MainActivity) requireActivity()).cerrarSesion();
            }
        }
    });

    private void seleccionarTramite() {
        actualizarComboTramites(1);
        peticion = "TRÁMITE";
        aplicarDisenoDesactivar(btnQueja, txtBtnQueja);
        aplicarDisenoDesactivar(btnInvestigacion, txtBtnInvestigacion);
        aplicarDisenoActivado(btnTramite, txtBtnTramite);
    }

    private void seleccionarQueja() {
        actualizarComboTramites(2);
        peticion = "QUEJA";
        aplicarDisenoActivado(btnQueja, txtBtnQueja);
        aplicarDisenoDesactivar(btnInvestigacion, txtBtnInvestigacion);
        aplicarDisenoDesactivar(btnTramite, txtBtnTramite);
    }

    private void seleccionarInvestigacion() {
        actualizarComboTramites(3);
        peticion = "INVESTIGACIÓN";
        aplicarDisenoDesactivar(btnQueja, txtBtnQueja);
        aplicarDisenoActivado(btnInvestigacion, txtBtnInvestigacion);
        aplicarDisenoDesactivar(btnTramite, txtBtnTramite);
    }

    private void aplicarDisenoDesactivar(LinearLayout button, TextView textView) {
        button.setElevation(0);
        if (getActivity() != null) {
            button.setBackground(getActivity().getDrawable(R.drawable.fondo_borde_gris));
            textView.setTextColor(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.colorTextoSecondario));
        }
    }

    private void aplicarDisenoActivado(LinearLayout button, TextView textView) {
        button.setElevation(13);
        if (getActivity() != null) {
            button.setBackground(getActivity().getDrawable(R.drawable.fondo_borde_azul_ceruleo));
            textView.setTextColor(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.colorAzulGrisaceo));
        }
    }

    private void limpiarTramite() {
        habilitarBotonSiguiente(false);
        txtTramite.setText("");
        txtSubtramite.setText("");
        spSubtipoTramite.setAdapter(new GenericoAdaptador(getActivity(), new ArrayList<>()));
        DocumentoTramiteAdapter adapter = new DocumentoTramiteAdapter(new ArrayList<>());
        recyclerDocumentos.setAdapter(adapter);
    }

    private void limpiarSubtramite() {
        txtSubtramite.setText("");
        habilitarBotonSiguiente(false);
        DocumentoTramiteAdapter adapter = new DocumentoTramiteAdapter(new ArrayList<>());
        recyclerDocumentos.setAdapter(adapter);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        throw new UnsupportedOperationException();
    }

    public void habilitarBotonSiguiente(boolean habilitar) {
        btnSiguiente.setEnabled(habilitar);
        Drawable fondoBoton = habilitar ? contexto.getDrawable(R.drawable.fondo_boton_azul) : contexto.getDrawable(R.drawable.fondo_boton_gris);
        btnSiguiente.setBackground(fondoBoton);
    }

    @Override
    public void onSpinnerOpened(Spinner spinner) {
        spinner.setBackground(contexto.getDrawable(R.drawable.fondo_spinner_borde_abierto));
        limpiarValoresPrevios();
    }

    @Override
    public void onSpinnerClosed(Spinner spinner) {
        spinner.setBackground(contexto.getDrawable(R.drawable.fondo_spinner_borde));
    }

    private void guardarTramite() {
        Progress.mostrar(contexto);
        Categoria categoriaTramite = (Categoria) spTipoTramite.getSelectedItem();
        Categoria categoriaSubtramite = (Categoria) spSubtipoTramite.getSelectedItem();
        tramitePresenter.obtenerFolioMIT(categoriaSubtramite.getClave(), categoriaTramite.getId(), categoriaSubtramite.getId());
    }

    private void mostrarDialogoCancelar() {
        String titulo = contexto.getString(R.string.componente_familiar_cancelar_tramite);
        String mensaje = contexto.getString(R.string.componente_familiar_cancelar_aviso);
        String confirmacion = contexto.getString(R.string.componente_familiar_cancelar_confirmacion);
        AlertaDialogo cancelarDialogo = AlertaDialogo.getInstancia(titulo, mensaje, confirmacion);
        cancelarDialogo.setDelegado(() -> {
            VariablesGlobales.getInstance().limpiar();
            ManejoArchivosHelper.limpiarArchivosDirectorio(Sesion.getInstancia(contexto).getPathPdf());
            DBHelperRealm.limpiar();
            if (getActivity() != null) {
                ((PrincipalActivity) getActivity()).cancelarTramite();
            }
        });

        if (getFragmentManager() != null) {
            cancelarDialogo.show(getFragmentManager(), TAG);
        }
    }

    @Override
    public void obtenerFolioMitExitoso() {
        Categoria categoriaTramite = (Categoria) spTipoTramite.getSelectedItem();
        Categoria categoriaSubtramite = (Categoria) spSubtipoTramite.getSelectedItem();
        tramitePresenter.guardarExpediente(peticion, categoriaTramite, categoriaSubtramite, listaDocumentos);
    }

    @Override
    public void iniciarDigitalizacion() {
        Progress.ocultar();
        if (getFragmentManager() != null) {
            FragmentTransaction formulario = getFragmentManager().beginTransaction();
            formulario.replace(R.id.frame_principal, new DigitalizacionFragment(), "DigitalizacionFragment");
            formulario.addToBackStack("TramiteFragment");
            formulario.commitAllowingStateLoss();
        }
    }

    @Override
    public void mostrarMensaje(String titulo, String mensaje) {
        Progress.ocultar();
        if (mensaje != null) {
            dialogoPresenter.mensajeAlerta(contexto, titulo, mensaje);
        }
    }

    @Override
    public void mostrarVistaDocumentos(boolean animacion) {
        if (viewDerecha.getVisibility() == View.GONE) {
            Animation slideUp = AnimationUtils.loadAnimation(contexto, R.anim.dezplazar_derecha);
            ViewGroup.LayoutParams layoutParams = viewIzquierda.getLayoutParams();
            layoutParams.width = 0;
            viewIzquierda.setLayoutParams(layoutParams);
            viewDerecha.setVisibility(View.VISIBLE);
            if (animacion) {
                viewDerecha.startAnimation(slideUp);
            }
        }
    }

    @Override
    public void precargarTramite(int idPeticion, int idTramite, int idSubtramite) {
        idSubtramitePrevio = idSubtramite;
        idTramitePrevio = idTramite;
        switch (idPeticion) {
            case 1:
                seleccionarTramite();
                break;
            case 2:
                seleccionarQueja();
                break;
            case 3:
                seleccionarInvestigacion();
            default:
        }
    }

    private void actualizarComboTramites(int idPeticion) {
        ArrayList<Categoria> listaTramites = DBHelperRealm.obtenerTramites(idPeticion);
        spTipoTramite.setAdapter(new GenericoAdaptador(getActivity(), listaTramites));
        spTipoTramite.setSelection(idTramitePrevio);
    }

    private void actualizarTramiteSeleccionado(int posicion, int seleccion) {
        if (posicion != 0) {
            Categoria categoria = (Categoria) spTipoTramite.getSelectedItem();
            txtTramite.setText(categoria.getDescripcion());
            if (Objects.nonNull(categoria) && Objects.nonNull(categoria.getId()))
                process = categoria.getId().toString();
            ArrayList<Categoria> listaSubtramites = DBHelperRealm.obtenerSubtramites(categoria.getId());
            spSubtipoTramite.setAdapter(new GenericoAdaptador(getActivity(), listaSubtramites));
            txtSubtramite.setText("");
            spSubtipoTramite.setSelection(seleccion);
            habilitarBotonSiguiente(false);
        } else {
            process = "";
            limpiarTramite();
        }
    }

    void seleccionarSubtramite(int posicion) {
        if (posicion != 0) {
            Categoria categoria = (Categoria) spSubtipoTramite.getSelectedItem();
            txtSubtramite.setText(categoria.getDescripcion());
            habilitarBotonSiguiente(true);
            if (Objects.nonNull(categoria) && Objects.nonNull(categoria.getId()))
                subprocess = categoria.getId().toString();
            listaDocumentos = DBHelperRealm.obtenerDocumentos(categoria.getId());
            DocumentoTramiteAdapter adapter = new DocumentoTramiteAdapter(listaDocumentos);
            recyclerDocumentos.setAdapter(adapter);
            mostrarVistaDocumentos(true);
        } else {
            limpiarSubtramite();
            subprocess = "";
        }
    }

    private void limpiarValoresPrevios() {
        idSubtramitePrevio = 0;
        idTramitePrevio = 0;
    }


}
