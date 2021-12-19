package com.georgeisaev.fundamentals.sharedobjects.threadconfinement;

import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.SortedSet;
import java.util.StringJoiner;
import java.util.TreeSet;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Slf4j
@Data
public class StackConfinement {

    Ark ark = new Ark();

    public static void main(String[] args) {
        List<Animal> cats = List.of(
                new Animal(Animal.MALE, "male cat", "cat"),
                new Animal(Animal.FEMALE, "female cat", "cat"),
                new Animal(Animal.MALE, "male cat 2", "cat"),
                new Animal(Animal.FEMALE, "female cat 2", "cat")
        );
        StackConfinement stackConfinement = new StackConfinement();
        stackConfinement.loadTheArk(cats);
        log.info("{}", stackConfinement);
    }

    public int loadTheArk(Collection<Animal> candidates) {
        SortedSet<Animal> animals;
        int numPairs = 0;
        Animal candidate = null;
        // animals confined to method, don't let them escape!
        animals = new TreeSet<>(new SpeciesGenderComparator());
        animals.addAll(candidates);
        for (var animal : animals) {
            if (isNull(candidate) || !candidate.isPotentialMate(animal)) {
                candidate = animal;
            } else {
                ark.load(new AnimalPair(candidate, animal));
                numPairs++;
                candidate = null;
            }
        }
        return numPairs;
    }

    @Data
    static class Ark {

        List<AnimalPair> pairs = new ArrayList<>();

        void load(AnimalPair pair) {
            pairs.add(pair);
        }

    }

    @Data
    @RequiredArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
    static class Animal {

        static final boolean FEMALE = false;
        static final boolean MALE = true;
        boolean gender;
        String name;
        String species;

        public boolean isPotentialMate(Animal other) {
            return this.gender != other.gender && (isNull(species) && isNull(other.species)
                    || nonNull(species) && this.species.equals(other.species));
        }

        @Override
        public String toString() {
            return new StringJoiner(", ", Animal.class.getSimpleName() + "[", "]")
                    .add(name)
                    .toString();
        }

    }

    @Data
    @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
    static class AnimalPair {

        Animal first;
        Animal second;

        @Override
        public String toString() {
            return new StringJoiner(", ", AnimalPair.class.getSimpleName() + "[", "]")
                    .add(String.valueOf(first))
                    .add(String.valueOf(second))
                    .toString();
        }

    }

    static class SpeciesGenderComparator implements Comparator<Animal> {

        @Override
        public int compare(Animal o1, Animal o2) {
            return Boolean.compare(o1.gender, o2.gender);
        }

    }

}
