# BeanDiff
BeanDiff is a simple library to compare two objects using reflection. 

The objects don't need to have the same type. BeanDiff looks for matching field names and compares the values.

##Usage
```java
  A a = new A();
  a.setName("Hello");

  B b = new B();
  b.setName("World");

  BeanDiff diff = new BeanDiff();
  diff.diff(a, b);

  assertTrue(diff.getDifferences().isEmpty());
```

The BeanDiff object contains a map of type Difference and Difference contains the path to the property and the compared values.

###Example ouput of a Difference
```
Difference{path=/name, left=Hello, right=World}
```

BeanDiff is also using reflection for the string representation of the diff values.

##License
BeanDiff is Open Source under Apache License Version 2
http://www.apache.org/licenses/LICENSE-2.0
