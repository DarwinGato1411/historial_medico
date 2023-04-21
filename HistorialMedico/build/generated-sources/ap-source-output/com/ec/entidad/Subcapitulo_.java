package com.ec.entidad;

import com.ec.entidad.Capitulo;
import com.ec.entidad.Detalle;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2023-04-21T06:16:07")
@StaticMetamodel(Subcapitulo.class)
public class Subcapitulo_ { 

    public static volatile SingularAttribute<Subcapitulo, Boolean> ciuEstado;
    public static volatile CollectionAttribute<Subcapitulo, Detalle> detalleCollection;
    public static volatile SingularAttribute<Subcapitulo, Capitulo> idCapitulo;
    public static volatile SingularAttribute<Subcapitulo, Integer> idSubcapitulo;
    public static volatile SingularAttribute<Subcapitulo, String> subDetalle;

}