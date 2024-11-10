package it.unibo.inner.impl;

import java.util.*;

import it.unibo.inner.api.IterableWithPolicy;
import it.unibo.inner.api.Predicate;

public class IterableWithPolicyImpl<T> implements IterableWithPolicy<T>{

    private final List<T> elements; 
    private Predicate<T> filter; 

    /**
     * The default policy is to iterate through all elements
     * @param elements the elements to iterate
     */
    public IterableWithPolicyImpl (final T[] elements) {
        this(
            elements,
            new Predicate<T>() {
                public boolean test(T value) {
                    return true;
                }
            }
        ); 
    }

    /**
     * If an alement satisfies the policy then iterates it
     * @param elements the elements to iterate 
     * @param filter the filter to apply
     */
    public IterableWithPolicyImpl (T[] elements, Predicate<T> filter) {
        this.elements = List.of(elements); 
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

            while (this.current < IterableWithPolicyImpl.this.elements.size()) {
                if(IterableWithPolicyImpl.this.filter.test(IterableWithPolicyImpl.this.elements.get(current))) {
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
            return IterableWithPolicyImpl.this.elements.get(current); 
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
        
    }

    

}
