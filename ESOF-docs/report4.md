# 4th Report - ESOF

## Index

1. [Introduction](#introduction)
2. [Testability](#testability)
3. [Test Statistics](#test-statistics)
4. [Bugs] (#bugs)
4. [Critical Analysis](#critical-analysis)



## Introduction

> Software testability is the degree to which a software artifact (i.e. a software system, software module, requirements- or design document) supports testing in a given test context. If the testability of the software artifact is high, then finding faults in the system (if it has any) by means of testing is easier.

>  _Definition from [Wikipedia]_
[Wikipedia]: https://en.wikipedia.org/wiki/Software_testability

In this report we will check **Rust**'s degree of testability by analising some factors like:
- **Controllability**: The degree to which it is possible to control the state of the component under test (CUT) as required for testing.
- **Observability**: The degree to which it is possible to observe (intermediate and final) test results.
- **Isolateability**: The degree to which the component under test (CUT) can be tested in isolation.
- **Separation of concerns**: The degree to which the component under test has a single, well defined responsibility.
- **Understandability**: The degree to which the component under test is documented or self-explaining.
- **Heterogeneity**: The degree to which the use of diverse technologies requires to use diverse test methods and tools in parallel.



## Testability 

By talking with some contributtors to the **Rust Project** and by reading the [Test chapter] on **Rust** blog we learned that Rust turns the test driven approach very easy and that the language lends itself well to testing because first Rust has no inheritance or other convoluted form of dependency which makes the testing of isolated componentes much easier. For example, when we want to give a certain behavior to a set of types, we create a trait and implement it for each type and the trait itself can be tested independently. 

During the tests, the compiler performs the heavy lifting when it comes to common bugs, so you can focus on other things and the language is a good match with unitary tests which leads to easily composable code. It is very simple to maintain coverage and to deploying the service. 

For Rust, the usual practice is having tests right below where something is defined. 

One of the biggest concerns about the testability of Rust is the lack of support for coverage analysis tools. 

Its is very easy to isolate components in Rust because there is no inheritance or ohter convulted form of dependency.
If you want to create an hierarchy that reuses data, you must compose the types explicitly (include the supertype as a field), this is called "composition over inheritance".

Another thing that makes Rust easier to test is that it has no exceptions. On some languages, it's hard to understand which set of exceptions you need to test for each piece of code. (Rust has panics, that typically terminate the program; but panics aren't an usual technique for error handling, and instead signal that the program encountered an error that isn't recoverable).

As with any project, the project itself plays a big role. That said, for unit testing Rust's existing test tools are more than adequate. Integration testing may be more difficult, depending on what you're integrating, but Cargo supports custom tests that don't use the built-in harness, so it's not too hard to write tests that do what you need.



[Test chapter]:https://doc.rust-lang.org/book/testing.html


## Test Statistics


## Bugs
In the issues we found out that there are lots of cases in which integer suffixes aren't needed at all. Usually audits are needed to make sure the intended type is inferred, and disabling the fallback to _i32_ wouldn't really work for tests. A solution would be to modify the compiler to dump the equivalent of _--pretty=typed_ (with pretty-printing showing no suffixes on expressions) into some temporary directory, then compare the results from two make check runs, one with all the suffixes removed. 

However, we didn't really knew how to fix the bug and we only found it thanks to the discussion on the issue, the bug was kind of solved by the author of the issue but only for some cases.

## Critical Analysis
**Rust** is a well organized project with a lot of contributors and very active. Despite this it was a little hard to gatter all information and if it weren't for [Rust's Reddit] and the their [official forum] it would be impossible to do any of the reports. This last report was a little bit more difficult because we had to work with the repository in a more specific way, the testing part of Rust required a lot of stuff that we didn't had any ideia how to use. 

Regarding the _test statistics_ we had several problems while trying to run the tests in this repository and we could not find any information about any _test statistics_ related to tests run on Rust.


[Rust's Reddit]:https://www.reddit.com/r/rust/
[official forum]:https://internals.rust-lang.org/
