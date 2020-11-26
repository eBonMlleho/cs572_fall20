/**
 * @author Zhanghao Wen
 */

package edu.iastate.cs572.proj2;

//Java program to construct an expression tree

import java.util.Stack;

class ExpressionTree {

	// A utility function to check if 'c'
	// is an operator

	boolean isOperator(char c) {

		if (c == '&' || c == '|' || c == '=' || c == '<' || c == '~') {
			return true;
		}
		return false;
	}

	// Utility function to do inorder traversal
	void inorder(Node t) {
		if (t != null) {
			inorder(t.left);
			System.out.print(t.value + " ");
			inorder(t.right);
		}
	}

	// Utility function to do Postorder traversal and convert sentence into CNF
	void Postorder(Node node) {
		if (node == null)
			return;

		// first recur on left subtree
		Postorder(node.left);

		// then recur on right subtree
		Postorder(node.right);

		// now deal with the node

		// node.testsetCNF();
		node.setCNF();

		// System.out.print(node.cnf + ", ");

	}

	// Returns root of constructed tree for given
	// postfix expression
	Node constructTree(char postfix[]) {
		Stack<Node> st = new Stack<Node>();
		Node t, t1, t2;

		// Traverse through every character of
		// input expression
		for (int i = 0; i < postfix.length; i++) {

			// If operand, simply push into stack
			if (!isOperator(postfix[i])) {
				t = new Node(postfix[i]);
				st.push(t);
			} else { // operator

				t = new Node(postfix[i]);

				if (t.value != '~') {
					// Pop two top nodes
					// Store top
					// System.out.println(t.value);
					t1 = st.pop(); // Remove top
					t2 = st.pop();

					// make them children
					t.right = t1;
					t.left = t2;

					// System.out.println(t1 + "" + t2);
					// Add this subexpression to stack
					st.push(t);
				} else { // ~ operator does not require 2 operands
					// Pop one top nodes
					t1 = st.pop(); // Remove top
					t.right = t1; // ******** ~ only has right child
					st.push(t);
				}

			}
		}

		t = st.peek();
		st.pop();

		return t;
	}

	public static void main(String args[]) {

		ExpressionTree et = new ExpressionTree();
//		String postfix = "PQ~&~R|ST~&=";
		String postfix = "YX<";
		postfix = postfix.replaceAll(" ", "");
		char[] charArray = postfix.toCharArray();
		Node root = et.constructTree(charArray);
		System.out.println("infix expression is");
		et.inorder(root);
		System.out.println("\nroot is " + root.value);

		et.Postorder(root);

	}
}