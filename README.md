# SDF_project_1

# Arbitrary Precision Arithmetic Library


A Java-based library for performing arithmetic operations with arbitrary precision, overcoming the limitations of standard data types. Designed for educational purposes, this project demonstrates high-precision calculations using string representations and efficient algorithms.

## Features

- **Unlimited Precision**: Handle integers and floating-point numbers of any size.
- **Core Operations**: Addition, subtraction, multiplication, and division.
- **Exact Decimal Handling**: No rounding errors in floating-point operations.
- **Efficient Algorithms**: Implements Karatsuba multiplication (O(n^1.585)).
- **CLI Interface**: User-friendly command-line interface for executing operations.
- **Cross-Language Support**: Includes a Python wrapper for seamless integration.

## Project Structure


## Installation

1. **Clone the repository**:
   ```bash
   git clone https://github.com/your-repo/arbitrary-arithmetic.git
   cd arbitrary-arithmetic

mvn clean package

java -jar target/Final_Project-1.0-SNAPSHOT.jar <int|float> <operation> <num1> <num2>

java -jar target/Final_Project-1.0-SNAPSHOT.jar int multiply 123456789 987654321

java -jar target/Final_Project-1.0-SNAPSHOT.jar float divide 22 7

python src/main/python/MyInfArith.py <int|float> <operation> <num1> <num2>
```
