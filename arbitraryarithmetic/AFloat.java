package arbitraryarithmetic;

public class AFloat {
    public String value;
    public int no_digits_after_decimal;
    public boolean if_negative;

    public AFloat (){
        this.value = "0";
        this.no_digits_after_decimal=0;  
        this.if_negative = false; 
    }

    public AFloat(String value){

        if (value.startsWith("-")) {
            this.if_negative = true;
            value = value.substring(1);
        }

        this.value = value;
        this.value = value.replaceFirst("0+$", ""); //remove zeroes after decimal
        this.value = value.replaceFirst("\\.$", ""); //removes decimal if there is no number left after removing zeroes

        if (value.contains(".")) {
            this.no_digits_after_decimal = value.length() - value.indexOf(".") - 1;
            value = value.replace(".", "");
        } else {
            this.no_digits_after_decimal = 0;
        }
        this.value = value.replaceFirst("^0+(?!$)", ""); //remove zeroes in front 
        
    }

    public AFloat(AFloat other_float){
        this.value = other_float.value;
        this.no_digits_after_decimal = other_float.no_digits_after_decimal;
        this.if_negative = other_float.if_negative;
    }

    public AFloat parse(String value){
        return new AFloat(value);
    }

    public AFloat abs() {
        AFloat result = new AFloat(this);
        result.if_negative = false;
        return result;
    }

    public String stringFormat() {
        StringBuilder final_output = new StringBuilder(this.value);
    
        // padding zeroes in front if the number has less number of digits than reuqired
        while (final_output.length() <= this.no_digits_after_decimal) {
            final_output.insert(0, "0");
        }
    
      

        int inserting_index = final_output.length() - this.no_digits_after_decimal;
        final_output.insert(inserting_index, ".");
        String result = final_output.toString();
        if (no_digits_after_decimal > 30){
            result = result.substring(0,inserting_index+1+30);
            return result;
        }
        result = result.replaceFirst("0+$", ""); // Remove trailing zeroes
        result = result.replaceFirst("\\.$", ""); // Remove decimal point if no digits after it
    
        if (this.if_negative && !result.equals("0")) {
            result = "-" + result;
        }
        return result;
    }    

    public int compareAbsolute(AFloat num1 , AFloat num2) {
        String integer_part_1 = num1.value.substring(0,num1.value.length()-num1.no_digits_after_decimal);
        String integer_part_2 = num2.value.substring(0,num2.value.length()-num2.no_digits_after_decimal);

        if(integer_part_1.length()>integer_part_2.length()){
            return 1;
        }
        else if (integer_part_2.length()>integer_part_1.length()){
            return -1;
        }
        for (int i = 0; i < integer_part_1.length(); i++) {
            int digit1 = integer_part_1.charAt(i) - '0';
            int digit2 = integer_part_2.charAt(i) - '0';
            if (digit1 > digit2) return 1;
            if (digit1 < digit2) return -1;
        }
        String float_1 = num1.value.substring(num1.value.length()-num1.no_digits_after_decimal);
        String float_2 = num2.value.substring(num2.value.length()-num2.no_digits_after_decimal);
        
        int decimal_sequence_length = float_1.length()>float_2.length()?float_2.length():float_1.length();
        for(int i =0;i<decimal_sequence_length;i++){
            int digit1 = float_1.charAt(i)-'0';
            int digit2 = float_2.charAt(i)-'0';
            if (digit1 > digit2) return 1;
            if (digit1 < digit2) return -1;
        }
       
        if(float_1.length()>float_2.length()){
            return 1;
        }
        else if(float_2.length()>float_1.length()){
            return -1;
        }
        return 0;

    }

    public AFloat pad_zeroes(int n) {
        StringBuilder zeros = new StringBuilder();
        for (int i = 0; i < n; i++) zeros.append("0");
        return new AFloat(this.value + zeros);
    }

    private String trim_leading_zeros() {
        return this.value.replaceFirst("^0+(?!$)", "");
    }
    
    public int pre_process(AFloat other){
        int no_decimal_1 = this.no_digits_after_decimal;
        int no_decimal_2 = other.no_digits_after_decimal;
        String number_1 = this.value;
        String number_2 = other.value;
        int final_decimal_count = 0;

        if(no_decimal_1>no_decimal_2){
            // pad zeroes behind number_2
            String zero_padding = "";
            for(int i =0 ; i<(no_decimal_1)-(no_decimal_2);i++){
                zero_padding = zero_padding+"0";
            }
            final_decimal_count = no_decimal_1;
            other.no_digits_after_decimal = this.no_digits_after_decimal;
            other.value = other.value+zero_padding;
        } else if(no_decimal_1<no_decimal_2){
            // pad zeroes behine number_1
            String zero_padding = "";
            for(int i =0 ; i<(no_decimal_2)-(no_decimal_1);i++){
                zero_padding = zero_padding+"0";
            }
            final_decimal_count = no_decimal_2;
            this.no_digits_after_decimal = other.no_digits_after_decimal;
            this.value = this.value+zero_padding;
        }

        return final_decimal_count;
    }

    public AFloat add_aux(AFloat other) {

        String number_1 = this.value;
        String number_2 = other.value;

        int length_1 = number_1.length() - 1;
        int length_2 = number_2.length() - 1;

        if (this.if_negative && !other.if_negative){
            return other.subtract(this.abs());
        }
        else if(other.if_negative && !this.if_negative){
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

        if(other.if_negative && this.if_negative){
            return new AFloat("-"+sum_value.reverse().toString());
        }
        return new AFloat(sum_value.reverse().toString());
    }
    
    public AFloat addition(AFloat other){

        AFloat copy1 = new AFloat(this);
        AFloat copy2 = new AFloat(other);
        int final_decimal_count = copy1.pre_process(copy2);
        // System.out.println(copy1.value+" "+copy2.value);
        // System.out.println(copy1.no_digits_after_decimal+" "+copy2.no_digits_after_decimal);
        AFloat result = copy1.add_aux(copy2);
        result.no_digits_after_decimal = final_decimal_count;
        // System.out.println(final_decimal_count);
        return result;

    }

    public AFloat subtract_aux(AFloat other) {
        if (this.if_negative && !other.if_negative) {
            AFloat result = this.abs().addition(other);
            result.if_negative = true;
            return result;
        } else if (!this.if_negative && other.if_negative) {
            AFloat result = this.addition(other.abs());
            result.if_negative = false;
            return result;
        } else if (this.if_negative && other.if_negative) {
            if(this.compareAbsolute(this.abs(), other.abs())>0){
                AFloat result = this.abs().subtract(other.abs());
                result.if_negative = true;
            }
            else if (this.compareAbsolute(this.abs(), other.abs())<0){
                AFloat result = other.abs().subtract(this.abs());
                result.if_negative = false;
                return result;
            }
        }
        
        String num1 = this.value;
        String num2 = other.value;
    
        if (compareAbsolute(this,other) < 0) {
            AFloat result = new AFloat(num2).subtract(new AFloat(num1));
            result.if_negative = true;
            return result;
        }
    
        int length_1 = num1.length() - 1;
        int length_2 = num2.length() - 1;
        int borrow = 0;
        StringBuilder result = new StringBuilder();
    
        while (length_1 >= 0 || length_2 >= 0) {
            int digit1 = (length_1 >= 0) ? num1.charAt(length_1--) - '0' : 0;
            int digit2 = (length_2 >= 0) ? num2.charAt(length_2--) - '0' : 0;
    
            digit1 -= borrow;
            borrow = 0;
    
            if (digit1 < digit2) {
                digit1 += 10;
                borrow = 1;
            }
    
            int diff = digit1 - digit2;
            result.append(diff);
        }
    
        String finalResult = result.reverse().toString().replaceFirst("^0+(?!$)", "");
        return new AFloat(finalResult.isEmpty() ? "0" : finalResult);
    }
    
    public AFloat subtract(AFloat other){
    
        AFloat copy1 = new AFloat(this);
        AFloat copy2 = new AFloat(other);

        int final_decimal_count = copy1.pre_process(copy2);
        // System.out.println(copy1.value+" "+copy2.value);
        // System.out.println(copy1.no_digits_after_decimal+" "+copy2.no_digits_after_decimal);
        AFloat result = copy1.subtract_aux(copy2);
        result.no_digits_after_decimal = final_decimal_count;
        // System.out.println(final_decimal_count);
        return result;
    }
    
    public AFloat multiply_aux(AFloat other){
        String num_1 = this.value;
        String num_2 = other.value;
        int length_1 = num_1.length();
        int length_2 = num_2.length();

        if (num_1.equals("0") || num_2.equals("0")) {
            return new AFloat("0");
        }

        boolean isNegative = false;
        if (this.if_negative) isNegative = !isNegative;
        if (other.if_negative) isNegative = !isNegative;

        if (length_1 == 1 && length_2 == 1) {
            int prod = (num_1.charAt(0) - '0') * (num_2.charAt(0) - '0');
            // if (isNegative) prod = (-1)*prod;
            return new AFloat(String.valueOf(prod));
        }

        if (length_1 < length_2) {
            num_1 = "0".repeat(length_2 - length_1) + num_1;
            length_1 = num_1.length();
        } else if (length_2 < length_1) {
            num_2 = "0".repeat(length_1 - length_2) + num_2;
            length_2 = num_2.length();
        }

        int half_length_1 = length_1 / 2;
        int half_length_2 = length_2 / 2;

        String num_1_1 = num_1.substring(0 , half_length_1);
        AFloat A_num_1_1 = new AFloat(num_1_1);
        String num_1_2 = num_1.substring(half_length_1);
        AFloat A_num_1_2 = new AFloat(num_1_2);
        String num_2_1 = num_2.substring(0 , half_length_2);
        AFloat A_num_2_1 = new AFloat(num_2_1);
        String num_2_2 = num_2.substring(half_length_2);
        AFloat A_num_2_2 = new AFloat(num_2_2);

        AFloat quarter_1 = A_num_1_1.multiply_aux(A_num_2_1);
        quarter_1 = quarter_1.pad_zeroes(length_1 - half_length_1 + length_2 - half_length_2);

        AFloat quarter_2 = A_num_1_2.multiply_aux(A_num_2_2);

        AFloat quarter_3 = A_num_1_1.multiply_aux(A_num_2_2);
        quarter_3 = quarter_3.pad_zeroes(length_1 - half_length_1);

        AFloat quarter_4 = A_num_2_1.multiply_aux(A_num_1_2);
        quarter_4 = quarter_4.pad_zeroes(length_2 - half_length_2);

        AFloat half_1 = quarter_1.addition(quarter_2);
        AFloat half_2 = quarter_3.addition(quarter_4);

        AFloat final_output = half_1.addition(half_2);
        final_output.trim_leading_zeros();

        if (isNegative) {
            final_output.if_negative = true;
        }

        return final_output;
    }   

    public AFloat multiply(AFloat other){
        AFloat copy1 = new AFloat(this);
        AFloat copy2 = new AFloat(other);

        int final_decimal_count = copy1.no_digits_after_decimal+copy2.no_digits_after_decimal;
        copy1.no_digits_after_decimal=0;
        copy2.no_digits_after_decimal=0;
        AFloat result = copy1.multiply_aux(copy2);
        result.no_digits_after_decimal = final_decimal_count;
        result.if_negative = this.if_negative ^ other.if_negative;
        // System.out.println(result.if_negative);
        // System.out.println("hello");
        // System.out.println(result.value+" "+result.no_digits_after_decimal);
        return result;
    }

    public AFloat division_aux(AFloat other){
        String num_1 = this.value;
        String num_2 = other.value;
    
        int length_1 = num_1.length();
        int length_2 = num_2.length();
        StringBuilder result = new StringBuilder();
        boolean result_is_negative = this.if_negative ^ other.if_negative;
    
        if (num_1.length() >= num_2.length()){
            AFloat divident = new AFloat(num_1);
            AFloat divisor = new AFloat(num_2);
            Recursive_division(divident, divisor, result , 0);
        }
    
        if (num_1.length() < num_2.length()){
            // implement decimal division later
        }
    
        if (result.length() == 0) result.append("0");
    
        if (result_is_negative && !result.toString().equals("0")) {
            result.insert(0, "-");
        }
    
        return new AFloat(result.toString());
    }
    
    public void Recursive_division(AFloat divident , AFloat divisor , StringBuilder result , int previous_remainder_length){
        String Divident_value = divident.value;

        if (Divident_value.isEmpty()) return;
    
        int i = previous_remainder_length + 1;
        while (i <= Divident_value.length() && compareAbsolute(this.parse(Divident_value.substring(0, i)), divisor) < 0) {
            i++;
            result.append("0");
        }
    
        if (i > Divident_value.length()) {
            // result.deleteCharAt(result.length()-1);
            return;
        }
    
        String to_be_divided = Divident_value.substring(0, i);
        AFloat A_to_be_divided = new AFloat(to_be_divided);
    
        AFloat multiplier = new AFloat("0");
        while (compareAbsolute(divisor.multiply(multiplier), A_to_be_divided) <= 0) {
            multiplier = multiplier.addition(multiplier.parse("1"));
        }
        multiplier = multiplier.subtract(multiplier.parse("1"));
    
        AFloat remainder = A_to_be_divided.subtract(divisor.multiply(multiplier));
        result.append(multiplier.value);
    
        if(remainder.value.equals("0")){
            result.append("0");
        }

        String remaining = remainder.value + Divident_value.substring(i);
        AFloat new_divident = new AFloat(remaining);
        Recursive_division(new_divident, divisor, result , remainder.value.length());
    }    

    public AFloat division(AFloat other){
        AFloat copy1 = new AFloat(this);
        AFloat copy2 = new AFloat(other);

        int decimal_value = copy1.pre_process(copy2);

        // System.out.println(copy1.value + " "+ copy2.value);

        AFloat copy1_ = copy1.pad_zeroes(1000);

        AFloat result = copy1_.division_aux(copy2);
        result.no_digits_after_decimal = 1000; // default value of 1000 decimals for non-terminating values
        // handling the case of terminating-decimals separtely

        // System.out.println(result.value+" "+result.no_digits_after_decimal);
        return result;
    }
}
