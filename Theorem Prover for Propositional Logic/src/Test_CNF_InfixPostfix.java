/**
 * @author Zhanghao Wen
 */
package edu.iastate.cs572.proj2;

class Test_CNF_InfixPostfix {
	public static void main(String[] args) {
		String infix = "~(P & ~Q)|R = S & ~T";
		infix = infix.replaceAll(" ", "");
		String postfix = Infix2Postfix.infixToPostfix(infix);
		System.out.println("postfix expressoin is " + postfix);

		// postfix = "PQ~&~R|ST~&=";
		ExpressionTree et = new ExpressionTree();
		char[] charArray = postfix.toCharArray();
		Node root = et.constructTree(charArray);
		System.out.println("infix expression is");
		et.inorder(root);

		System.out.println("\nroot is " + root.value);
		et.Postorder(root);
		System.out.println("\nroot CNF is " + root.cnf);
	}
}