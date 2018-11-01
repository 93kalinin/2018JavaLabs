package lab3;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Stream;

import static lab3.Property.Identifier;
import static lab3.Property.Type;

/**
 * Создает, загружает в память, хранит, изменяет и удаляет объекты недвижимости properties.
 * Для идентификации объекта недвижимости ему сопоставляется уникальный кадастровый номер Identifier.
 */
final class PropertyHandler {

    private static final Map<Identifier, Property> properties = new HashMap<>();
    private static final String DATABASE = "./PropertiesDB";
    /*
     Метод add отвечает за добавление объектов недвижимости в базу данных. При попытке добавить
     объект недвижимости с совпадающим кадастровым номером он бросает исключение, после чего
     пользователь может решить не изменять данные или вызвать метод override, тем самым обойдя
     проверку на совпадение кадастровых номеров и переписав данные.
     */
    private PropertyHandler() {}

    static void add(Identifier identifier, Type type, BigDecimal price)
    throws IllegalArgumentException {
        if (properties.containsKey(identifier))
            throw new IllegalArgumentException("Объект недвижимости с таким кадастровым "
                + "номером уже содержится в базе данных");
        override(identifier, type, price);
        }

    static void override(Identifier identifier, Type type, BigDecimal price)
    throws IllegalArgumentException {
        Property candidate;
        switch (type) {
            case LAND:  candidate = new Land(price);  break;
            case HOUSE:  candidate = new House(price);  break;
            case COUNTRY_HOUSE:  candidate = new CountryHouse(price);  break;
            case FLAT:  candidate = new Flat(price);  break;
            default:
                throw new IllegalArgumentException("Такой тип недвижимости не предусмотрен");
            }
        properties.put(identifier, candidate);
        }

    static void remove(Identifier identifier) throws IllegalArgumentException {
        if (properties.remove(identifier) == null)
            throw new IllegalArgumentException("Этот объект недвижимости отсутствует в базе данных");
        }

    static void save() throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(DATABASE))) {
            for (Map.Entry<Identifier, Property> entry : properties.entrySet()) {
                writer.println(entry.getKey() + " " + entry.getValue());
        }   }   }


    static void load()
    throws IOException, IllegalArgumentException, NoSuchElementException {
        try (BufferedReader reader = new BufferedReader(new FileReader(DATABASE))) {
            String entry = reader.readLine();
            while (entry != null) {
                String[] values = entry.trim().split(" ");
                add(new Identifier(values[0]), Type.parse(values[1]), new BigDecimal(values[2]));
                entry = reader.readLine();
        }   }   }

    static Stream<Map.Entry<Identifier, Property>> getProperties() {
        return properties
            .entrySet()
            .stream();
        }

    static Property find(Identifier id) throws IllegalArgumentException {
        if (properties.containsKey(id)) return properties.get(id);
        else throw new IllegalArgumentException("Не удалось найти объект недвижимости");
    }   }