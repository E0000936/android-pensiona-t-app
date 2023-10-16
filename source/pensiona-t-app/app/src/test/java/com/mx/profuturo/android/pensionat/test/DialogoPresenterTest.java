package com.mx.profuturo.android.pensionat.test;

import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import android.content.Context;

import com.mx.profuturo.android.pensionat.R;
import com.mx.profuturo.android.pensionat.presentation.dialogs.DialogoPresenter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * Created by Antonio Acevedo Trejo on 29/01/2019.
 * Company: Everis
 * Email: antonioacevedot@gmail.com
 * <p>
 * version: 1.0
 */
@RunWith(MockitoJUnitRunner.class)
public class DialogoPresenterTest {

    private static final String TEST_STRING = "Aceptado";

    //As we don't have access to Context in our JUnit test classes, we need to mock it
    @Mock
    Context mMockContext;

    @Before
    public void setUp() {
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
        assertThat(result, is(TEST_STRING));
        assertEquals("Afirma que el resultado es el valor de TEST_STRING", TEST_STRING, result);
    }
}