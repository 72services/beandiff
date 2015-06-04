# BeanDiff
BeanDiff is a simple library to compare to objects using reflection. 

The objects don't need to have the same type. BeanDiff looks for matching field names and compares the values.

```java
  A a = new A();
  a.setName("Name");

  B b = new B();
  b.setName("Name");

  BeanDiff diff = new BeanDiff();
  diff.diff(a, b);

  assertTrue(diff.getDifferences().isEmpty());
```

##License
BeanDiff is Open Source under Apache License Version 2
http://www.apache.org/licenses/LICENSE-2.0
