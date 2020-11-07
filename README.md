# CSX42: Assignment 5
## Name: Nicholas Vallejos

-----------------------------------------------------------------------
-----------------------------------------------------------------------

## Instruction to clean:

```commandline
ant -buildfile numberPlay/src/build.xml clean
```

-----------------------------------------------------------------------
## Instruction to compile:

```commandline
ant -buildfile numberPlay/src/build.xml all
```

-----------------------------------------------------------------------
## Instruction to run:

#### Use the below command to run the program.

```commandline
ant -buildfile numberPlay/src/build.xml run \
-Dinput="<Input filename>" \
-DacceptableWordsFile="<Acceptable words file>" \
-Dk="<Max size of the list containing the most frequent words.>" \
-DtopKOutputFile="<Name of the output file to which the top K words are written for each sentence analyzed>"
-DspellCheckOutputFile="<Name of the output file to which the possible spelling fixes are written in the previously mentioned format for each word analyzed>"
```

-----------------------------------------------------------------------
## References:
- HashMap Documentation: https://docs.oracle.com/javase/8/docs/api/java/util/HashMap.html
- HashSet Documentation: https://docs.oracle.com/javase/8/docs/api/java/util/HashSet.html
- ArrayList Documentation: https://docs.oracle.com/javase/8/docs/api/java/util/ArrayList.html
- Idea for priority queue: https://piazza.com/class/k5k3yuyx97612d?cid=452
- Builder Pattern Idea: Panopto lecture on Builder Pattern on MyCourses
- Visitor Pattern Idea: Panopto lecture on Visitor Pattern on MyCourses

-----------------------------------------------------------------------
## Important Notes:
- I used the same validator classes and custom exceptions from the previous assignment 3 which includes:
	- CommandArgHandler
	- Validator
	- ValidatorUtil
	- EmptyFileException
	- InvalidArgNameException
	- NumberOfArgsException
- I used the FileProcessor class from previous assignment 3 & 2

-----------------------------------------------------------------------
## Number of slack days used:
- I used 4 slack days
