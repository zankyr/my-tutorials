# Spring Boot Basic

Basic SpringBoot example from [official tutorial](https://spring.io/guides/gs/spring-boot/).

## Basic application
The class `HelloController.java` contains a basic REST method that returns a simple string when called.
The class `Application.java` is the entry point of the application and provides a method to print all the beans created by SpringBoot

## Tests
The class `HelloControllerTest.java` provides an example of how to configure a mocked mvc test.
`HelloControllerIntegrationTest.java` provides a very simple integration test, with random server port.

## How to run the application
1. Launch (via IDE or command line) the class `Application.java`.
1. In the terminal you'll see all the beans provided by SpringBoot.
1. Calling (curl or a RESTClient) the URL `localhost:9081` you should get a response from the service.
