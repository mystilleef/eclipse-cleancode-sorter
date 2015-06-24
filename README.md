The Clean Code Sorter implements a sorting algorithm based on the newspaper metaphor described by Robert C. Martin in 
his book titled Clean Code. Robert describes the sorting philosophy as follows:

"A class should be readable like a newspaper, starting with the most important methods, followed by methods which are 
invoked later in the execution flow."

The sorter is implemented such that the calling method (the caller) is placed as close as possible to the called 
method (the callee) to improve the readability of the source code. 

The most important methods are place at the top of the document while supporting methods are placed immediately below 
the methods they support. The result is that reading the source code, from top to bottom, is akin to reading prose. 

To allow relevant methods to be clustered as close as possible to each other, the following sorting algorithm is used.

In general, constructors are sorted before public methods, which are sorted before package methods, which are sorted 
before protected methods, which are sorted before private methods. In addition, methods are also sorted by the number 
of parameters they have and also their return type. Finally, the clean code sorting algorithm is applied to place 
called methods right below calling methods, and inner classes are placed at the end of the document.


...developing...
