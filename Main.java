import arbitraryarithmetic.AFloat;

public class Main {
    public static void main(String[] args) {
        // AInteger num1 = new AInteger("8564793736546546565465");
        // AInteger num2 = new AInteger("87554");

        // AInteger sum = num1.add(num2);
        // System.out.println("Sum is : "+sum.value);
        // AInteger subtraction = num1.subtract(num2);
        // System.out.println("Subtraction is : "+subtraction.value);
        // AInteger multiplication = num1.multiply(num2);
        // System.out.println("multiplication is : "+multiplication.value);
        // AInteger division = num1.division(num2);
        // System.out.println("division value : "+division.value);

        AFloat num_1 = new AFloat("8792726365283060579833950521677211.0");
        AFloat num_2 = new AFloat("493835253617089647454998358");
        AFloat sum_f = num_1.addition(num_2);
        System.out.println("sum_f: "+sum_f.value+" "+sum_f.no_digits_after_decimal);
        AFloat subtraction_f = num_1.subtract(num_2);
        System.out.println("subtraction_f: "+subtraction_f.value+" "+subtraction_f.no_digits_after_decimal);
        AFloat multiply = num_1.multiply(num_2);
        System.out.println("multiplication: "+multiply.value+" "+multiply.no_digits_after_decimal);
        AFloat division_f = num_1.division(num_2);
        System.out.println("Division : "+division_f.value+" "+division_f.no_digits_after_decimal);
        // System.out.println(num_1.value+" "+num_2.value);
    }
}
