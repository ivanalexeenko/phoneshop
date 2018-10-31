package com.es.core.model.phone;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

public interface PhoneDao {
    Optional<Phone> get(Long key);
    void save(Phone phone) throws InvocationTargetException, IllegalAccessException, IntrospectionException,IllegalArgumentException;
    List findAll(int offset, int limit) throws IllegalAccessException, IntrospectionException, InvocationTargetException;
}
