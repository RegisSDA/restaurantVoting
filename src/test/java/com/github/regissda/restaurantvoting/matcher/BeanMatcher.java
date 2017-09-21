package com.github.regissda.restaurantvoting.matcher;

import org.junit.Assert;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by MSI on 21.09.2017.
 */
public class BeanMatcher<T> {

    private Class<T> entity;
    private Equality<T> equality;

    public interface Equality<T>{
        boolean areEqual(T expected, T actual);
    }

    private class Wrapper {
        private T entity;

        private Wrapper(T entity) {
            this.entity = entity;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Wrapper that = (Wrapper) o;
            return entity != null ? equality.areEqual(entity, that.entity) : that.entity == null;
        }

        @Override
        public String toString() {
            return String.valueOf(entity);
        }
    }

    private BeanMatcher(Class<T> entity,Equality<T> equality){
        this.entity = entity;
        this.equality = equality;
    }
    public static <T>  BeanMatcher of(Class<T> entity,Equality<T> equality){
        return new BeanMatcher<T>(entity,equality);
    }

    public static <T> BeanMatcher of(Class<T> entity){
        return new BeanMatcher<>(entity,((expected, actual) ->(expected==actual||expected.toString().equals(actual))));
    }

    private Wrapper wrap(T entity) {
        return new Wrapper(entity);
    }

    private List<Wrapper> wrap(List<T> list) {
        return list.stream().map(this::wrap).collect(Collectors.toList());
    }

    public void assertEquals(T expected, T actual){
        Assert.assertEquals(wrap(expected),wrap(actual));
    }

    public void assertEquals(List<T> expected, List<T> actual){
        Assert.assertEquals(wrap(expected),wrap(actual));
    }

}
