package com.amigoscode.bughunt.easy.bug46;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AnimalTest {

    @Test
    void dogSoundIncludesBreed() {
        Animal.Dog d = new Animal.Dog("labrador");
        assertThat(d.sound()).isEqualTo("LABRADOR bark!");
    }

    @Test
    void differentBreedDifferentSound() {
        Animal.Dog d = new Animal.Dog("poodle");
        assertThat(d.sound()).isEqualTo("POODLE bark!");
    }
}
