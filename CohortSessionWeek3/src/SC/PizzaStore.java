package SC;

public class PizzaStore {

	MakePizza myPizzaFactory = new MakePizza();


	public Pizza orderPizza (String type) {
		Pizza pizza = null;

		// make pizza
		pizza = myPizzaFactory.makePizza(type);

		pizza.prepare();
		pizza.bake();
		pizza.cut();
		pizza.box();

		return pizza;
	}
	
	
	class MakePizza{
		
		public Pizza makePizza(String type) {
			if (type.equals("cheese")) {
				return new CheesePizza();
			}
			else if (type.equals("greek")) {
				return new GreekPizza();
			}
			else if (type.equals("pepperoni")) {
				return new PepperoniPizza();
			}else return null;
		}		
	}


	class Pizza {

		public void prepare() {
		}

		public void box() {
		}

		public void cut() {
		}

		public void bake() {
		}
	}

	class CheesePizza extends Pizza {
		public CheesePizza() {
			System.out.println("Made a cheese pizza!");
		}
	}
	class GreekPizza extends Pizza {
		public GreekPizza() {
			System.out.println("Made a greek pizza!");
		}
	}
	class PepperoniPizza extends Pizza {
		public PepperoniPizza() {
			System.out.println("Made a pepperoni pizza!");
		}
	}


	public static void main(String[] args) {
		PizzaStore pz = new PizzaStore();
		pz.orderPizza("greek");
		pz.orderPizza("cheese");
		pz.orderPizza("pepperoni");

	}
}

