package lab3;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Вместе с наследующими от него классами Land, CountryHouse, Hose и Flat моделирует
 * объекты недвижимости четырех типов: земельный участок, дача, частный дом и квартира.
 */
abstract class Property {
    /**
     * Класс Identifier моделирует корректный кадастровый номер объекта недвижимости и
     * служит для идентификации этих объектов.
     */
    static class Identifier {

        private static final String REGEX = "\\d{2}:\\d{2}:\\d{6,7}:\\d+";
        private static final Pattern PATTERN = Pattern.compile(REGEX);

        final String identifier;

        Identifier(String candidate) throws IllegalArgumentException {
            Matcher matcher = PATTERN.matcher(candidate);
            if ( ! matcher.matches())
                throw new IllegalArgumentException("Недопустимый кадастровый номер. "
                    + "Пример корректного кадастрового номера: 47:14:1203001:814");
            this.identifier = candidate;
            }

        @Override
        public String toString() { return identifier; }

        @Override
        public int hashCode() { return Objects.hash(identifier); }

        @Override
        public boolean equals(Object that) {
            if (that == null) return false;
            if (!(that instanceof Identifier)) return false;
            Identifier other = (Identifier) that;
            return this.identifier.equals(other.identifier);
        }   }
    /*
     Для цены объекта недвижимости и процентной ставки налога имеют значение не более двух цифр после
     запятой, поэтому используется BigDecimal, обеспечивающий точные вычисления и правильное округление.
     Поле taxRates ставит в соотвествие типу недвижимости процентую ставку. Перечисление Type описывает
     допустимые типы недвижимости и предоставляет метод для распознания типа из строки.
     */
    enum Type {

        LAND, COUNTRY_HOUSE, HOUSE, FLAT;

        static Type parse(String candidate) throws NoSuchElementException {
            switch (candidate.toUpperCase()) {
                case "LAND":  return LAND;
                case "COUNTRY_HOUSE":  return COUNTRY_HOUSE;
                case "HOUSE":  return HOUSE;
                case "FLAT":  return FLAT;
                default:
                    throw new NoSuchElementException("Не удалось распознать данный тип недвижимости");
        }   }   }

    private static final EnumMap<Type, BigDecimal> taxRates = new EnumMap<>(Type.class);

    static void setTaxRate(Type type, BigDecimal rate) throws IllegalArgumentException {
        if (rate.compareTo(new BigDecimal("0")) <= 0
        ||  rate.compareTo(new BigDecimal("100")) >= 0)
            throw new IllegalArgumentException("Налог не может быть выше 100% или ниже 0%");
        taxRates.put(type, rate.setScale(2, RoundingMode.CEILING));
        }

    static BigDecimal getTaxRate(Type type) throws NoSuchElementException {
        if (taxRates.containsKey(type)) return taxRates.get(type);
        else throw new NoSuchElementException("Налог для данного типа недвижимости не задан");
        }


    private final Type type;
    private BigDecimal price;

    @Override
    public String toString() { return type + " " + price; }
    Type getType() { return type; }
    BigDecimal getPrice() { return price; }

    Property(Type type, BigDecimal price) throws IllegalArgumentException {
        if (price.compareTo(new BigDecimal("0")) <= 0)
            throw new IllegalArgumentException("Цена не может быть меньше или равна нулю");
        this.price = price;  this.type = type;
        }

    BigDecimal calculateTax() throws NoSuchElementException {
        final BigDecimal PERCENT_TO_FRACTION = new BigDecimal("0.01");
        return getTaxRate(type)
            .multiply(PERCENT_TO_FRACTION)
            .multiply(price)
            .setScale(2, RoundingMode.CEILING);
    }   }

/*
 Без нижеследующих классов вполне можно обойтись, в силу чего они по сути одинаковые и почти пустые.
 Наличие этих классов является требованием задания. Каждый из них хранит собственный тип в поле type,
 унаследованном от Property. Это упрощает работу с такими классами.
 */
final class Land extends Property {
    Land(BigDecimal price) throws IllegalArgumentException { super(Type.LAND, price); }
    }

final class CountryHouse extends Property {
    CountryHouse(BigDecimal price) throws IllegalArgumentException {
        super(Type.COUNTRY_HOUSE, price);
    }   }

final class House extends Property {
    House(BigDecimal price) throws IllegalArgumentException { super(Type.HOUSE, price); }
    }

final class Flat extends Property {
    Flat(BigDecimal price) throws IllegalArgumentException { super(Type.FLAT, price); }
    }