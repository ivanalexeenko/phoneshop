package com.es.core.model.getter;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;

public class GetterInvoker {

    public Object invokeGetter(Object object,String fieldName) throws IntrospectionException, InvocationTargetException, IllegalAccessException {
        PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName,object.getClass());
        return propertyDescriptor.getReadMethod().invoke(object);
    }
}
