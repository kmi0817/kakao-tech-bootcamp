class Fighter extends Character{
    private int hp;
    private int power;
    private String name;

    Fighter(int id, int hp, int power) {
        super(id);
        this.hp = hp;
        this.power = power;
    }

    int getHp() {
        return hp;
    }

    void setHp(int hp) {
        this.hp = hp;
    }

    int getPower() {
        return power;
    }

    String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    boolean isDead() {
        return hp > 0 ? false : true;
    }

    void attack(Fighter target) {
        target.setHp(target.getHp() - power);
    }
}
