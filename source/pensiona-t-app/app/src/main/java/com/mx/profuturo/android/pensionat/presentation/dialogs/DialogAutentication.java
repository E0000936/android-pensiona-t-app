package com.mx.profuturo.android.pensionat.presentation.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.mx.profuturo.android.pensionat.R;

public class DialogAutentication extends DialogFragment {
    Button btnAceptar;
    Button btnCancelar;

    private AceptListener aceptListener;
    private CancelListener cancelListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View vista = inflater.inflate(R.layout.dialog_autentication , null);
        initView(vista);
        builder.setView(vista);
        setCancelable(false);
        return builder.create();
    }

    private void initView(View view){
        btnAceptar =  view.findViewById(R.id.btn_aceptar);
        btnCancelar = view.findViewById(R.id.btn_cancelar);
        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aceptListener.onAcept(DialogAutentication.this);
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelListener.onCancel(DialogAutentication.this);
            }
        });
    }

    public void setListenerAceptar(AceptListener aceptListener){this.aceptListener = aceptListener;}
    public void setListenerCancel(CancelListener cancelListener){this.cancelListener = cancelListener;}

    public interface AceptListener{ void onAcept(DialogFragment dialog);}
    public interface CancelListener{ void onCancel(DialogFragment dialog);}
}
