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
package com.mx.profuturo.android.pensionat.presentation.busqueda;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.mx.profuturo.android.pensionat.R;
import com.mx.profuturo.android.pensionat.data.local.db.realm.DBHelperRealm;
import com.mx.profuturo.android.pensionat.data.local.preferences.Sesion;
import com.mx.profuturo.android.pensionat.domain.model.Expediente;
import com.mx.profuturo.android.pensionat.domain.model.Grupo;
import com.mx.profuturo.android.pensionat.domain.model.Poliza;
import com.mx.profuturo.android.pensionat.domain.model.Solicitante;
import com.mx.profuturo.android.pensionat.presentation.dialogs.AlertaDialogo;
import com.mx.profuturo.android.pensionat.presentation.dialogs.DialogoPresenter;
import com.mx.profuturo.android.pensionat.presentation.login.LoginActivity;
import com.mx.profuturo.android.pensionat.presentation.login.MainActivity;
import com.mx.profuturo.android.pensionat.utils.Constantes;
import com.mx.profuturo.android.pensionat.utils.TraceLog;
import com.mx.profuturo.android.pensionat.utils.VariablesGlobales;
import com.mx.profuturo.android.pensionat.vistasCustomizadas.Progress;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <h1>BusquedaActivity</h1>
 * Clase búsqueda solicitante.
 *
 * @author Everis
 * @version 1.0
 * @since 2019-03-24
 */
public class BusquedaActivity extends MainActivity implements IBusquedaVista {

    private static final String TAG = BusquedaActivity.class.getSimpleName();
    private TextView txtEmpleado;
    private TextInputEditText edtBusqueda;
    private Spinner spTipoBusqueda;
    private TipoBusquedaAdaptador adaptador;
    private Button btnBusqueda;
    private Context contexto;
    private TextInputLayout layoutBusqueda;
    private ImageView imgBusqueda;
    private BusquedaPresenter busquedaPresenter;
    private Enum anEnum;
    private DialogoPresenter dialogoPresenter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_busqueda);
        contexto = this;
        dialogoPresenter = new DialogoPresenter();
        txtEmpleado = findViewById(R.id.txt_componente_empleado);
        spTipoBusqueda = findViewById(R.id.spinner_busqueda_tipo);
        edtBusqueda = findViewById(R.id.edt_busqueda_cliente);
        btnBusqueda = findViewById(R.id.btn_busqueda_buscar);
        layoutBusqueda = findViewById(R.id.layout_busqueda_cliente);
        imgBusqueda = findViewById(R.id.img_login_busqueda_error);
        busquedaPresenter = new BusquedaPresenter(contexto, this);
        edtBusqueda.addTextChangedListener(new validarActivacionEdtBusqueda());
        btnBusqueda.setOnClickListener((View v) -> {
            if (edtBusqueda.getText() != null) {
                busquedaPresenter.validarCampoBusqueda(spTipoBusqueda.getSelectedItemPosition(), edtBusqueda.getText().toString());
            }
        });
        edtBusqueda.setEnabled(false);
        cargarVista();
        View viewPrincipal = findViewById(R.id.view_busqueda_principal);
        View viewConstrain = findViewById(R.id.prueba);
        viewPrincipal.setOnClickListener((View v) -> esconderTeclado(viewPrincipal));
        viewConstrain.setOnClickListener((View v) -> esconderTeclado(viewConstrain));
    }

    @Override
    protected void onStart() {
        super.onStart();
        spTipoBusqueda.setSelection(0);
    }

    private void cargarVista() {
        String numeroEmpleado = getString(R.string.busqueda_empleado, Sesion.getInstancia(this).getNumeroEmpleado());
        txtEmpleado.setText(numeroEmpleado);

        /** Se agrega customKey para Crashalytics CUSP**/
        TraceLog.getInstance().setCuspEmployee(numeroEmpleado);
        /** == **/

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(null);
        }
        ImageButton imgCerrarSesion = toolbar.findViewById(R.id.btn_cerrar_sesion);
        imgCerrarSesion.setOnClickListener((View v) -> mostrarDialogoCerrarSesion());
        crearListaOpcionesBusqueda();
    }

    private void mostrarDialogoCerrarSesion() {
        AlertaDialogo cerrarSesionDialogo = new AlertaDialogo();
        cerrarSesionDialogo.setDelegado(() -> {
            busquedaPresenter.limpiarDatosAplicacion();
            Intent next = new Intent(BusquedaActivity.this, LoginActivity.class);
            next.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(next);
            finish();
        });
        cerrarSesionDialogo.show(getSupportFragmentManager(), TAG);
    }

    private void crearListaOpcionesBusqueda() {
        String[] myResArray = getResources().getStringArray(R.array.busqueda_tipo_spinner);
        List<String> myResArrayList = Arrays.asList(myResArray);
        ArrayList<String> lista = new ArrayList<>(myResArrayList);
        adaptador = new TipoBusquedaAdaptador(this, lista);
        spTipoBusqueda.setAdapter(adaptador);
        spTipoBusqueda.setSelection(0);
        spTipoBusqueda.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                adaptador.setPosicionActual(i);
                switch (i) {
                    case 0:
                        limpiarSeleccion(false);
                        layoutBusqueda.setHint(contexto.getString(R.string.busqueda_hint_busqueda));
                        break;
                    case 1:
                        limpiarSeleccion(true);
                        layoutBusqueda.setHint(contexto.getString(R.string.busqueda_hint_nss));
                        anEnum = Constantes.TipoBusqueda.NSS;
                        edtBusqueda.setFilters(new InputFilter[]{new InputFilter.LengthFilter(Constantes.TamanoCampos.NSS)});
                        break;
                    case 2:
                        limpiarSeleccion(true);
                        layoutBusqueda.setHint(contexto.getString(R.string.busqueda_hint_poliza));
                        anEnum = Constantes.TipoBusqueda.POLIZA;
                        edtBusqueda.setFilters(new InputFilter[]{new InputFilter.LengthFilter(Constantes.TamanoCampos.POLIZA)});
                        break;
                    case 3:
                        limpiarSeleccion(true);
                        layoutBusqueda.setHint(contexto.getString(R.string.busqueda_hint_oferta));
                        anEnum = Constantes.TipoBusqueda.FOLIO;
                        edtBusqueda.setFilters(new InputFilter[]{new InputFilter.LengthFilter(Constantes.TamanoCampos.NSS)});
                        break;
                    case 4:
                        limpiarSeleccion(true);
                        layoutBusqueda.setHint(contexto.getString(R.string.busqueda_hint_curp));
                        anEnum = Constantes.TipoBusqueda.CURP;
                        edtBusqueda.setFilters(new InputFilter[]{new InputFilter.LengthFilter(Constantes.TamanoCampos.CURP)});
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                throw new UnsupportedOperationException();
            }
        });
    }

    private void limpiarSeleccion(boolean habilitar) {
        limpiarVistaError();
        edtBusqueda.setText("");
        edtBusqueda.setEnabled(habilitar);
        habilitarBotonBuscar(habilitar);
    }

    @Override
    public void habilitarBotonBuscar(boolean habilitar) {
        btnBusqueda.setEnabled(habilitar);
        Drawable fondoBoton = habilitar ? getDrawable(R.drawable.fondo_boton_azul) : getDrawable(R.drawable.fondo_boton_gris);
        btnBusqueda.setBackground(fondoBoton);
    }

    private void limpiarVistaError() {
        layoutBusqueda.setError(null);
        imgBusqueda.setVisibility(View.GONE);
    }

    @Override
    public void iniciarBusquedaExitoso(List<Poliza> polizas, boolean esCasoCerrado) {
        Progress.ocultar();
        VariablesGlobales.getInstance().progressCompleto = false;
        if (esCasoCerrado) {
            guardarDatosOferta(polizas);
            mostrarDialogoDatosInsuficientes(esCasoCerrado);
        } else {
            VariablesGlobales.getInstance().polizas = polizas;
            Intent pantalla = new Intent(contexto, PrincipalActivity.class);
            startActivity(pantalla);
        }
    }

    public void guardarDatosOferta(List<Poliza> polizas) {
        Solicitante solicitante = new Solicitante();
        Poliza poliza = polizas.get(0);
        solicitante.setIdPoliza(0);
        solicitante.setIdGrpPago(0);
        solicitante.setIdTitularCobro(0);
        solicitante.setFolioOferta(edtBusqueda.getText().toString());
        if (polizas.size() > 0) {
            solicitante.setIdOferta(poliza.getIdOferta());

            if (poliza.getGrupos().size() > 0) {
                Grupo grupo = poliza.getGrupos().get(0);
                if (grupo != null) {
                    Expediente expediente = new Expediente();
                    expediente.setParentescoSolicitante(Constantes.Nombres.DESC_PARENTESCO);
                    expediente.setNombreTitular("");
                    expediente.setApPaternoTitular("");
                    expediente.setApMaternoTitular("");
                    expediente.setIdTipoRegimen(grupo.getIdTipoRegimenOferta());
                    expediente.setRegimen(grupo.getDescRegimenOferta());
                    expediente.setNss(grupo.getNssOferta());
                    DBHelperRealm.insertarActualizarExpendiente(expediente);
                }
            }
        }
        DBHelperRealm.insertarActualizarSolicitante(solicitante);
    }

    @Override
    public void mostrarErrorValidacion() {
        layoutBusqueda.setError(getString(R.string.login_error_formato));
        imgBusqueda.setVisibility(View.VISIBLE);
    }

    @Override
    public void validacionExitosaCampo(String valorBusqueda) {
        Progress.mostrar(contexto);
        busquedaPresenter.buscarUsuario(valorBusqueda, (Constantes.TipoBusqueda) anEnum);

    }

    @Override
    public void mostrarDialogoDatosInsuficientes(Boolean variable) {
        String titulo = contexto.getString(R.string.caso_cerrado_titulo);
        String mensaje = contexto.getString(R.string.caso_cerrado_aviso);
        String confirmacion = contexto.getString(R.string.caso_cerrado_confirmacion);
        AlertaDialogo casoCerradoDialogo = AlertaDialogo.getInstancia(titulo, mensaje, confirmacion);
        casoCerradoDialogo.setDelegado(() -> {
            Intent next = new Intent(BusquedaActivity.this, PrincipalActivity.class);
            next.putExtra("casoCerrado", variable);
            startActivity(next);
            casoCerradoDialogo.dismiss();
        });
        casoCerradoDialogo.show(getSupportFragmentManager(), TAG);
    }

    @Override
    public void iniciarBusquedaError(String titulo, String mensaje) {
        if (mensaje != null) {
            Progress.ocultar();
            Progress.ocultar();
            dialogoPresenter.mensajeAlerta(contexto, titulo, mensaje);
        }
    }

    public void esconderTeclado(View vista) {
        InputMethodManager inputMethodManager = (InputMethodManager) contexto.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(vista.getWindowToken(), 0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        // Cuando presiona el botón atrás en el dispositivo,
        // se llama a esta función y lo lleva a la actividad anterior realizada
        // llamando a la función de superclase, es decir, super.onBackPressed ().
        // Si no se llama a esta función, no ocurrirá nada, por lo tanto,
        // si desea deshabilitar la contrapresión, anule el onBackPressed ()
        // y comente la llamada a la superclase.
    }

    private class validarActivacionEdtBusqueda implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            limpiarVistaError();
        }

        @Override
        public void afterTextChanged(Editable s) {
            //implementación predeterminada ignorada
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //implementación predeterminada ignorada
        }
    }
}
