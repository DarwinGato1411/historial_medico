package com.ec.entidad;

import com.ec.entidad.VisitaMedica;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2023-03-31T10:56:45")
@StaticMetamodel(Receta.class)
public class Receta_ { 

    public static volatile SingularAttribute<Receta, String> recDescripcion;
    public static volatile SingularAttribute<Receta, VisitaMedica> idVisitaMedica;
    public static volatile SingularAttribute<Receta, String> recMedicamento;
    public static volatile SingularAttribute<Receta, Integer> idReceta;

}