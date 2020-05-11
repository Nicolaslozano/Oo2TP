package com.unla.grupo_2_oo2_2020.helpers;

public class ViewRouteHelper {

    /**** Views ****/
	//HOME
    public final static String INDEX = "dashboard/index";

    //CLIENTE
    public final static String CLIENTE_INDEX = "cliente/index";
    public final static String CLIENTE_NEW = "cliente/new";
    public final static String CLIENTE_UPDATE = "cliente/update";

    //LOCAL
    public final static String LOCAL_INDEX = "local/index";
    public final static String LOCAL_NEW = "local/new";
    public final static String LOCAL_UPDATE = "local/update";
    public final static String LOCAL_CALCULAR_DISTANCIA = "local/distance";
    public final static String LOCAL_CALCULAR_DISTANCIA_RESULT = "local/distance_result";

    //EMPLEADO
    public final static String EMPLEADO_INDEX = "empleado/index";
    public final static String EMPLEADO_NEW = "empleado/new";
    public final static String EMPLEADO_UPDATE = "empleado/update";
    
    
	/**** Redirects ****/
    public final static String ROUTE = "/index";
    public final static String LOCAL_ROOT = "/local";
    public final static String CLIENTE_ROOT = "/cliente";
    public final static String EMPLEADO_ROOT = "/empleado";

}