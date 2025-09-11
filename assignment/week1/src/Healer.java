class Healer extends Hero {
    Healer(int id, int hp, int power, int lives) {
        super(id, hp, power, lives);
        setName("Healer" + id);
    }

    void reviveHero(Hero hero) {
        if (getLives() < 2) {
            throw new RuntimeException("목숨을 나눠줄 수 없습니다.");
        }

        hero.incrementLife();
        decrementLife();
    }
}
