package com.ec.entidad;

import com.ec.entidad.Usuario;
import com.ec.entidad.VisitaMedica;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2023-05-10T13:48:02")
@StaticMetamodel(Paciente.class)
public class Paciente_ { 

    public static volatile SingularAttribute<Paciente, String> pacTipoSangre;
    public static volatile SingularAttribute<Paciente, Usuario> idUsuario;
    public static volatile SingularAttribute<Paciente, String> pacAseguradora;
    public static volatile SingularAttribute<Paciente, String> pacEnfermedadExistente;
    public static volatile SingularAttribute<Paciente, String> pacSp02;
    public static volatile SingularAttribute<Paciente, Boolean> pacEstado;
    public static volatile SingularAttribute<Paciente, String> pacApellidos;
    public static volatile SingularAttribute<Paciente, String> pacCuello;
    public static volatile SingularAttribute<Paciente, String> pacCintura;
    public static volatile SingularAttribute<Paciente, String> pacTelefonoReferencia;
    public static volatile SingularAttribute<Paciente, String> pacEnfermedadFamilia;
    public static volatile SingularAttribute<Paciente, String> pacAlegias;
    public static volatile SingularAttribute<Paciente, Date> pacFechaNacimiento;
    public static volatile SingularAttribute<Paciente, BigDecimal> pacPeso;
    public static volatile SingularAttribute<Paciente, String> pacDomicilio;
    public static volatile SingularAttribute<Paciente, BigDecimal> pacTalla;
    public static volatile SingularAttribute<Paciente, String> pacOtros;
    public static volatile SingularAttribute<Paciente, String> pacSexo;
    public static volatile SingularAttribute<Paciente, String> pacMovil;
    public static volatile SingularAttribute<Paciente, String> pacPerAbdominal;
    public static volatile SingularAttribute<Paciente, Integer> pacEdad;
    public static volatile SingularAttribute<Paciente, String> pacEstadoCivil;
    public static volatile SingularAttribute<Paciente, String> pacMedicacionActual;
    public static volatile SingularAttribute<Paciente, String> pacRuc;
    public static volatile SingularAttribute<Paciente, BigDecimal> pacImc;
    public static volatile SingularAttribute<Paciente, String> pacRefFamiliar;
    public static volatile CollectionAttribute<Paciente, VisitaMedica> visitaMedicaCollection;
    public static volatile SingularAttribute<Paciente, Integer> idPaciente;
    public static volatile SingularAttribute<Paciente, String> pacCirugias;
    public static volatile SingularAttribute<Paciente, String> pacPa;
    public static volatile SingularAttribute<Paciente, String> pacFc;
    public static volatile SingularAttribute<Paciente, String> pacTmBucal;
    public static volatile SingularAttribute<Paciente, String> pacCorreo;
    public static volatile SingularAttribute<Paciente, String> pacFotografia;
    public static volatile SingularAttribute<Paciente, String> pacNombres;
    public static volatile SingularAttribute<Paciente, String> pacTelefono;

}