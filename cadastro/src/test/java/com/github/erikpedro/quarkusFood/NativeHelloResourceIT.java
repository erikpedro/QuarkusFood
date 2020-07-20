package com.github.erikpedro.quarkusFood;

import io.quarkus.test.junit.NativeImageTest;

@NativeImageTest
public class NativeHelloResourceIT extends HelloResourceTest {

    // Execute the same tests but in native mode.
}