class Healer extends Hero {
    Healer(int id, int hp, int power, int lives) {
        super(id, hp, power, lives);
        setName("Healer" + id);
    }

    void reviveHero(Hero hero) {
        if (!hero.isDead()) {
            throw new RuntimeException("히어로가 이미 살아 있으므로 환생시킬 수 없습니다.");
        }

        if (getLives() < 2) {
            throw new RuntimeException("목숨을 나눠줄 수 없습니다.");
        }


        hero.setHp(100);
        hero.incrementLife();
        decrementLife();
    }
}
