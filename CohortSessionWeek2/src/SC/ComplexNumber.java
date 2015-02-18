package SC;

public class ComplexNumber {

	class Complex{
		double real;
		double img;
		String type;


		public Complex(double num1, double num2) {
			real = num1;
			img = num2;
			type = "rectangle";

		}

		public Complex(float r, float angleInRad){
			real = r * Math.cos(angleInRad);
			img = r * Math.sin(angleInRad);
			type = "polar";
		}

		public Complex add(Complex c1){
			if (!this.type.equals(c1.type)){
				System.out.println("Not the same type");
				return null;
			}else{
				double newReal = real + c1.real;
				double newImg = img + c1.img;
				return new Complex(newReal, newImg);
			}
		}

		public Complex sub(Complex c1){
			if (!this.type.equals(c1.type)){
				System.out.println("Not the same type");
				return null;
			}else{
				double newReal = real - c1.real;
				double newImg = img - c1.img;
				return new Complex(newReal, newImg);
			}
		}

		public Complex mult(Complex c1){
			if (!this.type.equals(c1.type)){
				System.out.println("Not the same type");
				return null;
			}else{
				double newReal = (real*c1.real - img*c1.img);
				double newImg = (real*c1.img + img*c1.real);
				return new Complex(newReal, newImg);
			}
		}

		public Complex divide(Complex c1){
			if (!this.type.equals(c1.type)){
				System.out.println("Not the same type");
				return null;
			}else{
				double fraction = 1/(Math.pow(c1.real, 2) + Math.pow(c1.img, 2));
				Complex c2 = this.mult(new Complex(c1.real, -c1.img));
				return new Complex(fraction * c2.real, fraction * c2.img);
			}
		}

		public String toString(){
			double r = Math.sqrt(Math.pow(real, 2) + Math.pow(img, 2));
			double angle = Math.atan(img/real);
			if (type.equals("rectangle")){
				return String.format("Rectangle form: %.2f +(%.2f)i\n", real,img);
			}else
				return String.format("Polar form: %.2fcos%.2f + (%.2fsin%.2f)i\n", r, angle, r, angle);
		}
	}

	public static void main(String[] args) {
		ComplexNumber c = new ComplexNumber();
		Complex c1 = c.new Complex(1.0, 2.0);
		Complex c2 = c.new Complex(2.0, 3.0);
		Complex c3 = c.new Complex(1, 0.707);
		System.out.println(c1);
		System.out.println(c2);
		System.out.println(c3);
		System.out.println(c1.add(c2));
		System.out.println(c1.sub(c2));
		System.out.println(c1.mult(c2));
		System.out.println(c1.divide(c2));
	}

}
