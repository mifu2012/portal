package com.infosmart.portal.util;

import org.apache.log4j.Logger;

/**
 * <p>
 * Title: Stack
 * </p>
 * 
 * <p>
 * Description: see the interface
 * </p>
 * 
 * <p>
 * Date: 2006-07-30
 * </p>
 */

interface Interface_Stack {

	/**
	 * return a new stack which have all the new created stackElems<br>
	 * but the object the StackElem.content point to is not cloned<br>
	 * they just copy their references, not object.clone<br>
	 * beacuse not many object support clone,as Integer,Double,String....
	 * 
	 * @return Object
	 */
	public Stack clone();

	public Object getTop();

	public boolean isEmpty();

	public boolean isFull();

	public int length();

	// if empty return null
	// when empty , tail point to the head node
	public Object pop();

	// print all the elem from head to tail
	// return the print string
	public String printAll();

	public void push(Object o);

	// reverse all the elems from head to tail
	// the same codes from Queue's reverse()
	// the same construction of them
	public void reverse();

	public void clean();
}

class StackElem implements Cloneable {

	public Object content;
	public StackElem next;

	private static final Logger LOGGER = Logger.getLogger(StackElem.class);

	/**
	 * the object the StackElem.content point to is not cloned<br>
	 * they just copy their references, not object.clone<br>
	 * beacuse not many object support clone,as Integer,Double,String....
	 * 
	 * @return Object
	 */
	public Object clone() {

		try {
			StackElem n = (StackElem) super.clone();
			n.content = this.content;
			if (this.next != null) {
				n.next = (StackElem) this.next.clone();
			} else {
				n.next = null;
			}
			return n;
		} catch (CloneNotSupportedException ex) {
			LOGGER.error(ex);
		}
		return null;
	}

	public StackElem(Object o) {

		content = o;
	}

	public StackElem() {

	}
}

public class Stack implements Cloneable, Interface_Stack {

	// the stack capacity,not counted the head
	private int capacity = Integer.MAX_VALUE;

	// number of element in stack, head is not counted in length
	private int length;

	// have head node
	public StackElem head = new StackElem();
	public StackElem tail = head;
	private static final Logger LOGGER = Logger.getLogger(Stack.class);

	/**
	 * return a new stack which have all the new created stackElems<br>
	 * but the object the StackElem.content point to is not cloned<br>
	 * they just copy their references, not object.clone<br>
	 * beacuse not many object support clone,as Integer,Double,String....
	 * 
	 * @return Object
	 */
	public Stack clone() {

		try {
			Stack n = (Stack) super.clone();
			n.head = (StackElem) this.head.clone();
			StackElem p = n.head;
			while (p.next != null) {
				p = p.next;
			}
			n.tail = p;
			return n;
		} catch (CloneNotSupportedException ex) {
			LOGGER.error(ex);
		}
		return null;
	}

	public Stack() {

	}

	// if have given the capacity,the length of stack will be limited
	public Stack(int size) {

		capacity = size;
	}

	public void push(Object o) {

		if (length >= capacity) {
			return;
		}
		StackElem n = new StackElem(o);
		tail.next = n;
		tail = n;
		length++;
	}

	// if empty return null
	// when empty , tail point to the head node
	public Object pop() {

		if (length == 0) {
			return null;
		}
		StackElem p = head, q = null;
		while (p.next != tail) {
			p = p.next;
		}
		q = p;
		p = tail;
		tail = q;
		tail.next = null;
		length--;
		return p.content;
	}

	// reverse all the elems from head to tail
	// the same codes from Queue's reverse()
	// the same construction of them
	public void reverse() {

		if (length <= 1) {
			return;
		}
		tail = head.next;
		StackElem q = head.next, p = q.next, r = p.next;
		q.next = null;
		// when only two nodes
		if (r == null) {
			p.next = q;
			head.next = p;
			return;
		}
		// more than two nodes
		while (r.next != null) {
			p.next = q;
			q = p;
			p = r;
			r = r.next;
		}
		p.next = q;
		r.next = p;
		head.next = r;
	}

	public boolean isFull() {

		return length == capacity;
	}

	public boolean isEmpty() {

		return length == 0;
	}

	public Object getTop() {

		if (isEmpty()) {
			return null;
		}
		return tail.content;
	}

	public int length() {

		return length;
	}

	public String printAll() {

		String out = "";
		if (isEmpty()) {
			out = "Stack:\tEmpty!";
		} else {
			StackElem p = head;
			out = "[Head]";
			while (p.next != null) {
				p = p.next;
				out += " -> " + p.content;
			}
		}
		return out;
	}

	public void clean() {

		head.next = null;
		tail = head;
		length = 0;
	}

	public static void main(String[] args) {

		Stack s = new Stack(50);
		for (int i = 0; i < 50; i++) {
			s.push(i);
		}
		s.reverse();
		s.printAll();
		Stack n = (Stack) s.clone();
		n.printAll();
		for (int i = 0; i < 50; i++) {
			s.pop();
		}
		s.printAll();
		s.push(33);
		s.push("asba");
		s.push("asba");
		s.reverse();
		s.printAll();
		s = new Stack();
		s.clean();
		s.printAll();
		s.push(3);
		s.push(2);
		s.printAll();
		s.clean();
		s.printAll();
	}
}
