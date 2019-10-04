package textgen;

import java.util.AbstractList;


/** A class that implements a doubly linked list
 * 
 * @author UC San Diego Intermediate Programming MOOC team
 *
 * @param <E> The type of the elements stored in the list
 */
public class MyLinkedList<E> extends AbstractList<E> {
	LLNode<E> head;
	LLNode<E> tail;
	int size;

	/** Create a new empty LinkedList */
	public MyLinkedList() {
		this.size = 0;
		this.head = new LLNode<E>();
		this.tail = new LLNode<E>();
		head.next = tail;
		tail.prev = head;
	}

	/**
	 * Appends an element to the end of the list
	 * @param element The element to add
	 */
	public boolean add(E element ) 
	{
		LLNode<E> newElement = new LLNode<E>(element);
		if(element == null) {
			throw new NullPointerException("Element cannot store null referetion!");
		}
		else {
			newElement.next = tail;
			newElement.prev = tail.prev;
			tail.prev.next = newElement;
			tail.prev = newElement;
			size ++;
			return true;
		}
	}

	public E get(int index) 
	{
		if(index >= size || index < 0 || size == 0) {
			throw new IndexOutOfBoundsException("Index out of bounds!");
		}
		else {
			LLNode<E> getNode = head.next;
				for(int i = 0; i < index; i++) {
					getNode = getNode.next;
				}
				return getNode.data;
			
		}
	}

	/**
	 * Add an element to the list at the specified index
	 * @param The index where the element should be added
	 * @param element The element to add
	 */
	public void add(int index, E element ) 
	{
		LLNode<E> temp = new LLNode<E>(element);
		
		if(index == 0 && size ==0) {
			this.add(element);
		}
		
		if(index < 0  || index >= size) {
			throw new IndexOutOfBoundsException("Index out of bounds!");
		}


		else {
			LLNode<E> current = head.next;
				for(int i = 0; i < index; i++) {
					current = current.next;	
				}
				LLNode<E> previous = current.prev;
				previous.next = temp;
				current.prev = temp;
				temp.prev = previous;
				temp.next = current;
				size++;
		}
		
	}


	public int size() 
	{
		return size;
	}

	/** Remove a node at the specified index and return its data element.
	 * @param index The index of the element to remove
	 * @return The data element removed
	 * @throws IndexOutOfBoundsException If index is outside the bounds of the list
	 * 
	 */
	public E remove(int index) 
	{
		if(index < 0  || index >= size) {
			throw new IndexOutOfBoundsException("Index out of bounds!");
		}
		else {
			LLNode<E> current = head.next;
				for(int i = 0; i < index; i++) {
					current = current.next;
				}
				LLNode<E> prev = current.prev;
				LLNode<E> next = current.next;
				prev.next = current.next;
				next.prev = current.prev;
				size --;
				return current.data;
			
		}
	}

	/**
	 * Set an index position in the list to a new element
	 * @param index The index of the element to change
	 * @param element The new element
	 * @return The element that was replaced
	 * @throws IndexOutOfBoundsException if the index is out of bounds.
	 */
	public E set(int index, E element) 
	{
		if(index < 0  || index >= size) {
			throw new IndexOutOfBoundsException("Index out of bounds!");
		}
		else {
			LLNode<E> current = head.next;
				for(int i = 0; i < index; i++) {
					current = current.next;
				}
				current.data = element;
				return current.data;
		}
	}   
}

class LLNode<E> 
{
	LLNode<E> prev;
	LLNode<E> next;
	E data;

	public LLNode(){
		this.data = null;
		this.prev = null;
		this.next = null;
	}

	public LLNode(E e) 
	{
		this.data = e;
		this.prev = null;
		this.next = null;
	}

}
