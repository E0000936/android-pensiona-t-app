package com.mx.profuturo.android.pensionat.utils;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ValidacionesTest {
    private static final String MENSAJE = "Los objectos deben ser iguales";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test(expected = IllegalStateException.class)
    public void testIllegalStateExceptionValidador() {
        new ValidadorFormulario();
    }

    @Test
    public void testContrasenaValida() {
        String contrasena = "Pfol89+0=fdP";
        Assert.assertTrue(ValidadorFormulario.validarContrasena(contrasena));
    }

    @Test
    public void testContrasenaValidaSinMayusculas() {
        String contrasena = "profuturo2019***";
        Assert.assertTrue(ValidadorFormulario.validarContrasena(contrasena));
    }

    @Test
    public void testContrasenaValidaMayusculas() {
        String contrasena = "PROFUTURO2019**/";
        Assert.assertTrue(ValidadorFormulario.validarContrasena(contrasena));
    }

    @Test
    public void testContrasenaValidaContrasena() {
        String contrasena = "Profuturñ2019**/";
        Assert.assertTrue(ValidadorFormulario.validarContrasena(contrasena));
    }

    @Test
    public void testContrasenaInvalida() {
        String contrasena = "Pfdsd0*";
        Assert.assertFalse(ValidadorFormulario.validarContrasena(contrasena));
    }

    @Test
    public void testUsuarioValido() {
        String usuario = "PFP8909";
        Assert.assertTrue(ValidadorFormulario.validarUsuario(usuario));
    }

    @Test
    public void testUsuarioValidoNumeros() {
        String usuario = "123456";
        Assert.assertTrue(ValidadorFormulario.validarUsuario(usuario));
    }

    @Test
    public void testUsuarioValidoLetras() {
        String usuario = "abcdef";
        Assert.assertTrue(ValidadorFormulario.validarUsuario(usuario));
    }

    @Test
    public void testUsuarioInvalido() {
        String usuario = "8900";
        Assert.assertFalse(ValidadorFormulario.validarUsuario(usuario));
    }

    @Test
    public void testUsuarioInvalidoCaracter() {
        String usuario = "abcdfñ";
        Assert.assertFalse(ValidadorFormulario.validarUsuario(usuario));
    }

    @Test
    public void testNSSInvalido() {
        String nss = "721083030";
        Assert.assertFalse(ValidadorFormulario.validarNSS(nss));
    }

    @Test
    public void testNSSValido() {
        String nss = "72108303024";
        Assert.assertTrue(ValidadorFormulario.validarNSS(nss));
    }

    @Test
    public void testCorreoValido() {
        String correo = "email@dominio.com";
        Assert.assertTrue(ValidadorFormulario.validarCorreo(correo));
    }

    @Test
    public void testCorreoInvalido() {
        String correo = "email@dominio";
        Assert.assertFalse(ValidadorFormulario.validarCorreo(correo));
    }

    @Test
    public void testCurpValido() {
        String curp = "GUBS891119MMCTTN03";
        Assert.assertTrue(ValidadorFormulario.validarCurp(curp));
    }

    @Test
    public void testCurpInvalido() {
        String curp = "GUBS100011MMCTTN03";
        Assert.assertFalse(ValidadorFormulario.validarCurp(curp));
    }

    @Test
    public void testRfcValido() {
        String rfc = "GUBS891119";
        Assert.assertTrue(ValidadorFormulario.validarRFC(rfc));
    }

    @Test
    public void testRfcInvalido() {
        String rfc = "09434FGVBGR";
        Assert.assertFalse(ValidadorFormulario.validarRFC(rfc));
    }

    @Test
    public void testPolizaValida() {
        String poliza = "20190011245";
        Assert.assertTrue(ValidadorFormulario.validarPoliza(poliza));
    }

    @Test
    public void testFolioValido() {
        String folio = "31902005397";
        Assert.assertTrue(ValidadorFormulario.validarFolio(folio));
    }

    @Test
    public void testFolioInvalido() {
        String folio = "09232090890A";
        Assert.assertFalse(ValidadorFormulario.validarFolio(folio));
    }

    @Test
    public void testTelefonoValido() {
        String telefono = "5535328790";
        Assert.assertTrue(ValidadorFormulario.validarTelefono(telefono));
    }

    @Test
    public void testTelefonoInvalidoLongitud() {
        String telefono = "5535328790098";
        Assert.assertFalse(ValidadorFormulario.validarTelefono(telefono));
    }

    @Test
    public void testTelefonoInvalidoConsecutivo() {
        String telefono = "1234567890";
        Assert.assertFalse(ValidadorFormulario.validarTelefono(telefono));
    }

    @Test
    public void testTelefonoInvalidoRepetidos() {
        String telefono = "1111111111";
        Assert.assertFalse(ValidadorFormulario.validarTelefono(telefono));
    }

    @Test
    public void testTarjetaValida() {
        String visa = "4111111111111111";
        Assert.assertTrue(ValidadorFormulario.validarNumeroTarjeta(visa));
    }

    @Test
    public void testTarjetaMasterValida() {
        String master = "5500 0000 0000 0004";
        Assert.assertTrue(ValidadorFormulario.validarNumeroTarjeta(master));
    }

    @Test
    public void testTarjetaMasterInvalida() {
        String master = "0000 0000 0000 0004";
        Assert.assertFalse(ValidadorFormulario.validarNumeroTarjeta(master));
    }

    @Test
    public void testTarjetaInvalida() {
        String visa = "11111111111111";
        Assert.assertFalse(ValidadorFormulario.validarNumeroTarjeta(visa));
    }

    @Test
    public void testCLABEValida() {
        String clabe = "012783011039657595";
        Assert.assertTrue(ValidadorFormulario.validarCLABE(clabe));
    }

    @Test
    public void testDigitoVerificadorInvalido() {
        int[] cuenta = new int[]{0, 1, 1, 1, 8, 0, 0, 6, 0, 5, 0, 1, 1, 6, 9, 1, 2};
        int digitoVerificador = ValidarCLABE.validarCuentaBancaria(cuenta);
        Assert.assertNotEquals(MENSAJE, digitoVerificador, 7);
    }

    @Test
    public void testDigitoVerificadorValido() {
        int[] cuenta = new int[]{0, 2, 1, 1, 8, 0, 0, 6, 0, 5, 0, 1, 1, 6, 9, 1, 2};
        int digitoVerificador = ValidarCLABE.validarCuentaBancaria(cuenta);
        Assert.assertEquals(MENSAJE, 7, digitoVerificador);
    }

    @Test(expected = IllegalStateException.class)
    public void testIllegalStateExceptionValidarCLABE() {
        new ValidarCLABE();
    }

    @Test
    public void testDigitoVerificadorLongitudInvalida() {
        int[] cuenta = new int[]{0, 1, 1, 1, 8, 0, 0, 6, 0, 5, 0, 1, 1, 6, 9, 1, 2, 7};
        int digitoVerificador = ValidarCLABE.validarCuentaBancaria(cuenta);
        Assert.assertEquals(MENSAJE, digitoVerificador, -1);
    }
}
