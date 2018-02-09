for this programm I used java for implement it.
And important notes about it is：
I develop this program in Windows10
with java version 1.8.0. Although I don't use the java 1.8 new features
One thing I need to make it clear is in windows I have to compile my java program right in the folder of the java file 
so I write the run.sh like below:

cd src
javac DonationAnalytics.java
java  DonationAnalytics  ../input/itcont.txt ../input/percentile.txt ../output/repeat_donors.txt

I first change the dir to src where it store my java file its name is DonationAnalytics.java
then I use javac to compile it and it genenrate a class file whose name is DonationAnalytics.class
then right at src dir I use java command line to run the program ,there are three argument seperately the input file of itcount.txt 
input file of percentile.txt and output file repeat_donors.txt

one thing I am not so sure is the suffix of each file, in windows ,it matters, in linux,it seems doesn't care about it .So if you run my run.sh and find there are something
wrong with it. feel free to change the dir, file suffix.

also I my src file I attach my class file which can be used directly.

And because the number of arguments for my java program is 3. So the String[] args's index range from 0-2
maybe in some operating system ,the program name DonationAnalytics occupy one position,so at that time it range from 1-3.

in my DonationAnalytics.java the main function, I just use args[0] args[1] args[2] to do that. If there are the problem of index over bound or some fault.
please modify my main argument index according to the running system.

one thing more is I don't modify the run.sh in test dir,because I just test in my own computer with the test case in test file and it works fine,so I don't know whether there are needs for modify that.

that is the end of readme, feel free to contact me if there are some problem with it. I run the example in my own computer and every thing just works well.
So I thought there should be okay on the test machine. If there are some faults ,please see my instructions first.