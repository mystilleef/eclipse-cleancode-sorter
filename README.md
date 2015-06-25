# Eclipse Clean Code Sorter

The __Clean Code Sorter__ implements a sorting algorithm based on the newspaper metaphor described by Robert C. Martin in his book titled __[Clean Code](http://www.amazon.com/Clean-Code-Handbook-Software-Craftsmanship/dp/0132350882)__. Robert describes the sorting philosophy as follows:

_*"A class should be readable like a newspaper, starting with the most important methods, followed by methods which are invoked later in the execution flow."*_

The sorter is implemented such that the calling method (the caller) is placed as close as possible to the called 
method (the callee) to improve the readability of the source code. 

The most important methods are place at the top of the document while supporting methods are placed immediately below the methods they support. The result is that reading the source code, from top to bottom, is akin to reading prose. 

To allow relevant methods to be clustered as close as possible to each other, the following sorting algorithm is used.

In general, constructors are sorted before public methods, which are sorted before package methods, which are sorted 
before protected methods, which are sorted before private methods. In addition, methods are also sorted by the number of parameters they have and also their return type. Finally, the clean code sorting algorithm is applied to place 
called methods right below calling methods, and inner classes are placed at the end of the document.

Source code that looks like this,

```java
class Foo {

	int mA;
	String mB;

	void e() {}

	void d() {
		e();
	}

	void b() {
		d();
		e();
	}
	
	void c() {
		d();
	}

	void a() {
		b();
		c();
	}

	public Foo() {}
	
}
```

will be sorted as follows.

```java
class Foo {

	int mA;
	String mB;

	public Foo() {}

	void a() {
		b();
		c();
	}

	void b() {
		d();
		e();
	}
	
	void c() {
		d();
	}

	void d() {
		e();
	}

	void e() {}
}
```

## What Works

- Press Ctrl-Shift-Alt-F to sort document

## What's Coming

- Save action to automatically sort the document during save operations
- Clean up action to perform group sort operations on packages and projects
- Add command to source menu
- Add options to preferences UI
- feedback to user interface during sort operations


## Update Site

__P.S.__ This is very experimental software for testing purposes __ONLY__.

- [Update Site](https://github.com/mystilleef/eclipse-cleancode-sorter-updatesite/master/com.laboki.eclipse.updatesite.cleancodesorter)
