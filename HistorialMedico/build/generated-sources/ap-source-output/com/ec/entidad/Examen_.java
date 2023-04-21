package com.ec.entidad;

import com.ec.entidad.VisitaMedica;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2023-04-21T06:16:07")
@StaticMetamodel(Examen.class)
public class Examen_ { 

    public static volatile SingularAttribute<Examen, Integer> idExamne;
    public static volatile SingularAttribute<Examen, Boolean> exaActivo;
    public static volatile SingularAttribute<Examen, String> exaPath;
    public static volatile SingularAttribute<Examen, VisitaMedica> idVisitaMedica;
    public static volatile SingularAttribute<Examen, String> exaDescripcion;

}