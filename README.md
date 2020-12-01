## **My Cell Automation**

##### IF THE JAR DOESN'T WORK, PLEASE RUN IT NORMALLY FROM MAIN. 


##### [Comments]
I've add comments on all the code to give you a basic idea on how each function works. So this
MD file is just for giving a bare bones summary of how each code works and some extras.

The program uses args. You must run the outer most main. That main calls all the other mains from the other classes. 
You must follow these instructions to run the files.

######[First]
Just quickly run the main. Then end it. 

######[Second]
run -> edit configurations -> program arguments

######[Third]
Path List (Put in program arguments): 
resources/elementaryCA/rule30.txt
resources/elementaryCA/rule126.txt
resources/gameOfLife/blinker.txt
resources/gameOfLife/dies1.txt
resources/gameOfLife/dies2.txt
resources/gameOfLife/glider1.txt
resources/gameOfLife/repeats1.txt
resources/gameOfLife/stable1.txt
resources/gameOfLife/stable2.txt
resources/langtonsLoop/init_config.txt resources/langtonsLoop/rule_table.txt 

######[Fourth]
Press ok.It should pop an error message like main already exist. That means the code is ready to run. You must switch
paths each time you're testing a new class. 

##### [ElementaryCA]
This program takes one cell in the middle and check its neighbors from left to right. It allows the user to input their 
own rule and starting position. 

##### [gameOfLife]
This program is a more complex version of elementaryCA. It uses list of list, like 2D arrays, to check a 3 x 3 square
through the 2D list. GameOfLife has its own set of rules. The player can input their own starting position or read in
the file inputs. 

##### [langtonsLoop]
This program is similar to gameOfLife. Same loops and checks except this time it's a plus. The plus is essentially a string.
Middle is the first index. It's neighbors (clockwise), are the four other indexes. The last index we get from the rule list. 
I essentially add 5 cells into a list, then compare it to the rule list to get the last index (the outcome).

ex:
    1
6   3   2
    0 
List: 31206.   
##### [GLITCHES]
