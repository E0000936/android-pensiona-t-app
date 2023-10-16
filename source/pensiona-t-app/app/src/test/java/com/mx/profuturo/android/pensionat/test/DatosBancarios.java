package com.mx.profuturo.android.pensionat.test;


import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import android.content.Context;

import com.mx.profuturo.android.pensionat.R;
import com.mx.profuturo.android.pensionat.presentation.dialogs.DialogoPresenter;
import com.mx.profuturo.android.pensionat.presentation.formulario.DatosBancariosFragment;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * Creado por Jose Antonio Acevedo Trejo en 14/03/2019.
 * Everis
 * antonioacevedot@gmail.com
 * version 1.0
 */
@RunWith(MockitoJUnitRunner.class)
public class DatosBancarios {

    private static final String TEST_STRING = "Aceptado";
    //Como no tenemos acceso a Contexto en nuestras clases de prueba de JUnit, debemos hacer mock de él
    @Mock
    Context mMockContext;
    @Mock
    private DatosBancariosFragment bancariosFragment;

    @Before
    public void setUp() {
    }

    @Test
    public void validarClabe() {
        ClabeBanco envioClabe = new ClabeBanco();
        ClabeBancoInterfaz clabeBanco = Mockito.mock(ClabeBancoInterfaz.class);
        Mockito.when(clabeBanco.getClabe()).thenReturn("012180015076590353");
        String clabe = envioClabe.envioClabe(clabeBanco);
        Assert.assertEquals("012180015076590353", clabe);
        bancariosFragment.validarClabeBanco(clabe);
    }

    @Test
    public void readStringFromContext() {
        //Devuelve el TEST_STRING cuando se llama a getString (R.string.hello_world)
        when(mMockContext.getString(R.string.text_hello_word)).thenReturn(TEST_STRING);
        // Crea un objeto de ClassUnderTest con el contexto simulado.
        DialogoPresenter.ClassUnderTest objectUnderTest = new DialogoPresenter.ClassUnderTest(mMockContext);
        // Almacena el valor de retorno de getHelloWorldString () en el resultado
        String result = objectUnderTest.getHelloWorldString();
        //Afirma que el resultado es el valor de TEST_STRING
        assertThat(result, CoreMatchers.is(TEST_STRING));
    }
}
