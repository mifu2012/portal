package com.infosmart.portal.util;

import org.apache.log4j.Logger;

/**
 * 
 * <p>
 * Title: Queue
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Date: 2006-08-29
 * </p>
 */

interface Interface_Queue {

	// only copy references
	public Queue clone();

	// out queue from head
	// when is empty , return null
	public Object deQueue();

	// enter queue from tail
	public void enQueue(Object o);

	// get the head elem in the queue
	// if empty return null
	public Object getHead();

	// get the tail elem in the queue
	public Object getTail();

	public int getLength();

	public boolean isEmpty();

	// print all the elem from head to tail
	// return the print string
	public String printAll();

	// reverse all the elems from head to tail
	public void reverse();

	public void clean();
}

public class Queue implements Cloneable, Interface_Queue {

	private static final Logger LOGGER = Logger
			.getLogger(Interface_Queue.class);

	class Node implements Cloneable {

		Object elem;
		Node next;

		Node(Object elem, Node next) {

			this.elem = elem;
			this.next = next;
		}

		// only copy references
		public Node clone() {

			try {
				Node n = (Node) super.clone();
				n.elem = this.elem;
				if (this.next != null) {
					n.next = (Node) this.next.clone();
				} else {
					n.next = null;
				}
				return n;
			} catch (CloneNotSupportedException ex) {
				LOGGER.error(ex);
			}
			return null;
		}
	}

	// have head node
	// when the queue is empty,the head and tail point to the same head node
	// enQueue from tail
	// deQueue from head
	private Node head = new Node(null, null);
	private Node tail = head;
	private int length = 0;

	// only copy references
	public Queue clone() {
		try {
			Queue n = (Queue) super.clone();
			n.head = this.head.clone();
			Node p = n.head;
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

	// enter queue from tail
	public void enQueue(Object o) {

		tail.next = new Node(o, null);
		tail = tail.next;
		length++;
	}

	// out queue from head
	// when is empty , return null
	public Object deQueue() {

		if (isEmpty()) {
			return null;
		}
		Node p = head.next;
		head.next = p.next;
		if (tail == p) {
			tail = head;
		}
		length--;
		return p.elem;
	}

	// reverse all the elems from head to tail
	public void reverse() {

		if (length <= 1) {
			return;
		}
		tail = head.next;
		Node q = head.next, p = q.next, r = p.next;
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

	// get the head elem in the queue
	// if empty return null
	public Object getHead() {

		if (isEmpty()) {
			return null;
		}
		return head.next.elem;
	}

	// get the tail elem in the queue
	public Object getTail() {

		if (isEmpty()) {
			return null;
		}
		return tail.elem;
	}

	// print all the elem from head to tail
	// return the print string
	public String printAll() {

		String out = "";
		if (isEmpty()) {
			out = "Queue:\tEmpty!";
		} else {
			out = "[Head]";
			Node p = head.next;
			while (p != null) {
				out += " -> " + p.elem;
				p = p.next;
			}
		}
		return out;
	}

	public int getLength() {

		return length;
	}

	public boolean isEmpty() {

		return length == 0;
	}

	public void clean() {

		head.next = null;
		tail = head;
		length = 0;
	}

	public static void main(String[] args) {

		Queue q = new Queue(), s;
		q.enQueue(1);
		q.enQueue(2);
		q.reverse();
		q.enQueue(3);
		q.enQueue(4);
		q.enQueue(5);
		q.printAll();
		q.reverse();
		q.printAll();
		q = new Queue();
		for (int i = 0; i < 20; i++) {
			q.enQueue(i);
		}
		s = q.clone();
		q.printAll();
		s.printAll();
		for (int i = 0; i < 20; i++) {
			q.deQueue();
		}
		q.printAll();
		s.printAll();
		s = q.clone();
		q.printAll();
		s.printAll();
		s.enQueue(3);
		s.enQueue(4);
		s.enQueue(5);
		q.printAll();
		s.printAll();
		q.enQueue(5);
		q.printAll();
		s.printAll();
		s = new Queue();
		s.clean();
		s.printAll();
		s.enQueue(3);
		s.enQueue(2);
		s.printAll();
		s.clean();
		s.printAll();
	}
}
