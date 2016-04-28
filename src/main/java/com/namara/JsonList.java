package com.tdw;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public class JsonList<T> implements ParameterizedType
{
    private Class<?> wrapped;

    public JsonList(Class<T> wrapper)
    {
        this.wrapped = wrapper;
    }

    public Type[] getActualTypeArguments()
    {
        return new Type[] { wrapped };
    }

    public Type getRawType()
    {
        return List.class;
    }

    public Type getOwnerType()
    {
        return null;
    }
}
