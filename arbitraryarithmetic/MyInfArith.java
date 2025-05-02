package arbitraryarithmetic;

public class MyInfArith {
    public static void main(String[] args) {
        if (args.length != 4) {
            System.err.println("java MyInfArith <int/float> <add/subtract/multiply/divide> <num1> <num2>");
            System.exit(1);
        }   

        String data_type = args[0];
        String operation = args[1];
        String num1 = args[2];
        String num2 = args[3];

        try {
            String result;
            if (data_type.equals("int")) {
                result = Integer_handling(operation, num1, num2);
            } else if (data_type.equals("float")) {
                result = Float_handling(operation, num1, num2);
            } else {
                throw new IllegalArgumentException("First argument must be 'int' or 'float'");
            }
            System.out.println(result);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(1);
        }
    }

    public static String Integer_handling(String operation, String op1, String op2) {
        AInteger a = new AInteger(op1);
        AInteger b = new AInteger(op2);

        switch (operation) {
            case "add": return a.add(b).stringFormat();
            case "subtract": return a.subtract(b).stringFormat();
            case "multiply": return a.multiply(b).stringFormat();
            case "divide": 
                int flag = 0 ;
                for(int i=0 ; i<b.value.length() ; i++){
                    if(b.value.charAt(i) != '0')
                    {
                        flag = 1;
                        break;
                    }
                }
                if(flag == 0){
                    return "Error: Division by Zero";
                }
                else{
                    return a.division(b).stringFormat();
                }
            default: throw new IllegalArgumentException("Invalid operation for integers");
        }
    }

    public static String Float_handling(String operation, String op1, String op2) {
        AFloat a = new AFloat(op1);
        AFloat b = new AFloat(op2);

        switch (operation) {
            case "add": return a.addition(b).stringFormat();
            case "subtract": return a.subtract(b).stringFormat();
            case "multiply": return a.multiply(b).stringFormat();
            case "divide": 
                int flag = 0 ;
                for(int i=0 ; i<b.value.length() ; i++){
                    if(b.value.charAt(i) != '0')
                    {
                        flag = 1;
                        break;
                    }
                }
                if(flag == 0){
                    return "Error: Division by Zero";
                }
                else{
                    return a.division(b).stringFormat();
                }
                
            default: throw new IllegalArgumentException("Invalid operation for floats");
        }
    }
}
