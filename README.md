# Scala Builder

Scala Builder provides a _very_ simple trait `BuilderSupport` to facilitate a clean syntax when using the [Builder Pattern](http://en.wikipedia.org/wiki/Builder_pattern) in Scala applications.

Usage
-----

Here is a simple example of using `BuilderSupport` to clean up your Builder Pattern usage. 

Firstly, let's define the domain specific builder:

```scala
case class Person(forename: String, surname: String, age: Int)

case class PersonBuilder(forename: Option[String] = None, surname: Option[String] = "", age: Option[Int] = None) extends Builder[Person] {

  def withForename(value: String) = copy(forename = Some(value))
  
  def withSurname(value: String) = copy(surname = Some(value))
  
  def withAge(value: Int) = copy(age = Some(value))

  override def build(): Person = {
    // do some validation here...
    Person(forename.get, surname.get, age.get)
  }
}

object Builders {

  implicit object PersonBuildable extends Buildable[Person] {
    type Result = PersonBuilder

    override def newBuilder = PersonBuilder()
  }
}
```

And now let's mix in `BuilderSupport` to our app:

```scala
object MyApp extends App with BuilderSupport {
  import Builders._
  
  val addressBook = new AddressBook()
  
  // Forever 21 
  addressBook.add(
    build[Person]
      .withForename("Drew")
      .withSurname("Easter")
      .withAge(21)
  )
}
```

You can see the benefits here of not needing to explicitly call `get()` on `PersonBuilder` when adding a `Person` to the address book. `BuilderSupport` provides an implicit conversion to facilitate this, thus removing verbose boilerplate, and significantly increasing readability.
