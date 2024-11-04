package it.unibo.inner.impl;

import java.util.Arrays;
import java.util.Iterator;

import it.unibo.inner.api.IterableWithPolicy;
import it.unibo.inner.api.Predicate;

public class IterableWithPolicyImpl<T> implements IterableWithPolicy<T>{

    private final T[] elements; 
    private Predicate<T> filter; 

    public IterableWithPolicyImpl (T[] elements) {
        this(
            elements,
            new Predicate<T>() {
                public boolean test(T value) {
                    return true;
                }
            }
        ); 
    }

    public IterableWithPolicyImpl (T[] elements, Predicate<T> filter) {
        this.elements = Arrays.copyOf(elements, elements.length); 
        this.setIterationPolicy(filter);
    }

    @Override
    public void setIterationPolicy(Predicate<T> filter) {

        this.filter = filter;     

    }

    public Iterator<T> iterator() {
        return this.new IteratorImpl(); 
    }

    private class IteratorImpl implements Iterator<T> {
        
        private int current = 0; 

        @Override
        public boolean hasNext() {

            while (this.current < IterableWithPolicyImpl.this.elements.length) {
                if(IterableWithPolicyImpl.this.filter.test(IterableWithPolicyImpl.this.elements[current])) {
                    return true; 
                } else {
                    this.current++; 
                }
            }

            return false; 
        }

        @Override
        public T next() {
            if (!this.hasNext()) {
                throw new java.util.NoSuchElementException(); 
            }
            return IterableWithPolicyImpl.this.elements[current++]; 
        }
        
    }

    

}
