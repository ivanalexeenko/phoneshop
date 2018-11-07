package com.es.core.model.phone;

import com.es.core.exception.GetterInvokerException;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

public interface PhoneDao {
    Optional<Phone> get(Long key);
    void save(Phone phone) throws IllegalArgumentException, GetterInvokerException;
    List findAll(int offset, int limit);
}
