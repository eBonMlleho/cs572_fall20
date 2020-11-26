/**
 * @author Zhanghao Wen
 */

package edu.iastate.cs572.proj2;

import java.util.LinkedList;

public class ConjunctiveNormalForm {
	LinkedList<Clause> list = new LinkedList<Clause>();

	public ConjunctiveNormalForm() {

	}

	public void add(Clause clause) {
		list.add(clause);
	}

	public Clause get(int index) {
		return list.get(index);
	}

	public void remove(int index) {
		list.remove(index);
	}

	public int size() {
		return list.size();
	}

	public String toString() {
		StringBuilder str = new StringBuilder("");
		str.append("[");
		for (int i = 0; i < list.size(); i++) {
			str.append("(");
			str.append(list.get(i).toString());
			str.append(")");
		}
		str.append("]");
		return str.toString();
	}

}
