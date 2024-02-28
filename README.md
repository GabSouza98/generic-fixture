### Never struggle again with creating random data for your tests.


# Generic-fixture

Library created with the aim of increasing productivity in developing tests in Java.

Provides a method to generate a Fixture for any class.

This library can generate attributes according to springboot's regular restrictions (spring-boot-starter-validation).

# How to use

To explain Generic fixture, the class bellow called Person will be used.

``` Java
public class Person {
  private String name;
  @Email
  private String email;
  @Positive
  private Long age;
  private String address;
  private GenderEnum gender;
  private Pet pet;
}

public enum GenderEnum {
  MAN,
  WOMAN,
  NON_BINARY
}

public class Pet {
  private String name;
  private String breed;
}


```
## Random data Generation

It's simple! Just inform the class in the method parameter that you want to generate the random data!

``` Java
Person person = GenericFixture.generate(Person.class);
System.out.println(person);
```
What will be printed in the console will be something similar:
```
Person(name=fkosbOWEFF, email=YMZ@email.com, age=5, address=gBpTpqg57w, gender=MAN, pet=Pet(name=3hGEoV7U6G, breed=Kl1zaV49U8))
```

## Customized data generation

It's possible to customize any attribute in generation.

To do this, it is necessary to use a HashMap whose key value is the name of the attribute and the key value is customized, as shown in the example below.

``` Java
HashMap<String, Object> customFields = new HashMap<>();
customFields.put("name", "Fábio Brazza"); 
customFields.put("age", null);
customFields.put("pet.name", "Jack");

Person myClass = GenericFixture.generate(Person.class, customFields);
System.out.println(person);

```

What will be printed in the console will be something similar:

```
Person(name=Fábio Brazza, email=XEI@email.com, age=null, address=3lfeMIP7Hv, gender=MAN, pet=Pet(name=Licudo, breed=Vira lata))
```


This way, the Person instance had the fields below customized:
- name: Fábio Brazza
- age: null
- pet.name: Jack

