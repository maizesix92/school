
public class AbstractTestImplement extends TestAbstract{

	public static void main(String[] args) {
		AbstractTestImplement ab = new AbstractTestImplement();
		// abstract classes can be created
		TestAbstract test = new TestAbstract() {
			
			@Override
			void hello() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			void bye(String message) {
				// TODO Auto-generated method stub
				
			}
		};
		ab.laugh();
		ab.hello();
		ab.bye("lol");
	}

	@Override
	void hello() {
		System.out.println("Hello");
		
	}

	@Override
	void bye(String message) {
		System.out.println("bye bye " + message);
		
	}

}
