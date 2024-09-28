# Ouroboros

A library to allow seamless integration between Kotlin/Native and Kotlin/JVM.
This can also be used as part of a KMP project to delegate JVM functionality to native
implementations.

### Lifting the barrier

In Kotlin Native you can easily write stub classes to access JVM objects using Ouroboros:

```kotlin
import io.karma.ouroboros.BridgedType
import io.karma.ouroboros.Bridged

@BridgedType("com.foo.SomeJavaClass")
class SomeJavaClass {
    // This will invoke com.foo.SomeJavaClass#myFunction on the JVM object if called
    @Bridged
    external fun myFunction(i: i32, s: String): String
}
```

The same is also possible the other way around:

```kotlin
import io.karma.ouroboros.BridgedType
import io.karma.ouroboros.Bridged

@BridgedType("com.foo.SomeNativeClass")
class SomeNativeClass {
    // This will invoke the native com.foo.SomeNativeClass#myFunction if called
    @Bridged
    external fun myFunction(i: i32, s: String): String
}
```

### Whats with the name?

> The ouroboros or uroboros (/ˌjʊərəˈbɒrəs/;[2] /ˌʊərəˈbɒrəs/[3]) is an ancient symbol depicting a
> serpent or dragon[4] eating its own tail.

The name was chosen because of the fact that this is a project to add interoperability between
Kotlin (Native)  
and Kotlin (JVM) using only Kotlin. It's like the Ouroboros trying to eat its own tail.