package com.ec.entidad;

import com.ec.entidad.Subcapitulo;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2023-05-16T10:39:05")
@StaticMetamodel(Capitulo.class)
public class Capitulo_ { 

    public static volatile SingularAttribute<Capitulo, Integer> idCapitulo;
    public static volatile SingularAttribute<Capitulo, Integer> capCodigo;
    public static volatile CollectionAttribute<Capitulo, Subcapitulo> subcapituloCollection;
    public static volatile SingularAttribute<Capitulo, String> capDetalle;

}