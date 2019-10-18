package it.alex.mylab.library.main;

import org.reflections.Reflections;

import java.util.Set;

public class AnnotationAnalyzer {
    public Set reflectionsScan() {
        Reflections reflections = new Reflections("it.alex.mylab.library.");
        return reflections.getTypesAnnotatedWith(AvailableOperation.class);
    }
}
