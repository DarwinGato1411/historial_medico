package com.ec.entidad;

import com.ec.entidad.Subcapitulo;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2023-03-31T10:56:45")
@StaticMetamodel(Detalle.class)
public class Detalle_ { 

    public static volatile SingularAttribute<Detalle, Integer> idDetalle;
    public static volatile SingularAttribute<Detalle, Boolean> canEstado;
    public static volatile SingularAttribute<Detalle, Subcapitulo> idSubcapitulo;
    public static volatile SingularAttribute<Detalle, String> detDetalle;

}