Java file
1. hammingCodeGenerator.java - this file is the final code contain GUI. And user in able to chose even or odd parity



For the block diagram
The block diagram is created by using LibreOffice Draw under linux operating system



Hamming Code Generator


1. This program will convert user input to binary first. Therefore, when user input 10, the program will read it as "ten" rather than "two"

2. Although, the program will convert user input to binary first, user can only input number



Step:


1. Read user input

2. Convert user input to binary code

3. Calculate number of parity bits required

4. Create an ArrayList to store new code which include data bits and parity bits. At this stage, set all parity bit to 0.
5. Calculate what value of each parity bit should be. Even parity will be used when the radio button is checked

6. Ouput hamming code to textarea.



Things can be improved:

1. As the program use int for user input. Then if user input some number which is longer than 10 digits when convert to binary
(e.g. 1024), there is an error, for int cannot hold that number
