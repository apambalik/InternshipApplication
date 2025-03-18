/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adt;

/**
 *
 * @author Goh Ee Lin
 */
public class HashSet<T> implements Set<T> {

    private static final int DEFAULT_CAPACITY = 16;
    private static final float LOAD_FACTOR = 0.75f;

    private Node<T>[] buckets;
    private int size;

    // Node for chaining collisions
    private static class Node<T> {

        T data;
        Node<T> next;

        Node(T data, Node<T> next) {
            this.data = data;
            this.next = next;
        }
    }

    @SuppressWarnings("unchecked")
    public HashSet() {
        buckets = new Node[DEFAULT_CAPACITY];
        size = 0;
    }

    @Override
    public boolean add(T element) {
        if (contains(element)) {
            return false;
        }

        int bucketIndex = getBucketIndex(element);
        Node<T> newNode = new Node<>(element, buckets[bucketIndex]);
        buckets[bucketIndex] = newNode;
        size++;

        if (size > buckets.length * LOAD_FACTOR) {
            resize();
        }

        return true;
    }

    @Override
    public boolean remove(T element) {
        int bucketIndex = getBucketIndex(element);
        Node<T> current = buckets[bucketIndex];
        Node<T> prev = null;

        while (current != null) {
            if (current.data.equals(element)) {
                if (prev == null) {
                    buckets[bucketIndex] = current.next;
                } else {
                    prev.next = current.next;
                }
                size--;
                return true;
            }
            prev = current;
            current = current.next;
        }
        return false;
    }

    @Override
    public boolean contains(T element) {
        int bucketIndex = getBucketIndex(element);
        Node<T> current = buckets[bucketIndex];

        while (current != null) {
            if (current.data.equals(element)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<T> elements) {
        Iterator<T> it = elements.iterator();
        while (it.hasNext()) {
            T element = it.next();
            if (!contains(element)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private int getBucketIndex(T element) {
        int hashCode = element == null ? 0 : element.hashCode();
        return Math.abs(hashCode) % buckets.length;
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        Node<T>[] oldBuckets = buckets;
        buckets = new Node[oldBuckets.length * 2];
        size = 0;

        for (Node<T> node : oldBuckets) {
            while (node != null) {
                add(node.data);
                node = node.next;
            }
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int currentBucket = 0;
            private Node<T> currentNode = null;
            private boolean started = false;

            @Override
            public boolean hasNext() {
                if (!started) {
                    // Find first non-empty bucket
                    moveToNextValidBucket();
                    started = true;
                }

                return currentNode != null;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new RuntimeException("No more elements");
                }

                T data = currentNode.data;

                // Move to next node or bucket
                currentNode = currentNode.next;
                if (currentNode == null) {
                    currentBucket++;
                    moveToNextValidBucket();
                }

                return data;
            }

            private void moveToNextValidBucket() {
                // Find next non-empty bucket
                while (currentBucket < buckets.length && buckets[currentBucket] == null) {
                    currentBucket++;
                }

                if (currentBucket < buckets.length) {
                    currentNode = buckets[currentBucket];
                } else {
                    currentNode = null;
                }
            }
        };
    }
}
