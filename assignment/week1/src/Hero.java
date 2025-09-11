class Hero extends Fighter {
    private int lives;

    Hero(int id, int hp, int power, int lives) {
        super(id, hp, power);
        this.lives = lives;
        setName("Hero" + id);
    }

    int getLives() {
        return lives;
    }

    void incrementLife() {
        lives++;
    }

    void decrementLife() {
        lives--;
    }

    boolean canRespawn() {
        return lives > 2 ? true : false;
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