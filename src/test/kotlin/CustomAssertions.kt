import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue

fun assertEmpty(collection: Collection<Any>) =
    assertEquals(0, collection.size, "Collection is not empty")
fun assertNotEmpty(collection: Collection<Any>) =
    assertTrue(collection.isNotEmpty(), "Collection is empty")