package com.ec.entidad;

import com.ec.entidad.Examen;
import com.ec.entidad.Paciente;
import com.ec.entidad.Receta;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2023-04-25T14:19:45")
@StaticMetamodel(VisitaMedica.class)
public class VisitaMedica_ { 

    public static volatile SingularAttribute<VisitaMedica, Paciente> idPaciente;
    public static volatile SingularAttribute<VisitaMedica, Integer> idVisitaMedica;
    public static volatile SingularAttribute<VisitaMedica, String> visDiagnostico;
    public static volatile SingularAttribute<VisitaMedica, String> visCargarCie10;
    public static volatile SingularAttribute<VisitaMedica, String> visReseta;
    public static volatile CollectionAttribute<VisitaMedica, Receta> recetaCollection;
    public static volatile CollectionAttribute<VisitaMedica, Examen> examenCollection;
    public static volatile SingularAttribute<VisitaMedica, Date> visFecha;
    public static volatile SingularAttribute<VisitaMedica, String> visObservacion;

}