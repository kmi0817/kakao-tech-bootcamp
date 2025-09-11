class Hero extends Fighter {
    private int lives;
    private int maxHp;

    Hero(int id, int hp, int power, int lives) {
        super(id, hp, power);
        this.lives = lives;
        this.maxHp = hp;
        setName("Hero" + id);
    }

    int getLives() {
        return lives;
    }

    int getMaxHp() {
        return maxHp;
    }

    void incrementLife() {
        lives++;
    }

    void decrementLife() {
        lives--;
    }

    boolean canRespawn() {
        return lives > 0;
    }

    @Override
    boolean isDead() {
        return lives <= 0;
    }

    @Override
    public String toString() {
        return getName() + "\t: hp(" + getHp() + "),\tpower(" + getPower() +"),\tlives(" + getLives() + ")";
    }
}