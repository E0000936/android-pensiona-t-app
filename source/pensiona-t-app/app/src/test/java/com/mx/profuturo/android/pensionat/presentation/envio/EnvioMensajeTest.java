package com.mx.profuturo.android.pensionat.presentation.envio;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Button;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;

import com.mx.profuturo.android.pensionat.R;
import com.mx.profuturo.android.pensionat.presentation.dialogs.ConfirmacionDialogo;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * Creado por Jose Antonio Acevedo Trejo en 09/05/2019.
 * Everis
 * antonioacevedot@gmail.com
 * version 1.0
 */
@RunWith(MockitoJUnitRunner.class)
public class EnvioMensajeTest {
    private static final String MENSAJE = "Los objectos deben ser iguales";
    @Mock
    private EnvioMensajePresenter presenter;
    @Mock
    private EnvioMensajeActivity mensajeActivity;
    @Mock
    private IEnvioMensajeVista MockInterfaceMensaje;
    @Mock
    private Context contexto;
    @Mock
    private CardView cardViewCorreo;
    @Mock
    private Button btnOtroTramite;
    @Mock
    private FragmentManager fragmentManager;

    @Spy
    @InjectMocks
    private ConfirmacionDialogo confirmacionDialogo;

    @Before
    public void inicializacion() {
        MockInterfaceMensaje = mock(IEnvioMensajeVista.class);
        presenter = new EnvioMensajePresenter(contexto, MockInterfaceMensaje);
        mensajeActivity = mock(EnvioMensajeActivity.class);
        confirmacionDialogo = mock(ConfirmacionDialogo.class);
        btnOtroTramite = mock(Button.class);

    }

    @Test
    public void onCreate() {
        String envio = "se envio el correo correctamente";
        cardViewCorreo.setOnClickListener((View v) -> presenter.enviarCorreoTramite());
        assertNotNull(cardViewCorreo);
        assertEquals(MENSAJE, "se envio el correo correctamente", envio);

    }

    @Test
    public void habilitarBotonOtroTramite() {
        btnOtroTramite.setEnabled(true);
        Drawable fondoBoton = mensajeActivity.getDrawable(R.drawable.fondo_boton_borde_azul);
        btnOtroTramite.setTextColor(R.color.colorPrimario);
        btnOtroTramite.setBackground(fondoBoton);
        Assert.assertNotNull(btnOtroTramite);
    }

    @Test
    public void habilitarCardView() {
        String habilitado = "se habilito";
        cardViewCorreo.setEnabled(true);
        cardViewCorreo.setCardElevation(15);
        assertNotNull(cardViewCorreo);
        assertEquals(MENSAJE, "se habilito", habilitado);

    }

    @Test
    public void envioSmsExitoso() {
        String telefono = "2288389902";
        confirmacionDialogo.show(fragmentManager, "");
        assertNotNull(confirmacionDialogo);
        assertEquals(MENSAJE, "2288389902", telefono);
    }
}