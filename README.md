
# Generic-Fixture

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.github.gabsouza98/generic-fixture/badge.svg?style=for-the-badge)](https://mvnrepository.com/artifact/io.github.gabsouza98/generic-fixture)

#### Never struggle again with creating random data for your tests.

Library created with the goal of increasing productivity in developing tests in Java.

Provides a method to generate a Fixture for any class.

This library can generate an instance of any Class, with random values for each field, respecting the field's type. 
Also, it can generate according to SpringBoot's regular restrictions (spring-boot-starter-validation).
There's also an option to customize specific fields, if random values are not desired in some cases.

# How to Use

To showcase the usage, the Person Class bellow will be used.

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
## Random Data Generation

It's simple! Just inform the Class that you want to generate random data in the method parameter!

``` Java
Person person = GenericFixture.generate(Person.class);
System.out.println(person);
```
The console will display something similar:
```
Person(name=fkosbOWEFF, email=YMZ@email.com, age=5, address=gBpTpqg57w, gender=MAN, pet=Pet(name=3hGEoV7U6G, breed=Kl1zaV49U8))
```

## Customized Data Generation

It's possible to customize any field during generation.

To do this, it is necessary to use a HashMap whose **key** is the path to the field and the **value** is customized, as shown in the example below.

``` Java
HashMap<String, Object> customFields = new HashMap<>();
customFields.put("name", "Fábio Brazza"); 
customFields.put("age", null);
customFields.put("pet.name", "Jack");

Person myClass = GenericFixture.generate(Person.class, customFields);
System.out.println(person);

```
Since "name" and "age" are in the root of the Person Class, the **key** is directly the field name. To specify the Pet name, the **key** must be the field's name path. In that case, "pet.name".
<br>
What will be printed in the console will be something similar to:

```
Person(name=Fábio Brazza, email=XEI@email.com, age=null, address=3lfeMIP7Hv, gender=MAN, pet=Pet(name=Jack, breed=dLKs687jkL))
```

This way, the Person instance had the fields below customized:
- name: Fábio Brazza
- age: null
- pet.name: Jack

Needless to say, if the value passed to the key "pet.name" isn't a String, an Exception will be thrown. 
<br> 
The Object passed as **value** in the HashMap must be compatible with the expected field type. 

## Lists, Maps and Arrays

It's possible to determine the number of items generated inside Fields that contain objects.
Currently, this is implemented for all types of Lists, Maps, and Arrays.
Consider the following Library class:

``` Java
public class Library {
    private List<String> clients;
    private Map<String, Integer> prices;
    private Book[] books;
}
```

Calling the following constructor:

``` Java
Library myLibrary = GenericFixture.generate(Library.class, 3);
//or
Library myLibraryWithCustomFields = GenericFixture.generate(Library.class, <customFieldsMap>, 3);
```
Generates 3 random Objects for the fields clients, prices and books. 
If the constructor used doesn't inform the numberOfItems parameter, the default value is 1.

Although not widely used, the generation of fields like Map<K,V>[] it's supported.

## Supported Annotations

Accepted annotations from jakarta.validation.constraints

### String Annotations
* @Pattern: _The generated String satisfies the regexp._
* @Email: _The generated String satisfies the Email pattern._
* @Size: _The generated String will have the specified length._

### Number Annotations
* @Min: _The generated number will be at least the minimum value._
* @Max: _The generated number will be at most the maximum value._
* @Digits: _The generated number will have the specified amount of digits._
* @DecimalMin: _The generated number will be higher or equal to the specified minimum._
* @DecimalMax: _The generated number will be lowed or equal to the specified minimum._
* @Positive: _The generated number will be positive._
* @Negative: _The generated number will be negative._
* @PositiveOrZero: _The generated number will be positive or zero._
* @NegativeOrZero: _The generated number will be negative or zero._

### Date/Time Annotations
* @Past: _The generated date/time will be in the past._
* @Future: _The generated date/time will be in the future._
* @PastOrPresent: _The generated date/time will be in the past or equal to present._
* @FutureOrPresent: _The generated date/time will be in the future or equal to present._

## How It Works
This section aims to clarify some of the inner workings of the generate() method.

### Recursion
The generate() method uses the package fully qualified name initials to distinguish between a Java Class, 
and a user created Class. In case it's a user class, a recursion step occurs, allowing to generate random 
values for that inner class. 
<br> 
This is what makes possible to populate the Pet field of the Person class, 
without having to call generate(Pet.class) first, than setting that value to Person object. For each field in a Class, 
this distinction is made, which allows the process of recursion to occur until there's no more user defined Classes, and all 
field types come from Java packages.
<br>
When the recursion call occurs, the name of the current field is passed as a parameter to the generate() method,
keeping track of the path taken inside the class. This is used to check if we have reached one of 
the customFields map keys, to set the field with the custom value.
<br>

### Constructors with Arguments
If the no arguments constructor is absent/hidden, then the constructor with the least amount of parameters is used.
If the argument type is primitive, a random value is generated. If the argument type is an Object, it's instantiated with null. 
This is the simplest way to generate an instance of a class without noArgsConstructor. The null values will be populated
after instantiation, when going through the field iteration.

### Interfaces and Abstract Classes
Since it's possible to define fields with types such as "List" or "Map", without defining which 
implementation to use, the GenericFixture checks if the field type is an interface or an abstract class. Then, 
it chooses a default class that implements/extends that type. If the field type is not an interface/abstract class, the 
implementation provided is respected (i.e., a LinkedList field will be instantiated as a LinkedList).

Here are the default implementations used:

#### Collection
* List &rarr; ArrayList
* Queue &rarr; PriorityQueue
* Deque &rarr; ArrayDeque
* Set &rarr; HashSet
* SortedSet &rarr; TreeSet

#### Map/Dictionary
* Map, AbstractMap &rarr; HashMap
* ConcurrentMap &rarr; ConcurrentHashMap
* SortedMap, NavigableMap &rarr; TreeMap
* ConcurrentNavigableMap &rarr; ConcurrentSkipListMap
* Dictionary &rarr; Hashtable

#### Temporal
* ChronoLocalDate &rarr; LocalDate
* ChronoLocalDateTime &rarr; LocalDateTime
* ChronoZonedDateTime &rarr; ZonedDateTime

## Currently Supported Java Types:

* Long / long
* Integer / int
* Double / double
* Boolean / boolean
* Character / char
* String
* UUID
* BigDecimal
* LocalDateTime
* OffsetDateTime
* Instant
* Date
* ZonedDateTime
* LocalDate
* LocalTime
* OffsetTime
* ChronoLocalDate
* ChronoLocalDateTime
* ChronoZonedDateTime
* List
* ArrayList
* ArrayList
* Queue
* Deque
* Set
* SortedSet
* Map
* AbstractMap
* SortedMap
* NavigableMap
* TreeMap
* Dictionary
* Enum