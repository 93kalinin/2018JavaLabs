package prac3;

//TODO: предотвратить стрельбу для случая, когда недостаточно патронов в винтовках. добавить комменты

abstract class Gun {

    protected int ammoInMagazine;
    protected final int magazineCapacity;

    Gun(int magazineCapacity) throws IllegalArgumentException {
        if (magazineCapacity < 0  ||  magazineCapacity > 1000)
            throw new IllegalArgumentException("Invalid magazine capacity");
        this.magazineCapacity = magazineCapacity;
    }

    final void reload() { ammoInMagazine = magazineCapacity; }
    final boolean magIsEmpty() { return ammoInMagazine == 0; }
    abstract void shoot();
}

class Pistol extends Gun {

    public enum Type { REVOLVER, SEMI_AUTOMATIC }
    public final Type type;

    Pistol(Type type, int magazineCapacity) {
        super(magazineCapacity);
        this.type = type;
        }

    @Override
    void shoot() {
        ammoInMagazine--;
        System.out.println("pow!");
    }
}

class Rifle extends Gun {

    public enum Type { BOLT_ACTION, LEVER_ACTION, PUMP_ACTION, SEMI_AUTOMATIC }
    public final Type type;

    Rifle(Type type, int magazineCapacity) {
        super(magazineCapacity);
        this.type = type;
    }

    @Override
    void shoot() {
        ammoInMagazine -= 3;
        System.out.println("bang! bang! bang!");
    }
}