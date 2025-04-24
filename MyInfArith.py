import subprocess
import sys
import os

def compile_java():
    """Compile the Java project using Maven"""
    try:
        print("Compiling")
        subprocess.run(['mvn', 'clean', 'package'], check=True)
        print("compilation successful")
    except subprocess.CalledProcessError as e:
        print(f"Maven compilation failed: {e}")
        sys.exit(1)
    except FileNotFoundError:
        print("Error: Maven not found. Please install Maven and ensure it's in your PATH")
        sys.exit(1)


def run_java_code(data_type , operation , num1 , num2):
    """Run the compiled Java program"""
    try:
        jar_file_path = "target/Final_Project-1.0-SNAPSHOT.jar"
        if not os.path.exists(jar_file_path):
            print(f"Error: JAR file not found at {jar_file_path}")
            exit(1)
        
        result = subprocess.run(
            ['java', '-jar', jar_file_path, data_type, operation, num1 , num2],
            check=True,
            capture_output=True,
            text=True
        )
        # print(result.stdout)
        return result
    except subprocess.CalledProcessError as e:
        print(f"Error running Java program: {e.stderr.strip()}")
        exit(1)

def main():
    print("hello")
    if len(sys.argv) != 5 :
        print("python3 MyInfArith.py <int/float> <operation> <num1> <num2>")
        exit(1)

    data_type = sys.argv[1]
    operation = sys.argv[2]
    num1 = sys.argv[3]
    num2 = sys.argv[4]

    if data_type not in ['int' , 'float']:
        print("the first argument should be <int> or <float>")

    if operation not in ['add' , 'subtract' , 'multiply' , 'divide']:
        print("The second argument should be any of these <add> <subtract> <multiply> <divide>")
    
    if not os.path.exists('MyInfArith.class'):
        print("Java class not found. Compiling...")
        compile_java()

    result = run_java_code(data_type , operation , num1 , num2)
    print(f"the result is: {result.stdout}")

main()