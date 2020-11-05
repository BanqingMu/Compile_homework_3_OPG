import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.Stack;

public class compiler {
	static Stack<Character> stack = new Stack<Character>();
	static void stack_push(char c) {
		stack.push(c);
		System.out.println("I"+c);
	}

	public static void main(String[] args) throws IOException {
//		FileInputStream fis=new FileInputStream(args[0]);
//		BufferedReader br=new BufferedReader(new InputStreamReader(fis));
//		String string=br.readLine();
		String string=new Scanner(System.in).nextLine();
		char[] input=string.toCharArray();
		int len=input.length;
		stack.push('\0');
		for(int i=0;i<len;i++)
		{
			char a=input[i],p=stack_peek();
			examine(p,a);
			if(compare(p,a))
			{
				while(compare(stack_peek(),a))
					stack_reduction();
				stack_push(a);
			}else
			{
				stack_push(a);
			}
		}
		while(stack.peek()!='\n'||stack_peek()!='\0')
		{
			stack_reduction();
		}
	}

	private static void examine(char p, char a) {
		switch(p)
		{
		case 'i':
			if(a=='i'||a=='(') error();
			break;
		case '(':
			if(a=='\0') error();
			break;
		case ')':
			if(a=='i'||a=='(') error();
			break;
		case '\0':
			if(a==')'||a=='\0') error();
			break;
		}
		
	}

	private static boolean compare(Character p, char a) {
		if(f(p)>g(a)) {
			return true;
		}
		return false;
	}

	private static Character stack_peek() {
		char ret=stack.peek();
		if(ret=='\n') {
			stack.pop();
			char temp=ret;
			ret=stack.peek();
			stack.push(temp);
		}
		return ret;
	}

	private static void stack_reduction() {
		char p=stack_peek();
		switch(p)
		{
		case 'i':
			stack.pop();
			stack.push('\n');
			System.out.println("R");
			break;
		case ')':
			stack.pop();
			if(stack_peek()=='(')
			{
				stack.pop();
				stack.pop();
				stack.push('\n');
			}else
			{
				reduction_error();
			}
			System.out.println("R");
			break;
		case '+':
		case '*':
			if(stack.peek()=='\n')
			{
				stack.pop();
				stack.pop();
			}else
			{
				reduction_error();
			}
			if(stack.peek()=='\n')
			{
				stack.pop();
				stack.push('\n');
			}else
			{
				reduction_error();
			}
			System.out.println("R");
			break;
		default:
			reduction_error();
		}
	}

	private static void reduction_error() {
		System.out.println("RE");
		System.exit(0);
	}

	private static int g(char a) {
		switch(a)
		{
		case '+':return 1;
		case '*':return 3;
		case '(':return 5;
		case ')':return 0;
		case 'i':return 5;
		case '\0':return 0;
		default:
			error();
		}
		return 0;
	}

	private static int f(Character p) {
		switch(p)
		{
		case '+':return 2;
		case '*':return 4;
		case '(':return 0;
		case ')':return 6;
		case 'i':return 6;
		case '\0':return 0;
		default:
			error();
		}
		return 0;
	}

	private static void error() {
		System.out.println("E");
		System.exit(0);
	}

}
