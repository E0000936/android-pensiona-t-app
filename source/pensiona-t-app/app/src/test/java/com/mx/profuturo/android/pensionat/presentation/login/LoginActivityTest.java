package com.mx.profuturo.android.pensionat.presentation.login;

import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;

import com.mx.profuturo.android.pensionat.data.external.web.volley.LoginServicio;
import com.mx.profuturo.android.pensionat.test.Usuario;
import com.mx.profuturo.android.pensionat.test.UsuarioContrasena;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * Created by Antonio Acevedo Trejo on 29/01/2019.
 * Company: Everis
 * Email: antonioacevedot@gmail.com
 * <p>
 * version: 1.0
 */
@RunWith(MockitoJUnitRunner.class)

public class LoginActivityTest {
    @Before
    public void setUt() {
    }

    @Test
    public void iniciarSesionExitoso() {
        LoginServicio loginServicio = mock(LoginServicio.class);
        assertNull(loginServicio.numeroEmpleado);//JUnit assertion
    }

    @Test
    public void testEnviarUsuario() {
        UsuarioContrasena usuarioContrasena = new UsuarioContrasena();
        Usuario usuarioInterface = Mockito.mock(Usuario.class);
        Mockito.when(usuarioInterface.getUsuario()).thenReturn("005459");
        String bodyServicio = usuarioContrasena.envioUsuario(usuarioInterface);
        Assert.assertEquals("005459", bodyServicio);
    }

    @Test
    public void obtenerPermisosAlmacenamiento() {
        LoginActivity loginActivity = new LoginActivity();
        loginActivity.obtenerPermisosAlmacenamiento();
        Assert.assertNotNull(loginActivity);
    }
}
