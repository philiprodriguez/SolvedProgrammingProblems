import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class e {

	public static void main(String[] args) {
		new e();
	}
	
	char str[];
	int len,submax,numLetters;
	Node root;
	Stack<Character> answer;
	
	e(){
		
		Scanner in = new Scanner(System.in);
		int times = in.nextInt();
		
		for(int q=1;q<=times;q++){
			
			len = in.nextInt();
			submax = in.nextInt();
			numLetters = in.nextInt();
			str = in.next().toCharArray();
			root = new Node('\\',null);
			answer = new Stack<>();
			
			/*if(submax > len){
				System.out.print(new String(str));
				System.out.println("a");
				continue;
			}*/
			
			for(int i=0;i<str.length;i++){
				Node temp = root;
				//System.out.println("substr: ");
				for(int j=i;j<Math.min(i+submax,str.length);j++){
					char c = str[j];
					if(temp.child(c)==null){
						temp.addChild(c, new Node(c,temp));
					}
					temp = temp.child(c);
					//System.out.print(str[j]);
				}
				//System.out.println();
			}
			
			/*for(int i=0;i<str.length-submax+1;i++){
				Node temp = root;
				System.out.println("substr: ");
				for(int j=i;j<i+submax;j++){
					char c = str[j];
					if(temp.child(c)==null){
						temp.addChild(c, new Node(c,temp));
					}
					temp = temp.child(c);
					System.out.print(str[j]);
				}
				System.out.println();
			}*/
			
			bfs();
			//System.out.println("ans:");
			while(!answer.isEmpty()){
				System.out.print(answer.pop());
			}
			System.out.println();
			
		}
		
	}
	
	void bfs(){
		
		Queue<Node> q = new ArrayDeque<>();
		q.add(root);
		Node temp;
		
		while(!q.isEmpty()){
			temp = q.poll();
			Node a[] = temp.neighbors;
			for(int i=0;i<numLetters;i++){
				if(a[i]!=null){
					q.add(a[i]);
				}else{ //GAAAAAAAAAAAAAAAAASP!!!!!!!!
					answer.push((char) (i+'a'));
					while(temp!=root){
						answer.push(temp.letter);
						temp = temp.parent;
					}
					return;
				}
			}
		}
		
	}
	
	static class Node{
		char letter;
		Node neighbors[],parent;
		Node(char letter, Node parent){
			this.letter = letter;
			this.parent = parent;
			neighbors = new Node[26];
		}
		void addChild(char c, Node n){
			neighbors[(int)(c-'a')] = n;
		}
		Node child(char c){
			return neighbors[(int)(c-'a')];
		}
	}
	
}

/*

2
9 3 2
bbbaababb
9 3 2
aaabbabaa

1
29 5 3
aabcdefghijkacdfjkjkcalklkjwa

1
9 3 2
bbbaababb

3
4 5 2
abaa
4 4 2
abaa
5 4 2
abaaa


 */














