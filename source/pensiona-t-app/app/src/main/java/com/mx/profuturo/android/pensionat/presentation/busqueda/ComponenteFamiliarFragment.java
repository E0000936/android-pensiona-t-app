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
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.mx.profuturo.android.pensionat.R;
import com.mx.profuturo.android.pensionat.data.local.db.realm.DBHelperRealm;
import com.mx.profuturo.android.pensionat.data.local.preferences.Sesion;
import com.mx.profuturo.android.pensionat.domain.model.Categoria;
import com.mx.profuturo.android.pensionat.domain.model.Componente;
import com.mx.profuturo.android.pensionat.domain.model.Expediente;
import com.mx.profuturo.android.pensionat.domain.model.Grupo;
import com.mx.profuturo.android.pensionat.domain.model.Poliza;
import com.mx.profuturo.android.pensionat.domain.model.Solicitante;
import com.mx.profuturo.android.pensionat.presentation.dialogs.AlertaDialogo;
import com.mx.profuturo.android.pensionat.presentation.dialogs.DialogoPresenter;
import com.mx.profuturo.android.pensionat.presentation.formulario.FormularioFragment;
import com.mx.profuturo.android.pensionat.utils.Constantes;
import com.mx.profuturo.android.pensionat.utils.Utilidades;
import com.mx.profuturo.android.pensionat.utils.VariablesGlobales;
import com.mx.profuturo.android.pensionat.vistasCustomizadas.Progress;
import com.mx.profuturo.android.pensionat.vistasCustomizadas.SpinnerCaja;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1>ComponenteFamiliarFragment</h1>
 * Clase que lleva la lógica para la selección
 * del componente familiar o grupo de pago.
 *
 * @author Everis
 * @version 1.0
 * @since 2019-03-24
 */
public class ComponenteFamiliarFragment extends Fragment
        implements GrupoFamiliarAdaptador.GrupoFamiliarInterfaz,
        IComponenteFamiliarVista,
        SpinnerCaja.OnSpinnerEventsListener {

    private static final String TAG = ComponenteFamiliarFragment.class.getSimpleName();
    public Context contexto;
    private TextView txtNumeroResultados;
    private SpinnerCaja spinnerPolizas;
    private List<Poliza> polizas;
    private TextView txtInstituto;
    private TextView txtTipo;
    private TextView txtEstatus;
    private ViewPager paginador;
    private TextView txtTitularPoliza;
    private Button btnSiguiente;
    private GrupoFamiliarAdaptador grupoFamiliarAdaptador;
    private ComponenteFamiliarPresenter componentePresenter;
    private DialogoPresenter dialogoPresenter;
    private Poliza poliza = new Poliza();
    private Grupo grupo = new Grupo();
    private Componente componente = new Componente();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmento_componente_familiar, parent, false);
        PrincipalActivity principalActivity = (PrincipalActivity) getActivity();
        if (principalActivity != null) {
            principalActivity.cambiarTituloBarra(contexto.getString(R.string.componente_familiar_titulo), contexto.getString(R.string.componente_familiar_subtitulo));
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        polizas = VariablesGlobales.getInstance().polizas;
        dialogoPresenter = new DialogoPresenter();
        TextView txtEmpleado = view.findViewById(R.id.txt_componente_empleado);
        String numeroEmpleado = getString(R.string.busqueda_empleado, Sesion.getInstancia(getContext()).getNumeroEmpleado());
        txtEmpleado.setText(numeroEmpleado);
        txtNumeroResultados = view.findViewById(R.id.txt_poliza_resultado);
        spinnerPolizas = view.findViewById(R.id.spinner_poliza);
        spinnerPolizas.setSpinnerEventsListener(this);
        txtInstituto = view.findViewById(R.id.txt_poliza_instituto);
        txtTipo = view.findViewById(R.id.txt_poliza_tipo);
        txtEstatus = view.findViewById(R.id.txt_poliza_estatus);
        txtTitularPoliza = view.findViewById(R.id.text_componente_titular);
        paginador = view.findViewById(R.id.pager_componente_familiar);
        btnSiguiente = view.findViewById(R.id.btn_componente_familiar_siguiente);
        Button btnCancelar = view.findViewById(R.id.btn_componente_familiar_cancelar);
        btnCancelar.setOnClickListener((View vista) -> mostrarDialogoCancelar());
        TabLayout tabLayout = view.findViewById(R.id.tab_componente_familiar);
        paginador.setPageMargin(40);
        componentePresenter = new ComponenteFamiliarPresenter(contexto, this);
        btnSiguiente.setOnClickListener((View v) -> validarGrupoPago());
        tabLayout.setupWithViewPager(paginador, true);
        actualizarDatosVista();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            contexto = context;
        }
    }

    private void validarGrupoPago() {
        Progress.mostrar(contexto);
        Utilidades.limpiarDatosBusqueda(contexto);
        int idPoliza = 0;
        int idGrupoPago = 0;
        int idOferta = 0;

        if (poliza.getIdPoliza() != null) {
            idPoliza = poliza.getIdPoliza();
        }
        if (grupo.getIdGrupoPago() != null) {
            idGrupoPago = grupo.getIdGrupoPago();
        }
        if (grupo.getIdOferta() != null) {
            idOferta = grupo.getIdOferta();
        }
        componentePresenter.obtenerDetalleSolicitante(idPoliza, idOferta, idGrupoPago);
    }

    public void actualizarDatosVista() {
        txtNumeroResultados.setText(obtenerLeyendaNumPolizas(polizas.size()));
        GenericoAdaptador adaptador = new GenericoAdaptador(getContext(), obtenerListFormatoPolizas(polizas));
        spinnerPolizas.setAdapter(adaptador);
        spinnerPolizas.setSelection(0);
        spinnerPolizas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                adaptador.setPosicionSeleccionada(i);
                poliza = polizas.get(i);
                actualizarVista(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                throw new UnsupportedOperationException();
            }
        });
    }

    public String obtenerLeyendaNumPolizas(int tamano) {
        if (tamano > 1) {
            return contexto.getString(R.string.componente_familiar_resultados, String.valueOf(tamano));
        } else {
            return contexto.getString(R.string.componente_familiar_resultado, String.valueOf(tamano));
        }
    }

    public ArrayList<Categoria> obtenerListFormatoPolizas(List<Poliza> polizas) {
        ArrayList<Categoria> lista = new ArrayList<>();
        for (Poliza poliza : polizas) {
            if (poliza.getGrupos().size() > 0) {
                Grupo grupo = poliza.getGrupos().get(0);
                if (grupo != null && !grupo.getNumRenta().contains("Sin Póliza")) {
                    String nombrePoliza = "Póliza ";
                    nombrePoliza = nombrePoliza.concat(grupo.getNumRenta());
                    Categoria categoria = new Categoria(poliza.getIdPoliza(), 0, nombrePoliza, "");
                    lista.add(categoria);
                }
            }
        }

        return lista;
    }

    private void mostrarDialogoCancelar() {
        String titulo = contexto.getString(R.string.componente_familiar_cancelar_tramite);
        String mensaje = contexto.getString(R.string.componente_familiar_cancelar_aviso);
        String confirmacion = contexto.getString(R.string.componente_familiar_cancelar_confirmacion);
        AlertaDialogo cancelarDialogo = AlertaDialogo.getInstancia(titulo, mensaje, confirmacion);
        cancelarDialogo.setDelegado(() -> {
            Utilidades.limpiarDatosTramite(contexto);
            if (getActivity() != null) {
                getActivity().onBackPressed();
            }
        });
        if (getFragmentManager() != null) {
            cancelarDialogo.show(getFragmentManager(), TAG);
        }
    }

    private void actualizarVista(int posicion) {
        habilitarBotonSiguiente(false);
        grupoFamiliarAdaptador = new GrupoFamiliarAdaptador(getContext(), polizas.get(posicion).getGrupos(), this);
        paginador.setAdapter(grupoFamiliarAdaptador);
        paginador.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
                //metodo de la clase OnPageChangeListener se requiere para darle funcionalidad al viewPager
            }

            @Override
            public void onPageSelected(int i) {
                habilitarBotonSiguiente(false);
                grupoFamiliarAdaptador.notifyDataSetChanged();
            }

            @Override
            public void onPageScrollStateChanged(int i) {
                //metodo de la clase OnPageChangeListener se requiere para darle funcionalidad al viewPager
            }
        });

        validacionActualizarVista(posicion);
    }

    private void validacionActualizarVista(int posicion) {
        Poliza poliza = polizas.get(posicion);
        if (poliza.getGrupos().size() > 0) {
            grupo = poliza.getGrupos().get(0);
            if (grupo != null) {
                rellenarCampos(posicion);
            }
        }
    }

    private void rellenarCampos(int posicion) {
        Poliza poliza = polizas.get(posicion);
        String nombreCompleto = "";

        if (poliza.getIdOferta() == 0) {
            txtInstituto.setText(poliza.getTipoRegimen());
            txtEstatus.setText(poliza.getEstatus());
            txtTipo.setText(poliza.getTipoPension());
            if (grupo.getApPaternoPoliza() != null &&
                    grupo.getApMaternoPoliza() != null &&
                    grupo.getNombrePoliza() != null) {
                nombreCompleto = grupo.getApPaternoPoliza() + " " + grupo.getApMaternoPoliza() + " " + grupo.getNombrePoliza();
            }
        } else {
            txtInstituto.setText(grupo.getDescRegimenOferta());
            txtEstatus.setText(grupo.getEstatusOferta());
            txtTipo.setText(grupo.getTipoPensionOferta());
            if (grupo.getApPaternoOferta() != null &&
                    grupo.getApMaternoOferta() != null &&
                    grupo.getNombreOferta() != null) {
                nombreCompleto = grupo.getApPaternoOferta() + " " + grupo.getApMaternoOferta() + " " + grupo.getNombreOferta();
            }
        }

        txtTitularPoliza.setText(nombreCompleto);
    }

    @Override
    public void notificarGrupoSeleccionado(Grupo grupo) {
        this.grupo = grupo;
        Componente componente = new Componente();
        componente.setParentesco(Constantes.Nombres.DESC_PARENTESCO);
        this.componente = componente;
        ViewGroup vista = grupoFamiliarAdaptador.getRegisteredFragment(paginador.getCurrentItem());
        View layoutGrupoFamiliar = vista.findViewById(R.id.layout_grupo_familiar);
        layoutGrupoFamiliar.setBackground(ContextCompat.getDrawable(contexto, R.drawable.fondo_borde_azul_ceruleo));
        habilitarBotonSiguiente(true);
    }

    @Override
    public void notificarComponenteSeleccionado(Componente componente) {
        Poliza poliza = polizas.get(spinnerPolizas.getSelectedItemPosition());
        int posicion = paginador.getCurrentItem();
        this.grupo = poliza.getGrupos().get(posicion);
        this.componente = componente;

        ViewGroup vista = grupoFamiliarAdaptador.getRegisteredFragment(paginador.getCurrentItem());
        RadioButton grupoFamiliar = vista.findViewById(R.id.radio_grupo_familiar_nombre);
        View layoutGrupoFamiliar = vista.findViewById(R.id.layout_grupo_familiar);
        layoutGrupoFamiliar.setBackground(ContextCompat.getDrawable(contexto, R.drawable.fondo_borde_azul_ceruleo));
        grupoFamiliar.setChecked(true);
        habilitarBotonSiguiente(true);
    }

    public void habilitarBotonSiguiente(boolean habilitar) {
        btnSiguiente.setEnabled(habilitar);
        Drawable fondoBoton = habilitar ? contexto.getDrawable(R.drawable.fondo_boton_azul) : contexto.getDrawable(R.drawable.fondo_boton_gris);
        btnSiguiente.setBackground(fondoBoton);
    }

    @Override
    public void consultarDetalleSolicitanteError(String titulo, String mensaje) {
        Progress.ocultar();
        if (mensaje != null) {
            dialogoPresenter.mensajeAlerta(contexto, titulo, mensaje);
        }
    }

    @Override
    public void consultarDetalleSolicitanteExitoso(Solicitante solicitante) {
        Progress.ocultar();
        VariablesGlobales.getInstance().solicitante = solicitante;
        DBHelperRealm.insertarActualizarExpendiente(crearObjetoExpediente(grupo, componente, poliza));
        if (getFragmentManager() != null) {
            FragmentTransaction formulario = getFragmentManager().beginTransaction();
            FormularioFragment formularioFragment = new FormularioFragment();
            formularioFragment.delegado = (PrincipalActivity) getActivity();
            formulario.replace(R.id.frame_principal, formularioFragment, "FormularioFragment");
            formulario.addToBackStack("ComponenteFamiliarFragment");
            formulario.commitAllowingStateLoss();
        }
    }

    public Expediente crearObjetoExpediente(Grupo grupo, Componente componente, Poliza poliza) {
        String nss;
        String regimen;
        int idRegimen = 0;
        String sexo;
        int idSexo = 0;

        if (grupo.getSexoPoliza() != null) {
            sexo = grupo.getSexoPoliza();
            idSexo = grupo.getIdSexoPol();
        } else {
            sexo = grupo.getSexoOferta();
            idSexo = grupo.getIdSexoOferta();
        }
        if (componente.getSexo() != null) {
            sexo = componente.getSexo();
            idSexo = componente.getIdSexo();
        }

        if (grupo.getDescRegimenOferta() != null) {
            regimen = grupo.getDescRegimenOferta();
        } else {
            regimen = poliza.getTipoRegimen();
        }

        if (grupo.getNssOferta() != null) {
            nss = grupo.getNssOferta();
        } else {
            nss = grupo.getNssPoliza();
        }
        if (grupo.getIdTipoRegimenOferta() != null) {
            idRegimen = grupo.getIdTipoRegimenOferta();
        } else {
            idRegimen = poliza.getIdRegimen();
        }


        Expediente expediente = new Expediente();
        expediente.setNombreTitular(grupo.getNombrePoliza());
        expediente.setApPaternoTitular(grupo.getApPaternoPoliza());
        expediente.setApMaternoTitular(grupo.getApMaternoPoliza());
        expediente.setPoliza(grupo.getNumRenta());
        expediente.setGrupoPago(grupo.getGrupoPago());
        expediente.setRegimen(regimen);
        expediente.setSexo(sexo);
        expediente.setIdSexo(idSexo);
        expediente.setIdTipoRegimen(idRegimen);
        expediente.setIdBeneficiarioOferta(componente.getIdBeneficiarioOferta());
        expediente.setIdBeneficiarioPoliza(componente.getIdBeneficiarioPoliza());
        expediente.setParentescoSolicitante(componente.getParentesco());
        expediente.setNss(nss);
        return expediente;
    }

    @Override
    public void onSpinnerOpened(Spinner spinner) {
        spinner.setBackground(contexto.getDrawable(R.drawable.fondo_spinner_borde_abierto));
    }

    @Override
    public void onSpinnerClosed(Spinner spinner) {
        spinner.setBackground(contexto.getDrawable(R.drawable.fondo_spinner_borde));
    }
}

