package com.example.cardealerxml.util;

import jakarta.xml.bind.JAXBException;

public interface XmlParser {
    <E> E parse (Class<E> clazz, String path) throws JAXBException;

    <E> void exportToFile(E object, String path) throws JAXBException;
}
