/**
 * @author Zhanghao Wen
 */
package edu.iastate.cs572.proj2;

import java.util.ArrayList;

public class Clause {
	ArrayList<Literal> ll = new ArrayList<Literal>();

	public Clause() {

	}

	public void add(Literal literal) { // clause can add literal
		ll.add(literal);

	}

	public void add(Clause clause) { // clause can also add clause (X V Y) V (Z) = X V Y V Z
		for (int i = 0; i < clause.size(); i++) {
			ll.add(clause.get(i));
		}

	}

	public Literal get(int i) {

		return ll.get(i);
	}

	public void remove(int i) {
		ll.remove(i);
	}

	public int size() {
		return ll.size();
	}

	public String toString() {
		StringBuilder str = new StringBuilder("");
		for (int i = 0; i < ll.size(); i++) {

			str.append(ll.get(i).toString());
			str.append(" ");

		}

		return str.toString();
	}

	// see two clause contains same element, if so, return that literal
	public Literal containsSomeLiteral(Clause clause) {
		for (int i = 0; i < ll.size(); i++) {
			for (int j = 0; j < clause.size(); j++) {
				if (ll.get(i).equals(clause.get(j))) {
					return ll.get(i);
				}
			}
		}
		return null;
	}

	// compare this clause to another clause. return true if all literal and literal
	// negation are both same
	public boolean equals(Clause otherClause) {
		if (ll.size() != otherClause.size()) {
			return false;
		}

		boolean result = true;

		outerloop: for (int i = 0; i < ll.size(); i++) { // iteralte all literal a

			for (int j = 0; j < otherClause.size(); j++) { // find b in B that can equal a
				if (ll.get(i).equals(otherClause.get(j)) && ll.get(i).isNegative == otherClause.get(j).isNegative) {
					result = true; // exist one b that equal a
					continue outerloop; // find next a
				} else {
					result = false; // else still in inner loop, go for next b in B
				}
			}

			if (result == false) { // if after all B no b equals a, then return false
				return false;
			}

		}
		return result;
	}

}
