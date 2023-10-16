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
package com.mx.profuturo.android.pensionat.data.local.db.realm;

import com.mx.profuturo.android.pensionat.domain.model.Banco;
import com.mx.profuturo.android.pensionat.domain.model.Captura;
import com.mx.profuturo.android.pensionat.domain.model.Catalogo;
import com.mx.profuturo.android.pensionat.domain.model.Categoria;
import com.mx.profuturo.android.pensionat.domain.model.Digitalizacion;
import com.mx.profuturo.android.pensionat.domain.model.Documento;
import com.mx.profuturo.android.pensionat.domain.model.Domicilio;
import com.mx.profuturo.android.pensionat.domain.model.Expediente;
import com.mx.profuturo.android.pensionat.domain.model.Peticion;
import com.mx.profuturo.android.pensionat.domain.model.Referencia;
import com.mx.profuturo.android.pensionat.domain.model.Solicitante;
import com.mx.profuturo.android.pensionat.domain.model.Subtramite;
import com.mx.profuturo.android.pensionat.domain.model.Telefono;
import com.mx.profuturo.android.pensionat.domain.model.Tramite;
import com.mx.profuturo.android.pensionat.utils.Constantes;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * <h1>DBHelperRealm</h1>
 * Clase que contiene las consultas a la base de datos interna.
 *
 * @author Everis
 * @version 1.0
 * @since 2019-02-21
 */
public class DBHelperRealm {
    private static final String DEFAULT_NINGUNA = "Ninguna";
    private static final String DEFAULT_VACIA = "Seleccionar";
    private static final String COLUMNA_CATALOGOS = "catalogos.id";
    private static final String COLUMNA_DESCRIPCION = "descripcion";
    private static final String COLUMNA_DESCRIPCION_DIGITALIZACION = "descripcionDigitalizacion";
    private static final String COLUMNA_ESTATUS = "estatus";
    private static final Categoria CATEGORIA_VACIA = new Categoria(0, 0, "Seleccionar", "");

    public static void limpiar() {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction((Realm r) -> {
            realm.delete(Solicitante.class);
            realm.delete(Referencia.class);
            realm.delete(Domicilio.class);
            realm.delete(Telefono.class);
            realm.delete(Banco.class);
            realm.delete(Expediente.class);
            realm.delete(Captura.class);
            realm.delete(Digitalizacion.class);
        });
    }

    public static boolean validaExistenCatalogos() {
        Realm realm = Realm.getDefaultInstance();
        List<Catalogo> catalogoList = realm.where(Catalogo.class).findAll();
        return catalogoList.size() != 0;
    }

    public static void guadarCatalogos(List<Catalogo> catalogos) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction((Realm r) -> {
            RealmList<Catalogo> catalogoRealmList = new RealmList<>();
            catalogoRealmList.addAll(catalogos);
            r.insert(catalogoRealmList);
        });
    }

    public static void guardarPeticiones(List<Peticion> peticiones) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction((Realm r) -> {
            realm.delete(Peticion.class);
            realm.delete(Tramite.class);
            realm.delete(Subtramite.class);
            realm.delete(Documento.class);
            r.insert(peticiones);
        });
    }

    public static ArrayList<Categoria> obtenerCatalogo(int id) {
        Realm realm = Realm.getDefaultInstance();
        ArrayList<Categoria> listaCatalogo = new ArrayList<>();
        String leyendaInicial = (id == Constantes.Catalogos.EXCEPCION) ? DEFAULT_NINGUNA : DEFAULT_VACIA;
        Categoria categoriaVacia = new Categoria(0, 0, leyendaInicial, "");
        RealmResults<Categoria> valores = realm.where(Categoria.class).equalTo(COLUMNA_CATALOGOS, id).findAll();
        listaCatalogo.add(categoriaVacia);

        // Para catálogo paises se manipula la estructura
        // ya que la descripción que desean se visualice viene en un orden diferente
        if (id == Constantes.Catalogos.PAIS) {
            for (Categoria categoria : valores) {
                Categoria categoria1 = new Categoria();
                categoria1.setId(categoria.getId());
                categoria1.setClave(categoria.getClave());
                categoria1.setDescripcion(categoria.getDescCompleta());
                categoria1.setDescCompleta(categoria.getDescCompleta());
                listaCatalogo.add(categoria1);
            }
        } else {
            listaCatalogo.addAll(valores);
        }

        return listaCatalogo;
    }

    public static void insertarActualizarSolicitante(Solicitante solicitante) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction((Realm r) -> {
            Solicitante solicitanteRealm = realm.where(Solicitante.class).findFirst();
            if (solicitanteRealm != null) {

                solicitanteRealm1(solicitanteRealm);

                solicitanteRealm.setNombre(solicitante.getNombre());
                solicitanteRealm.setApPaterno(solicitante.getApPaterno());
                solicitanteRealm.setApMaterno(solicitante.getApMaterno());
                solicitanteRealm.setIdNacionalidad(solicitante.getIdNacionalidad());
                solicitanteRealm.setNacionalidad(solicitante.getNacionalidad());
                solicitanteRealm.setFechaNacimiento(solicitante.getFechaNacimiento());
                solicitanteRealm.setOcupacion(solicitante.getOcupacion());
                solicitanteRealm.setCurp(solicitante.getCurp());
                solicitanteRealm.setRfc(solicitante.getRfc());
                solicitanteRealm.seteFirma(solicitante.geteFirma());
                solicitanteRealm.setCorreo(solicitante.getCorreo());
                solicitanteRealm.setIdOcupacion(solicitante.getIdOcupacion());

                solicitanteRealm2(solicitanteRealm, solicitante, r);

                if (solicitante.getIdTitularCobro() != null) {
                    solicitanteRealm.setIdTitularCobro(solicitante.getIdTitularCobro());
                }
            } else {
                r.insertOrUpdate(solicitante);
            }
        });
    }

    private static void solicitanteRealm2(Solicitante solicitanteRealm, Solicitante solicitante, Realm r) {
        if (solicitante.getBanco() != null) {
            solicitanteRealm.setBanco(r.copyToRealm(solicitante.getBanco()));
        }
        if (solicitante.getDomicilio() != null) {
            solicitanteRealm.setDomicilio(r.copyToRealm(solicitante.getDomicilio()));
        }
        if (solicitante.getReferencias() != null) {
            solicitanteRealm.getReferencias().addAll(solicitante.getReferencias());
        }
        if (solicitante.getTelefonos() != null) {
            solicitanteRealm.getTelefonos().addAll(solicitante.getTelefonos());
        }
    }

    private static void solicitanteRealm1(Solicitante solicitanteRealm) {
        if (solicitanteRealm.getTelefonos() != null) {
            solicitanteRealm.getTelefonos().deleteAllFromRealm();
        }
        if (solicitanteRealm.getReferencias() != null) {
            solicitanteRealm.getReferencias().deleteAllFromRealm();
        }
        if (solicitanteRealm.getBanco() != null) {
            solicitanteRealm.getBanco().deleteFromRealm();
        }
        if (solicitanteRealm.getDomicilio() != null) {
            solicitanteRealm.getDomicilio().deleteFromRealm();
        }
    }

    public static Solicitante obtenerSolicitante() {
        Realm realm = Realm.getDefaultInstance();
        return realm.where(Solicitante.class).findFirst();
    }

    public static Integer obtenerIdTelefono(String tipoTelefono) {
        Realm realm = Realm.getDefaultInstance();
        Categoria categoria = realm.where(Categoria.class).equalTo(COLUMNA_DESCRIPCION, tipoTelefono).findFirst();
        if (categoria != null) {
            return categoria.getClave();
        } else {
            return 0;
        }
    }

    public static ArrayList<Categoria> obtenerTramites(int id) {
        Realm realm = Realm.getDefaultInstance();
        ArrayList<Categoria> listaTramites = new ArrayList<>();
        listaTramites.add(CATEGORIA_VACIA);
        Peticion peticion = realm.where(Peticion.class).equalTo("id", id).findFirst();
        if (peticion != null) {
            for (Tramite tramite : peticion.getTramites()) {
                listaTramites.add(new Categoria(tramite.getId(), tramite.getIdTipoPeticion(), tramite.getNombre(), ""));
            }
        }

        return listaTramites;
    }

    public static ArrayList<Categoria> obtenerSubtramites(int id) {
        Realm realm = Realm.getDefaultInstance();
        ArrayList<Categoria> listaSubtramites = new ArrayList<>();
        listaSubtramites.add(CATEGORIA_VACIA);
        Tramite tramites = realm.where(Tramite.class).equalTo("id", id).findFirst();
        if (tramites != null) {
            for (Subtramite subtramite : tramites.getSubtramites()) {
                listaSubtramites.add(new Categoria(subtramite.getId(), subtramite.getIdParamEnvio(), subtramite.getNombre(), ""));
            }
        }

        return listaSubtramites;
    }

    public static RealmList<Documento> obtenerDocumentos(int id) {
        Realm realm = Realm.getDefaultInstance();
        RealmList<Documento> listaDocumetos = new RealmList<>();
        Subtramite subtramite = realm.where(Subtramite.class).equalTo("id", id).findFirst();
        if (subtramite != null) {
            for (Documento documento : subtramite.getDocumentos()) {
                Documento doc = new Documento();
                doc.setAyuda(documento.getAyuda());
                doc.setEsObligatorio(documento.getEsObligatorio());
                doc.setDescripcion(documento.getDescripcion());
                doc.setNumeroDocumentos(documento.getNumeroDocumentos());
                doc.setIdParamEnvioProceso(documento.getIdParamEnvioProceso());
                doc.setIdTipoDoc(documento.getIdTipoDoc());
                listaDocumetos.add(doc);
            }
        }

        return listaDocumetos;
    }

    public static void actualizarCargaExitosaCaptura(String nombre, int idParamEnvioProceso) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction((Realm r) -> {
            Captura capturaRealm = realm.where(Captura.class)
                    .equalTo("ruta", nombre)
                    .equalTo("idParamEnvioProceso", idParamEnvioProceso)
                    .findFirst();
            if (capturaRealm != null) {
                capturaRealm.setCargaExitosa(1);
            }
        });
    }

    public static Captura obtenerCaptura() {
        Realm realm = Realm.getDefaultInstance();
        return realm.where(Captura.class).equalTo("cargaExitosa", 0).equalTo(COLUMNA_ESTATUS, 1).findFirst();
    }

    public static long obtenerNumeroCapturas() {
        Realm realm = Realm.getDefaultInstance();
        return realm.where(Captura.class).equalTo("cargaExitosa", 1).equalTo(COLUMNA_ESTATUS, 1).count();
    }

    public static long obtenerTotalCapturas() {
        int SumaDOs = 2;
        Realm realm = Realm.getDefaultInstance();
        return realm.where(Captura.class).equalTo(COLUMNA_ESTATUS, 1).count() + SumaDOs;
    }

    public static void actualizarFolioMIT(String folioMit) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction((Realm r) -> {
            Expediente expedienteRealm = realm.where(Expediente.class).findFirst();
            if (expedienteRealm != null) {
                expedienteRealm.setFolioMit(folioMit);
            }
        });
    }

    public static void actualizarExcepcion(String excepcion, Integer idExcepcion) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction((Realm r) -> {
            Expediente expedienteRealm = realm.where(Expediente.class).findFirst();
            if (expedienteRealm != null) {
                expedienteRealm.setExcepcion(excepcion);
                expedienteRealm.setIdExcepcion(idExcepcion);
            }
        });
    }

    public static void insertarActualizarExpendiente(Expediente expediente) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction((Realm r) -> {
            Expediente expedienteRealm = realm.where(Expediente.class).findFirst();
            if (expedienteRealm != null) {
                realm.delete(Digitalizacion.class);
                realm.delete(Captura.class);
                tramiteSubtramite(expediente, expedienteRealm);
                peticionTitular(expediente, expedienteRealm);
                nssPoliza(expediente, expedienteRealm);
                folioRegimen(expediente, expedienteRealm);
                folioMit(expediente, expedienteRealm);
                excepcion(expediente, expedienteRealm);
                if (expediente.getIdBeneficiarioPoliza() != null) {
                    expedienteRealm.setIdBeneficiarioPoliza(expediente.getIdBeneficiarioPoliza());
                }
            } else {
                r.insertOrUpdate(expediente);
            }
        });
    }

    private static void excepcion(Expediente expediente, Expediente expedienteRealm) {
        if (expediente.getExcepcion() != null) {
            expedienteRealm.setExcepcion(expediente.getExcepcion());
        }
        if (expediente.getIdExcepcion() != null) {
            expedienteRealm.setIdExcepcion(expediente.getIdExcepcion());
        }
        if (expediente.getIdBeneficiarioOferta() != null) {
            expedienteRealm.setIdBeneficiarioOferta(expediente.getIdBeneficiarioOferta());
        }
        if (expediente.getParentescoSolicitante() != null) {
            expedienteRealm.setParentescoSolicitante(expediente.getParentescoSolicitante());
        }
    }

    private static void folioMit(Expediente expediente, Expediente expedienteRealm) {
        if (expediente.getFolioMit() != null) {
            expedienteRealm.setFolioMit(expediente.getFolioMit());
        }
        if (expediente.getIdTipoRegimen() != null) {
            expedienteRealm.setIdTipoRegimen(expediente.getIdTipoRegimen());
        }
        if (expediente.getNss() != null) {
            expedienteRealm.setNss(expediente.getNss());
        }
        if (expediente.getIdSexo() != null) {
            expedienteRealm.setIdSexo(expediente.getIdSexo());
        }
    }

    private static void folioRegimen(Expediente expediente, Expediente expedienteRealm) {
        if (expediente.getRegimen() != null) {
            expedienteRealm.setRegimen(expediente.getRegimen());
        }
        if (expediente.getObservaciones() != null) {
            expedienteRealm.setObservaciones(expediente.getObservaciones());
        }
        if (expediente.getIdParamEnvio() != null) {
            expedienteRealm.setIdParamEnvio(expediente.getIdParamEnvio());
        }
        if (expediente.getSexo() != null) {
            expedienteRealm.setSexo(expediente.getSexo());
        }
    }

    private static void nssPoliza(Expediente expediente, Expediente expedienteRealm) {
        if (expediente.getNss() != null) {
            expedienteRealm.setNss(expediente.getNss());
        }
        if (expediente.getPoliza() != null) {
            expedienteRealm.setPoliza(expediente.getPoliza());
        }
        if (expediente.getGrupoPago() != null) {
            expedienteRealm.setGrupoPago(expediente.getGrupoPago());
        }
        if (expediente.getIdPeticion() != null) {
            expedienteRealm.setIdPeticion(expediente.getIdPeticion());
        }
    }

    private static void tramiteSubtramite(Expediente expediente, Expediente expedienteRealm) {
        if (expediente.getIdTramite() != null) {
            expedienteRealm.setIdTramite(expediente.getIdTramite());
        }
        if (expediente.getTramite() != null) {
            expedienteRealm.setTramite(expediente.getTramite());
        }
        if (expediente.getIdSubtramite() != null) {
            expedienteRealm.setIdSubtramite(expediente.getIdSubtramite());
        }
        if (expediente.getSubtramite() != null) {
            expedienteRealm.setSubtramite(expediente.getSubtramite());
        }
    }

    private static void peticionTitular(Expediente expediente, Expediente expedienteRealm) {
        if (expediente.getPeticion() != null) {
            expedienteRealm.setPeticion(expediente.getPeticion());
        }
        if (expediente.getNombreTitular() != null) {
            expedienteRealm.setNombreTitular(expediente.getNombreTitular());
        }
        if (expediente.getApPaternoTitular() != null) {
            expedienteRealm.setApPaternoTitular(expediente.getApPaternoTitular());
        }
        if (expediente.getApMaternoTitular() != null) {
            expedienteRealm.setApMaternoTitular(expediente.getApMaternoTitular());
        }
    }

    public static String obtenerTitularPoliza() {
        Realm realm = Realm.getDefaultInstance();
        Expediente expediente = realm.where(Expediente.class).findFirst();
        if (expediente != null) {
            String nombre = "";
            if (expediente.getNombreTitular() != null) {
                nombre = nombre.concat(expediente.getNombreTitular() + " ");
            }
            if (expediente.getApPaternoTitular() != null) {
                nombre = nombre.concat(expediente.getApPaternoTitular() + " ");
            }
            if (expediente.getApMaternoTitular() != null) {
                nombre = nombre.concat(expediente.getApMaternoTitular());
            }
            return nombre;
        } else {
            return "";
        }
    }

    public static void actualizarObservacionesExpediente(String observaciones) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction((Realm r) -> {
            Expediente expedienteRealm = realm.where(Expediente.class).findFirst();
            if (expedienteRealm != null) {
                expedienteRealm.setObservaciones(observaciones);
            }
        });
    }

    public static void insertarDigitalizaciones(List<Digitalizacion> digitalizaciones) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction((Realm r) -> {
            realm.delete(Digitalizacion.class);
            realm.delete(Captura.class);
            r.insert(digitalizaciones);
        });
    }

    public static void actualizarDigitalizacion(Digitalizacion digitalizacion) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction((Realm r) -> {
            Digitalizacion digitalizacionRealm = realm.where(Digitalizacion.class)
                    .equalTo(COLUMNA_DESCRIPCION_DIGITALIZACION, digitalizacion.getDescripcion())
                    .equalTo("idParametroEnvioProceso", digitalizacion.getIdParamEnvioProceso())
                    .findFirst();
            if (digitalizacionRealm != null) {
                if (digitalizacionRealm.getCapturasArchivo() != null) {
                    for (int x = 0; x < digitalizacionRealm.getCapturasArchivo().size(); x++) {
                        Captura captura = digitalizacion.getCapturasArchivo().get(x);
                        Captura capturaRealm = digitalizacionRealm.getCapturasArchivo().get(x);
                        if (capturaRealm != null && captura != null) {
                            capturaRealm.setEstatus(captura.getEstatus());
                            capturaRealm.setNombre(captura.getNombre());
                            capturaRealm.setNumero(captura.getNumero());
                            capturaRealm.setIdParamEnvioProceso(captura.getIdParamEnvioProceso());
                            capturaRealm.setIdTipoDoc(captura.getIdTipoDoc());
                            capturaRealm.setCargaExitosa(captura.getCargaExitosa());
                            capturaRealm.setRuta(captura.getRuta());
                        }
                    }
                }

                digitalizacionRealm.setTomarCaptura(digitalizacion.getTomarCaptura());
            }
        });
    }

    public static Expediente obtenerExpediente() {
        Realm realm = Realm.getDefaultInstance();
        return realm.where(Expediente.class).findFirst();
    }

    public static List<Digitalizacion> obtenerDigitalizaciones() {
        Realm realm = Realm.getDefaultInstance();
        return realm.where(Digitalizacion.class).findAll();
    }

    public static List<Digitalizacion> obtenerDocumentosEntregados() {
        Realm realm = Realm.getDefaultInstance();
        return realm.where(Digitalizacion.class).greaterThan("capturasDigitalizacion", 0).greaterThan("tomarCapturaDigitalizaion", 0).findAll();
    }

    public static void eliminarImagenCaptura(String descripcion, Integer numeroCaptura) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction((Realm r) -> {
            Digitalizacion digitalizacionRealm = realm.where(Digitalizacion.class).equalTo(COLUMNA_DESCRIPCION_DIGITALIZACION, descripcion).findFirst();
            if (digitalizacionRealm != null) {
                int capturasActuales = digitalizacionRealm.getTomarCaptura();
                digitalizacionRealm.setTomarCaptura(capturasActuales - 1);
                if (digitalizacionRealm.getCapturasArchivo() != null) {
                    for (int x = 0; x < digitalizacionRealm.getCapturasArchivo().size(); x++) {
                        Captura capturaRealm = digitalizacionRealm.getCapturasArchivo().get(x);
                        if (capturaRealm != null) {
                            eliminarCapturaRealm(capturaRealm, numeroCaptura);
                        }
                    }
                }
            }
        });
    }

    private static void eliminarCapturaRealm(Captura capturaRealm, Integer numeroCaptura) {
        if (capturaRealm.getNumero().equals(numeroCaptura)) {
            capturaRealm.setEstatus(0);
            capturaRealm.setNombre("");
            capturaRealm.setCargaExitosa(0);
            capturaRealm.setRuta("");
        }
    }
}
