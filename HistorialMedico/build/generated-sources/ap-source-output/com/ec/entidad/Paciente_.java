package com.ec.entidad;

import com.ec.entidad.Usuario;
import com.ec.entidad.VisitaMedica;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2023-04-04T14:13:11")
@StaticMetamodel(Paciente.class)
public class Paciente_ { 

    public static volatile SingularAttribute<Paciente, String> pacSexo;
    public static volatile SingularAttribute<Paciente, String> pacTipoSangre;
    public static volatile SingularAttribute<Paciente, String> pacEdad;
    public static volatile SingularAttribute<Paciente, Usuario> idUsuario;
    public static volatile SingularAttribute<Paciente, String> pacAseguradora;
    public static volatile SingularAttribute<Paciente, String> pacEstadoCivil;
    public static volatile SingularAttribute<Paciente, String> pacRuc;
    public static volatile SingularAttribute<Paciente, String> pacApellidos;
    public static volatile CollectionAttribute<Paciente, VisitaMedica> visitaMedicaCollection;
    public static volatile SingularAttribute<Paciente, Integer> idPaciente;
    public static volatile SingularAttribute<Paciente, String> pacCorreo;
    public static volatile SingularAttribute<Paciente, String> pacNombres;
    public static volatile SingularAttribute<Paciente, Date> pacFechaNacimiento;
    public static volatile SingularAttribute<Paciente, String> pacDomicilio;
    public static volatile SingularAttribute<Paciente, String> pacTelefono;

}