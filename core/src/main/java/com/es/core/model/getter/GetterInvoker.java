package com.es.core.model.getter;

import com.es.core.exception.GetterInvokerException;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;

public class GetterInvoker {

    public Object invokeGetter(Object object,String fieldName) throws GetterInvokerException {
        try {
            PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName,object.getClass());
            return propertyDescriptor.getReadMethod().invoke(object);
        }
        catch (IntrospectionException | InvocationTargetException | IllegalAccessException e) {
            throw new GetterInvokerException(e);
        }

    }
}
