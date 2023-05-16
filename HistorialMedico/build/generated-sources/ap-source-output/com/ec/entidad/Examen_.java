package com.ec.entidad;

import com.ec.entidad.VisitaMedica;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

<<<<<<< HEAD
@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2023-05-15T15:34:46")
=======
@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2023-05-15T16:27:37")
>>>>>>> bdbcd52981222a151bfb454bedba76f8ac35969c
@StaticMetamodel(Examen.class)
public class Examen_ { 

    public static volatile SingularAttribute<Examen, Integer> idExamne;
    public static volatile SingularAttribute<Examen, Boolean> exaActivo;
    public static volatile SingularAttribute<Examen, String> exaPath;
    public static volatile SingularAttribute<Examen, VisitaMedica> idVisitaMedica;
    public static volatile SingularAttribute<Examen, String> exaDescripcion;

}