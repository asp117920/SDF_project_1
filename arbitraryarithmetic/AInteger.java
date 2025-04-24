package arbitraryarithmetic;

public class AInteger {
    public String value;
    public boolean if_negative = false;

    // public AInteger() {
    //     this.value = "0";
    // }

    public AInteger(int value){
        String value_in_string = String.valueOf(value);
        if (value_in_string.startsWith("-")) {
            this.if_negative = true;
            value_in_string = value_in_string.substring(1);
        }
        this.value = value_in_string.replaceFirst("^0+(?!$)", "");
        if (this.value.isEmpty()) this.value = "0";
    }

    public AInteger(String value){
        if (value.startsWith("-")) {
            this.if_negative = true;
            value = value.substring(1);
        }
        this.value = value.replaceFirst("^0+(?!$)", "");
        if (this.value.isEmpty()) this.value = "0";
    }

    public AInteger (AInteger other_integer) {
        this.value = other_integer.value;
        this.if_negative = other_integer.if_negative;
    }

    public AInteger parse(String number) {
        return new AInteger(number);
    }

    public AInteger add(AInteger other) {
        // return new AInteger(String.valueOf(Double.parseDouble(number_1)+Double.parseDouble(number_2)));
        String number_1 = this.value;
        String number_2 = other.value;
        int length_1 = number_1.length() - 1;
        int length_2 = number_2.length() - 1;

        if (this.if_negative && !other.if_negative){// returns other - this
            return other.subtract(this.abs());
        }
        else if(other.if_negative && !this.if_negative){// returns this - other
            return this.subtract(other.abs());
        }
        int digit_sum = 0;
        int carry = 0;
        StringBuilder sum_value = new StringBuilder();

        while(length_1>=0 || length_2>=0 || carry>0){
            int digit_from_num_1 = length_1>=0 ? number_1.charAt(length_1) - '0' : 0 ;
            int digit_from_num_2 = length_2>=0 ? number_2.charAt(length_2) - '0' : 0 ;

            length_1--;
            length_2--;

            digit_sum = digit_from_num_1 + digit_from_num_2 + carry;
            carry = digit_sum/10;
            digit_sum = digit_sum - (10*carry);
            sum_value.append(digit_sum);
        }

        if(other.if_negative && this.if_negative){// both of them are negative
            return new AInteger("-"+sum_value.reverse().toString());
        }
        return new AInteger(sum_value.reverse().toString());
    }


    public AInteger abs() {
        AInteger result = new AInteger(this);
        result.if_negative = false;
        return result;
    }

    public AInteger subtract(AInteger other) {
        if (this.if_negative && !other.if_negative) {
            return other.subtract(this.abs());
        } else if (!this.if_negative && other.if_negative) {
            return this.add(other.abs());
        } else if (this.if_negative && other.if_negative) {
            return other.abs().subtract(this.abs());
        }
        
        String num1 = this.value;
        String num2 = other.value;
    
        // Handle negative results (if num1 < num2)
        if (compareAbsolute(num1, num2) < 0) {
            AInteger result = new AInteger(num2).subtract(new AInteger(num1));
            result.value = "-" + result.value;
            return result;
        }
    
        int length_1 = num1.length() - 1;
        int length_2 = num2.length() - 1;
        int borrow = 0;
        StringBuilder result = new StringBuilder();
    
        while (length_1 >= 0 || length_2 >= 0) {
            int digit1 = (length_1 >= 0) ? num1.charAt(length_1--) - '0' : 0;
            int digit2 = (length_2 >= 0) ? num2.charAt(length_2--) - '0' : 0;
    
            // Apply borrow from previous step
            digit1 -= borrow;
            borrow = 0;
    
            // If digit1 < digit2, we need to borrow
            if (digit1 < digit2) {
                digit1 += 10;
                borrow = 1;
            }
    
            int diff = digit1 - digit2;
            result.append(diff);
        }
    
        // Remove leading zeros
        String finalResult = result.reverse().toString().replaceFirst("^0+(?!$)", "");
        return new AInteger(finalResult.isEmpty() ? "0" : finalResult);
    }
    
    // Helper method to compare absolute values of two number strings
    private int compareAbsolute(String num1, String num2) {
        // Compare lengths first
        if (num1.length() > num2.length()) return 1;
        if (num1.length() < num2.length()) return -1;
    
        // If lengths are equal, compare digit by digit
        for (int i = 0; i < num1.length(); i++) {
            int digit1 = num1.charAt(i) - '0';
            int digit2 = num2.charAt(i) - '0';
            if (digit1 > digit2) return 1;
            if (digit1 < digit2) return -1;
        }
        return 0;
    }

    public AInteger pad_zeroes(int n) {
        StringBuilder zeros = new StringBuilder();
        for (int i = 0; i < n; i++) zeros.append("0");
        return new AInteger(this.value + zeros);
    }

    private String trim_leading_zeros() {
        return this.value.replaceFirst("^0+(?!$)", "");
    }
    
    public AInteger multiply( AInteger other){
        String num_1 = this.value;
        String num_2 = other.value;
        int length_1 = num_1.length();
        int length_2 = num_2.length();

        if (num_1.equals("0") || num_2.equals("0")) {
            return new AInteger("0");
        }

        boolean isNegative = false;
        if (this.if_negative) {
            isNegative = !isNegative;
            // num_1 = num_1.substring(1);
        }
        if (other.if_negative) {
            isNegative = !isNegative;
            // num_2 = num_2.substring(1);
        }

        if (length_1 == 1 && length_2 == 1) {
            int prod = (num_1.charAt(0) - '0') * (num_2.charAt(0) - '0');
            if (isNegative) {
                prod = (-1)*prod;
            }
            return new AInteger(String.valueOf(prod));
        }

        if (length_1 < length_2) {
            num_1 = "0".repeat(length_2 - length_1) + num_1;
            length_1 = num_1.length();
        } else if (length_2 < length_1) {
            num_2 = "0".repeat(length_1 - length_2) + num_2;
            length_2 = num_2.length();
        }

        int half_length_1 = (int) length_1/2;
        int half_length_2 = (int) length_2/2;

        int length_1_1 = half_length_1;
        int length_1_2 = length_1-half_length_1;
        int length_2_1 = half_length_2;
        int length_2_2 = length_2-half_length_2;

        String num_1_1 = num_1.substring(0 , length_1_1); // it is the strating part of length_1_1 and will have length_1_2 number of zeroes padded behind it
        AInteger A_num_1_1 = new AInteger(num_1_1);
        String num_1_2 = num_1.substring(length_1_1); // it is the ending part of length_1_2
        AInteger A_num_1_2 = new AInteger(num_1_2);
        String num_2_1 = num_2.substring(0 , length_2_1); // it is the strating part of length_2_1 and will have length_2_2 number of zeroes padded behind it
        AInteger A_num_2_1 = new AInteger(num_2_1);
        String num_2_2 = num_2.substring(length_2_1); // it is the ending part of length_2_2
        AInteger A_num_2_2 = new AInteger(num_2_2);

        // num_1 = (num_1_1)*(10)**(length_1_2) + (num_1_2)
        // num_2 = (num_2_1)*(10)**(length_2_2) + (num_2_2)

        // num_1 * num_2 = ((num_1_1)*(num_2_1))*(10)**(length_1_2 + length_2_2) + 
        // (num_1_2)*(num_2_2) + 
        // (num_1_1)*(num_2_2)*10**(length_1_2) + 
        // (num_2_1)*(num_1_2)*10**length_2_2

        AInteger quarter_1 = (A_num_1_1.multiply(A_num_2_1));
        quarter_1 = quarter_1.pad_zeroes(length_1_2 + length_2_2);

        AInteger quarter_2 = A_num_1_2.multiply(A_num_2_2);

        AInteger quarter_3 = A_num_1_1.multiply(A_num_2_2);
        quarter_3 = quarter_3.pad_zeroes(length_1_2);

        AInteger quarter_4 = A_num_2_1.multiply(A_num_1_2);
        quarter_4 = quarter_4.pad_zeroes(length_2_2);

        AInteger half_1 = quarter_1.add(quarter_2);
        AInteger half_2 = quarter_3.add(quarter_4);

        AInteger final_output = half_1.add(half_2);
        final_output.trim_leading_zeros();

        if (isNegative) {
            final_output.if_negative = true;
        }
        return final_output;
    }   

    public AInteger division(AInteger other){
        String num_1 = this.value;
        String num_2 = other.value;
    
        int length_1 = num_1.length();
        int length_2 = num_2.length();
        StringBuilder result = new StringBuilder();
        boolean result_is_negative = this.if_negative ^ other.if_negative;
    
        if (num_1.length() >= num_2.length()){
            AInteger divident = new AInteger(num_1);
            AInteger divisor = new AInteger(num_2);
            Recursive_division(divident, divisor, result , 0);
        }
    
        if (num_1.length() < num_2.length()){
            // implement decimal division later
        }
    
        if (result.length() == 0) result.append("0");
    
        if (result_is_negative && !result.toString().equals("0")) {// getting final output as a negative number
            result.insert(0, "-");
        }
    
        return new AInteger(result.toString());
    }
    
    public void Recursive_division(AInteger divident , AInteger divisor , StringBuilder result , int previous_remainder_length){
        String Divident_value= divident.value;
        // System.out.println(Divident_value);
        // Divident_value = Divident_value.replaceFirst("^0+(?!$)", ""); // remove leading zeros
    
        if (Divident_value.isEmpty()) return;
    
        // Take a prefix that is >= divisor
        int i = previous_remainder_length+1;
        while (i <= Divident_value.length() && compareAbsolute(Divident_value.substring(0, i), divisor.value) < 0) {
            i++;
            result.append("0");
        }
    
        if (i > Divident_value.length()) {
            return; // nothing more to divide
        }
    
        String to_be_divided = Divident_value.substring(0, i);
        // System.out.println(to_be_divided);
        AInteger A_to_be_divided = new AInteger(to_be_divided);
    
        AInteger multiplier = new AInteger("0");
        while (compareAbsolute(divisor.multiply(multiplier).value, A_to_be_divided.value) <= 0) {
            multiplier = multiplier.add(multiplier.parse("1"));
        }
        multiplier = multiplier.subtract(multiplier.parse("1"));
    
        AInteger remainder = A_to_be_divided.subtract(divisor.multiply(multiplier));
        // System.out.println("remainder: "+remainder.value);

        result.append(multiplier.value);
    
        if(remainder.value.equals("0")){
            result.append("0");
        }

        String remaining = remainder.value + Divident_value.substring(i);
        // remaining = remaining.replaceFirst("^0+(?!$)", "");
    
        AInteger new_divident = new AInteger(remaining);
        Recursive_division(new_divident, divisor, result , remainder.value.length());
    }    


}
