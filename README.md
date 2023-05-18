# MIPS-Assembler
# 

# Assembler

**Note: This is the largest project I have worked on so far.**

This assembler is a powerful tool designed to facilitate the conversion of instructions from a previous MIPS-like processor implementation into machine code. It offers several features and provides error handling capabilities. However, it also has certain limitations.

## Features:

1. **Instruction Conversion:** The assembler reads instructions from a file and converts them into machine code, which can be saved to a file or displayed in the terminal. By default, the machine code is presented in hexadecimal format, but binary format is also supported.
2. **Flexibility:** The assembler is highly flexible in dealing with instructions. It can handle variations in spacing, indentation, and case sensitivity, ensuring a seamless conversion process.
3. **Error Handling:** The assembler captures various types of errors, such as syntax errors and incompatible operands. It provides detailed information about the error, including the specific line in which it occurs, facilitating easy debugging.
4. **Comment Support:** The assembler recognizes and supports comments, denoted by the "%" symbol. This allows for clear and descriptive code documentation.
5. **Label Usage:** The assembler enables the use of labels within the code, allowing for improved code organization and readability.
6. **Extendable Instruction Set:** Adding new instructions to the instruction set is straightforward and hassle-free, providing flexibility for customization and expansion.

## Limitations:

1. **Comparison Instructions:** The comparison instructions are not implemented in hardware. Instead, they are handled by the assembler. Consequently, there is a limitation in the available registers, and the use of comparison instructions may overwrite the contents of the R7 register.
2. **Multiline or Block Comments:** The assembler does not support multiline or block comments. Comments must be specified on a single line using the "%" symbol.
3. **No User Interface (UI) or GUI:** The assembler operates through a command-line interface and does not offer a graphical user interface (GUI).
4. **No Support for Variables:** The assembler does not provide support for variables or variable declarations within the code.

Feel free to explore, use, and contribute to this assembler. Your feedback and suggestions for improvement are highly appreciated!

## Usage

The assembler can be used in different ways depending on the number of arguments provided. Below are the three cases of usage:

1. Case 1: Prompted Input
    
    If no arguments are provided, the assembler will prompt the user to enter the paths of the code file, the location to save the machine code, and the preferred format (binary or hex).
    
    ```
    java -jar Assembler.jar
    ```
    
2. Case 2: Single Argument
    
    If a single argument is provided, it should be the path to the code file. The assembler will automatically generate the machine code and display it in the terminal.
    
    ```
    java -jar Assembler.jar /path/to/code.txt
    ```
    
3. Case 3: Two or Three Arguments
    
    If two arguments are provided, the first argument should be the path to the code file, and the second argument should be the path to save the machine code. The assembler will generate the machine code and save it at the specified location.
    
    If three arguments are provided, the third argument should be the format of the machine code, either "binary" or "hex". If not specified, the default format is hex.
    
    ```
    java -jar Assembler.jar /path/to/code.txt /path/to/save/machinecode [binary|hex]
    ```
    

**Note:** Replace `/path/to/code.txt` with the actual path to your code file, and `/path/to/save/machinecode` with the desired location to save the machine code.

## Example Usages

1. Prompted Input:
    
    ```
    java -jar Assembler.jar
    ```
    
2. Single Argument:
    
    ```
    java -jar Assembler.jar /path/to/code.txt
    ```
    
3. Two Arguments:
    
    ```
    java -jar Assembler.jar /path/to/code.txt /path/to/save/machinecode
    ```
    
4. Three Arguments (with Format):
    
    ```
    java -jar Assembler.jar /path/to/code.txt /path/to/save/machinecode binary
    ```
    
    or
    
    ```
    java -jar Assembler.jar /path/to/code.txt /path/to/save/machinecode hex
    ```
    

**Note:** Make sure to have Java installed and include the correct path to the `Assembler.jar` file in the command.

**Note:** The entery point of this project is the `main` method in class `MachineCode.java` within `codegenerating` package.
