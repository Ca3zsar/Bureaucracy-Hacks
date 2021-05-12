package com.example.utils;

public class Pair<T,E> {
    private T first;
    private E second;

     public Pair(T first, E second)
    {
        this.first = first;
        this.second = second;
    }
    public E getValue()
    {
        return second;
    }

    public T getKey()
    {
        return first;
    }







}
