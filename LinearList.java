/*  Micah Joseph Grande
    cssc0900
*/

package data_structures;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.ConcurrentModificationException;

public class LinearList<E extends Comparable<E>> implements LinearListADT<E> {
	
//  Node Object Constructor creates the a field for data and a link to the next
//	and previous nodes
	class Node<T>{
		T data;
		Node<T> next, prev;
		Node(T obj){
			data = obj;
			next = prev = null;
		}
	}
	
	private int currentSize;
	private Node<E> head, tail;
	private long modCounter;
    
//  Adds the Object obj to the beginning of list and returns true if the list is not full.
//  returns false and aborts the insertion if the list is full.
    public boolean addFirst(E obj) {
    	Node<E> newNode = new Node<E>(obj);
    	if(head == null)	//empty list
    		head = tail = newNode;
    	else {
    		newNode.next = head;
    		head.prev = newNode;
    		head = newNode;
    	}
    	currentSize++;
    	modCounter++;
    	return true;
    }
    
//  Adds the Object obj to the end of list and returns true if the list is not full.
//  returns false and aborts the insertion if the list is full..  
    public boolean addLast(E obj) {
    	Node<E> newNode = new Node<E>(obj);
    	if (tail == null)	//empty list
    		head = tail = newNode;
    	else {
    		newNode.prev = tail;
    		tail.next = newNode;
    		tail = newNode;
    	}
    	currentSize++;
    	modCounter++;
    	return true;
    }
    
//  Removes and returns the parameter object obj in first position in list if the list is not empty,  
//  null if the list is empty. 
    public E removeFirst() {
    	if(head == null)	//empty list
    		return null;
    	E tmp = head.data;
    	head = head.next;
    	if(head == null)	//list became empty
    		tail = null;
    	else
    		head.prev = null;
    	currentSize--;
    	modCounter++;
    	return tmp;
    }
    
//  Removes and returns the parameter object obj in last position in list if the list is not empty, 
//  null if the list is empty. 
    public E removeLast() {
    	if(tail == null)
    		return null;
    	E tmp = tail.data;
    	tail = tail.prev;
    	if (tail == null)
    		head = null;
    	else
    		tail.next = null;
    	currentSize--;
    	modCounter++;
    	return tmp;
    }
    
//  Removes and returns the parameter object obj from the list if the list contains it, null otherwise.
//  The ordering of the list is preserved.  The list may contain duplicate elements.  This method
//  removes and returns the first matching element found when traversing the list from first position.
//  Note that you may have to shift elements to fill in the slot where the deleted element was located.
    public E remove(E obj) {
    	Node<E> current = head;
    	while(current != null && obj.compareTo(current.data) != 0)
    			current = current.next;
    	if(current == null)		//list is empty
    		return null;
    	if(current == head)		//obj is first node
    		return removeFirst();
    	if(current == tail)		//obj is last node
    		return removeLast();
    	current.prev.next = current.next;
    	current.next.prev = current.prev;
    	currentSize--;
    	modCounter++;
    	return current.data;
    	
    }
    
//  Returns the first element in the list, null if the list is empty.
//  The list is not modified.
    public E peekFirst() {
    	if(currentSize == 0)
    		return null;
    	return head.data;
    }
    
//  Returns the last element in the list, null if the list is empty.
//  The list is not modified.
    public E peekLast() {
    	if(currentSize == 0)
    		return null;
    	return tail.data;
    }

//  Returns true if the parameter object obj is in the list, false otherwise.
//  The list is not modified.
    public boolean contains(E obj) {
    	return (find(obj) != null);
    }
    
//  Returns the element matching obj if it is in the list, null otherwise.
//  In the case of duplicates, this method returns the element closest to front.
//  The list is not modified.
    public E find(E obj) {
		for (E tmp:this)
			if(obj.compareTo(tmp)== 0)
				return tmp;
			return null;
	}


//  The list is returned to an empty state.
    public void clear() {
    	head = tail = null;
    	currentSize = 0;
    	modCounter = 0;
    }

//  Returns true if the list is empty, otherwise false
    public boolean isEmpty() {
    	return (currentSize == 0);
    }
    
//  Returns true if the list is full, otherwise false
    public boolean isFull() {
    	return false;
    }

//  Returns the number of Objects currently in the list.
    public int size() {
    	return currentSize;
    }
    
//  Returns an Iterator of the values in the list, presented in
//  the same order as the underlying order of the list. (front first, rear last)
    public Iterator<E> iterator() {
    	return new IteratorHelper();
    }
    
    class IteratorHelper implements Iterator<E>{
		
    	private Node<E> iterPtr;
    	private long modCheck;
    	
		//Constructor for IteratorHelper
		public IteratorHelper() {
			modCheck = modCounter;
			iterPtr = head;
		}
		
		//Checks if the list has a next element
		public boolean hasNext() {
			if(modCheck != modCounter)
				throw new ConcurrentModificationException();
			return (iterPtr != null);
			
		}
		
		//Returns the next element in the array
		public E next() {
			if (!hasNext())
				throw new NoSuchElementException();
			E tmp = iterPtr.data;
			iterPtr = iterPtr.next;
			return tmp;
		}
		
		//Unused method
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
}
