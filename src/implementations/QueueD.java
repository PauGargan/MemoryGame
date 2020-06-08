package implementations;

import api.ColaTDA;

public class QueueD implements ColaTDA {
	
	private class Node{
		int value;
		Node next;
	}
	Node start;
	Node end;

	public void initializeQueue() {
		start =null;
		end = null;
	}


	public void add(int x) {
		Node newNode = new Node();
		newNode.value = x;
		if(start != null) {
			newNode.next = null;
			end.next = newNode;
			end = newNode;
		}else {
			newNode.next = null;
			start = newNode;
			end = newNode;
		}
	}


	public void remove() {
		start = start.next;
		if(this.isEmpty()) {
			end = null;
		}
	}


	public boolean isEmpty() {
		return start==null;
	}


	public int first() {
		return start.value;
	}

}
