### Never struggle again with creating random data for your tests.


# generic-fixture
Provides a method to generate a Fixture for any class

Never struggle again with creating random data for your tests.


# Como Utilizar

Para os exemplos será utilizado a classe abaixa chamada Person para gerar os dados randômicos.


``` java
public class Person {
  private String name;
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
Utilizar a assinatura abaixo:

``` Java
Person person = GenericFixture.generate(Person.class);
System.out.println(person);
```
O que será printado no console será algo semelhante:
```
Person(name=vAwUgnVv6a, age=3443304950750282643, address=WBMaRmssIG, gender=MAN, pet=Pet(name=sGKefdGAGm, breed=jo9zXvjLtt))
```


É possível personalizar qualquer atributo da classe.

``` Java
HashMap<String, Object> customFields = new HashMap<>();
customFields.put("name", "Fábio Brazza"); 
customFields.put("age", null);
customFields.put("pet.name", "Jack");

Person myClass = GenericFixture.generate(Person.class, customFields);
```

O que será printado no console será algo semelhante:
```
Person(name=Fábio Brazza, age=null, address=WBMaRmssIG, gender=MAN, pet=Pet(name=Jack, breed=jo9zXvjLtt))
```


Dessa maneira a instância myClass terá os valores definidos de
- name: Fábio Brazza
- age: nulo
- pet.name: Jack

